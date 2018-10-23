package com.letv.mas.caller.iptv.tvproxy.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

//@Component
@ConfigurationProperties(prefix = "rest.client.pc")
public class RestConfig implements Serializable{

    private int maxConnections = 500;
    private int maxTotalConnections = 500;
    private int connectionTimeout = 5000;
    private int timeout = 110000;

    private static final long serialVersionUID = 1L;

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
