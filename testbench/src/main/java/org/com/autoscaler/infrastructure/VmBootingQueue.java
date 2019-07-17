package org.com.autoscaler.infrastructure;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.com.autoscaler.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the Virtual Machines that were selected by the autoscaler to start
 * but are still booting.
 * 
 * @author Niko
 *
 */
public class VmBootingQueue {
    
    private static final Logger log = LoggerFactory.getLogger(VmBootingQueue.class);

    /*
     * The Integer value represents the amount of clock ticks the virtual machine
     * needs for booting
     */
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
            log.info("Add VM to Booting queue. Needs to wait " + vm.getVmStartUpTimeInClockIntervals() + " intervals to boot" );
            bootingQueue.add(new Pair<Integer, VirtualMachine>(vm.getVmStartUpTimeInClockIntervals(), vm));
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

    /**
     * Each clock tick, this method gets triggered so that the amount of clock ticks
     * until startup is reduced by 1
     */
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
