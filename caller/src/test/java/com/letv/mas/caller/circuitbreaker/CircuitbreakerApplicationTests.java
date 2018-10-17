package com.letv.mas.caller.circuitbreaker;

import com.alibaba.fastjson.JSON;
import com.letv.mas.caller.demo.ServiceRibbonApplication;
import com.letv.mas.caller.circuitbreaker.model.ResourceInfo;
import com.letv.mas.caller.circuitbreaker.service.CircuitbreakerService;
import com.netflix.util.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceRibbonApplication.class, properties = {"spring.profiles.active:hystrixprod",
        "CALLER_SERVER_PORT:8080", "local.server.port:8080", "CONFIG_SERVER_DOMAIN:http://config.mas.letv.cn",
        "CALLER_PROFILE:hystrixprod", "EUREKA_SERVER_LIST:http://letvMasManager:admin20180605@10.112.34.124:8015/eureka,http://letvMasManager:admin20180605@10.112.34.127:8015/eureka,http://letvMasManager:admin20180605@10.129.29.86:8015/eureka,http://letvMasManager:admin20180605@10.129.29.90:8015/eureka"}, webEnvironment = SpringBootTest.WebEnvironment
        .DEFINED_PORT)
public class CircuitbreakerApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(CircuitbreakerApplicationTests.class);

    private static final int SAMPLE_AMOUNT = 20;

    private static final int BENCHMARK_SAMPLE_AMOUNT = 10000;

    private static long TRIGGER_OFFSET = 30L;

    @Value("${hystrix.command.getResWithDelay.execution.isolation.thread.timeoutInMilliseconds}")
    private String commandTimeout;


    @Value("${hystrix.command.getResWithDelay.circuitBreaker.requestVolumeThreshold}")
    private String requestVolumeThreshold;

    @Value("${hystrix.command.getResWithDelay.metrics.rollingStats.timeInMilliseconds}")
    private String clearMilliseconds;

    @Value("${hystrix.command.getResWithDelay.circuitBreaker.sleepWindowInMilliseconds}")
    private String sleepWindowMilliseconds;

    @Autowired
    private CircuitbreakerService circuitbreakerService;

    @Test
    public void circuitBreakerStateTest() throws InterruptedException {
        this.circuitbreakerService.resetCnt();
        ResourceInfo resourceInfo = this.circuitbreakerService.getInfoFromRemoteAPI(Long.valueOf(commandTimeout) - TRIGGER_OFFSET);
        LOG.info("GET RES FROM REMOTE API, RSP={}", JSON.toJSONString(resourceInfo));
        Assert.assertNotEquals(ResourceInfo.DEFAULT_RESOURCE_ID, resourceInfo.getResourceId());
        this.circuitbreakerService.resetCnt();
        Thread.sleep(Long.valueOf(clearMilliseconds));
        // circuit breaker open when executing
        for (int i = 0; i < SAMPLE_AMOUNT; i++) {
            ResourceInfo resInfo = this.circuitbreakerService.getInfoFromRemoteAPI(Long.valueOf(commandTimeout) +
                    TRIGGER_OFFSET);
            Assert.assertEquals(ResourceInfo.DEFAULT_RESOURCE_ID, resInfo.getResourceId());
            LOG.info("GET RES FROM REMOTE API, RSP={}", JSON.toJSONString(resInfo));
        }
        Assert.assertTrue(SAMPLE_AMOUNT > this.circuitbreakerService.getCntValue());
        // circuit breaker half-open
        Thread.sleep(Long.valueOf(sleepWindowMilliseconds));
        resourceInfo = this.circuitbreakerService.getInfoFromRemoteAPI(Long.valueOf(commandTimeout) - TRIGGER_OFFSET);
        Assert.assertNotEquals(ResourceInfo.DEFAULT_RESOURCE_ID, resourceInfo.getResourceId());
    }

    @Test
    public void isolationStrategyBenchmarkTest() {
        vmWarmUp();
        List<Pair<String, Long>> cases = new ArrayList<Pair<String, Long>>() {
            {
                add(new Pair<String, Long>("semaphore", 1L));
                add(new Pair<String, Long>("thread", 1L));
                add(new Pair<String, Long>("semaphore", 5L));
                add(new Pair<String, Long>("thread", 5L));
                add(new Pair<String, Long>("semaphore", 30L));
                add(new Pair<String, Long>("thread", 30L));
                add(new Pair<String, Long>("semaphore", 50L));
                add(new Pair<String, Long>("thread", 50L));
            }
        };
        StringBuilder report = new StringBuilder("\n");

        for (Pair<String, Long> test : cases) {
            long cost = executeBatchWithFixStrategyAndDelay(test.first(), test.second());
            long frameworkCost = cost - (BENCHMARK_SAMPLE_AMOUNT * test.second());
            float frameworkCostPerCmd = (float) frameworkCost / BENCHMARK_SAMPLE_AMOUNT;
            float frameworkCostPercentage = frameworkCostPerCmd / test.second();
            report.append(String.format("%s TEST WITH %s ms DELAY, FRAMEWORK-COST-PER-CMD=%s, " +
                            "FRAMEWORK-COST-PERCENTAGE=%s", test.first(),
                    test.second(), frameworkCostPerCmd, frameworkCostPercentage));
            report.append("\n");
        }
        LOG.info(report.toString());


    }


    private long executeBatchWithFixStrategyAndDelay(String strategy, long delayMs) {
        long begin = System.currentTimeMillis();
        if ("semaphore".equalsIgnoreCase(strategy)) {
            for (int i = 0; i < BENCHMARK_SAMPLE_AMOUNT; i++) {
                this.circuitbreakerService.getResourceIsoBySemaphore(delayMs);
            }
        } else if ("thread".equalsIgnoreCase(strategy)) {
            for (int i = 0; i < BENCHMARK_SAMPLE_AMOUNT; i++) {
                this.circuitbreakerService.getResourceIsoByThread(delayMs);
            }
        }
        long end = System.currentTimeMillis();
        return end - begin;
    }

    private void vmWarmUp() {
        for (int i = 0; i < 10000; i++) {
            this.circuitbreakerService.getResourceIsoBySemaphore(1L);
            this.circuitbreakerService.getResourceIsoByThread(1L);
        }
    }
}
