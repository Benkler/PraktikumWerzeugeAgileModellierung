package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;

public interface IInfrastructureModelEventPublisher {
    
    public void fireInfrastructureStateEvent(InfrastructureStateTransferObject state, ClockEvent clockEvent);

}
