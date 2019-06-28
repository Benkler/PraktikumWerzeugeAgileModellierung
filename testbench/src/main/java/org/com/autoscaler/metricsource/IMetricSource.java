package org.com.autoscaler.metricsource;

import java.util.List;

import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.infrastructure.InfrastructureStateTransferObject;
import org.com.autoscaler.infrastructure.VirtualMachine;

public interface IMetricSource {
    
    
    public double getCPUUtilization();
    
    public int getQueueLength();

    public List<VirtualMachine> getServiceInstances();
    
    public InfrastructureStateTransferObject getInfrastructureState();
    
    public void  handleInfrastructureStateEvent(InfrastructureStateEvent event);
    
    public void initMetricSource(int cpuUtilWindow, int queueLengthWindow);
}