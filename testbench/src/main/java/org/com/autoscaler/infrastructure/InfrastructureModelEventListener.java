package org.com.autoscaler.infrastructure;

import org.com.autoscaler.events.ClockEvent;
import org.com.autoscaler.events.WorkloadChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InfrastructureModelEventListener implements IInfrastructureModelEventListener {
   
    Logger log = LoggerFactory.getLogger(InfrastructureModelEventListener.class);
    
    @Override
    public void handleClockEvent(ClockEvent clockEvent) {
        log.info("handle clock event with clock tick count: " + clockEvent.getClockTickCount());
    }

    @Override
    public void handleWorkloadChangedEvent(WorkloadChangedEvent workloadChangedEvent) {
        log.info("handle workload change event with clock tick count: " + workloadChangedEvent.getClockTickCount());
        
    }

}
