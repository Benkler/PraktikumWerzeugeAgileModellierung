package org.com.autoscaler.tracker.queueutilization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.com.autoscaler.events.DiscardJobsEvent;
import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
import org.com.autoscaler.events.QueueStateEvent;
import org.com.autoscaler.events.StartSimulationEvent;
import org.com.autoscaler.util.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

@Component
public class QueueUtilizationTracker implements IQueueUtilizationTracker {

    @Autowired
    private ApplicationContext context;

    private final Logger log = LoggerFactory.getLogger(QueueDiscardJobsTracker.class);

    private final String[] header = { "Clock Tick", "Amount of waiting tasks in queue", "Queue fill in %",
            "Arrival Rate (in Tasks Per Intervall)", "Arrival Rate (in tasks per ms)",
            "Processing Rate (TasksPerIntervall)", "Processing Rate (tasks per ms)", "Queuin Delay in Intervals",
            "Queuing Delay in ms" };

    File file;
    FileWriter outputFile;
    CSVWriter writer;

    int previousAmountOfTasks;
    double previousArrivalRate;
    double previousProcessingRate;

    @Override
    public void startSimulation(StartSimulationEvent event) {
        previousAmountOfTasks = 0;
        previousArrivalRate = 0;
        previousProcessingRate = 0;
        try {
            Path path = Paths.get(System.getProperty("user.home") + "\\Testbench");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            file = new File(System.getProperty("user.home") + "\\Testbench\\queueUtilization.csv\\");
            outputFile = new FileWriter(file);
            writer = new CSVWriter(outputFile, ';', '"', '\'', System.getProperty("line.separator"));
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
    public void trackQueueStateEvent(QueueStateEvent event) {
        
       
        int clockTIckCount = event.getClockTickCount();
        int amountOfTasks = event.getState().getTasksInQueue();
        double arrivalRateInTasksPerInterval = MathUtil.round(event.getState().getQueueArrivalRateInTasksPerInterval(), 2);
        double processingRateInTasksPerInterval = MathUtil.round(event.getState().getQueueProcessingRateInTasksPerInterval(), 2);
        double queueFillInPercent = event.getState().getQueueFillInPercent();
        double queuingDelayInIntervals = MathUtil.round(event.getState().getQueueingDelayInIntervals(), 2);
        
        double processingRateInTasksPerMillisecond = event.getState().getQueueProcessingRateInTasksPerMilliSecond();
        double arrivalRateInTasksPerMillisecond = event.getState().getQueueArrivalRateInTasksPerMilliSecond();
        double queuingDelayInMilliseconds = event.getState().getQueuingDelayInMilliseconds();
        
        

        // // No duplicate information
        // if (previousAmountOfTasks == amountOfTasks && previousArrivalRate ==
        // arrivalRate
        // && previousProcessingRate == processingRate)
        // return;

        String[] newLine = { String.valueOf(clockTIckCount), String.valueOf(amountOfTasks),
                String.valueOf(queueFillInPercent), String.valueOf(arrivalRateInTasksPerInterval), String.valueOf(arrivalRateInTasksPerMillisecond), String.valueOf(processingRateInTasksPerInterval),
                String.valueOf(processingRateInTasksPerMillisecond),String.valueOf(queuingDelayInIntervals) ,String.valueOf(queuingDelayInMilliseconds)};
        writer.writeNext(newLine);

        previousAmountOfTasks = amountOfTasks;
        previousArrivalRate = arrivalRateInTasksPerInterval;
        previousProcessingRate = processingRateInTasksPerInterval;

    }

}
