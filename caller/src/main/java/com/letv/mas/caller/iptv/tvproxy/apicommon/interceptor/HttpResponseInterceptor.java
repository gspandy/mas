package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.HsResponseWrapper;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 响应头拦截器
 */
public class HttpResponseInterceptor extends HandlerInterceptorAdapter {

    /*@Autowired(required = false)
    private SessionCache sessionCache;*/

    // private static final Logger log =
    // LoggerFactory.getLogger("requestTimeLog");
    private static final Logger slowLog = LoggerFactory.getLogger("slowLog");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("requestStartTime", System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        try {
            String requestPath = request.getPathInfo();
            if (requestPath != null && requestPath.contains("/errorHandler")) {// 表示发生异常
                response.setHeader("Cache-Control", "max-age=0");
            } else {
                // 被映射的方法
                HandlerMethod handlerMethod = (HandlerMethod) handler;

                // 从HandlerMethod的注解中解析出响应头
                HttpResponseInterceptorAnnotation httpResponseInterceptorAnnotation = handlerMethod
                        .getMethodAnnotation(HttpResponseInterceptorAnnotation.class);
                if (httpResponseInterceptorAnnotation != null) {
                    String[] headers = httpResponseInterceptorAnnotation.headers();
                    if (headers != null) {
                        for (String header : headers) {
                            String[] splitHeader = header.split(":");

                            // 注入响应中
                            response.setHeader(splitHeader[0], splitHeader[1]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
        Long requestStartTime = (Long) request.getAttribute("requestStartTime");
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuffer sb = new StringBuffer();
        if (parameterMap != null && parameterMap.size() > 0) {
            sb.append("?");
            for (String key : parameterMap.keySet()) {
                sb.append("&").append(key).append("=");
                String[] values = parameterMap.get(key);
                if (values != null && values.length > 0) {
                    for (int i = 0; i < values.length; i++) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        sb.append(values[i]);
                    }
                }
            }
        }

        if (null != SessionCache.getSession()) {
            StringBuilder logInfo = new StringBuilder();
            logInfo.append("|200");
            if (null != response && response instanceof HsResponseWrapper
                    && null != ((HsResponseWrapper) response).getContent()) {
                logInfo.append("|").append(((HsResponseWrapper) response).getContent().length());
            } else {
                logInfo.append("|0");
            }
            logInfo.append("|").append(System.currentTimeMillis() - requestStartTime);
            logInfo.append("|0");
            SessionCache.getSession().setResponse(request.getRequestURI() + sb.toString(), "", logInfo.toString());
        }

        // log.info(getIP(request) + "," + request.getRequestURI() +
        // sb.toString() + ",请求耗时:"
        // + (System.currentTimeMillis() - requestStartTime));
        if (System.currentTimeMillis() - requestStartTime > 200) {
            slowLog.info(this.getIP(request) + "," + request.getRequestURI() + sb.toString() + ",请求耗时:"
                    + (System.currentTimeMillis() - requestStartTime));
        }
        // CommonUtil.printLog(System.currentTimeMillis() - requestStartTime);
    }

    public String getIP(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
