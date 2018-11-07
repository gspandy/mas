package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import com.google.common.util.concurrent.AtomicLongMap;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author guoyunfeng
 */
public class VisitCounterStatInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(VisitCounterStatInterceptor.class);

    /**
     * 记录接口访问
     */
    public static final AtomicLongMap<String> VISIT_COUNT_MAP = AtomicLongMap.create();

    /**
     * 记录接口慢查询
     */
    public static final AtomicLongMap<String> VISIT_SLOW_COST_MAP = AtomicLongMap.create();

    /**
     * 最大接受的耗时
     */
    public final static long MAX_ACCEPT_TIME = 100;

    /**
     * 耗时
     */
    private ThreadLocal<Long> costThreadLocal = new ThreadLocal<>();

    public VisitCounterStatInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (StringUtil.isNotBlank(uri)) {
            VISIT_COUNT_MAP.incrementAndGet(uri);
            costThreadLocal.set(System.currentTimeMillis());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        try {
            String uri = request.getRequestURI();
            if (StringUtil.isNotBlank(uri)) {
                long startTime = costThreadLocal.get();
                long costTime = System.currentTimeMillis() - startTime;
                if (costTime > MAX_ACCEPT_TIME) {
                    VISIT_SLOW_COST_MAP.incrementAndGet(uri);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            costThreadLocal.remove();
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
