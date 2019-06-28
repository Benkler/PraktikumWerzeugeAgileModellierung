package org.com.autoscaler.metricsource;

import org.com.autoscaler.events.InfrastructureStateEvent;

import org.springframework.context.event.EventListener;

public interface IMetricSourceEventListener {
    
    @EventListener
    public void handleInfrastructureStateEvent(InfrastructureStateEvent event);

}
