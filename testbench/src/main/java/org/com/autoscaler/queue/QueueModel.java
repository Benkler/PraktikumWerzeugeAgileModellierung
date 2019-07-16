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

    /*
     * Represents the amount of clocktTicks a tasks needs to get through the queue
     */
    private int queuingDelay;

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
    private MovingAverage<Integer> queueDepartureRateInTasksPerInterval;
    private MovingAverage<Double> queueingDelayMeasurementInIntervals;

    private boolean init = false;

    Logger log = LoggerFactory.getLogger(QueueModel.class);

    @Autowired
    IQueueEventPublisher publisher;

    @Override
    public void initQueue(int maxLength, int windowSize, int queuingDelay) {
        if (init)
            return;

        if (maxLength < 0) {
            throw new IllegalArgumentException("Invalid Queue parameters");
        }

        this.maxLength = maxLength;
        this.currentQueueLevelInTasks = 0;
        this.queueArrivalRateInTasksPerInterval = new MovingAverage<Integer>(windowSize);
        this.queueDepartureRateInTasksPerInterval = new MovingAverage<Integer>(windowSize);
        this.queueingDelayMeasurementInIntervals = new MovingAverage<Double>(windowSize);
        this.taskBatches = new LinkedList<Pair<Integer, Integer>>();
        this.queuingDelay = queuingDelay;

        log.info("Queue initialized: " + toString());

    }

    private boolean checkIfFirstBatchCanBeDequeued(int currentClockTick) {

        // Nothing to dequeue --> Queuing Delay is 0
        if (taskBatches.isEmpty()) {

            return false;
        }

        /*
         * The task batch list is an ordered list. Therefore the first element is oldest
         * task to leave the system. But each task needs to be in queue for at least x
         * clock ticks, where x is represented by the queueingDelay.
         */
        if (currentClockTick < taskBatches.getFirst().getL() + queuingDelay) {
            // Tasks to be removed have not been processed yet
            return false;
        }

        return true;
    }

    /**
     * Return the amount of jobs that were dequeued. <br>
     * currentLevel - jobs > minLength: Return jobs jobs < minLength: Return
     * currentLevel - minLength
     */
    @Override
    public int dequeue(int jobs, ClockEvent event) {

        log.info("Trying to dequeue " + jobs + " Tasks.");

        int dequeuedJobs = dequeueBatchAndUpdateQueueingDelay(event, jobs);

        updateProcessingRate(dequeuedJobs);

        log.info("Actually amount of dequeued Tasks: " + dequeuedJobs);
        return dequeuedJobs;

    }

    /*
     * For testing purposes.
     */
    private void testqueue() {
        int tempSize = 0;
        for (int i = 0; i < taskBatches.size(); i++) {
            tempSize += taskBatches.get(i).getR();
        }

        if (tempSize != currentQueueLevelInTasks) {
            throw new AssertionError("In batch: " + tempSize + " Desired: " + currentQueueLevelInTasks);
        }
    }

    /*
     * Dequeue amount of jobs from batch FIFO list. This might include to remove
     * several batches from FIFO list (in case the amount of jobs exceed the first
     * batch to remove) OR to update the next batch (in case the amount of jobs to
     * remove does not exceed the amount of jobs in the current batch)
     */
    private int dequeueBatchAndUpdateQueueingDelay(ClockEvent event, int amountOfJobsToDequeue) {

        /*
         * Jobs that will be dequeued
         */
        int dequeuedJobsTemp = 0;

        // Necessary as more that one batch might be removed --> Average necessary
        int amountOfRemovedBatches = 0;
        int sumOfQueueingDelays = 0;

        /*
         * Remove as many batches as required. In case a batch is only partly removed,
         * just update first FIFO list element (reduce amount of jobs by removed jobs)
         */
        while ((dequeuedJobsTemp < amountOfJobsToDequeue)
                && checkIfFirstBatchCanBeDequeued(event.getClockTickCount())) {
            Pair<Integer, Integer> firstBatchInFIFO = taskBatches.removeFirst();
            amountOfRemovedBatches++;

            /*
             * process currently first batch in list entirely an proceed with next list
             * element
             */
            if (dequeuedJobsTemp + firstBatchInFIFO.getR() <= amountOfJobsToDequeue) {
                dequeuedJobsTemp += firstBatchInFIFO.getR();
                log.info("Remove entire batch with elements: " + firstBatchInFIFO.getR());

                /*
                 * Remove necessary elements from first batch and update first batch. Now,
                 * enough elements haven been deleted from queue
                 */
            } else {
                int remainingElementsToRetrieveFromBatch = amountOfJobsToDequeue - dequeuedJobsTemp;
                /*
                 * dequeuedJobs = amountOfJobsToDequeue --> Exit loop during next iteration
                 */
                dequeuedJobsTemp += remainingElementsToRetrieveFromBatch;

                int newAmountOfElementsInFirstBatch = firstBatchInFIFO.getR() - remainingElementsToRetrieveFromBatch;
                taskBatches
                        .addFirst(new Pair<Integer, Integer>(firstBatchInFIFO.getL(), newAmountOfElementsInFirstBatch));

                if (newAmountOfElementsInFirstBatch < 0) {
                    throw new AssertionError("New amount of elements is " + newAmountOfElementsInFirstBatch);
                }
                log.info("New Amount for the first batch in queue is: " + newAmountOfElementsInFirstBatch);

            }

            sumOfQueueingDelays += event.getClockTickCount() - firstBatchInFIFO.getL();

        }

        if (amountOfRemovedBatches == 0) {
            log.info("No jobs are available to be dequeued at clockTick " + event.getClockTickCount());
            updateQueuingDelay(0.0);
        } else {
            double queueingDelayToAdd = (double) sumOfQueueingDelays / (double) amountOfRemovedBatches;
            updateQueuingDelay(queueingDelayToAdd);

        }

        currentQueueLevelInTasks -= dequeuedJobsTemp;
        return dequeuedJobsTemp;

    }

    /**
     * For testing purposes
     */
    void printQueue() {
        for (int i = 0; i < taskBatches.size(); i++) {
            log.info("Batch with clockTick " + taskBatches.get(i).getL() + "  has " + taskBatches.get(i).getR()
                    + " jobs");
        }

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

        // arrival rate is independent from actually enqueued jobs --> Needs to be
        // before enqueing
        updateArrivalRate(jobs);

        // Jobs that will be enqueued (Might be less then incoming amount due to full
        // Queue)
        int enqueuedJobs = enqueueBatch(clockEvent, jobs);

        // for testing purposes
        // printQueue();

        return enqueuedJobs;
    }

    /*
     * Enqueue batch of jobs at a certain clock tick.
     */
    private int enqueueBatch(ClockEvent event, int amountOfJobs) {

        // Jobs that will be enqueued
        int enqueuedJobs = 0;

        // Enough space in queue available
        if (currentQueueLevelInTasks + amountOfJobs <= maxLength) {
            enqueuedJobs = amountOfJobs;
            currentQueueLevelInTasks += amountOfJobs;

            // Need to discard Jobs
        } else {

            enqueuedJobs = maxLength - currentQueueLevelInTasks;
            currentQueueLevelInTasks = maxLength;
            int discardedJobs = amountOfJobs - enqueuedJobs;
            publisher.fireQueueDiscardJobsEvent(event, discardedJobs);
            log.info("Queue is full. Desired amoun of enqueuing tasks was " + amountOfJobs + ". Actually enqueued: "
                    + enqueuedJobs);
        }

        /*
         * Don't need to enqueue batch if all jobs were discarded due to full queue
         */

        if (enqueuedJobs == 0) {
            log.info("No need to enqueue batch. Amount of jobs to be enqueued is 0 ");
            return 0;
        }

        log.info("Enqeue batch at clocktTick " + event.getClockTickCount() + " with " + amountOfJobs
                + " tasks. Current Level of tasks is: " + currentQueueLevelInTasks);

        taskBatches.addLast(new Pair<Integer, Integer>(event.getClockTickCount(), enqueuedJobs));
        return enqueuedJobs;
    }

    private void updateQueuingDelay(double queueingDelayToAdd) {
        queueingDelayMeasurementInIntervals.add(queueingDelayToAdd);
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
        this.queueDepartureRateInTasksPerInterval.add(amountOfProcessedJobs);

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

    private QueueStateTransferObject getQueueState(double intervallDurationInMilliSeconds) {
        QueueStateTransferObject state = new QueueStateTransferObject();

        double averageQueueDepartureRateInTasksPerInterval = queueDepartureRateInTasksPerInterval.average();
        double averageQueueArrivalRateInTasksPerInterval = queueArrivalRateInTasksPerInterval.average();
        double averageQueueingDelayInIntervals = queueingDelayMeasurementInIntervals.average();

        double averageQueueDepartureRateInTasksPerMilliseconds = MathUtil.tasksPerIntervallInTasksPerMillisecond(
                averageQueueDepartureRateInTasksPerInterval, intervallDurationInMilliSeconds);

        double averageQueueArrivalRateInTasksPerMilliseconds = MathUtil.tasksPerIntervallInTasksPerMillisecond(
                averageQueueArrivalRateInTasksPerInterval, intervallDurationInMilliSeconds);

        double averageQueueingDelayInMilliseconds = MathUtil.clockTicksInMillisecond(averageQueueingDelayInIntervals,
                intervallDurationInMilliSeconds);

        state.setQueueFillInPercent(currentLevelInPercent());
        state.setTasksInQueue(currentLevelInTasks());
        
        state.setQueueProcessingRateInTasksPerInterval(averageQueueDepartureRateInTasksPerInterval);
        state.setQueueArrivalRateInTasksPerInterval(averageQueueArrivalRateInTasksPerInterval);
        state.setQueueingDelayInIntervals(averageQueueingDelayInIntervals);

        state.setQueueArrivalRateInTasksPerMilliSecond(averageQueueArrivalRateInTasksPerMilliseconds);
        state.setQueueProcessingRateInTasksPerMilliSecond(averageQueueDepartureRateInTasksPerMilliseconds);
        state.setQueuingDelayInMilliseconds(averageQueueingDelayInMilliseconds);
        return state;
    }

    @Override
    public void publishQueueState(TriggerPublishQueueStateEvent event) {
        publisher.fireQueueStateEvent(event.getClockTickCount(), event.getIntervallDuratioInMilliSeconds(),
                getQueueState(event.getIntervallDuratioInMilliSeconds()));

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
