package org.com.autoscaler.events;

/**
 * Standard clock event indicating a new interval. <br>
 * intervallDuration is always transmitted although it does not change after clock initialization
 * @author Niko
 *
 */
public class ClockEvent extends AbstractEvent {

    public ClockEvent(Object source, int intervallCount, double intervalDurationInSeconds) {
        super(source, intervallCount, intervalDurationInSeconds);
        
    }

    /**
     * 
     */
    private static final long serialVersionUID = 3974412473659778916L;

}
