package org.com.autoscaler.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtil {
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
     
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static int tasksPerMillisecondInTasksPerIntervall(double tasksPerMillisecond, double intervallDurationInMilliseconds) {
        
       
        
        return   Math.round((float) (tasksPerMillisecond * intervallDurationInMilliseconds)); 
    }
    
    
    public static int millisecondsInClockTicks(double milliSeconds, double intervallDurationInMilliseconds) {
        
        return Math.round((float)(milliSeconds / intervallDurationInMilliseconds));
        
        
    }
    
   public static int millisecondsInClockTicksFloor(double milliSeconds, double intervallDurationInMilliseconds) {
        
        
        return (int) Math.floor(milliSeconds / intervallDurationInMilliseconds);
        
    }
    
    public static double tasksPerIntervallInTasksPerMillisecond(int tasksPerIntervall, double intervallDurationInMilliseconds) {
        
        
        return round((tasksPerIntervall / intervallDurationInMilliseconds), 2);
        
    }
}
