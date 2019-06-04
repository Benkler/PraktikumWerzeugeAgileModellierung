package org.com.autoscaler.workloadhandler;

import org.com.autoscaler.events.ClockEvent;

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
