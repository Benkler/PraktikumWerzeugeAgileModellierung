package org.com.autoscaler.clock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private double intervalDurationInMilliSeconds;
    private int clockTicksTillWorkloadChange;
    private int clockTicksTillScalingDecision;
    private int monitoringDelay;
    private boolean initialized = false;
    private ClockState state;
    private int experimentDurationInClockTicks;
    private final int SECONDS = 60;
    private final int MILLIS = 1000;

    // Autowired by Spring
    private IClockEventPublisher clockEventPublisher;

    @Autowired
    public Clock(IClockEventPublisher clockEventPublisher) {
        this.clockEventPublisher = clockEventPublisher;

    }

    /**
     * Start the clock starts the discrete simulation
     */
    @Override
    public void startClock() {
        state = ClockState.RUNNING;

        while (clockTickCount <= experimentDurationInClockTicks) {
            fireEvents();
            clockTickCount++;
        }

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
    public void initClock(ClockInformation clockInfo) {
        if (initialized)
            return; // only allow initClock once!

        this.intervalDurationInMilliSeconds = clockInfo.getIntervalDurationInMilliSeconds();
        this.clockTickCount = 0;
        this.clockTicksTillScalingDecision = clockInfo.getClockTicksTillScalingDecision();
        this.clockTicksTillWorkloadChange = clockInfo.getClockTicksTillWorkloadChange();
        this.monitoringDelay = clockInfo.getMonitoringDelay();

        /* 
         * Calculate the clock ticks by duration in minutes and interval duration in
         * milli seconds
         */
        this.experimentDurationInClockTicks = Math.round((float) (clockInfo.getExperimentDurationInMinutes() * SECONDS
                * MILLIS / intervalDurationInMilliSeconds));

        initialized = true;
        state = ClockState.INITIALIZED;

    }

    /*
     * Fire clock events in predefined order to guarantee that scaling decisions,
     * change in workload etc. are executed according to predefined granularity
     */
    private void fireEvents() {
        clockEventPublisher.fireClockEvent(clockTickCount, intervalDurationInMilliSeconds);
 
        if (clockTickCount % clockTicksTillWorkloadChange == 0) {
            clockEventPublisher.fireTriggerWorkloadHandlerEvent(clockTickCount, intervalDurationInMilliSeconds);
        }

        if (clockTickCount % clockTicksTillScalingDecision == 0) {
            clockEventPublisher.fireTriggerAutoScalerEvent(clockTickCount, intervalDurationInMilliSeconds);
        }

        if(clockTickCount % monitoringDelay == 0) {
           clockEventPublisher.fireTriggerPublishInfrastructureStateEvent(clockTickCount, intervalDurationInMilliSeconds);
        }

    }

}
