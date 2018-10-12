package com.letv.mas.router.iptv.tvproxy.controller;

import com.letv.mas.router.iptv.tvproxy.model.dto.BaseResponseDto;
import com.letv.mas.router.iptv.tvproxy.model.dto.GrafanaNotifyDto;
import com.letv.mas.router.iptv.tvproxy.service.IAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Created by leeco on 18/10/8.
 */
@RestController
@RequestMapping(value = "/i/alert")
public class AlertMgrController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertMgrController.class);

    @Autowired
    @Qualifier("GrafanaAlertService")
    IAlertService alertService;

    @RequestMapping(value = { "/test" })
    public String test(@RequestBody String body) {
        LOGGER.info(body);
        return body;
    }

    @RequestMapping(value = { "/grafana" }, method = RequestMethod.POST)
    public BaseResponseDto<String> notifyFromGrafana(@RequestBody GrafanaNotifyDto grafanaNotifyDto) {
        BaseResponseDto<String> responseDto = new BaseResponseDto<String>();
        boolean result = alertService.handleMessage(grafanaNotifyDto);
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
