package com.letv.mas.caller.iptv.tvproxy.user.util;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HystrixUtil {

    private static Log hystrixMonitor = LogFactory.getLog("hystrixMonitorLog");
    private static Log resourceMonitor = LogFactory.getLog("resourceMonitorLog");
    private static Log log = LogFactory.getLog(HystrixUtil.class);
    public static boolean enableHyLogfile = false;

    public static void logFallBack(String group, String name, String resource, Throwable e) {
        if (!enableHyLogfile) {
            return;
        }
        String logStr = group + "|" + name + "|" + resource;
        if (e != null) {
            if (StringUtil.isNotBlank(e.getMessage())) {
                logStr = logStr + "|" + e.getMessage();
            }
            log.error(logStr,e);
        }
        resourceMonitor.info(logStr);
    }

    public static void initContext() {
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
        }
        if (HystrixRequestContext.getContextForCurrentThread() == null) {
            HystrixRequestContext.initializeContext();
        }
    }

    public static void shutdown() {
        if (HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.getContextForCurrentThread().shutdown();
        }
    }

    public static void logHystrix() {
        if (!enableHyLogfile) {
            return;
        }
        HystrixRequestLog hystrixRequestLog = HystrixRequestLog.getCurrentRequest();
        if (hystrixRequestLog != null) {
            hystrixRequestLog.getAllExecutedCommands().forEach(hystrixInvokableInfo -> {
                HystrixCommandMetrics.HealthCounts counts = hystrixInvokableInfo.getMetrics().getHealthCounts();
                int time = hystrixInvokableInfo.getExecutionTimeInMilliseconds();
                long start = hystrixInvokableInfo.getCommandRunStartTimeInNanos();
                boolean isTimeOut = hystrixInvokableInfo.isResponseTimedOut();
                boolean isFallBack = hystrixInvokableInfo.isResponseFromFallback();
                HystrixCommandKey key = hystrixInvokableInfo.getCommandKey();
                boolean isOpen = hystrixInvokableInfo.isCircuitBreakerOpen();
                String name = key.name();
                String group = hystrixInvokableInfo.getCommandGroup().name();
                Throwable error = hystrixInvokableInfo.getFailedExecutionException();
                String errorStr = error == null ? "" : error.getMessage();
                if (error != null) {
                    log.error(error);
                }
                /*if (isOpen) {
                    String logStr = "[" + group + "." + name + "] circuitBreakerOpen:" + TimeUtil.timestamp2date(System.currentTimeMillis());
                    hystrixMonitor.info(logStr);
                } else {
                    String log = "[" + group + "." + name + "] executionTime:" + time + " startTime:" + TimeUtil.timestamp2date(start/1000000) + " isTimeOut:" + isTimeOut + " isFallBack:" + isFallBack;
                    hystrixMonitor.info(log);
                }*/
                if (isOpen || isFallBack || isTimeOut || StringUtil.isNotBlank(errorStr)) {
                    String logStr = group + "|" + name  + "|" + time + "|" + isOpen + "|" + isFallBack + "|" + isTimeOut + "|" + errorStr;
                    hystrixMonitor.info(logStr);
                }

            });
        }
    }
}
