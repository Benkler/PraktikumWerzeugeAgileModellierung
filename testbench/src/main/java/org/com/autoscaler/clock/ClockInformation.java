package org.com.autoscaler.clock;

/**
 * This class holds the information of a clock that is used to initialize the
 * clock
 * 
 * @author Niko
 *
 */
public class ClockInformation {

    // Clock Settings
    private final double intervalDurationInMilliSeconds;
    private final int clockTicksTillWorkloadChange;
    private final int clockTicksTillScalingDecision;
    private final int clockTicksTillPublishInfrastructureState;
    private final int clockTicksTillPublishQueueState;
    
    private final int experimentDurationInMinutes;

    public ClockInformation(double intervalDurationInMilliSeconds, int clockTickTillWorkloadChange,
            int clockTicksTillScalingDecision, int clockTicksTillPublishInfrastructureState,    int clockTicksTillPublishQueueState, int experimentDurationInMinutes) {

        this.intervalDurationInMilliSeconds = intervalDurationInMilliSeconds;
        this.clockTicksTillWorkloadChange = clockTickTillWorkloadChange;
        this.clockTicksTillScalingDecision = clockTicksTillScalingDecision;
        this.clockTicksTillPublishInfrastructureState = clockTicksTillPublishInfrastructureState;
        this.clockTicksTillPublishQueueState = clockTicksTillPublishQueueState;
        this.experimentDurationInMinutes = experimentDurationInMinutes;
    }

    

    public int getClockTicksTillPublishInfrastructureState() {
        return clockTicksTillPublishInfrastructureState;
    }



    public int getClockTicksTillPublishQueueState() {
        return clockTicksTillPublishQueueState;
    }



    public double getIntervalDurationInMilliSeconds() {
        return intervalDurationInMilliSeconds;
    }

    public int getClockTicksTillWorkloadChange() {
        return clockTicksTillWorkloadChange;
    }

    public int getClockTicksTillScalingDecision() {
        return clockTicksTillScalingDecision;
    }

    public int getExperimentDurationInMinutes() {
        return experimentDurationInMinutes;
    }

}
