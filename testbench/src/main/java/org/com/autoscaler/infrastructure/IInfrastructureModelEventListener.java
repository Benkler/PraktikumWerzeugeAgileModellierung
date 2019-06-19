package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.ScalingEvent;
import org.com.autoscaler.events.WorkloadChangedEvent;
import org.springframework.context.event.EventListener;

public interface IInfrastructureModelEventListener{
    
    @EventListener
    public void handleClockEvent(ClockEvent clockEvent);
    
    @EventListener
    public void handleWorkloadChangedEvent(WorkloadChangedEvent workloadChangedEvent);
    
    @EventListener
    public void handleScalingEvent(ScalingEvent scalingEvent);

}
