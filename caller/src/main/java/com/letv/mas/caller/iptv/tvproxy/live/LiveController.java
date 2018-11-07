package com.letv.mas.caller.iptv.tvproxy.live;

import com.letv.mas.caller.iptv.tvproxy.common.constant.LiveConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveTpConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto.LiveIndexPageDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.request.GetLiveListRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.live.model.dto.LiveProject;
import com.letv.mas.caller.iptv.tvproxy.live.model.dto.LiveProjectDto;
import com.letv.mas.caller.iptv.tvproxy.live.model.dto.LiveSDKDto;
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

@Api(value = "/iptv/api/new/live", description = "直播相关")
@Component(value = "v2.LiveController")
@RestController
public class LiveController {

    @Autowired
    LiveService liveService;

    /**
     * LIVE下导航tab页频道 —— 直播节目单列表，获取直播大厅数据列表，或获取某一（某几）个直播信息 TODO
     * @param size
     * @param broadcastId
     * @param refresh
     *            0--取内存或缓存数据；1--强制刷新，走内存或缓存数据
     * @param liveIds
     *            要单独查询的某一（某几）个直播id，多个直播id使用英文逗号拼接
     * @param queryType
     *            1--查询直播大厅数据，默认值；2--查询单场直播信息，此时暂忽略refresh值，而使用liveIds
     * @param categoryId
     *            直播分类id，参见LiveConstants.ChannelType.categoryId
     * @return
     */
    @ApiOperation(value = "LIVE下导航tab页频道 —— 直播节目单列表，获取直播大厅数据列表，或获取某一（某几）个直播信息", httpMethod = "GET")
    @RequestMapping("/live/list")
    public Response<LiveDto> getLiveList(@RequestParam(value = "channelid") Integer channelId,
                                         @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
                                         @RequestParam(value = "broadcastId", required = false, defaultValue = "0") Integer broadcastId,
                                         @RequestParam(value = "langcode", required = false, defaultValue = "zh_cn") String langCode,
                                         @RequestParam(value = "wcode", required = false, defaultValue = "cn") String wCode,
                                         @RequestParam(value = "refresh", required = false, defaultValue = "0") Integer refresh,
                                         @RequestParam(value = "queryType", required = false, defaultValue = "1") String queryType,
                                         @RequestParam(value = "liveIds", required = false) String liveIds,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "terminalSeries", required = false) String terminalSeries,
                                         @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        broadcastId = (broadcastId == null) ? 0 : broadcastId;
        queryType = (queryType == null) ? LiveConstants.LIVE_LIST_QUERY_TYPE_LIVE_ROOM : queryType;
        refresh = (refresh == null) ? 0 : refresh;

        GetLiveListRequest request = new GetLiveListRequest(channelId, size, broadcastId, langCode, wCode, refresh,
                queryType, liveIds, LiveConstants.SPLITID.get(LiveConstants.LIVE_ROOM_SPLITID_KEY));
        request.setTerminalSeries(terminalSeries);
        return new Response<LiveDto>(liveService.getLiveList(request, commonParam));
    }

    /**
     * 直播频道首页数据
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "直播频道首页数据", httpMethod = "GET")
    @RequestMapping("/live/index")
    public Response<LiveIndexPageDto> getLiveIndexPageDto(
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return liveService.getLiveIndexPageDto(commonParam);
    }

    @ApiOperation(value = "获取直播聊天室地址", httpMethod = "GET", hidden = true)
    @RequestMapping("/v2/live/chatroom/getLiveChatRoomAddr")
    public Response<String> getLiveChatRoomInfo(@RequestParam(value = "chatroomId") String chatroomId) {
        return liveService.getLiveChatRoomAddr(chatroomId);
    }

    /**
     * 根据直播id获取直播详情;
     * 直播、轮播、卫视投屏播放（注意此时需传过来手机的imei和deviceKey）
     * @param model
     *            播放模式，0--TV版请求，1--儿童播放，2--投屏播放；在直播需求中，1不会出现
     * @param imei
     *            非TV设备（一般为移动设备）传IMEI
     * @param remoteScreenType
     *            投屏来源，1--领先版，2--superLive, 3--其他
     * @param from
     *            来源［1-Web，2-iPhone，3-iPad，4-TV，5-PC桌面，6-Android
     *            Phone，7-LePhone， 8-M站］，默认值：1
     * @param sourceType
     *            投屏资源类型，1--点播，2--直播，3--轮播（signal=7），4--卫视台（signal=2），在投屏直播逻辑中，1
     *            不会出现
     * @param timestamp
     *            用户登录或启动TV版时拿到的服务器时间戳，直播投屏签名校验使用
     * @return
     */
    @ApiOperation(value = "根据直播id获取直播详情", httpMethod = "GET")
    @RequestMapping("/live/liveInfo")
    public Response<LiveProject> getLiveById(@RequestParam(value = "liveId") String liveId,
                                             @RequestParam(value = "model", required = false) Integer model,
                                             @RequestParam(value = "imei", required = false) String imei,
                                             @RequestParam(value = "remoteScreenType", required = false) Integer remoteScreenType,
                                             @RequestParam(value = "from", required = false) Integer from,
                                             @RequestParam(value = "sourceType", required = false) Integer sourceType,
                                             @RequestParam(value = "timestamp", required = false) Long timestamp,
                                             @RequestParam(value = "sig", required = false) String sig,
                                             @RequestParam(value = "streamCode", required = false) String streamCode,
                                             @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        return liveService.getLiveById(liveId, model, sourceType, timestamp, sig, imei,
                streamCode, commonParam);
    }

    /**
     * 获取直播专题列表
     * @param userId
     *            用户id
     * @param size
     * @param broadcastId
     * @param langCode
     * @param wcode
     * @return
     */
    @ApiOperation(value = "获取直播专题列表(VIP)", httpMethod = "GET")
    @RequestMapping("/live/liveProjects")
    public Response<LiveProjectDto> getliveProjects(@RequestParam(value = "userId", required = false) String userId,
                                                    @RequestParam(value = "subjectId", required = false, defaultValue = "1607") String subjectId,
                                                    @RequestParam(value = "splatId", required = false, defaultValue = "1007") String splatid,
                                                    @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
                                                    @RequestParam(value = "broadcastId", required = false, defaultValue = "0") Integer broadcastId,
                                                    @RequestParam(value = "mac", required = false) String mac,
                                                    @RequestParam(value = "deviceKey", required = false) String deviceKey,
                                                    @RequestParam(value = "langcode", required = false, defaultValue = "zh_cn") String langCode,
                                                    @RequestParam(value = "wcode", required = false, defaultValue = "cn") String wcode,
                                                    @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        subjectId = (subjectId == null || subjectId.length() == 0) ? "1607" : subjectId;
        broadcastId = broadcastId == null ? 0 : broadcastId;
        splatid = LiveConstants.getLiveSplatIdByTerminalApplication(commonParam.getTerminalApplication(),
                LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
        return new Response<LiveProjectDto>(liveService.getliveProjects(subjectId, userId,
                broadcastId, splatid, true, mac, deviceKey, commonParam));
    }

    /**
     * 获取直播专题列表(V2)
     * @param userId
     *            用户id
     * @param size
     * @param broadcastId
     * @param langCode
     * @param wcode
     * @return
     */
    @ApiOperation(value = "获取直播专题列表(V2)", httpMethod = "GET")
    @RequestMapping("/live/v2/liveProjects")
    public Response<LiveProjectDto> getliveProjectsV2(@RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "subjectId", required = false, defaultValue = "1607") String subjectId,
            @RequestParam(value = "splatId", required = false, defaultValue = "1007") String splatid,
            @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
            @RequestParam(value = "broadcastId", required = false, defaultValue = "2") Integer broadcastId,
            @RequestParam(value = "mac", required = false) String mac,
            @RequestParam(value = "deviceKey", required = false) String deviceKey,
            @RequestParam(value = "langcode", required = false, defaultValue = "zh_cn") String langCode,
            @RequestParam(value = "wcode", required = false, defaultValue = "cn") String wcode,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        subjectId = (subjectId == null || subjectId.length() == 0) ? "1607" : subjectId;
        broadcastId = broadcastId == null ? 0 : broadcastId;
        splatid = StringUtil.isNotBlank(splatid) ? splatid : LiveConstants.getLiveSplatIdByTerminalApplication(
                commonParam.getTerminalApplication(), LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
        return new Response<LiveProjectDto>(liveService.getliveProjectsV2(subjectId, userId,
                broadcastId, splatid, true, mac, deviceKey, commonParam));
    }

    @ApiOperation(value = "getCmsLiveList", httpMethod = "GET")
    @RequestMapping("/live/cms/list")
    public Response<LiveSDKDto> getCmsLiveList(@RequestParam(value = "userId", required = false) String userId,
                                               @RequestParam(value = "splatId", required = false, defaultValue = "1007") String splatid,
                                               @RequestParam(value = "mac", required = false) String mac,
                                               @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
                                               @RequestParam(value = "broadcastId", required = false, defaultValue = "0") Integer broadcastId,
                                               @RequestParam(value = "langcode", required = false, defaultValue = "zh_cn") String langCode,
                                               @RequestParam(value = "wcode", required = false, defaultValue = "cn") String wcode,
                                               @RequestParam(value = "bsChannel", required = false, defaultValue = "def") String bsChannel) {
        splatid = LiveConstants.SPLITID.get(LiveConstants.LIVE_SDK_SPLITID_KEY);
        broadcastId = broadcastId == null ? 0 : broadcastId;
        bsChannel = bsChannel == null ? "daf" : bsChannel;
        String subjectId = LiveConstants.LIVE_TP_CHANNEL_CODE_MAP.get(bsChannel) == null ? LiveTpConstants.LIVE_TP_CHANNEL_DEFULT_CODE
                : LiveConstants.LIVE_TP_CHANNEL_CODE_MAP.get(bsChannel);
        LiveSDKDto dto = liveService.getCmsLiveList(subjectId, mac, broadcastId, splatid, true);
        return new Response<LiveSDKDto>(dto);
    }
}
