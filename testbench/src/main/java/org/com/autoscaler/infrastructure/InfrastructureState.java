package org.com.autoscaler.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.com.autoscaler.util.MathUtil;
import org.com.autoscaler.workloadhandler.WorkloadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to bundle Infrastructure Information. Not used for publishing state.
 * 
 * @author Niko
 *
 */
public class InfrastructureState {

 

    private static final int MILLIS = 1000;

    private final Logger log = LoggerFactory.getLogger(InfrastructureState.class);

    private final double intervallDurationInMilliSeconds;

    /*
     * Represents the amount of tasks ALL VMs are able to process during a clock
     * intervall
     */
    private int currentCapacityInTasksPerInterval;

    /*
     * Represents the amount of tasks ALL VMs are able to process per second
     */
    private double currentCapacityInTasksPerMilliSecond;

    /*
     * Represents the currentArrivalRate
     */
    private int currentArrivalRateInTasksPerIntervall;

    /*
     * Represents the currentArrivalRate
     */
    private double currentArrivalRateInTasksPerMilliSecond;

    /*
     * Holds currentInformation about virtual Machines
     */
    private HashMap<Integer, VirtualMachine> virtualMachines;

    public InfrastructureState( HashMap<Integer, VirtualMachine> virtualMachines,
            double intervallDurationInMilliSeconds) {

       


        this.intervallDurationInMilliSeconds = intervallDurationInMilliSeconds;
        this.virtualMachines = virtualMachines;
        this.currentArrivalRateInTasksPerIntervall = 0;
        this.currentArrivalRateInTasksPerMilliSecond = 0;

        calculateCurrentCapacity();
    }

    public double getIntervallDurationInMilliSeconds() {
        return intervallDurationInMilliSeconds;
    }

    public double getCurrentArrivalRateInTasksPerMilliSecond() {
        return currentArrivalRateInTasksPerMilliSecond;
    }

    public int getCurrentArrivalRateInTasksPerIntervall() {
        return currentArrivalRateInTasksPerIntervall;
    }

    /**
     * Remove set of virtual machines from infrastructure. In case the given vms are
     * not present no action is taken but an error message is displayed in the log
     * files
     * 
     * @param virtualMachines
     */
    public void scaleDownVirtualMachines(List<VirtualMachine> virtualMachines) {

        for (VirtualMachine vm : virtualMachines) {

            if (this.virtualMachines.remove(vm.getId()) == null) {
                log.error("Error while scaling down vm with id " + vm.getId() + ". Vm not present!");
                throw new AssertionError("Error while scaling down vm with id " + vm.getId() + ". Vm not present!");
            } else {
                log.info("Successfully scaled down vm with id " + vm.getId());
            }

        }

        calculateCurrentCapacity();
    }

    /**
     * Add new virtual machines to infrastructure. In case a vm is already present,
     * no action is taken but an error message in the logfiles
     * 
     * @param virtualMachines
     */
    public void scaleUpVirtualMachines(List<VirtualMachine> virtualMachines) {

        for (VirtualMachine vm : virtualMachines) {

            if (this.virtualMachines.containsKey(vm.getId())) {
                log.error("Error while scale up vm with id " + vm.getId() + ". Duplicated Id!");
                throw new AssertionError("Error while scale up vm with id " + vm.getId() + ". Duplicated Id!");
            } else {
                this.virtualMachines.put(vm.getId(), vm);
                log.info("Successfully scaled up vm with id " + vm.getId());
            }

        }

        calculateCurrentCapacity();

    }

    // public void

    public HashMap<Integer, VirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }



    public double getCurrentCapacityInTasksPerMilliSecond() {
        return currentCapacityInTasksPerMilliSecond;
    }

    public void setWorkload(WorkloadInfo info) {

        currentArrivalRateInTasksPerIntervall = info.getArrivalRateInTasksPerIntervall();
        currentArrivalRateInTasksPerMilliSecond = info.getArrivalRateInTasksPerMilliSecond();
    }

    public int getCurrentCapacityInTasksPerInterval() {
        return currentCapacityInTasksPerInterval;
    }

    /*
     * Recalculate the current capacity based on the available virtual machines
     */
    private void calculateCurrentCapacity() {
        currentCapacityInTasksPerInterval = 0;
        currentCapacityInTasksPerMilliSecond = 0;

        for (VirtualMachine vm : virtualMachines.values()) {
            currentCapacityInTasksPerInterval += vm.getTasksPerClockInterval();
        }

        
        currentCapacityInTasksPerMilliSecond = MathUtil.tasksPerIntervallInTasksPerMillisecond(
                currentCapacityInTasksPerInterval, intervallDurationInMilliSeconds);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Current State of the Infrastructure: \n");
 
        sb.append("intervallDurationInMilliSeconds: " + intervallDurationInMilliSeconds + "\n");
        sb.append("virtualMachines: " + virtualMachines.toString() + "\n");
        sb.append("currentArrivalRateInTasksPerIntervall: " + currentArrivalRateInTasksPerIntervall + "\n");
        sb.append("currentArrivalRateInTasksPerSecond: " + currentArrivalRateInTasksPerMilliSecond + "\n");

        return sb.toString();
    }

}
