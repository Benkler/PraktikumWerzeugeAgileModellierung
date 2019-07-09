package org.com.autoscaler.queue;

public class QueueStateTransferObject {
    
    
    private int tasksInQueue;
    private double queueFillInPercent;
    
    
    
    
    public double getQueueFillInPercent() {
        return queueFillInPercent;
    }

    public void setQueueFillInPercent(double queueFillInPercent) {
        this.queueFillInPercent = queueFillInPercent;
    }
    
    
    
    public int getTasksInQueue() {
        return tasksInQueue;
    }

    public void setTasksInQueue(int tasksInQueue) {
        this.tasksInQueue = tasksInQueue;
    }

}
