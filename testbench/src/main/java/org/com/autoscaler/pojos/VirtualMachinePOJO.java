package org.com.autoscaler.pojos;

/**
 * Represents virtual machine info. For JSON deserialization. 
 * @author Niko
 *
 */
public class VirtualMachinePOJO {

    private int id;
    private int tasksPerIntervall;
    private int vmStartUpTime;

    public int getId() {
        return id;
    }

    public int getTasksPerIntervall() {
        return tasksPerIntervall;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTasksPerIntervall(int tasksPerIntervall) {
        this.tasksPerIntervall = tasksPerIntervall;
    }
    
   
    public void setVmStartUpTime(int vmStartUpTime) {
        this.vmStartUpTime = vmStartUpTime;
    }
    

    public int getVmStartUpTime() {
        return vmStartUpTime;
    }


    public VirtualMachinePOJO() {
        super();
    }

    @Override
    public String toString() {
        String output = " Id: " + id +"\n tasks per intervall: " + tasksPerIntervall +"\n cm startuptime: " + vmStartUpTime;
        return output;
    }
}
