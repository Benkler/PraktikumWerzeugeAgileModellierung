package org.com.autoscaler.pojos;

import java.util.List;

/**
 * Represents infrastructure info. For JSON deserialization. 
 * @author Niko
 *
 */
public class InfrastructurePOJO {

    private VirtualMachineTypePOJO virtualMachineType;

 
    
    private int cpuUitilizationWindow;
    
    private int amountOfVmsAtSimulationStart;

   
   
   

   


   

    public InfrastructurePOJO() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String output = "Infrastructure: \n Virtual Machines: \n";
        sb.append(output);
        sb.append(virtualMachineType.toString());
        sb.append("\nAmount of virtual machines at start: " + amountOfVmsAtSimulationStart);
        sb.append("\nCpu utilization window Size:" + cpuUitilizationWindow);
        return sb.toString();
    }

    public int getCpuUitilizationWindow() {
        return cpuUitilizationWindow;
    }

    public void setCpuUitilizationWindow(int cpuUitilizationWindow) {
        this.cpuUitilizationWindow = cpuUitilizationWindow;
    }

    public VirtualMachineTypePOJO getVirtualMachineType() {
        return virtualMachineType;
    }

    public void setVirtualMachineType(VirtualMachineTypePOJO virtualMachineType) {
        this.virtualMachineType = virtualMachineType;
    }

    public int getAmountOfVmsAtSimulationStart() {
        return amountOfVmsAtSimulationStart;
    }

    public void setAmountOfVmsAtSimulationStart(int amountOfVmsAtSimulationStart) {
        this.amountOfVmsAtSimulationStart = amountOfVmsAtSimulationStart;
    }

}
