package org.com.autoscaler.testbench;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartUpRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartUpRunner.class);
    
    
  
    @Override
    public void run(ApplicationArguments args) throws Exception {
        

        log.info("Application started with command-line Arguments: {}", Arrays.toString(args.getSourceArgs()));
        log.info("NonOptionArgs: {}", args.getNonOptionArgs());
        log.info("OptionNames: {}", args.getOptionNames());

        for (String name : args.getOptionNames()) {
            log.info("arg-" + name + "=" + args.getOptionValues(name));
        }
        
        boolean containsOption = args.containsOption("input.path.workload");
        log.info("Contains input.path.workload: " + containsOption);
        
        if(!containsOption) {
            throw new Exception("Abort");
        }
        
        //Check if all parameters were provided

        

    }

}
