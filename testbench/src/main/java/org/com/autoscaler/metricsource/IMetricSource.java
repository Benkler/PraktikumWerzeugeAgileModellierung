package org.com.autoscaler.metricsource;

import java.util.List;

import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.QueueStateEvent;
import org.com.autoscaler.infrastructure.InfrastructureStateTransferObject;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.infrastructure.VirtualMachineType;

public interface IMetricSource {
    
    
    public double getCPUUtilization();
    
    public int getQueueLength();

    public List<VirtualMachine> getServiceInstances();
    
    public InfrastructureStateTransferObject getInfrastructureState();
    
    public void  handleInfrastructureStateEvent(InfrastructureStateEvent event);
    
    public void handleQueueStateEvent(QueueStateEvent event);
    
    public VirtualMachineType getVirtualMachineType();
    
    public void initMetricSource(int cpuUtilWindow, int queueLengthWindow);
}