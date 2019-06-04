package org.com.autoscaler.workloadhandler;

import org.com.autoscaler.events.TriggerWorkloadHandlerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The workload handler is triggered by the clock after a provided number of
 * clock ticks. <br>
 * When triggered, it reads the next workload as provided in the input file and
 * indicates the change of workload.
 * 
 * @author Niko
 *
 */
@Component
public class WorkloadHandler implements IWorkloadHandler {

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
        publisher.fireWorkloadChangedEvent(workload, triggerEvent.getClockTickCount(),
                triggerEvent.getIntervallDuratioInSeconds());

    }

}
