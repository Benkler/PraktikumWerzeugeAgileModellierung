package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.ScalingEvent;
import org.com.autoscaler.events.WorkloadChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfrastructureModelEventListener implements IInfrastructureModelEventListener {
   
    Logger log = LoggerFactory.getLogger(InfrastructureModelEventListener.class);
    
    @Autowired
    InfrastructureModel model;
    
    @Override
    public void handleClockEvent(ClockEvent clockEvent) {
        log.info("handle clock event with clock tick count: " + clockEvent.getClockTickCount());
        model.handleClockTick(clockEvent);
    }

    @Override
    public void handleWorkloadChangedEvent(WorkloadChangedEvent workloadChangedEvent) {
        log.info("handle workload change event with clock tick count: " + workloadChangedEvent.getClockTickCount());
        model.changeWorkload(workloadChangedEvent);
        
    }

    @Override
    public void handleScalingEvent(ScalingEvent scalingEvent) {
        log.info("handle scaling event with clock tick count:  " + scalingEvent.getClockTickCount());
        model.scaleVirtualMachines(scalingEvent);
        
    }

}
