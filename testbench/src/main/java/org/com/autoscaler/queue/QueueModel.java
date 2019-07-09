package org.com.autoscaler.queue;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.TriggerPublishQueueStateEvent;
import org.com.autoscaler.util.MathUtil;
import org.com.autoscaler.util.MovingAverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private MovingAverage<Integer> queueArrivalRateInTasksPerInterval;
    private MovingAverage<Integer> queueProcessingRateInTasksPerInterval;
    private int windowSize ;
    
    private boolean init = false;
    
    Logger log = LoggerFactory.getLogger(QueueModel.class);

    @Autowired
    IQueueEventPublisher publisher;

    @Override
    public void initQueue(int maxLength, int windowSize) {
        if (init)
            return;

        if ( maxLength < 0 ) {
            throw new IllegalArgumentException("Invalid Queue parameters");
        }
        
        this.maxLength = maxLength;
        this.currentLevelInTasks = 0;
        this.queueArrivalRateInTasksPerInterval = new MovingAverage<Integer>(windowSize);
        this.queueProcessingRateInTasksPerInterval = new MovingAverage<Integer>(windowSize);
        this.windowSize = windowSize;
        
        log.info("Queue initialized: " + toString());

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
         * Arrival Rate includes discarded Jobs
         */
       this.queueArrivalRateInTasksPerInterval.add(jobs);
       //Update processing rate
       this.queueProcessingRateInTasksPerInterval.add(0);
        
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
        
        /*
         * Processing rate only includes actually dequeued jobs
         */
        this.queueProcessingRateInTasksPerInterval.add(dequeuedJobs);
        //update moving average for arrival rate
        this.queueArrivalRateInTasksPerInterval.add(0);
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
        state.setQueueProcessingRateInTasksPerInterval(queueProcessingRateInTasksPerInterval.average());
        state.setQueueArrivalRateInTasksPerInterval(queueArrivalRateInTasksPerInterval.average());
        
        
        return state;
    }

    @Override
    public void publishQueueState(TriggerPublishQueueStateEvent event) {
        publisher.fireQueueStateEvent(event.getClockTickCount(), event.getIntervallDuratioInMilliSeconds(), getQueueState());
        
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("maxLegth: ");
        sb.append(maxLength);
        sb.append("\ncurrentLevelInTasks:");
        sb.append(currentLevelInTasks);
        sb.append("\nwindowSize: ");
        sb.append(windowSize);
        
        return sb.toString();
    }

}
