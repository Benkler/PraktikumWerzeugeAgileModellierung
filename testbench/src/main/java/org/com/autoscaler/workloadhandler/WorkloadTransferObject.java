package org.com.autoscaler.workloadhandler;

import java.util.List;

/**
 * Class that represents entire workload <br>
 * This class will be replaced, as soon as workload is loaded on the fly
 * @author Niko
 *
 */
public class WorkloadTransferObject {
    
    public WorkloadTransferObject(List<Integer> workflow) {
        this.workflow = workflow;
    }
    
    private List<Integer> workflow;

    public List<Integer> getWorkflow() {
        return workflow;
    }

   
    

}
