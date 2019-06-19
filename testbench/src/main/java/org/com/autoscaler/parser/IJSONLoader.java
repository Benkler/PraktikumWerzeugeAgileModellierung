package org.com.autoscaler.parser;

import org.com.autoscaler.pojos.AutoscalerPOJO;
import org.com.autoscaler.pojos.ClockPOJO;
import org.com.autoscaler.pojos.InfrastructurePOJO;
import org.com.autoscaler.pojos.QueuePOJO;
import org.com.autoscaler.pojos.WorkflowPOJO;

public interface IJSONLoader {

    InfrastructurePOJO loadInfrastructureInformation(String path);

    QueuePOJO loadQueueInformation(String path);

    WorkflowPOJO loadWorkflow(String path);

    AutoscalerPOJO loadAutoScalerInformation(String path);
    
    ClockPOJO loadClockInformation(String path);

}