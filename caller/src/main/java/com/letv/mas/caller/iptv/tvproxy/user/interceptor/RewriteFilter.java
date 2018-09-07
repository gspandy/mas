package com.letv.mas.caller.iptv.tvproxy.user.interceptor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.HsResponseWrapper;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.ResponseBean;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.user.util.HystrixUtil;
import com.letv.mas.caller.iptv.tvproxy.user.util.IpAddrUtil;
import com.letv.mas.caller.iptv.tvproxy.user.util.StringUtil;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/*@Order(9)
@WebFilter(filterName = "rewrite", urlPatterns = "/api/tv/*")
@Iptv*/
public class RewriteFilter implements Filter {
    private final static ArrayList<String> DEBUG_IP_WHITELIST = new ArrayList<String>(
            Arrays.asList("10.58.*.*", "10.124.66.210", "10.124.66.211"));

    private final static Logger LOGGER = LoggerFactory.getLogger(RewriteFilter.class);
    //@Autowired
    //private SessionCache sessionCache;
   
    private boolean enableLogfile = true;

    private String host = "127.0.0.1";

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        SessionCache.initSessionCacheForRequest();
        long stime = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getServletPath() + request.getPathInfo();
        String url = request.getRequestURL().toString();
        String param = request.getQueryString();
        if (path.indexOf("-") > -1
                && (path.indexOf("/album/overseas/play/") > -1 || path.indexOf("/album/free/play/") > -1 || path
                        .indexOf("/secret/album/play/") > -1)) {
            path = path.substring(0, path.indexOf("-"));
            if (request.getRequestURI().lastIndexOf(".") > -1) {
                path += request.getRequestURI().substring(request.getRequestURI().lastIndexOf("."));
                param = "&stream="
                        + request.getRequestURI().substring(request.getRequestURI().indexOf("-") + 1,
                                request.getRequestURI().lastIndexOf("."));
            } else {
                int endIndex = request.getRequestURI().length();
                param = "&stream="
                        + request.getRequestURI().substring(request.getRequestURI().indexOf("-") + 1, endIndex);
            }
            System.out.println(path + (param == null ? "" : "?" + param));

            request.getRequestDispatcher(path + (param == null ? "" : "?" + param)).forward(request, response);
            // String basePath =
            // request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+(param==null?"":"?"+param);
            // response.sendRedirect(basePath);
        } else {
            String debug = request.getParameter("debug");
            if (StringUtil.isNotBlank(debug) /*&& IpAddrUtil.matchIP(DEBUG_IP_WHITELIST, IpAddrUtil.getIPAddr())*/) {
                if (Integer.parseInt(debug) > 0) {
                    SessionCache.getSession().setCommObj("debug", request.getParameter("debug"));
                }
            }
            // 输出依赖3rd接口调试信息
            String respContent = this.getResponseContent(request, response, chain);
            if (null == respContent) {
                chain.doFilter(request, response);
            }else{
                SessionCache.getSession().setRespContent(respContent);
                this.attachTraceInfo4Response(request, response);
                this.log(request, response,respContent.length(),stime);
                HystrixUtil.logHystrix();
                if (HystrixRequestContext.isCurrentThreadInitialized()) {
                    HystrixRequestContext.getContextForCurrentThread().shutdown();
                }
            }
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
        try {
            HsResponseWrapper hsResponseWrapper = new HsResponseWrapper(response);
            chain.doFilter(request, hsResponseWrapper);
            ret = hsResponseWrapper.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private boolean attachTraceInfo4Response(HttpServletRequest request,
                                             HttpServletResponse response) {

        SessionCache sessionCache = SessionCache.getSession();
        if (null == sessionCache.getRespContent() || !(sessionCache.getRespContent() instanceof String)) {
           return false;
        }
        String debug = request.getParameter("debug");
        boolean ret = false;
        JSONObject outJson = JSONObject.parseObject((String) sessionCache.getRespContent());
        if (StringUtil.isNotBlank(debug) && Integer.parseInt(debug) > 0 && null != sessionCache.getResponseStack()) {
            try {
                JSONObject debugJson = this.buildDebugInfoJO(request, sessionCache.getResponseStack(), debug);
                if (null != debugJson) {
                    outJson.put("debug", debugJson);
                    ret = true;
                }
            } catch (Exception e) {
                // TODO
            }
            sessionCache.destroy();
        }
        try {
            response.setContentLength(-1);
            response.getWriter().write(outJson.toString());
        } catch (Exception e) {
            // TODO
        }

        return ret;
    }

    private boolean attachTraceInfo4ResponseV2(HttpServletRequest request,
                                             HttpServletResponse response) {

        SessionCache sessionCache = SessionCache.getSession();
        if (null == sessionCache.getRespContent() || !(sessionCache.getRespContent() instanceof String)) {
            return false;
        }
        String debug = request.getParameter("debug");
        boolean ret = false;
        if (StringUtil.isNotBlank(debug) && Integer.parseInt(debug) > 0 && null != sessionCache.getResponseStack()) {
            try {
                JSONObject outJson = JSONObject.parseObject((String) sessionCache.getRespContent());
                JSONObject debugJson = this.buildDebugInfoJO(request, sessionCache.getResponseStack(), debug);
                if (null != debugJson) {
                    outJson.put("debug", debugJson);
                    ret = true;
                }
                response.setContentLength(-1);
                response.getWriter().write(outJson.toString());
            } catch (Exception e) {
                // TODO
            }
            sessionCache.destroy();
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

    private void log(HttpServletRequest request,
                     HttpServletResponse response,long contentLength,long stime){
        // 默认不输出日志
        if (!enableLogfile) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String splitSymbol = "|";
        int upstream_response_time = 0;
        long costTime = System.currentTimeMillis() - stime;
        String requestUrl = request.getRequestURI();
        String queryStr=request.getQueryString();
        if(StringUtil.isNotBlank(queryStr)){
            requestUrl += "?"+queryStr;
        }
        sb.append(this.getISO8601Timestamp(new Date())).append(splitSymbol)
                .append(IpAddrUtil.getRequestIP(request)).append(splitSymbol)
                .append(this.handleEmpty(request.getHeader("x-forwarded-for"))).append(splitSymbol)
                .append(this.handleEmpty(request.getRemoteUser())).append(splitSymbol)
                .append(request.getMethod()).append(splitSymbol)
                .append(request.getScheme()).append(splitSymbol)
                .append(this.handleEmpty(request.getRemoteHost())).append(splitSymbol)
                .append(requestUrl).append(splitSymbol)
                .append(request.getProtocol()).append(splitSymbol)
                .append(response.getStatus()).append(splitSymbol)
                .append(contentLength).append(splitSymbol)
//                    .append(this.handleEmpty(routingMap.get("contentLength"))).append(splitSymbol) //TODO
//                    .append(this.handleEmpty(routingMap.get("contentLength"))).append(splitSymbol) //TODO
                .append(this.handleEmpty(request.getHeader("referer"))).append(splitSymbol)
                .append(this.handleEmpty(request.getHeader("user-agent"))).append(splitSymbol)
                .append(this.handleEmpty(host)).append(splitSymbol)
                .append(upstream_response_time).append(splitSymbol)
                .append(costTime)
        ;
        LOGGER.info(sb.toString());
    }

    /**
     * 传入Data类型日期，返回字符串类型时间（ISO8601标准时间）
     *
     * @param date
     * @return
     */
    private String getISO8601Timestamp(Date date) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }

    private String handleEmpty(String str) {
        if (StringUtils.isEmpty(str)) {
            return "-";
        } else {
            return str;
        }
    }
}
