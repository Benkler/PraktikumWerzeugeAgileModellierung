package org.com.autoscaler.workloadhandler;

import org.com.autoscaler.events.TriggerWorkloadHandlerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkloadHandler implements IWorkloadHandler{

    private final IWorkloadHandlerEventPublisher publisher;
    
    @Autowired
    public WorkloadHandler(IWorkloadHandlerEventPublisher publisher) {
        this.publisher = publisher;
    }
    
 

    /**
     * Read changed Workload
     */
    @Override
    public void processNewWorkloadInfo(TriggerWorkloadHandlerEvent triggerEvent) {
        int requestsPerIntervall = 2;
        WorkloadInfo workload = new WorkloadInfo(requestsPerIntervall, triggerEvent.getIntervallDuratioInSeconds());
        publisher.fireWorkloadChangedEvent(workload, triggerEvent.getIntervallCount(), triggerEvent.getIntervallDuratioInSeconds());
        
    }

}
