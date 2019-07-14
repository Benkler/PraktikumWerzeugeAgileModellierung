package org.com.autoscaler.tracker.cpuutilization;

import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.StartSimulationEvent;

public interface IInfrastructureUtilizationTracker {
    
    public void startSimulation(StartSimulationEvent event);
    
    public void finishSimulation(FinishSimulationEvent event);
    
    public void trackInfrastructureState(InfrastructureStateEvent event);
    

}
