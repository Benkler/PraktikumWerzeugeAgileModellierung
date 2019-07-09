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
     * amount of tasks the all vms are able to process per interval
     */
    private int currentCapacityInTasksPerIntervall;

    /*
     * amount of tasks the all vms are able to process per second
     */
    private int currentCapacityInTasksPerSecond;

    private int currentArrivalRateInTasksPerIntervall;

    private int currentArrivalRateInTasksPerSecond;

  

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

   

    public int getCurrentArrivalRateInTasksPerSecond() {
        return currentArrivalRateInTasksPerSecond;
    }

    public void setCurrentArrivalRateInTasksPerSecond(int currentArrivalRateInTasksPerSecond) {
        this.currentArrivalRateInTasksPerSecond = currentArrivalRateInTasksPerSecond;
    }

    public int getCurrentCapacityInTasksPerSecond() {
        return currentCapacityInTasksPerSecond;
    }

    public void setCurrentCapacityInTasksPerSecond(int currentCapacityInTasksPerSecond) {
        this.currentCapacityInTasksPerSecond = currentCapacityInTasksPerSecond;
    }

}
