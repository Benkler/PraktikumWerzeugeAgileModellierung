package org.com.autoscaler.events;

/**
 * Workload Handler has to be triggered after a certain amount of clock ticks to
 * adapt the simulation to the new provided workload
 * 
 * @author Niko
 *
 */
public class TriggerWorkloadHandlerEvent extends AbstractEvent {

    public TriggerWorkloadHandlerEvent(Object source, final int clockTickCount,
            final double intervalDurationInMilliSeconds) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);

    }

    /**
     * 
     */
    private static final long serialVersionUID = -770167694526502644L;

}
