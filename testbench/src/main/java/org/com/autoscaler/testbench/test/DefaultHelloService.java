package org.com.autoscaler.testbench.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultHelloService implements HelloService {

    private final Logger log = LoggerFactory.getLogger(DefaultHelloService.class);

    @Value("${input.path.workload}")
    private String path;
    
    @Value("${input.path.workloadBla}")
    private String path2;

    @Override
    public void hello() {
        log.error(path2);
        log.error(path);
    }

}
