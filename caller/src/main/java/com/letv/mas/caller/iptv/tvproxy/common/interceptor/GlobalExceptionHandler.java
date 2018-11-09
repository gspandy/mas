package com.letv.mas.caller.iptv.tvproxy.common.interceptor;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author：apple on 17/8/16 13:20
 * eMail：dengliwei@le.com
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ExceptionDto.class)
    // 将返回值转成json格式
    @ResponseBody
    // 返回状态码
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handleExceptionDto(ExceptionDto e) {
        LOGGER.error("handleExceptionDto: " + e);
        BaseResponse baseResponseDto = new BaseResponse(){

        };
        baseResponseDto.setErrCode(e.getErrorCode());
        baseResponseDto.setStatus(BaseResponse.STATUS_ERROR);
        baseResponseDto.setErrMsg(e.getMessage());
        return baseResponseDto;
    }
}
