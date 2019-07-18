package org.com.autoscaler.scaler;

import java.util.List;

import org.com.autoscaler.infrastructure.VirtualMachine;

public interface IScalingController {

    /**
     * Send Scaling decision to Infrastructure. Scaling mode determine whether
     * Scaling Up or Down
     */
    void setInstances(List<VirtualMachine> updatedInstances, int clockTickCount, double intervallDuratioInMilliSeconds,
            ScalingMode mode);
}
