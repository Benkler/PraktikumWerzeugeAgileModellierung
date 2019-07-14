package org.com.autoscaler.pojos;

import java.util.List;

/**
 * Represents infrastructure info. For JSON deserialization. 
 * @author Niko
 *
 */
public class InfrastructurePOJO {

    private List<VirtualMachinePOJO> virtualMachines;

    private int vmMax;

    private int vmMin;
    
    private int cpuUitilizationWindow;

   
   
    
    private double intervalDurationInMilliSeconds;

    public double getIntervalDurationInMilliSeconds() {
        return intervalDurationInMilliSeconds;
    }

    public void setIntervalDurationInMilliSeconds(double intervalDurationInMilliSeconds) {
        this.intervalDurationInMilliSeconds = intervalDurationInMilliSeconds;
    }

    public List<VirtualMachinePOJO> getVirtualMachines() {
        return virtualMachines;
    }



    public void setVirtualMachines(List<VirtualMachinePOJO> virtualMachines) {
        this.virtualMachines = virtualMachines;
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

   

    public InfrastructurePOJO() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String output = "Infrastructure: \n vmMax: " + vmMax + " \n vmMin: " + vmMin
                 + "\n Virtual Machines: \n";
        sb.append(output);
        for (VirtualMachinePOJO vm : virtualMachines) {
            sb.append(vm.toString());
        }
        
        sb.append("\nCpu utilization window Size:" + cpuUitilizationWindow);
        return sb.toString();
    }

    public int getCpuUitilizationWindow() {
        return cpuUitilizationWindow;
    }

    public void setCpuUitilizationWindow(int cpuUitilizationWindow) {
        this.cpuUitilizationWindow = cpuUitilizationWindow;
    }

}
