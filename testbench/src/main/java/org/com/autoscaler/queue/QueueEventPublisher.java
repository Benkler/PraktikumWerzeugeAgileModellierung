package org.com.autoscaler.queue;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.DiscardJobsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class QueueEventPublisher implements IQueueEventPublisher{

    
    private final Logger log = LoggerFactory.getLogger(QueueEventPublisher.class);

    @Autowired
    private ApplicationEventPublisher applEventPublisher;
    
    @Override
    public void fireQueueDiscardJobsEvent(ClockEvent event, int amountOfDiscardedEvents) {
        log.info("Fire Discarded Tasks Event with: "+  amountOfDiscardedEvents +" events");
        DiscardJobsEvent discardEvent = new DiscardJobsEvent(this, event.getClockTickCount() , event.getIntervallDuratioInMilliSeconds(), amountOfDiscardedEvents);
        applEventPublisher.publishEvent(discardEvent);
        
    }

}
