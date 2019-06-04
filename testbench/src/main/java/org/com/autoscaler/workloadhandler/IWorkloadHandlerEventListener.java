package org.com.autoscaler.workloadhandler;

import org.com.autoscaler.events.TriggerWorkloadHandlerEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

/**
 * Interface to describe a listner that listens to any workload handler related
 * event
 * 
 * @author Niko
 *
 */
public interface IWorkloadHandlerEventListener {
  
    @EventListener
    public void handleTriggerWorkloadHandlerEvent(TriggerWorkloadHandlerEvent event);
}
