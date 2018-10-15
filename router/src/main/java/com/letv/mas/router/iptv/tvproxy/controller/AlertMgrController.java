package com.letv.mas.router.iptv.tvproxy.controller;

import com.letv.mas.router.iptv.tvproxy.model.dto.BaseResponseDto;
import com.letv.mas.router.iptv.tvproxy.model.dto.GrafanaNotifyDto;
import com.letv.mas.router.iptv.tvproxy.service.IAlertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by leeco on 18/10/8.
 */
@Api(value = "/i/alert", description = "监控报警")
@RestController
@RequestMapping(value = "/i/alert")
public class AlertMgrController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertMgrController.class);

    @Autowired
    @Qualifier("GrafanaAlertService")
    IAlertService alertService;

    @ApiIgnore
    @ApiOperation(
            value = "调试接口",
            notes = "请求需带消息体<br/>",
            httpMethod = "POST")
    @RequestMapping(value = { "/test" })
    public String test(
            @ApiParam(value = "消息体", required = true, defaultValue = "") @RequestBody String body) {
        LOGGER.info(body);
        return body;
    }

    @ApiOperation(
            value = "[Grafana]钉钉报警",
            notes = "消息体参考：http://docs.grafana.org/alerting/notifications<br/>",
            httpMethod = "POST")
    @RequestMapping(value = { "/grafana/dingding" }, method = RequestMethod.POST)
    public BaseResponseDto<String> notifyFromGrafana(@ModelAttribute @RequestBody GrafanaNotifyDto grafanaNotifyDto) {
        BaseResponseDto<String> responseDto = new BaseResponseDto<String>();
        boolean result = alertService.handleMessage(grafanaNotifyDto, IAlertService.AlertType.DINGDING);
        if (result) {
            responseDto.setStatus(BaseResponseDto.STATUS_OK);
            responseDto.setCode("200");
            responseDto.setMsg("OK");
        } else {
            responseDto.setStatus(BaseResponseDto.STATUS_ERROR);
            responseDto.setCode("100");
            responseDto.setMsg("NG");
        }
        return responseDto;
    }
}
