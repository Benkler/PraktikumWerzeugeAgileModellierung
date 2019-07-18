package org.com.autoscaler.scaler;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.com.autoscaler.events.ClockEvent;

import org.com.autoscaler.infrastructure.VirtualMachine;
import org.com.autoscaler.infrastructure.VirtualMachineType;
import org.com.autoscaler.metricsource.IMetricSource;
import org.com.autoscaler.util.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("activeScaler")
public class AutoScaler implements IAutoScaler {

    @Autowired
    private IScalingController scalingController;

    @Autowired
    private IMetricSource metricSource;

    private Logger log = LoggerFactory.getLogger(AutoScaler.class);

    private boolean init = false;

    private int clockTicksTillScalingDecision;
    private double lowerThreshold;
    private double upperThreshold;
    private int vmMin;
    private int vmMax;

    /*
     * In case of an auto scaling decision, auto scaler shall not be triggered for
     * certain amount of ticks
     */
    private int coolDownTime;

    private int coolDownCounter;

    private boolean isCoolDown;

    @Override
    public void initAutoScaler(double lowerThreshold, double upperThreshold, int vmMin, int vmMax,
            int coolDownTimeInIntervallTicks, int clockTicksTillScalingDecision) {
        if (!init) {
            log.info("Init Autoscaler with \n lowerThreshold: " + lowerThreshold + "\n upperThreshold: "
                    + upperThreshold + "\n vmMin: " + vmMin + "\n vmMax: " + vmMax);
            this.lowerThreshold = lowerThreshold;
            this.upperThreshold = upperThreshold;

            this.vmMax = vmMax;
            this.vmMin = vmMin;
            this.coolDownTime = coolDownTimeInIntervallTicks;
            this.coolDownCounter = 0;
            this.isCoolDown = false;
            this.clockTicksTillScalingDecision = clockTicksTillScalingDecision;
        }

    }

    /*
     * After a provided amount of discrete clock intervals the auto scaler has to be
     * triggered in order to evaluate the given state and re-calculate the required
     * amount of resources. This is due to the fact, that an auto scaling decision
     * should not happen on every clock tick or even on every worklaod change
     */
    private void update(ClockEvent event) {

        /*
         * Still in cooldown phase
         */
        if (isCoolDown) {
            log.info("No Autoscaler decision! Still in cooldown phase!");
            return;
        }

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
                    + updatedInstances.size() + " virtual machines removed");
            scalingController.setInstances(updatedInstances, event.getClockTickCount(),
                    event.getIntervallDuratioInMilliSeconds(), ScalingMode.SCALE_DOWN);
            setCoolDown(true);

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
            setCoolDown(true);

        } else {
            log.info("No Autoscaler decision needed: current capacity and desired capacity close enough together");
        }

    }

    private void setCoolDown(boolean isCoolDown) {
        this.isCoolDown = isCoolDown;
    }

    /*
     * 
     */
    private void resetCoolDownCounter() {
        coolDownCounter = 0;
    }

    /**
     * Each clock tick increases coolDownCounter in case the auto scaler is in cool
     * down phase <\br> As soon as cooldown phase is finished, the autoscaler is
     * able to do scaling decisions again
     */
    @Override
    public void handleClockTick(ClockEvent event) {

        if (event.getClockTickCount() % clockTicksTillScalingDecision == 0) {

            /*
             * Avoid scaling down at first clockTick (hack needed, because arrival rate at
             * first clockTick = 0!)
             */
            if (event.getClockTickCount() == 0) {
                return;
            }

            log.info("Autoscaler triggered at clockTick: " + event.getClockTickCount());
            update(event);
        }

        if (isCoolDown) {
            coolDownCounter++;

            if (coolDownCounter > coolDownTime) {
                resetCoolDownCounter();
                setCoolDown(false);
            }
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

        int id = MathUtil.generateRandomInteger();

        if (currentInstances.size() < vmMax) {

            // VirtualMachine tobeAdded = new VirtualMachine(id, vmTasksPerIntervall,
            // vmStartUpTime);
            VirtualMachineType vmType = metricSource.getVirtualMachineType();
            VirtualMachine tobeAdded = new VirtualMachine(id, vmType.getTasksPerInterval(),
                    vmType.getVmStartUpTimeInIntervals());
            vmsToBoot.add(tobeAdded);
            log.info("Successfully added  virtual machine with id  " + tobeAdded.getId());
        } else {
            log.info("Could not add virtual machine. Maximal Amount reached: " + vmMax);
        }

        return vmsToBoot;
    }

}
