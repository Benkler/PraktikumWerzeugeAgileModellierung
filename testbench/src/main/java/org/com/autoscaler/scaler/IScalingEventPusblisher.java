package org.com.autoscaler.scaler;

import java.util.List;

import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.util.ScalingMode;

public interface IScalingEventPusblisher {
    
    

   

    public void fireScalingEvent(List<VirtualMachine> updatedInstances, int clockTickCount,
            double intervallDuratioInMilliSeconds, ScalingMode mode);

}
