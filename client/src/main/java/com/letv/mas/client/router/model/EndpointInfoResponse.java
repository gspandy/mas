package com.letv.mas.client.router.model;

import java.io.Serializable;

public class EndpointInfoResponse implements Serializable {

    private String zone;
    private String instanceId;
    private String region;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
