package org.com.autoscaler.util;

import java.util.LinkedList;

/**
 * UtilityClass to represent moving average queue with given window size
 * @author Niko
 *
 * @param <T>
 */
public class MovingAverage <T extends Number>{
    
    private int windowSize;
    private LinkedList<T> window;
    
    private int counter;
    
    public MovingAverage(int windowSize) {
       this.windowSize = windowSize;
       window = new LinkedList<T>();
       counter = 0;
    }
    
    public void add(T number) {
        
        
        if(counter < windowSize) {
            window.addLast(number);
            counter ++;
        }else {
            window.removeFirst();
            window.add(number);
        }
    }
    
    public LinkedList<T> getWindow(){
        return window;
    }
    
    public double average() {
        if(counter ==0) {
            return 0;
        }
        
        double sum =0;
        
        for(int i=0; i<counter;i++) {
            sum += window.get(i).doubleValue();
        }
        
        return sum / (double)counter;
    }
    
    
}
