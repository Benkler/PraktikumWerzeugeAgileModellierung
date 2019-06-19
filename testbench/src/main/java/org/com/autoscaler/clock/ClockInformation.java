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
    private final int monitoringDelay;
    private final int experimentDurationInMinutes;

    public ClockInformation(double intervalDurationInMilliSeconds, int clockTickTillWorkloadChange,
            int clockTicksTillScalingDecision, int monitoringDelay, int experimentDurationInMinutes) {

        this.intervalDurationInMilliSeconds = intervalDurationInMilliSeconds;
        this.clockTicksTillWorkloadChange = clockTickTillWorkloadChange;
        this.clockTicksTillScalingDecision = clockTicksTillScalingDecision;
        this.monitoringDelay = monitoringDelay;
        this.experimentDurationInMinutes = experimentDurationInMinutes;
    }

    public int getMonitoringDelay() {
        return monitoringDelay;
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
