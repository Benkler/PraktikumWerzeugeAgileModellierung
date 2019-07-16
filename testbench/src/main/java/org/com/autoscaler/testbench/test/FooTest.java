package org.com.autoscaler.testbench.test;

import org.com.autoscaler.util.MathUtil;

public class FooTest {

    public static void main(String[] args) {
        double tasksPerMillisecond = 3.6;
        double intervallDurationInMilliseconds = 100;
        
        
        double milliSeconds = 50015;

        Math.round((float) (tasksPerMillisecond * intervallDurationInMilliseconds));

        //System.out.println(Math.round((float) (tasksPerMillisecond * intervallDurationInMilliseconds)));

      //  System.out.println(Math.round((float) (milliSeconds / intervallDurationInMilliseconds)));
        
        
        System.out.println(MathUtil.tasksPerIntervallInTasksPerMillisecond(915, 100));

    }

}
