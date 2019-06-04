package org.com.autoscaler.workloadhandler;

import org.com.autoscaler.events.TriggerWorkloadHandlerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class listens to any workload handler related event
 * @author Niko
 *
 */
@Component
public class WorkloadHandlerEventListener implements IWorkloadHandlerEventListener{

    @Autowired
    IWorkloadHandler handler;
    
    @Override
    public void onApplicationEvent(TriggerWorkloadHandlerEvent event) {
        handler.processNewWorkloadInfo(event);
        
    }

}
