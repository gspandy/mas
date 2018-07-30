package com.letv.mas.client.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * 客户单集群+基于消息总线配置刷新单元测试
 *
 * Created by David.Liu on 2018/6/26.
 */
@RunWith(SpringRunner.class)
public class ClientConfigBusTests extends ClientConfigBaseTests{

    private final Logger logger = LoggerFactory.getLogger(ClientConfigBusTests.class);

    private final int THREAD_NUM = 5;
    private final int INTERNAL_THREAD_NUM = 5;
    private final int SUM_THREAD_NUM = THREAD_NUM * INTERNAL_THREAD_NUM;
    private final int SEDS = 3;
    private final String CLIENT_LOG = "/letv/logs/mas/client/letv-mas-client-configprod";

    // 启动client并清理日志
    @Before
    public void before() {
        CLIENT_PORTS.forEach(p -> {
            startClient(p);
            wait(p);
            exeCmd(":> " + CLIENT_LOG + "-" + p + ".log");
        });
    }

    /*
     * 消息总线发送消息压力测试，验证消息可靠性
     */
    @Test
    public void testBusRefreshPerf() throws InterruptedException {
        // 模拟并发发起基于消息总线的配置刷新
        ExecutorService service = Executors.newFixedThreadPool(THREAD_NUM);
        for (int t = 0; t < THREAD_NUM; t++)  {
            service.submit(() -> {
                for (int sub = 0; sub < INTERNAL_THREAD_NUM; sub++) {
                    postBusRefresh();
                    logger.info("rest template send bus refresh request..");
                }
            });
        }

        service.awaitTermination(SUM_THREAD_NUM * SEDS, TimeUnit.SECONDS);

        // 验证消息确认数量
        CLIENT_PORTS.forEach(p -> {
            String result = exeCmd("cat " + CLIENT_LOG + "-" + p + ".log" + "| grep 'Keys refreshed' | grep RefreshListener | wc -l");
            logger.info("refresh listener receive message number:{}, SUM_THREAD_NUM:{}", result.trim(), SUM_THREAD_NUM);
            assertTrue(result.trim().equals(String.valueOf(SUM_THREAD_NUM)));
        });

        sleep(DEFAULT_TIME);
    }

    /**
     * 远程仓库更新，验证git版本一致性
     *
     * @throws InterruptedException
     */
    @Test
    public void testClusterClientByBusRefresh() throws InterruptedException {
        CLIENT_PORTS.forEach(p -> {
            assertThat(clientVersion(p)).isEqualTo(configVersion());
        });

        cloneAndHandleGitRepo();

        CLIENT_PORTS.forEach(p -> {
            assertThat(clientVersion(p)).isNotEqualTo(configVersion());
        });
        postBusRefresh();

        Thread.sleep(DEFAULT_TIME);
        CLIENT_PORTS.forEach(p -> {
            assertThat(clientVersion(p)).isEqualTo(configVersion());
        });

        clearTempGitRepo();
    }

    // 关闭client
    @After
    public void after() {
        CLIENT_PORTS.forEach(p -> {
            shutdown(p);
        });
    }

}