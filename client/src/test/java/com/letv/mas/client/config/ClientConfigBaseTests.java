package com.letv.mas.client.config;

import org.assertj.core.util.Lists;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Created by David.Liu on 2018/6/26.
 */
public class ClientConfigBaseTests {

    protected final String USER = "config";
    protected final String PASSWD = "config2018";
    protected final String CONFIG_DOMAIN = "http://config.mas.letv.cn/%s";
    protected final int DEFAULT_TIME = 2000;
    protected final String CONFIG_DEFAULT_PORT = "8021";
    protected final List<String> CLIENT_PORTS = Lists.newArrayList("8901", "8902");
    protected final TestRestTemplate restTemplate = new TestRestTemplate();
    protected final String LOCAL_URL = "http://localhost:%s/%s";
    private final String BUS_REFRESH_URL = String.format(CONFIG_DOMAIN, "bus/refresh?destination=letv-mas-client-configprod:**");
    private final String APP_PROFILE_LABEL_URL = "http://config.mas.letv.cn/letv-mas-client/configprod/master";
    private final String BASE_DIR = System.getProperty("user.dir") + "/src/test/java/com/letv/mas/client/config/";
    private final String CLIENT_JAR_PATH = System.getProperty("user.dir") + "/target/client-1.0-SNAPSHOT.jar";
    private final String CONFIG_JAR_PATH = CLIENT_JAR_PATH.replace("client", "config");
    private final int MAX_RETRY = 5;

    protected void cloneAndHandleGitRepo() {
        CommandHandler.exec(new String[]{"/bin/sh", "-c", "sh gitrepo_handle.sh"}, BASE_DIR);
    }

    protected void clearTempGitRepo() {
        CommandHandler.exec(new String[]{"/bin/sh", "-c", "rm -rf config"}, BASE_DIR);
    }

    protected String clientVersion(String port) {
        Map envMap = restTemplate.getForObject(String.format(LOCAL_URL, port, "/env") , Map.class);
        return ((Map<String, String>)envMap.get("configService:configClient")).get("config.client.version");
    }

    protected String configVersion() {
        Map configMap = new TestRestTemplate(USER, PASSWD).getForObject(APP_PROFILE_LABEL_URL, Map.class);
        return (String)configMap.get("version");
    }

    protected void postBusRefresh() {
        new TestRestTemplate(USER, PASSWD).postForObject( BUS_REFRESH_URL, null, Object.class);
    }

    protected void startClient(String port) {
        exeCmd("java -jar " + CLIENT_JAR_PATH + " --spring.profiles.active=configprod --CLIENT_SERVER_PORT=" + port + " &");
    }

    protected void startClient(String port, String configUrl) {
        exeCmd("java -jar " + CLIENT_JAR_PATH + " --spring.profiles.active=configprod --CLIENT_SERVER_PORT=" + port + " --CONFIG_SERVER_DOMAIN=" + configUrl + " &");
    }

    protected void startClient(String port, String configUrl, long timeToLive) {
        exeCmd("java -jar " + CLIENT_JAR_PATH + " --spring.profiles.active=configprod --CLIENT_SERVER_PORT=" + port + " --CONFIG_SERVER_DOMAIN=" + configUrl + " --spring.cloud.config.watcher.enabled=false --health.config.timeToLive=" + timeToLive + " &");
    }

    protected void startConfig(String port) {
        exeCmd("java -jar " + CONFIG_JAR_PATH + " --CLIENT_SERVER_PORT=" + port + " &");
    }

    protected void shutdown(String port) {
        try {
            new TestRestTemplate().postForObject(String.format(LOCAL_URL, port, "/shutdown"), null, String.class);
            Thread.sleep(DEFAULT_TIME);
        } catch (Exception e) {
        }
    }

    protected void shutdown(String... ports) {
        Arrays.stream(ports).forEach(p -> {
            shutdown(p);
        });
    }

    protected void sendRequest(String command) {
        restTemplate.getForObject(command, String.class);
    }

    protected String exeCmd(String cmd) {
        return CommandHandler.exec(new String[]{"/bin/sh", "-c", cmd});
    }

    protected boolean wait(String port) {
        boolean result = false;
        int count = 0;
        while (count < MAX_RETRY) {
            try {
                sleep(DEFAULT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 检测接口
            try {
                ResponseEntity<Map> response =  new TestRestTemplate().getForEntity(String.format(LOCAL_URL, port, "/info"), Map.class);
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    result = true;
                    break;
                }
            } catch (ResourceAccessException e) {
                continue;
            }
            count++;
        }
        return result;
    }
}