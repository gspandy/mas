package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.proxy;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.AddressProvider;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.ThriftConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.pool.ConnectionPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.async.TAsyncClient;
import org.apache.thrift.async.TAsyncClientFactory;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ThriftServiceClientProxyFactory implements FactoryBean<Object>, InitializingBean {
    /**
     * 接口的完整路径
     */
    private String service;

    /**
     * 连接池
     */
    private ConnectionPool connectionPool;

    private Object proxyClient;

    private Class<Object> objectClass;

    /**
     * 传输协议
     * 1.TBinaryProtocol – 二进制格式.
     * 2.TCompactProtocol – 压缩格式
     */
    private Integer protocol;

    private AddressProvider addressProvider;

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (this.connectionPool.getSocketType().equals(ThriftConstants.TTSocket)) {
            // 加载第三方提供的接口和Client.Factory类
            this.objectClass = (Class<Object>) classLoader.loadClass(this.service + "$Iface");
            Class<TServiceClientFactory<TServiceClient>> tServiceClientFactoryClass = (Class<TServiceClientFactory<TServiceClient>>) classLoader
                    .loadClass(this.service + "$Client$Factory");
            // 设置创建handler
            InvocationHandler clientHandler = new TTSocketHandler(this.connectionPool,
                    tServiceClientFactoryClass.newInstance(), this.protocol, this.addressProvider);
            this.proxyClient = Proxy.newProxyInstance(classLoader, new Class[] { this.objectClass }, clientHandler);
        } else if (this.connectionPool.getSocketType().equals(ThriftConstants.TTNonblockingSocket)) {
            // 加载第三方提供的接口和Client.Factory类
            this.objectClass = (Class<Object>) classLoader.loadClass(this.service + "$AsyncIface");
            Class<TAsyncClientFactory<TAsyncClient>> tAsyncClientFactoryClass = (Class<TAsyncClientFactory<TAsyncClient>>) classLoader
                    .loadClass(this.service + "$AsyncClient$Factory");
            Constructor<TAsyncClientFactory<TAsyncClient>> tAsyncClientFactoryConstructor = tAsyncClientFactoryClass
                    .getConstructor(TAsyncClientManager.class, TProtocolFactory.class);
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TProtocolFactory protocolFactory = null;
            switch (this.protocol) {
            case 1:
                protocolFactory = new TBinaryProtocol.Factory();
                break;
            case 2:
                protocolFactory = new TCompactProtocol.Factory();
                break;
            default:
                protocolFactory = new TBinaryProtocol.Factory();
            }
            TAsyncClientFactory<TAsyncClient> tAsyncClientFactory = tAsyncClientFactoryConstructor.newInstance(
                    clientManager, protocolFactory);
            // 设置创建handler
            InvocationHandler clientHandler = new TTNonblockingSocketHandler(this.connectionPool, tAsyncClientFactory,
                    this.addressProvider);
            this.proxyClient = Proxy.newProxyInstance(classLoader, new Class[] { this.objectClass }, clientHandler);
        }

    }

    @Override
    public Object getObject() throws Exception {
        return this.proxyClient;
    }

    @Override
    public Class<?> getObjectType() {
        return this.objectClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setAddressProvider(AddressProvider addressProvider) {
        this.addressProvider = addressProvider;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

}
