package org.com.autoscaler.queue;

import org.com.autoscaler.util.MovingAverage;

public class QueueStateTransferObject {
    
    
    private int tasksInQueue;
    private double queueFillInPercent;
    private double queueArrivalRateInTasksPerInterval;
    private double queueProcessingRateInTasksPerInterval;
    
    
    
    
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

    public double getQueueProcessingRateInTasksPerInterval() {
        return queueProcessingRateInTasksPerInterval;
    }

    public void setQueueProcessingRateInTasksPerInterval(double queueProcessingRateInTasksPerInterval) {
        this.queueProcessingRateInTasksPerInterval = queueProcessingRateInTasksPerInterval;
    }

    public double getQueueArrivalRateInTasksPerInterval() {
        return queueArrivalRateInTasksPerInterval;
    }

    public void setQueueArrivalRateInTasksPerInterval(double queueArrivalRateInTasksPerInterval) {
        this.queueArrivalRateInTasksPerInterval = queueArrivalRateInTasksPerInterval;
    }
    
    @Override
    public String toString() {
      
        
        StringBuilder sb = new StringBuilder();
        sb.append("tasksInQueue: ");
        sb.append(tasksInQueue);
        sb.append("\nqueueFillInPercent: ");
        sb.append(queueFillInPercent);
        sb.append("\nqueueArrivalRateInTasksPerInterval: ");
        sb.append(queueArrivalRateInTasksPerInterval);
        sb.append("\nqueueProcessingRateInTasksPerInterval: ");
        sb.append(queueProcessingRateInTasksPerInterval);
        return sb.toString();
        
    }

}
