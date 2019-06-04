package org.com.autoscaler.testbench;

import org.com.autoscaler.testbench.test.DefaultHelloService;
import org.com.autoscaler.testbench.test.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestbenchApplication  {
    

    
    private static final Logger log = LoggerFactory.getLogger(TestbenchApplication.class);
    
    public static void main(String[] args) {    
        log.debug("Trying to start Spring Application");
        SpringApplication.run(TestbenchApplication.class, args);
        //Nothing to be executed after this statement
       
    }

  
   

}
