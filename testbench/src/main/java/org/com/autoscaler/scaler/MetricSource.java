package org.com.autoscaler.scaler;

import java.util.List;

import org.com.autoscaler.infrastructure.IInfrastructureModel;
import org.com.autoscaler.infrastructure.InfrastructureStateTransferObject;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Proactive metric controller
 * 
 * @author Niko
 *
 */
@Component
public class MetricSource implements IMetricSource {

    Logger log = LoggerFactory.getLogger(MetricSource.class);

    @Autowired
    IInfrastructureModel infrastructure;

    /**
     * > 1: over provisioning ==1: optimal resource utilization < 1: under
     * provisioning
     * 
     */
    @Override
    public double getValue() {

        InfrastructureStateTransferObject state = infrastructure.getInfrastructureState();

        double capacityDiscrepancy = (double)state.getCurrentArrivalRateInTasksPerIntervall()
                / state.getCurrentCapacityInTasksPerIntervall();
        log.info("CurrentArrivalRate: " + state.getCurrentArrivalRateInTasksPerIntervall() + " currentCapacity: "
                + state.getCurrentCapacityInTasksPerIntervall() + "  currentDiscrepancy: " + capacityDiscrepancy);
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
