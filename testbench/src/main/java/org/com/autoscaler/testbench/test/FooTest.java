package org.com.autoscaler.testbench.test;

import org.com.autoscaler.util.MathUtil;

public class FooTest {

    public static void main(String[] args) {
        final int MILLIS = 1000;
        int requestsPerIntervall=50;
        double intervallDurationInMilliSeconds=99999;

        double arrivalRateInTasksPerSecond = ((double)requestsPerIntervall / intervallDurationInMilliSeconds * MILLIS);
        System.out.println(arrivalRateInTasksPerSecond);
       arrivalRateInTasksPerSecond = MathUtil.round(((double)requestsPerIntervall / intervallDurationInMilliSeconds * MILLIS), 2);
        
        System.out.println(arrivalRateInTasksPerSecond);

    }

}
