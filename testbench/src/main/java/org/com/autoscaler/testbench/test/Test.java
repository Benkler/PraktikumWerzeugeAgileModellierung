package org.com.autoscaler.testbench.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        
        Random rand = new Random();
        
        List<Integer> list = new LinkedList<Integer>();
        for (int i=0; i<10000;i++) {
            
            list.add(rand.nextInt(3000)+600);
        }
        
        System.out.println(list);

    }

}
