package org.com.autoscaler.scaler;

import org.com.autoscaler.events.ClockEvent;

public interface IAutoScaler {

    void initAutoScaler(double lowerThreshold, double upperThreshold, int vmMin, int vmMax,
            int coolDownTimeInIntervallTicks, int clockTicksTillScalingDecision);

    void handleClockTick(ClockEvent event);

}