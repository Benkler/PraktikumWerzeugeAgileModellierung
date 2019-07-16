package org.com.autoscaler.metricsource;

import java.util.IntSummaryStatistics;
import java.util.List;

import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.QueueStateEvent;
import org.com.autoscaler.infrastructure.IInfrastructureModel;
import org.com.autoscaler.infrastructure.InfrastructureStateTransferObject;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.infrastructure.VirtualMachineType;
import org.com.autoscaler.util.MovingAverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Reactive metric source provider that listens to Infrastructure Events and
 * generates moving average. Autoscaler or any other interested component can
 * request cpu utilization or queue length (both as moving average)
 * 
 * @author Niko
 *
 */
@Component
public class MetricSource implements IMetricSource {

    Logger log = LoggerFactory.getLogger(MetricSource.class);

    private MovingAverage<Double> cpuUtilizationAverage;
    private MovingAverage<Integer> queueLengthAverage;

    private boolean init = false;

    @Override
    public void initMetricSource(int cpuUtilWindow, int queueLengthWindow) {
        if (!init) {
            init = true;
            cpuUtilizationAverage = new MovingAverage<Double>(cpuUtilWindow);
            queueLengthAverage = new MovingAverage<Integer>(queueLengthWindow);
        }

    }

    @Autowired
    IInfrastructureModel infrastructure;

    /**
     * > 1: over provisioning ==1: optimal resource utilization < 1: under
     * provisioning
     * 
     */
    @Override
    public double getCPUUtilization() {

        return cpuUtilizationAverage.average();
    }

    @Override
    public List<VirtualMachine> getServiceInstances() {
        InfrastructureStateTransferObject state = getInfrastructureState();
        return state.getVirtualMachines();
    }

    @Override
    public InfrastructureStateTransferObject getInfrastructureState() {
        return infrastructure.getInfrastructureState();
    }

    /**
     * Moving average of queue length
     */
    @Override
    public int getQueueLength() {
        return (int) Math.floor(queueLengthAverage.average());

    }

    @Override
    public void handleInfrastructureStateEvent(InfrastructureStateEvent event) {
  
        
        cpuUtilizationAverage.add(event.getInfrastructureState().getCurrentCPUUtilization());
        log.info("CurrentArrivalRate: " + event.getInfrastructureState().getCurrentArrivalRateInTasksPerIntervall()
                + " currentCapacity: " + event.getInfrastructureState().getCurrentCapacityInTasksPerIntervall()
                + "  moving average discrepancy : " + cpuUtilizationAverage.average());

       

    }

    @Override
    public void handleQueueStateEvent(QueueStateEvent event) {
        // Update queue Length
        queueLengthAverage.add(event.getState().getTasksInQueue());
        log.info("Moving average queue length: " + queueLengthAverage.average());
        
    }

    @Override
    public VirtualMachineType getVirtualMachineType() {
        return infrastructure.getVirtualMachineType();
    }

}
