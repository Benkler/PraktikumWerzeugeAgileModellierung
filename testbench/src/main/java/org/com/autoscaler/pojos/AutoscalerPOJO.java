package org.com.autoscaler.pojos;

/**
 * Represents AutoScaler info. For JSON deserialization.
 * 
 * @author Niko
 *
 */
public class AutoscalerPOJO {

    private double lowerThreshold;
    private double upperThreshold;
    
    private int vmMax;
    private int vmMin;
   
    private int cpuUtilWindow;
    private int queueLengthWindow;
    private int coolDownTimeInMilliSeconds;
    private int timeInMsTillNextScalingDecision;
    
    
    public AutoscalerPOJO() {
        super();
    }

    public int getCoolDownTimeInMilliSeconds() {
        return coolDownTimeInMilliSeconds;
    }

    public void setCoolDownTimeInMilliSeconds(int coolDownTimeInMilliSeconds) {
        this.coolDownTimeInMilliSeconds = coolDownTimeInMilliSeconds;
    }

    @Override
    public String toString() {

        String output = "Autoscaler Information: \n lower Threshold: " + lowerThreshold + "\n upper Treshhold: "
                + upperThreshold +  "\n vmMax: " + vmMax
                + "\n vmMin: " + vmMin + "\n cpuUtilWindow: " + cpuUtilWindow
                + "\n queueLengthWindow: " + queueLengthWindow + "\n coolDownTime in ms: " + coolDownTimeInMilliSeconds + "\n clockTickTillScalingDecision: " + timeInMsTillNextScalingDecision;
        return output;
    }

   
    public double getLowerThreshold() {
        return lowerThreshold;
    }

    public void setLowerThreshold(double lowerThreshold) {
        this.lowerThreshold = lowerThreshold;
    }

   

    
    public int getTimeInMsTillNextScalingDecision() {
        return timeInMsTillNextScalingDecision;
    }

    public void setTimeInMsTillNextScalingDecision(int timeInMsTillNextScalingDecision) {
        this.timeInMsTillNextScalingDecision = timeInMsTillNextScalingDecision;
    }

    public double getUpperThreshold() {
        return upperThreshold;
    }

    public void setUpperThreshold(double upperThreshold) {
        this.upperThreshold = upperThreshold;
    }

   

    public int getVmMax() {
        return vmMax;
    }

    public void setVmMax(int vmMax) {
        this.vmMax = vmMax;
    }

    public int getVmMin() {
        return vmMin;
    }

    public void setVmMin(int vmMin) {
        this.vmMin = vmMin;
    }

    public int getCpuUtilWindow() {
        return cpuUtilWindow;
    }

    public void setCpuUtilWindow(int cpuUtilWindow) {
        this.cpuUtilWindow = cpuUtilWindow;
    }

    public int getQueueLengthWindow() {
        return queueLengthWindow;
    }

    public void setQueueLengthWindow(int queueLengthWindow) {
        this.queueLengthWindow = queueLengthWindow;
    }

   
    

}
