package org.com.autoscaler.pojos;

public class AutoscalerPOJO {

    private double lowerThreshold;
    private double upperThreshold;
    private int vmTasksPerIntervall;
    private int vmMax;
    private int vmMin;



    public AutoscalerPOJO() {
        super();
    }

    @Override
    public String toString() {

        String output = "Autoscaler Information: \n lower Threshold: " + lowerThreshold + "\n upper Treshhold: "
                + upperThreshold + "\n vmTasksPerIntervall: " + vmTasksPerIntervall + "\n vmMax: " + vmMax + "\n vmMin: " + vmMin;
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

}
