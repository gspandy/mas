package com.letv.mas.caller.iptv.tvproxy.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

//@ConfigurationProperties(prefix = "datasource.mysql")
public class DataSourceConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    private int maximumConnectionCount;
    private int minimumConnectionCount;
    private int initialPoolSize;
    private int maxIdleTime;
    private int acquireIncrement;
    private int maxStatements;
    private int idleConnectionTestPeriod;
    private int acquireRetryAttempts;
    private int acquireRetryDelay;
    private int checkoutTimeout;

    private boolean breakAfterAcquireFailure;
    private boolean testConnectionOnCheckout;
    private boolean testConnectionOnCheckin;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getMaximumConnectionCount() {
        return maximumConnectionCount;
    }

    public void setMaximumConnectionCount(int maximumConnectionCount) {
        this.maximumConnectionCount = maximumConnectionCount;
    }

    public int getMinimumConnectionCount() {
        return minimumConnectionCount;
    }

    public void setMinimumConnectionCount(int minimumConnectionCount) {
        this.minimumConnectionCount = minimumConnectionCount;
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getAcquireIncrement() {
        return acquireIncrement;
    }

    public void setAcquireIncrement(int acquireIncrement) {
        this.acquireIncrement = acquireIncrement;
    }

    public int getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(int maxStatements) {
        this.maxStatements = maxStatements;
    }

    public int getIdleConnectionTestPeriod() {
        return idleConnectionTestPeriod;
    }

    public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
        this.idleConnectionTestPeriod = idleConnectionTestPeriod;
    }

    public int getAcquireRetryAttempts() {
        return acquireRetryAttempts;
    }

    public void setAcquireRetryAttempts(int acquireRetryAttempts) {
        this.acquireRetryAttempts = acquireRetryAttempts;
    }

    public int getAcquireRetryDelay() {
        return acquireRetryDelay;
    }

    public void setAcquireRetryDelay(int acquireRetryDelay) {
        this.acquireRetryDelay = acquireRetryDelay;
    }

    public int getCheckoutTimeout() {
        return checkoutTimeout;
    }

    public void setCheckoutTimeout(int checkoutTimeout) {
        this.checkoutTimeout = checkoutTimeout;
    }

    public boolean isBreakAfterAcquireFailure() {
        return breakAfterAcquireFailure;
    }

    public void setBreakAfterAcquireFailure(boolean breakAfterAcquireFailure) {
        this.breakAfterAcquireFailure = breakAfterAcquireFailure;
    }

    public boolean isTestConnectionOnCheckout() {
        return testConnectionOnCheckout;
    }

    public void setTestConnectionOnCheckout(boolean testConnectionOnCheckout) {
        this.testConnectionOnCheckout = testConnectionOnCheckout;
    }

    public boolean isTestConnectionOnCheckin() {
        return testConnectionOnCheckin;
    }

    public void setTestConnectionOnCheckin(boolean testConnectionOnCheckin) {
        this.testConnectionOnCheckin = testConnectionOnCheckin;
    }
}
