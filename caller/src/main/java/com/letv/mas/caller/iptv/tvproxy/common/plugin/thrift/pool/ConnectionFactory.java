package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.pool;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.AddressProvider;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.ThriftConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.transport.TTNonblockingSocket;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.transport.TTSocket;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Iterator;

public class ConnectionFactory extends BasePoolableObjectFactory<Object> {
    private static Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);

    private final AddressProvider addressProvider;
    private int timeout = 2000;
    private String socketType;

    public ConnectionFactory(AddressProvider addressProvider, String socketType) throws Exception {
        this.addressProvider = addressProvider;
        this.socketType = socketType;
    }

    @Override
    public Object makeObject() throws Exception {
        String logPrefix = "makeTSocket_";
        Object connection = null;
        InetSocketAddress address = null;
        Exception exception = null;
        try {
            address = this.addressProvider.selectOne();
            connection = this.makeConnection(address.getHostName(), address.getPort(), timeout);
            LOGGER.info(logPrefix + "connect server:[" + address.getHostName() + ":" + address.getPort() + "] success");
        } catch (Exception e) {
            LOGGER.warn(logPrefix + "connect server[" + address.getHostName() + ":" + address.getPort() + "] error: ",
                    e);
            exception = e;
            connection = null;// 这里是为了下面连接其他服务器
        }
        // 轮循所有ip
        if (connection == null) {
            String hostName = address.getHostName();
            int port = address.getPort();
            Iterator<InetSocketAddress> addressIterator = this.addressProvider.addressIterator();
            while (addressIterator.hasNext()) {
                try {
                    address = addressIterator.next();
                    // 不再尝试连接之前已经连接失败的主机
                    if (address.getHostName().equals(hostName) && address.getPort() == port) {
                        continue;
                    }
                    connection = this.makeConnection(address.getHostName(), address.getPort(), timeout);
                    LOGGER.info(logPrefix + "connect server:[" + address.getHostName() + ":" + address.getPort()
                            + "] success");
                    break;
                } catch (Exception e) {
                    LOGGER.warn(logPrefix + "connect server[" + address.getHostName() + ":" + address.getPort()
                            + "] error: ", e);
                    exception = e;
                    connection = null;
                }
            }
        }
        // 所有服务均无法建立连接时抛出异常
        if (connection == null) {
            throw exception;
        }
        return connection;
    }

    private Object makeConnection(String ip, int port, int timeout) throws Exception {
        if (this.socketType.equals(ThriftConstants.TTSocket)) {
            TTSocket tTSocket = new TTSocket(ip, port, timeout);
            tTSocket.open();
            return tTSocket;
        } else if (this.socketType.equals(ThriftConstants.TTNonblockingSocket)) {
            TTNonblockingSocket tTNonblockingSocket = new TTNonblockingSocket(ip, port, timeout);
            return tTNonblockingSocket;
        }
        return null;
    }

    @Override
    public void destroyObject(Object connection) throws Exception {
        if (connection != null) {
            try {
                if (this.socketType.equals(ThriftConstants.TTSocket)) {
                    TTSocket tTSocket = (TTSocket) connection;
                    tTSocket.close();
                } else if (this.socketType.equals(ThriftConstants.TTNonblockingSocket)) {
                    TTNonblockingSocket tTNonblockingSocket = (TTNonblockingSocket) connection;
                    tTNonblockingSocket.close();
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public boolean validateObject(Object connection) {
        if (connection == null) {
            return false;
        }
        // 在成功创建连接后，将网络断掉，这里调用还是true
        if (this.socketType.equals(ThriftConstants.TTSocket)) {
            TTSocket tTSocket = (TTSocket) connection;
            return tTSocket.isOpen();
        } else if (this.socketType.equals(ThriftConstants.TTNonblockingSocket)) {
            TTNonblockingSocket tTNonblockingSocket = (TTNonblockingSocket) connection;
            return tTNonblockingSocket.isOpen();
        }

        return false;
    }
}
