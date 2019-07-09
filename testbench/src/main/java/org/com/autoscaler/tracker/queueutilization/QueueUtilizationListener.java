package org.com.autoscaler.tracker.queueutilization;

import org.com.autoscaler.events.DiscardJobsEvent;
import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.QueueStateEvent;
import org.com.autoscaler.events.StartSimulationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueUtilizationListener implements IQueueUtilizationListener {

    @Autowired
    IQueueDiscardJobsTracker discardJobsTracker;
    
    @Autowired
    IQueueLengthTracker lengthTracker;
    
    @Override
    public void listenDiscardJobsEvent(DiscardJobsEvent event) {
        discardJobsTracker.trackDiscardJobsEvent(event);
    }

    @Override
    public void listenStartSimulationEvent(StartSimulationEvent event) {
        discardJobsTracker.startSimulation(event);
        lengthTracker.startSimulation(event);
        
    }

    @Override
    public void listenFinishSimulationEvent(FinishSimulationEvent event) {
        discardJobsTracker.finishSimulation(event);
        lengthTracker.finishSimulation(event);
        
    }

    @Override
    public void listenQueueStateEvent(QueueStateEvent event) {
        lengthTracker.trackQueueStateEvent(event);
        
    }

}
