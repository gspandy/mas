package com.letv.mas.caller.iptv.tvproxy.video.controller;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GoLiveNavigationDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.GoliveNavigationsDto;
import com.letv.mas.caller.iptv.tvproxy.video.service.GoLiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "/iptv/api/new/channel", description = "直播")
@Component(value = "v2.GoLiveController")
@RestController
public class GoLiveController {
    
    @Autowired
    GoLiveService goLiveService;

    @ApiOperation(value = "直播列表页", httpMethod = "GET")
    @RequestMapping("/golive/getMovieList")
    public PageResponse<GoLiveNavigationDto> getMovieList(
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return goLiveService.getMovieList(commonParam);
    }

    @ApiOperation(value = "直播列表页V2", httpMethod = "GET")
    @RequestMapping("/golive/getMovieList2")
    public PageResponse<List<GoliveNavigationsDto>> getMovieList2(
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return goLiveService.getMovieList2(commonParam);
    }

}
