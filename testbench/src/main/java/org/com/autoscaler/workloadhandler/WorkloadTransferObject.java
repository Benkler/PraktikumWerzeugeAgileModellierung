package org.com.autoscaler.workloadhandler;

import java.util.List;

/**
 * Class that represents entire workload <br>
 * This class will be replaced, as soon as workload is loaded on the fly
 * 
 * @author Niko
 *
 */
public class WorkloadTransferObject {

    public WorkloadTransferObject(List<Integer> workflow) {
        this.workflow = workflow;
    }

    /**
     * Each entry represents the amount of tasks that arrive at the system as a
     * BURST at the beginning of an interval and needs to be processed in this interval! <br>
     * IMPORTANT: This is different to the WorklFlowPOJO which represents the total amount
     * of tasks that will be processed between two workload changes
     */
    private List<Integer> workflow;

    public List<Integer> getWorkflow() {
        return workflow;
    }

}
