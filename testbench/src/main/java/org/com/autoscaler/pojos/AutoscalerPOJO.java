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
    private int vmTasksPerIntervall;
    private int vmMax;
    private int vmMin;
    private int vmStartUpTime;
    private int cpuUtilWindow;
    private int queueLengthWindow;
    private int coolDownTime;
    private int clockTicksTillScalingDecision;

    public AutoscalerPOJO() {
        super();
    }

    @Override
    public String toString() {

        String output = "Autoscaler Information: \n lower Threshold: " + lowerThreshold + "\n upper Treshhold: "
                + upperThreshold + "\n vmTasksPerIntervall: " + vmTasksPerIntervall + "\n vmMax: " + vmMax
                + "\n vmMin: " + vmMin + "\n vm Startuptime: " + vmStartUpTime + "\n cpuUtilWindow: " + cpuUtilWindow
                + "\n queueLengthWindow: " + queueLengthWindow + "\n coolDownTime: " + coolDownTime + "\n clockTickTillScalingDecision: " + clockTicksTillScalingDecision;
        return output;
    }

    public int getVmTasksPerIntervall() {
        return vmTasksPerIntervall;
    }

    public void setVmTasksPerIntervall(int vmTasksPerIntervall) {
        this.vmTasksPerIntervall = vmTasksPerIntervall;
    }

    public double getLowerThreshold() {
        return lowerThreshold;
    }

    public void setLowerThreshold(double lowerThreshold) {
        this.lowerThreshold = lowerThreshold;
    }

    public int getClockTicksTillScalingDecision() {
        return clockTicksTillScalingDecision;
    }


    public void setClockTicksTillScalingDecision(int clockTicksTillScalingDecision) {
        this.clockTicksTillScalingDecision = clockTicksTillScalingDecision;
    }

    
    public double getUpperThreshold() {
        return upperThreshold;
    }

    public void setUpperThreshold(double upperThreshold) {
        this.upperThreshold = upperThreshold;
    }

    public int getVmStartUpTime() {
        return vmStartUpTime;
    }

    public void setVmStartUpTime(int vmStartUpTime) {
        this.vmStartUpTime = vmStartUpTime;
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

    public int getCoolDownTime() {
        return coolDownTime;
    }

    public void setCoolDownTime(int coolDownTime) {
        this.coolDownTime = coolDownTime;
    }
    
    

}
