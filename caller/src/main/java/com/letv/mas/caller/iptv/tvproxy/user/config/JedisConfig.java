package com.letv.mas.caller.iptv.tvproxy.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

//@Component
@ConfigurationProperties(prefix = "jedis.redis")
public class JedisConfig implements Serializable{

    private static final long serialVersionUID = 1L;

    private Config master;

    private Config slave;

    private Pool pool;

    public Config getMaster() {
        return master;
    }

    public void setMaster(Config master) {
        this.master = master;
    }

    public Config getSlave() {
        return slave;
    }

    public void setSlave(Config slave) {
        this.slave = slave;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public static class Config {
        private String ip;
        private int port;
        private String pass;
        private int socketTimeout;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public int getSocketTimeout() {
            return socketTimeout;
        }

        public void setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
        }
    }

    public static class Pool {
        private Integer maxActive;
        private Integer maxIdle;
        private Integer maxWait;
        private boolean testOnBorrow;
        private boolean testOnReturn;

        public Integer getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(Integer maxActive) {
            this.maxActive = maxActive;
        }

        public Integer getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(Integer maxIdle) {
            this.maxIdle = maxIdle;
        }

        public Integer getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(Integer maxWait) {
            this.maxWait = maxWait;
        }

        public boolean isTestOnBorrow() {
            return testOnBorrow;
        }

        public void setTestOnBorrow(boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        public boolean isTestOnReturn() {
            return testOnReturn;
        }

        public void setTestOnReturn(boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }
    }
}
