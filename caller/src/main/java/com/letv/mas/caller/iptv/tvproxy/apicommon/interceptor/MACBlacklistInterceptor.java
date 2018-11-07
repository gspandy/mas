package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvCommonException;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.cache.CacheTemplate;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam.getIP;

public class MACBlacklistInterceptor extends HandlerInterceptorAdapter {

    private static final String MAC_BLACKLIST_CACHE_KEY = "mac_blacklist_key";
    private static final String MAC_BLACKLIST_BLOCK_REQUEST = "mac_blacklist_block_request";

    private final CacheTemplate cacheTemplate;
    private final UserService userService;

    private Log log = LogFactory.getLog(MACBlacklistInterceptor.class);

    @Autowired
    public MACBlacklistInterceptor(UserService userService, CacheTemplate cacheTemplate) {
        this.userService = userService;
        this.cacheTemplate = cacheTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        // 只有标注MACBlacklistInterceptorAnnotation，才会拦截
        MACBlacklistInterceptorAnnotation annotation = method
                .getMethodAnnotation(MACBlacklistInterceptorAnnotation.class);
        if (annotation != null) {
            String ip = getIP(request);
            String uid = request.getParameter("userId");
            String terminalBrand = request.getParameter("terminalBrand");
            String terminalSeries = request.getParameter("terminalSeries");
            String mac = request.getParameter("mac");
            String terminalApplication = request.getParameter("terminalApplication");
            String appVersion = request.getParameter("appVersion");
            String flush = request.getParameter("flush");
            // 如果请求带有flush=1，先执行刷新缓存
            if (StringUtil.isNotBlank(flush)) {
                switch (flush) {
                case "1":
                    // 清除黑名单
                    this.flushBlacklist();
                    break;
                case "2":
                    // 增量刷新黑名单
                    this.appendBlacklist();
                    break;
                }
            }

            if (this.inBlacklist(mac)) {
                String logStr = String.format("|%s|%s|%s|%s|%s|%s|%s", ip, uid, terminalBrand, terminalSeries, mac,
                        terminalApplication, appVersion);
                log.info(logStr);
                if (this.isBlockRequest()) {
                    throw new LetvCommonException(ErrorCodeConstant.USER_MAC_BLACKLIST,
                            MessageUtils.getMessage(ErrorCodeConstant.USER_MAC_BLACKLIST));
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    private void flushBlacklist() {
        if(userService == null){
            return;
        }
        Set<String> macBlacklist = userService.getMacBlacklist();
        this.cacheTemplate.delete(MAC_BLACKLIST_CACHE_KEY);
        this.cacheTemplate.sadd(MAC_BLACKLIST_CACHE_KEY, macBlacklist);
    }

    private void appendBlacklist() {
        if(userService == null){
            return;
        }
        Set<String> macBlacklist = userService.getMacBlacklist();
        this.cacheTemplate.sadd(MAC_BLACKLIST_CACHE_KEY, macBlacklist);
    }

    private boolean isBlockRequest() {
        String blockRequest = cacheTemplate.get(MAC_BLACKLIST_BLOCK_REQUEST, String.class);
        return StringUtil.isNotBlank(blockRequest) && blockRequest.equals("1");
    }

    private boolean inBlacklist(String mac) {
        boolean isExist = false;
        int result = cacheTemplate.sismember(MAC_BLACKLIST_CACHE_KEY, mac);
        switch (result) {
        case CacheConstants.SISMEMBER_MEMBER_EXIST:
            isExist = true;
            break;
        case CacheConstants.SISMEMBER_MEMBER_NOT_EXIST:
            isExist = false;
            break;
        case CacheConstants.SISMEMBER_KEY_NOT_EXIST:
            if(userService != null){
                Set<String> macBlacklist = userService.getMacBlacklist();
                isExist = macBlacklist.contains(mac);
                cacheTemplate.sadd(MAC_BLACKLIST_CACHE_KEY, macBlacklist);
            }
            break;
        }
        return isExist;
    }
}
