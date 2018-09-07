package com.letv.mas.caller.circuitbreaker.service;


import com.letv.mas.caller.circuitbreaker.model.ResourceInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CircuitbreakerService {

    private static final Logger LOG = LoggerFactory.getLogger(CircuitbreakerService.class);

    @Autowired
    private UnstableService unstableService;

    private static AtomicInteger EXEC_CNT = new AtomicInteger(0);

    @HystrixCommand(commandKey = "getResWithDelay", threadPoolKey = "cbThreadPool", fallbackMethod = "getDefaultRes")
    public ResourceInfo getInfoFromRemoteAPI(long delayMs) {
        LOG.info("GET INFO FROM REMOTE API WITH DELAY={} ms", delayMs);
        EXEC_CNT.getAndIncrement();
        return this.unstableService.getResWithFixedDelay(delayMs);
    }


    @HystrixCommand(commandKey = "getResIsoByThread", threadPoolKey = "cbThreadPool")
    public ResourceInfo getResourceIsoByThread(long delayMs) {
        return this.unstableService.getResWithFixedDelay(delayMs);
    }


    @HystrixCommand(commandKey = "getResIsoBySemaphore")
    public ResourceInfo getResourceIsoBySemaphore(long delayMs) {
        return this.unstableService.getResWithFixedDelay(delayMs);
    }

    @HystrixCommand(commandKey = "getResWithDelay", threadPoolKey = "cbThreadPool", fallbackMethod = "getDefaultRes")
    public ResourceInfo getResourceWithUncheckedException(long delayMs) throws Exception {
        int i = 1 / 0;
        return new ResourceInfo("111");
//        return this.unstableService.getResWithFixedDelay(1L);
    }

    private ResourceInfo getDefaultRes1(long delayMs) {
        return new ResourceInfo(ResourceInfo.DEFAULT_RESOURCE_ID);
    }

    public int getCntValue() {
        return EXEC_CNT.intValue();
    }

    public void resetCnt() {
        EXEC_CNT.set(0);
    }

}
