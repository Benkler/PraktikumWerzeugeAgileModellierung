package org.com.autoscaler.tracker.queueutilization;

import org.com.autoscaler.events.DiscardJobsEvent;
import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.StartSimulationEvent;

public interface IQueueDiscardJobsTracker {
    
public void startSimulation(StartSimulationEvent event);
    
    public void finishSimulation(FinishSimulationEvent event);
    
    public void trackDiscardJobsEvent(DiscardJobsEvent event);

}
