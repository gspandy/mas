package com.letv.mas.caller.iptv.tvproxy.common.plugin.zookeeper;


import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Closeable;

/**
 * Curator客户端生产工厂类
 * @author chenjian
 */
public class CuratorClientFactory implements FactoryBean<CuratorFramework>, Closeable, InitializingBean {
    private static Logger LOGGER = LoggerFactory.getLogger(CuratorClientFactory.class);
    /**
     * 默认值
     */
    private static final int defaultSessionTimeout = 3000;
    private static final int defaultConnectionTimeout = 3000;

    /**
     * zookeeper集群地址
     */
    private String zookeeperHosts;

    /**
     * session、连接 超时
     */
    private Integer sessionTimeout;
    private Integer connectionTimeout;

    private CuratorFramework zkClient;

    public void setZookeeperHosts(String zookeeperHosts) {
        this.zookeeperHosts = zookeeperHosts;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public CuratorFramework getObject() throws Exception {
        return this.zkClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtil.isBlank(this.zookeeperHosts)) {
            return;
        }
        this.zkClient = create(zookeeperHosts, sessionTimeout, connectionTimeout);
        this.zkClient.start();
    }

    /**
     * 对外提供，也可本类工厂类使用
     * @param connectString
     * @param sessionTimeout
     * @param connectionTimeout
     * @return
     */
    public static CuratorFramework create(String connectString, Integer sessionTimeout, Integer connectionTimeout) {
        try {
            // 默认值
            if (sessionTimeout == null) {
                sessionTimeout = defaultSessionTimeout;
            }
            if (connectionTimeout == null) {
                connectionTimeout = defaultConnectionTimeout;
            }

            return CuratorFrameworkFactory.newClient(connectString, sessionTimeout, connectionTimeout,
                    new ExponentialBackoffRetry(1000, Integer.MAX_VALUE));
        } catch (Exception e) {
            LOGGER.error("ZookeeperFactory create error", e);
            return null;
        }
    }

    public void close() {
        if (zkClient != null) {
            zkClient.close();
        }
    }

    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
