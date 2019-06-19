package org.com.autoscaler.infrastructure;

/**
 * Simple entity that represents a Virtual Machine and the amount of task it is
 * able to handle per clock intervall or per second, depending of which
 * information the user provides
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
     * The (unique) id of a VM <br> If persisted, this can be changed to uuid
     */
    private int id;

    /*
     * Startup time this particular VM needs to start
     */
    private int vmStartUpTime;

    public VirtualMachine(int id, int tasksPerClockInterval, int vmStartUpTime) {
        this.id = id;
        this.tasksPerClockInterval = tasksPerClockInterval;
        this.vmStartUpTime = vmStartUpTime;

    }

    public int getTasksPerClockInterval() {
        return tasksPerClockInterval;
    }

    public int getId() {
        return id;
    }

    public int getVmStartUpTime() {
        return vmStartUpTime;
    }

    @Override
    public String toString() {

       return "Virtual Machine: \n id: " + id + "\n tasks per clock interval: " + tasksPerClockInterval
                + "\n vm startup time: " + vmStartUpTime + "\n";

           }

}
