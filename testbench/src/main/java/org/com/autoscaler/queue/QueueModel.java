package org.com.autoscaler.queue;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.TriggerPublishQueueStateEvent;
import org.com.autoscaler.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that represents queue infrastructure.
 * 
 * @author Niko
 *
 */
@Component
public class QueueModel implements IQueueModel {

   
    private int maxLength;
    private int currentLevelInTasks;

    private boolean init = false;

    @Autowired
    IQueueEventPublisher publisher;

    @Override
    public void initQueue(int maxLength) {
        if (init)
            return;

        if ( maxLength < 0 ) {
            throw new IllegalArgumentException("Invalid Queue parameters");
        }
        
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
    public int enqueue(int jobs, ClockEvent clockEvent) {

        /*
         * Jobs that will be enqueued
         */
        int enqueuedJobs = 0;

        // Enough space in queue available
        if (currentLevelInTasks + jobs <= maxLength) {
            enqueuedJobs = jobs;
            currentLevelInTasks += jobs;

            // Need to discard Jobs
        } else {

            enqueuedJobs = maxLength - currentLevelInTasks;
            currentLevelInTasks = maxLength;

            int discardedJobs = jobs - enqueuedJobs;
            publisher.fireQueueDiscardJobsEvent(clockEvent, discardedJobs);
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
        if (currentLevelInTasks - jobs >= 0) {
            dequeuedJobs = jobs;
            currentLevelInTasks -= jobs;

            /*
             * Not enough jobs in queue. Dequeue all available jobs until minimum value is
             * reached. Set current fill to minimum value and return the difference between
             * previously available jobs and min value
             */
        } else {
            dequeuedJobs = currentLevelInTasks;
            currentLevelInTasks = 0;
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
        if (currentLevelInTasks < 0 ) {
            throw new IllegalStateException("Queue underflow");
        }

        return currentLevelInTasks == 0;
    }

    @Override
    public int currentLevelInTasks() {
        return currentLevelInTasks;
    }

    @Override
    public double currentLevelInPercent() {
        return MathUtil.round(((double) currentLevelInTasks / (double) maxLength) * 100.00, 2);

    }

    @Override
    public QueueStateTransferObject getQueueState() {
        QueueStateTransferObject state = new QueueStateTransferObject();
        state.setQueueFillInPercent(currentLevelInPercent());
        state.setTasksInQueue(currentLevelInTasks());
        
        
        return state;
    }

    @Override
    public void publishQueueState(TriggerPublishQueueStateEvent event) {
        publisher.fireQueueStateEvent(event.getClockTickCount(), event.getIntervallDuratioInMilliSeconds(), getQueueState());
        
    }

}
