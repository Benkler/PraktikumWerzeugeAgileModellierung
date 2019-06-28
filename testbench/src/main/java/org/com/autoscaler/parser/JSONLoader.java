package org.com.autoscaler.parser;

import java.io.File;
import java.io.IOException;

import org.com.autoscaler.pojos.AutoscalerPOJO;
import org.com.autoscaler.pojos.ClockPOJO;
import org.com.autoscaler.pojos.InfrastructurePOJO;
import org.com.autoscaler.pojos.QueuePOJO;
import org.com.autoscaler.pojos.WorkflowPOJO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class to load necessary information from json files
 * 
 * @author Niko
 *
 */
@Service
public class JSONLoader implements IJSONLoader {

    Logger log = LoggerFactory.getLogger(JSONLoader.class);

    @Override
    public InfrastructurePOJO loadInfrastructureInformation(String path) {

        InfrastructurePOJO infrastructure;
        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            infrastructure = objectMapper.readValue(file, InfrastructurePOJO.class);

            log.info("\n Successfully loaded infrstructure: \n" + infrastructure.toString());
        } catch (IOException e) {
            log.error("Could not dezerialize Infrastructure Information from Json file. Error Message: "
                    + e.getMessage());
            return null;
        }

        return infrastructure;
    }

    @Override
    public QueuePOJO loadQueueInformation(String path) {

        QueuePOJO queue;
        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            queue = objectMapper.readValue(file, QueuePOJO.class);
            log.info("\n Successfully loaded Queue information: \n" + queue.toString());
        } catch (IOException e) {
            log.error("\n Could not deserialize Queue Information from Json File. Error Message: " + e.getMessage());
            return null;
        }

        return queue;

    }

    @Override
    public WorkflowPOJO loadWorkflow(String path) {

        WorkflowPOJO workflow;

        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            workflow = objectMapper.readValue(file, WorkflowPOJO.class);
            log.info("\n Successfully loaded Workflow.");
        } catch (IOException e) {
            log.error("\n Could not deserialize Workflow. Error Message: " + e.getMessage());

            return null;

        }

        return workflow;
    }

    @Override
    public AutoscalerPOJO loadAutoScalerInformation(String path) { 
        AutoscalerPOJO autoScaler;

        File file = new File(path);

        ObjectMapper mapper = new ObjectMapper();

        try {
            autoScaler = mapper.readValue(file, AutoscalerPOJO.class);
            log.info("\n Successfully loaded autoscaler information: \n " + autoScaler.toString());
        } catch (IOException e) {
            log.error("\n Could not deserialize Autoscaler information. Error message: " + e.getMessage());

            return null;
        }

        return autoScaler;

    }

    @Override
    public ClockPOJO loadClockInformation(String path) {
        ClockPOJO clockInformation;

        File file = new File(path);

        ObjectMapper mapper = new ObjectMapper();

        try {
            clockInformation = mapper.readValue(file, ClockPOJO.class);
            log.info("\n Successfully loaded clock information: \n " + clockInformation.toString());
        } catch (IOException e) {
            log.error("\n Could not deserialize Clock Information. Error message: " + e.getMessage());

            return null;
        }

        return clockInformation;
    }

}
