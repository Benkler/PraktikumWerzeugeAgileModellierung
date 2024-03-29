package org.com.autoscaler.tracker.cpuutilization;

import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.StartSimulationEvent;
import org.springframework.context.event.EventListener;

public interface IInfrastructureUtilizationListener {

    
    @EventListener
    public void listenInfrastructureStateEvent(InfrastructureStateEvent event);
    
    @EventListener
    public void listenStartSimulationEvent(StartSimulationEvent event);
    
    @EventListener
    public void listenFinishSimulationEvent(FinishSimulationEvent event);
}
