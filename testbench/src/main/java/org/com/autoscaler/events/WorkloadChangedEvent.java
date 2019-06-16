package org.com.autoscaler.events;

import org.com.autoscaler.workloadhandler.WorkloadInfo;

/**
 * Class that holds information about the new workload
 * @author Niko
 *
 */
public class WorkloadChangedEvent extends AbstractEvent {

    private WorkloadInfo workloadInfo;
    
    public WorkloadChangedEvent(Object source, WorkloadInfo workloadInfo, int clockTickCount, double intervalDurationInMilliSeconds) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);
        this.workloadInfo = workloadInfo;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -4643953059386563320L;

    public WorkloadInfo getWorkloadInfo() {
        return workloadInfo;
    }

}
