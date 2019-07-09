package org.com.autoscaler.scaler;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.TriggerAutoScalerEvent;

public interface IAutoScaler {

    void initAutoScaler(double lowerThreshold, double upperThreshold, int vmTasksPerIntervall, int vmMin, int vmMax, int startUpTime,int coolDownTimeInIntervallTicks);

    void update(TriggerAutoScalerEvent event);
    
    void handleClockTick(ClockEvent event);

}