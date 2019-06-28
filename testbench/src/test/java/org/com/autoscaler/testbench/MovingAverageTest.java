package org.com.autoscaler.testbench;

import org.com.autoscaler.util.MovingAverage;
import org.junit.Test;

public class MovingAverageTest {
    
    @Test
    public void testDoubleMovingAverage() {
        MovingAverage<Double> mov = new MovingAverage<Double>(4);
        
        mov.add(3.0);
        assert(mov.average() == 3.0);
        
        mov.add(2.0);
        assert(mov.average()== (3+2)/2.0);
        
        mov.add(5.0);
        assert(mov.average()== (3+2+5)/3.0);
        
        mov.add(8.4);
        assert(mov.average()== (3+2+5+8.4)/4.0);
        
        mov.add(12.4);
        assert(mov.average()== (2+5+8.4+12.4)/4.0);
        
        
    }
    
    @Test
    public void testIntMovingAverage() {
        MovingAverage<Integer> mov = new MovingAverage<Integer>(3);
        
        mov.add(3);
        assert(mov.average() == 3.0);
        
        mov.add(2);
        assert(mov.average() == (3+2)/2.0);
        
        mov.add(10);
        assert(mov.average() == (3+2+10)/3.0);
        
        mov.add(11);
        assert(mov.average() == (2+10+11)/3.0);
    }

}
