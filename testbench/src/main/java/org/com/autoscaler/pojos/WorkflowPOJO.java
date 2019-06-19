package org.com.autoscaler.pojos;

import java.util.List;

/**
 * Represents workflow info. For JSON deserialization. 
 * @author Niko
 *
 */
public class WorkflowPOJO {
    
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
