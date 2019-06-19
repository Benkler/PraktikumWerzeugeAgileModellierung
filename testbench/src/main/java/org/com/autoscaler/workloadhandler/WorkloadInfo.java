package org.com.autoscaler.workloadhandler;

/**
 * Class to encapsulate any specific Information about a current workload
 * @author Niko
 *
 */
public class WorkloadInfo {
    private static final int MILLIS = 1000;
    
    /*
     * Arrival Rate in requests per second!
     */
    private int arrivalRateInTasksPerSecond; 
    
    /*
     *Amount of requests per time interval used by the clock 
     */
    private int arrivalRateInTasksPerIntervall;
    
    
    /*
     * interval duration provided by clock 
     */
    private double intervallDurationInMilliSeconds;


    public WorkloadInfo(int requestsPerIntervall, double intervallDurationInMilliSeconds) {
       this.arrivalRateInTasksPerIntervall = requestsPerIntervall;
       this.intervallDurationInMilliSeconds = intervallDurationInMilliSeconds;
      this.arrivalRateInTasksPerSecond = Math.round((float)(requestsPerIntervall/intervallDurationInMilliSeconds * MILLIS));
               
    }


    public int getArrivalRateInTasksPerSecond() {
        return arrivalRateInTasksPerSecond;
    }


    public int getArrivalRateInTasksPerIntervall() {
        return arrivalRateInTasksPerIntervall;
    }


    public double getIntervallDurationInMilliSeconds() {
        return intervallDurationInMilliSeconds;
    }
    
    
    
    
    

}
