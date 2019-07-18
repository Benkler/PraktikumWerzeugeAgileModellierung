package org.com.autoscaler.scaler;

import org.com.autoscaler.events.ClockEvent;

public interface IAutoScaler {

    /**
     * AutoScaler is initialized with those parameters
     */
    void initAutoScaler(double lowerThreshold, double upperThreshold, int vmMin, int vmMax,
            int coolDownTimeInIntervallTicks, int clockTicksTillScalingDecision);

    /**
     * Implement Logic. Proactive scaling decision! Need to check each clockTick if
     * scaling decision needs to be executed.
     * This includes to administer coolDown counter.
     * 
     * @param event
     */
    void handleClockTick(ClockEvent event);

}