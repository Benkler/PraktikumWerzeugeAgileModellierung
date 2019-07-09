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
    private int clockTicksTillPublishInfrastructureState;
    private int clockTicksTillPublishQueueState;
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


  


    public int getClockTicksTillPublishInfrastructureState() {
        return clockTicksTillPublishInfrastructureState;
    }


    public void setClockTicksTillPublishInfrastructureState(int clockTicksTillPublishInfrastructureState) {
        this.clockTicksTillPublishInfrastructureState = clockTicksTillPublishInfrastructureState;
    }


    public int getClockTicksTillPublishQueueState() {
        return clockTicksTillPublishQueueState;
    }


    public void setClockTicksTillPublishQueueState(int clockTicksTillPublishQueueState) {
        this.clockTicksTillPublishQueueState = clockTicksTillPublishQueueState;
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
                + "\n clockTicksTillScalingDecision: " + clockTicksTillScalingDecision + "\n clockTicksTillPublishInfrastructureState;: "
                + clockTicksTillPublishInfrastructureState + "\n clockTickTillPublishQueueState: " + clockTicksTillPublishQueueState+ "\n experimentDurationInMinutes:" + experimentDurationInMinutes;
    }

}
