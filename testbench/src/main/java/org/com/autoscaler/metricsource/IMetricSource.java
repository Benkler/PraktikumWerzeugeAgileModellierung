package org.com.autoscaler.metricsource;

import java.util.List;

import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.QueueStateEvent;
import org.com.autoscaler.infrastructure.InfrastructureStateTransferObject;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.infrastructure.VirtualMachineType;

public interface IMetricSource {

    /**
     * Moving Average of CPU-Utilization
     * 
     */
    public double getCPUUtilization();

    /**
     * Moving Average of Queue-Length
     * 
     */
    public int getQueueLength();

    /**
     * Retrieve currently available List of active Virtual Machines
     * 
     */
    public List<VirtualMachine> getServiceInstances();

    /**
     * Return Infrastructure-State at a certain Clock-Interval
     * @return
     */
    public InfrastructureStateTransferObject getInfrastructureState();

    public void handleInfrastructureStateEvent(InfrastructureStateEvent event);

    public void handleQueueStateEvent(QueueStateEvent event);
    
    /**
     * Information of currently used VM-Type! For a Scaling decision, it is needed
     * to instantiate VMs with those parameters
     */
    public VirtualMachineType getVirtualMachineType();

    public void initMetricSource(int cpuUtilWindow, int queueLengthWindow);
}