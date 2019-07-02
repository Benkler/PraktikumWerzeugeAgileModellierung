package org.com.autoscaler.tracker.cpuutilization;

import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.StartSimulationEvent;

public interface ICPUUtilTracker {
    
    public void startSimulation(StartSimulationEvent event);
    
    public void finishSimulation(FinishSimulationEvent event);
    
    public void trackCpuUtil(InfrastructureStateEvent event);
    

}
