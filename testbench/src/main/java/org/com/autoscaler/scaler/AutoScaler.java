package org.com.autoscaler.scaler;

import java.util.List;
import java.util.Random;

import org.com.autoscaler.events.TriggerAutoScalerEvent;
import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.util.ScalingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoScaler {

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

    public void initAutoScaler(double lowerThreshold, double upperThreshold, int vmTasksPerIntervall, int vmMin,
            int vmMax) {
        if (!init) {
            log.info("Init Autoscaler with \n lowerThreshold: " + lowerThreshold + "\n upperThreshold: "
                    + upperThreshold + "\n vmTasksPerIntervall: " + vmTasksPerIntervall + "\n vmMin: " + vmMin
                    + "\n vmMax: " + vmMax);
            this.lowerThreshold = lowerThreshold;
            this.upperThreshold = upperThreshold;
            this.vmTasksPerIntervall = vmTasksPerIntervall;
        }

    }

    // public void setBinding(IScalingController scalingController, IMetricSource
    // metricSource) {
    // this.scalingController = scalingController;
    // this.metricSource = metricSource;
    // }

    public void update(TriggerAutoScalerEvent event) {

        double currentVal = metricSource.getValue();
        List<VirtualMachine> updatedInstances;

        if (currentVal < lowerThreshold) {
            updatedInstances = scaleUp();
            log.info("Autoscaler decision: Scale up at clocktick " + event.getClockTickCount());
            scalingController.setInstances(updatedInstances, event.getClockTickCount(),
                    event.getIntervallDuratioInMilliSeconds(), ScalingMode.SCALE_UP);
        } else if (currentVal > upperThreshold) {
            updatedInstances = scaleDown();
            log.info("Autoscaler decision: Scale down at clocktick: " + event.getClockTickCount());
            scalingController.setInstances(updatedInstances, event.getClockTickCount(),
                    event.getIntervallDuratioInMilliSeconds(), ScalingMode.SCALE_DOWN);
        } else {
            log.info("No Autoscaler decision neede: current capacity and desired capacity close enough together");
        }

        // scalingController.setInstances(currentInstances);
    }

    private List<VirtualMachine> scaleDown() {

        List<VirtualMachine> currentInstances = metricSource.getServiceInstances();

        /*
         * Generate Random id to remove
         * 
         */
        Random rand = new Random();
        int index = rand.nextInt(currentInstances.size());

        if (currentInstances.size() > vmMin) {
            currentInstances.remove(index);
            log.info("Successfully removed virtual machine");
        } else {
            log.info("Could not remove virtual machine. Minimal amount reached: " + vmMin);
        }

        return currentInstances;

    }

    private List<VirtualMachine> scaleUp() {
        List<VirtualMachine> currentInstances = metricSource.getServiceInstances();

        /*
         * Generate random id
         */
        Random rand = new Random();
        int id = rand.nextInt();

        if (currentInstances.size() < vmMax) {

            VirtualMachine tobeAdded = new VirtualMachine(id, vmTasksPerIntervall);
            currentInstances.add(tobeAdded);
        }

        return currentInstances;
    }

}
