package org.com.autoscaler.workloadhandler;

/**
 * Interface that describes a publisher of any workload handler related event 
 * @author Niko
 *
 */
public interface IWorkloadHandlerEventPublisher {
    
    /**
     * Fire event containing the changed workload information
     * @param workload
     */
    public void fireWorkloadChangedEvent(WorkloadInfo workload, int clockTickCount, double intervalDurationInSeconds);

}
