package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift;

import java.lang.reflect.Method;

public class ThriftConstants {

    public static final String TTSocket = "TTSocket";
    public static final String TTNonblockingSocket = "TTNonblockingSocket";

    /**
     * thrift 将 方法名称转为接口信息，方便日志中识别
     * @param method
     * @return
     */
    public static String getInterfaceName(Method method) {
        String interfaceName = method.getDeclaringClass().toString();
        interfaceName = interfaceName.substring(10, interfaceName.length());
        return interfaceName + "$" + method.getName();
    }

    /**
     * thrift ip和参数的拼接方法
     * @param service
     * @param args
     * @return
     */
    public static String getUrl(String service, Object[] args) {
        StringBuilder wholeUrl = new StringBuilder("thrift://");
        wholeUrl.append(service);
        if (args != null) {
            wholeUrl.append("&allParams=[ ");
            for (Object object : args) {
                wholeUrl.append(object);
            }
            wholeUrl.append(" ]");
        }
        return wholeUrl.toString();
    }
}
