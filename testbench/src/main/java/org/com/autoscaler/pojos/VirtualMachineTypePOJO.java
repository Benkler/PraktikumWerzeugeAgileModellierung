package org.com.autoscaler.pojos;

/**
 * Represents virtual machine info. For JSON deserialization. 
 * @author Niko
 *
 */
public class VirtualMachineTypePOJO {

    
    private double millisecondsPerTask;
    private int vmStartUpTimeInMilliSeconds;

    public int getVmStartUpTimeInMilliSeconds() {
        return vmStartUpTimeInMilliSeconds;
    }

    public void setVmStartUpTimeInMilliSeconds(int vmStartUpTimeInMilliSeconds) {
        this.vmStartUpTimeInMilliSeconds = vmStartUpTimeInMilliSeconds;
    }

   
   
    
   
  


    public double getMillisecondsPerTask() {
        return millisecondsPerTask;
    }

    public void setMillisecondsPerTask(double millisecondsPerTask) {
        this.millisecondsPerTask = millisecondsPerTask;
    }

    public VirtualMachineTypePOJO() {
        super();
    }

    @Override
    public String toString() {
        String output = " VritualMachineType " +"\n tasks per Millisecond: " + millisecondsPerTask +"\n cm startuptime in milliseconds: " + vmStartUpTimeInMilliSeconds;
        return output;
    }
}
