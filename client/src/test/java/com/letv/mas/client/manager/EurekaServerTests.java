package com.letv.mas.client.manager;


import com.jayway.jsonpath.JsonPath;
import com.letv.mas.client.EurekaClientApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by MaNing on 2018/7/6.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.JVM)
@SpringBootTest(classes = EurekaClientApplication.class, properties = {"spring.profiles.active:managerdev", "local.server.port:9993"}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ConditionalOnProperty(value = "spring.cloud.eureka.enabled", havingValue = "true", matchIfMissing = false)
public class EurekaServerTests {

    private final Logger logger = LoggerFactory.getLogger(EurekaServerTests.class);

    private static final String APP_NAME = "EUREKA-SERVER-TEST-CLIENT";
    private static final String TEST_CLIENT_PORT = "9992";
    private static String CLIENT_PORT = "9993";

    private static final String TEST_CLIENT_PATH = System.getProperty("user.dir") + "/target/client-1.0-SNAPSHOT.jar";

    private static final String TEST_CLIENT_PROFILE = "managercdev";

    private static final String INSTANCE_ID = "127.0.0.1:" + TEST_CLIENT_PORT;
    private static final String TEST_EUREKA_CLIENT = "http://127.0.0.1:" + TEST_CLIENT_PORT;

    private static String EUREKA_CLIENT = "http://127.0.0.1:" + CLIENT_PORT;

    private static final String CONFIG_SERVER = "http://config:config2018@config.mas.letv.cn";

    private static final String BASE_DIR = System.getProperty("user.dir") + "/src/test/java/com/letv/mas/client/manager/";


    private static final String SJS_1_ZONE = "http://letvMasManager:admin20180605@10.129.29.86:8015";

    private static final String SJS_2_ZONE = "http://letvMasManager:admin20180605@10.129.29.90:8015";

    private static final String DX_1_ZONE = "http://letvMasManager:admin20180605@10.112.34.124:8015";

    private static final String DX_2_ZONE = "http://letvMasManager:admin20180605@10.112.34.127:8015";

    private static final String DX_1_ZONE_IP = "10.112.34.124";

    private static final String CONFIG_OLD_STR = "testIp";

    private static final String CONFIG_NEW_STR = "testServerIp";

    private static final int EUREKA_SERVER_EVICTION_CACHE_TIME = 5;//eviction-interval-timer-in-ms

    private static final int LEASE_EXPIRATION_DURATION = 30;//lease-expiration-duration

    private static final int LEASE_RENEWAL_INTERVAL = 10;//lease-renewal-interval

    private static final int REGISTRY_FETCH_INTERVAL = 10;//registry-fetch-interval-seconds

    private static final int NET_TIME = 2;

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${local.server.port}")// 注入端口号
    private int port;

    @Before
    public void bootstrap() throws Exception {
        logger.info(" bootstrap ----- ");
        fastShutDownServer(DX_2_ZONE, APP_NAME, INSTANCE_ID, TEST_CLIENT_PORT);
        try {
            Thread.sleep(REGISTRY_FETCH_INTERVAL * 1000);
        } catch (Exception e) {

        }
        logger.info(" bootstrap over ----- ");
    }

    /**
     * 高可用.注册中心节点间同步，及client是否能感知
     *
     * @throws Exception
     */
    @Test
    public void testEurekaServerPeerSynchronization() {
        logger.info(" testEurekaServerPeerSynchronization ----- ");

        boolean dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(false);

        boolean dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(false);

        boolean sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(false);

        boolean sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(false);

        boolean client = isContainInstanceForEurekaClient(EUREKA_CLIENT, APP_NAME, INSTANCE_ID);
        assertThat(client).isEqualTo(false);

        logger.info(" testEurekaServerPeerSynchronization test begin----- ");

        startupClient();

        int wait = NET_TIME + NET_TIME + 1;//一次心跳，一次注册，有可能心跳在注册之前
        logger.info(" testEurekaServerPeerSynchronization test wait server up for eureka server----- " + wait);
        try {
            Thread.sleep(wait * 1000);
        } catch (Exception e) {

        }

        dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(true);

        dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(true);

        sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(true);

        sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(true);

        logger.info(" testEurekaServerPeerSynchronization test wait server up for eureka client----- " + (REGISTRY_FETCH_INTERVAL + 1));
        try {
            Thread.sleep((REGISTRY_FETCH_INTERVAL + 1) * 1000);
        } catch (Exception e) {

        }

        client = isContainInstanceForEurekaClient(EUREKA_CLIENT, APP_NAME, INSTANCE_ID);
        assertThat(client).isEqualTo(true);

        shutDownClient();

        wait = NET_TIME + 1;//主动下线
        logger.info(" testEurekaServerPeerSynchronization test wait server down for eureka server----- " + wait);
        try {
            Thread.sleep(wait * 1000);//eviction-interval-timer-in-ms: 5 + 1
        } catch (Exception e) {

        }

        dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(false);

        dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(false);

        sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(false);

        sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(false);
        logger.info(" testEurekaServerPeerSynchronization test wait client down for eureka server----- " + (REGISTRY_FETCH_INTERVAL + 1));
        try {
            Thread.sleep((REGISTRY_FETCH_INTERVAL + 1) * 1000);
        } catch (Exception e) {

        }

        client = isContainInstanceForEurekaClient(EUREKA_CLIENT, APP_NAME, INSTANCE_ID);
        assertThat(client).isEqualTo(false);

    }

    /**
     * 一致性.接入方节点故障感应可预期
     *
     * @throws Exception
     */

    @Test
    public void testFastKnowServerDown() {
        logger.info(" testFastKnowServerDown ----- ");
        startupClient();
        int wait = NET_TIME + NET_TIME + 1;//一次心跳，一次注册，有可能心跳在注册之前
        logger.info(" testFastKnowServerDown -----  test wait server up for eureka server----- " + wait);
        try {
            Thread.sleep(wait * 1000);
        } catch (Exception e) {

        }
        boolean dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(true);

        boolean dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(true);

        boolean sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(true);

        boolean sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(true);

        logger.info(" testFastKnowServerDown -----  test wait server up for eureka client----- " + (REGISTRY_FETCH_INTERVAL + 1));
        try {
            Thread.sleep((REGISTRY_FETCH_INTERVAL + 1) * 1000);//registry-fetch-interval-seconds:10 +1
        } catch (Exception e) {

        }

        boolean client = isContainInstanceForEurekaClient(EUREKA_CLIENT, APP_NAME, INSTANCE_ID);
        assertThat(client).isEqualTo(true);

        logger.info(" testFastKnowServerDown -----  killClientWithSingEurekaServer ");
        killClient();

        long begin = System.currentTimeMillis();

        //因为eureka server对于过期采用的是分批下线，一次下线的比例为0.15
        wait = LEASE_EXPIRATION_DURATION + EUREKA_SERVER_EVICTION_CACHE_TIME + 1;
        logger.info(" testFastKnowServerDown -----  test wait server down for eureka server-----" + wait);
        try {
            Thread.sleep(wait * 1000);//lease-expiration-duration-in-seconds: 30 + eviction-interval-timer-in-ms: 5 *2  + 1
        } catch (Exception e) {

        }

        dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        if (dx_2) {
            getDownTimeFormEurekaServer(begin, DX_2_ZONE, APP_NAME, INSTANCE_ID, false);
        }
        assertThat(dx_2).isEqualTo(false);

        dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(false);

        sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(false);

        sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(false);

        logger.info(" testFastKnowServerDown -----  test wait server down for eureka client-----" + (REGISTRY_FETCH_INTERVAL + 1));

        try {
            Thread.sleep((REGISTRY_FETCH_INTERVAL + 1) * 1000);//registry-fetch-interval-seconds:10 +1
        } catch (Exception e) {

        }

        client = isContainInstanceForEurekaClient(EUREKA_CLIENT, APP_NAME, INSTANCE_ID);
        assertThat(client).isEqualTo(false);
    }

    /**
     * 分区容忍度性.接入方节点就近选取注册中心
     * 线上4台eureka server的机器已经做过时钟同步
     */

    @Test
    public void testEurekaClientPriorityFindSameZoneEurekaServer() {

        logger.info(" testEurekaClientPriorityFindSameZoneEurekaServer ----- ");

        boolean dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(false);

        boolean dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(false);

        boolean sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(false);

        boolean sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(false);

        logger.info(" testEurekaClientPriorityFindSameZoneEurekaServer  test----- ");

        startupClient();

        int wait = NET_TIME + NET_TIME + 1;//一次心跳，一次注册，有可能心跳在注册之前

        logger.info(" testEurekaClientPriorityFindSameZoneEurekaServer  test wait server up for eureka server ----- " + wait);
        try {
            Thread.sleep(wait * 1000);
        } catch (Exception e) {

        }

        Map<String, Object> instance_dx_2 = getInstanceForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(instance_dx_2).isNotEmpty();

        Map<String, Object> instance_dx_1 = getInstanceForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(instance_dx_1).isNotEmpty();

        Map<String, Object> instance_sjs_1 = getInstanceForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(instance_sjs_1).isNotEmpty();

        Map<String, Object> instance_sjs_2 = getInstanceForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(instance_sjs_2).isNotEmpty();

        Map<String, Long> leaseInfo_dx_2 = (Map<String, Long>) instance_dx_2.get("leaseInfo");
        Map<String, Long> leaseInfo_dx_1 = (Map<String, Long>) instance_dx_1.get("leaseInfo");
        Map<String, Long> leaseInfo_sjs_1 = (Map<String, Long>) instance_sjs_1.get("leaseInfo");
        Map<String, Long> leaseInfo_sjs_2 = (Map<String, Long>) instance_sjs_2.get("leaseInfo");

        long regist_dx_2 = leaseInfo_dx_2.get("registrationTimestamp");
        logger.info(" testEurekaClientPriorityFindSameZoneEurekaServer  regist_dx_2----- " + regist_dx_2);
        long regist_dx_1 = leaseInfo_dx_1.get("registrationTimestamp");
        logger.info(" testEurekaClientPriorityFindSameZoneEurekaServer  regist_dx_1----- " + regist_dx_1);
        long regist_sjs_1 = leaseInfo_sjs_1.get("registrationTimestamp");
        logger.info(" testEurekaClientPriorityFindSameZoneEurekaServer  regist_sjs_1----- " + regist_sjs_1);
        long regist_sjs_2 = leaseInfo_sjs_2.get("registrationTimestamp");
        logger.info(" testEurekaClientPriorityFindSameZoneEurekaServer  regist_sjs_2----- " + regist_sjs_2);

        assertThat(regist_dx_1 < regist_dx_2).isEqualTo(true);
        assertThat(regist_dx_1 < regist_sjs_1).isEqualTo(true);
        assertThat(regist_dx_1 < regist_sjs_2).isEqualTo(true);
    }

    /**
     * 高可用.集群保证接入方注册安全、透明
     */
    @Test
    public void testFaultTolerantEurekaServerWhenClientServerStart() {

        logger.info(" testFaultTolerantEurekaServerWhenClientServerStart ----- ");

        boolean dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(false);

        boolean dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(false);

        boolean sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(false);

        boolean sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(false);

        logger.info(" testFaultTolerantEurekaServerWhenClientServerStart test----- ");

        fastShutDownEurekaServer(DX_1_ZONE_IP);

        startupClient();

        int wait = NET_TIME + NET_TIME;//一次心跳，一次注册，有可能心跳在注册之前
        logger.info(" testFaultTolerantEurekaServerWhenClientServerStart wait server up for eureka server ----- " + wait);
        try {
            Thread.sleep(wait * 1000);
        } catch (Exception e) {

        }

        dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(true);

        sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(true);

        sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(true);

        startEurekaServer(DX_1_ZONE_IP, DX_1_ZONE);

        dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(true);

    }

    /**
     * 高可用.集群保证接入方注册安全、透明
     */
    @Test
    public void testFaultTolerantEurekaServerWhenClientServerUsing() {

        logger.info(" testFaultTolerantEurekaServerWhenClientServerUsing ----- ");

        startupClient();

        int wait = NET_TIME + NET_TIME + 1;//一次心跳，一次注册，有可能心跳在注册之前
        logger.info(" testFaultTolerantEurekaServerWhenClientServerUsing wait server up for eureka server ----- " + wait);
        try {
            Thread.sleep(wait * 1000);
        } catch (Exception e) {

        }

        boolean dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(true);

        boolean dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(true);

        boolean sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(true);

        boolean sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(true);

        fastShutDownEurekaServer(DX_1_ZONE_IP);

        wait = LEASE_EXPIRATION_DURATION + EUREKA_SERVER_EVICTION_CACHE_TIME + 1;
        try {
            Thread.sleep(wait * 1000);
        } catch (Exception e) {

        }

        dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(true);

        sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(true);

        sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(true);

        shutDownClient();

        wait = NET_TIME + NET_TIME + 1;
        logger.info(" testFaultTolerantEurekaServerWhenClientServerUsing wait server down for eureka server ----- " + LEASE_RENEWAL_INTERVAL + NET_TIME + NET_TIME + NET_TIME + 1);
        try {
            Thread.sleep(wait * 1000);
        } catch (Exception e) {

        }

        dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(false);

        sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(false);

        sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(false);

        startEurekaServer(DX_1_ZONE_IP, DX_1_ZONE);
    }

    /**
     * 一致性.注册中心节点故障恢复可预期
     *
     * @throws Exception
     */
    @Test
    public void testFastRecoveryEurekaServer() {

        logger.info(" testFastRecoveryEurekaServer ----- ");

        boolean dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(false);

        boolean dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(false);

        boolean sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(false);

        boolean sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(false);

        fastShutDownEurekaServer(DX_1_ZONE_IP);

        startupClient();

        try {
            Thread.sleep((NET_TIME + NET_TIME) * 1000);//eviction-interval-timer-in-ms: 5 + 1
        } catch (Exception e) {

        }

        dx_2 = isSynchronizationForSingleEureka(DX_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_2).isEqualTo(true);

        sjs_1 = isSynchronizationForSingleEureka(SJS_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_1).isEqualTo(true);

        sjs_2 = isSynchronizationForSingleEureka(SJS_2_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(sjs_2).isEqualTo(true);

        startEurekaServer(DX_1_ZONE_IP, DX_1_ZONE);

        dx_1 = isSynchronizationForSingleEureka(DX_1_ZONE, APP_NAME, INSTANCE_ID);
        assertThat(dx_1).isEqualTo(true);

    }

    /**
     * 高可用.可通过配置中心支持单台更新，全部更新
     *
     * @throws Exception
     */
    @Test
    public void testUpdateConfigForEureka() {
        logger.info(" testUpdateConfigForEureka ----- ");
        boolean reset = resetConfig();
        assertThat(reset).isEqualTo(true);

        logger.info(" testUpdateConfigForEureka test single----- ");
        updateConfig();

        refreshEureka("letv-mas-manager:prod:10.129.29.90");

        boolean dx_2_result = checkIsUpdateForSingleEureka(SJS_2_ZONE, CONFIG_NEW_STR);
        assertThat(dx_2_result).isEqualTo(true);

        boolean dx_1_result = checkIsUpdateForSingleEureka(SJS_1_ZONE, CONFIG_NEW_STR);
        assertThat(dx_1_result).isEqualTo(false);

        boolean sjs_1_result = checkIsUpdateForSingleEureka(DX_1_ZONE, CONFIG_NEW_STR);
        assertThat(sjs_1_result).isEqualTo(false);

        boolean sjs_2_result = checkIsUpdateForSingleEureka(DX_2_ZONE, CONFIG_NEW_STR);
        assertThat(sjs_2_result).isEqualTo(false);

        logger.info(" testUpdateConfigForEureka test all----- ");

        refreshEureka("letv-mas-manager:**");

        dx_2_result = checkIsUpdateForSingleEureka(DX_2_ZONE, CONFIG_NEW_STR);
        assertThat(dx_2_result).isEqualTo(true);

        dx_1_result = checkIsUpdateForSingleEureka(DX_1_ZONE, CONFIG_NEW_STR);
        assertThat(dx_1_result).isEqualTo(true);

        sjs_1_result = checkIsUpdateForSingleEureka(SJS_1_ZONE, CONFIG_NEW_STR);
        assertThat(sjs_1_result).isEqualTo(true);

        sjs_2_result = checkIsUpdateForSingleEureka(SJS_2_ZONE, CONFIG_NEW_STR);
        assertThat(sjs_2_result).isEqualTo(true);

    }


    /***
     * 启动test eureka client
     */

    private void startupClient() {
        startUpServer(TEST_CLIENT_PATH, TEST_CLIENT_PROFILE, TEST_CLIENT_PORT, false);
    }

    private void shutDownClient() {
        shutDownServer(TEST_EUREKA_CLIENT);
    }

    /***
     * 刷新应用
     */
    private void refreshEureka(String destination) {
        restTemplate.postForObject(CONFIG_SERVER + "/bus/refresh?destination=" + destination, null, String.class);
        try {
            Thread.sleep(10 * 1000);
        } catch (Exception e) {

        }
    }

    private boolean checkIsUpdateForSingleEureka(String eurekaServer, String newStr) {
        Map<String, Object> result = restTemplate.getForObject(eurekaServer + "/eurekaclient", Map.class);
        Map<String, Object> temp = (Map<String, Object>) result.get("serviceUrl");
        String str = (String) temp.get("testZone");
        if (str != null && str.contains(newStr)) {
            return true;
        }
        return false;
    }

    private boolean isSynchronizationForSingleEureka(String eurekaServer, String appName, String instanceId) {
        Map<String, Object> instance = getInstanceForSingleEureka(eurekaServer, appName, instanceId);
        if (instance != null) {
            return true;
        }
        return false;
    }

    private Map getInstanceForSingleEureka(String eurekaServer, String appName, String instanceId) {
        String result = restTemplate.getForObject(eurekaServer + "/eureka/apps", String.class);
        ArrayList<Map<String, Object>> instances = JsonPath.read(result, "$.applications.application[*].instance[*]");
        for (int j = 0; j < instances.size(); j++) {
            Map<String, Object> instance = instances.get(j);
            String tempInstanceId = (String) instance.get("instanceId");
            String status = (String) instance.get("status");
            String app = (String) instance.get("app");
            if (app.equals(appName) && status.equals("UP") && tempInstanceId.contains(instanceId)) {
                return instance;
            }
        }
        return null;
    }

    private boolean isContainInstanceForEurekaClient(String client, String serverId, String instance) {
        ArrayList<Map<String, Object>> results = restTemplate.getForObject(client + "/eureka/app?serviceId=" + serverId, ArrayList.class);
        if (results != null) {
            for (Map<String, Object> result : results) {
                Map<String, Object> temp = (Map<String, Object>) result.get("instanceInfo");
                String instanceId = (String) temp.get("instanceId");
                String status = (String) temp.get("status");
                if (status.equals("UP") && instanceId.equals(instance)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void shutDownServer(String server) {
        logger.info(" shutDownServer ----- " + server);
        try {
            restTemplate.postForObject(server + "/shutdown", null, String.class);
            Thread.sleep(2 * 1000);
        } catch (Exception e) {

        }
        logger.info(" shutDownServer end----- " + server);
    }

    private void killClient() {
        killServer(TEST_CLIENT_PORT);
    }

    private void killServer(String port) {
        logger.info(" killServer ----- " + port);
        String cmd = BASE_DIR + "kill_server_handle.sh " + port;
        CommandHandler.exec(new String[]{"/bin/sh", "-c", cmd}, BASE_DIR);
        try {
            Thread.sleep(1 * 1000);
        } catch (Exception e) {

        }
        logger.info(" killServer end ----- " + port);
    }

    /***
     * 修改git上对应的config
     */
    private void updateConfig() {
        String cmd = BASE_DIR + "update_git_handle.sh " + CONFIG_OLD_STR + " " + CONFIG_NEW_STR;
        CommandHandler.exec(new String[]{"/bin/sh", "-c", cmd}, BASE_DIR);
    }

    private boolean resetConfig() {
        String cmd = BASE_DIR + "update_git_handle.sh " + CONFIG_NEW_STR + " " + CONFIG_OLD_STR;
        CommandHandler.exec(new String[]{"/bin/sh", "-c", cmd}, BASE_DIR);
        refreshEureka("letv-mas-manager:**");
        boolean dx_2_result = checkIsUpdateForSingleEureka(DX_2_ZONE, CONFIG_OLD_STR);

        boolean dx_1_result = checkIsUpdateForSingleEureka(DX_1_ZONE, CONFIG_OLD_STR);

        boolean sjs_1_result = checkIsUpdateForSingleEureka(SJS_1_ZONE, CONFIG_OLD_STR);

        boolean sjs_2_result = checkIsUpdateForSingleEureka(SJS_2_ZONE, CONFIG_OLD_STR);

        return dx_2_result && dx_1_result && sjs_1_result && sjs_2_result;
    }

    private void startUpServer(String jarPath, String proFile, String port, boolean isServer) {
        logger.info(" startUpServer ----- " + port);
        String main_class = "";
        String cmd = BASE_DIR + "startup_server_handle.sh " + jarPath + " " + proFile + " " + port + " " + main_class + " > " + BASE_DIR + "start-server-" + port + ".log";
        CommandHandler.exec(new String[]{"/bin/sh", "-c", cmd}, BASE_DIR);
        logger.info(" startUpServer end ----- " + port);
    }

    @After
    public void clear() {
        fastShutDownServer(DX_2_ZONE, APP_NAME, INSTANCE_ID, TEST_CLIENT_PORT);
        fastShutDownServer(DX_2_ZONE, APP_NAME, INSTANCE_ID, TEST_CLIENT_PORT);
    }

    private int getDownTimeFormEurekaServer(long begin, String server, String app, String instance, boolean expect) {
        boolean result = !expect;
        int time = 0;
        int size = 0;
        while (result == !expect && size < 100) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            result = isSynchronizationForSingleEureka(server, app, instance);
            size++;
            //logger.info(" ------------ size ----------- " + size);
        }
        if (result == expect) {
            time = (int) ((System.currentTimeMillis() - begin) / 1000);
            logger.info(" ------------ getDownTimeFormEurekaServer end time ----------- " + time);
        } else {
            logger.info(" ------------ getDownTimeFormEurekaServer over size ----------- " + size);
        }
        return time;
    }

    private void fastShutDownServer(String server, String app, String instance, String port) {
        String url = server + "/eureka/apps/" + app + "/" + instance;
        logger.info(" fastShutDownServer ----- " + url);
        killServer(port);
        try {
            restTemplate.delete(url);
            Thread.sleep(1 * 1000);
        } catch (Exception e) {

        }
        logger.info(" fastShutDownServer end----- ");
    }

    private void fastShutDownEurekaServer(String serverIp) {
        logger.info(" fastShutDownEurekaServer ----- " + serverIp);
        try {
            restTemplate.postForObject(serverIp + ":9003" + "/dsp", null, String.class);
            Thread.sleep(1 * 1000);
        } catch (Exception e) {

        }
        logger.info(" fastShutDownEurekaServer end----- " + serverIp);
    }

    private void startEurekaServer(String serverIp, String server) {
        logger.info(" startEurekaServer ----- " + server);
        try {
            restTemplate.getForObject(serverIp + ":9002" + "/dst", String.class);
            boolean isStartUp = false;
            int size = 0;
            while (!isStartUp && size < 100) {
                isStartUp = isStartUp(server);
                size++;
                Thread.sleep(1000);
            }
            logger.info(" startEurekaServer over ----- " + isStartUp + " " + size);
        } catch (Exception e) {

        }
        logger.info(" startEurekaServer end----- " + server);
    }

    private boolean isStartUp(String server) {
        String info = restTemplate.getForObject(server + "/info", String.class);
        String name = JsonPath.read(info, "$.app.name");
        return name != null && !name.equals("");
    }
}