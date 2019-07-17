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

    private int clockTicksTillPublishInfrastructureState;
    private int clockTicksTillPublishQueueState;
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
        /*
         * Used as scaling factor for workload and virtual machine power --> Need to
         * scale output as well
         */
        double scalingFactor = this.clockTicksTillWorkloadChange*this.intervalDurationInMilliSeconds;
        clockEventPublisher.fireStartSimulationEvent(0, intervalDurationInMilliSeconds, scalingFactor);

        while (clockTickCount <= experimentDurationInClockTicks) {
            fireEvents();
            clockTickCount++;

        }
        clockEventPublisher.fireFinishSimulationEvent(clockTickCount, intervalDurationInMilliSeconds);

    }

    /**
     * Stop the clock ends the simulation
     */
    @Override
    public void stopClock() {
        state = ClockState.STOPPED;
        // TODO

    }

    /**
     * Pause the clock to pause simulation
     */
    @Override
    public void pauseClock() {
        state = ClockState.PAUSED;
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

        this.clockTicksTillWorkloadChange = clockInfo.getClockTicksTillWorkloadChange();
        this.clockTicksTillPublishInfrastructureState = clockInfo.getClockTicksTillPublishInfrastructureState();
        this.clockTicksTillPublishQueueState = clockInfo.getClockTicksTillPublishQueueState();

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

        if (clockTickCount % clockTicksTillWorkloadChange == 0) {
            clockEventPublisher.fireTriggerWorkloadHandlerEvent(clockTickCount, intervalDurationInMilliSeconds);
        }

        // if (clockTickCount % clockTicksTillScalingDecision == 0) {
        // clockEventPublisher.fireTriggerAutoScalerEvent(clockTickCount,
        // intervalDurationInMilliSeconds);
        // }

        clockEventPublisher.fireClockEvent(clockTickCount, intervalDurationInMilliSeconds);

        // INfrastruktur
        if (clockTickCount % clockTicksTillPublishInfrastructureState == 0) {
            clockEventPublisher.fireTriggerPublishInfrastructureStateEvent(clockTickCount,
                    intervalDurationInMilliSeconds);

        }

        // Queue
        if (clockTickCount % clockTicksTillPublishQueueState == 0) {

            clockEventPublisher.fireTriggerPublishQueueStateEvent(clockTickCount, intervalDurationInMilliSeconds);
        }

    }

}
