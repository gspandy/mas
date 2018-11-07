package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Author：apple on 17/8/16 13:20
 * eMail：dengliwei@le.com
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(BeanCreationException.class)
    public void handleBeanCreationException(Exception e) {
        // TODO
        LOG.error("handleBeanCreationException: " + e);
    }
}
