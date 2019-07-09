package org.com.autoscaler.events;

import org.com.autoscaler.queue.QueueStateTransferObject;

public class QueueStateEvent extends AbstractEvent {

    private final QueueStateTransferObject state;
    
    public QueueStateEvent(Object source, int clockTickCount, double intervalDurationInMilliSeconds, QueueStateTransferObject state) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);
        this.state = state;
    }

    public QueueStateTransferObject getState() {
        return state;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -1218539074407060951L;

}
