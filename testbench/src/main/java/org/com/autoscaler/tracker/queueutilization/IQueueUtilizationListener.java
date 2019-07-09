package org.com.autoscaler.tracker.queueutilization;

import org.com.autoscaler.events.DiscardJobsEvent;
import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.QueueStateEvent;
import org.com.autoscaler.events.StartSimulationEvent;
import org.springframework.context.event.EventListener;

public interface IQueueUtilizationListener {
    
    @EventListener
    public void listenDiscardJobsEvent(DiscardJobsEvent event);
    
    @EventListener
    public void listenStartSimulationEvent(StartSimulationEvent event);
    
    @EventListener
    public void listenFinishSimulationEvent(FinishSimulationEvent event);
    
    @EventListener
    public void listenQueueStateEvent(QueueStateEvent event);

}
