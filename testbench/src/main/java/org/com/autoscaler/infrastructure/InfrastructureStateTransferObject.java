package org.com.autoscaler.infrastructure;

import java.util.List;

/**
 * Class that represents the current state of the entire infrastructure,
 * including the queue state. This class is used to bundle information, wrap it
 * in infrastructure state event and publish so that any intereseted listeners
 * are able to receive current state
 * 
 * @author Niko
 *
 */
public class InfrastructureStateTransferObject {

    /*
     * amount of tasks all vms are able to process per interval
     */
    private int currentCapacityInTasksPerIntervall;

    /*
     * amount of tasks all vms are able to process per second
     */
    private double currentCapacityInTasksPerSecond;

    private int currentArrivalRateInTasksPerIntervall;

    private double currentArrivalRateInTasksPerSecond;
    
    /*
     * Capacity vs. incoming arrival rate + queue fill 
     */
    private double currentCPUUtilization;

  

    private List<VirtualMachine> virtualMachines;

    public List<VirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }

    public void setVirtualMachines(List<VirtualMachine> virtualMachines) {
        this.virtualMachines = virtualMachines;
    }

    public int getCurrentCapacityInTasksPerIntervall() {
        return currentCapacityInTasksPerIntervall;
    }

    public void setCurrentCapacityInTasksPerIntervall(int currentCapacityInTasksPerIntervall) {
        this.currentCapacityInTasksPerIntervall = currentCapacityInTasksPerIntervall;
    }

  

    public int getCurrentArrivalRateInTasksPerIntervall() {
        return currentArrivalRateInTasksPerIntervall;
    }

    public void setCurrentArrivalRateInTasksPerIntervall(int currentArrivalRateInTasksPerIntervall) {
        this.currentArrivalRateInTasksPerIntervall = currentArrivalRateInTasksPerIntervall;
    }

   

    public double getCurrentArrivalRateInTasksPerSecond() {
        return currentArrivalRateInTasksPerSecond;
    }

    public void setCurrentArrivalRateInTasksPerSecond(double currentArrivalRateInTasksPerSecond) {
        this.currentArrivalRateInTasksPerSecond = currentArrivalRateInTasksPerSecond;
    }

    public double getCurrentCapacityInTasksPerSecond() {
        return currentCapacityInTasksPerSecond;
    }

    public void setCurrentCapacityInTasksPerSecond(double currentCapacityInTasksPerSecond) {
        this.currentCapacityInTasksPerSecond = currentCapacityInTasksPerSecond;
    }

    public double getCurrentCPUUtilization() {
        return currentCPUUtilization;
    }

    public void setCurrentCPUUtilization(double currentCPUUtilization) {
        this.currentCPUUtilization = currentCPUUtilization;
    }
    
    

}
