package com.letv.mas.caller.iptv.tvproxy.apicommon.util;

import com.letv.mas.caller.iptv.tvproxy.common.util.HttpCommonUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

    /**
     * 获取请求的IP地址
     * @return
     */
    public static String getIP() {
        return getIP(HttpCommonUtil.getRequest());
    }

    /**
     * 获取请求的IP地址
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("cdn-src-ip");

        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if (ip != null) {

            String[] ips = ip.split(",");

            for (String i : ips) {
                if (!i.startsWith("10.") && !i.startsWith("172.16.") && !i.startsWith("127.")
                        && !i.startsWith("192.168.")) {
                    ip = i.trim();

                    break;
                }
            }
        }

        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 校验是否合法IP
     * @param addr
     * @return
     */
    public static boolean isIP(String addr) {

        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr) || "127.0.0.1".equalsIgnoreCase(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        return addr.matches(rexp);
    }

    /**
     * 从url中获取指定参数的值
     * @param url
     * @param paramName
     * @return
     */
    public static String getUrlParamValue(String url, String paramName) {
        String value = null;
        url = StringUtils.trimToNull(url);
        if (url != null && paramName != null) {
            String paramStr = StringUtils.trimToNull(StringUtils.substringAfter(url, "?"));
            if (paramStr != null) {
                String[] paramArray = paramStr.split("&");
                if (paramArray != null && paramArray.length > 0) {
                    for (String paramEntryStr : paramArray) {
                        paramEntryStr = StringUtils.stripToNull(paramEntryStr);
                        if (paramEntryStr != null) {
                            String[] paramEntry = paramEntryStr.split("=");
                            if (paramEntry != null && paramEntry.length == 2
                                    && paramName.equalsIgnoreCase(paramEntry[0])) {
                                value = paramEntry[1];
                                break;
                            }
                        }
                    }
                }
            }
        }
        return value;
    }
}
