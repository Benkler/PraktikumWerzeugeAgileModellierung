package org.com.autoscaler.testbench;

import static org.junit.Assert.assertEquals;

import org.com.autoscaler.parser.JSONLoader;
import org.com.autoscaler.pojos.AutoscalerPOJO;
import org.com.autoscaler.pojos.InfrastructurePOJO;
import org.com.autoscaler.pojos.QueuePOJO;
import org.com.autoscaler.pojos.WorkflowPOJO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JSONLoaderTest {

    @Test
    public void loadQueueTest() {
        JSONLoader loader = new JSONLoader();

        QueuePOJO queue = loader.loadQueueInformation("src/test/data/queueTest.json");
        assertEquals(queue.getQueueLengthMax(), 20);
        assertEquals(queue.getQueueLengthMin(), 5);

    }

    @Test
    public void loadAutoscalerTest() {
        JSONLoader loader = new JSONLoader();

        AutoscalerPOJO autoscaler = loader.loadAutoScalerInformation("src/test/data/autoscalerTest.json");
        assertEquals(autoscaler.getMonitoringDelay(), 4);
        assertEquals(autoscaler.getVmStartUpTime(), 100);

    }
    
    @Test
    public void loadInfrastructureTest() {
        JSONLoader loader= new JSONLoader();
        
        InfrastructurePOJO infrastructure = loader.loadInfrastructureInformation("src/test/data/infrastructureTest.json");
        
        assertEquals(infrastructure.getVmMax() , 20);
        assertEquals(infrastructure.getVmMin(), 2);
        assertEquals(infrastructure.getQueue().getQueueLengthMax(), 123);
        assertEquals(infrastructure.getQueue().getQueueLengthMin(), 0);
        
        assertEquals(infrastructure.getVirtualMachines().get(0).getId(), 12);
        assertEquals(infrastructure.getVirtualMachines().get(0).getTasksPerIntervall(), 200);
        
        assertEquals(infrastructure.getVirtualMachines().get(1).getId(), 11);
        assertEquals(infrastructure.getVirtualMachines().get(1).getTasksPerIntervall(), 220);
        
        assertEquals(infrastructure.getVirtualMachines().get(2).getId(), 10);
        assertEquals(infrastructure.getVirtualMachines().get(2).getTasksPerIntervall(), 230);
        
        
    }
    
    @Test
    public void loadWorkFlow() {
        JSONLoader loader = new JSONLoader();
        
        WorkflowPOJO workflow = loader.loadWorkflow("src/test/data/workflowTest.json");
        
        assertEquals(workflow.getWorkflow().get(0).intValue(), 140);
        assertEquals(workflow.getWorkflow().get(1).intValue(), 150);
        assertEquals(workflow.getWorkflow().get(2).intValue(), 280);
        assertEquals(workflow.getWorkflow().get(3).intValue(), 0);
        assertEquals(workflow.getWorkflow().get(4).intValue(), 400);
        assertEquals(workflow.getWorkflow().size(), 5);
    }

}
