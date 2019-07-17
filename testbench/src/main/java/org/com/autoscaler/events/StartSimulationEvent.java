package org.com.autoscaler.events;

/**
 * Indicates the start of the simulation
 * @author Niko
 *
 */
public class StartSimulationEvent extends AbstractEvent{
    
    private final double scalingFactor;

    public StartSimulationEvent(Object source, int clockTickCount, double intervalDurationInMilliSeconds, double scalingFactor) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);
        this.scalingFactor = scalingFactor;
        // TODO Auto-generated constructor stub
    }

    public double getScalingFactor() {
        return scalingFactor;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -2170806723970150998L;

}
