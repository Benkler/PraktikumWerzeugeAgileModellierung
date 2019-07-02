package org.com.autoscaler.scaler;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.com.autoscaler.events.TriggerAutoScalerEvent;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.metricsource.IMetricSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoScaler implements IAutoScaler {

    @Autowired
    private IScalingController scalingController;

    @Autowired
    private IMetricSource metricSource;

    private Logger log = LoggerFactory.getLogger(AutoScaler.class);

    private boolean init = false;

    private double lowerThreshold;
    private double upperThreshold;
    private int vmMin;
    private int vmMax;

    /*
     * Only temporary, Vm's can be different
     */
    private int vmTasksPerIntervall; 
    private int vmStartUpTime;

    @Override
    public void initAutoScaler(double lowerThreshold, double upperThreshold, int vmTasksPerIntervall, int vmMin,
            int vmMax, int startUpTime) {
        if (!init) {
            log.info("Init Autoscaler with \n lowerThreshold: " + lowerThreshold + "\n upperThreshold: "
                    + upperThreshold + "\n vmTasksPerIntervall: " + vmTasksPerIntervall + "\n vmMin: " + vmMin
                    + "\n vmMax: " + vmMax);
            this.lowerThreshold = lowerThreshold;
            this.upperThreshold = upperThreshold;
            this.vmTasksPerIntervall = vmTasksPerIntervall;
            this.vmStartUpTime = startUpTime;
            this.vmMax = vmMax;
            this.vmMin = vmMin;
        }

    }

    // public void setBinding(IScalingController scalingController, IMetricSource
    // metricSource) {
    // this.scalingController = scalingController;
    // this.metricSource = metricSource;
    // }

    // TODO do not scale under VM Min
    @Override
    public void update(TriggerAutoScalerEvent event) {

        double currentVal = metricSource.getCPUUtilization();
        List<VirtualMachine> updatedInstances;
        log.info("Autoscaler decision based on value: " + currentVal);

        // Scale Down
        if (currentVal < lowerThreshold) {
            updatedInstances = scaleDown();

            if (updatedInstances.size() == 0) {
                log.info("Autoscaler decision: Scale down at clocktick: " + event.getClockTickCount()
                        + " not possible. Minimum Amount of Virtual Machines reached ");
                return;
            }

            log.info("Autoscaler decision: Scale down at clocktick: " + event.getClockTickCount() + ".  "
                    + updatedInstances.size() + " virtual machines added");
            scalingController.setInstances(updatedInstances, event.getClockTickCount(),
                    event.getIntervallDuratioInMilliSeconds(), ScalingMode.SCALE_DOWN);

            // Scale Up
        } else if (currentVal > upperThreshold) {
            updatedInstances = scaleUp();
            
            if (updatedInstances.size() == 0) {
                log.info("Autoscaler decision: Scale up at clocktick: " + event.getClockTickCount()
                        + " not possible. Maximum Amount of Virtual Machines reached ");
                return;
            }

            log.info("Autoscaler decision: Scale up at clocktick " + event.getClockTickCount());
            scalingController.setInstances(updatedInstances, event.getClockTickCount(),
                    event.getIntervallDuratioInMilliSeconds(), ScalingMode.SCALE_UP);

        } else {
            log.info("No Autoscaler decision neede: current capacity and desired capacity close enough together");
        }

        
    }

    /*
     * Scale Down Operation --> This Autoscaler only selects one VM to shut down but
     * it is possible to shut down more than one
     */
    private List<VirtualMachine> scaleDown() {

        List<VirtualMachine> currentInstances = metricSource.getServiceInstances();
        List<VirtualMachine> vmsToShutDown = new LinkedList<VirtualMachine>();

        /*
         * Generate Random id to remove
         * 
         */
        Random rand = new Random();
        int index = rand.nextInt(currentInstances.size());

        if (currentInstances.size() > vmMin) {
            VirtualMachine vm = currentInstances.remove(index);
            vmsToShutDown.add(vm);
            log.info("Successfully removed virtual machine with id " + vm.getId());
        } else {
            log.info("Could not remove virtual machine. Minimal amount reached: " + vmMin);
        }

        return vmsToShutDown;

    }

    /*
     * This auto scaler currently selects one vm to boot up but in general more than
     * one is possible
     */
    private List<VirtualMachine> scaleUp() {
        List<VirtualMachine> currentInstances = metricSource.getServiceInstances();
        List<VirtualMachine> vmsToBoot = new LinkedList<VirtualMachine>();

        /*
         * Generate random id 
         */
        Random rand = new Random();
        //zero out the sign bits
        int id = rand.nextInt() & Integer.MAX_VALUE;

        if (currentInstances.size() < vmMax) {

            //VirtualMachine tobeAdded = new VirtualMachine(id, vmTasksPerIntervall, vmStartUpTime);
            VirtualMachine tobeAdded = new VirtualMachine(id, vmTasksPerIntervall, vmStartUpTime);
            vmsToBoot.add(tobeAdded);
            log.info("Successfully added  virtual machine with id  " + tobeAdded.getId());
        } else {
            log.info("Could not add virtual machine. Maximal Amount reached: " + vmMax);
        }

        return vmsToBoot;
    }

}
