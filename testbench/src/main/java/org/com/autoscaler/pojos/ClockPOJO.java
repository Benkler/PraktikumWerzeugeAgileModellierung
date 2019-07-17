package org.com.autoscaler.pojos;

/**
 * Represents Clock info. For JSON deserialization. 
 * @author Niko
 *
 */
public class ClockPOJO {

    private double intervalDurationInMilliSeconds;
    private int millisecondsTillWorkloadChange;

    private int millisecondsTillPublishInfrastructureState;
    
    private int millisecondsTillPublishQueueState;
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




    public int getMillisecondsTillWorkloadChange() {
        return millisecondsTillWorkloadChange;
    }


    public void setMillisecondsTillWorkloadChange(int millisecondsTillWorkloadChange) {
        this.millisecondsTillWorkloadChange = millisecondsTillWorkloadChange;
    }


    public int getMillisecondsTillPublishInfrastructureState() {
        return millisecondsTillPublishInfrastructureState;
    }


    public void setMillisecondsTillPublishInfrastructureState(int millisecondsTillPublishInfrastructureState) {
        this.millisecondsTillPublishInfrastructureState = millisecondsTillPublishInfrastructureState;
    }


    public int getMillisecondsTillPublishQueueState() {
        return millisecondsTillPublishQueueState;
    }


    public void setMillisecondsTillPublishQueueState(int millisecondsTillPublishQueueState) {
        this.millisecondsTillPublishQueueState = millisecondsTillPublishQueueState;
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
                + "\n clockTicksTillWorkloadChange: " + millisecondsTillWorkloadChange
                +  "\n clockTicksTillPublishInfrastructureState;: "
                + millisecondsTillPublishInfrastructureState + "\n clockTickTillPublishQueueState: " + millisecondsTillPublishQueueState+ "\n experimentDurationInMinutes:" + experimentDurationInMinutes;
    }

}
