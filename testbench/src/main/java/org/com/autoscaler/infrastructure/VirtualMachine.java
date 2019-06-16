package org.com.autoscaler.infrastructure;

/**
 * Simple entity that represents a Virtual Machine and the amount of task it is
 * able to handle per clock intervall or per second, depending of which information the user provides
 * 
 * @author Niko
 *
 */
public class VirtualMachine {

    /*
     * Represents the amount of tasks a VM is able to process during a clock
     * intervall
     */
    private int tasksPerClockInterval;

   
    
    /*
     * The (unique) id of a VM <br> 
     * If persisted, this can be changed to uuid 
     */
    private int id;

    public VirtualMachine(int id, int tasksPerClockInterval) {
        this.id = id;
       this.tasksPerClockInterval = tasksPerClockInterval;
       
    }

    public int getTasksPerClockInterval() {
        return tasksPerClockInterval;
    }

   
    
    public int getId() {
        return id;
    }
    
    

}
