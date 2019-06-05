package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.WorkloadChangedEvent;
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
    private InfrastructureInformation infrastructureInfo;

    private Queue queue;

    @Autowired
    private IInfrastructureModelEventPublisher publisher;

    @Override
    public void handleClockTick(ClockEvent clockEvent) {
        // TODO send infrastructure State event

    }

    @Override
    public void changeWorkload(WorkloadChangedEvent wlChangedEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initInfrastructureModel(InfrastructureInformation infrastructure) {
        if (initialized)
            return;

        this.infrastructureInfo = infrastructure;
        queue.setQueueParameters(infrastructure.getMinQueueLength(), infrastructure.getMaxQueueLength());
        initialized = true;

    }

    private InfrastructureState getState() {
        InfrastructureState state = new InfrastructureState();

        return state;
    }

}
