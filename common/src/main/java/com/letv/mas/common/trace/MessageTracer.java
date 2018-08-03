package com.letv.mas.common.trace;

import com.google.common.base.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dalvikchen
 * @date 18/8/1
 */
@Aspect
@Component
@ConditionalOnProperty(value = "spring.sleuth.enabled", havingValue = "true", matchIfMissing = false)
public class MessageTracer {

    private static final Logger logger = LoggerFactory.getLogger(MessageTracer.class);

    @Autowired
    Tracer tracer;

    @Around("@annotation(trace)")
    public Object trace(ProceedingJoinPoint joinPoint, MessageTrace trace) throws Throwable {
        TraceInfo traceInfo = buildTraceInfo(joinPoint, trace);

        if (tracer != null) {
            Span span = tracer.createSpan(traceInfo.getSpanName());
            try {
                span.logEvent(traceInfo.getEventName().concat(" Start"));
                span.tag("name", traceInfo.getTagName());
                span.tag("content", traceInfo.getMsg());
                span.tag("msgId", traceInfo.getMsgId());
                span.logEvent(traceInfo.getEventName().concat(" END"));
            } finally {
                if (span != null) {
                    tracer.close(span);
                }
            }
        } else {
            logger.warn("Tracer Instance Not Initialized!");
        }

        return joinPoint.proceed();
    }

    /**
     * 根据参数构造traceInfo
     *
     * @param joinPoint
     * @param trace
     * @return
     */
    public TraceInfo buildTraceInfo(ProceedingJoinPoint joinPoint,
                                    MessageTrace trace) {
        TraceInfo traceInfo = new TraceInfo();
        traceInfo.setSpanName(trace.spanName());
        traceInfo.setTagName(trace.tagName());
        traceInfo.setEventName(trace.eventName());
        StringBuilder sb = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        if (args != null && args.length > 0) {
            // 参数map
            Map<String, Object> paramMap = new HashMap<>();
            String[] parameterNames = signature.getParameterNames();
            for (int i = 0; i < args.length; i++) {
                paramMap.put(parameterNames[i], args[i]);
            }

            if (!Strings.isNullOrEmpty(trace.msgId())) {
                String msgId = retrieveValue(trace.msgId(), paramMap).toString();
                traceInfo.setMsgId(msgId);
            }
            if (!Strings.isNullOrEmpty(trace.msg())) {
                String msg = retrieveValue(trace.msg(), paramMap).toString();
                traceInfo.setMsg(msg);
            }
        }
        return traceInfo;
    }

    /**
     * 获取参数值
     *
     * @param key
     * @param paramMap
     * @return
     */
    private String retrieveValue(String key, Map<String, Object> paramMap) {
        String regex = "\\$\\{(.*?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(key);
        while (m.find()) {
            String p = m.group(1);
            String[] pArr = p.split("\\.");
            Object param = paramMap.get(pArr[0]);
            if (param == null) {
                return "";
            }
            if (pArr.length == 1) {
                return param.toString();
            } else {
                try {
                    Map<String, Object> propMap = MapUtils.convertBeanToMap(param);
                    Object propObj = propMap.get(pArr[1]);
                    if (propObj == null) {
                        return "";
                    }
                    return propObj.toString();
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return key;
    }

    class TraceInfo {
        private String spanName;
        private String tagName;
        private String msg;
        private String msgId;
        private String eventName;

        public String getSpanName() {
            return spanName;
        }

        public TraceInfo setSpanName(String spanName) {
            this.spanName = spanName;
            return this;
        }

        public String getTagName() {
            return tagName;
        }

        public TraceInfo setTagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

        public String getMsg() {
            return msg;
        }

        public TraceInfo setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public String getMsgId() {
            return msgId;
        }

        public TraceInfo setMsgId(String msgId) {
            this.msgId = msgId;
            return this;
        }

        public String getEventName() {
            return eventName;
        }

        public TraceInfo setEventName(String eventName) {
            this.eventName = eventName;
            return this;
        }
    }
}
