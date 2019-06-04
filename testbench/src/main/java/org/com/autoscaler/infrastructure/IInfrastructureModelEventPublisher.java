package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;

public interface IInfrastructureModelEventPublisher {
    
    public void fireInfrastructureStateEvent(InfrastructureState state, ClockEvent clockEvent);

}
