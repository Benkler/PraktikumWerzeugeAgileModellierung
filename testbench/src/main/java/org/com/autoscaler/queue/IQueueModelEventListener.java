package org.com.autoscaler.queue;

import org.com.autoscaler.events.TriggerPublishQueueStateEvent;
import org.springframework.context.event.EventListener;

public interface IQueueModelEventListener {
    
    
    @EventListener
    public void handleTriggerPublishQueueStateEvent(TriggerPublishQueueStateEvent event);

}
