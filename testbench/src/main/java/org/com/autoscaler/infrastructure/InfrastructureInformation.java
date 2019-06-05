package org.com.autoscaler.infrastructure;

import java.util.List;

import org.springframework.util.Assert;

/**
 * Class to bundle Infrastructure Information
 * 
 * @author Niko
 *
 */
public class InfrastructureInformation {

    private final int minAmountVM;
    private final int maxAmountVM;
    private List<VirtualMachine> virtualMachines;

    private final int maxQueueLength;
    private final int minQueueLength;

    /*
     * Represents the amount of tasks ALL VMs are able to process during a clock
     * intervall
     */
    private int tasksPerClockInterval;

    /*
     * Represents the amount of tasks ALL VMs are able to process per second
     */
    private int tasksPerSecond;

    public InfrastructureInformation(int minAmountVM, int maxAmountVM, List<VirtualMachine> virtualMachines,
            int maxQueueLength, int minQueueLength) {

        if (minAmountVM < 0 || maxAmountVM < 0 || maxAmountVM < minAmountVM) {
            throw new IllegalArgumentException("Invalid parameters for minimum and maximum amount of virtual machines");
        }
        
        if(minQueueLength <0 || maxQueueLength < 0|| maxQueueLength < minQueueLength) {
            throw new IllegalArgumentException("Invalid parameter for minimum and maximum queue length");
        }

        this.minAmountVM = minAmountVM;
        this.maxAmountVM = maxAmountVM;
        this.minQueueLength = minQueueLength;
        this.maxQueueLength = maxQueueLength;
        setVirtualMachines(virtualMachines);
    }

    public List<VirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }

    public void setVirtualMachines(List<VirtualMachine> virtualMachines) {
        if (virtualMachines == null) {
            throw new IllegalArgumentException();
        }
        this.virtualMachines = virtualMachines;
        calculateCurrentCapacity();
    }

    public int getMinAmountVM() {
        return minAmountVM;
    }

    public int getMaxAmountVM() {
        return maxAmountVM;
    }

    public int getTasksPerClockInterval() {
        return tasksPerClockInterval;
    }

    public int getTasksPerSecond() {
        return tasksPerSecond;
    }
    
    

    public int getMaxQueueLength() {
        return maxQueueLength;
    }

    public int getMinQueueLength() {
        return minQueueLength;
    }

    /*
     * Recalculate the current capacity
     */
    private void calculateCurrentCapacity() {
        tasksPerClockInterval = 0;
        tasksPerSecond = 0;

        for (VirtualMachine vm : virtualMachines) {
            tasksPerClockInterval += vm.getTasksPerClockInterval();
            tasksPerSecond += vm.getTasksPerSecond();
        }
    }

}
