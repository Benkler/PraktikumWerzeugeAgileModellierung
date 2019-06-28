package org.com.autoscaler.testbench;

import static org.junit.Assert.assertEquals;

import org.com.autoscaler.parser.IJSONLoader;
import org.com.autoscaler.parser.JSONLoader;
import org.com.autoscaler.pojos.AutoscalerPOJO;
import org.com.autoscaler.pojos.ClockPOJO;
import org.com.autoscaler.pojos.InfrastructurePOJO;
import org.com.autoscaler.pojos.QueuePOJO;
import org.com.autoscaler.pojos.WorkflowPOJO;
import org.junit.Test;



//@RunWith(SpringRunner.class)
//@SpringBootTest
public class JSONLoaderTest {

    @Test
    public void loadQueueTest() {
        IJSONLoader loader = new JSONLoader();

        QueuePOJO queue = loader.loadQueueInformation("src/test/data/queueTest.json");
        assertEquals(queue.getQueueLengthMax(), 20);
        assertEquals(queue.getQueueLengthMin(), 5);

    }

    @Test
    public void loadAutoscalerTest() {
        IJSONLoader loader = new JSONLoader();

        AutoscalerPOJO autoscaler = loader.loadAutoScalerInformation("src/test/data/autoscalerTest.json");
        assertEquals(autoscaler.getVmStartUpTime(), 100);
        assertEquals(autoscaler.getLowerThreshold(), 0.96, 0.001);
        assertEquals(autoscaler.getUpperThreshold(), 1.06, 0.001);
 

    }
    
    @Test
    public void loadInfrastructureTest() {
        IJSONLoader loader= new JSONLoader();
        
        InfrastructurePOJO infrastructure = loader.loadInfrastructureInformation("src/test/data/infrastructureTest.json");
        
        assertEquals(infrastructure.getVmMax() , 20);
        assertEquals(infrastructure.getVmMin(), 2);
        
        assertEquals(infrastructure.getIntervalDurationInMilliSeconds(), 1, 0.001);
     

        
        
        assertEquals(infrastructure.getVirtualMachines().get(0).getId(), 12);
        assertEquals(infrastructure.getVirtualMachines().get(0).getTasksPerIntervall(), 200);
        assertEquals(infrastructure.getVirtualMachines().get(0).getVmStartUpTime(), 100);
        
        
        assertEquals(infrastructure.getVirtualMachines().get(1).getId(), 11);
        assertEquals(infrastructure.getVirtualMachines().get(1).getTasksPerIntervall(), 200);
        assertEquals(infrastructure.getVirtualMachines().get(1).getVmStartUpTime(), 100);
        
        assertEquals(infrastructure.getVirtualMachines().get(2).getId(), 10);
        assertEquals(infrastructure.getVirtualMachines().get(2).getTasksPerIntervall(), 200);
        assertEquals(infrastructure.getVirtualMachines().get(2).getVmStartUpTime(), 100); 
        
        
    }
     
    @Test
    public void loadWorkFlowTest() {
        IJSONLoader loader = new JSONLoader();
        
        WorkflowPOJO workflow = loader.loadWorkflow("src/test/data/workflowTest.json");
        //No test because workload to  big
    }
    
    @Test
    public void loadClockTest() {
        IJSONLoader loader = new JSONLoader();
        
        ClockPOJO clock = loader.loadClockInformation("src/test/data/clockTest.json");
        
        
        assertEquals(clock.getIntervalDurationInMilliSeconds()  , 1,  0.001);
       // assertEquals(clock.getClockTicksTillScalingDecision()  , 2000);
        assertEquals(clock.getClockTicksTillWorkloadChange(), 200);
    //    assertEquals(clock.getExperimentDurationInMinutes(), 60);
        assertEquals(clock.getMonitoringDelay() , 20); 
        
    }

}