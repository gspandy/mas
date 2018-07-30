package com.letv.mas.caller.circuitbreaker.service;

import com.letv.mas.caller.circuitbreaker.model.ResourceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UnstableService {

    private static final Logger LOG = LoggerFactory.getLogger(UnstableService.class);

    public ResourceInfo getResWithFixedDelay(long delayMs) {
        long begin = System.currentTimeMillis();
        try {
            Thread.sleep(delayMs);
            LOG.info("COST={} ms", System.currentTimeMillis() - begin);
            return new ResourceInfo(UUID.randomUUID().toString());
        } catch (InterruptedException e) {
            LOG.info("Execute Thread Interrupted");
            throw new RuntimeException("Thread Interrupted");
        }

    }
}
