package org.com.autoscaler.events;

/**
 * Event that is fired whenever the autoscaler needs to be triggered
 * @author Niko
 *
 */
public class TriggerAutoScalerEvent extends AbstractEvent {

    public TriggerAutoScalerEvent(Object source, int clockTickCount, double intervalDurationInSeconds) {
        super(source, clockTickCount, intervalDurationInSeconds);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = -4359407484413396462L;

}
