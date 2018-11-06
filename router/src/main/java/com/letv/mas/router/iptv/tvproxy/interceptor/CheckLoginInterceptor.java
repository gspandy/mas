package com.letv.mas.router.iptv.tvproxy.interceptor;

import com.letv.mas.router.iptv.tvproxy.constant.ErrorConsts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by leeco on 18/11/6.
 */
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckLoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        CheckLoginInterceptorAnnotation annotation = method.getMethodAnnotation(CheckLoginInterceptorAnnotation.class);
        if (annotation != null) {
            if (!this.isLogin(request)) {
                ErrorConsts.throwException(ErrorConsts.COM_UNLOGIN);
            }
        }
        return true;
    }

    private boolean isLogin(HttpServletRequest request) {
        boolean ret = false;
        String uuid = request.getHeader("uuid");
        if (StringUtils.isNotBlank(uuid)) {
            ret = true;
            // TODO: 验证uuid
        }
        return ret;
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
