package org.com.autoscaler.events;

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
