package com.letv.mas.caller.iptv.tvproxy.user.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.user.util.HystrixUtil;
import com.letv.mas.caller.iptv.tvproxy.user.util.StringUtil;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author：apple on 17/6/12 10:37
 * eMail：dengliwei@le.com
 */
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class SessionCache {
    private final static Logger log = LoggerFactory.getLogger(SessionCache.class);

    /**
     * 对象转换为JSON字符串工具类
     */
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Vector<ResponseBean> responseBeanStack = new Vector<ResponseBean>();
    private ConcurrentHashMap<String, Object> commObjPool = new ConcurrentHashMap<String, Object>(45);

    private Object respContent;

    public Object getRespContent() {
        return respContent;
    }

    public void setRespContent(Object repContent) {
        this.respContent = repContent;
    }

    public static final HystrixRequestVariableDefault<SessionCache> sessionCache = new HystrixRequestVariableDefault<>();
    //private static final ThreadLocal<SessionCache> sessionCache = new ThreadLocal<SessionCache>();

    public static void initSessionCacheForRequest() {
        SessionCache.sessionCache.set(new SessionCache());
    }

    public void setResponse(ResponseBean responseBean) {
        // avoid to store more caused OOM under the running in application mode!
        if (responseBeanStack.size() >= 50) {
            responseBeanStack.clear();
        }
        responseBeanStack.add(responseBean);
    }

    public static SessionCache getSession(){
        /*HystrixUtil.initContext();
        return SessionCache.sessionCache.get();*/
        return null;
    }

    public void setResponse(String url, Object data, String result) {
        Object debug = this.getCommObj("debug");

        if (debug != null && (debug instanceof String) && StringUtil.isNotBlank((String) debug)) {
            ResponseBean responseBean = new ResponseBean();
            responseBean.url = url;
            if (Integer.parseInt((String) debug) > 1) {
                String jsonValue = null;
                if (null != data) {
                    if (data instanceof String) {
                        jsonValue = (String) data;
                    } else {
                        if (Integer.parseInt((String) debug) > 2) {
                            try {
                                jsonValue = OBJECT_MAPPER.writeValueAsString(data);
                            } catch (Exception e) {
                                jsonValue = "";
                            }
                        } else {
                            return;
                        }
                    }
                }
                responseBean.data = (String) jsonValue;
            }
            responseBean.result = result;

            this.setResponse(responseBean);
            // log.info("SessionCache.setResponse: url=" + url);
        }
    }

    public Vector<ResponseBean> getResponseStack() {
        return this.responseBeanStack;
    }

    public Object getCommObj(String key) {
        return this.commObjPool.get(key);
    }

    public void setCommObj(String key, Object obj) {
        this.commObjPool.put(key, obj);
    }

    public void init() {
        if (responseBeanStack != null) {
            responseBeanStack.clear();
        }
        if (commObjPool != null) {
            commObjPool.clear();
        }
        respContent = null;
    }

    public void destroy() {
        if (responseBeanStack != null) {
            responseBeanStack.clear();
            responseBeanStack = null;
        }
        if (commObjPool != null) {
            commObjPool.clear();
            commObjPool = null;
        }
        if (null != respContent)
            respContent = null;

        //shutdownHystrixRequestContext();
    }
}