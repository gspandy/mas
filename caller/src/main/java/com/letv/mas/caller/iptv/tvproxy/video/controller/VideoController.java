package com.letv.mas.caller.iptv.tvproxy.video.controller;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.BroadcastConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.AuthorizedInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.CheckLoginInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.HttpResponseInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.MACBlacklistInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.NewResponse;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.TrailerVideoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpCommonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.video.service.DownloadService;
import com.letv.mas.caller.iptv.tvproxy.video.service.LivePlayService;
import com.letv.mas.caller.iptv.tvproxy.video.service.PlayService;
import com.letv.mas.caller.iptv.tvproxy.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 点播
 * @author wanglonghu
 */
@Api(value = "/iptv/api/new/channel", description = "点播")
@Component(value = "v2.VideoController")
@RestController
public class VideoController {

    @Autowired
    VideoService videoService;
    
    @Autowired
    PlayService playService;

    @Autowired
    AlbumVideoAccess albumVideoAccess;
    
    @Autowired
    DownloadService downloadService;
    
    @Autowired
    LivePlayService livePlayService;
    
    /**
     * 专辑详情页
     * @param albumId
     *            专辑ID
     * @param globalId
     *            全局专辑ID
     * @param seriesPaged
     *            是否分页（1 表示需要分页；其它值表示不需要分页）
     * @param supportDV
     *            是否支持杜比视界
     * @param commonParam
     *            接口通用参数
     * @return Response
     */
    @ApiOperation(value = "专辑详情页(VIP)", httpMethod = "GET")
    @RequestMapping("/video/album/detailandseries/get")
    public Response<AlbumDto> getAlbumDetailAndSeries(
            @ApiParam(value = "专辑ID", required = false) @RequestParam(value = "albumid", required = false) String albumId,
            @ApiParam(value = "全局专辑ID", required = false) @RequestParam(value = "globalid", required = false) String globalId,
            @ApiParam(value = "是否分页（1 表示需要分页；其它值表示不需要分页）", required = false) @RequestParam(value = "seriesPaged", required = false) Integer seriesPaged,
            @ApiParam(value = "是否支持杜比视界（true 表示支持；false 表示不支持）", required = false, defaultValue = "false") @RequestParam(value = "supportDV", required = false, defaultValue = "false") Boolean supportDV,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        // 印度版 bug，印度传的 albumId 为 String 类型，导致接口 400
        Long realAlbumId = 0L;
        if (!StringUtil.isBlank(albumId) && albumId.matches("^[0-9]*$")) {
            realAlbumId = Long.valueOf(albumId);
        }
        return videoService.getAlbumDetail(realAlbumId, globalId, seriesPaged, commonParam,
                false, supportDV);
    }

    /**
     * 第三方专辑详情页
     * @param albumId
     *            专辑ID
     * @param globalId
     *            全局专辑ID
     * @param seriesPaged
     *            是否分页（1 表示需要分页；其它值表示不需要分页）
     * @param commonParam
     *            接口通用参数
     * @return
     */
    @ApiOperation(value = "第三方专辑详情页", httpMethod = "GET")
    @RequestMapping("/video/thirdparty/album/detailandseries/get")
    public Response<AlbumDto> thdGetAlbumDetailAndSeries(
            @ApiParam(value = "专辑ID", required = false) @RequestParam(value = "albumid", required = false) String albumId,
            @ApiParam(value = "全局专辑ID", required = false) @RequestParam(value = "globalid", required = false) String globalId,
            @ApiParam(value = "是否分页（1 表示需要分页；其它值表示不需要分页）", required = false) @RequestParam(value = "seriesPaged", required = false) Integer seriesPaged,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        // 印度版 bug，印度传的 albumId 为 String 类型，导致接口 400
        Long realAlbumId = 0L;
        if (!StringUtil.isBlank(albumId) && albumId.matches("^[0-9]*$")) {
            realAlbumId = Long.valueOf(albumId);
        }
        return videoService.getAlbumDetail(realAlbumId, globalId, seriesPaged, commonParam,
                true, false);
    }

    /**
     * 获得用户设置码流列表
     * @return
     */
    @ApiOperation(value = "获得用户设置码流列表", httpMethod = "GET")
    @RequestMapping("/video/user/streams/get")
    public Response<Stream> getStream(
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return new Response<Stream>(videoService.getStream(commonParam));
    }

    /**
     * @param videoId
     * @param startPos
     *            非第一次请求，当前数据不在该页中，所请求数据的排序，一般为请求页的开始位置
     * @param initPos
     *            第一次请求，一般为请求第一页数据，当前数据在该页中的位置
     * @param pageSize
     * @param history
     * @param model
     *            播放模式，0--TV版播放，1--儿童播放，2--投屏播放
     * @param pageNum
     *            投屏播放接口返回的pageNum
     * @param imei
     *            非TV设备（一般为移动设备）传IMEI
     * @param categoryId
     *            视频所属分类，投屏时需要，因为不保证当前数据在tv端有版权，所以直接由客户端传值
     * @return
     */
    @ApiOperation(value = "获取视频播放列表", notes = "获取视频播放列表；<br/>" + "版本更新：<br/>"
            + "- 2017.9月需求：增加角标，统一在chargeInfos中获取(免费则不返回chargeInfos)", httpMethod = "GET")
    @RequestMapping("/video/play/list")
    public PageResponse<BaseData> getPlayList(
            @ApiParam(value = "视频ID", required = true) @RequestParam(value = "videoid") Long videoId,
            @ApiParam(value = "请求页的开始位置", required = true) @RequestParam(value = "startpos") Integer startPos,
            @RequestParam(value = "initpos") Integer initPos,
            @ApiParam(value = "分页大小", required = false) @RequestParam(value = "pagesize") Integer pageSize,
            @ApiParam(value = "分页号码", required = false, defaultValue = "0") @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @ApiParam(value = "用户观看历史, 传递用户最近观看的10个以内视频，按历史时刻顺序，vid1是最新观看的视频，格式如下：\"vid1-vid2-vid3-vid4-vid5-vid6-vid7-vid8-vid9-vid10\"，此字段只在猜我喜欢有用", required = false) @RequestParam(value = "history", required = false) String history,
            @ApiParam(value = "播放模式，0--TV版播放，1--儿童播放，2--投屏播放", required = false, defaultValue = "0") @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
            @ApiParam(value = "专辑ID", required = false) @RequestParam(value = "albumid", required = false) Long albumId,
            @ApiParam(value = "视频所属分类，投屏时需要，因为不保证当前数据在tv端有版权，所以直接由客户端传值", required = false, defaultValue = "0") @RequestParam(value = "categoryId", required = false, defaultValue = "0") Integer categoryId,
            @ApiParam(value = "非TV设备（一般为移动设备）传IMEI", required = false) @RequestParam(value = "imei", required = false) String imei,
            @ApiParam(value = "排序规则", required = false, defaultValue = "false") @RequestParam(value = "positive", required = false, defaultValue = "false") Boolean positive,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        return new PageResponse<BaseData>(videoService.getPlayList(videoId, startPos, initPos,
                pageSize, history, model, albumId, pageNum, categoryId, imei, positive, commonParam));
    }

    /**
     * 播放剧集列表，分为专辑详情页剧集列表和播放下拉列表；支持按videoid获取，和按分页获取；
     * 新接口，目前仅le中使用，使用分页功能；
     * 当按videoid获取时，videoId必传；当按分页获取时，albumId、stype、page必传
     * @param actionType
     *            1--按videoid获取；2--按分页获取
     * @param videoId
     * @param albumId
     * @param stype
     * @param page
     * @param functionType
     *            功能类型，1--专辑详情页，2--播放下拉；
     * @param history
     *            用户最近播放记录，
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "播放剧集列表", notes = "分为专辑详情页剧集列表和播放下拉列表；支持按videoid获取，和按分页获取；新接口，目前仅le中使用，使用分页功能；当按videoid获取时，videoId必传；当按分页获取时，albumId、stype、page必传", httpMethod = "GET")
    @RequestMapping("/video/play/seriesPage")
    public Response<AlbumSeriesPlayListPageDto> getVideoPlaySeriesList(
            @ApiParam(value = "动作类型，1--按videoid获取；2--按分页获取", required = false) @RequestParam(value = "actionType") Integer actionType,
            @ApiParam(value = "视频ID", required = false) @RequestParam(value = "vid", required = false) Long videoId,
            @ApiParam(value = "专辑ID", required = false) @RequestParam(value = "aid", required = false) Long albumId,
            @ApiParam(value = "专辑内各种视频播放列表的类型定义，0--所有剧集，1--正片剧集（选集），2--预告，3--周边视频，4--推荐", required = false, allowableValues = "0,1,2,3,4") @RequestParam(value = "stype", required = false) Integer stype,
            @ApiParam(value = "分页页码", required = false, defaultValue = "1") @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "功能类型，1--专辑详情页，2--播放下拉；", required = true, allowableValues = "1,2") @RequestParam(value = "functionType") Integer functionType,
            @ApiParam(value = "用户观看历史, 传递用户最近观看的10个以内视频，按历史时刻顺序，vid1是最新观看的视频，格式如下：\"vid1-vid2-vid3-vid4-vid5-vid6-vid7-vid8-vid9-vid10\"，此字段只在猜我喜欢有用", required = false) @RequestParam(value = "history", required = false) String history,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return videoService.getVideoPlaySeriesList(actionType, videoId, albumId, stype, page,
                functionType, history, commonParam);
    }

    /**
     * 获取体育直播焦点图位置
     * @param type
     *            是否为体育直播大厅 0：是
     * @return
     */
    @ApiOperation(value = "获取体育直播焦点图位置", httpMethod = "GET")
    @RequestMapping("/video/live/sport/list")
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=60" })
    public Response<SportsLiveChannelDto> getSportLiveList(
            @ApiParam(value = "是否为体育直播大厅 0：是", required = true, allowableValues = "0,1") @RequestParam("type") Integer type,
            @ApiParam(value = "分页大小", required = false) @RequestParam(value = "size", required = false) Integer size,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return new Response<SportsLiveChannelDto>(new SportsLiveChannelDto(videoService
                .getSportLiveForIndex(size, commonParam), type));
    }

    /**
     * 获取视频下载地址 ,只验证。同播放逻辑，但是不插入播放记录
     * @param stream
     * @param loginTime
     * @param pricePackageType
     * @return
     */
    @ApiOperation(value = "获取视频下载地址 ,只验证。同播放逻辑，但是不插入播放记录", httpMethod = "GET")
    @RequestMapping("/video/download/check")
    @CheckLoginInterceptorAnnotation
    public Response<String> checkDownLoad(
            HttpServletRequest request,
            @ApiParam(value = "视频ID", required = true) @RequestParam(value = "videoid") Long videoId,
            @ApiParam(value = "码流类型", required = false) @RequestParam(value = "stream", required = false) String stream,
            @ApiParam(value = "登录时间", required = false) @RequestParam(value = "logintime", required = false) String loginTime,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelid", required = false) String channelId,
            @ApiParam(value = "TV端对应终端编号", required = false) @RequestParam(value = "pricepackagetype", required = false) Integer pricePackageType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        if ((commonParam.getBroadcastId() != null) && (CommonConstants.CIBN == commonParam.getBroadcastId())) {
            BroadcastConstant.transFromBroadcastUsername(commonParam);
        }
        return videoService.checkDownloadV2(videoId, stream, loginTime, channelId,
                pricePackageType, commonParam);
    }

    /**
     * 获取视频下载地址V2 ,只验证。同播放逻辑，但是不插入播放记录
     * @param stream
     * @param loginTime
     * @param pricePackageType
     * @return
     */
    @ApiOperation(value = "获取视频下载地址 ,只验证。同播放逻辑，但是不插入播放记录（V2）(VIP)", httpMethod = "GET")
    @RequestMapping("/video/v2/download/check")
    @CheckLoginInterceptorAnnotation
    public Response<String> checkDownLoadV2(
            HttpServletRequest request,
            @ApiParam(value = "视频ID", required = true) @RequestParam(value = "videoid") Long videoId,
            @ApiParam(value = "码流类型", required = false) @RequestParam(value = "stream", required = false) String stream,
            @ApiParam(value = "登录时间", required = false) @RequestParam(value = "logintime", required = false) String loginTime,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelid", required = false) String channelId,
            @ApiParam(value = "TV端对应终端编号", required = false) @RequestParam(value = "pricepackagetype", required = false) Integer pricePackageType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        if ((commonParam.getBroadcastId() != null) && (CommonConstants.CIBN == commonParam.getBroadcastId())) {
            BroadcastConstant.transFromBroadcastUsername(commonParam);
        }
        return videoService.checkDownloadV2(videoId, stream, loginTime, channelId,
                pricePackageType, commonParam);
    }

    /**
     * TV版点播播放接口，支持乐视儿童播放，投屏播放；
     * TV版点播播放走正常TV版版权和鉴权逻辑；
     * 乐视儿童播放传入专辑ID，默认播专辑下第一集正片，走正常TV版版权和鉴权逻辑；
     * 投屏播放传入视频id，不走版权和鉴权逻辑，放行播放；
     * 2016-03-07TV版码流修改，1000码率不转，降为800，重新罗列清晰度对应表，标清由1000修改为800；针对老版本，请求1000码率时
     * ，以800播放（如果支持），不提示降码流；返回当前视频码流列表时，800再显示为1000（如果之前支持）；
     * @param request
     * @param timestamp
     *            用户登录或启动TV版时拿到的服务器时间戳
     * @param sig
     *            签名
     * @param isFromCntv
     *            请求是否来自CNTV
     * @param isFromCibn
     *            请求是否来自CIBN
     * @param stream
     *            客户端请求播放码流
     * @param loginTime
     *            登录时间
     * @param pricePackageType
     *            终端用户付费类型（是否开通点播服务）
     * @param model
     *            播放模式，0--TV版播放，1--儿童播放，2--投屏播放
     * @param imei
     *            非TV设备（一般为移动设备）传IMEI
     * @param mid
     *            视频对应的媒资id
     * @param isPay
     *            是否付费
     * @param supportStream
     *            设备是否支持某些特殊码流，格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断
     * @param supportDV
     *            是否支持杜比视界
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "点播播放接口(VIP)", notes = "sig字段签名规则：md5(asort(k1=v1&k2=v2)SecrectKey)<br/>"
            + "原版本：md5(albumId=xxx&model=xx&timestamp=xxx&videoId=xxxSecrectKey)<br/>"
            + "支持防盗链版本：md5(albumId=xxx&appCode=xxx&devSign=xxxxxx&model=xx&timestamp=xxx&videoId=xxxSecrectKey)", httpMethod = "GET")
    @MACBlacklistInterceptorAnnotation
    @RequestMapping("/video/play/get")
    public Response<VideoDto> getPlayInfo(
            HttpServletRequest request,
            @ApiParam(value = "视频ID", required = false) @RequestParam(value = "videoid", required = false) String videoId,
            @ApiParam(value = "外部视频ID", required = false) @RequestParam(value = "externalId", required = false) String externalId,
            @ApiParam(value = "用户登录或启动TV版时拿到的服务器时间戳", required = false) @RequestParam(value = "timestamp", required = false) Long timestamp,
            @ApiParam(value = "签名, 规则见接口备注", required = true) @RequestParam(value = "sig") String sig,
            @ApiParam(value = "请求是否来自CNTV", required = false, defaultValue = "false") @RequestParam(value = "isFromCntv", required = false, defaultValue = "false") Boolean isFromCntv,
            @ApiParam(value = "请求是否来自CIBN", required = false, defaultValue = "false") @RequestParam(value = "isFromCibn", required = false, defaultValue = "false") Boolean isFromCibn,
            @ApiParam(value = "客户端请求播放码流", required = false) @RequestParam(value = "stream", required = false) String stream,
            @ApiParam(value = "用户名", required = false) @RequestParam(value = "username", required = false) String userName,
            @ApiParam(value = "用户ID", required = false) @RequestParam(value = "userid", required = false) Long userid,
            @ApiParam(value = "登录时间", required = false) @RequestParam(value = "loginTime", required = false) String loginTime,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelid", required = false) Integer channelId,
            @ApiParam(value = "终端用户付费类型", required = false) @RequestParam(value = "pricePackageType", required = false) Integer pricePackageType,
            @ApiParam(value = "应用版本号", required = false) @RequestParam(value = "appCode", required = false) String appCode,
            @ApiParam(value = "有效期", required = false) @RequestParam(value = "validDate", required = false) String validDate,
            @ApiParam(value = "专辑ID", required = false) @RequestParam(value = "albumid", required = false) String albumId,
            @ApiParam(value = "播放模式，0--TV版播放，1--儿童播放，2--投屏播放", required = false, allowableValues = "0,1,2", defaultValue = "0") @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
            @ApiParam(value = "音频ID", required = false) @RequestParam(value = "audioId", required = false) String audioId,
            @ApiParam(value = "音轨类别", required = false) @RequestParam(value = "langType", required = false) String langType,
            @ApiParam(value = "音质", required = false) @RequestParam(value = "kbpsType", required = false) String kbpsType,
            @ApiParam(value = "特殊操作类型", required = false) @RequestParam(value = "operType", required = false) Integer operType,
            @ApiParam(value = "非TV设备（一般为移动设备）传IMEI", required = false) @RequestParam(value = "imei", required = false) String imei,
            @ApiParam(value = "视频对应的媒资id", required = false) @RequestParam(value = "mid", required = false) Long mid,
            @ApiParam(value = "是否付费", required = false) @RequestParam(value = "isPay", required = false) Integer isPay,
            @ApiParam(value = "视频来源，eg：youtube", required = false) @RequestParam(value = "source", required = false) String source,
            @ApiParam(value = "设备是否支持某些特殊码流，格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断", required = false) @RequestParam(value = "supportStream", required = false) String supportStream,
            @ApiParam(value = "是否支持 3D（true 表示支持；false 表示不支持）", required = false, defaultValue = "false") @RequestParam(value = "support3d", required = false, defaultValue = "false") Boolean support3d,
            @ApiParam(value = "是否支持 4K（true 表示支持；false 表示不支持）", required = false, defaultValue = "false") @RequestParam(value = "support4k", required = false, defaultValue = "false") Boolean support4k,
            @ApiParam(value = "是否支持杜比视界（true 表示支持；false 表示不支持）", required = false, defaultValue = "false") @RequestParam(value = "supportDV", required = false, defaultValue = "false") Boolean supportDV,
            @ApiParam(value = "是否请求连播的预告片（true 是；false 否）", required = false, defaultValue = "false") @RequestParam(value = "trailer", required = false, defaultValue = "false") Boolean trailer,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (model == null) {
            model = VideoConstants.PLAY_MODEL_COMMON;
        }

        String routerId = HttpCommonUtil.getRequestHeaderValue(request,
                VideoTpConstant.LETV_LEADING_REQUEST_HEADER_REOUTER_ID_KEY);

        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) { // levidi
            return playService.getVideoPlayInfo(videoId, albumId, externalId, mid, source, isPay, stream, timestamp, sig,
                            userName, model, audioId, channelId, langType, kbpsType, commonParam);
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) { // 美国版lecom
            return playService.getVideoPlayInfo4LecomUS(StringUtil.toLong(videoId, null),
                    StringUtil.toLong(albumId), stream, timestamp, sig, userName, loginTime, pricePackageType,
                    isFromCntv, isFromCibn, support3d, model, appCode, validDate, audioId, channelId, langType,
                    kbpsType, imei, routerId, operType, supportStream, commonParam);
        } else {
            // 临时方案：播控全放开
            if ((commonParam.getBroadcastId() != null) && (CommonConstants.CNTV == commonParam.getBroadcastId())) {
                commonParam.setBroadcastId(null);
            }

            if ((commonParam.getBroadcastId() != null) && (CommonConstants.CIBN == commonParam.getBroadcastId())) {
                userName = BroadcastConstant.transFromBroadcastUsername(userName, commonParam.getBroadcastId());
            }

            // 兼容逻辑，2.9.5之后可去掉
            if (StringUtil.toInteger(commonParam.getUserId(), 1) < 1) {
                commonParam.setUserId(String.valueOf(userid));
            }
            return playService.getVideoPlayInfo(StringUtil.toLong(videoId, null),
                    StringUtil.toLong(albumId), stream, timestamp, sig, userName, loginTime, pricePackageType,
                    isFromCntv, isFromCibn, support3d, model, appCode, validDate, audioId, channelId, langType,
                    kbpsType, imei, routerId, operType, supportStream, trailer, commonParam, support4k);
        }

    }

    /**
     * 联播播放接口
     * @param request
     * @param videoid
     *            视频id
     * @param channelId
     *            频道ID🆔
     * @param type
     *            视频类型
     * @param stream
     *            客户端请求播放码流
     * @param model
     *            播放模式，0--TV版播放，1--儿童播放，2--投屏播放
     * @param supportStream
     *            设备是否支持某些特殊码流，格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "联播播放接口(VIP)", httpMethod = "GET")
    @RequestMapping("/video/trailer/play/get")
    public Response<TrailerVideoDto> getTrailerPlayInfo(
            HttpServletRequest request,
            @ApiParam(value = "视频ID", required = false) @RequestParam(value = "videoid", required = false) String videoid,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelid", required = false) Integer channelId,
            @ApiParam(value = "视频类型", required = false) @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(value = "播放模式，0--TV版播放，1--儿童播放，2--投屏播放", required = false, allowableValues = "0,1,2", defaultValue = "0") @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
            @ApiParam(value = "客户端请求播放码流", required = false) @RequestParam(value = "stream", required = false) String stream,
            @ApiParam(value = "设备是否支持某些特殊码流，格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断", required = false) @RequestParam(value = "supportStream", required = false) String supportStream,
            @ApiParam(value = "是否支持 3D（true 表示支持；false 表示不支持）", required = false, defaultValue = "false") @RequestParam(value = "support3d", required = false, defaultValue = "false") Boolean support3d,
            @ApiParam(value = "是否支持 4K（true 表示支持；false 表示不支持）", required = false, defaultValue = "false") @RequestParam(value = "support4k", required = false, defaultValue = "false") Boolean support4k,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (model == null) {
            model = VideoConstants.PLAY_MODEL_COMMON;
        }
        return playService.setTrailerVideoDto(StringUtil.toLong(videoid, null), stream,
                supportStream, model, support3d, commonParam, support4k, channelId, type);
    }

    /**
     * 第三方TV版点播播放接口，支持乐视儿童播放，投屏播放；
     * 第三方TV版点播播放走正常TV版版权和鉴权逻辑；
     * 乐视儿童播放传入专辑ID，默认播专辑下第一集正片，走正常TV版版权和鉴权逻辑；
     * 投屏播放传入视频id，不走版权和鉴权逻辑，放行播放；
     * 2016-03-07TV版码流修改，1000码率不转，降为800，重新罗列清晰度对应表，标清由1000修改为800；针对老版本，请求1000码率时
     * ，以800播放（如果支持），不提示降码流；返回当前视频码流列表时，800再显示为1000（如果之前支持）；
     * @param request
     * @param timestamp
     *            用户登录或启动TV版时拿到的服务器时间戳
     * @param sig
     *            签名
     * @param isFromCntv
     *            请求是否来自CNTV
     * @param isFromCibn
     *            请求是否来自CIBN
     * @param stream
     *            客户端请求播放码流
     * @param mid
     *            视频对应的媒资id
     * @param isPay
     *            是否付费
     * @param supportStream
     *            设备是否支持某些特殊码流，格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "第三方点播播放接口(VIP)", notes = "参数定义同点播播放接口", httpMethod = "GET")
    @RequestMapping("/video/thirdparty/play/get")
    public Response<VideoDto> thirdPartyGetPlayInfo(HttpServletRequest request,
                                                    @RequestParam(value = "videoid", required = false) String videoId,
                                                    @RequestParam(value = "externalId", required = false) String externalId,
                                                    @RequestParam(value = "timestamp", required = false) Long timestamp,
                                                    @RequestParam(value = "sig") String sig,
                                                    @RequestParam(value = "isFromCntv", required = false, defaultValue = "false") Boolean isFromCntv,
                                                    @RequestParam(value = "isFromCibn", required = false, defaultValue = "false") Boolean isFromCibn,
                                                    @RequestParam(value = "support3d", required = false, defaultValue = "false") Boolean support3d,
                                                    @RequestParam(value = "support4k", required = false, defaultValue = "true") Boolean support4k,
                                                    @RequestParam(value = "stream", required = false) String stream,
                                                    @RequestParam(value = "username", required = false) String userName,
                                                    @RequestParam(value = "userid", required = false) Long userid,
                                                    @RequestParam(value = "loginTime", required = false) String loginTime,
                                                    @RequestParam(value = "channelid", required = false) Integer channelId,
                                                    @RequestParam(value = "pricePackageType", required = false) Integer pricePackageType,
                                                    @RequestParam(value = "appCode", required = false) String appCode,
                                                    @RequestParam(value = "validDate", required = false) String validDate,
                                                    @RequestParam(value = "albumid", required = false) String albumId,
                                                    @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
                                                    @RequestParam(value = "audioId", required = false) String audioId,
                                                    @RequestParam(value = "langType", required = false) String langType,
                                                    @RequestParam(value = "kbpsType", required = false) String kbpsType,
                                                    @RequestParam(value = "operType", required = false) Integer operType,
                                                    @RequestParam(value = "imei", required = false) String imei,
                                                    @RequestParam(value = "mid", required = false) Long mid,
                                                    @RequestParam(value = "isPay", required = false) Integer isPay,
                                                    @RequestParam(value = "supportStream", required = false) String supportStream,
                                                    @RequestParam(value = "source", required = false) String source,
                                                    @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (model == null) {
            model = VideoConstants.PLAY_MODEL_COMMON;
        }
        String routerId = HttpCommonUtil.getRequestHeaderValue(request,
                VideoTpConstant.LETV_LEADING_REQUEST_HEADER_REOUTER_ID_KEY);
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) { // 印度levidi
            return playService
                    .getVideoPlayInfo(videoId, albumId, externalId, mid, source, isPay, stream, timestamp, sig,
                            userName, model, audioId, channelId, langType, kbpsType, commonParam);
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) {
            // 美国版lecom
            return playService.getVideoPlayInfo4LecomUS(StringUtil.toLong(videoId, null),
                    StringUtil.toLong(albumId), stream, timestamp, sig, userName, loginTime, pricePackageType,
                    isFromCntv, isFromCibn, support3d, model, appCode, validDate, audioId, channelId, langType,
                    kbpsType, imei, routerId, operType, supportStream, commonParam, true);
        } else {
            // 临时方案：播控全放开
            if ((commonParam.getBroadcastId() != null) && (CommonConstants.CNTV == commonParam.getBroadcastId())) {
                commonParam.setBroadcastId(null);
            }
            if ((commonParam.getBroadcastId() != null) && (CommonConstants.CIBN == commonParam.getBroadcastId())) {
                userName = BroadcastConstant.transFromBroadcastUsername(userName, commonParam.getBroadcastId());
            }
            // 兼容逻辑，2.9.5之后可去掉
            if (StringUtil.toInteger(commonParam.getUserId(), 1) < 1) {
                commonParam.setUserId(String.valueOf(userid));
            }
            return playService.getVideoPlayInfo(StringUtil.toLong(videoId, null),
                    StringUtil.toLong(albumId), stream, timestamp, sig, userName, loginTime, pricePackageType,
                    isFromCntv, isFromCibn, support3d, model, appCode, validDate, audioId, channelId, langType,
                    kbpsType, imei, routerId, operType, supportStream, false, commonParam, true, false);
        }
    }

    /**
     * 播放引导用户接口
     */
    @ApiOperation(value = "播放引导用户接口", httpMethod = "GET")
    @RequestMapping("/video/play/getConsumeGuideInfo")
    public Response<VideoPlayConsumeGuideDto> getPlayGuideInfo(
            @ApiParam(value = "设备类型, 1-TV", required = false, defaultValue = "1") @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return playService.getPlayGuideInfoV2(deviceType, commonParam);
    }

    /**
     * 获取视频码流列表接口
     * @param videoId
     *            视频ID
     * @param channelId
     *            频道ID
     * @param commonParam
     *            接口通用参数
     * @return PageResponse
     */
    @ApiOperation(value = "获取视频码流列表接口", httpMethod = "GET")
    @RequestMapping("/video/streams/get")
    public PageResponse<Stream> getVideoStreams(
            @ApiParam(value = "视频ID", required = false) @RequestParam(value = "videoid", required = false) Long videoId,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelid", required = false) Integer channelId,
            @ApiParam(value = "是否支持杜比视界", required = false, defaultValue = "false") @RequestParam(value = "supportDV", required = false) Boolean supportDV,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        /*
         * return new
         * PageResponse<Stream>(videoService.
         * getVideoStreams
         * (videoId, channelId,
         * terminalSeries, langCode, broadcastId, terminalBrand,
         * terminalApplication));
         */
        return new PageResponse<Stream>(videoService.getVideoStreams(videoId, channelId,
                commonParam, supportDV));
    }

    /**
     * 获取播放页的互动信息
     * @param videoId
     *            视频id
     * @param type
     *            获取互动数据类型，1--播放器，2--阵营，3--扫码互动
     * @return
     */
    @ApiOperation(value = "获取播放页的互动信息", httpMethod = "GET")
    @RequestMapping("/video/reaction/get")
    public Response<VideoReactionDto> getVideoReaction(
            @ApiParam(value = "视频ID", required = false) @RequestParam(value = "videoId", defaultValue = "0") String videoId,
            @ApiParam(value = "专辑ID", required = false) @RequestParam(value = "albumId", required = false, defaultValue = "0") String albumId,
            @ApiParam(value = "获取互动数据类型，1--播放器，2--阵营，3--扫码互动", required = false, defaultValue = "3", allowableValues = "1,2,3") @RequestParam(value = "type", required = false, defaultValue = "3") String type,
            @ApiParam(value = "客户端请求播放码流", required = false) @RequestParam(value = "stream", required = false) String stream,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (StringUtils.isBlank(type)) {// 默认是扫码互动
            type = VideoConstants.VIDEO_REACTION_TYPE_3;
        }
        if (StringUtils.isBlank(albumId)) {
            albumId = "0";
        }
        return videoService.getVideoReaction(type, videoId, albumId, stream, commonParam);
    }

    @RequestMapping("/video/vrs/stats")
    @ApiOperation(value = "vrs测试", hidden = true, httpMethod = "GET")
    public Object testVrs2() {
        return albumVideoAccess.getStats();
    }

    /**
     * 点播播放
     * @param vrsVideoInfoId
     *            vrs视频id
     * @param stream
     *            码流
     * @param useAuth
     *            是否启用防盗链,开发人员测试用
     * @return
     */
    @ApiOperation(value = "获取播放地址", httpMethod = "GET")
    @RequestMapping("/v3/video/getPlayUrl")
    public Response<VideoPlayDto> getPlayInfo(
            @ApiParam(value = "视频ID", required = true) @RequestParam(value = "vrsVideoInfoId") Long vrsVideoInfoId,
            @ApiParam(value = "用户登录或启动TV版时拿到的服务器时间戳", required = true) @RequestParam(value = "timestamp") Long timestamp,
            @ApiParam(value = "签名", required = true) @RequestParam(value = "sig") String sig,
            @ApiParam(value = "码流", required = false) @RequestParam(value = "stream", required = false) String stream,
            @ApiParam(value = "是否需要302跳转", required = false) @RequestParam(value = "expectDispatcherUrl", required = false) Boolean expectDispatcherUrl,
            @ApiParam(value = "是否需要TS流", required = false) @RequestParam(value = "expectTS", required = false) Boolean expectTS,
            @ApiParam(value = "是否启用防盗链,开发人员测试用", required = false, defaultValue = "true") @RequestParam(value = "useAuth", required = false, defaultValue = "true") Boolean useAuth,
            @ApiParam(value = "播放类型，默认db", required = false, defaultValue = "db") @RequestParam(value = "playType", required = false, defaultValue = "db") String playType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        // 验证加密key
        if (useAuth) {
            Boolean validate = videoService.checkSig(vrsVideoInfoId, timestamp, sig);
            if (!validate) {
                ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.SIG_ERROR, commonParam.getLangcode());
            }
        }
        // 获取播放地址信息
        Boolean expectTSActual = false;// 期待调度地址
        Boolean exceptNomalActual = false;// 期待调度地址
        return downloadService.getVideoPlayForDB(vrsVideoInfoId, stream, null, false, null,
                expectTS, expectTSActual, expectDispatcherUrl, exceptNomalActual, "play", playType, Boolean.TRUE, null,
                null, commonParam);

    }

    /**
     * @return
     */
    @ApiOperation(value = "获取视频信息", httpMethod = "GET")
    @RequestMapping("/v2/video/getVideoInfo")
    public Response<AlbumSeriesDto> getVideoInfoByid(
            @ApiParam(value = "视频ID", required = false) @RequestParam(value = "vid", required = false) Long vid,
            @ApiParam(value = "视频自增ID", required = false) @RequestParam(value = "videoInfoId", required = false) Long videoInId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return new Response<AlbumSeriesDto>(videoService.getVideoInfoByid(vid, videoInId,
                commonParam));
    }

    /**
     * 获取排行榜数据
     * @param commonParam
     * @param size
     *            取得数量
     * @param cid
     *            频道,可以去多个频道，用','隔开
     * @return
     */
    @ApiOperation(value = "获取排行榜数据(VIP)", httpMethod = "GET")
    @RequestMapping("/video/ranklist")
    public Response<Map<String, List<AlbumDto>>> getRankList4Voice(
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam,
            @ApiParam(value = "分页大小", required = false) @RequestParam(value = "size", required = false) Integer size,
            @ApiParam(value = "频道ID,可以去多个频道，用','隔开", required = false) @RequestParam(value = "cid", required = false) String cid,
            @ApiParam(value = "cms板块ID", required = false) @RequestParam(value = "cmsBlockId", required = false) String cmsBlockId) {
        if (size == null || size < 0) {
            size = 10;
        }
        if (StringUtil.isBlank(cid)) { // 默认取全部
            cid = "0";
        }
        return videoService.getPlaylist4Voice(size, cid, cmsBlockId, commonParam);
    }

    /**
     * Live点播播放接口
     * @param videoId
     *            视频id
     * @return
     */
    @ApiOperation(value = "Live点播播放接口", httpMethod = "GET")
    @RequestMapping("/p2/video/play")
    public NewResponse<VODDto> getVideoPlay4Live(
            @ApiParam(value = "视频ID", required = false) @RequestParam(value = "videoId", required = false) Long videoId,
            @ApiParam(value = "专辑ID", required = false) @RequestParam(value = "albumId", required = false) Long albumId,
            @ApiParam(value = "码流类型", required = false) @RequestParam(value = "stream", required = false) String stream,
            @ApiParam(value = "用户登录或启动TV版时拿到的服务器时间戳", required = false) @RequestParam(value = "timestamp") Long timestamp,
            @ApiParam(value = "签名", required = true) @RequestParam(value = "sig") String sig,
            @ApiParam(value = "业务ID", required = false) @RequestParam(value = "businessId") String businessId,
            @ApiParam(value = "业务ID", required = false) @RequestParam(value = "rand", required = false) Integer rand,
            @ApiParam(value = "随机整数", required = false, hidden = true) @RequestParam(value = "uid", required = false) String uid,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return livePlayService.getVideoPlay4Live(videoId, albumId, stream, timestamp, sig,
                businessId, rand, uid, commonParam);
    }

    /**
     * 清除缓存数据
     */
    @ApiOperation(value = "清除缓存数据", httpMethod = "GET")
    @RequestMapping("/commonutil/cache/flush")
    public BaseResponse clearCache(HttpServletRequest request,
                                   @ApiParam(value = "缓存key", required = true) @RequestParam("key") String key,
                                   @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return videoService.clearCache(key, commonParam);
    }

    @ApiOperation(value = "专辑详情页动态信息(VIP)", httpMethod = "GET")
    @RequestMapping("/video/album/detailandseries/more")
    @AuthorizedInterceptorAnnotation
    public Response<AlbumDetailMoreDto> getMore4AlbumDetailAndSeries(
            @ApiParam(name = "favoriteType", value = "收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题") @RequestParam(value = "favoriteType", required = false) Integer favoriteType,
            @ApiParam(name = "globalid", value = "作品库数据的globalid，瀑布流专题传112_{{瀑布流专题id}}") @RequestParam(value = "globalid", required = false) String globalId,
            @ApiParam(name = "ztId", value = "专题ID(瀑布流专题不传，见说明)") @RequestParam(value = "ztId", required = false) Integer ztId,
            @ApiParam(value = "专辑ID", required = true) @RequestParam(value = "albumId", required = true) String albumId,
            @ApiParam(value = "观星活动ID集合，多个用半角逗号分隔", required = false) @RequestParam(value = "posIds", required = false) String posIds,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        Long realAlbumId = 0L;

        BroadcastConstant.transFromBroadcastUsername(commonParam);

        if (!StringUtil.isBlank(albumId) && albumId.matches("^[0-9]*$")) {
            realAlbumId = Long.valueOf(albumId);
        }
        return videoService.getAlbumDetailMore(realAlbumId, posIds, favoriteType, ztId,
                globalId, commonParam);
    }
}
