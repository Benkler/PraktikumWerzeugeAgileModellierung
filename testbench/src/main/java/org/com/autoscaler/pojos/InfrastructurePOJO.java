package org.com.autoscaler.pojos;

import java.util.List;

public class InfrastructurePOJO {

    private List<VirtualMachinePOJO> virtualMachines;

    private int vmMax;

    private int vmMin;

    private QueuePOJO queue;
    private int monitoringDelay;
    private int vmStartUpTime;

    public List<VirtualMachinePOJO> getVirtualMachines() {
        return virtualMachines;
    }
    
    public int getMonitoringDelay() {
        return monitoringDelay;
    }

    public void setMonitoringDelay(int monitoringDelay) {
        this.monitoringDelay = monitoringDelay;
    }

    public int getVmStartUpTime() {
        return vmStartUpTime;
    }

    public void setVmStartUpTime(int vmStartUpTime) {
        this.vmStartUpTime = vmStartUpTime;
    }

    public void setVirtualMachines(List<VirtualMachinePOJO> virtualMachines) {
        this.virtualMachines = virtualMachines;
    }

    public int getVmMax() {
        return vmMax;
    }

    public void setVmMax(int vmMax) {
        this.vmMax = vmMax;
    }

    public int getVmMin() {
        return vmMin;
    }

    public void setVmMin(int vmMin) {
        this.vmMin = vmMin;
    }

    public QueuePOJO getQueue() {
        return queue;
    }

    public void setQueue(QueuePOJO queue) {
        this.queue = queue;
    }

    public InfrastructurePOJO() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String output = " Queue: \n" + queue.toString() + " vmMax: " + vmMax + " \n vmMin: " + vmMin
                + "\n monitoringDelay: " + monitoringDelay + "\n vm startupTime: " + vmStartUpTime + "\n Virtual Machines: \n";
        sb.append(output);
        for(VirtualMachinePOJO vm : virtualMachines) {
            sb.append(vm.toString());
        }
        return sb.toString();
    }

}
