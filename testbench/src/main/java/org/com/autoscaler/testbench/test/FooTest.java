package org.com.autoscaler.testbench.test;

import java.util.LinkedList;
import java.util.List;

import org.com.autoscaler.util.MathUtil;

public class FooTest {

    public static void main(String[] args) {
       LinkedList<Integer> list = new LinkedList<Integer>();
       
       list.add(4);
       list.add(5);
       System.out.println(list);
       
       test(list);
       
       System.out.println(list);
        
        String test = "ast";
        
        test2(test);
        
        System.out.println(test);
                
       
       
    }
    
    private static void test(LinkedList<Integer> list){
        list.removeFirst();
    }
    
    private static void test2(String input) {
        
        input.replace('a', " ".charAt(0));
        
    }

}
