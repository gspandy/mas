package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.proxy;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.AddressProvider;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.ListenableFutureAdapter;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.ThriftConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.ThriftServiceStatus;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.transport.TTNonblockingSocket;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.async.TAsyncClient;
import org.apache.thrift.async.TAsyncClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TTNonblockingSocketHandler implements InvocationHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(TTSocketHandler.class);

    private GenericObjectPool<Object> pool; // 连接池
    private TAsyncClientFactory<TAsyncClient> tAsyncClientFactory;// 异步客户端工厂
    private ThriftServiceStatus thriftServiceStatus;// 服务状态
    private AddressProvider addressProvider;

    public TTNonblockingSocketHandler(GenericObjectPool<Object> pool,
            TAsyncClientFactory<TAsyncClient> tAsyncClientFactory, AddressProvider addressProvider) {
        this.pool = pool;
        this.tAsyncClientFactory = tAsyncClientFactory;
        this.thriftServiceStatus = new ThriftServiceStatus();
        this.addressProvider = addressProvider;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String logPrefix = "ThriftInvocationHandler_";
        TTNonblockingSocket tTNonblockingSocket = null;
        boolean ifBorrowException = true;
        String interfaceName = ThriftConstants.getInterfaceName(method);
        try {
            // 服务处于“切服”状态时 直接返回null
            if (!this.thriftServiceStatus.ifServiceUsable()) {
                return null;
            }

            // 当第三方服务不可用时，会阻塞在这里一定时间后抛出异常，并进行服务状态统计
            tTNonblockingSocket = (TTNonblockingSocket) this.pool.borrowObject();
            ifBorrowException = false;

            String interfaceWholeName = interfaceName + "&ip=" + tTNonblockingSocket.getHostThrift() + ":"
                    + tTNonblockingSocket.getPortThrift();
            LOGGER.info(logPrefix + interfaceWholeName + " borrowed:" + this.pool.getNumActive() + "  idle:"
                    + this.pool.getNumIdle() + " total :" + (this.pool.getNumActive() + this.pool.getNumIdle()));

            String url = ThriftConstants.getUrl(interfaceWholeName, args);
            long startTime = System.currentTimeMillis();
            ((ListenableFutureAdapter) args[1]).setData(this.pool, tTNonblockingSocket, startTime, url);
            method.invoke(this.tAsyncClientFactory.getAsyncClient(tTNonblockingSocket), args);

            return null;
        } catch (Exception e) {
            LOGGER.error("thrift invoke error", e);
            if (ifBorrowException) {
                this.thriftServiceStatus.checkThriftServiceStatus(interfaceName);
            } else {
                if (tTNonblockingSocket != null) {
                    this.pool.returnObject(tTNonblockingSocket);
                }
            }
            return null;
        }
    }
}
