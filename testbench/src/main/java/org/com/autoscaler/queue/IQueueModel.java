package org.com.autoscaler.queue;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.TriggerPublishQueueStateEvent;

public interface IQueueModel {

    void initQueue(int queueLength, int windowSize, int queuingDelay);

    /**
     * Return the amount of jobs that were enqueued <br>
     * If currentLevel + jobs <= maxLength: Return jobs <br>
     * If currentLevel + jobs > maxLengtht: Return available capacity before
     * enqueuing --> Indicates a discarding of jobs!
     * 
     * @param jobs
     * @return
     */
    int enqueue(int jobs, ClockEvent event);

    /**
     * Return the amount of jobs that were dequeued. <br>
     * currentLevel - jobs > minLength: Return jobs jobs < minLength: Return
     * currentLevel - minLength
     */
    int dequeue(int jobs, ClockEvent event);
    
    public QueueStateTransferObject getQueueState();

    boolean isFull();

    boolean isEmpty();

    int currentLevelInTasks();

    double currentLevelInPercent();
    
    public void publishQueueState(TriggerPublishQueueStateEvent event);

}