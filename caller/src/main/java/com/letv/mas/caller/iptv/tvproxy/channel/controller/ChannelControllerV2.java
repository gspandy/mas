package com.letv.mas.caller.iptv.tvproxy.channel.controller;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.ChannelDto;
import com.letv.mas.caller.iptv.tvproxy.channel.service.ChannelV2Service;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.WFSubjectDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/iptv/api/new/channel", description = "频道相关V2")
@RestController(value = "v2.ChannelControllerV2")
public class ChannelControllerV2 {

    @Autowired
    ChannelV2Service channelV2Service;

    @ApiOperation(value = "获取推广位活动接口", httpMethod = "GET")
    @RequestMapping("/channel/v2/content")
    public PageResponse<ChannelDto> getChannel(
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelid", required = false) String channelId,
            @ApiParam(value = "会员有效期", required = false) @RequestParam(value = "validDate", required = false) String validDate,
            @ApiParam(value = "设备类型", required = false, defaultValue = "1") @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        return new PageResponse<ChannelDto>(channelV2Service.getMine(commonParam));
    }

    /**
     * 新瀑布流专题详情接口：获得专题数据，专题包数据
     * @param pageId
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "新瀑布流专题详情接口", notes = "获得专题数据", httpMethod = "GET")
    @RequestMapping("/channel/v2/data/WFSubject/content/get")
    public Response<WFSubjectDto> getSubjectPackagesById(
            @ApiParam(value = "瀑布流专题页面ID", required = false) @RequestParam(value = "pageId", required = false) String pageId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelV2Service.getSubjectPackagesById(pageId, commonParam, true);
    }

    @ApiOperation(value = "新瀑布流专题详情生成静态文件接口", notes = "获得专题数据", httpMethod = "GET")
    @RequestMapping("/channel/v2/data/WFSubject/content/createWF")
    public Response<WFSubjectDto> createWFSubjectPackagesById(
            @ApiParam(value = "专题ID", required = false) @RequestParam(value = "subjectId", required = false) String subjectId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelV2Service.getSubjectPackagesByIdCreateStatic(subjectId, commonParam);
    }
}
