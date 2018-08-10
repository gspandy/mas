package com.letv.mas.common.config.client;

import com.google.gson.Gson;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

//@Component
public class EurekaClientWatcher implements Closeable {

    private final Logger log = LoggerFactory.getLogger(EurekaClientWatcher.class);

    @Value("${eureka.instance.metadata-map.zone}")
    private String myZone;

    @Value("${eureka.instance.ip-address}")
    private String localIp;

    @Value("${server.port}")
    private String localPort;

    @Value("${eureka.client.service-name}")
    private String serverName;

    @Value("${eureka.client.service-user}")
    private String serverUser;

    @Value("${eureka.client.service-passwd}")
    private String serverPasswd;

    private Gson gson;

    private final AtomicBoolean running = new AtomicBoolean(false);

    public EurekaClientWatcher() {
        gson = new Gson();
    }

    @EventListener
    public void hearBeat(HeartbeatEvent event) {
        Object source = event.getSource();
        Object value = event.getValue();
        if (value instanceof Long) {
            long count = (long) value;
            log.info("refresh cache...." + count);
            if (this.running.get()) {
                updateEurekaServerUrls();
            }
        }
    }

    @EventListener
    public void register(InstanceRegisteredEvent event) {
        log.info("register....");
    }

    private void updateEurekaServerUrls() {
        if (serverName == null || serverName.equals("")) {
            return;
        }
        EurekaClientConfig config = DiscoveryManager.getInstance().getEurekaClientConfig();
        EurekaClient client = DiscoveryManager.getInstance().getEurekaClient();
        if (config == null || client == null) {
            return;
        }
        if (config instanceof EurekaClientConfigBean) {
            EurekaClientConfigBean eurekaClientConfigBean = (EurekaClientConfigBean) config;
            Map<String, String> beforeAvailabilityZones = eurekaClientConfigBean.getAvailabilityZones();
            Map<String, String> beforeServiceUrl = eurekaClientConfigBean.getServiceUrl();
            log.info("refresh cache before .... availabilityZones " + gson.toJson(beforeAvailabilityZones));
            log.info("refresh cache before .... serviceUrl " + gson.toJson(beforeServiceUrl));
            Application application = client.getApplication(serverName);
            String region = eurekaClientConfigBean.getRegion();
            String beforeZones = beforeAvailabilityZones.get(region);
            if (beforeZones == null) {
                beforeZones = "";
            }
            List<InstanceInfo> instanceInfos = application.getInstances();
            Map<String, String> serverUrlMap = new HashMap<>();
            if (myZone == null) {
                myZone = "";
            }
            //String zones = "";
            for (InstanceInfo info : instanceInfos) {
                if (info != null) {
                    String zone = info.getMetadata().get("zone");
                    String url = info.getIPAddr() + ":" + info.getPort();
                    if (zone == null || url == null) {
                        continue;
                    }
                    if (localIp != null && localPort != null) {
                        if (url.contains(localIp + ":" + localPort)) {
                            continue;
                        }
                    }
                    if (beforeZones.equals("")) {
                        beforeZones = zone;
                    } else {
                        if (!beforeZones.contains(zone)) {
                            if (zone.equals(myZone)) {
                                beforeZones = zone + "," + beforeZones;
                            } else {
                                beforeZones = beforeZones + "," + zone;
                            }
                        }
                    }
                    if (serverUser != null && serverPasswd != null) {
                        url = "http://" + serverUser + ":" + serverPasswd + "@" + url + "/eureka";
                    } else {
                        url = "http://" + url + "/eureka";
                    }
                    if (serverUrlMap.containsKey(zone)) {
                        String urls = serverUrlMap.get(zone);
                        urls = urls + "," + url;
                        serverUrlMap.put(zone, urls);
                    } else {
                        serverUrlMap.put(zone, url);
                    }
                }
            }
            if (serverUrlMap.isEmpty()) {
                return;
            }
            String[] beforeZoneArray = beforeZones.split(",");
            String zones = "";
            for (String zone : beforeZoneArray) {
                if (serverUrlMap.containsKey(zone)) {
                    if (zones.equals("")) {
                        zones = zone;
                    } else {
                        zones = zones + "," + zone;
                    }
                }
            }
            if (serverUrlMap.containsKey(myZone) && !zones.contains(myZone)) {
                zones = myZone + "," + zones;
            }
            Map<String, String> tempMap = eurekaClientConfigBean.getAvailabilityZones();
            if (tempMap == null) {
                tempMap = new HashMap<>();
            }
            tempMap.put(region, zones);
            eurekaClientConfigBean.setAvailabilityZones(tempMap);
            eurekaClientConfigBean.setServiceUrl(serverUrlMap);
            log.info("refresh cache.... availabilityZones " + gson.toJson(tempMap));
            log.info("refresh cache.... serviceUrl " + gson.toJson(serverUrlMap));
        }
    }

    @PostConstruct
    public void start() {
        this.running.compareAndSet(false, true);
    }

    @Override
    public void close() {
        this.running.compareAndSet(true, false);
    }
}
