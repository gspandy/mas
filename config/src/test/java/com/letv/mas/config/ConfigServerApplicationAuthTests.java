package com.letv.mas.config;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.io.File;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * 配置中心权限认证测试，包括管理端点权限、配置中心访问权限、GIT仓库访问权限
 *
 * Created by David.Liu on 2018/6/25.
 */
@SpringBootTest(classes = ConfigServerApplication.class, properties = {"spring.profiles.active:prod", "security.basic.enabled:true", "security.user.name:user_error",
        "security.user.password:pass_error", "management.security.enabled:true", "spring.cloud.config.server.git.username:config_error",
        "spring.cloud.config.server.git.password:pass_error"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfigServerApplicationAuthTests extends ConfigServerBaseTests {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置中心访问权限认证测试
     */
    @Test
    public void testConfigServerAccessPermission() {
        Map resource = new TestRestTemplate(USERNAME, PASSWORD).getForObject(baseUrl + "/letv-mas-client-prod.json", Map.class);
        log.info("get properties resource access config server --> " + resource);

        assertThatUnauthorized(resource);
    }

    /**
     * 管理端点访问权限测试
     */
    @Test
    public void testRefreshEndpointsAccessPermission() {
        Map resource = restTemplate.postForObject(baseUrl + "/refresh", null, Map.class);
        log.info("get properties resource from endpoints --> " + resource);

        assertThatUnauthorized(resource);
    }

    /**
     * GIT仓库访问权限测试
     */
    @Test
    public void testGitReposAccessPermission() {
        String letvMasClientConfig = LOCAL_REPOS + "/" + LETV_MAS_CLIENT;
        File localRepos = new File(letvMasClientConfig);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        assertTrue("Assert failed, local repos letv-mas-client file exist", !localRepos.exists());
    }

    private void assertThatUnauthorized(Map resource) {
        assertThat(resource).containsEntry("status", 401);
        assertThat(resource).containsEntry("error", "Unauthorized");
    }

}
