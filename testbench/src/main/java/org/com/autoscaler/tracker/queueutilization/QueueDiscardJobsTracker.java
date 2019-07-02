package org.com.autoscaler.tracker.queueutilization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.com.autoscaler.events.DiscardJobsEvent;
import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.StartSimulationEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

@Component
public class QueueDiscardJobsTracker implements IQueueDiscardJobsTracker {
    
    @Autowired
    private ApplicationContext context;

    private final Logger log = LoggerFactory.getLogger(QueueDiscardJobsTracker.class);

    private final String[] header = { "Clock Tick", "Amount of Discarded Jobs" };
    
    File file;
    FileWriter outputFile;
    CSVWriter writer;
    @Override
    public void startSimulation(StartSimulationEvent event) {
        
        
        try {
            Path path = Paths.get(System.getProperty("user.home") + "\\Testbench");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            file = new File(System.getProperty("user.home") + "\\Testbench\\discardedJobs.csv\\");
            outputFile = new FileWriter(file);
            writer = new CSVWriter(outputFile);
            writer.writeNext(header);
        } catch (IOException e) {

            log.error("IO-Exception while starting simulation: " + e.getMessage());
            int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
            System.exit(exitCode);
        }
        
    }

    @Override
    public void finishSimulation(FinishSimulationEvent event) {
        try {
            writer.close();
        } catch (IOException e) {
            log.error("IO-Exception while finishing simulation: " + e.getMessage());
            int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
            System.exit(exitCode);
        }
        
    }

    @Override
    public void trackDiscardJobsEvent(DiscardJobsEvent event) {
       int amountOfDiscardedJobs = event.getAmountOfDiscardedTasks();
       int clockTIckCount = event.getClockTickCount();
       
       String[] newLine = { String.valueOf(clockTIckCount), String.valueOf(amountOfDiscardedJobs)};
       writer.writeNext(newLine);
        
    }

}
