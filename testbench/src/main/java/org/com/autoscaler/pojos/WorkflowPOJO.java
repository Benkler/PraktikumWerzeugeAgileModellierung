package org.com.autoscaler.pojos;

import java.util.List;

/**
 * Represents workflow info. For JSON deserialization.
 * 
 * @author Niko
 *
 */
public class WorkflowPOJO {

    /**
     * Each entry represents the amount of tasks that the system needs to process
     * between two workload changes!
     */
    private List<Integer> workflow;

    public List<Integer> getWorkflow() {
        return workflow;
    }

    public void setWorkflow(List<Integer> workFlow) {
        this.workflow = workFlow;
    }

    public WorkflowPOJO() {
        super();
    }

}
