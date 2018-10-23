package com.letv.mas.caller.iptv.tvproxy.common.util;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpCommonUtil {

    /**
     * 获取当前context的HttpServletRequest对象
     * @return
     */
    public static HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        return null;
    }

    public static <T> T getRequestScopedTarget(String name, Class<T> c) {
        if (StringUtil.isBlank(name)) {
            return null;
        }
        HttpServletRequest request = HttpCommonUtil.getRequest();
        T target = null;
        if (request != null) {
            Object sessionObj = request.getAttribute("scopedTarget." + name);
            if (sessionObj != null) {
                try {
                    target = (T) sessionObj;
                } catch (Exception e) {

                }
            }
        }
        return target;
    }

    /**
     * 从HttpServletRequest的header中获取header值
     * @param request
     * @param headerKey
     * @return
     */
    public static String getRequestHeaderValue(HttpServletRequest request, String headerKey) {
        if (request != null) {
            return request.getHeader(headerKey);
        }
        return null;
    }

    /**
     * help method create HTTP headers.
     * @return
     */
    public static HttpHeaders createHttpHeader(String acceptMediaType, String acceptCharset) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Accept", acceptMediaType);
        requestHeaders.set("Accept-Charset", acceptCharset);
        return requestHeaders;
    }

}
