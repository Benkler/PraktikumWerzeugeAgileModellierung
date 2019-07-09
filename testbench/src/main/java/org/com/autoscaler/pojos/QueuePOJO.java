package org.com.autoscaler.pojos;

/**
 * Represents queue info. For JSON deserialization. 
 * @author Niko
 *
 */
public class QueuePOJO {

   

    private int queueLengthMax;

  

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
        String output = " queueLengthMax: " + queueLengthMax ;
        return output;
    }

}
