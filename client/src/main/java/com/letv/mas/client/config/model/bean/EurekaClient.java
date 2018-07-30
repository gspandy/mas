package com.letv.mas.client.config.model.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by David.Liu on 2018/6/11.
 */
@Component("eurekaClient1")
@ConfigurationProperties(prefix = "eureka.client")
public class EurekaClient {

    private String region;

    private Map<String, String> availabilityZones;

    private Map<String, String> serviceUrl;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Map<String, String> getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(Map<String, String> serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public Map<String, String> getAvailabilityZones() {
        return availabilityZones;
    }

    public void setAvailabilityZones(Map<String, String> availabilityZones) {
        this.availabilityZones = availabilityZones;
    }
}
