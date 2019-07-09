package org.com.autoscaler.scaler;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.TriggerAutoScalerEvent;
import org.springframework.context.event.EventListener;

public interface IAutoScalerEventListener {
    
    @EventListener
    public void handleTriggerAutoScalerEvent(TriggerAutoScalerEvent event);
    
    @EventListener
    public void handleClockEvent(ClockEvent event);

}
