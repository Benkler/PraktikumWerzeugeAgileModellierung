package org.com.autoscaler.tracker.cpuutilization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import org.com.autoscaler.events.FinishSimulationEvent;
import org.com.autoscaler.events.InfrastructureStateEvent;
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
public class CPUUtilTracker implements ICPUUtilTracker {

    @Autowired
    private ApplicationContext context;

    private final Logger log = LoggerFactory.getLogger(CPUUtilTracker.class);
    private int oldArrivalRateInTasksPerInterval;
    private int oldCapacityInTasksPerInterval;
    DecimalFormat df = new DecimalFormat("#.##");

    private final String[] header = { "Clock Tick", "Current Arrival Rate in Tasks per Interval",
            "Current Capacity in Tasks per Interval", "Current Arrival Rate in Tasks per Second",
            "Current Capacity in Tasks per Second", "CPU Utilization in %" };

    File file;
    FileWriter outputFile;
    CSVWriter writer;

    @Override
    public void startSimulation(StartSimulationEvent event) {

        oldArrivalRateInTasksPerInterval = 0;
        oldCapacityInTasksPerInterval = 0;

        try {
            Path path = Paths.get(System.getProperty("user.home") + "\\Testbench");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            file = new File(System.getProperty("user.home") + "\\Testbench\\cpuUtilization.csv\\");
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
    public void trackCpuUtil(InfrastructureStateEvent event) {
        int clockTickCount = event.getClockTickCount();
        int arrRateInTasksPerInterval = event.getInfrastructureState().getCurrentArrivalRateInTasksPerIntervall();
        int arrRateInTasksPerSecond = event.getInfrastructureState().getCurrentArrivalRateInTasksPerSecond();
        int curCapInTasksPerIntervall = event.getInfrastructureState().getCurrentCapacityInTasksPerIntervall();
        int curCapInTasksPerSecond = event.getInfrastructureState().getCurrentCapacityInTasksPerSecond();

        double cpuutilization = MathUtil
                .round(((double) arrRateInTasksPerInterval / (double) curCapInTasksPerIntervall) * 100.00, 2);

        /*
         * Discard event if nothing new happened
         */
        if (curCapInTasksPerIntervall == oldCapacityInTasksPerInterval
                && arrRateInTasksPerInterval == oldArrivalRateInTasksPerInterval) {
            return;
        }
        oldCapacityInTasksPerInterval = curCapInTasksPerIntervall;
        oldArrivalRateInTasksPerInterval = arrRateInTasksPerInterval;

        String[] newLine = { String.valueOf(clockTickCount), String.valueOf(arrRateInTasksPerInterval),
                String.valueOf(curCapInTasksPerIntervall), String.valueOf(arrRateInTasksPerSecond),
                String.valueOf(curCapInTasksPerSecond), String.valueOf(cpuutilization) };

        writer.writeNext(newLine);

    }

}
