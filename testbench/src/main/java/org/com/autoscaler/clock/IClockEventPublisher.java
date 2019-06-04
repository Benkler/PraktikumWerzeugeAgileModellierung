package org.com.autoscaler.clock;



public interface IClockEventPublisher {

    
    public void fireClockEvent(int clockTickCount, double intervalDurationInSeconds) ;

    public void fireTriggerWorkloadHandlerEvent(int clockTickCount, double intervalDurationInSeconds) ;
    
    public void fireTriggerAutoScalerEvent(int clockTickCount, double intervalDurationInSeconds) ;
}
