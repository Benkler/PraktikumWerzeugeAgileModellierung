package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InfrastructureModelEventPublisher implements IInfrastructureModelEventPublisher {
    
    private final Logger log = LoggerFactory.getLogger(InfrastructureModelEventPublisher.class);

    @Autowired
    private ApplicationEventPublisher applEventPublisher;

    @Override
    public void fireInfrastructureStateEvent(InfrastructureStateTransferObject state, ClockEvent clockEvent) {
        log.info("Fire infrastructure state event at clockTick: " + clockEvent.getClockTickCount());
        InfrastructureStateEvent event = new InfrastructureStateEvent(this, clockEvent.getClockTickCount(),
                clockEvent.getIntervallDuratioInMilliSeconds(), state);
        
        applEventPublisher.publishEvent(event);

    }

}
