package org.com.autoscaler.workloadhandler;

import org.com.autoscaler.events.WorkloadChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publishes any wokrload handler related events like the change of the current workload
 * @author Niko
 *
 */
@Component
public class WorkloadEventPublisher implements IWorkloadHandlerEventPublisher {

    private final Logger log = LoggerFactory.getLogger(WorkloadEventPublisher.class);

    @Autowired
    private ApplicationEventPublisher applEventPublisher;
   
    /**
     * Indicates, that the current wokrload has changed
     */
    @Override
    public void fireWorkloadChangedEvent(WorkloadInfo workload, int clockTickCount, double intervalDurationInSeconds) {
        log.info("Fire workload changed event. Arrival Rate In Tasks Per MilliSecond: " + workload.getArrivalRateInTasksPerMilliSecond()
                + " ; Arrival Rate in Tasks per Intervall: " + workload.getArrivalRateInTasksPerIntervall());

        WorkloadChangedEvent event = new WorkloadChangedEvent(this, workload, clockTickCount,
                intervalDurationInSeconds);
        applEventPublisher.publishEvent(event);

    }

}
