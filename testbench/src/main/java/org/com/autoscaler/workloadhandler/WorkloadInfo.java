package org.com.autoscaler.workloadhandler;

import org.com.autoscaler.util.MathUtil;

/**
 * Class to encapsulate any specific Information about a current workload
 * 
 * @author Niko
 *
 */
public class WorkloadInfo {
   

    /*
     * Arrival Rate in requests per second!
     */
    private double arrivalRateInTasksPerMilliSecond;

    /*
     * Amount of requests per time interval used by the clock
     */
    private int arrivalRateInTasksPerIntervall;

    /*
     * interval duration provided by clock
     */
    private double intervallDurationInMilliSeconds;

    public WorkloadInfo(int requestsPerIntervall, double intervallDurationInMilliSeconds) {
        this.arrivalRateInTasksPerIntervall = requestsPerIntervall;
       
       this.intervallDurationInMilliSeconds = intervallDurationInMilliSeconds;
        
        this.arrivalRateInTasksPerMilliSecond = MathUtil.tasksPerIntervallInTasksPerMillisecond(requestsPerIntervall,
                intervallDurationInMilliSeconds);
    }

    public double getArrivalRateInTasksPerMilliSecond() {
        return arrivalRateInTasksPerMilliSecond;
    }

    public int getArrivalRateInTasksPerIntervall() {
        return arrivalRateInTasksPerIntervall;
    }

    public double getIntervallDurationInMilliSeconds() {
        return intervallDurationInMilliSeconds;
    }

}
