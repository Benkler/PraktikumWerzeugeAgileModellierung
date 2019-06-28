package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.InfrastructureStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publishes any onfrastructure model based event
 * 
 * @author Niko
 *
 */
@Component
public class InfrastructureModelEventPublisher implements IInfrastructureModelEventPublisher {

    private final Logger log = LoggerFactory.getLogger(InfrastructureModelEventPublisher.class);

    @Autowired
    private ApplicationEventPublisher applEventPublisher;

    /**
     * after certain amount of clock ticks, the clock triogges the infrastructure to
     * publish its current state
     */
    @Override
    public void fireInfrastructureStateEvent(InfrastructureStateTransferObject state, int clockTickCount,
            double intervalDurationInMilliSeconds) {
        log.info("Fire infrastructure state event at clockTick: " + clockTickCount);
        InfrastructureStateEvent event = new InfrastructureStateEvent(this, clockTickCount, intervalDurationInMilliSeconds,
                state);

        applEventPublisher.publishEvent(event); 

    }

}
