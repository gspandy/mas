package com.letv.mas.caller.circuitbreaker.model;

public class ResourceInfo {
    
    public static final String DEFAULT_RESOURCE_ID = "default";

    private String resourceId;


    public ResourceInfo(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
