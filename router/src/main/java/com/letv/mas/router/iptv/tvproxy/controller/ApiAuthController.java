package com.letv.mas.router.iptv.tvproxy.controller;

import com.letv.mas.router.iptv.tvproxy.constant.ErrorConsts;
import com.letv.mas.router.iptv.tvproxy.interceptor.CheckLoginInterceptorAnnotation;
import com.letv.mas.router.iptv.tvproxy.model.dto.BaseResponseDto;
import com.letv.mas.router.iptv.tvproxy.service.AuthService;
import com.letv.mas.router.iptv.tvproxy.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by leeco on 18/10/16.
 */
@RestController
@RequestMapping(value = "/i/auth")
public class ApiAuthController {

    @Autowired
    AuthService authService;

    @ApiIgnore
    @ApiOperation(
            value = "调试接口",
            notes = "功能验证<br/>",
            httpMethod = "GET")
    @RequestMapping(value = {"/test/{model}"})
    public Object test(
            @PathVariable("model") String model,
            @ApiParam(value = "p", required = true, defaultValue = "") @RequestParam(value = "p", required = false) String param) {
        return authService.test(model, param);
    }

    @CheckLoginInterceptorAnnotation
    @ApiOperation(
            value = "获取token",
            notes = "请求需带消息体<br/>",
            httpMethod = "GET")
    @RequestMapping(value = {"/token/get"}, method = RequestMethod.GET)
    public BaseResponseDto<String> getToken(
            @ApiParam(value = "code", required = true, defaultValue = "") @RequestParam(value = "code", required = true) String code) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        String token = authService.getToken(code);

        if (StringUtils.isNotBlank(token)) {
            baseResponseDto.setData(token);
            baseResponseDto.setStatus(BaseResponseDto.STATUS_OK);
            baseResponseDto.setCode(ErrorConsts.COM_OK);
            baseResponseDto.setMsg(ErrorConsts.getMessage(ErrorConsts.COM_OK));
        } else {
            baseResponseDto.setStatus(BaseResponseDto.STATUS_ERROR);
            baseResponseDto.setCode(ErrorConsts.COM_FAIL);
            baseResponseDto.setMsg(ErrorConsts.getMessage(ErrorConsts.COM_FAIL));
        }

        return baseResponseDto;
    }

}
