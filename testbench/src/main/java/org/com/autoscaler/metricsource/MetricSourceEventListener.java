package org.com.autoscaler.metricsource;

import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.QueueStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class MetricSourceEventListener implements IMetricSourceEventListener{

    @Autowired
    IMetricSource metricSource;
    
    Logger log = LoggerFactory.getLogger(MetricSourceEventListener.class);
    
    
    @Override
    public void handleInfrastructureStateEvent(InfrastructureStateEvent event) {
        log.info("Handle infrastructure state event at clock tick " + event.getClockTickCount());
        metricSource.handleInfrastructureStateEvent(event);
        
    }


    @Override
    public void handleQueueStateEvent(QueueStateEvent event) {
        // TODO Auto-generated method stub
        
    }

}
