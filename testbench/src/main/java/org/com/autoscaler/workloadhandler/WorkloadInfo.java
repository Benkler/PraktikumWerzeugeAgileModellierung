package org.com.autoscaler.workloadhandler;

/**
 * Class to encapsulate any specific Information about a current workload
 * @author Niko
 *
 */
public class WorkloadInfo {
    
    public WorkloadInfo(int requestsPerIntervall, double intervallDurationInSeconds) {
       this.arrivalRateInTasksPerIntervall = requestsPerIntervall;
       this.intervallDurationInSeconds = intervallDurationInSeconds;
       this.arrivalRateInTasksPerSecond = requestsPerIntervall/intervallDurationInSeconds;
               
    }
    
    /*
     * Arrival Rate in requests per second!
     */
    private double arrivalRateInTasksPerSecond; 
    
    /*
     *Amount of requests per time interval used by the clock 
     */
    private int arrivalRateInTasksPerIntervall;
    
    
    /*
     * interval duration provided by clock 
     */
    private double intervallDurationInSeconds;


    public double getArrivalRateInTasksPerSecond() {
        return arrivalRateInTasksPerSecond;
    }


    public int getarrivalRateInTasksPerIntervall() {
        return arrivalRateInTasksPerIntervall;
    }
    
    
    

}
