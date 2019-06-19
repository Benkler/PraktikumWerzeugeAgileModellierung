package org.com.autoscaler.infrastructure;

import java.util.List;

/**
 * Class that represents the current state of the entire infrastructure,
 * including the queue state
 * 
 * @author Niko
 *
 */
public class InfrastructureStateTransferObject {

    private int currentArrivalRateInTasksPerIntervall;

    /*
     * arrival rate and queue fill together!
     */
    private int currentCapacityInTasksPerIntervall;
    private int desiredCapacityInTasksPerIntervall;
    
    private int currentArrivalRateInTasksPerSecond;

    /*
     * arrival rate and queue fill together!
     */
    private int currentCapacityInTasksPerSecond;
    private int desiredCapacityInTasksPerSecond;

    private int tasksInQueue;
    private double queueFillInPercent;

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

    public int getDesiredCapacityInTasksPerIntervall() {
        return desiredCapacityInTasksPerIntervall;
    }

    public void setDesiredCapacityInTasksPerIntervall(int desiredCapacityInTasksPerIntervall) {
        this.desiredCapacityInTasksPerIntervall = desiredCapacityInTasksPerIntervall;
    }

    public double getQueueFillInPercent() {
        return queueFillInPercent;
    }

    public void setQueueFillInPercent(double queueFillInPercent) {
        this.queueFillInPercent = queueFillInPercent;
    }

    public int getCurrentArrivalRateInTasksPerIntervall() {
        return currentArrivalRateInTasksPerIntervall;
    }

    public void setCurrentArrivalRateInTasksPerIntervall(int currentArrivalRateInTasksPerIntervall) {
        this.currentArrivalRateInTasksPerIntervall = currentArrivalRateInTasksPerIntervall;
    }

    public int getTasksInQueue() {
        return tasksInQueue;
    }

    public void setTasksInQueue(int tasksInQueue) {
        this.tasksInQueue = tasksInQueue;
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
