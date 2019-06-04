package org.com.autoscaler.clock;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class represents a clock that triggers the various components of the
 * system according to a give time schema
 * 
 * @author Niko
 *
 */
@Component
public class Clock implements IClock {

    private final Logger log = LoggerFactory.getLogger(Clock.class);

    // Clock Settings
    private int clockTickCount;
    private double intervalDurationInSeconds;
    private int clockTicksTillWorkloadChange;
    private int clockTicksTillScalingDecision;

    private boolean initialized = false;
    private ClockState state;

    // Autowired by Spring
    private IClockEventPublisher clockEventPublisher;

    @Autowired
    public Clock(ClockEventPublisher clockEventPublisher) {
        this.clockEventPublisher = clockEventPublisher;
    }
 
    /**
     * Start the clock starts the discrete simulation
     */
    @Override
    public void startClock() {
        state = ClockState.RUNNING;

        for (int i = 0; i < 10; i++) {
            fireEvents();
        }

        // while (state == ClockState.RUNNING) {
        //
        // }

    }
    
    /**
     * Stop the clock ends the simulation
     */
    @Override
    public void stopClock() {
        state = ClockState.STOPPED;

    }
    
    /**
     * Pause the clock to pause simulation
     */
    @Override
    public void pauseClock() {
        // TODO Auto-generated method stub

    }
    
    /**
     * Initialize all required clock fields
     */
    @Override
    public void initClock(double intervalDurationInSeconds, int clockTickTillWorkloadChange,
            int clockTicksTillScalingDecision) {
        if (initialized)
            return; // only allow initClock once!

        assert(intervalDurationInSeconds > 0);
        assert(clockTicksTillScalingDecision > 0);
        assert(clockTickTillWorkloadChange > 0);
        
        
        this.intervalDurationInSeconds = intervalDurationInSeconds;
        this.clockTickCount = 0;
        this.clockTicksTillScalingDecision = clockTicksTillScalingDecision;
        this.clockTicksTillWorkloadChange = clockTickTillWorkloadChange;

        initialized = true;
        state = ClockState.INITIALIZED;

    }

    /*
     * Fire clock events in predefined order to guarantee that scaling decisions,
     * change in workload etc. are executed according to predefined granularity
     */
    private void fireEvents() {
        clockEventPublisher.fireClockEvent(clockTickCount, intervalDurationInSeconds);

        if (clockTickCount % clockTicksTillWorkloadChange == 0) {
            clockEventPublisher.fireTriggerWorkloadHandlerEvent(clockTickCount, intervalDurationInSeconds);
        }

        if (clockTickCount % clockTicksTillScalingDecision == 0) {
            clockEventPublisher.fireTriggerAutoScalerEvent(clockTickCount, intervalDurationInSeconds);
        }
        
        clockTickCount++;

    }

}
