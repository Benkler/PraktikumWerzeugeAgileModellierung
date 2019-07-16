package org.com.autoscaler.pojos;

/**
 * Represents virtual machine info. For JSON deserialization. 
 * @author Niko
 *
 */
public class VirtualMachinePOJO {

    private int id;
    private double tasksPerMillisecond;
    private int vmStartUpTimeInMilliSeconds;

    public int getVmStartUpTimeInMilliSeconds() {
        return vmStartUpTimeInMilliSeconds;
    }

    public void setVmStartUpTimeInMilliSeconds(int vmStartUpTimeInMilliSeconds) {
        this.vmStartUpTimeInMilliSeconds = vmStartUpTimeInMilliSeconds;
    }

    public int getId() {
        return id;
    }

    public double getTasksPerMillisecond() {
        return tasksPerMillisecond;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTasksPerMillisecond(int tasksPerMillisecond) {
        this.tasksPerMillisecond = tasksPerMillisecond;
    }
    
   
  


    public VirtualMachinePOJO() {
        super();
    }

    @Override
    public String toString() {
        String output = " Id: " + id +"\n tasks per Millisecond: " + tasksPerMillisecond +"\n cm startuptime in milliseconds: " + vmStartUpTimeInMilliSeconds;
        return output;
    }
}
