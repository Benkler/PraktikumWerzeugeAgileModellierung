package org.com.autoscaler.clock;



public interface IClockEventPublisher {

    
    public void fireClockEvent(int clockTickCount, double intervalDurationInMilliSeconds) ;

    public void fireTriggerWorkloadHandlerEvent(int clockTickCount, double intervalDurationInMilliSeconds) ;
    
    public void fireTriggerAutoScalerEvent(int clockTickCount, double intervalDurationInMilliSeconds) ;
    
    public void fireTriggerPublishInfrastructureStateEvent(int clockTickCount, double intervallDurationInMilliSeconds);
    
    public void fireStartSimulationEvent(int clockTickCount, double intervallDuarationInMilliSeconds);
    
    public void fireFinishSimulationEvent(int clockTickCount,double intervallDuarationInMilliSeconds);
    
    
}
