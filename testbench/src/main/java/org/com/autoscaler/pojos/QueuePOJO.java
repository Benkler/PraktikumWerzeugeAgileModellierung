package org.com.autoscaler.pojos;

/**
 * Represents queue info. For JSON deserialization.
 * 
 * @author Niko
 *
 */
public class QueuePOJO {

    private int queueLengthMax;

    private int windowSize;

    private int queuingDelay;

    public QueuePOJO() {
        super();
    }

    public int getQueueLengthMax() {
        return queueLengthMax;
    }

    public void setQueueLengthMax(int queueLengthMax) {
        this.queueLengthMax = queueLengthMax;
    }

    @Override
    public String toString() {
        String output = " \nqueueLengthMax: " + queueLengthMax
                + "\nwindowSize for average arrival and processing rate: " + windowSize + "\nQueuingDelay" + queuingDelay;
        return output;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getQueuingDelay() {
        return queuingDelay;
    }

    public void setQueuingDelay(int queuingDelay) {
        this.queuingDelay = queuingDelay;
    }

}
