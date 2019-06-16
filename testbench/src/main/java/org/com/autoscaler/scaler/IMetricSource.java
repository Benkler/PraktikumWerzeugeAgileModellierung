package org.com.autoscaler.scaler;

import java.util.List;

import org.com.autoscaler.infrastructure.InfrastructureStateTransferObject;
import org.com.autoscaler.infrastructure.VirtualMachine;

public interface IMetricSource {
    
    
    public double getValue();

    public List<VirtualMachine> getServiceInstances();
    
    public InfrastructureStateTransferObject getInfrastructureState();
}