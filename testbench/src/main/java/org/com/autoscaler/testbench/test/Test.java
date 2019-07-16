package org.com.autoscaler.testbench.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        
        Random rand = new Random();
        
        List<Integer> list = new LinkedList<Integer>();
        for (int i=0; i<10000;i++) {
            
            int toBeadded =  Math.min(rand.nextInt(15000)+16000, 31000);
            
            list.add(toBeadded);
        }
        
        System.out.println(list);

    }

}
