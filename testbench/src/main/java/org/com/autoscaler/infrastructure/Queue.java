package org.com.autoscaler.infrastructure;

import org.springframework.stereotype.Component;

public class Queue {

    /*
     * Should be 0 in most use cases
     */
    private int minLength;
    private int maxLength;
    private int currentLevelInTasks;

    public void setQueueParameters(int minLength, int maxLength) {
        if (minLength < 0 || maxLength < 0 || maxLength < minLength) {
            throw new IllegalArgumentException("Invalid Queue parameters");
        }
        this.minLength = minLength;
        this.maxLength = maxLength;

    }

    /**
     * Return the amount of jobs that were enqueued <br>
     * If currentLevel + jobs <= maxLength: Return jobs <br>
     * If currentLevel + jobs > maxLengtht: Return available capacity before
     * enqueuing --> Indicates a discarding of jobs!
     * 
     * @param jobs
     * @return
     */
    public int enqueue(int jobs) {
        int enqueuedJobs = 0;
        if(currentLevelInTasks + jobs <= maxLength) {
            enqueuedJobs = jobs;
            currentLevelInTasks += jobs;
        }else {
            
            enqueuedJobs = maxLength - currentLevelInTasks;
            currentLevelInTasks = maxLength;
        }
        return enqueuedJobs;
    }

    /**
     * Return the amount of jobs that were dequeued. <br>
     * currentLevel - jobs > minLength: Return jobs jobs < minLength: Return
     * currentLevel - minLength
     */
    public int dequeue(int jobs) {
        int dequeuedJobs = 0;

        /*
         * Enough jobs in queue to dequeue desired amount
         */
        if (currentLevelInTasks - jobs >= minLength) {
            dequeuedJobs = jobs;
            currentLevelInTasks -= jobs;

            /*
             * Not enough jobs in queue. Dequeue all available jobs until minimum value is
             * reached. Set current fill to minimum value and return the difference between
             * previously available jobs and min value
             */
        } else {
            dequeuedJobs = currentLevelInTasks - minLength;
            currentLevelInTasks = minLength;
        }

        return dequeuedJobs;

    }

    public boolean isFull() {

        if (currentLevelInTasks > maxLength) {
            throw new IllegalStateException("Queue overflow.");
        }

        return currentLevelInTasks == maxLength;
    }

    public boolean isEmpty() {
        if (currentLevelInTasks < minLength) {
            throw new IllegalStateException("Queue underflow");
        }

        return currentLevelInTasks == minLength;
    }

    public int currentLevelInTasks() {
        return currentLevelInTasks;
    }

    public long currentLevelInPercent() {

        return currentLevelInTasks / maxLength;
    }

}
