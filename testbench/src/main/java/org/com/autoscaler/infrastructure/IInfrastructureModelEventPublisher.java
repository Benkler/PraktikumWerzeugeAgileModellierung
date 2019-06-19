package org.com.autoscaler.infrastructure;

public interface IInfrastructureModelEventPublisher {
    
    public void fireInfrastructureStateEvent(InfrastructureStateTransferObject state,int clockTickCount, double intervalDurationInSeconds);

}
