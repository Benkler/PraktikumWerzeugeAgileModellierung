package org.com.autoscaler.tracker.cpuutilization;

import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.StartSimulationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfrastructureUtilizationListener implements IInfrastructureUtilizationListener{

    @Autowired
    IInfrastructureUtilizationTracker tracker;
    
    @Override
    public void listenInfrastructureStateEvent(InfrastructureStateEvent event) {
        tracker.trackInfrastructureState(event);
        
    }

    @Override
    public void listenStartSimulationEvent(StartSimulationEvent event) {
        tracker.startSimulation(event);
        
    }

    @Override
    public void listenFinishSimulationEvent(FinishSimulationEvent event) {
        tracker.finishSimulation(event);
        
    }
    
    

}
