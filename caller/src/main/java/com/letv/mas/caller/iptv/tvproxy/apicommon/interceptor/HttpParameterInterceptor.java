package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.TerminalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * API层HTTP参数拦截器，用于修改或补充请求参数
 * @author KevinYi
 */
public class HttpParameterInterceptor extends HandlerInterceptorAdapter {

    /*@Autowired(required = false)
    private SessionCache sessionCache;*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取客户端ip，便于后面封装到CommonParam中
        // HandlerMethod method = (HandlerMethod) handler;
        // HttpParameterInterceptorAnnotation annotation = method
        // .getMethodAnnotation(HttpParameterInterceptorAnnotation.class);
        // if (annotation != null) {
        // request.setAttribute("clientIp", HttpUtil.getIP(request));
        // }

        if (null != SessionCache.getSession()) {
            // [分端付费]当前接口线程全局保存p_devType
            if (null != request.getParameter("p_devType")) {
                SessionCache.getSession().setCommObj("p_devType", request.getParameter("p_devType"));
                if ((!CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE && !request.getParameter("p_devType").equals("2"))
                        || !TerminalUtil.supportDistributedPaying(request)) { // 兼容老版本按自有tv类型
                    SessionCache.getSession().setCommObj("p_devType", "2");
                }
            } else { // 向下兼容，默认为TV版
                SessionCache.getSession().setCommObj("p_devType", "2");
            }

            if (null != request.getParameter("debug")) {
                SessionCache.getSession().setCommObj("debug", request.getParameter("debug"));
            }
        }

        return super.preHandle(request, response, handler);
    }
}
