package com.letv.mas.caller.iptv.tvproxy.terminal.controller;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.terminal.model.dto.BootStrapResultsDto;
import com.letv.mas.caller.iptv.tvproxy.terminal.service.TerminalService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("v2.bootStrapController")
public class BootStrapController {

    @Autowired
    TerminalService terminalService;

    /**
     * 第三方调用启动接口
     * @param appVersion
     *            安装版本
     */
    @ApiOperation(value = "第三方调用启动接口", httpMethod = "GET")
    @RequestMapping(value = "/start/bootStrap")
    public Response<BootStrapResultsDto> bootStrap(@RequestParam("pCode") String pCode,
                                                   @RequestParam("appVersion") String appVersion, @RequestParam("ip") String ip,
                                                   @ModelAttribute CommonParam commonParam) {
        Response<BootStrapResultsDto> result = terminalService.bootStrap(appVersion, ip,
                commonParam);
        return result;
    }

}
