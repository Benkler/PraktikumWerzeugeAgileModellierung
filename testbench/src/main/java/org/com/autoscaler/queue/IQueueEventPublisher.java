package org.com.autoscaler.queue;

import org.com.autoscaler.events.ClockEvent;

public interface IQueueEventPublisher {
    
    public void fireQueueDiscardJobsEvent(ClockEvent clockEvent, int amountOfDiscardedEvents);

}
