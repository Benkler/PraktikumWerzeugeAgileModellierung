package org.com.autoscaler.infrastructure;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.com.autoscaler.util.Pair;

public class VmBootingQueue {

    private List<Pair<Integer, VirtualMachine>> bootingQueue;

    public VmBootingQueue() {
        bootingQueue = new LinkedList<Pair<Integer, VirtualMachine>>();
    }

    /**
     * Add VM to Booting Queue.
     * 
     * @return
     */
    public void addVirtualMachinesToQueue(List<VirtualMachine> vms) {

        for (VirtualMachine vm : vms) {
            bootingQueue.add(new Pair<Integer, VirtualMachine>(vm.getVmStartUpTime(), vm));
        }

    }

    /**
     * Return all Vms which are finally booted. This is the case if they where in
     * the queue for the start up time as specified in the virtual machine object
     * 
     * @return
     */
    public List<VirtualMachine> selectAndRemoveBootedVmsFromQueue() {
        List<VirtualMachine> bootedVms = new LinkedList<VirtualMachine>();

        for (int i = 0; i < bootingQueue.size(); i++) {
            Pair<Integer, VirtualMachine> pair = bootingQueue.get(i);
            if (pair.getL() <= 0) {
                bootedVms.add(pair.getR());
                bootingQueue.remove(i);
            }

        }

        return bootedVms;
    }

    public void reduceWaitingAmount() {
        List<Pair<Integer, VirtualMachine>> updatedQueue = new LinkedList<Pair<Integer, VirtualMachine>>();

        updatedQueue = bootingQueue.stream().map(x -> new Pair<Integer, VirtualMachine>(x.getL() - 1, x.getR()))
                .collect(Collectors.toList());
        bootingQueue = updatedQueue;
    }

    public List<Pair<Integer, VirtualMachine>> getCurrentBootingVms() {
        return bootingQueue;
    }

}
