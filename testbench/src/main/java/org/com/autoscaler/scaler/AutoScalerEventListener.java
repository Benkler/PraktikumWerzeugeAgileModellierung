package org.com.autoscaler.scaler;

import org.com.autoscaler.events.TriggerAutoScalerEvent;
import org.springframework.beans.factory.annotation.Autowired;

public class AutoScalerEventListener implements IAutoScalerEventListener{

    @Autowired
    IAutoScaler autoScaler;
    
    @Override
    public void handleTriggerAutoScalerEvent(TriggerAutoScalerEvent event) {
        autoScaler.update(event);
        
    }

}
