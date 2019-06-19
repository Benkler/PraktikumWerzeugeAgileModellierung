package org.com.autoscaler.pojos;

/**
 * Represents Clock info. For JSON deserialization. 
 * @author Niko
 *
 */
public class ClockPOJO {

    private double intervalDurationInMilliSeconds;
    private int clockTicksTillWorkloadChange;
    private int clockTicksTillScalingDecision;
    private int monitoringDelay;
    private int experimentDurationInMinutes;

    public ClockPOJO() {
       super();
    }

  
    public double getIntervalDurationInMilliSeconds() {
        return intervalDurationInMilliSeconds;
    }


    public void setIntervalDurationInMilliSeconds(double intervalDurationInMilliSeconds) {
        this.intervalDurationInMilliSeconds = intervalDurationInMilliSeconds;
    }


    public int getClockTicksTillWorkloadChange() {
        return clockTicksTillWorkloadChange;
    }


    public void setClockTicksTillWorkloadChange(int clockTicksTillWorkloadChange) {
        this.clockTicksTillWorkloadChange = clockTicksTillWorkloadChange;
    }


    public int getClockTicksTillScalingDecision() {
        return clockTicksTillScalingDecision;
    }


    public void setClockTicksTillScalingDecision(int clockTicksTillScalingDecision) {
        this.clockTicksTillScalingDecision = clockTicksTillScalingDecision;
    }


    public int getMonitoringDelay() {
        return monitoringDelay;
    }


    public void setMonitoringDelay(int monitoringDelay) {
        this.monitoringDelay = monitoringDelay;
    }


    public int getExperimentDurationInMinutes() {
        return experimentDurationInMinutes;
    }


    public void setExperimentDurationInMinutes(int experimentDurationInMinutes) {
        this.experimentDurationInMinutes = experimentDurationInMinutes;
    }


    @Override
    public String toString() {
        return " Clock Information: \n intervalDurationInMilliSeconds: " + intervalDurationInMilliSeconds
                + "\n clockTicksTillWorkloadChange: " + clockTicksTillWorkloadChange
                + "\n clockTicksTillScalingDecision: " + clockTicksTillScalingDecision + "\n monitoringDelay: "
                + monitoringDelay + "\n experimentDurationInMinutes:" + experimentDurationInMinutes;
    }

}
