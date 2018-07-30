package com.letv.mas.client.config;

import com.jayway.jsonpath.JsonPath;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 客户端及配置中心故障单元测试
 *
 * Created by David.Liu on 2018/6/26.
 */
@RunWith(SpringRunner.class)
public class ClientConfigFaultTests extends ClientConfigBaseTests{

    private final String ADD_HOSTS = "sudo sh -c \"echo '10.1.1.1 legitlab.letv.cn' >> /etc/hosts\"";
    private final String REVERT_HOSTS = "sudo sh -c \"sed -i '' 's/10.1.1.1 legitlab.letv.cn//g' /etc/hosts\"";
//    private final String REVERT_HOSTS = "sudo sh -c \"touch /etc/temp\";sudo sh -c \"sed '$ d' /etc/hosts > /etc/temp\";sudo sh -c \"cat /etc/temp > /etc/hosts\"";
    private final String PING_GIT_DOMAIN = "ping legitlab.letv.cn -c 3";
    private final String REQUEST_TIME_FLAG = "Request timeout";
    private final String CLIENT_PORT = CLIENT_PORTS.get(0);
    private final long TIME_TO_LIVE = 5000; // 健康检查周期
    private final String TEST_CONFIG_DOMAIN = "http://10.112.33.0/";
    private final String TEST_CONFIG_DOMAIN_URL = "http://10.112.33.0:%s/%s";
    private final String NGX_START = String.format(TEST_CONFIG_DOMAIN_URL, 9004, "/ngxstart");
    private final String NGX_STOP = String.format(TEST_CONFIG_DOMAIN_URL, 9003, "/ngxstop");
    private final String CONFIG_NODE_STOP = String.format(TEST_CONFIG_DOMAIN_URL, 9002, "/cstop");
    private final String CONFIG_NODE_START = String.format(TEST_CONFIG_DOMAIN_URL, 9001, "/cstart");

    @Before
    public void before() {
        exeCmd(REVERT_HOSTS);
    }

    /**
     * 模拟Git远程仓库故障后，客户端可以正常从配置中心获取配置并启动
     *
     * @throws InterruptedException
     */
    @Test
    public void testRemoteGitReposFault() throws InterruptedException {
        startConfig(CONFIG_DEFAULT_PORT);
        wait(CONFIG_DEFAULT_PORT);

        exeCmd(ADD_HOSTS);
        String pingResult = exeCmd(PING_GIT_DOMAIN);
        Assert.assertTrue(pingResult.contains(REQUEST_TIME_FLAG));

        startClient(CLIENT_PORT, String.format(LOCAL_URL, CONFIG_DEFAULT_PORT, ""));
        Assert.assertTrue(wait(CLIENT_PORT)); // 返回true 说明启动服务成功
    }

    /**
     * 模拟配置中心域名故障，健康检查为DOWN，启动后健康检查为UP
     */
    @Test
    public void testConfigCenterDownAndUp() throws InterruptedException {
        startClient(CLIENT_PORT, TEST_CONFIG_DOMAIN, TIME_TO_LIVE);
        wait(CLIENT_PORT);
        healthUP();

        sendRequest(NGX_STOP);
        Thread.sleep(TIME_TO_LIVE);
        healthDOWN();

        sendRequest(NGX_START);
        Thread.sleep(TIME_TO_LIVE);
        healthUP();
    }

    /**
     * 模拟配置中心节点故障，Git仓库配置更新后/bus/refresh后可以获取最新配置
     */
    @Test
    public void testConfigCenterNodeDown() throws InterruptedException {
        startClient(CLIENT_PORT, TEST_CONFIG_DOMAIN, TIME_TO_LIVE);
        wait(CLIENT_PORT);
        healthUP();

        sendRequest(CONFIG_NODE_STOP);
        Thread.sleep(TIME_TO_LIVE);
        healthUP();

        assertThat(clientVersion(CLIENT_PORT)).isEqualTo(configVersion());
        cloneAndHandleGitRepo();

        assertThat(clientVersion(CLIENT_PORT)).isNotEqualTo(configVersion());
        postBusRefresh();

        Thread.sleep(DEFAULT_TIME);
        assertThat(clientVersion(CLIENT_PORT)).isEqualTo(configVersion());
        sendRequest(CONFIG_NODE_START);
    }

    private void healthUP() {
        assertThat(healthStatus()).isNotEmpty().isEqualToIgnoringCase("UP");
    }

    private void healthDOWN() {
        assertThat(healthStatus()).isNotEmpty().isEqualToIgnoringCase("DOWN");
    }

    private String healthStatus() {
        String health = restTemplate.getForObject(String.format(LOCAL_URL, CLIENT_PORT, "/health"), String.class);
        return JsonPath.read(health, "$.configServer.status");
    }

    @After
    public void after() {
        shutdown(CONFIG_DEFAULT_PORT, CLIENT_PORT);
        sendRequest(NGX_START);
        sendRequest(CONFIG_NODE_START);
        exeCmd(REVERT_HOSTS);
        clearTempGitRepo();
    }

}