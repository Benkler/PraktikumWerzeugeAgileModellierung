package org.com.autoscaler.events;

import org.springframework.context.ApplicationEvent;

/**
 * Abstract Event: Each Event that is thrown in this application needs to extend
 * this event to guarantee, that current intervallCount and interval duration is always available
 * 
 * @author Niko
 *
 */
public abstract class AbstractEvent extends ApplicationEvent {

    private final int clockTickCount;
    private final double intervalDuratioInSeconds;

    public AbstractEvent(Object source, final int clockTickCount, final double intervalDurationInSeconds) {
        super(source);
        this.clockTickCount = clockTickCount;
        this.intervalDuratioInSeconds = intervalDurationInSeconds;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1763084861796216090L;

    public int getIntervallCount() {
        return clockTickCount;
    }

    public double getIntervallDuratioInSeconds() {
        return intervalDuratioInSeconds;
    }

}
