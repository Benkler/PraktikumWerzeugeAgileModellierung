package org.com.autoscaler.infrastructure;

import org.com.autoscaler.pojos.VirtualMachineTypePOJO;
import org.com.autoscaler.util.MathUtil;

public class VirtualMachineType {

    private int tasksPerInterval;
    private int vmStartUpTimeInIntervals;

    public VirtualMachineType(VirtualMachineTypePOJO pojo, double intervallDurationInMilliSeconds, int millisecondsTillWorkloadChange) {
        
        int scalingFactor = millisecondsTillWorkloadChange;
        
        this.tasksPerInterval = MathUtil.tasksPerMillisecondInTasksPerIntervall((1/pojo.getMillisecondsPerTask())*scalingFactor,
                intervallDurationInMilliSeconds);
        
        
        

        this.vmStartUpTimeInIntervals = MathUtil.millisecondsInClockTicks(pojo.getVmStartUpTimeInMilliSeconds(), intervallDurationInMilliSeconds);
                
                
             
    }

    public int getTasksPerInterval() {
        return tasksPerInterval;
    }

   
    public int getVmStartUpTimeInIntervals() {
        return vmStartUpTimeInIntervals;
    }

   
   

    @Override
    public String toString() {
        String output = " VritualMachineType " + "\n tasks per Millisecond: " + tasksPerInterval
                + "\n cm startuptime in milliseconds: " + vmStartUpTimeInIntervals;
        return output;
    }

}
