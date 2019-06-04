package org.com.autoscaler.events;

public class TriggerWorkloadHandlerEvent extends AbstractEvent {

    public TriggerWorkloadHandlerEvent(Object source, final int clockTickCount, final double intervalDurationInSeconds) {
        super(source, clockTickCount, intervalDurationInSeconds);
       
    }

    /**
     * 
     */
    private static final long serialVersionUID = -770167694526502644L;

}
