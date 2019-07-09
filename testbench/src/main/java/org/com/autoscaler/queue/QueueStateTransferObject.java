package org.com.autoscaler.queue;

import org.com.autoscaler.util.MovingAverage;

public class QueueStateTransferObject {
    
    
    private int tasksInQueue;
    private double queueFillInPercent;
    private double queueArrivalRateInTasksPerInterval;
    private double queueProcessingRateInTasksPerInterval;
    private double queueingDelayInIntervals;
    
    
    
    
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
    
    public double getQueueingDelayInIntervals() {
        return queueingDelayInIntervals;
    }

    public void setQueueingDelayInIntervals(double queueingDelayInIntervals) {
        this.queueingDelayInIntervals = queueingDelayInIntervals;
    }

    @Override
    public String toString() {
    
        
        StringBuilder sb = new StringBuilder();
        sb.append("tasksInQueue: ");
        sb.append(tasksInQueue);
        sb.append("\n queueFillInPercent: ");
        sb.append(queueFillInPercent);
        sb.append("\n queueArrivalRateInTasksPerInterval: ");
        sb.append(queueArrivalRateInTasksPerInterval);
        sb.append("\n queueProcessingRateInTasksPerInterval: ");
        sb.append(queueProcessingRateInTasksPerInterval);
        sb.append("\n QueueingDelayInIntervals: ");
        sb.append(queueingDelayInIntervals);
        return sb.toString();
        
    }

}
