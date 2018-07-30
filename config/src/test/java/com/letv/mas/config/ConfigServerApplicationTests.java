package com.letv.mas.config;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * 配置中心启动测试，开启用户权限认证
 *
 * Created by leeco on 18/4/27.
 */
@SpringBootTest(classes = ConfigServerApplication.class, properties = {"security.basic.enabled:true", "spring.profiles.active:prod"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfigServerApplicationTests extends ConfigServerBaseTests {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void contextLoads() {
        log.info("context loads..");
    }

    /**
     *  访问配置中心验证资源属性
     */
    @Test
    public void testAccessClientProperties() throws InterruptedException {
        Map resource = new TestRestTemplate(USERNAME, PASSWORD).getForObject(baseUrl + "/letv-mas-client-prod.json", Map.class);
        log.info("get properties resource from config server --> " + resource);

        assertThat(resource).containsKey("spring");
        Map<String, Object> springProps = (Map<String, Object>) resource.get("spring");
        assertThat(springProps).containsKeys("application");
        Map<String, Object> appProps = (Map<String, Object>) springProps.get("application");
        assertThat(appProps).containsEntry("name", LETV_MAS_CLIENT);
    }

    /**
     * 配置项对称加密测试
     * 加密方式: curl 'http://10.112.33.0:8021/encrypt' -d 'encrypt123456'
     * test.password: '{cipher}94cdbba34951d881d6cf947e3785ae34d7a658bd5737fbbc5bddbbe4ff020ded'
     */
    @Test
    public void testSymmetricEncryptPropertiesByJson() {
        Map resource = new TestRestTemplate(USERNAME, PASSWORD).getForObject(baseUrl + "/letv-mas-client-dev.json", Map.class);
        log.info("get properties resource from config server --> " + resource);

        assertThat((Map<String, Object>)resource.get("test")).containsEntry("password", "encrypt123456");
    }

    /**
     * 配置项对称加密测试
     * 加密方式: curl 'http://10.112.33.0:8021/encrypt' -d 'encrypt123456'
     * test.password: '{cipher}94cdbba34951d881d6cf947e3785ae34d7a658bd5737fbbc5bddbbe4ff020ded'
     */
    @Test
    public void testSymmetricEncryptPropertiesByRest() {
        String resource = new TestRestTemplate(USERNAME, PASSWORD).getForObject(baseUrl + "/letv-mas-client/dev/master", String.class);
        log.info("get properties resource from config server --> " + resource);

        String name = JsonPath.read(resource, "$.propertySources[0].name");
        assertThat(name).contains("letv-mas-client-dev.yml");

        Map<String, Object> map = JsonPath.read(resource, "$.propertySources[0].source");
        assertThat(map).isNotEmpty().containsEntry("test.password", "encrypt123456");
    }

    /**
     * 自定义的topic正确性测试
     */
    @Test
    public void testCustomKafkaTopics() {
        String resource = new TestRestTemplate(USERNAME, PASSWORD).getForObject(baseUrl + "/channels", String.class);
        log.info("get properties resource from config server --> " + resource);

        String destination = JsonPath.read(resource, "$.inputs.springCloudBusInput.destination");
        assertThat(destination).isNotEmpty().isEqualToIgnoringCase("tvvideo.springcloud.config");
    }

    /**
     * 验证配置中心环境变量
     */
    @Test
    public void testAccessEnvEndpoints() {
        Map env = restTemplate.getForObject(baseUrl + "/env", Map.class);
        log.info("get env resource from endpoints --> " + env);

        assertThat(env).containsKey("server.ports");
        Map<String, Object> properties = (Map<String, Object>) env.get("server.ports");
        assertThat(properties).containsEntry("local.server.port", localPort);
    }

    /**
     * 验证本地仓库正常拉取远程仓库配置
     */
    @Test
    public void testAccessLocalRepos() throws InterruptedException {
        String resource = new TestRestTemplate(USERNAME, PASSWORD).getForObject(baseUrl + "/letv-mas-client/dev/master", String.class);
        log.info("get properties resource from config server --> " + resource);

        String letvMasClientConfig = LOCAL_REPOS + "/" + LETV_MAS_CLIENT;
        long nowMillis = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        File localRepos = new File(letvMasClientConfig);

        Thread.sleep(200);

        assertTrue("Assert failed, local repos letv-mas-client file do not exists", localRepos.exists());
        assertTrue("Assert failed, the last modification time of local repos is not correct", localRepos.lastModified() + 3600  > nowMillis);
    }

    /**
     * 验证健康检查状态
     */
    @Test
    public void testAccessHealthStatus() {
        Map health = restTemplate.getForObject(baseUrl + "/health", Map.class);
        log.info("get health resource from endpoints --> " + health);

        assertThat(health).containsKey("configServer");
        Map<String, Object> properties = (Map<String, Object>) health.get("configServer");
        assertThat(properties).containsEntry("status", "UP");
    }

    /**
     * 前置条件：letv-mas-client客户端10.112.33.0:8021已启动
     * 发送消息/bus/refresh消息，访问trace接口获得send和ack信息
     *
     [{
         "timestamp": "2018-07-10T10:35:32.222+0000",
         "info": {
         "signal": "spring.cloud.bus.ack",
         "event": "RefreshRemoteApplicationEvent",
         "id": "153afe04-e4f8-462e-b23c-9a697298a7db",
         "origin": "letv-mas-client:dev:10.112.33.0",
         "destination": "letv-mas-client:dev:10.112.33.0"
         }
       },
      {
         "timestamp": "2018-07-10T10:35:31.213+0000",
         "info": {
         "signal": "spring.cloud.bus.sent",
         "type": "RefreshRemoteApplicationEvent",
         "id": "153afe04-e4f8-462e-b23c-9a697298a7db",
         "origin": "letv-mas-config:dev:127.0.0.1",
         "destination": "letv-mas-client:dev:10.112.33.0"
         }
      }]
     */
    @Test
    public void testKafkaMessageSendAndAck() throws InterruptedException {
        String destination = "letv-mas-config:prod:10.112.33.0";
        restTemplate.postForObject( baseUrl + "/bus/refresh?destination=" + destination, null, Object.class);

        Thread.sleep(500);
        List<Map<String, Map<String, String>>> trace = restTemplate.getForObject(baseUrl + "/trace", List.class);
        List<String> cloudBus = new ArrayList<>();
        for (Map<String, Map<String, String>> map : trace) {
            Map<String, String> info =  map.get("info");
            if (null == map.get("info") || map.get("info").get("signal") == null)  {
                continue;
            }
            if (info.get("signal").equals("spring.cloud.bus.sent")) {
                assertThat(info.get("destination")).isEqualTo(destination);
                cloudBus.add(info.get("signal"));
            } else if (info.get("signal").equals("spring.cloud.bus.ack")) {
                assertThat(info.get("origin")).isEqualTo(destination);
                cloudBus.add(info.get("signal"));
            }
        }

        assertThat(cloudBus).isNotEmpty().contains("spring.cloud.bus.sent").contains("spring.cloud.bus.ack");
    }
}