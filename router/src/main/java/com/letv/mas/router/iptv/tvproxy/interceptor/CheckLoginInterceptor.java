package com.letv.mas.router.iptv.tvproxy.interceptor;

import com.letv.mas.router.iptv.tvproxy.constant.ErrorConsts;
import com.letv.mas.router.iptv.tvproxy.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by leeco on 18/11/6.
 */
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckLoginInterceptor.class);

    @Autowired
    private AuthService authService;

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

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private boolean isLogin(HttpServletRequest request) {
        boolean ret = false;
        String uuid = request.getHeader("uuid");
        if (StringUtils.isNotBlank(uuid)) {
            ret = authService.checkLogin(uuid);
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
