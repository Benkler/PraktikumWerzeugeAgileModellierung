package org.com.autoscaler.clock;

/**
 *This class holds the information of a clock
 * @author Niko
 *
 */
public class ClockInformation {
    
    // Clock Settings
    private int clockTickCount;
    private double intervalDurationInSeconds;
    private int clockTicksTillWorkloadChange;
    private int clockTicksTillScalingDecision;
    
    public ClockInformation(int clockTickCount, double intervalDurationInSeconds, int clockTickTillWorkloadChange,
            int clockTicksTillScalingDecision) {
        
        this.clockTickCount = clockTickCount;
        this.intervalDurationInSeconds = intervalDurationInSeconds;
        this.clockTicksTillWorkloadChange = clockTickTillWorkloadChange;
        this.clockTicksTillScalingDecision = clockTicksTillScalingDecision;
        
    }

    public int getClockTickCount() {
        return clockTickCount;
    }

    public void setClockTickCount(int clockTickCount) {
        this.clockTickCount = clockTickCount;
    }

    public double getIntervalDurationInSeconds() {
        return intervalDurationInSeconds;
    }

    public int getClockTicksTillWorkloadChange() {
        return clockTicksTillWorkloadChange;
    }

    public int getClockTicksTillScalingDecision() {
        return clockTicksTillScalingDecision;
    }
    
    

}
