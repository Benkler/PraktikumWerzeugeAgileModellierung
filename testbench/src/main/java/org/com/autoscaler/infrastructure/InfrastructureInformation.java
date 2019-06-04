package org.com.autoscaler.infrastructure;

import java.util.List;

import org.springframework.util.Assert;

/**
 * Class to bundle Insfrastructure Information
 * 
 * @author Niko
 *
 */
public class InfrastructureInformation {

    private final int minAmountVM;
    private final int maxAmountVM;
    private List<VirtualMachine> virtualMachines;

    /*
     * Represents the amount of tasks ALL VMs are able to process during a clock
     * intervall
     */
    private int tasksPerClockInterval;

    /*
     * Represents the amount of tasks ALL VMs are able to process per second
     */
    private int tasksPerSecond;

    public InfrastructureInformation(int minAmountVM, int maxAmountVM, List<VirtualMachine> virtualMachines) {

        if (minAmountVM < 0 || maxAmountVM < 0 || maxAmountVM < minAmountVM) {
            throw new IllegalArgumentException();
        }

        this.minAmountVM = minAmountVM;
        this.maxAmountVM = maxAmountVM;
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
