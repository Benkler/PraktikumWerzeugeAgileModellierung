package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.WorkloadChangedEvent;

public interface IInfrastructureModel {
    
    public void handleClockTick(ClockEvent clockEvent);
    
    public void changeWorkload(WorkloadChangedEvent wlChangedEvent);
    
    public void initInfrastructureModel(InfrastructureInformation infrastructure);

}
