package org.com.autoscaler.infrastructure;

import java.util.LinkedList;
import java.util.List;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.ScalingEvent;
import org.com.autoscaler.events.TriggerPublishInfrastructureStateEvent;
import org.com.autoscaler.events.WorkloadChangedEvent;
import org.com.autoscaler.queue.IQueue;
import org.com.autoscaler.util.ScalingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Singleton that forms the core of the infrastructure. <br>
 * Handles workload changes, scaling decision and processes workload according
 * to actual infrastructure capacity
 * 
 * @author Niko
 *
 */
@Component
public class InfrastructureModel implements IInfrastructureModel {

    private final Logger log = LoggerFactory.getLogger(InfrastructureModel.class);
    private boolean initialized = false;

    private VmBootingQueue vmBootingQueue;

    /*
     * Holds Information about current Infrastructure without queue
     */
    private InfrastructureState infrastructureState;

    /*
     * Represents the queue of the infrastructure
     */
    @Autowired
    private IQueue queue;

    @Autowired
    private IInfrastructureModelEventPublisher publisher;

    /**
     * Initialize infrastructure with given state. This is only allowed once!
     */
    @Override
    public void initInfrastructureModel(InfrastructureState infrastructure) {
        if (initialized)
            return;

        this.infrastructureState = infrastructure;
        initialized = true;
        vmBootingQueue = new VmBootingQueue();
        log.info("Infrastructure initialized: \n" + infrastructure.toString());

    }

    /**
     * The Clock tick is the smallest unit of time in the system. For each clock
     * tick, the model processes the current worklaod with the current available
     * virtual machines and de-/enqueues tasks from the queue
     */
    @Override
    public void handleClockTick(ClockEvent clockEvent) {
        
        //Check if new vms are ready
        checkForBootedVirtualMachines();
        
        //Calculate Capacity
        int currentCapacity = infrastructureState.getCurrentCapacityInTasksPerInterval();
        int currentArrivalRate = infrastructureState.getCurrentArrivalRateInTasksPerIntervall();
        int discrepancy = currentCapacity - currentArrivalRate;

        //Execute (de-)queueing
        if (discrepancy <= 0) { // arrival rate higher than capacity --> Enqueue
            log.info("Process Jobs: Arrival Rate at " + currentArrivalRate
                    + " tasks per Intervall. Current capacity at " + currentCapacity
                    + " tasks per Intervall. --> Enqueue " + Math.abs(discrepancy) + " elements");
            queue.enqueue(Math.abs(discrepancy), clockEvent);
        } else { // arrival rate lower than capacity --> Dequeue
            log.info(
                    "Process Jobs: Arrival Rate at " + currentArrivalRate + " tasks per Intervall. Current capacity at "
                            + currentCapacity + " tasks per Intervall. --> Dequeue " + discrepancy + " elements");
            queue.dequeue(discrepancy);
        }
        
        //Reduce boot time
        vmBootingQueue.reduceWaitingAmount();

    }

    /**
     * Workload Handler fired a workload change which is processed by the
     * Infrastructure Model. New workload is set
     */
    @Override
    public void changeWorkload(WorkloadChangedEvent wlChangedEvent) {
        log.info("Process workkload change event at clock tick " + wlChangedEvent.getClockTickCount()
                + ". New Arrival rate in tasks per intervall: "
                + wlChangedEvent.getWorkloadInfo().getArrivalRateInTasksPerIntervall());

        infrastructureState.setWorkload(wlChangedEvent.getWorkloadInfo());

    }

    /**
     * Interface in case another component requires the current state. <br>
     * Important: This is the state at a specific clock interval and does not
     * include any moving average. <br>
     * If a moving average is required, the interested component periodically need
     * to request this state and calculates whatever it desires
     */
    @Override
    public InfrastructureStateTransferObject getInfrastructureState() {
        // TODO build state
        InfrastructureStateTransferObject state = new InfrastructureStateTransferObject();
        state.setCurrentArrivalRateInTasksPerIntervall(infrastructureState.getCurrentArrivalRateInTasksPerIntervall());
        state.setCurrentArrivalRateInTasksPerSecond(infrastructureState.getCurrentArrivalRateInTasksPerSecond());
        state.setCurrentCapacityInTasksPerIntervall(infrastructureState.getCurrentCapacityInTasksPerInterval());
        state.setCurrentCapacityInTasksPerSecond(infrastructureState.getCurrentCapacityInTasksPerSecond());
        state.setQueueFillInPercent(queue.currentLevelInPercent());
        state.setVirtualMachines(new LinkedList<VirtualMachine>(infrastructureState.getVirtualMachines().values()));

        return state;
    }

    /**
     * After a variable amount of clock ticks, the clock triggers the infrastructure
     * to publish its state. This should model the fact that the infrastructure
     * state cannot be monitored on every clock tick
     */
    @Override
    public void publishInfrastructureState(TriggerPublishInfrastructureStateEvent event) {
        publisher.fireInfrastructureStateEvent(getInfrastructureState(), event.getClockTickCount(),
                event.getIntervallDuratioInMilliSeconds());

    }

    /**
     * Process the scaling decision provided by an auto scaler <br>
     * Downscaling can happen immediately (for now...). <br>
     * Up scaling needs so time as the individual VMS need to start
     */
    @Override
    public void scaleVirtualMachines(ScalingEvent event) {
        log.info("Sacaling Decision with new amount of virtual machines: " + event.getVirtualMachines().size());

        // Scale Down Immediately
        if (event.getMode() == ScalingMode.SCALE_DOWN) {
            infrastructureState.scaleDownVirtualMachines(event.getVirtualMachines());

            
        //scale up by enqueueing in booting queue    
        } else if (event.getMode() == ScalingMode.SCALE_UP) {
            log.info("Execute scaling UP decision. Previous amount of virtual machines: "
                    + infrastructureState.getVirtualMachines().size() + "  ");

           
            enqueueVirtualMachinesInBootingQueue(event.getVirtualMachines());

            

        } else {
            throw new IllegalArgumentException(
                    "Scale Mode not supported. Expected either Scale_down or Sclae_up  but given " + event.getMode());
        }

    }

    /*
     * Enqueue the set of virtual machines that where selected during the current
     * scale up decision. They will be dequeued and added to the infrastructure as
     * soon as there are completely up and running which is after a certain time of
     * clock ticks
     */
    private void enqueueVirtualMachinesInBootingQueue(List<VirtualMachine> vms) {
        log.info("Enqueue " + vms.size() + " virtual machines into booting queue");
        vmBootingQueue.addVirtualMachinesToQueue(vms);
    }
   
    /*
     * Check if vms are booted
     */
    private void checkForBootedVirtualMachines() {
     List<VirtualMachine> booted = vmBootingQueue.selectAndRemoveBootedVmsFromQueue();
     if(booted.size() > 0) {
         addVmsToInfrastructure(booted); 
     }
    }
    
    /*
     * Add booted vms to infrastructure
     */
    private void addVmsToInfrastructure(List<VirtualMachine> vms) {
        infrastructureState.scaleUpVirtualMachines(vms);
        
        log.info("Vms successfully booted: New amount of virtual machines: " + infrastructureState.getVirtualMachines().size() + "  ");
        
    }

}
