package org.com.autoscaler.queue;

import org.springframework.stereotype.Component;

/**
 * Class that represents queue infrastructure.
 * @author Niko
 *
 */
@Component
public class Queue implements IQueue {

    /*
     * Should be 0 in most use cases
     */
    private int minLength;
    private int maxLength;
    private int currentLevelInTasks;
    
    private boolean init = false;

    @Override
    public void initQueue(int minLength, int maxLength) {
        if (init) return;
        
        if (minLength < 0 || maxLength < 0 || maxLength < minLength) {
            throw new IllegalArgumentException("Invalid Queue parameters");
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.currentLevelInTasks = 0;

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
    @Override
    public int enqueue(int jobs) {
        //TODO  publish discard event!
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
    @Override
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

    @Override
    public boolean isFull() {

        if (currentLevelInTasks > maxLength) {
            throw new IllegalStateException("Queue overflow.");
        }

        return currentLevelInTasks == maxLength;
    }

    @Override
    public boolean isEmpty() {
        if (currentLevelInTasks < minLength) {
            throw new IllegalStateException("Queue underflow");
        }

        return currentLevelInTasks == minLength;
    }

    @Override
    public int currentLevelInTasks() {
        return currentLevelInTasks;
    }

    @Override
    public long currentLevelInPercent() {

        return (currentLevelInTasks / maxLength)*100;
    }

}
