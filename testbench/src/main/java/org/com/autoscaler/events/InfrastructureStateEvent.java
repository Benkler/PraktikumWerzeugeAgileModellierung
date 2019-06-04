package org.com.autoscaler.events;

import org.com.autoscaler.infrastructure.InfrastructureState;

public class InfrastructureStateEvent extends AbstractEvent {

    private final InfrastructureState state;
    
    public InfrastructureStateEvent(Object source, int clockTickCount, double intervalDurationInSeconds, InfrastructureState state) {
        super(source, clockTickCount, intervalDurationInSeconds);
        this.state = state;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 622342663226466121L;
    
    public InfrastructureState getInfrastructureState() {
        return state;
    }

}
