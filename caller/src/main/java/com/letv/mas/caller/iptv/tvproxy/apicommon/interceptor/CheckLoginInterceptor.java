package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ResponseUtil;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.UserConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
    Log logger = LogFactory.getLog(CheckLoginInterceptor.class);

    /**
     * 验证登陆拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        CheckLoginInterceptorAnnotation annotation = method.getMethodAnnotation(CheckLoginInterceptorAnnotation.class);
        if (annotation != null) {
            if (!this.isLogin(request, false)) {
                this.logger.error("need login !username:" + request.getParameter("username"));
                ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.USER_NOT_LOGIN,
                        ResponseUtil.getLocale(request));
            }
        }
        return true;
    }

    private Boolean isLogin(HttpServletRequest request, boolean isTryCatch) {
        Boolean isLogin = false;
        String from = request.getParameter("from");
        if (UserConstants.USER_FROM_TYPE_GOLIVE.equals(from)) {// golive不做登录校验，但在业务里会做token校验
            return true;
        }
        String terminalApplication = request.getParameter("terminalApplication");
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_MOBILE.equals(terminalApplication)) {// child_mobile不做登录校验，但在业务里会做token校验
            return true;
        }
        String username = request.getParameter("username");
        Integer broadcastId = null;
        try {
            String bc = request.getParameter("broadcastId");
            if (bc != null) {
                broadcastId = Integer.parseInt(bc);
            }
        } catch (Exception e) {

        }

        String loginTime = request.getParameter("loginTime");
        String realMac = request.getParameter("mac");
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(loginTime) && StringUtils.isNotEmpty(realMac)) {
            // 2015-09-28 线上故障临时修改，凡传了username的认为都已经登录
            return true;
        }

        String isfromcntv = request.getParameter("isFromCntv");
        String mac = request.getParameter("cntvMac");

        /*
         * username = BroadcastConstant.transFromBroadcastUsername(username,
         * broadcastId);
         * if (StringUtils.isNotEmpty(username) &&
         * StringUtils.isNotEmpty(loginTime)) {
         * WebApplicationContext context =
         * WebApplicationContextUtils.getWebApplicationContext
         * (request.getSession()
         * .getServletContext());
         * UserService userService = context.getBean(UserServiceImpl.class);
         * if (isTryCatch) {
         * try {
         * isLogin = userService.checkLogin(username, loginTime, realMac,
         * MutilLanguageConstants.getLocale(request));
         * } catch (Exception e) {
         * this.logger.error(e.getMessage(), e);
         * }
         * } else {
         * isLogin = userService.checkLogin(username, loginTime, realMac,
         * MutilLanguageConstants.getLocale(request));
         * }
         * }
         */

        if ("true".equals(isfromcntv) && mac != null && mac.length() > 0 && StringUtils.isEmpty(username)) {
            return true;
        }
        return isLogin;
    }
}
