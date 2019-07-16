package org.com.autoscaler.pojos;

/**
 * Represents virtual machine info. For JSON deserialization. 
 * @author Niko
 *
 */
public class VirtualMachineTypePOJO {

    
    private double tasksPerMillisecond;
    private int vmStartUpTimeInMilliSeconds;

    public int getVmStartUpTimeInMilliSeconds() {
        return vmStartUpTimeInMilliSeconds;
    }

    public void setVmStartUpTimeInMilliSeconds(int vmStartUpTimeInMilliSeconds) {
        this.vmStartUpTimeInMilliSeconds = vmStartUpTimeInMilliSeconds;
    }

   
    public double getTasksPerMillisecond() {
        return tasksPerMillisecond;
    }

   

    public void setTasksPerMillisecond(int tasksPerMillisecond) {
        this.tasksPerMillisecond = tasksPerMillisecond;
    }
    
   
  


    public VirtualMachineTypePOJO() {
        super();
    }

    @Override
    public String toString() {
        String output = " VritualMachineType " +"\n tasks per Millisecond: " + tasksPerMillisecond +"\n cm startuptime in milliseconds: " + vmStartUpTimeInMilliSeconds;
        return output;
    }
}
