package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.transport;

import org.apache.thrift.transport.TNonblockingSocket;

import java.io.IOException;

public class TTNonblockingSocket extends TNonblockingSocket {
    private String hostThrift;
    private int portThrift;
    private int timeoutThrift;

    public TTNonblockingSocket(String host, int port, int timeout) throws IOException {
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
