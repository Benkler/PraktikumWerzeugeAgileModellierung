package org.com.autoscaler.clock;

public interface Clock {
    
    public void startClock();
    public void stopClock();
    public void pauseClock();
    public void initClock(double intervalDurationInseconds, int clockTickTillWorkloadChange, int clockTicksTillScalingDecision);

}
