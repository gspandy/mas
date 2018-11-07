package com.letv.mas.caller.iptv.tvproxy.video.controller;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoPlayDto;
import com.letv.mas.caller.iptv.tvproxy.video.service.DownloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/iptv/api/new/channel", description = "下载")
@Component("downloadController_v2")
@RestController
public class DownloadController {

    @Autowired
    DownloadService downloadService;

    /**
     * 获取下载地址
     * @param vrsVideoInfoId
     * @param stream
     * @param timestamp
     * @param sig
     * @param useAuth
     *            是否启用防盗链，只是开发人员测试用
     * @return
     */
    @ApiOperation(value = "获取下载地址", httpMethod = "GET")
    @RequestMapping("/v2/video/getDownloadInfo")
    public Response<VideoPlayDto> getVideoDownInfo(
            @ApiParam(value = "视频ID", required = true) @RequestParam(value = "vrsVideoInfoId") Long vrsVideoInfoId,
            @ApiParam(value = "客户端请求播放码流", required = false) @RequestParam(value = "stream") String stream,
            @ApiParam(value = "用户登录或启动TV版时拿到的服务器时间戳", required = false) @RequestParam(value = "timestamp") Long timestamp,
            @ApiParam(value = "签名", required = true) @RequestParam(value = "sig") String sig,
            @ApiParam(value = "是否启用防盗链,开发人员测试用", required = false, defaultValue = "true") @RequestParam(value = "useAuth", required = false, defaultValue = "true") Boolean useAuth,
            @ApiParam(value = "用户token", required = false, defaultValue = "") @RequestParam(value = "token", required = false, defaultValue = "") String token,
            @ApiParam(value = "用户ID", required = false, defaultValue = "") @RequestParam(value = "tokenUserId", required = false, defaultValue = "") String tokenUserId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return downloadService.getDownloadInfo(vrsVideoInfoId, stream, timestamp, sig, useAuth,
                token, tokenUserId, commonParam);
    }
}
