package org.com.autoscaler.pojos;

/**
 * Represents queue info. For JSON deserialization. 
 * @author Niko
 *
 */
public class QueuePOJO {

   

    private int queueLengthMax;
    
   private int windowSize;

  

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
        String output = " queueLengthMax: " + queueLengthMax + "windowSize for average arrival and processing rate: " + windowSize;
        return output;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

}
