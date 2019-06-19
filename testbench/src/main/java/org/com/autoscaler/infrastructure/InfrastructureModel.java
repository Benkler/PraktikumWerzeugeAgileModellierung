package org.com.autoscaler.infrastructure;

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

@Component
public class InfrastructureModel implements IInfrastructureModel {

    private final Logger log = LoggerFactory.getLogger(InfrastructureModel.class);
    private boolean initialized = false;

    /*
     * Holds Information about current Infrastructure including Queue and Virtual
     * Machines
     */
    private InfrastructureState infrastructureState;

    @Autowired
    private IQueue queue;

    @Autowired
    private IInfrastructureModelEventPublisher publisher;

    @Override
    public void initInfrastructureModel(InfrastructureState infrastructure) {
        if (initialized)
            return; 
    
        this.infrastructureState = infrastructure;
        initialized = true;
    
    }
    
    @Override
    public void handleClockTick(ClockEvent clockEvent) {
        int currentCapacity = infrastructureState.getCurrentCapacityInTasksPerInterval(); 
        int currentArrivalRate = infrastructureState.getCurrentArrivalRateInTasksPerIntervall();

        int discrepancy = currentCapacity - currentArrivalRate;

        if (discrepancy <= 0) { // arrival rate higher than capacity --> Enqueue
            log.info("Process Jobs: Arrival Rate at " + currentArrivalRate
                    + " tasks per Intervall. Current capacity at " + currentCapacity
                    + " tasks per Intervall. --> Enqueue " + Math.abs(discrepancy) + " elements");
            queue.enqueue(Math.abs(discrepancy));
        } else { // arrival rate lower than capacity --> Dequeue
            log.info(
                    "Process Jobs: Arrival Rate at " + currentArrivalRate + " tasks per Intervall. Current capacity at "
                            + currentCapacity + " tasks per Intervall. --> Dequeue " + discrepancy + " elements");
            queue.dequeue(discrepancy);
        } 

    }

    @Override
    public void changeWorkload(WorkloadChangedEvent wlChangedEvent) {
        log.info("Process workkload change event at clock tick " + wlChangedEvent.getClockTickCount()
                + ". New Arrival rate in tasks per intervall: "
                + wlChangedEvent.getWorkloadInfo().getArrivalRateInTasksPerIntervall());

        infrastructureState.setWorkload(wlChangedEvent.getWorkloadInfo());

    }

    @Override 
    public InfrastructureStateTransferObject getInfrastructureState() {
        // TODO build state
        InfrastructureStateTransferObject state = new InfrastructureStateTransferObject();
        state.setCurrentArrivalRateInTasksPerIntervall(infrastructureState.getCurrentArrivalRateInTasksPerIntervall());
        state.setCurrentArrivalRateInTasksPerSecond(infrastructureState.getCurrentArrivalRateInTasksPerSecond());
        state.setCurrentCapacityInTasksPerIntervall(infrastructureState.getCurrentCapacityInTasksPerInterval());
        state.setCurrentCapacityInTasksPerSecond(infrastructureState.getCurrentCapacityInTasksPerSecond());
        
     
        state.setQueueFillInPercent(queue.currentLevelInPercent());
        state.setVirtualMachines(infrastructureState.getVirtualMachines());
        
        /*
         * Desired Capacity includes tasks in the Queue 
         */
        
        state.setDesiredCapacityInTasksPerIntervall(calculateDesiredCapacity());
        return state;
    }
    
    
    @Override
    public void publishInfrastructureState(TriggerPublishInfrastructureStateEvent event) {
        publisher.fireInfrastructureStateEvent(getInfrastructureState(), event.getClockTickCount(),
                event.getIntervallDuratioInMilliSeconds());

    }

    //TODO stimmt das?
    private int calculateDesiredCapacity() {
        int desiredCapacity = 0;
        desiredCapacity = infrastructureState.getCurrentArrivalRateInTasksPerIntervall() + queue.currentLevelInTasks();
        
        return desiredCapacity;
    }

    @Override
    public void scaleVirtualMachines(ScalingEvent event) {
        
        //Scale Down Immediately
        if(event.getMode() == ScalingMode.SCALE_DOWN) {
            infrastructureState.setVirtualMachines(event.getVirtualMachines());
        }else {
           // event.getVirtualMachines().get(0).
        }
        
    }

}
