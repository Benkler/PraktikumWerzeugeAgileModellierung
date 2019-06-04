package org.com.autoscaler.workloadhandler;

import org.com.autoscaler.events.TriggerWorkloadHandlerEvent;

public interface IWorkloadHandler{
    
    public void processNewWorkloadInfo(TriggerWorkloadHandlerEvent triggeEvent);
    
    

}
