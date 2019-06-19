package org.com.autoscaler.scaler;

import org.com.autoscaler.events.TriggerAutoScalerEvent;

public interface IAutoScaler {

    void initAutoScaler(double lowerThreshold, double upperThreshold, int vmTasksPerIntervall, int vmMin, int vmMax, int startUpTime);

    void update(TriggerAutoScalerEvent event);

}