package org.com.autoscaler.testbench;

import java.util.Arrays;

import org.com.autoscaler.clock.ClockInformation;
import org.com.autoscaler.clock.IClock;
import org.com.autoscaler.testbench.test.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Main class that initializes and starts the simulation.
 * The run function is triggered as soon as the SPringApplication has fully started
 * @author Niko
 *
 */
@Component
public class ApplicationStartUpRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartUpRunner.class);

    private final IClock clock;
    
    
    
   

    @Autowired
    public ApplicationStartUpRunner(IClock clock) {
        this.clock = clock;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    
       logCommandLineParameters(args);
       
       //ReadSetUp of Infrastructure Modell
       
       ClockInformation info = new ClockInformation(0, 1, 2, 5);
       clock.initClock(info); //TODO auslesen
       clock.startClock();
        

    }
    
    
    /*
     * Helper Method to Log the passed command line parameters
     */
    private void logCommandLineParameters(ApplicationArguments args) {

        log.info("Application started with command-line Arguments: {}", Arrays.toString(args.getSourceArgs()));
        log.info("NonOptionArgs: {}", args.getNonOptionArgs());
        log.info("OptionNames: {}", args.getOptionNames());

        for (String name : args.getOptionNames()) {
            log.info("arg-" + name + "=" + args.getOptionValues(name));
        }

        boolean containsOption = args.containsOption("input.path.workload");
        log.info("Contains input.path.workload: " + containsOption);

        if (!containsOption) {
           // throw new Exception("Abort");
        }
    }

}
