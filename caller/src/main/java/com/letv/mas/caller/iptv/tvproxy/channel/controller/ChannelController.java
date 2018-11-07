package com.letv.mas.caller.iptv.tvproxy.channel.controller;

import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.HttpResponseInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.channel.service.ChannelService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AdvertisementPicture;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.SubjectDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.StartupAdResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.TerminalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "/iptv/api/new/channel", description = "频道列表相关")
@RestController(value = "v2.ChannelController")
public class ChannelController {

    @Autowired
    ChannelService channelService;

    /**
     * @param parentChannelId
     *            频道ID
     * @param support3D
     *            是否支持3D
     * @param vnum
     *            版本号老参数
     * @param supportStream
     *            设备是否支持某些特殊码流，格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "频道墙", httpMethod = "GET")
    @RequestMapping("/channel/list")
    public PageResponse<Channel> channel_list(
            @ApiParam(value = "父频道ID", required = false) @RequestParam(value = "parentChannelId", required = false) Integer parentChannelId,
            @ApiParam(value = "是否支持3D，0代表不支持，1代表支持（默认）", required = false) @RequestParam(value = "support3D", required = false, defaultValue = "1") String support3D,
            @ApiParam(value = "版本号老参数", required = false, defaultValue = "5.0") @RequestParam(value = "vnum", required = false, defaultValue = "5.0") String vnum,
            @RequestParam(value = "mcode", required = false) Integer mcode,
            @ApiParam(value = "设备是否支持某些特殊码流, 格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断", required = false) @RequestParam(value = "supportStream", required = false) String supportStream,
            @ApiParam(value = "cms页面id", required = false) @RequestParam(value = "cmsPageId", required = false) String cmsPageId,
            @ApiParam(value = "频道编码", required = false) @RequestParam(value = "channelCode", required = false) String channelCode,
            @ApiParam(value = "分页页码", required = false) @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "分页大小", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (!TerminalUtil.isLetvUs(commonParam) && mcode != null && mcode == 3) {
            return new PageResponse<Channel>(null);
        }
        return channelService.getChannelList(parentChannelId, cmsPageId, support3D, vnum,
                mcode, supportStream, channelCode, page, pageSize, commonParam);
    }

    /**
     * 频道数据列表；
     * 频道数据是从某一数据来源得到的数据信息集合，可以是从CMS读取的信息，也可以使走搜索推荐读取的信息；一个频道
     * 数据的数据信息集合可以包含页面跳转信息、视频、专辑、专题、直播、定制或默认板块，也可以使推荐内容链接；
     * @param channelId
     *            频道Id
     * @param support3D
     *            是否支持3D
     * @param vnum
     *            老参数版本号
     * @param age
     *            年龄
     * @param gender
     *            性别
     * @param model
     *            模式代码1儿童
     * @param cpId
     *            cp标识
     * @param commonParam
     *            通用参数
     * @return
     *         返回的Response包含集合、普通对象类型data
     */
    @ApiOperation(value = "频道数据列表(VIP)", notes = "注：线上客户端调用通过静态接口<br/>"
            + "频道数据是从某一数据来源得到的数据信息集合，可以是从CMS读取的信息，也可以使走搜索推荐读取的信息；一个频道\n"
            + "数据的数据信息集合可以包含页面跳转信息、视频、专辑、专题、直播、定制或默认板块，也可以使推荐内容链接；<br/>" + "版本更新：<br/>"
            + "- 2017.9月需求：增加角标，统一在chargeInfos中获取(免费则不返回chargeInfos)", httpMethod = "GET")
    @RequestMapping("/channel/data/list")
    public BaseResponse channel_data_list(
            @ApiParam(value = "频道ID", required = true) @RequestParam(value = "channelId") Integer channelId,
            @ApiParam(value = "是否支持3D，0代表不支持，1代表支持（默认）", required = false, defaultValue = "1") @RequestParam(value = "support3D", required = false, defaultValue = "1") String support3D,
            @ApiParam(value = "版本号老参数", required = false, defaultValue = "5.0") @RequestParam(value = "vnum", required = false, defaultValue = "5.0") String vnum,
            @ApiParam(value = "年龄", required = false) @RequestParam(value = "age", required = false) String age,
            @ApiParam(value = "性别，F－女，M－男", required = false) @RequestParam(value = "gender", required = false) String gender,
            @ApiParam(value = "时间周数", required = false) @RequestParam(value = "week", required = false) String week,
            @ApiParam(value = "模式标识，0-超级影视（默认）, 1-儿童相关请求", required = false, allowableValues = "0,1") @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
            @ApiParam(value = "会员类型，0－非会员，1－会员", required = false) @RequestParam(value = "viptype", required = false) Integer vipType,
            @ApiParam(value = "内容提供方ID", required = false) @RequestParam(value = "cpId", required = false) String cpId,
            @ApiParam(value = "内容提供方分类ID", required = false) @RequestParam(value = "cpCategoryId", required = false) String cid,
            @ApiParam(value = "请求频道数据数量，默认值6", required = false, defaultValue = "6") @RequestParam(value = "num", required = false, defaultValue = "6") Integer num,
            @ApiParam(value = "频道代码，例如tv、film", required = false) @RequestParam(value = "channelCode", required = false) String channelCode,
            @ApiParam(value = "设备能否分别支持3D，DB，4K码流，0代表不支持，1代表支持，2代表客户端无法判断，例如：1_0_2。 当该参数存在时会覆盖support3D", required = false) @RequestParam(value = "supportStream", required = false) String supportStream,
            @ApiParam(value = "cms页面id", required = false) @RequestParam(value = "pageId", required = false) String pageId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        return channelService.getChannelDataList(channelId, pageId, support3D, age, gender,
                week, vipType, model, vnum, cpId, cid, num, channelCode, supportStream, commonParam);
    }

    /**
     * @param pageId
     *            cms系统配置的id，一般传空由服务端默认取值
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "频道首页", httpMethod = "GET")
    @RequestMapping("/tv/channel/data/list")
    public Response<ChannelComRespDto> ChannelDataList(
            @ApiParam(value = "频道ID", required = true) @RequestParam(value = "channelId") Integer channelId,
            @ApiParam(value = "cms页面id", required = false) @RequestParam(value = "pageId", required = false) String pageId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getChannelDataList(channelId, pageId, commonParam);
    }

    /**
     * 频道推荐数据列表
     * @param rcPageid
     *            推荐页面ID
     * @param rcHistory
     *            用户播放记录
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "频道推荐数据列表(VIP)", httpMethod = "GET")
    @RequestMapping("/channel/data/recommendation/list")
    public BaseResponse channel_data_recommendation_list(
            @ApiParam(value = "推荐页面ID，如Lecom美国版大首页是page_cms1003448011", required = false) @RequestParam(value = "rcPageid", required = false) String rcPageid,
            @ApiParam(value = "用户播放记录", required = false) @RequestParam(value = "rcHistory", required = false) String rcHistory,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelId", required = false) Integer channelId,
            @ApiParam(value = "内容提供方ID", required = false) @RequestParam(value = "cpId", required = false) String cpId,
            @ApiParam(value = "内容提供方分类ID", required = false) @RequestParam(value = "cpCategoryId", required = false) String cid,
            @ApiParam(value = "请求频道数据数量，默认值12", required = false, defaultValue = "12") @RequestParam(value = "num", required = false, defaultValue = "12") Integer num,
            @ApiParam(value = "开始索引，仅为印度版本", required = false, defaultValue = "0") @RequestParam(value = "startIndex", required = false, defaultValue = "0") Integer startIndex,
            @ApiParam(value = "频道代码，例如tv、film，仅为印度版本", required = false) @RequestParam(value = "channelCode", required = false) String channelCode,
            @ApiParam(value = "TODO类型，仅为印度版本", required = false) @RequestParam(value = "bizType", required = false) String bizType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getChannelDataRecommendationList(rcPageid, rcHistory, cpId, cid,
                num, channelId, startIndex, channelCode, bizType, commonParam);
    }

    /**
     * 提供给第三方SDK的推荐页接口，其数据从缓存中取出，而在/channel/data/recommendation/list接口中根据业务设置缓存
     * @param rcPageid
     *            推荐页面ID，如Lecom美国版大首页是page_cms1003448011
     * @param dataIndex
     *            返回数据是二维数组形式，这里dataIndex只获取排序为第n的那组数据，排序从0开始，支持多组，使用英文逗号分隔，如0,
     *            2,3,表示获取第1、3、 4组数据
     * @param dataTypes
     *            数据类型过滤，不传则不过滤，返回所有数据，否则只返dataTypes制定的数据，参见数据类型；多种数据使用英文逗号分隔，如1
     *            ,2表示只返回专辑和视频
     * @param size
     *            请求每组数据数量，不传或小于等于0则全部返回，大于每组数据量则全部返回，最大值20
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "第三方SDK推荐页接口", notes = "提供给第三方SDK的推荐页接口，其数据从缓存中取出，而在/channel/data/recommendation/list接口中根据业务设置缓存", httpMethod = "GET")
    @RequestMapping("/channel/data/sdk/recommendation/list")
    public BaseResponse channelDataSkdRecommendationList(
            @ApiParam(value = "推荐页面ID，如Lecom美国版大首页是page_cms1003448011", required = true) @RequestParam(value = "rcPageid") String rcPageid,
            @ApiParam(value = "返回数据是二维数组形式，这里dataIndex只获取排序为第n的那组数据，排序从0开始，支持多组，使用英文逗号分隔，如0, 2,3,表示获取第1、3、 4组数据", required = false) @RequestParam(value = "dataIndex", required = false) String dataIndex,
            @ApiParam(value = "数据类型过滤，不传则不过滤，返回所有数据，否则只返dataTypes制定的数据，参见数据类型；多种数据使用英文逗号分隔，如1,2表示只返回专辑和视频", required = false) @RequestParam(value = "dataTypes", required = false) String dataTypes,
            @ApiParam(value = "请求每组数据数量，不传或小于等于0则全部返回，大于每组数据量则全部返回，最大值20", required = false) @RequestParam(value = "size", required = false) Integer size,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getChannelDataSdkRecommendationList(rcPageid, dataIndex,
                dataTypes, size, commonParam);
    }

    /**
     * 专题详情接口：获得专题数据，专题包数据
     * 专题包参数[packageIds]不为空,即认为客户端要获取指定包数据
     * @param subjectId
     *            专题id
     * @param packageIds
     *            专题包id
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "专题详情接口", notes = "获得专题数据，专题包数据；<br/>" + "版本更新：<br/>"
            + "- 2017.9月需求：增加角标，统一在chargeInfos中获取(免费则不返回chargeInfos)", httpMethod = "GET")
    @RequestMapping("/channel/data/subject/content/get")
    public Response<SubjectDto> getSubjectPackagesById(
            @ApiParam(value = "专题ID", required = false) @RequestParam(value = "subjectId", required = false) String subjectId,
            @ApiParam(value = "专题包ID", required = false) @RequestParam(value = "packageIds", required = false) String packageIds,
            @ApiParam(value = "模式标识，0-超级影视（默认）, 1-儿童相关请求", required = false, allowableValues = "0,1") @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getSubjectPackagesById(subjectId, packageIds, model, commonParam);
    }

    /**
     * 获取某一热点专题下的某个视频包
     * @param subjectId
     *            热点专题id
     * @param packageIds
     *            视频包id
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "获取某一热点专题下的某个视频包", notes = "多了cache策略；<br/>" + "版本更新：<br/>"
            + "- 2017.9月需求：增加角标，统一在chargeInfos中获取(免费则不返回chargeInfos)", httpMethod = "GET")
    @RequestMapping("/channel/data/subject/hotspot/get")
    public Response<SubjectDto> getHotspotSubjectPackageData(
            @ApiParam(value = "专题ID", required = false) @RequestParam(value = "subjectId", required = false) String subjectId,
            @ApiParam(value = "专题包ID", required = true) @RequestParam(value = "packageIds") String packageIds,
            @ApiParam(value = "模式标识，0-超级影视（默认）, 1-儿童相关请求", required = false, allowableValues = "0,1") @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        return channelService.getHotspotSubjectPackageData(subjectId, packageIds, model,
                commonParam);
    }

    /**
     * 获取某一排行榜内数据
     * @param chartsId
     *            排行榜id
     * @return
     */
    @ApiOperation(value = "获取某一排行榜内数据", httpMethod = "GET")
    @RequestMapping(value = "/channel/data/charts/content/get", method = RequestMethod.GET)
    public Response<ChartsDto> getChartsContentById(
            @ApiParam(value = "排行榜ID", required = true) @RequestParam("chartsId") Integer chartsId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getChartsContentById(chartsId, commonParam);
    }

    /**
     * 乐视儿童：课程表
     * @param channelId
     *            课程表id
     * @param age
     *            年龄
     * @param gender
     *            性别
     * @param week
     *            星期
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "乐视儿童：课程表(VIP)", httpMethod = "GET")
    @RequestMapping("/channel/data/block/content/getCourse")
    public Response<BlockContentDto> getCourse(
            @ApiParam(value = "频道ID", required = true) @RequestParam(value = "channelId") Integer channelId,
            @ApiParam(value = "年龄", required = false) @RequestParam(value = "age", required = false) String age,
            @ApiParam(value = "性别", required = false) @RequestParam(value = "gender", required = false) String gender,
            @ApiParam(value = "星期", required = false) @RequestParam(value = "week", required = false) String week,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getCourse(channelId, age, gender, week, commonParam);
    }

    @ApiOperation(value = "乐视儿童：课程表2(VIP)", httpMethod = "GET")
    @RequestMapping("/channel/data/block/content/getCourse2")
    public Response<BlockContentDto> getCourse2(
            @ApiParam(value = "年龄", required = false) @RequestParam(value = "age", required = false) String age,
            @ApiParam(value = "星期", required = false) @RequestParam(value = "week", required = false) String week,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getCourse_v2(age, week, commonParam);
    }

    /**
     * 加载广告图片
     * @param page
     *            页码
     * @param pageSize
     *            页码大小
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "加载广告图片", httpMethod = "GET")
    @RequestMapping(value = "/v2/startup/advertise/pic", method = RequestMethod.GET)
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_5_MINUTE })
    public Response<StartupAdResponse> getV2StartupAdvertisePic(
            @ApiParam(value = "分页页码", required = false, defaultValue = "1") @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "分页大小", required = false, defaultValue = "5") @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        StartupAdResponse<AdvertisementPicture> resp = new StartupAdResponse<AdvertisementPicture>();
        Integer type = null;
        // levidi过滤广告
        if (CommonConstants.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) {
            // resp.setStatus(0);
            // resp.setMsg("加载广告图片");
            // resp.setPostAd("http://i2.letvimg.com/webtv/201212/25/1356429426421.png");
            return new Response<StartupAdResponse>(null);
        }
        List<AdvertisementPicture> list = channelService.getStartupAdvertisePic(type, "TV2.0",
                page, pageSize);
        resp.setItems(list);
        if (list != null && list.size() > 0) {
            resp.setStatus(0);
            resp.setMsg("加载广告图片");
            resp.setPostAd(list.get(0).getImagUrl());
        } else {
            resp.setStatus(0);
            resp.setMsg("加载广告图片");
            resp.setPostAd("http://i2.letvimg.com/webtv/201212/25/1356429426421.png");
        }
        return new Response<StartupAdResponse>(resp);
    }

    /**
     * 加载广告图片
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "加载广告图片(VIP)", httpMethod = "GET")
    @RequestMapping(value = "/startup/advertise/pic")
    public Response<StartupAdResponse> getStartupAdvertisePic(
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getStartupAdvertisePic(commonParam);
    }

    /**
     * TV2.5 大首页 | 频道 | 专题首页 接口
     * @param channelId
     *            约定的频道参数
     *            [频道名称:参数]
     *            [推荐:index]
     *            [电视剧:tv]
     *            [电影:film]
     *            [亲子:qinzi]
     *            [动漫:cartoon]
     *            [体育:sport]
     *            [NBA:nba]
     *            [综艺:complex]
     *            [娱乐:entertainment]
     *            [音乐:music]
     *            [纪录片:documentary]
     *            [热点:hotspot]
     *            [专题:topic]
     *            [4K:4k]
     *            [猜你喜欢:favourate]
     * @param validDate
     *            会员有效期
     * @return
     */
    @ApiOperation(value = "TV2.5 大首页 | 频道 | 专题首页 接口(VIP)", httpMethod = "GET")
    @RequestMapping("/channel/content")
    public PageResponse<ChannelDto> getChannel(
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelid", required = false) String channelId,
            @ApiParam(value = "会员有效期", required = false) @RequestParam(value = "validDate", required = false) String validDate,
            @ApiParam(value = "设备类型", required = false, defaultValue = "1") @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        return new PageResponse<ChannelDto>(channelService.getMine(channelId, validDate,
                deviceType, commonParam));
    }

    /**
     * 获取CMS板块内容
     * @param blockId
     * @return
     */
    @ApiOperation(value = "获取CMS板块内容(VIP)", httpMethod = "GET")
    @RequestMapping("/channel/data/block/content/get")
    public Response<BaseData> getCmsBlockContent(
            @ApiParam(value = "CMS板块ID, dataType=63、64、65时可为空", required = false) @RequestParam(value = "blockId", required = false) String blockId,
            @ApiParam(value = "数据类型: 63-超影退出页, 64-儿童独立版退出页, 65-超影关于页", required = false) @RequestParam(value = "dataType", required = false) Integer dataType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getCmsBlockContent(blockId, dataType, commonParam);
    }

    /**
     * 芈月传容器接口
     * @param containerId
     *            新型推广容器id
     * @param comParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "芈月传容器接口((VIP))", httpMethod = "GET")
    @RequestMapping("/channel/data/container/get")
    public Response<ContainerDto> getContainerDataById(
            @ApiParam(value = "容器id（不同的容器id能够表示不同的推广内容）", required = true) @RequestParam("containerId") Integer containerId,
            @ApiParam(value = "是否会员标记：0－非会员或未登陆，1－会员", required = false, defaultValue = "0") @RequestParam(value = "viptype", required = false, defaultValue = "0") Integer viptype,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam comParam) {
        if (viptype == null) {
            viptype = 0;
        }
        return channelService.getContainerDataById(containerId, viptype, comParam);
    }

    /**
     * 清空色块内存数据
     */
    @ApiOperation(value = "清空色块内存数据", httpMethod = "GET")
    @RequestMapping("/channel/recblock/cache/clear")
    public BaseResponse clearCache(HttpServletRequest request) {
        return channelService.clearRecBlockCache();
    }

    /**
     * 清空内存数据
     */
    @ApiOperation(value = "清空内存数据", httpMethod = "GET")
    @RequestMapping("/commonutil/cache/clear")
    public BaseResponse clearCache(HttpServletRequest request,
                                   @ApiParam(value = "缓存key", required = true) @RequestParam("clearId") Integer clearId,
                                   @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.clearCache(clearId, commonParam);
    }

    @ApiOperation(value = "cms页面接口(VIP)", httpMethod = "GET")
    @RequestMapping("/page/list")
    public PageResponse<Channel> pagelist(
            @ApiParam(value = "cms页面id", required = true) @RequestParam(value = "cmsPageId") String cmsPageId,
            @ApiParam(value = "是否支持3D，0代表不支持，1代表支持（默认）", required = false, defaultValue = "1") @RequestParam(value = "support3D", required = false, defaultValue = "1") String support3D,
            @ApiParam(value = "版本号老参数", required = false, defaultValue = "5.0") @RequestParam(value = "vnum", required = false, defaultValue = "5.0") String vnum,
            @RequestParam(value = "mcode", required = false) Integer mcode,
            @ApiParam(value = "设备能否分别支持3D，DB，4K码流，0代表不支持，1代表支持，2代表客户端无法判断，例如：1_0_2。 当该参数存在时会覆盖support3D", required = false) String supportStream,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (!TerminalUtil.isLetvUs(commonParam) && mcode != null && mcode == 3) {
            return new PageResponse<Channel>(null);
        }
        return channelService.getPageList(cmsPageId, support3D, mcode, supportStream,
                commonParam);
    }

    @ApiOperation(value = "cms页面数据接口(VIP)", httpMethod = "GET")
    @RequestMapping("/page/data/list")
    public BaseResponse pageDataList(
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelId", required = false) Integer channelId,
            @ApiParam(value = "cms页面id", required = false) @RequestParam(value = "cmsPageId", required = false) String cmsPageId,
            @ApiParam(value = "年龄", required = false) @RequestParam(value = "age", required = false) String age,
            @ApiParam(value = "性别", required = false) @RequestParam(value = "gender", required = false) String gender,
            @ApiParam(value = "星期", required = false) @RequestParam(value = "week", required = false) String week,
            @ApiParam(value = "模式标识，0-超级影视（默认）, 1-儿童相关请求", required = false, allowableValues = "0,1") @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
            @ApiParam(value = "是否会员标记：0－非会员或未登陆，1－会员", required = false, defaultValue = "0") @RequestParam(value = "viptype", required = false) Integer vipType,
            @RequestParam(value = "flushCode", required = false, defaultValue = "0") Integer flushCode,
            @ApiParam(value = "内容提供方ID", required = false) @RequestParam(value = "cpId", required = false) String cpId,
            @ApiParam(value = "内容提供方分类ID", required = false) @RequestParam(value = "cpCategoryId", required = false) String cid,
            @ApiParam(value = "请求频道数据数量，默认值6", required = false, defaultValue = "6") @RequestParam(value = "num", required = false, defaultValue = "6") Integer num,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getPageDataList(channelId, cmsPageId, age, gender, week, vipType,
                model, flushCode, commonParam);
    }

    @ApiOperation(value = "儿童页面数据列表接口", httpMethod = "GET")
    @RequestMapping("/child/data/list")
    public Response<ChannelComRespDto> ChildDataList(
            @ApiParam(value = "cms页面id", required = false) @RequestParam(value = "pageId", required = false) String pageId,
            @ApiParam(value = "年龄", required = false) @RequestParam(value = "age", required = false) String age,
            @ApiParam(value = "性别", required = false) @RequestParam(value = "gender", required = false) String gender,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return channelService.getChildDataList(pageId, age, gender, commonParam);
    }

    /**
     * 获取会员内容包
     * @param productId
     *            会员id
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取会员内容包", httpMethod = "GET")
    @RequestMapping("/channel/data/getContentPackage")
    public PageResponse<ContentPackageDto> getContentPackage(
            @ApiParam(value = "会员产品id", required = false) @RequestParam(value = "productId", required = false) Integer productId,
            @ApiParam(value = "分页页码", required = false) @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "分页大小", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        return channelService.getContentPackage(productId, page, pageSize, commonParam);
    }
}
