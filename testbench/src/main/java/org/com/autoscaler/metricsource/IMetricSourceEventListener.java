package org.com.autoscaler.metricsource;

import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.QueueStateEvent;
import org.springframework.context.event.EventListener;

public interface IMetricSourceEventListener {
    
    @EventListener
    public void handleInfrastructureStateEvent(InfrastructureStateEvent event);
    
    @EventListener
    public void handleQueueStateEvent(QueueStateEvent event);

}
