package com.letv.mas.caller.iptv.tvproxy.recommendation;

import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.HttpResponseInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto.RecomendationVideoByIdDto;
import com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto.RecommendationChildTagDTO;
import com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto.RecommendationMaybeLikeDto;
import com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto.RecommendationRelationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/iptv/api", description = "推荐相关")
@RestController(value = "v2.RecommendationController")
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    /**
     * 根据视频或专辑信息，获取推荐列表；单板块推荐
     * 推荐接口所需参数可参见文档http://wiki.letv.cn/pages/viewpage.action?pageId=32708712
     * @param categoryId
     *            分类id；推荐接口中该参数用法为，对于相关推荐、分频道推荐、分type推荐等，cid取用户当前观看行为的视频、
     *            专辑或频道的cid；有特例。
     * @param albumId
     *            专辑id
     * @param videoId
     *            视频id
     * @param number
     *            要获取的数据量
     * @param area
     *            页面区域，推荐接口关键参数；目前仅支持rec_0001--相关推荐，rec_0020--TV音乐台
     * @param rcHistory
     *            传递用户最近观看的10个视频，按历史时刻顺序，vid1是最新观看的视频，格式如
     *            "vid1-vid2-vid3-vid4-vid5-vid6-vid7-vid8-vid9-vid10"
     * @param commonParam
     *            通用参数列表
     * @param rcType
     *            推荐类型，2--播放结束的短视频推荐(2.9.3新需求，视频结束后推荐单视频，由于推荐不支持，还是走退出播放的推荐。
     *            与客户端协定rcType=2代表视频播放结束后的短视频推荐)
     * @return
     */
    @ApiOperation(value = "根据视频或专辑信息，获取推荐列表；单板块推荐", httpMethod = "GET")
    @RequestMapping("/recommendation/list")
    public BaseResponse recommendation_list(
            @ApiParam(value = "分类id", required = false) @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @ApiParam(value = "专辑id", required = false) @RequestParam(value = "albumId", required = false) Integer albumId,
            @ApiParam(value = "视频id", required = false) @RequestParam(value = "videoId", required = false) Integer videoId,
            @ApiParam(value = "要获取的数据量", required = false) @RequestParam(value = "number", required = false, defaultValue = "4") Integer number,
            // TODO ligang 20151216 为什么默认值是rec_0020
            // TODO ligang 20151216 area是不是不应该由客户端直传？
            @ApiParam(value = "页面区域，推荐接口关键参数；目前仅支持rec_0001--相关推荐，rec_0020--TV音乐台", required = false) @RequestParam(value = "area", required = false, defaultValue = "rec_0020") String area,
            @ApiParam(value = "CMS中配置的标签ID", required = false) @RequestParam(value = "pageId", required = false) String pageId,
            @ApiParam(value = "传递用户最近观看的10个视频，按历史时刻顺序，vid1是最新观看的视频，格式如vid1-vid2-vid3-vid4-vid5-vid6-vid7-vid8-vid9-vid10", required = false) @RequestParam(value = "rcHitory", required = false) String rcHistory,
            // TODO ligang 20151216 recType有意义？
            @ApiParam(value = "推荐类型，2--播放结束的短视频推荐(2.9.3新需求，视频结束后推荐单视频，由于推荐不支持，还是走退出播放的推荐。与客户端协定rcType=2代表视频播放结束后的短视频推荐)", required = false) @RequestParam(value = "rcType", required = false) String rcType,
            @ApiParam(value = "globalId", required = false) @RequestParam(value = "globalId", required = false) String globalId,
            @ApiParam(value = "通用参数列表") @ModelAttribute CommonParam commonParam) {

        return recommendationService.getSingleBlock(categoryId, albumId, videoId, globalId,
                number, area, pageId, rcHistory, rcType, commonParam);
    }

    /**
     * 可替换为 /recommendation/new/list接口
     * 获取儿童某一标签下的专辑数据；多板块推荐，一部分数据来自cms
     * @param pageId
     *            CMS中配置的标签ID
     * @param history
     *            播放记录，例如： 123-123-123-123-123 最多十个
     * @return
     */
    // （接口合并为 /recommendation/new/list）
    @ApiOperation(value = "获取儿童某一标签下的专辑数据", httpMethod = "GET")
    @RequestMapping("/recommendation/child/list")
    public BaseResponse recommendation_child_list(
            @ApiParam(value = "CMS中配置的标签ID", required = false) @RequestParam(value = "tagId", required = true) String pageId,
            @ApiParam(hidden = true) @RequestParam(value = "model", required = false) Integer model,
            @ApiParam(value = "播放记录", required = false) @RequestParam(value = "history", required = false) String history,
            @ModelAttribute CommonParam commonParam) {

        if (commonParam.getBroadcastId() == null || "".equals(commonParam.getBroadcastId())) {// 默认letv
            commonParam.setBroadcastId(CommonConstants.LETV);
        }
        return recommendationService.getRecommendationChildList(pageId, history, commonParam);
        // return
        // recommendationService.getMultiBlocks(pageId,
        // null, model, history, commonParam);
    }

    /**
     * 根据年龄获取儿童标签
     * @param age
     *            年龄
     * @param commonParam
     *            通用参数列表
     * @return
     */
    // （接口合并为 /recommendation/new/list）
    @ApiOperation(value = "根据年龄获取儿童标签", httpMethod = "GET")
    @RequestMapping("/recommendation/child/tag")
    public PageResponse<RecommendationChildTagDTO> recommendation_child_tag(
            @ApiParam(value = "年龄", required = false) @RequestParam(value = "age", required = true) Integer age,
            @ApiParam(value = "通用参数列表") CommonParam commonParam) {

        return recommendationService.getChildTags(age, commonParam);
    }

    /**
     * 更新推荐容错缓存
     * @param commonParam
     *            通用参数列表
     * @return
     */
    @ApiOperation(value = "更新推荐容错缓存", httpMethod = "GET")
    @RequestMapping("/recommendation/cache")
    public Response<Boolean> updateRecoveryCache(@ApiParam(value = "通用参数列表") @ModelAttribute CommonParam commonParam) {
        recommendationService.updateRecCache(commonParam);
        return new Response<Boolean>(true);
    }

    /**
     * 更新推荐容错缓存(新)
     * @param commonParam
     *            通用参数列表
     * @return
     */
    @ApiOperation(value = "更新推荐容错缓存(新)", httpMethod = "GET")
    @RequestMapping("/recommendation/new/cache")
    public Response<Boolean> updateNewRecoveryCache(
            @ApiParam(value = "推荐类型") @RequestParam(value = "type", required = true) Integer type,
            @ApiParam(value = "通用参数列表") @ModelAttribute CommonParam commonParam) {
        recommendationService.updateNewRecCache(type, commonParam);
        return new Response<Boolean>(true);
    }

    /**
     * 以片搜片接口，支持内外网数据,走推荐接口
     * @param iptvAlbumId
     *            老专辑id
     * @param albumId
     *            新专辑id
     * @param vrsVideoInfoId
     *            视频id
     * @param num
     *            数量
     * @param src
     *            数据来源 1:内网 2:外网
     * @param categoryId
     *            频道id
     * @return
     */
    // 和专辑相关 （接口合并为 /recommendation/album/relation）
    @ApiOperation(value = "以片搜片接口，支持内外网数据,走推荐接口", httpMethod = "GET")
    @RequestMapping("/search/album/similar")
    public PageResponse<RecomendationVideoByIdDto> recVideoById(
            @ApiParam(value = "老专辑id") @RequestParam(value = "iptvAlbumId", required = false) Long iptvAlbumId,
            @ApiParam(value = "新专辑id") @RequestParam(value = "albumId", required = false) Long albumId,
            @ApiParam(value = "视频id") @RequestParam(value = "vrsVideoInfoId", required = false) Long vrsVideoInfoId,
            @ApiParam(value = "数量") @RequestParam(value = "num", required = false, defaultValue = "10") Integer num,
            @ApiParam(value = "数据来源 1:内网 2:外网") @RequestParam(value = "src") String src,
            @ApiParam(value = "频道id") @RequestParam(value = "categoryId") Integer categoryId,
            @ApiParam(value = "通用参数列表") @ModelAttribute CommonParam commonParam) {

        if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() != CommonConstants.CIBN) {
            commonParam.setBroadcastId(null);
        }
        albumId = albumId == null ? iptvAlbumId : albumId;
        return recommendationService.searchVideoById(categoryId, albumId, src, num,
                vrsVideoInfoId, commonParam);
    }

    /**
     * 访问专辑详情页,会同时请求相关视频
     * @param albumId
     *            专辑ID
     * @param categoryId
     *            频道ID
     * @param page
     *            分页
     * @param pageSize
     *            分页大小
     * @param uid
     *            用户id
     * @param pt
     *            推荐调用来源
     * @param area
     *            推荐页面内容
     * @return
     */
    // 专辑相关 （接口合并为 /recommendation/album/relation）
    @ApiOperation(value = "访问专辑详情页,会同时请求相关视频", httpMethod = "GET")
    @RequestMapping("/album/relation")
    public Response<RecommendationRelationDto> recRelation(
            @ApiParam(value = "专辑ID") @RequestParam(value = "albumId") Long albumId,
            @ApiParam(value = "频道ID") @RequestParam(value = "categoryid") Integer categoryId,
            @ApiParam(value = "分页") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "分页大小") @RequestParam(value = "pageSize", required = false, defaultValue = "45") Integer pageSize,
            @ApiParam(value = "用户id") @RequestParam(value = "uid", required = false, defaultValue = "") String uid,
            @ApiParam(value = "推荐调用来源") @RequestParam(value = "pt", required = false, defaultValue = "0004") String pt,
            @ApiParam(value = "推荐页面内容") @RequestParam(value = "area", required = false, defaultValue = "rec_0010") String area,
            @ApiParam(value = "用户观看历史") @RequestParam(value = "history", required = false, defaultValue = "") String history,
            @ApiParam(value = "通用参数列表") @ModelAttribute CommonParam commonParam) {
        if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() != CommonConstants.CIBN) {
            commonParam.setBroadcastId(null);
        }
        if (area == null || "".equalsIgnoreCase(area)) {
            area = "rec_0010";
        }
        Integer num = pageSize == null ? 45 : pageSize;

        return recommendationService.recRelation(albumId, categoryId, num, uid, pt, area,
                history, commonParam);
    }

    /**
     * 猜你喜欢 客户端缓存30分钟 暂时无法合并
     * 猜你喜欢接口，获取推荐数据；乐搜当搜索无结果时使用；开机调用
     * @param uid
     *            用户的uid
     * @param pt
     *            推荐接口需要的调用来源
     * @param area
     *            页面区域
     * @param num
     *            推荐结果列表大小
     * @param history
     *            用户观看历史
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "猜你喜欢接口，获取推荐数据", httpMethod = "GET")
    @RequestMapping("/v2/search/searchMayBeLike")
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_30_MINUTE })
    public PageResponse<RecommendationMaybeLikeDto> recMaybeLike(
            @ApiParam(value = "用户的uid") @RequestParam(value = "uid", required = false, defaultValue = "") String uid,
            @ApiParam(value = "推荐接口需要的调用来源") @RequestParam(value = "pt", required = false, defaultValue = "0004") String pt,
            @ApiParam(value = "页面区域") @RequestParam(value = "area", required = false, defaultValue = "rec_0002") String area,
            @ApiParam(value = "推荐结果列表大小") @RequestParam(value = "num", required = false, defaultValue = "10") String num,
            @ApiParam(value = "专辑ID") @RequestParam(value = "model", required = false, defaultValue = "0") String model,
            @ApiParam(value = "用户观看历史") @RequestParam(value = "history", required = false, defaultValue = "") String history,
            @ApiParam(value = "频道ID") @RequestParam(value = "channelId", required = false) Integer channelId,
            @ApiParam(value = "通用参数列表") @ModelAttribute CommonParam commonParam) {
        if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() != CommonConstants.CIBN) {
            commonParam.setBroadcastId(null);
        }
        if (StringUtils.isBlank(commonParam.getUserId())) {
            commonParam.setUserId(uid);
        }
        int size = 12;
        if (num != null && num.matches("\\d+")) {
            try {
                size = Integer.parseInt(num);
            } catch (Exception e) {
            }
        }

        // 2016-04-19乐搜桌面传参错误，服务端先容错
        if (history != null) {
            history = history.replaceAll(",", "-");
        }

        return recommendationService.recMaybeLike(pt, area, size, history, model, channelId,
                commonParam);
    }

    /**
     * 乐见桌面新接口，同时老接口recommnedation/list保留
     * @param number
     * @param rcType
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "乐见桌面新接口", hidden = true)
    @RequestMapping(value = { "/v2/leview/desk/cibn", "/v2/leview/desk/le", "/p10/leview/get" })
    public BaseResponse leview(@RequestParam(value = "number", required = false, defaultValue = "4") Integer number,
                               @RequestParam(value = "rcType", required = false) String rcType, @ModelAttribute CommonParam commonParam) {
        return recommendationService.getLeviewData(number, rcType, commonParam);
    }
}
