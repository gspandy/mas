package com.letv.mas.caller.iptv.tvproxy.common.interceptor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.HsResponseWrapper;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.ResponseBean;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.HystrixUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.IpAddrUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Order(1)
@WebFilter(filterName = "rewrite", urlPatterns = "/api/*")
public class DRewriteFilter implements Filter {
    private final static ArrayList<String> DEBUG_IP_WHITELIST = new ArrayList<String>(
            Arrays.asList("10.58.*.*", "10.124.66.210", "10.124.66.211"));
    private static final Logger slowLog = LoggerFactory.getLogger("slowLog");
    private final static Logger LOGGER = LoggerFactory.getLogger("hostAccessLog");

    /*@Autowired(required = false)
    private SessionCache sessionCache;*/

    @Value("${spring.cloud.iptv.host-log.enabled}")
    private boolean enableLogfile = false;

    @Value("${spring.cloud.iptv.hystrix-log.enabled}")
    private boolean enableHyLogfile = false;

    private String host = "127.0.0.1";

    @Value("${spring.profiles.active}")
    private String profile = "";


    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HystrixUtil.initContext();
        HystrixUtil.enableHyLogfile = enableHyLogfile;
        long stime = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        // 输出依赖3rd接口调试信息
        String respContent = this.getResponseContent(request, response, chain);
        if (null == respContent) {
            chain.doFilter(request, response);
        } else {
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setRespContent(respContent);
                this.attachTraceInfo4Response(request, response, SessionCache.getSession());
            } else {
                chain.doFilter(request, response);
            }
        }
        this.logSlowLog(request, response, stime);
        this.loghostAccess(request, response, stime);
        HystrixUtil.logHystrix();
        HystrixUtil.shutdown();
    }

    private void logSlowLog(HttpServletRequest request,
                            HttpServletResponse response, long stime) {
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

        if (System.currentTimeMillis() - stime > 1) {
            slowLog.info(this.getIP(request) + "," + request.getRequestURI() + sb.toString() + ",请求耗时:"
                    + (System.currentTimeMillis() - stime));
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, arg0.getServletContext());


    }

    private String getResponseContent(HttpServletRequest request,
                                      HttpServletResponse response,
                                      FilterChain chain) {
        String ret = null;
        String debug = request.getParameter("debug");
        if (StringUtil.isNotBlank(debug) && isAllowIp() && Integer.parseInt(debug) > 0) {
            SessionCache.initSessionCacheForRequest();
            //SessionCache.getSession().changeWriteStatus(true);
            SessionCache.getSession().setCommObj("debug", request.getParameter("debug"));
        } else {
            //SessionCache.getSession().changeWriteStatus(false);
        }
        if (SessionCache.getSession() != null) {
            try {
                HsResponseWrapper hsResponseWrapper = new HsResponseWrapper(response);
                chain.doFilter(request, hsResponseWrapper);
                ret = hsResponseWrapper.getContent();
            } catch (Exception e) {
                // TODO
            }
        }
        return ret;
    }

    private boolean isAllowIp() {
        //暂时先放开，待业务稳定
        /*if (StringUtil.isNotBlank(profile) && profile.endsWith("prod")) {
            return IpAddrUtil.matchIP(DEBUG_IP_WHITELIST, IpAddrUtil.getIPAddr());
        }*/
        return true;
    }

    private boolean attachTraceInfo4Response(HttpServletRequest request,
                                             HttpServletResponse response,
                                             SessionCache sessionCache) {
        String debug = request.getParameter("debug");
        boolean ret = false;
        if (StringUtil.isNotBlank(debug) && null != sessionCache.getResponseStack()) {
            System.out.println(sessionCache.getRespContent());
            if (null != sessionCache.getRespContent() && sessionCache.getRespContent() instanceof String) {
                JSONObject outJson = null;
                if (Integer.parseInt(debug) > 0) {
                    try {
                        outJson = JSONObject.parseObject((String) sessionCache.getRespContent());
                        JSONObject debugJson = this.buildDebugInfoJO(request, sessionCache.getResponseStack(), debug);
                        if (null != debugJson) {
                            outJson.put("debug", debugJson);
                        }
                        response.setContentLength(-1);
                        response.getWriter().write(outJson.toString());
                        ret = true;
                    } catch (Exception e) {
                        // TODO
                    }
                    sessionCache.destroy();
                }
            }
        }
        return ret;
    }

    private JSONObject buildDebugInfoJO(HttpServletRequest request,
                                        Vector<ResponseBean> debugInfos,
                                        String debug) {
        JSONObject ret = null;
        if (null != debugInfos && !debugInfos.isEmpty()) {
            ret = new JSONObject();
            JSONArray tmpJA = new JSONArray();
            for (int i = 0, size = debugInfos.size(); i < size; i++) {
                if ("1".equals(debug)) {
                    debugInfos.get(i).data = null;
                }
                tmpJA.add(debugInfos.get(i));
            }
            ret.put("cliIP", IpAddrUtil.getRequestIP(request));
            ret.put("svrIP", IpAddrUtil.getIPAddr());
            ret.put("3rdAPIs", tmpJA);
        }
        return ret;
    }

    private void loghostAccess(HttpServletRequest request,
                               HttpServletResponse response, long stime) {
        // 默认不输出日志
        if (!enableLogfile) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String splitSymbol = "|";
        int upstream_response_time = 0;
        long costTime = System.currentTimeMillis() - stime;
        String requestUrl = request.getRequestURI();
        String queryStr = request.getQueryString();
        if (StringUtil.isNotBlank(queryStr)) {
            requestUrl += "?" + queryStr;
        }
        sb.append(TimeUtil.getISO8601Timestamp(new Date())).append(splitSymbol)
                .append(IpAddrUtil.getRequestIP(request)).append(splitSymbol)
                .append(this.handleEmpty(request.getHeader("x-forwarded-for"))).append(splitSymbol)
                .append(this.handleEmpty(request.getRemoteUser())).append(splitSymbol)
                .append(request.getMethod()).append(splitSymbol)
                .append(request.getScheme()).append(splitSymbol)
                .append(this.handleEmpty(request.getRemoteHost())).append(splitSymbol)
                .append(requestUrl).append(splitSymbol)
                .append(request.getProtocol()).append(splitSymbol)
                .append(response.getStatus()).append(splitSymbol)
                .append(0).append(splitSymbol)
                .append(this.handleEmpty(request.getHeader("referer"))).append(splitSymbol)
                .append(this.handleEmpty(request.getHeader("user-agent"))).append(splitSymbol)
                .append(this.handleEmpty(host)).append(splitSymbol)
                .append(upstream_response_time).append(splitSymbol)
                .append(costTime)
        ;
        LOGGER.info(sb.toString());
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

    private String handleEmpty(String str) {
        if (StringUtils.isEmpty(str)) {
            return "-";
        } else {
            return str;
        }
    }
}
