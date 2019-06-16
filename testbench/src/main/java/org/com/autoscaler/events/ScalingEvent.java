package org.com.autoscaler.events;

import java.util.List;



import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.util.ScalingMode;

public class ScalingEvent extends AbstractEvent{
    
    private List<VirtualMachine> virtualMachines;
    private ScalingMode mode;

    public ScalingEvent(Object source, int clockTickCount, double intervalDurationInMilliSeconds, List<VirtualMachine> virtualMachines, ScalingMode mode) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);
        this.virtualMachines = virtualMachines;
        this.mode = mode;
    }
    
    public ScalingEvent(Object source, ClockEvent event, List<VirtualMachine> virtualMachines, ScalingMode mode) {
        super(source, event.getClockTickCount(), event.getIntervallDuratioInMilliSeconds());
        this.virtualMachines = virtualMachines;
        this.mode = mode;
    }
    
   
    /**
     * Either SCALE_UP or SCALE_DOWN
     * @return
     */
    public ScalingMode getMode() {
        return mode;
    }



    /**
     * 
     */
    private static final long serialVersionUID = -5843945289396025484L;

    public List<VirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }
    
    

}
