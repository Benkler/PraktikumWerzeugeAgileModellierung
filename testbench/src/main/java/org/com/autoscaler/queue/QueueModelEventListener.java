package org.com.autoscaler.queue;

import org.com.autoscaler.events.TriggerPublishQueueStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueModelEventListener implements IQueueModelEventListener {

    private final Logger log = LoggerFactory.getLogger(QueueModelEventListener.class);
    
    @Autowired
    private QueueModel queue;
    
    @Override
    public void handleTriggerPublishQueueStateEvent(TriggerPublishQueueStateEvent event) {
        log.info("handle trigger publish queue state event at clock tick: " + event.getClockTickCount());
        queue.publishQueueState(event);
        
    }

}
