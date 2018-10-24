package com.letv.mas.caller.iptv.tvproxy.daemon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq.vip.mq")
public class RabbitMqBeanConfig {

    private String vhost;
    private String host;
    private int port;
    private String username;
    private String password;

    private String fanout_exchange_name;
    private String queue_name;
    private String exchange_name;

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFanout_exchange_name() {
        return fanout_exchange_name;
    }

    public void setFanout_exchange_name(String fanout_exchange_name) {
        this.fanout_exchange_name = fanout_exchange_name;
    }

    public String getQueue_name() {
        return queue_name;
    }

    public void setQueue_name(String queue_name) {
        this.queue_name = queue_name;
    }

    public String getExchange_name() {
        return exchange_name;
    }

    public void setExchange_name(String exchange_name) {
        this.exchange_name = exchange_name;
    }
}
