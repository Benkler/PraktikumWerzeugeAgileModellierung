package org.com.autoscaler.infrastructure;

import java.util.List;

import org.com.autoscaler.workloadhandler.WorkloadInfo;

/**
 * Class to bundle Infrastructure Information
 * 
 * @author Niko
 *
 */
public class InfrastructureState {

    private final int minAmountVM;
    private final int maxAmountVM;
    
    private static final int MILLIS = 1000;
   
    private final double intervallDurationInMilliSeconds;

    /*
     * Represents the amount of tasks ALL VMs are able to process during a clock
     * intervall
     */
    private  int currentCapacityInTasksPerInterval;
    
    /*
     * Represents the amount of tasks ALL VMs are able to process per second
     */
    private int currentCapacityInTasksPerSecond; 
    
    /*
     * Represents the currentArrivalRate
     */
    private int currentArrivalRateInTasksPerIntervall;
    
    
    /*
     * Represents the currentArrivalRate
     */
    private int currentArrivalRateInTasksPerSecond;
    /*
     * Holds currentInformation about virtual Machines
     */
    private List<VirtualMachine> virtualMachines;

    public InfrastructureState(int minAmountVM, int maxAmountVM, List<VirtualMachine> virtualMachines,
           double intervallDurationInMilliSeconds) {

        if (minAmountVM < 0 || maxAmountVM < 0 || maxAmountVM < minAmountVM) {
            throw new IllegalArgumentException("Invalid parameters for minimum and maximum amount of virtual machines");
        }
        
       

        this.minAmountVM = minAmountVM;
        this.maxAmountVM = maxAmountVM;

        this.intervallDurationInMilliSeconds = intervallDurationInMilliSeconds;
        this.virtualMachines = virtualMachines;
        this.currentArrivalRateInTasksPerIntervall = 0;
        this.currentArrivalRateInTasksPerSecond = 0;
        calculateCurrentCapacity();
    }
    
   
    public double getIntervallDurationInMilliSeconds() {
        return intervallDurationInMilliSeconds;
    }


    public int getCurrentArrivalRateInTasksPerSecond() {
        return currentArrivalRateInTasksPerSecond;
    }


    public int getCurrentArrivalRateInTasksPerIntervall() {
        return currentArrivalRateInTasksPerIntervall;
    }


    public void setVirtualMachines(List<VirtualMachine> virtualMachines) {
        this.virtualMachines = virtualMachines;
        calculateCurrentCapacity();
    }

   

    public List<VirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }

   
    public int getMinAmountVM() {
        return minAmountVM;
    }

    public int getMaxAmountVM() {
        return maxAmountVM;
    }

   
    public int getCurrentCapacityInTasksPerSecond() {
        return currentCapacityInTasksPerSecond;
    }

 
    public void setWorkload(WorkloadInfo info) {
        
        currentArrivalRateInTasksPerIntervall = info.getArrivalRateInTasksPerIntervall();
        currentArrivalRateInTasksPerSecond = info.getArrivalRateInTasksPerSecond();
    }

   
    public int getCurrentCapacityInTasksPerInterval() {
        return currentCapacityInTasksPerInterval;
    }

    /* 
     * Recalculate the current capacity based on the available virtual machines
     */ 
    private void calculateCurrentCapacity() {
        currentCapacityInTasksPerInterval = 0;
        currentCapacityInTasksPerSecond = 0;

        for (VirtualMachine vm : virtualMachines) {
            currentCapacityInTasksPerInterval += vm.getTasksPerClockInterval();
        }
        
       currentCapacityInTasksPerSecond = Math.round((float)(currentCapacityInTasksPerInterval * MILLIS / intervallDurationInMilliSeconds));
      
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
       sb.append("Current State of the Infrastructure: \n");
       sb.append("minAmountVM: " + minAmountVM + "\n");
       sb.append("maxAmountVM: " + maxAmountVM + "\n");
       sb.append("intervallDurationInMilliSeconds: " + intervallDurationInMilliSeconds + "\n");
       sb.append("virtualMachines: " + virtualMachines.toString() + "\n");
       sb.append("currentArrivalRateInTasksPerIntervall: " + currentArrivalRateInTasksPerIntervall + "\n");
       sb.append("currentArrivalRateInTasksPerSecond: " + currentArrivalRateInTasksPerSecond + "\n");
       
       

        
        
        return sb.toString();
    }

}
