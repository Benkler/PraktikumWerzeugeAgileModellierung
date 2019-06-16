package org.com.autoscaler.scaler;

import java.util.List;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.util.ScalingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScalingController implements IScalingController {
    
    @Autowired
    IScalingEventPusblisher publisher;

   

    @Override
    public void setInstances(List<VirtualMachine> updatedInstances, int clockTickCount,
            double intervallDuratioInMilliSeconds, ScalingMode mode) {
        publisher.fireScalingEvent(updatedInstances, clockTickCount,intervallDuratioInMilliSeconds, mode);
        
    }
    
  

   

   

}
