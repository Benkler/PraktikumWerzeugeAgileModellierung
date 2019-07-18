package org.com.autoscaler.scaler;

public enum ScalingMode {
   /**
    * Boot VMs 
    */
    SCALE_UP,
    
    /**
     * Shut down VMs
     */
    SCALE_DOWN;
}
