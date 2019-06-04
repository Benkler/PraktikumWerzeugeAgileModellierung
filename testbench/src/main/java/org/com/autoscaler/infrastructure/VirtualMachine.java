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
     * Represents the amount of tasks a VM is able to process per second
     */
    private int tasksPerSecond;
    
    /*
     * The (unique) id of a VM <br> 
     * If persisted, this can be changed to uuid
     */
    private int id;

    public VirtualMachine(int id, int tasksPerClockInterval, int taskPersecond) {
        this.id = id;
       this.tasksPerClockInterval = tasksPerClockInterval;
       this.tasksPerSecond = taskPersecond;
    }

    public int getTasksPerClockInterval() {
        return tasksPerClockInterval;
    }

    public int getTasksPerSecond() {
        return tasksPerSecond;
    }
    
    public int getId() {
        return id;
    }
    
    

}
