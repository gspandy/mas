package com.letv.mas.client.config;

import com.jayway.jsonpath.JsonPath;
import com.letv.mas.client.EurekaClientApplication;
import net.minidev.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 单机客户端版本一致性单元测试
 *
 * Created by David.Liu on 2018/6/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaClientApplication.class, properties = {"spring.profiles.active:configprod", "local.server.port:18882"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientConfigVersionTests extends ClientConfigBaseTests {

    @LocalServerPort
    private int basePort;

    private String baseUrl;

    @Before
    public void bootstrap() throws Exception {
        baseUrl= String.format("http://localhost:%d/", this.basePort);
    }

    /**
     * 健康检查测试
     */
    @Test
    public void testConfigServerHealth() {
        String health = restTemplate.getForObject(baseUrl + "/health", String.class);

        String status = JsonPath.read(health, "$.configServer.status");
        assertThat(status).isNotEmpty().isEqualToIgnoringCase("UP");

        JSONArray propertySources = JsonPath.read(health, "$.configServer.propertySources");
        propertySources.remove(0); // remove 0 index: configClients
        propertySources.forEach(property -> assertThat((String)property).contains("config.git/letv-mas-client"));
    }

    /**
     * 单机客户端使用的git版本一致性测试
     * 启动容器->测试版本一致->git仓库更新版本->测试版本不一致->bus/refresh刷新->测试版本一致->清理环境
     */
    @Test
    public void testConfigClientVersionConsistencyByBusRefresh() throws InterruptedException {
        assertThat(clientVersion()).isEqualTo(configVersion());
        cloneAndHandleGitRepo();

        assertThat(clientVersion()).isNotEqualTo(configVersion());
        postBusRefresh();

        Thread.sleep(DEFAULT_TIME);
        assertThat(clientVersion()).isEqualTo(configVersion());
    }

    @After
    public void after() {
        clearTempGitRepo();
    }

    /**
     * 单机客户端使用的git版本一致性测试
     * 启动容器->测试版本一致->git仓库更新版本->测试版本不一致->refresh刷新->测试版本一致->清理环境
     */
    @Test
    public void testConfigClientVersionConsistencyByRefresh() throws InterruptedException {
        assertThat(clientVersion()).isEqualTo(configVersion());
        cloneAndHandleGitRepo();

        assertThat(clientVersion()).isNotEqualTo(configVersion());
        restTemplate.postForObject( baseUrl + "/refresh", null, Object.class);

        Thread.sleep(DEFAULT_TIME / 10);
        assertThat(clientVersion()).isEqualTo(configVersion());
    }

    private String clientVersion() {
        Map envMap = restTemplate.getForObject(baseUrl + "env", Map.class);
        return ((Map<String, String>)envMap.get("configService:configClient")).get("config.client.version");
    }

}