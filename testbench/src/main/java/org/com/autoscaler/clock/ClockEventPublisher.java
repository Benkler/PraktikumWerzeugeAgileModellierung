package org.com.autoscaler.clock;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.StartSimulationEvent;
import org.com.autoscaler.events.TriggerAutoScalerEvent;
import org.com.autoscaler.events.TriggerPublishInfrastructureStateEvent;
import org.com.autoscaler.events.TriggerPublishQueueStateEvent;
import org.com.autoscaler.events.TriggerWorkloadHandlerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * This class is responsible of publishing all clock related events
 * 
 * @author Niko
 *
 */
@Component
public class ClockEventPublisher implements IClockEventPublisher {

    private final Logger log = LoggerFactory.getLogger(ClockEventPublisher.class);

    @Autowired
    private ApplicationEventPublisher applEventPublisher;

    /**
     * Fire clock event represents the smallest unit in the discrete time
     * simulation.
     */
    @Override
    public void fireClockEvent(int clockTickCount, double intervalDurationInMilliSeconds) {
        log.info("Normal clock event fired. Clock Tick Count: " + clockTickCount);
        ClockEvent clockEvent = new ClockEvent(this, clockTickCount, intervalDurationInMilliSeconds);
        applEventPublisher.publishEvent(clockEvent);

    }

    /**
     * After a provided amount of discrete clock intervals the workload handler has
     * to be triggered in order to set the new workload. This is due to the fact,
     * that the workload usually does not change at every clock interval but rather
     * stays constant for a given amount of clock ticks
     */
    @Override
    public void fireTriggerWorkloadHandlerEvent(int clockTickCount, double intervalDurationInMilliSeconds) {
        log.info("Trigger workload handler event fired. Clock Tick Count: " + clockTickCount);
        TriggerWorkloadHandlerEvent wlHandlerEvent = new TriggerWorkloadHandlerEvent(this, clockTickCount,
                intervalDurationInMilliSeconds);
        applEventPublisher.publishEvent(wlHandlerEvent);

    }

    /**
     * After a provided amount of discrete clock intervals the auto scaler has to be
     * triggered in order to evaluate the given state and re-calculate the required
     * amount of resources. This is due to the fact, that an auto scaling decision
     * should not happen on every clock tick or even on every worklaod change
     */
    // TODO is das wirklich so, dass die Clock den scaler triggern soll?
    @Override
    public void fireTriggerAutoScalerEvent(int clockTickCount, double intervalDurationInMilliSeconds) {
        log.info("Trigger auto scaler event fired. Clock Tick Count: " + clockTickCount);
        TriggerAutoScalerEvent scalerEvent = new TriggerAutoScalerEvent(this, clockTickCount,
                intervalDurationInMilliSeconds);
        applEventPublisher.publishEvent(scalerEvent);
    }

    @Override
    public void fireTriggerPublishInfrastructureStateEvent(int clockTickCount, double intervallDurationInMilliSeconds) {
        log.info("Trigger publish infrastructure state event fired at clock tick count: " + clockTickCount);
        TriggerPublishInfrastructureStateEvent event = new TriggerPublishInfrastructureStateEvent(this, clockTickCount,
                intervallDurationInMilliSeconds);
        applEventPublisher.publishEvent(event);

    }

    @Override
    public void fireStartSimulationEvent(int clockTickCount, double intercallDuarationInMilliSeconds) {
       log.info("----------------------START SIMULATION EVENT-----------------------------------");
       StartSimulationEvent event = new StartSimulationEvent(this, clockTickCount, intercallDuarationInMilliSeconds);
       applEventPublisher.publishEvent(event);
        
    }

    @Override
    public void fireFinishSimulationEvent(int clockTickCount, double intercallDuarationInMilliSeconds) {
        log.info("----------------------------END SIMULATION EVENT--------------------------- at clocktick: " + clockTickCount);
        FinishSimulationEvent event = new FinishSimulationEvent(this, clockTickCount, intercallDuarationInMilliSeconds);
        applEventPublisher.publishEvent(event);
    }

    @Override
    public void fireTriggerPublishQueueStateEvent(int clockTickCount, double intervallDuarationInMilliSeconds) {
        log.info("Trigger publish queue state event fired at clock tick count: " + clockTickCount);
        TriggerPublishQueueStateEvent event = new TriggerPublishQueueStateEvent(this, clockTickCount, intervallDuarationInMilliSeconds);
        applEventPublisher.publishEvent(event);
        
    }

}
