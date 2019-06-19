package org.com.autoscaler.workloadhandler;

import java.util.List;

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

    private boolean init = false;

    private List<Integer> workflow;

    /**
     * Init Workload --> Later, this is not necessary, as the workload should be
     * read on the fly from file buffer
     */
    @Override
    public void initWorkloadHandler(WorkloadTransferObject workFlow) {
        if (init)
            return;
        this.workflow = workFlow.getWorkflow();
        init = true;
    }

    @Autowired
    public WorkloadHandler(IWorkloadHandlerEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Read new workload and propagate to publisher. <br>
     * For later use, reading the workload should happen on the fly
     */
    @Override
    public void processNewWorkloadInfo(TriggerWorkloadHandlerEvent triggerEvent) {
        
        int requestsPerIntervall = 0;
        if (workflow == null) {
            throw new IllegalArgumentException("Workflow has to be initialized");
        }

        if (workflow.isEmpty()) {
            requestsPerIntervall = 0;
        }else {
            //Read and remove the first element which is the next workload
            requestsPerIntervall = workflow.get(0);
            workflow.remove(0);
        }

        WorkloadInfo workload = new WorkloadInfo(requestsPerIntervall,
                triggerEvent.getIntervallDuratioInMilliSeconds());
        publisher.fireWorkloadChangedEvent(workload, triggerEvent.getClockTickCount(),
                triggerEvent.getIntervallDuratioInMilliSeconds());

    }

}
