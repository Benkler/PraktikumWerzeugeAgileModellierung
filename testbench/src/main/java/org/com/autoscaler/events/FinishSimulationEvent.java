package org.com.autoscaler.events;

/**
 * Indicates the end of the simulation
 * @author Niko
 *
 */
public class FinishSimulationEvent extends AbstractEvent {

    public FinishSimulationEvent(Object source, int clockTickCount, double intervalDurationInMilliSeconds) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = 8107877521936508789L;

}
