package org.com.autoscaler.pojos;

public class QueuePOJO {

    private int queueLengthMin;

    private int queueLengthMax;

    public int getQueueLengthMin() {
        return queueLengthMin;
    }

    public void setQueueLengthMin(int queueLengthMin) {
        this.queueLengthMin = queueLengthMin;
    }

    public int getQueueLengthMax() {
        return queueLengthMax;
    }

    public void setQueueLengthMax(int queueLengthMax) {
        this.queueLengthMax = queueLengthMax;
    }

    public QueuePOJO() {
        super();
    }
    
    @Override
    public String toString() {
        String output = " queueLengthMax: " + queueLengthMax +"\n queueLengthMin: " + queueLengthMin +"\n";
        return output;
    }

}
