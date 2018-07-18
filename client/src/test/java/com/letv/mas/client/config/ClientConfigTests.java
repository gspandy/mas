package com.letv.mas.client.config;

import com.jayway.jsonpath.JsonPath;
import com.letv.mas.client.EurekaClientApplication;
import com.letv.mas.client.config.model.bean.EurekaClient;
import com.letv.mas.client.config.model.bean.User;
import net.minidev.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by David.Liu on 2018/6/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaClientApplication.class, properties = {"spring.profiles.active:prod"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientConfigTests {

    private static final String DEFAULT_ZONE = "http://letvMasManager:admin20180605@10.129.29.86:8015/eureka";

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int basePort;

    private String baseUrl;

    @Before
    public void bootstrap() throws Exception {
        baseUrl= String.format("http://localhost:%d/", this.basePort);
    }

    @Test
    public void testCustomClientConfig() {
        User user = restTemplate.getForObject(baseUrl + "/user", User.class);
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("test");
        assertThat(user.getId()).isEqualTo(100);
        assertThat(user.getDesc()).contains("Hello world");
    }

    @Test
    public void testEurekaClientConfig() {
        EurekaClient eurekaClient = restTemplate.getForObject(baseUrl + "/eurekaclient", EurekaClient.class);
        assertThat(eurekaClient).isNotNull();
        assertThat(eurekaClient.getRegion()).isEqualTo("cn-bj");
        assertThat(eurekaClient.getAvailabilityZones().get(eurekaClient.getRegion())).contains("sjs-1");
        assertThat(eurekaClient.getServiceUrl().get("defaultZone")).contains(DEFAULT_ZONE);
    }

    /**
     * 自定义的topic正确性测试
     */
    @Test
    public void testCustomKafkaTopics() throws InterruptedException {
        String channels = restTemplate.getForObject(baseUrl + "/channels", String.class);
        String destination = restTemplate.getForObject(baseUrl + "/destination", String.class);

        String destinationForChannels = JsonPath.read(channels, "$.inputs.springCloudBusInput.destination");
        assertThat(destination).isNotEmpty().isEqualToIgnoringCase(destinationForChannels);
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

}