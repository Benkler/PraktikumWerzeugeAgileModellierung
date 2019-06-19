package org.com.autoscaler.events;

import org.com.autoscaler.infrastructure.InfrastructureStateTransferObject;

/**
 * This is an event to encapsulate a current state of the infrastructure and
 * send it to any other component that might be intereseted in reading it
 */
public class InfrastructureStateEvent extends AbstractEvent {

    private final InfrastructureStateTransferObject state;

    public InfrastructureStateEvent(Object source, int clockTickCount, double intervalDurationInMilliSeconds,
            InfrastructureStateTransferObject state) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);
        this.state = state;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 622342663226466121L;

    public InfrastructureStateTransferObject getInfrastructureState() {
        return state;
    }

}
