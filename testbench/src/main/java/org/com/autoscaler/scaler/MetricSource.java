package org.com.autoscaler.scaler;

import java.util.List;

import org.com.autoscaler.infrastructure.IInfrastructureModel;
import org.com.autoscaler.infrastructure.InfrastructureStateTransferObject;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Proactive metric controller
 * @author Niko
 *
 */
@Component
public class MetricSource implements IMetricSource{
    
    @Autowired
    IInfrastructureModel infrastructure;
    
    /**
     *> 1: over provisioning 
     * ==1: optimal resource utilization
     * < 1: under provisioning
     * 
     */
    @Override
    public double getValue() {
        
        
        
        InfrastructureStateTransferObject state = infrastructure.getInfrastructureState();
        
       
        double capacityDiscrepancy =  state.getCurrentArrivalRateInTasksPerIntervall() / state.getCurrentCapacityInTasksPerIntervall();
        
        return capacityDiscrepancy;
    }

    @Override
    public List<VirtualMachine> getServiceInstances() {
       InfrastructureStateTransferObject state = infrastructure.getInfrastructureState();
        return state.getVirtualMachines();
    } 
    
    


    @Override
    public InfrastructureStateTransferObject getInfrastructureState() {
        return infrastructure.getInfrastructureState();
    }

}
