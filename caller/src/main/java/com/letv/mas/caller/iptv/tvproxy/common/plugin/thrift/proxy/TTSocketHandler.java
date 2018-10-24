package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.proxy;


import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.AddressProvider;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.ThriftConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.ThriftServiceStatus;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.transport.TTSocket;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TTSocketHandler implements InvocationHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(TTSocketHandler.class);
    private static Log httpClientUtilLogger = LogFactory.getLog(HttpClientUtil.class);

    private GenericObjectPool<Object> pool; // 连接池
    private TServiceClientFactory<TServiceClient> tServiceClientFactory;
    private Integer protocol;
    private ThriftServiceStatus thriftServiceStatus;// 服务状态
    private AddressProvider addressProvider;

    public TTSocketHandler(GenericObjectPool<Object> pool, TServiceClientFactory<TServiceClient> tServiceClientFactory,
            Integer protocol, AddressProvider addressProvider) {
        this.pool = pool;
        this.tServiceClientFactory = tServiceClientFactory;
        this.protocol = protocol;
        this.thriftServiceStatus = new ThriftServiceStatus();
        this.addressProvider = addressProvider;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        String logPrefix = "ThriftInvocationHandler_";
        TTSocket tTSocket = null;
        boolean ifBorrowException = true;
        String interfaceName = ThriftConstants.getInterfaceName(method);
        try {
            // 服务处于“切服”状态时 直接返回null
            if (!this.thriftServiceStatus.ifServiceUsable()) {
                return null;
            }

            // 当第三方服务不可用时，会阻塞在这里一定时间后抛出异常，并进行服务状态统计
            tTSocket = (TTSocket) this.pool.borrowObject();
            ifBorrowException = false;

            String interfaceWholeName = interfaceName + "&ip=" + tTSocket.getHostThrift() + ":"
                    + tTSocket.getPortThrift();
            LOGGER.info(logPrefix + interfaceWholeName + " borrowed:" + this.pool.getNumActive() + "  idle:"
                    + this.pool.getNumIdle() + " total :" + (this.pool.getNumActive() + this.pool.getNumIdle()));

            long startTime = System.currentTimeMillis();
            long costTime;
            String url = ThriftConstants.getUrl(interfaceWholeName, args);
            Object o = null;
            try {
                o = method.invoke(this.tServiceClientFactory.getClient(this.getTProtocol(tTSocket)), args);
                costTime = System.currentTimeMillis() - startTime;
                httpClientUtilLogger.info(url + "|200|0|" + costTime + "|0");
            } catch (Exception e) {
                costTime = System.currentTimeMillis() - startTime;
                httpClientUtilLogger.error(url + "|000|0|" + costTime + "|1");
                // 抛出异常的连接认为不可用，从池中remove掉
                this.pool.invalidateObject(tTSocket);
                this.thriftServiceStatus.checkThriftServiceStatus(interfaceName);// 进入切服检查
                tTSocket = null;
                o = null;
            }

            return o;
        } catch (Exception e) {
            LOGGER.error("thrift invoke error", e);
            if (ifBorrowException) {
                this.thriftServiceStatus.checkThriftServiceStatus(interfaceName);
            }
            return null;
        } finally {
            if (tTSocket != null) {
                this.pool.returnObject(tTSocket);
            }
        }
    }

    private TProtocol getTProtocol(TSocket tSocket) {
        // 服务端均为异步类型
        TTransport transport = new TFramedTransport(tSocket);
        TProtocol tProtocol = null;
        switch (this.protocol) {
        case 1:
            tProtocol = new TBinaryProtocol(transport);
            break;
        case 2:
            tProtocol = new TCompactProtocol(transport);
            break;
        default:
            tProtocol = new TBinaryProtocol(transport);
        }
        return tProtocol;
    }
}
