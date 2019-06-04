package org.com.autoscaler.clock;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.TriggerAutoScalerEvent;
import org.com.autoscaler.events.TriggerWorkloadHandlerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ClockEventPublisher implements IClockEventPublisher {

    private final Logger log = LoggerFactory.getLogger(ClockEventPublisher.class);

    @Autowired
    private ApplicationEventPublisher applEventPublisher;

    public void fireClockEvent(int clockTickCount, double intervalDurationInSeconds) {
        log.info("Normal clock event fired. Clock Tick Count: " + clockTickCount);
        ClockEvent clockEvent = new ClockEvent(this, clockTickCount, intervalDurationInSeconds);
        applEventPublisher.publishEvent(clockEvent);

    }

    public void fireTriggerWorkloadHandlerEvent(int clockTickCount, double intervalDurationInSeconds) {
        log.info("Trigger workload handler event fired. Clock Tick Count: " + clockTickCount);
        TriggerWorkloadHandlerEvent wlHandlerEvent = new TriggerWorkloadHandlerEvent(this, clockTickCount,
                intervalDurationInSeconds);
        applEventPublisher.publishEvent(wlHandlerEvent);

    }
    
    public void fireTriggerAutoScalerEvent(int clockTickCount, double intervalDurationInSeconds) {
        log.info("Trigger auto scaler event fired. Clock Tick Count: " + clockTickCount);
        TriggerAutoScalerEvent scalerEvent = new TriggerAutoScalerEvent(this, clockTickCount, intervalDurationInSeconds);
        applEventPublisher.publishEvent(scalerEvent);
    }

}
