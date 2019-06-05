package org.com.autoscaler.infrastructure;

/**
 * Class that represents the current state of the entire infrastructure,
 * including the queue state
 * 
 * @author Niko
 *
 */
public class InfrastructureState {

    private int currentArrivalRateInTasksPerIntervall;

    /*
     * arrival rate and queue fill together!
     */
    private int currentCapacityInTasksPerIntervall;
    private int desiredCapacityInTasksPerIntervall;

    private int tasksInQueue;
    private double queueFillInPercent;

    private int amountOfVMs;

    public InfrastructureState() {

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

    public int getAmountOfVMs() {
        return amountOfVMs;
    }

    public void setAmountOfVMs(int amountOfVMs) {
        this.amountOfVMs = amountOfVMs;
    }

}
