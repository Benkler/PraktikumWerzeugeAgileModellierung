package org.com.autoscaler.events;

/**
 * Indicates the start of the simulation
 * @author Niko
 *
 */
public class StartSimulationEvent extends AbstractEvent{

    public StartSimulationEvent(Object source, int clockTickCount, double intervalDurationInMilliSeconds) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = -2170806723970150998L;

}
