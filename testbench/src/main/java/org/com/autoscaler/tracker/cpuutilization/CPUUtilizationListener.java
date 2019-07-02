package org.com.autoscaler.tracker.cpuutilization;

import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.StartSimulationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CPUUtilizationListener implements ICPUUtilizationListener{

    @Autowired
    ICPUUtilTracker tracker;
    
    @Override
    public void listenCPUUtilization(InfrastructureStateEvent event) {
        tracker.trackCpuUtil(event);
        
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
