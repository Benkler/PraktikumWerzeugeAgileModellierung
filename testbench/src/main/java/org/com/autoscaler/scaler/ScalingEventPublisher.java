package org.com.autoscaler.scaler;

import java.util.List;

import org.com.autoscaler.events.ScalingEvent;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ScalingEventPublisher implements IScalingEventPusblisher {

    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    Logger log = LoggerFactory.getLogger(ScalingEventPublisher.class);

    @Override
    public void fireScalingEvent(List<VirtualMachine> virtualMachines, int clockTickCount,
            double intervallDuratioInMilliSeconds, ScalingMode mode) {
        log.info("Fire Scaling Event at ClockTick: " + clockTickCount);
        
        ScalingEvent event = new ScalingEvent(this, clockTickCount,intervallDuratioInMilliSeconds, virtualMachines, mode);
        publisher.publishEvent(event);
        
    }
    
    
}
