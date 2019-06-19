package org.com.autoscaler.events;

/**
 * Event to trigger infrastructure to publish current state
 * @author Niko
 *
 */
public class TriggerPublishInfrastructureStateEvent extends AbstractEvent {

    public TriggerPublishInfrastructureStateEvent(Object source, int clockTickCount,
            double intervalDurationInMilliSeconds) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = 5430881910543314382L;

}
