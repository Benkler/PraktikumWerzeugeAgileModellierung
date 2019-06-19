package org.com.autoscaler.testbench;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.com.autoscaler.clock.ClockInformation;
import org.com.autoscaler.clock.IClock;
import org.com.autoscaler.infrastructure.IInfrastructureModel;
import org.com.autoscaler.infrastructure.InfrastructureModel;
import org.com.autoscaler.infrastructure.InfrastructureState;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.parser.IJSONLoader;
import org.com.autoscaler.pojos.AutoscalerPOJO;
import org.com.autoscaler.pojos.ClockPOJO;
import org.com.autoscaler.pojos.InfrastructurePOJO;
import org.com.autoscaler.pojos.QueuePOJO;
import org.com.autoscaler.pojos.VirtualMachinePOJO;
import org.com.autoscaler.pojos.WorkflowPOJO;
import org.com.autoscaler.queue.IQueue;
import org.com.autoscaler.scaler.IAutoScaler;
import org.com.autoscaler.testbench.test.HelloService;
import org.com.autoscaler.workloadhandler.IWorkloadHandler;
import org.com.autoscaler.workloadhandler.WorkloadTransferObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Main class that initializes and starts the simulation. The run function is
 * triggered as soon as the SPringApplication has fully started
 * 
 * @author Niko
 *
 */
@Component
public class ApplicationStartUpRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartUpRunner.class);

    private final IClock clock;

    private final IQueue queue;
    private final IInfrastructureModel infrastructure;
    private final IAutoScaler autoScaler;
    private final IWorkloadHandler workloadHandler;
    private final IJSONLoader jsonLoader;
    private final int AUTOSCALERINDEX = 0;
    private final int INFRASTRUCTUREINDEX = 1;
    private final int QUEUEINDEX = 2;
    private final int WORKFLOWINDEX = 3;
    private final int CLOCKINDEX = 4;

    @Autowired
    public ApplicationStartUpRunner(IClock clock, IQueue queue, IInfrastructureModel infrastructureModel,
            IAutoScaler autoscaler, IWorkloadHandler workloadHandler, IJSONLoader jsonLoader) {
        this.clock = clock;
        this.queue = queue;
        this.infrastructure = infrastructureModel;
        this.autoScaler = autoscaler;
        this.workloadHandler = workloadHandler;
        this.jsonLoader = jsonLoader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logCommandLineParameters(args);

        // ReadSetUp of Infrastructure Modell

        String clockPath = "src/test/data/clockTest.json";
        initClock(clockPath);

        String queuePath = "src/test/data/queueTest.json";
        initQueue(queuePath);

        String autoScalerPath = "src/test/data/autoscalerTest.json";
        initAutoScaler(autoScalerPath);

        String infrastructurePath = "src/test/data/infrastructureTest.json";
        initInfrastructure(infrastructurePath);

        String workFlowPath = "src/test/data/workflowTest.json";
        initWorkloadHandler(workFlowPath);

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

    private void initClock(String path) {

        ClockPOJO clockPOJO = jsonLoader.loadClockInformation(path);
        ClockInformation info = new ClockInformation(clockPOJO.getIntervalDurationInMilliSeconds(),
                clockPOJO.getClockTicksTillWorkloadChange(), clockPOJO.getClockTicksTillScalingDecision(),
                clockPOJO.getMonitoringDelay(), clockPOJO.getExperimentDurationInMinutes());
        clock.initClock(info);

    }

    private void initQueue(String path) {
        QueuePOJO queuePOJO = jsonLoader.loadQueueInformation(path);

        queue.initQueue(queuePOJO.getQueueLengthMin(), queuePOJO.getQueueLengthMax());
    }

    private void initAutoScaler(String path) {
        AutoscalerPOJO autoscalerPOJO = jsonLoader.loadAutoScalerInformation(path);
        autoScaler.initAutoScaler(autoscalerPOJO.getLowerThreshold(), autoscalerPOJO.getUpperThreshold(),
                autoscalerPOJO.getVmTasksPerIntervall(), autoscalerPOJO.getVmMin(), autoscalerPOJO.getVmMax(),
                autoscalerPOJO.getVmStartUpTime());
    }

    private void initInfrastructure(String path) {
        InfrastructurePOJO infrastructurePOJO = jsonLoader.loadInfrastructureInformation(path);

        List<VirtualMachine> vms = new LinkedList<VirtualMachine>();
        for (VirtualMachinePOJO virtualMachine : infrastructurePOJO.getVirtualMachines()) {
            vms.add(new VirtualMachine(virtualMachine.getId(), virtualMachine.getTasksPerIntervall(),
                    virtualMachine.getVmStartUpTime()));
        }

        InfrastructureState state = new InfrastructureState(infrastructurePOJO.getVmMin(),
                infrastructurePOJO.getVmMax(), vms, infrastructurePOJO.getIntervalDurationInMilliSeconds());

        infrastructure.initInfrastructureModel(state);
    }

    private void initWorkloadHandler(String path) {
        WorkflowPOJO workflow = jsonLoader.loadWorkflow(path);
        WorkloadTransferObject workFlow = new WorkloadTransferObject(workflow.getWorkflow());
        workloadHandler.initWorkloadHandler(workFlow);

    }

}
