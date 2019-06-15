package org.com.autoscaler.pojos;

public class AutoscalerPOJO {

    private int monitoringDelay;
    private int vmStartUpTime;

    public int getMonitoringDelay() {
        return monitoringDelay;
    }

    public void setMonitoringDelay(int monitoringDelay) {
        this.monitoringDelay = monitoringDelay;
    }

    public int getVmStartUpTime() {
        return vmStartUpTime;
    }

    public void setVmStartUpTime(int vmStartUpTime) {
        this.vmStartUpTime = vmStartUpTime;
    }

    public AutoscalerPOJO() {
        super();
    }

    @Override
    public String toString() {

        String output = "Autoscaler Information: \n monitoring delay: " + monitoringDelay + "\n VM startupTime: "
                + vmStartUpTime;
        return output;
    }

}
