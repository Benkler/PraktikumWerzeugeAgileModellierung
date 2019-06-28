package org.com.autoscaler.testbench;

import java.util.LinkedList;
import java.util.List;

import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.infrastructure.VmBootingQueue;
import org.com.autoscaler.util.Pair;
import org.junit.Before;
import org.junit.Test;


public class VmBootingQueueTest {
    
    VmBootingQueue queue;
    
    @Before
    public void init() {
        queue = new VmBootingQueue();
        
        VirtualMachine vm1 = new VirtualMachine(1, 12, 10);
        VirtualMachine vm2 = new VirtualMachine(2, 12, 5);
        VirtualMachine vm3 = new VirtualMachine(3, 12, 2);
        List<VirtualMachine> vmList = new LinkedList<VirtualMachine>();
        vmList.add(vm1);
        vmList.add(vm2);
        vmList.add(vm3);
        
        queue.addVirtualMachinesToQueue(vmList);
    }
    
    @Test
    public void vmBootingQueueTest() {
        
        
        assert(queue.getCurrentBootingVms().size()==3);
        queue.reduceWaitingAmount(); 
        assert(queue.selectAndRemoveBootedVmsFromQueue().size() ==0);
        
        queue.reduceWaitingAmount(); 
        assert(queue.selectAndRemoveBootedVmsFromQueue().size() ==1);
        assert(queue.getCurrentBootingVms().size()==2);
        
        queue.reduceWaitingAmount();
        queue.reduceWaitingAmount(); 
        assert(queue.selectAndRemoveBootedVmsFromQueue().size() ==0);
        assert(queue.getCurrentBootingVms().size()==2);
        
        queue.reduceWaitingAmount(); 
        assert(queue.selectAndRemoveBootedVmsFromQueue().size() ==1);
        assert(queue.getCurrentBootingVms().size()==1);
        
        queue.reduceWaitingAmount(); 
        queue.reduceWaitingAmount();
        queue.reduceWaitingAmount(); 
        queue.reduceWaitingAmount();
        queue.reduceWaitingAmount(); 
        
        
        assert(queue.selectAndRemoveBootedVmsFromQueue().size() ==1);
        assert(queue.getCurrentBootingVms().size()==0);
        
        

        
        queue.reduceWaitingAmount();
        assert(queue.selectAndRemoveBootedVmsFromQueue().size() ==0);
        assert(queue.getCurrentBootingVms().size()==0);
        
        
        
        
        
    }

}
