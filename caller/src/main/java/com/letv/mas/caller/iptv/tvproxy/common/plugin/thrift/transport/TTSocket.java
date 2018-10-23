package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.transport;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class TTSocket extends TSocket {

    private String hostThrift;
    private int portThrift;
    private int timeoutThrift;

    public TTSocket(String host, int port, int timeout) throws TTransportException {
        super(host, port, timeout);
        this.hostThrift = host;
        this.portThrift = port;
        this.timeoutThrift = timeout;
    }

    public String getHostThrift() {
        return this.hostThrift;
    }

    public int getPortThrift() {
        return this.portThrift;
    }

    public int getTimeoutThrift() {
        return this.timeoutThrift;
    }

}
