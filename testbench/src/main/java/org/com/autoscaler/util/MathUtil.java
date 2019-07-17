package org.com.autoscaler.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public final class MathUtil {

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static int tasksPerMillisecondInTasksPerIntervall(double tasksPerMillisecond,
            double intervallDurationInMilliseconds) {

        return Math.round((float) (tasksPerMillisecond * intervallDurationInMilliseconds));
    }

    public static int millisecondsInClockTicks(double milliSeconds, double intervallDurationInMilliseconds) {

        return Math.round((float) (milliSeconds / intervallDurationInMilliseconds));

    }

    public static int millisecondsInClockTicksFloor(double milliSeconds, double intervallDurationInMilliseconds) {

        return (int) Math.floor(milliSeconds / intervallDurationInMilliseconds);

    }
    
    public static double clockTicksInMillisecond(double clockTicks, double intervalDurationInMilliSeconds) {
        
        return round(clockTicks*intervalDurationInMilliSeconds, 2);
    }

    public static double tasksPerIntervallInTasksPerMillisecond(int tasksPerIntervall,
            double intervallDurationInMilliseconds) {

        return round((tasksPerIntervall / intervallDurationInMilliseconds), 4);

    }
    
    /**
     * calculate tasks per milliseconds from tasksPerInterval (both as double for more precise calculation) 
     * @param tasksPerIntervall
     * @param intervallDurationInMilliseconds
     * @return
     */
    public static double tasksPerIntervallInTasksPerMillisecond(double tasksPerIntervall,
            double intervallDurationInMilliseconds) {

        return round((tasksPerIntervall / intervallDurationInMilliseconds), 4);

    }
    
    public static double scaleValue(double value, double scalingFactor) {

        return MathUtil.round(value / scalingFactor, 4);
    }

    public static int generateRandomInteger() {
        /*
         * Generate random id
         */
        Random rand = new Random();
        // zero out the sign bits
        int id = rand.nextInt() & Integer.MAX_VALUE;
        return id;
    }
}
