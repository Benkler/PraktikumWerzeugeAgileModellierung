package org.com.autoscaler.testbench;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.com.autoscaler.clock.ClockInformation;
import org.com.autoscaler.clock.IClock;
import org.com.autoscaler.infrastructure.IInfrastructureModel;
import org.com.autoscaler.infrastructure.InfrastructureState;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.infrastructure.VirtualMachineType;
import org.com.autoscaler.metricsource.IMetricSource;
import org.com.autoscaler.parser.IJSONLoader;
import org.com.autoscaler.pojos.AutoscalerPOJO;
import org.com.autoscaler.pojos.ClockPOJO;
import org.com.autoscaler.pojos.InfrastructurePOJO;
import org.com.autoscaler.pojos.QueuePOJO;
import org.com.autoscaler.pojos.VirtualMachineTypePOJO;
import org.com.autoscaler.pojos.WorkflowPOJO;
import org.com.autoscaler.queue.IQueueModel;
import org.com.autoscaler.scaler.IAutoScaler;
import org.com.autoscaler.util.MathUtil;
import org.com.autoscaler.workloadhandler.IWorkloadHandler;
import org.com.autoscaler.workloadhandler.WorkloadTransferObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final IQueueModel queue;
    private final IInfrastructureModel infrastructure;
    private final IAutoScaler autoScaler;
    private final IWorkloadHandler workloadHandler;
    private final IJSONLoader jsonLoader;
    private final IMetricSource metricSource;
    private final int AUTOSCALERINDEX = 0;
    private final int INFRASTRUCTUREINDEX = 1;
    private final int QUEUEINDEX = 2;
    private final int WORKFLOWINDEX = 3;
    private final int CLOCKINDEX = 4;

    @Autowired
    public ApplicationStartUpRunner(IClock clock, IQueueModel queue, IInfrastructureModel infrastructureModel,
            @Qualifier("activeScaler")IAutoScaler autoscaler, IWorkloadHandler workloadHandler, IJSONLoader jsonLoader,
            IMetricSource metricSOurce) {
        this.clock = clock;
        this.queue = queue;
        this.infrastructure = infrastructureModel;
        this.autoScaler = autoscaler;
        this.workloadHandler = workloadHandler;
        this.jsonLoader = jsonLoader;
        this.metricSource = metricSOurce;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logCommandLineParameters(args);

        // ReadSetUp of Infrastructure Modell

        String clockPath = "src/main/data/clock.json";
        initClock(clockPath);

        String queuePath = "src/main/data/queue.json";
        initQueue(queuePath, clockPath);

        String autoScalerPath = "src/main/data/autoscaler.json";
        initAutoScalerAndMetricSource(autoScalerPath, clockPath);

        String infrastructurePath = "src/main/data/infrastructure.json";
        initInfrastructure(infrastructurePath, clockPath);

        String workFlowPath = "src/main/data/workflow.json";
        initWorkloadHandler(workFlowPath, clockPath);

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

        int clockTicksTillWorkloadChange = MathUtil.millisecondsInClockTicks(
                clockPOJO.getMillisecondsTillWorkloadChange(), clockPOJO.getIntervalDurationInMilliSeconds());

        int clockTicksTillPublishInfrastructureState = MathUtil.millisecondsInClockTicks(
                clockPOJO.getMillisecondsTillPublishInfrastructureState(),
                clockPOJO.getIntervalDurationInMilliSeconds());

        int clockTicksTillPublishQueueState = MathUtil.millisecondsInClockTicks(
                clockPOJO.getMillisecondsTillPublishQueueState(), clockPOJO.getIntervalDurationInMilliSeconds());

        ClockInformation info = new ClockInformation(clockPOJO.getIntervalDurationInMilliSeconds(),
                clockTicksTillWorkloadChange, clockTicksTillPublishInfrastructureState, clockTicksTillPublishQueueState,
                clockPOJO.getExperimentDurationInMinutes());
        clock.initClock(info);

    }

    private void initQueue(String path, String pathToClock) {
        /*
         * We need interval duration for capacity calculation
         */
        ClockPOJO clockPOJO = jsonLoader.loadClockInformation(pathToClock);

        QueuePOJO queuePOJO = jsonLoader.loadQueueInformation(path);

        
        int scalingFactor = clockPOJO.getMillisecondsTillWorkloadChange();
        /**
         * ############################################IMPORTANT: <br>
         * We use floor here. In case queuing delay is smaller than a multiple of
         * interval duration, the queuing delay will be the lower interval border as the
         * tasks leave the system within the this interval. <br>
         * Example: interval duration = 100, <br>
         * # Queuing delay = 220 ==> result = 2 Intervals as task needs to stay in queue
         * for 2 intervals and will be processed in third; <br>
         * # Queuing delay = 280 ==> result also 2 intervals for same reason # Queuing
         * delay = 80 ==> result = 0 as tasks can be processed within same interval
         * 
         */
        int queuingDelayInClockTicks = MathUtil.millisecondsInClockTicksFloor(queuePOJO.getQueuingDelayInMilliSeconds(),
                clockPOJO.getIntervalDurationInMilliSeconds());

        queue.initQueue(queuePOJO.getQueueLengthMax()*scalingFactor, queuePOJO.getWindowSize(), queuingDelayInClockTicks);
    }

    private void initAutoScalerAndMetricSource(String path, String pathToClock) {
        AutoscalerPOJO autoscalerPOJO = jsonLoader.loadAutoScalerInformation(path);
        ClockPOJO clockPOJO = jsonLoader.loadClockInformation(pathToClock);

        int timeToScalingDecisionInCLockTicks = MathUtil.millisecondsInClockTicks(
                autoscalerPOJO.getTimeInMsTillNextScalingDecision(), clockPOJO.getIntervalDurationInMilliSeconds());

        int coolDownInClockTicks = MathUtil.millisecondsInClockTicks(autoscalerPOJO.getCoolDownTimeInMilliSeconds(),
                clockPOJO.getIntervalDurationInMilliSeconds());

        autoScaler.initAutoScaler(autoscalerPOJO.getLowerThreshold(), autoscalerPOJO.getUpperThreshold(),
                autoscalerPOJO.getVmMin(), autoscalerPOJO.getVmMax(), coolDownInClockTicks,
                timeToScalingDecisionInCLockTicks);
        metricSource.initMetricSource(autoscalerPOJO.getCpuUtilWindow(), autoscalerPOJO.getQueueLengthWindow());

    }

    private void initInfrastructure(String pathToInfrastructure, String pathToClock) {
        /*
         * We need interval duration for capacity calculation
         */
        ClockPOJO clockPOJO = jsonLoader.loadClockInformation(pathToClock);
        InfrastructurePOJO infrastructurePOJO = jsonLoader.loadInfrastructureInformation(pathToInfrastructure);
        

        /*
         * Get Type with information in milliseconds
         */
        VirtualMachineTypePOJO vmTypePOJO = infrastructurePOJO.getVirtualMachineType();
        
        

        /*
         * Create type with information in clock ticks
         */
        VirtualMachineType vmType = new VirtualMachineType(vmTypePOJO, clockPOJO.getIntervalDurationInMilliSeconds(), clockPOJO.getMillisecondsTillWorkloadChange());

        HashMap<Integer, VirtualMachine> vms = instantiateVirtualMachines(vmType,
                infrastructurePOJO.getAmountOfVmsAtSimulationStart());

        InfrastructureState state = new InfrastructureState (vms, clockPOJO.getIntervalDurationInMilliSeconds());

        infrastructure.initInfrastructureModel(state, infrastructurePOJO.getCpuUitilizationWindow(), vmType);
    }

    /*
     * Instantiate given amount of virtual machines
     */
    private HashMap<Integer, VirtualMachine> instantiateVirtualMachines(VirtualMachineType type, int amount) {
        HashMap<Integer, VirtualMachine> vms = new HashMap<Integer, VirtualMachine>();

        for (int i = 0; i < amount; i++) {
            int id = MathUtil.generateRandomInteger();
            vms.put(id, new VirtualMachine(id, type.getTasksPerInterval(), type.getVmStartUpTimeInIntervals()));

        }
        
    

        return vms;

    }

    private void initWorkloadHandler(String path, String pathToClock) {
        WorkflowPOJO workflow = jsonLoader.loadWorkflow(path);
        ClockPOJO clockPojo = jsonLoader.loadClockInformation(pathToClock);

        List<Integer> amountOfTasksBetweenTwoWorkloadChanges = workflow.getWorkflow();

        List<Integer> amountOfTasksProcessedInEachInterval = amountOfTasksBetweenTwoWorkloadChanges.stream()
                .map(i -> processWorkload(clockPojo.getMillisecondsTillWorkloadChange(),
                        clockPojo.getIntervalDurationInMilliSeconds(), i))
                .collect(Collectors.toList());

        WorkloadTransferObject workFlow = new WorkloadTransferObject(amountOfTasksProcessedInEachInterval);
        workloadHandler.initWorkloadHandler(workFlow);

    }

    private int processWorkload(int milliSecondsTillWorkloadChange, double intervalDurationInMillisecond,
            int amountOfTasksToBeProcessed) {

        double tasksPerMillisecond = (double) amountOfTasksToBeProcessed / (double) milliSecondsTillWorkloadChange;
        
       

       // double tasksPerIntervall = tasksPerMillisecond * intervalDurationInMillisecond;
        
        double tasksPerIntervall = tasksPerMillisecond * intervalDurationInMillisecond * milliSecondsTillWorkloadChange;

        return Math.round((float) tasksPerIntervall);
    }

}
