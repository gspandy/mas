/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.mas.caller.iptv.tvproxy.common.controller;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

//TODO 1.统一输出日志文件到error.log 2.增加异常堆栈信息
@Controller
@Component
public class ErrorResponseController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/v2/errorHandler", method = { RequestMethod.GET, RequestMethod.POST })
    public CommonError responseWithErrorV2(@RequestParam("path") String path,
                                           @RequestParam("resultStatus") String resultStatus, @RequestParam("httpStatusCode") int httpStatusCode,
                                           @RequestParam("message") String message, @RequestParam("exception") String exception,
                                           @RequestParam("errorCode") String errorCode, HttpServletRequest request) {
        CommonError commonError = new CommonError();
        commonError.setResultStatus(resultStatus);
        commonError.setMessage(message);
        commonError.setErrorCode(errorCode == null ? "" : errorCode);
        this.log.error("Rest response erro: {}" + commonError);
        return commonError;
    }
}
