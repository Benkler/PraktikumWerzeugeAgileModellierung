package org.com.autoscaler.events;

public class DiscardJobsEvent extends AbstractEvent{
    
    private int amountOfDiscardedTasks;

    public DiscardJobsEvent(Object source, int clockTickCount, double intervalDurationInMilliSeconds, int discardedTasks) {
        super(source, clockTickCount, intervalDurationInMilliSeconds);
       this.amountOfDiscardedTasks = discardedTasks;
    }

    public int getAmountOfDiscardedTasks() {
        return amountOfDiscardedTasks;
    }


    /**
     * 
     */
    private static final long serialVersionUID = -7384492851541134899L;

}
