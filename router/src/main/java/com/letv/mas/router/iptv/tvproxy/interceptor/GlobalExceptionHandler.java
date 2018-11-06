package com.letv.mas.router.iptv.tvproxy.interceptor;

import com.letv.mas.router.iptv.tvproxy.model.dto.BaseResponseDto;
import com.letv.mas.router.iptv.tvproxy.model.dto.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by leeco on 18/11/6.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ExceptionDto.class)
    // 将返回值转成json格式
    @ResponseBody
    // 返回状态码
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponseDto handleExceptionDto(ExceptionDto e) {
        LOGGER.error("handleExceptionDto: " + e);
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        baseResponseDto.setCode(e.getErrorCode());
        baseResponseDto.setStatus(BaseResponseDto.STATUS_ERROR);
        baseResponseDto.setMsg(e.getMessage());
        return baseResponseDto;
    }
}
