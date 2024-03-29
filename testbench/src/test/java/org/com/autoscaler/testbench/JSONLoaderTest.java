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
        assertEquals(queue.getQueueLengthMax(), 2000);
        assertEquals(queue.getWindowSize(), 20);
        assertEquals(queue.getQueuingDelayInMilliSeconds(), 200);
        

    }

    @Test
    public void loadAutoscalerTest() {
        IJSONLoader loader = new JSONLoader();

        AutoscalerPOJO autoscaler = loader.loadAutoScalerInformation("src/test/data/autoscalerTest.json");
        
        assertEquals(autoscaler.getLowerThreshold(), 0.97, 0.001);
        assertEquals(autoscaler.getUpperThreshold(), 1.08, 0.001);
        assertEquals(autoscaler.getCpuUtilWindow(), 8);
        assertEquals(autoscaler.getQueueLengthWindow(), 10);
        assertEquals(autoscaler.getCoolDownTimeInMilliSeconds(), 1000);
        assertEquals(autoscaler.getTimeInMsTillNextScalingDecision()  , 2000);
        assertEquals(autoscaler.getVmMax(), 20);
 
        assertEquals(autoscaler.getVmMin(), 2);

    }
    
    @Test 
    public void loadInfrastructureTest() {
        IJSONLoader loader= new JSONLoader();
        
        InfrastructurePOJO infrastructure = loader.loadInfrastructureInformation("src/test/data/infrastructureTest.json");
        
   
        
        
        assertEquals(infrastructure.getCpuUitilizationWindow(), 20);
     

        
       assertEquals(infrastructure.getVirtualMachineType().getMillisecondsPerTask(), 1, 0.001); 
       assertEquals(infrastructure.getVirtualMachineType().getVmStartUpTimeInMilliSeconds(), 5, 0.001); 
       assertEquals(infrastructure.getAmountOfVmsAtSimulationStart(), 10);
      
        
        
        
        
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
    
        assertEquals(clock.getMillisecondsTillWorkloadChange(), 200);
        assertEquals(clock.getExperimentDurationInMinutes(), 60);
        assertEquals(clock.getMillisecondsTillPublishInfrastructureState() , 20); 
        assertEquals(clock.getMillisecondsTillPublishQueueState() , 20);
        
    }

}
