package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.WorkloadChangedEvent;
import org.com.autoscaler.queue.IQueue;
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

    private IQueue queue;

    @Autowired
    private IInfrastructureModelEventPublisher publisher;

    @Override
    public void handleClockTick(ClockEvent clockEvent) {
        int currentCapacity = infrastructureState.getCurrentCapacityInTasksPerInterval();
        int currentArrivalRate = infrastructureState.getCurrentArrivalRateInTasksPerIntervall();

        int discrepancy = currentCapacity - currentArrivalRate;

        if (discrepancy <= 0) { // arrival rate higher than capacity --> Enqueue
            log.info(
                    "Process Jobs: Arrival Rate at " + currentArrivalRate + " tasks per Intervall. Current capacity at "
                            + currentCapacity + " tasks per Intervall. --> Enqueue " + Math.abs(discrepancy) + " elements");
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
       

    }

    @Override
    public void initInfrastructureModel(InfrastructureState infrastructure) {
        if (initialized)
            return;

        this.infrastructureState = infrastructure;
        initialized = true;

        // TODO init Queue

    }

    @Override
    public InfrastructureStateTransferObject getInfrastructureState() {
        // TODO build state
        InfrastructureStateTransferObject state = new InfrastructureStateTransferObject();
        return state;
    }

}
