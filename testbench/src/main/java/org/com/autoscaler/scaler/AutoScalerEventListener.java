package org.com.autoscaler.scaler;

import org.com.autoscaler.events.ClockEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoScalerEventListener implements IAutoScalerEventListener{

    @Autowired
    IAutoScaler autoScaler;
    
    private static final Logger log = LoggerFactory.getLogger(AutoScalerEventListener.class);
    
  

    @Override
    public void handleClockEvent(ClockEvent event) {
       autoScaler.handleClockTick(event);
        
    }

}
