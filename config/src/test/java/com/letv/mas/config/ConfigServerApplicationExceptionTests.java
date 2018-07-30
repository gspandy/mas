package com.letv.mas.config;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * 配置中心异常测试，模拟GIT仓库不存在，关闭用户安全认证
 *
 * Created by David.Liu on 2018/6/25.
 */
@SpringBootTest(classes = ConfigServerApplication.class,
        properties = {"spring.profiles.active:prod", "spring.cloud.config.server.git.uri:http://legitlab.letv.cn/letv-mas-tv/config-not-exist.git", "security.basic.enabled:false"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfigServerApplicationExceptionTests extends ConfigServerBaseTests {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置中心仓库无法访问测试
     */
    @Test
    public void testUnableAccessConfig() {
        Map resource = restTemplate.getForObject(baseUrl + "/letv-mas-client-prod.json", Map.class);
        log.info("get properties resource from config server --> " + resource);

        assertThat(resource).containsKey("exception");
        String properties = (String) resource.get("exception");
        assertTrue(properties.contains("NoSuchRepositoryException"));
    }

    /**
     * 验证配置中心环境变量
     */
    @Test
    public void testSpecificGitUriProperties() {
        Map env = restTemplate.getForObject(baseUrl + "/env", Map.class);
        log.info("get env resource from endpoints --> " + env);

        String appConfigKey = "Inlined Test Properties";
        assertThat(env).containsKey(appConfigKey);
        Map<String, Object> properties = (Map<String, Object>) env.get(appConfigKey);
        assertThat(properties).containsEntry("spring.cloud.config.server.git.uri", "http://legitlab.letv.cn/letv-mas-tv/config-not-exist.git");
    }

    /**
     * 验证本地仓库为空
     */
    @Test
    public void testLocalEmptyRepos() {
        String letvMasClientConfig = LOCAL_REPOS + "/" + LETV_MAS_CLIENT;
        File localRepos = new File(letvMasClientConfig);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }
        assertTrue("Assert failed, local repos letv-mas-client file exist", !localRepos.exists());
    }

}
