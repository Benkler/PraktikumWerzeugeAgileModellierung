package org.com.autoscaler.pojos;

public class VirtualMachinePOJO {

    private int id;
    private int tasksPerIntervall;

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

    public VirtualMachinePOJO() {
        super();
    }

    @Override
    public String toString() {
        String output = " Id: " + id +"\n tasks per intervall: " + tasksPerIntervall +"\n";
        return output;
    }
}
