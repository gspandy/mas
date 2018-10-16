package com.letv.mas.router.iptv.tvproxy.controller;

import com.letv.mas.router.iptv.tvproxy.model.dto.BaseResponseDto;
import com.letv.mas.router.iptv.tvproxy.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by leeco on 18/10/16.
 */
@RestController
@RequestMapping(value = "/i/auth")
public class ApiAuthController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation(
            value = "获取token",
            notes = "请求需带消息体<br/>",
            httpMethod = "GET")
    @RequestMapping(value = { "/token/get" }, method = RequestMethod.GET)
    public BaseResponseDto<String> getToken(
            @ApiParam(value = "code", required = true, defaultValue = "") @RequestParam(value = "code", required = true) String code) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        String token = jwtTokenUtil.genToken(code);

        if (StringUtils.isNotBlank(token)) {
            baseResponseDto.setData(token);
            baseResponseDto.setStatus(BaseResponseDto.STATUS_OK);
            baseResponseDto.setCode("200");
            baseResponseDto.setMsg("OK");
        } else {
            baseResponseDto.setStatus(BaseResponseDto.STATUS_ERROR);
            baseResponseDto.setCode("100");
            baseResponseDto.setMsg("NG");
        }

        return baseResponseDto;
    }
}
