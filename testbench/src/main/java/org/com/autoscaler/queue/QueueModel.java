package org.com.autoscaler.queue;

import java.util.LinkedList;
import java.util.List;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.TriggerPublishQueueStateEvent;
import org.com.autoscaler.util.MathUtil;
import org.com.autoscaler.util.MovingAverage;
import org.com.autoscaler.util.Pair;
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

    /*
     * Represents the current fill level in tasks. This information could also be
     * retrieved from taskBatches, but store separately for efficiency reason
     */
    private int currentQueueLevelInTasks;

    /*
     * Represents a FIFO list of pair where the first entry corresponds to the clock
     * tick when tasks arrive, and the second entry represents the amount of tasks.
     * It is used to calculate the queuing delay
     */
    private LinkedList<Pair<Integer, Integer>> taskBatches;

    private MovingAverage<Integer> queueArrivalRateInTasksPerInterval;
    private MovingAverage<Integer> queueProcessingRateInTasksPerInterval;
    private MovingAverage<Double> queueingDelayInIntervals;

    private boolean init = false;

    Logger log = LoggerFactory.getLogger(QueueModel.class);

    @Autowired
    IQueueEventPublisher publisher;

    @Override
    public void initQueue(int maxLength, int windowSize) {
        if (init)
            return;

        if (maxLength < 0) {
            throw new IllegalArgumentException("Invalid Queue parameters");
        }

        this.maxLength = maxLength;
        this.currentQueueLevelInTasks = 0;
        this.queueArrivalRateInTasksPerInterval = new MovingAverage<Integer>(windowSize);
        this.queueProcessingRateInTasksPerInterval = new MovingAverage<Integer>(windowSize);
        this.queueingDelayInIntervals = new MovingAverage<Double>(windowSize);
        this.taskBatches = new LinkedList<Pair<Integer, Integer>>();

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

        // arrival rate is independent from actually enqueued jobs
        updateArrivalRate(jobs);

        // Jobs that will be enqueued
        int enqueuedJobs = 0;

        // Enough space in queue available
        if (currentQueueLevelInTasks + jobs <= maxLength) {
            enqueuedJobs = jobs;
            currentQueueLevelInTasks += jobs;

            // Need to discard Jobs
        } else {

            enqueuedJobs = maxLength - currentQueueLevelInTasks;
            currentQueueLevelInTasks = maxLength;

            int discardedJobs = jobs - enqueuedJobs;
            publisher.fireQueueDiscardJobsEvent(clockEvent, discardedJobs);
        }

        enqueueBatch(clockEvent.getClockTickCount(), enqueuedJobs);

        int temp = 0;

        for (Pair<Integer, Integer> batch : taskBatches) {
            temp += batch.getR();
        }

        if (temp != currentQueueLevelInTasks) {
            throw new IllegalArgumentException(
                    "Enqueue     " + "Temp: " + temp + "   current: " + currentQueueLevelInTasks);
        }

        return enqueuedJobs;
    }

    /**
     * Return the amount of jobs that were dequeued. <br>
     * currentLevel - jobs > minLength: Return jobs jobs < minLength: Return
     * currentLevel - minLength
     */
    @Override
    public int dequeue(int jobs, ClockEvent event) {
        
        int dequeuedJobs = 0;

        /*
         * Enough jobs in queue to dequeue desired amount
         */
        if (currentQueueLevelInTasks - jobs >= 0) {
            dequeuedJobs = jobs;
            currentQueueLevelInTasks -= jobs;

            /*
             * Not enough jobs in queue. Dequeue all available jobs until minimum value(=0)
             * is reached. Set current fill to minimum value(=0) and return the difference
             * between previously available jobs and min value(=0)
             */
        } else {
            dequeuedJobs = currentQueueLevelInTasks;
            currentQueueLevelInTasks = 0;
        }
       
        updateProcessingRate(dequeuedJobs);
        dequeueBatches(event.getClockTickCount(), dequeuedJobs);

       

        return dequeuedJobs;

    }

    /*
     * Enqueue batch of jobs at a certain clock tick to calculate queuing Delay
     */
    private void enqueueBatch(int clockTick, int amountOfJobs) {

        /*
         * Don't need to enqueue if all jobs were discarded due to full queue
         */
        if (amountOfJobs == 0) {
            // updateQueueingDelay(0.0);
            return;
        }
        taskBatches.addLast(new Pair<Integer, Integer>(clockTick, amountOfJobs));
    }

    /*
     * Dequeue amount of jobs from batch FIFO list. This might include to remove
     * several batches from FIFO list (in case the amount of jobs exceed the first
     * batch to remove) OR to update the next batch (in case the amount of jobs to
     * remove does not exceed the amount of jobs in the current batch)
     */
    private void dequeueBatches(int currentClockTick, int amountOfJobsToDequeue) {

        // Nothing to dequeue --> Queuing Delay is 0
        if (taskBatches.isEmpty()) {
            updateQueueingDelay(0);
            return;
        }

        int dequeuedJobsTemp = 0;

        // Necessary as more that one batch might be removed --> Average necessary
        int amountOfRemovedBatches = 0;
        int sumOfQueueingDelays = 0;

        /*
         * Remove as many batches as required. In case a batch is only partly removed,
         * just update first FIFO list element (reduce amount of jobs by removed jobs)
         */
        while (dequeuedJobsTemp < amountOfJobsToDequeue) {
            Pair<Integer, Integer> listHead = taskBatches.removeFirst();
            amountOfRemovedBatches++;

            /*
             * remove currently first batch in list entirely an proceed with next list
             * element
             */
            if (dequeuedJobsTemp + listHead.getR() <= amountOfJobsToDequeue) {
                dequeuedJobsTemp += listHead.getR();
                log.info("Remove entire batch with elements: " + listHead.getR());

                /*
                 * Remove necessary elements from first batch and update first batch
                 */
            } else {
                int remainingJobsToRemove = amountOfJobsToDequeue - dequeuedJobsTemp;
                dequeuedJobsTemp += remainingJobsToRemove; // dequeuedJobs = amountOfJobsToDequeue --> Exit loop during next
                                                     // iteration
                taskBatches.addFirst(new Pair<Integer, Integer>(listHead.getL(), listHead.getR() - remainingJobsToRemove));
                log.info("New Amount is: " + remainingJobsToRemove);
                

            }

            sumOfQueueingDelays += currentClockTick - listHead.getL();

            /*
             * Exit loop if no more batches available
             */
            if (taskBatches.isEmpty()) {
                break;
            }

        }
       
        
        double queueingDelayToAdd = (double) sumOfQueueingDelays / (double) amountOfRemovedBatches;
        updateQueueingDelay(queueingDelayToAdd);

    }

    private void updateQueueingDelay(double amountOfClockTicksWaitingInQueue) {
        queueingDelayInIntervals.add(amountOfClockTicksWaitingInQueue);
    }

    /*
     * Updating the arrival rate (Moving Average). This includes the update of the
     * processing rate with value=0 as no jobs are processed when new jobs arrive
     * --> Infrastructure is currently busy as arrival rate exceeds capacity
     */
    private void updateArrivalRate(int amountOfIncomingTasks) {
        /*
         * Arrival Rate includes discarded Jobs
         */
        this.queueArrivalRateInTasksPerInterval.add(amountOfIncomingTasks);
        // Update processing rate
        this.queueProcessingRateInTasksPerInterval.add(0);
    }

    /*
     * Updating the processing rate (Moving Average). This includes the update of
     * the arrival rate with value=0 as no new jobs arrive when waiting jobs are
     * processed --> Infrastructure is currently available as arrival rate is less
     * then capacity
     */
    private void updateProcessingRate(int amountOfProcessedJobs) {

        /*
         * Processing rate only includes actually dequeued jobs
         */
        this.queueProcessingRateInTasksPerInterval.add(amountOfProcessedJobs);
        // update moving average for arrival rate
        this.queueArrivalRateInTasksPerInterval.add(0);
    }

    @Override
    public boolean isFull() {

        if (currentQueueLevelInTasks > maxLength) {
            throw new IllegalStateException("Queue overflow.");
        }

        return currentQueueLevelInTasks == maxLength;
    }

    @Override
    public boolean isEmpty() {
        if (currentQueueLevelInTasks < 0) {
            throw new IllegalStateException("Queue underflow");
        }

        return currentQueueLevelInTasks == 0;
    }

    @Override
    public int currentLevelInTasks() {
        return currentQueueLevelInTasks;
    }

    @Override
    public double currentLevelInPercent() {
        return MathUtil.round(((double) currentQueueLevelInTasks / (double) maxLength) * 100.00, 2);

    }

    @Override
    public QueueStateTransferObject getQueueState() {
        QueueStateTransferObject state = new QueueStateTransferObject();
        state.setQueueFillInPercent(currentLevelInPercent());
        state.setTasksInQueue(currentLevelInTasks());
        state.setQueueProcessingRateInTasksPerInterval(queueProcessingRateInTasksPerInterval.average());
        state.setQueueArrivalRateInTasksPerInterval(queueArrivalRateInTasksPerInterval.average());
        state.setQueueingDelayInIntervals(queueingDelayInIntervals.average());

        return state;
    }

    @Override
    public void publishQueueState(TriggerPublishQueueStateEvent event) {
        publisher.fireQueueStateEvent(event.getClockTickCount(), event.getIntervallDuratioInMilliSeconds(),
                getQueueState());

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("maxLegth: ");
        sb.append(maxLength);
        sb.append("\ncurrentLevelInTasks:");
        sb.append(currentQueueLevelInTasks);

        return sb.toString();
    }

}
