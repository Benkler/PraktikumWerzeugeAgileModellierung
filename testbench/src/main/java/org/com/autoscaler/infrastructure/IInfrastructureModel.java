package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.ScalingEvent;
import org.com.autoscaler.events.TriggerPublishInfrastructureStateEvent;
import org.com.autoscaler.events.WorkloadChangedEvent;

public interface IInfrastructureModel {
    
    public void handleClockTick(ClockEvent clockEvent);
    
    public void changeWorkload(WorkloadChangedEvent wlChangedEvent);
    
    public void initInfrastructureModel(InfrastructureState infrastructure, int cpuUitilizationWindow);
    
    public InfrastructureStateTransferObject getInfrastructureState();
    
    public void publishInfrastructureState(TriggerPublishInfrastructureStateEvent event);
    
    public void scaleVirtualMachines(ScalingEvent event);

}
