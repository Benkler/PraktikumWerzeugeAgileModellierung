package org.com.autoscaler.workloadhandler;

/**
 * Class to encapsulate any specific Information about a current workload
 * @author Niko
 *
 */
public class WorkloadInfo {
    
    public WorkloadInfo(int requestsPerIntervall, double intervallDurationInSeconds) {
       this.requestsPerIntervall = requestsPerIntervall;
       this.intervallDurationInSeconds = intervallDurationInSeconds;
       this.arrivalRate = requestsPerIntervall/intervallDurationInSeconds;
               
    }
    
    /*
     * Arrival Rate in requests per second!
     */
    private double arrivalRate; 
    
    /*
     *Amount of requests per time interval used by the clock 
     */
    private int requestsPerIntervall;
    
    
    /*
     * interval duration provided by clock 
     */
    private double intervallDurationInSeconds;


    public double getArrivalRate() {
        return arrivalRate;
    }


    public int getRequestsPerIntervall() {
        return requestsPerIntervall;
    }
    
    
    

}
