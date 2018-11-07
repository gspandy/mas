package com.letv.mas.caller.iptv.tvproxy.search;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.BroadcastConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.HttpResponseInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageCommonResponse;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CategoryIdConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.common.controller.BaseController;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.AlbumDetailWebsiteTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.AlbumDetailWebsiteTpResponse.AlbumDetailWebSiteDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.JSVersionDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.SearchCardDataResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.WebsiteTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.search.constant.SearchConstant;
import com.letv.mas.caller.iptv.tvproxy.search.constant.SearchTpConstant;
import com.letv.mas.caller.iptv.tvproxy.search.model.bean.bo.SearchPageResponse;
import com.letv.mas.caller.iptv.tvproxy.search.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.search.model.dto.SearchResultsDto.VientianeDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import search2.extractor.RequestSource;
import serving.GenericServingResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@RestController
//@RequestMapping("${server.path}/v2/search")
public class SearchController extends BaseController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "复式检索(多条件搜索) 从搜索组根据条件检索专辑信息", httpMethod = "GET")
    //@HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_5_MINUTE })
    @RequestMapping(value = "/v2/search/searchTypes")
    public GenericServingResponse searchTypes(
            @ApiParam(value = "页码", required = false, defaultValue = "1") @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "页长", required = false, defaultValue = "45") @RequestParam(value = "pageSize", required = false, defaultValue = "45") Integer pageSize,
            @ApiParam(value = "分类ID", required = false) @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @ApiParam(value = "搜索多条件内容(如：地区 T:V / 3:253 注多个以;分隔)", required = false) @RequestParam(value = "searchContent", required = false) String searchContent,
            @ApiParam(value = "搜索数据类型 参照CategoryConstant的SEARCH_DATA_TYPE", required = false) @RequestParam(value = "dt", required = false, defaultValue = "1,2") String dt,
            @ApiParam(value = "分类(频道新id)", required = false) @RequestParam(value = "newCategoryId", required = false) String newCategoryId,
            @ApiParam(value = "搜索关键词", required = false) @RequestParam(value = "wd", required = false) String word,
            @ApiParam(value = "pushChild", required = false) @RequestParam(value = "pushChild", required = false, defaultValue = "0") String pushChild,
            @ApiParam(value = "用户ID", required = false) @RequestParam(value = "uid", required = false) String uid,
            @ApiParam(value = "ID", required = false) @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "splatid", required = false) @RequestParam(value = "splatid", required = false) String splatid,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelId", required = false) Integer channelId,
            @ApiParam(value = "leIds", required = false) @RequestParam(value = "leIds", required = false) String leIds,
            @ApiParam(value = "productId", required = false) @RequestParam(value = "productId", required = false) String productId,
            @ApiParam(value = "repo_type", required = false) @RequestParam(value = "repo_type", required = false) String repo_type,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam,
            HttpServletRequest request) {
        String newCid = newCategoryId;
        if (StringUtil.isBlank(newCid)) {
            Integer cid = CategoryIdConstant.getNewCategory(categoryId);
            if (cid != null) {
                newCid = cid.toString();
            }
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 45;
        }
        String from = "tv_02";
        String ph = "420007" + ",-121";// 检索出大陆 即可
        String pcp = null; // 控制检索数据是否需要通过如cibn等牌照方的申通
        if (2 == commonParam.getBroadcastId()) { // CIBN版本pcp为2
            pcp = "2";
        }

        return searchService.searchTypes2(from, dt, page, pageSize, word, newCid, ph,
                searchContent, id, splatid, channelId, leIds, productId, pushChild, repo_type, pcp, commonParam);

    }

    /**
     * 获取频道下的搜索条件(地区、年份、类型等等)
     * @param channelId
     *            频道id
     * @return
     */
    @ApiOperation(value = "某频道下分类检索的查询项列表", httpMethod = "GET")
    @RequestMapping(value = "/search/condition/list")
    public @ResponseBody
    PageResponse<SearchCondition> search_condition_list(
            @ApiParam(value = "频道ID", required = false) @RequestParam("channelId") Integer channelId,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {

        return searchService.getSearchConditionList(channelId, commonParam);
    }
    
    /**
     * "频道"下导航Tab页-->某一频道-->频道二级标签检索（推荐与猜你喜欢中间的标签检索）
     * @param page
     *            分页
     * @param pageSize
     *            页大小
     * @param searchType
     *            检索类型
     * @param searchValue
     *            检索值
     * @param categoryId
     *            分类ID(老ID)
     * @param orderType
     *            排序类型
     * @param showTvout
     *            是否显示TV外跳视频 0:只显示TV版权视频 1:显示TV版权+TV外跳
     * @param showPrevue
     *            是否只显示正片 0: 只显示正片
     * @param dt
     *            搜索数据类型 参照CategoryConstant的SEARCH_DATA_TYPE
     * @param newCategoryId
     *            分类ID(新ID)
     * @return
     */
    @ApiOperation(value = "\"频道\"下导航Tab页-->某一频道-->频道二级标签检索（推荐与猜你喜欢中间的标签检索）(VIP)", httpMethod = "GET")
    @RequestMapping(value = "/search/retrieve/searchMenu", method = RequestMethod.GET)
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_15_MINUTE })
    public PageCommonResponse<AlbumInfoDto> searchMenu(
            @ApiParam(value = "页码", required = false) @RequestParam("page") Integer page,
            @ApiParam(value = "页大小", required = false) @RequestParam("pageSize") Integer pageSize,
            @ApiParam(value = "检索类型", required = false) @RequestParam("searchType") Integer searchType,
            @ApiParam(value = "检索值", required = false) @RequestParam("searchValue") String searchValue,
            @ApiParam(value = "分类ID(老ID)", required = false) @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @ApiParam(value = "排序类型", required = false) @RequestParam(value = "orderType", required = false) Integer orderType,
            @ApiParam(value = "是否显示TV外跳视频 0:只显示TV版权视频 1:显示TV版权+TV外跳", required = false) @RequestParam(value = "showTvout", required = false) Integer showTvout,
            @ApiParam(value = "是否只显示正片 0: 只显示正片", required = false) @RequestParam(value = "showPrevue", required = false) Integer showPrevue,
            @ApiParam(value = "搜索数据类型 参照CategoryConstant的SEARCH_DATA_TYPE", required = false, defaultValue = "1,2") @RequestParam(value = "dt", required = false, defaultValue = "1,2") String dt,
            @ApiParam(value = "分类ID(新ID)", required = false) @RequestParam(value = "newCategoryId", required = false) Integer newCategoryId,
            @ApiParam(value = "splatid", required = false) @RequestParam(value = "splatid", required = false) String splatid,
            @ApiParam(value = "leIds", required = false) @RequestParam(value = "leIds", required = false) String leIds,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelId", required = false) Integer channelId,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam,
            HttpServletRequest request) {

        Integer cid = (newCategoryId != null) ? newCategoryId : CategoryIdConstant.getNewCategory(categoryId);
        String ph = "420007,-121";
        String from = SearchConstant.FROM_LETVVIDEO;
        String pcp = null; // 控制检索数据是否需要通过如cibn等牌照方的申通
        if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) { // CIBN版本pcp为2
            pcp = "2";
        }

        // appid platformid 有一个为空时需要全部置空，否则搜索不出数据
        if (StringUtil.isBlank(commonParam.getDisplayAppId()) || StringUtil.isBlank(commonParam.getDisplayPlatformId())) {
            commonParam.setDisplayAppId(null);
            commonParam.setDisplayPlatformId(null);
        }
        // for tvod filter
        if (commonParam != null) {
            if (StringUtil.isBlank(commonParam.getDisplayAppId())
                    && StringUtil.isBlank(commonParam.getDisplayPlatformId())) {
                commonParam.setDisplayAppId(SearchConstant.DISPLAYAPPID);
                commonParam.setDisplayPlatformId(SearchConstant.DISPLAYPLATFORMID);
            }
        }
        return searchService.searchMenu(searchType, cid, page, pageSize, searchValue,
                orderType, from, dt, ph, splatid, channelId, leIds, pcp, commonParam);
    }

    /**
     * 获取搜索建议列表
     * @param type
     *            搜索内容的类型,例如按笔画，按拼音
     * @return
     */
    @ApiOperation(value = "获取搜索建议列表", hidden = true)
    @RequestMapping("/v2/search/getSuggestList")
    public PageResponse<SuggestDto> getSuggestList(@RequestParam("t") String type, @RequestParam("q") String query,
                                                   @RequestParam(value = "m", required = false) String method,
                                                   @RequestParam(value = "from", required = false) String from,
                                                   @RequestParam(value = "platform", required = false, defaultValue = "tv") String platform,
                                                   @RequestParam(value = "splatid", required = false) String splatid,
                                                   @RequestParam(value = "dt", required = false) String dt,
                                                   @RequestParam(value = "ph", required = false) String ph,
                                                   @RequestParam(value = "countryArea", required = false) String countryArea,
                                                   @RequestParam(value = "pushChild", required = false, defaultValue = "0") String pushChild,
                                                   @RequestParam(value = "src", required = false) String src,
                                                   @RequestParam(value = "num", required = false) Integer num,

                                                   @ModelAttribute CommonParam commonParam) {

        // 美国行货 客户端该参数传错，暂作兼容
        if (from.contains("DEVICE")) {
            from = SearchConstant.FROM_LETVVIDEO;
        }
        // 美国行货 TV版乐搜 SDK只传了-121，暂作兼容
        if (!StringUtil.isBlank(ph) && "-121".equals(ph)) {
            ph = "-121,420007";
        }
        if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) {
            splatid = "1060704002";// 国广特殊
        }
        return searchService.getSuggestList(platform, type, query, method, from, splatid, dt,
                ph, countryArea, pushChild, src, num, commonParam);
    }

    /**
     * @param page
     *            分页
     * @param pageSize
     *            分页大小
     * @param keyName
     *            搜索关键字
     * @param dt
     *            搜索数据类型1--专辑 2--视频 3--明星 4--专题
     * @param ph
     *            播放平台 例如TV版权(420007)
     * @param from
     *            搜索调用方来源
     * @param request
     * @return
     */
    @ApiOperation(value = "放大镜的搜索接口", httpMethod = "GET")
    @RequestMapping("/v2/search/sresult_v2")
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_10_MINUTE })
    public SearchPageResponse<SearchResultDto> searchByWord(@RequestParam("page") Integer page,
                                                            @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "keyName") String keyName,
                                                            @RequestParam(value = "dt", defaultValue = "1,2", required = false) String dt,
                                                            @RequestParam(value = "ph", required = false) String ph,
                                                            @RequestParam(value = "from", required = false) String from,
                                                            @RequestParam(value = "or", required = false) String orderType,
                                                            @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                                            @RequestParam(value = "subCategoryId", required = false) Integer subCategoryId,
                                                            @RequestParam(value = "uid", required = false) String uid,
                                                            @RequestParam(value = "splatid", required = false) String splatid,
                                                            @RequestParam(value = "leIds", required = false) String leIds,
                                                            @RequestParam(value = "eid", required = false) String eid,
                                                            @RequestParam(value = "src", required = false) String src,
                                                            @RequestParam(value = "repo_type", required = false) String repo_type,
                                                            @RequestParam(value = "ispay", required = false) String ispay,
                                                            @RequestParam(value = "countryArea", required = false) String countryArea,
                                                            @RequestParam(value = "pushChild", required = false, defaultValue = "0") String pushChild,
                                                            @RequestParam(value = "filterData", required = false) String filterData,
                                                            @ModelAttribute CommonParam commonParam, HttpServletRequest request) {

        String stat = "all";// 三个版本都走动态tab
        String pcp = null;

        if (LocaleConstant.Wcode.CN.equals(commonParam.getWcode())) {
            // 播控方为LETV
            if (BroadcastConstant.BROADCAST_LETV == commonParam.getBroadcastId()) {
                // 来自live的请求不作处理
                if (from != null && !from.equals("mobile_live08") && !from.equals("mobile_live32")) {
                    splatid = "1009";
                }
            } else if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) {
                splatid = "1060704002";// 国广特殊
                // 老版本搜索未传时 ，将过滤掉未审核通过的数据
                if (filterData == null || filterData.equals("0")) {
                }
                pcp = "2";// 志纲确认后再尝试放开
            }

        } else if (LocaleConstant.Wcode.US.equals(commonParam.getWcode())) {
            ph = "420007,-121";// 美国行货线上强制修改
        } else if (LocaleConstant.Wcode.HK.equals(commonParam.getWcode())) {
            if (from != null && !from.equals("mobile_live08") && !from.equals("mobile_live32")) {
                splatid = "1060303002";
            }
        }

        // appid platformid 有一个为空时需要全部置空，否则搜索不出数据
        if (StringUtil.isBlank(commonParam.getDisplayAppId()) || StringUtil.isBlank(commonParam.getDisplayPlatformId())) {
            commonParam.setDisplayAppId(null);
            commonParam.setDisplayPlatformId(null);
        }
        // for tvod filter
        if (commonParam != null) {
            if (StringUtil.isBlank(commonParam.getDisplayAppId())
                    && StringUtil.isBlank(commonParam.getDisplayPlatformId())) {
                commonParam.setDisplayAppId(SearchConstant.DISPLAYAPPID);
                commonParam.setDisplayPlatformId(SearchConstant.DISPLAYPLATFORMID);
            }
        }
        return searchService.searchByKeyword2(from, src, orderType, ph, dt, page, pageSize,
                keyName, categoryId, subCategoryId, splatid, leIds, eid, repo_type, ispay, stat, countryArea,
                pushChild, pcp, commonParam);
    }

    /**
     * 万象搜索接口
     * @param from
     *            搜索调用方来源
     * @param dt
     *            搜索数据类型1--专辑 2--视频 3--明星 4--专题 5--app应用 6--直播 8--音乐专辑 9--单首音乐
     * @param page
     *            分页
     * @param pageSize
     *            分页大小
     * @param word
     *            搜索关键字
     * @param ph
     *            播放平台 例如TV版权(420007)
     * @param mix
     *            混排方式 万象传2
     * @param splatid
     *            直播平台
     * @param limitRst
     *            是否需要限制搜索数量，传1
     * @return
     */
    @ApiOperation(value = "万象搜索接口", hidden = true)
    @RequestMapping("/v2/search/pano")
    public Response<VientianeDto> panoSearch(@RequestParam("from") String from, @RequestParam("dt") String dt,
                                             @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                                             @RequestParam("word") String word, @RequestParam("ph") String ph, @RequestParam("mix") Integer mix,
                                             @RequestParam("splatid") String splatid, @RequestParam("limitRst") String limitRst,
                                             @RequestParam("pcp") String pcp, @RequestParam(value = "categoryId", required = false) String categoryId,
                                             @RequestParam(value = "eid", required = false) String eid, @ModelAttribute CommonParam commonParam) {

        return searchService.panoSearch(from, dt, page, pageSize, word, ph, mix, splatid,
                limitRst, pcp, categoryId, eid, commonParam);

    }

    /**
     * 频道二级标签 猜你喜欢接口
     * @param pageSize
     * @param lc
     * @param cid
     *            0、首页你可能喜欢 -1、乐视推荐台专辑列表
     * @param area
     *            rec_0001表示相关推荐，rec_0002表示首页及频道页个性化，rec_0003表示轮播个性化推荐
     * @return
     */
    @ApiOperation(value = "频道二级标签 猜你喜欢接口", httpMethod = "GET")
    @RequestMapping("/v2/recommend/getUserLikeAlbums")
    public PageResponse<AlbumInfoDto> getUserLikeAlbums(
            @ApiParam(value = "页长", required = false) @RequestParam("pageSize") Integer pageSize,
            @ApiParam(value = "mac", required = false) @RequestParam("lc") String lc,
            @ApiParam(value = "0、首页你可能喜欢 -1、乐视推荐台专辑列表", required = false) @RequestParam("cid") String cid,
            @ApiParam(value = "rec_0001表示相关推荐，rec_0002表示首页及频道页个性化，rec_0003表示轮播个性化推荐", required = false) @RequestParam("area") String area,
            @ApiParam(value = "历史", required = false) @RequestParam(value = "rcHistory", required = false) String history,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelId", required = false) Integer channelId,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {
        List<AlbumInfoDto> dtoList = searchService.getUserLikeAlbums("0004", lc, area,
                pageSize, cid, history, channelId, commonParam);
        Integer categoryId = null;
        try {
            categoryId = Integer.parseInt(cid);
        } catch (Exception e) {

        }
        if (!CollectionUtils.isEmpty(dtoList)) {
            this.changeSubTitle(dtoList, categoryId);
        }

        return new PageResponse<AlbumInfoDto>(dtoList);
    }

    /**
     * 搜索桌面，card搜索数据
     * @param cardId
     *            卡片id，为空时默认返回102-103-104-105-106五种卡片数据
     * @param num
     *            每个卡片返回数据个数
     * @param history
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "搜索桌面，card搜索数据", hidden = true)
    @RequestMapping("/search/card/data")
    public PageResponse<SearchCardDto> getCardData(@RequestParam(value = "cardId", required = false) String cardId,
                                                   @RequestParam(value = "num", required = false) Integer num,
                                                   @RequestParam(value = "history", required = false) String history,
                                                   @RequestParam(value = "splatid", required = false) String splatid,
                                                   @RequestParam(value = "ph", required = false) String ph,
                                                   @RequestParam(value = "from", required = false) String from,
                                                   @RequestParam(value = "src", required = false) String src,
                                                   @RequestParam(value = "platform", required = false) String platform,
                                                   @RequestParam(value = "fromWX", required = false) String fromWX, @ModelAttribute CommonParam commonParam) {

        // 线上搜索桌面没传这三个值，给默认值，热词接口李阳已确认传了
        platform = StringUtils.isBlank(platform) ? "tv" : platform;
        from = StringUtils.isBlank(from) ? "tv_desk09" : from;
        cardId = StringUtils.isBlank(cardId) ? SearchTpConstant.CARD_ID_PARAM_FOR_SEARCH : cardId;

        // 搜索桌面的请求作特殊处理，新热词接口李阳已确认传了
        if (from.equals("tv_desk09")) {
            ph = "420007,-121";
        }

        if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) {
            splatid = "1060704002";// 国广特殊
            // 客户端有num并且小于20时强制改为20，防止后端数据过滤后不足10条
            if (num != null && num < 20) {
                num = 20;
            }
        }

        return searchService.getCardData(cardId, num, history, splatid, ph, platform, from,
                src, commonParam.getDisplayAppId(), commonParam.getDisplayPlatformId(), fromWX, commonParam);
    }

    /**
     * 搜索接口---获取指定服务的id获取数据, 1.老的乐搜首页调用（香港在用，可以干掉） 2.推荐搜索词（还在调用）
     * @param cardId
     *            :cardId
     * @param num
     *            :number
     * @param history
     *            :历史记录
     * @return
     */
    @ApiOperation(value = "搜索接口---获取指定服务的id获取数据, 1.老的乐搜首页调用（香港在用，可以干掉） 2.推荐搜索词（还在调用）", httpMethod = "GET")
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_30_MINUTE })
    @RequestMapping("/v2/search/getCardData")
    public Response<SearchCardDataResponse> getCardData1(
            @ApiParam(value = "cardId", required = false) @RequestParam(value = "cardId", required = false) String cardId,
            @ApiParam(value = "from", required = false) @RequestParam(value = "from", required = false) String from,
            @ApiParam(value = "platform", required = false, defaultValue = "tv") @RequestParam(value = "platform", required = false, defaultValue = "tv") String platform,
            @ApiParam(value = "history", required = false) @RequestParam(value = "history", required = false) String history,
            @ApiParam(value = "num", required = false) @RequestParam("num") Integer num,
            @ApiParam(value = "splatid", required = false) @RequestParam(value = "splatid", required = false) String splatid,
            @ApiParam(value = "ph", required = false) @RequestParam(value = "ph", required = false) String ph,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {
        String src = null;
        if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) {
            src = "1"; // 国广只取内网数据
            splatid = "1060704002";// 国广特殊
        }
        if (LocaleConstant.Wcode.US.equals(commonParam.getWcode())) {
            ph = "420007,-121";// 美国行货线上强制修改
        }
        platform = StringUtil.isBlank(platform) ? "tv" : platform;

        return new Response<SearchCardDataResponse>(searchService.getCardData(cardId, history,
                num, src, ph, platform, splatid, from, commonParam.getDisplayAppId(),
                commonParam.getDisplayPlatformId(), commonParam));
    }

    /**
     * 乐搜新首页获取推送专辑
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "乐搜新首页获取推送专辑", hidden = true)
    @RequestMapping("/search/mainpage/push")
    public PageResponse<MainPageHeadDto> getMainPagePushAlbums(
            @RequestParam(value = "uid", required = false) String uid,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "splatid", required = false) String splatid,
            @RequestParam(value = "ph", required = false) String ph, @ModelAttribute CommonParam commonParam) {

        ph = "420007,-121";// CIBN版强升后全面下掉（大陆没加）
        if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) {
            splatid = "1060704002";// 国广特殊
        }
        return searchService.getMainPagePushAlbums("tv", ph, from, splatid,
                commonParam.getDisplayAppId(), commonParam.getDisplayPlatformId(), commonParam);
    }

    /**
     * 获取乐搜新首页频道列表；最新电影，热播电视剧等；开机调用
     * @return
     */
    @ApiOperation(value = "乐搜新首页获取推送专辑", hidden = true)
    @RequestMapping("/search/mainpage/channel")
    public PageResponse<MainPageChannelDto> getMainPageChannels(
            @RequestParam(value = "uid", required = false) String uid,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "splatid", required = false) String splatid,
            @RequestParam(value = "ph", required = false) String ph, @ModelAttribute CommonParam commonParam) {

        ph = "420007,-121";// CIBN版强升后全面下掉（大陆没加）
        if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) {
            splatid = "1060704002";// 国广特殊
        }
        return searchService.getMainPageChannels("tv", ph, from, splatid,
                commonParam.getDisplayAppId(), commonParam.getDisplayPlatformId(), commonParam);
    }

    /**
     * 乐搜首页频道列表，选择频道时调用
     * @param chid
     *            频道id
     * @return
     */
    @ApiOperation(value = "乐搜首页频道列表，选择频道时调用", hidden = true)
    @RequestMapping("/search/mainpage/topics")
    public Response<MainPageTopicsDto> getLesoMainPageTopics(@RequestParam(value = "uid", required = false) String uid,
                                                             @RequestParam(value = "chid") String chid, @RequestParam(value = "from", required = false) String from,
                                                             @RequestParam(value = "splatid", required = false) String splatid,
                                                             @RequestParam(value = "ph", required = false) String ph, @ModelAttribute CommonParam commonParam) {

        ph = "420007,-121";// CIBN版强升后全面下掉（大陆没加）
        if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) {
            splatid = "1060704002";// 国广特殊
        }
        return searchService.getMainPageTopics("tv", ph, chid, from, splatid,
                commonParam.getDisplayAppId(), commonParam.getDisplayPlatformId(), commonParam);
    }

    /**
     * @param topicid
     *            话题id
     * @param page
     *            分页
     * @param pageSize
     *            分页大小
     * @param history
     *            客户端观看记录 vid1-vid2-.....-vid10
     * @return
     */
    @ApiOperation(value = "根据视频或专辑信息，获取推荐列表；单板块推荐", httpMethod = "GET")
    @RequestMapping("/search/mainpage/topic/data")
    public Response<MainPageTopicDataDto> getLesoMainPageTopicData(
            @ApiParam(value = "用户ID", required = false) @RequestParam(value = "uid", required = false) String uid,
            @ApiParam(value = "频道id", required = false) @RequestParam(value = "chid") String channelId,
            @ApiParam(value = "话题id", required = false) @RequestParam(value = "topicid") String topicid,
            @ApiParam(value = "分页", required = false) @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "分页大小", required = false) @RequestParam(value = "pageSize", required = false, defaultValue = "25") Integer pageSize,
            @ApiParam(value = "客户端观看记录 vid1-vid2-.....-vid10", required = false) @RequestParam(value = "history", required = false) String history,
            @ApiParam(value = "from", required = false) @RequestParam(value = "from", required = false) String from,
            @ApiParam(value = "splatid", required = false) @RequestParam(value = "splatid", required = false) String splatid,
            @ApiParam(value = "ph", required = false) @RequestParam(value = "ph", required = false) String ph,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {

        ph = "420007,-121";// CIBN版强升后全面下掉（大陆没加）
        if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) {
            splatid = "1060704002";// 国广特殊
        }
        return searchService.getMainPageTopicData("tv", ph, channelId, topicid, page, pageSize,
                history, from, splatid, commonParam.getDisplayAppId(), commonParam.getDisplayPlatformId(), commonParam);
    }

    /**
     * 根据IP获取地域信息
     * 如果ip地址为空，则默认取接口调用方的ip地址
     * @param request
     * @return
     */
    @ApiOperation(value = "根据IP获取地域信息", httpMethod = "GET")
    @RequestMapping("/location")
    public Response<IpLocationDto> getIpLocation(
            @ApiParam(value = "splatid", required = false) @RequestParam(value = "splatid", required = false) String splatid,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam,
            HttpServletRequest request) {
        return searchService.getIpLocation(commonParam.getClientIp(), splatid, commonParam);
    }

    /**
     * 结果页中，从内网搜索专辑详情页
     * @param aid
     *            专辑id
     * @param type
     *            专辑类型 0 来自PTV；1来自VRS；2来自外网；3来自IPTV
     * @param from
     *            来源，搜索统计需要 230表示TV
     * @param page
     *            页码
     * @param pageSize
     *            每页数据量
     * @return
     */
    @ApiOperation(value = "结果页中，从内网搜索专辑详情页", httpMethod = "GET")
    @RequestMapping("/v2/search/webSiteAlbumDetail")
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_5_MINUTE })
    public Response<AlbumDetailDto> getAlbumDetail(
            @ApiParam(value = "专辑id", required = false) @RequestParam(value = "aid") Long aid,
            @ApiParam(value = "专辑类型 0 来自PTV；1来自VRS；2来自外网；3来自IPTV", required = false) @RequestParam(value = "type") String type,
            @ApiParam(value = "来源，搜索统计需要 230表示TV", required = false) @RequestParam(value = "from", required = false, defaultValue = "230") String from,
            @ApiParam(value = "页码", required = false) @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @ApiParam(value = "每页数据量", required = false) @RequestParam(value = "pageSize", required = false, defaultValue = "65535") Integer pageSize,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {

        from = SearchConstant.FROM_LESO;
        return searchService.getAlbumDetail(aid, type, from, CommonConstants.TV_PLAY_PLAT_FROM,
                page, pageSize, commonParam);

    }

    /**
     * 结果页中，从外网搜索专辑详情页
     * @param aid
     *            专辑id
     * @param site
     *            站源信息
     * @return
     */
    @ApiOperation(value = "结果页中，从外网搜索专辑详情页", httpMethod = "GET")
    @RequestMapping("/v2/search/series")
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_5_MINUTE })
    public Response<AlbumDetailWebSiteDto> getAlbumDetailWebSite(
            @ApiParam(value = "专辑id", required = false) @RequestParam(value = "aid") Long aid,
            @ApiParam(value = "站源信息", required = false) @RequestParam(value = "site") String site,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {
        return searchService.getAlbumDetailWebSite(aid, site, SearchConstant.FROM_LESO,
                commonParam);
    }

    /**
     * 获取站源列表
     * @param aid
     *            专辑id
     * @param type
     *            专辑类型 0 来自PTV；1来自VRS；2来自外网；3来自IPTV
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取站源列表", httpMethod = "GET")
    @RequestMapping("/v2/search/website")
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_5_MINUTE })
    public PageResponse<WebsiteTpResponse.WebsiteDto> getWebSiteList(
            @ApiParam(value = "专辑id", required = false) @RequestParam(value = "aid") Long aid,
            @ApiParam(value = "专辑类型 0 来自PTV；1来自VRS；2来自外网；3来自IPTV", required = false) @RequestParam(value = "type") String type,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {

        return searchService.getWebSiteList(aid, type, CommonConstants.TV_PLAY_PLAT_FROM,
                SearchConstant.FROM_LESO, commonParam);
    }

    /**
     * 专辑详情中演职表查看明星详情数据
     * @param id
     *            明星id
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "专辑详情中演职表查看明星详情数据", httpMethod = "GET")
    @RequestMapping("/v2/search/getStarById")
    public Response<SearchStarDetailDto> getStarDetail(
            @ApiParam(value = "明星id", required = false) @RequestParam("id") String id,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {

        return searchService.getStarDetail(id, commonParam);
    }

    /**
     * 专辑详情中演职表查看明星详情数据时，同时会根据明星姓名获取明星作品类别
     * @param name
     *            明星姓名
     * @return
     */
    @ApiOperation(value = "专辑详情中演职表查看明星详情数据时，同时会根据明星姓名获取明星作品类别", httpMethod = "GET")
    @RequestMapping("/v2/search/getStarByName")
    public Response<SearchStarCategoryDto> getStarCategory(
            @ApiParam(value = "明星姓名", required = false) @RequestParam("name") String name,
            @ApiParam(value = "分页大小", required = false) @RequestParam(value = "num", required = false, defaultValue = "1") Integer pageSize,
            @ApiParam(value = "页码", required = false) @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "数据上报字段,mac_uid", required = false) @RequestParam(value = "lc", required = false) String lc,
            @ApiParam(value = "用户uid", required = false) @RequestParam(value = "uid", required = false) String uid,
            @ApiParam(value = "来源：tv", required = false) @RequestParam(value = "from", required = false) String from,
            @ApiParam(value = "splatid", required = false) @RequestParam(value = "splatid", required = false) String splatid,
            @ApiParam(value = "ph", required = false) @RequestParam(value = "ph", required = false) String ph,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {

        ph = "420007,-121";// CIBN强升后 删除

        // appid platformid 有一个为空时需要全部置空，否则搜索不出数据
        if (StringUtil.isBlank(commonParam.getDisplayAppId()) || StringUtil.isBlank(commonParam.getDisplayPlatformId())) {
            commonParam.setDisplayAppId(null);
            commonParam.setDisplayPlatformId(null);
        }

        return searchService.getStarCategory(name, null, ph, page, pageSize, from, splatid,
                commonParam.getDisplayAppId(), commonParam.getDisplayPlatformId(), commonParam);

    }

    /**
     * 根据明星姓名及作品类别 获取明星作品
     * @param name
     *            明星名字
     * @param page
     *            页码
     * @param pageSize
     *            每页大小
     * @param dataType
     *            数据类型
     * @param categoryId
     *            频道分类
     * @param uid
     * @param from
     * @param splatid
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "根据明星姓名及作品类别 获取明星作品", httpMethod = "GET")
    @RequestMapping("/v2/search/getStarWorks")
    public Response<SearchStarWorksDto> getStarWorks(
            @ApiParam(value = "明星姓名", required = false) @RequestParam("name") String name,
            @ApiParam(value = "页码", required = false, defaultValue = "1") @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "每页大小", required = false, defaultValue = "11") @RequestParam(value = "ps", required = false, defaultValue = "11") Integer pageSize,
            @ApiParam(value = "数据类型", required = false) @RequestParam("dataType") String dataType,
            @ApiParam(value = "频道分类", required = false) @RequestParam("category") Integer categoryId,
            @ApiParam(value = "用户ID", required = false) @RequestParam(value = "uid", required = false) String uid,
            @ApiParam(value = "来源 tv", required = false) @RequestParam(value = "from", required = false) String from,
            @ApiParam(value = "splatid", required = false) @RequestParam(value = "splatid", required = false) String splatid,
            @ApiParam(value = "ph", required = false) @RequestParam(value = "ph", required = false) String ph,
            @ApiParam(value = "filterData", required = false) @RequestParam(value = "filterData", required = false) String filterData,
            @ApiParam(value = "leId", required = false) @RequestParam(value = "leId", required = false) String leId,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {

        ph = "420007,-121";// CIBN版强升后删除；
        String dt = "1,2";
        String order = "3";
        String sf = "starringName,directoryName,actorName";
        String src = "1";

        String pcp = null;
        // 老版本搜索未传时 ，将过滤掉未审核通过的数据
        if (filterData == null || filterData.equals("0")) {
            pcp = "2";
        }

        // appid platformid 有一个为空时需要全部置空，否则搜索不出数据
        if (StringUtil.isBlank(commonParam.getDisplayAppId()) || StringUtil.isBlank(commonParam.getDisplayPlatformId())) {
            commonParam.setDisplayAppId(null);
            commonParam.setDisplayPlatformId(null);
        }
        // for tvod filter
        if (commonParam != null) {
            if (StringUtil.isBlank(commonParam.getDisplayAppId())
                    && StringUtil.isBlank(commonParam.getDisplayPlatformId())) {
                commonParam.setDisplayAppId(SearchConstant.DISPLAYAPPID);
                commonParam.setDisplayPlatformId(SearchConstant.DISPLAYPLATFORMID);
            }
        }
        return searchService.getStarWorks2(from, dt, page, pageSize, name, categoryId, ph, src,
                order, splatid, sf, pcp, leId, commonParam);

    }

    /**
     * 新的获取流地址服务
     * @param url
     *            请求播放的 编码后的url
     * @param os
     *            客户端操作系统 0-ios 1-android 可以有多个，用分号隔开
     * @param format
     *            请求流 清晰度 详情 Format，可以有多个，用 分号 隔开
     * @param provinceId
     *            省份id
     * @param distinctId
     *            地区id
     * @param site
     *            站源
     * @param aid
     *            专辑id
     * @param vid
     *            视频Id
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "新的获取流地址服务", httpMethod = "GET")
    @RequestMapping("/v2/search/getStream")
    public PageResponse<GetStreamDto> getStream(
            @ApiParam(value = "请求播放的 编码后的url", required = false) @RequestParam("url") String url,
            @ApiParam(value = "客户端操作系统 0-ios 1-android 可以有多个，用分号隔开", required = false) @RequestParam("os") String os,
            @ApiParam(value = "请求流 清晰度 详情 Format，可以有多个，用 分号 隔开", required = false) @RequestParam("format") String format,
            @ApiParam(value = "省份id", required = false) @RequestParam("provinceId") String provinceId,
            @ApiParam(value = "地区id", required = false) @RequestParam("distinctId") String distinctId,
            @ApiParam(value = "站源", required = false) @RequestParam("site") String site,
            @ApiParam(value = "专辑id", required = false) @RequestParam(value = "aid", required = false) String aid,
            @ApiParam(value = "视频Id", required = false) @RequestParam(value = "vid", required = false) String vid,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {
        return searchService.getStream(url, os, format, RequestSource.LEVIEW.getValue(),
                provinceId, distinctId, site, aid, vid, commonParam);
    }

    /**
     * 本地流播放失败时上报错误信息，
     * 参考：http://wiki.letv.cn/pages/viewpage.action?pageId=52863561 流程图
     * @param url
     *            请求播放的url
     * @param os
     *            客户端操作系统 0-ios 1-android
     * @param format
     *            请求流 清晰度 详情 Format
     * @param failState
     *            流失效原因 详情FailState
     */
    @ApiOperation(value = "本地流播放失败时上报错误信息", httpMethod = "GET")
    @RequestMapping("/v2/search/updateStream")
    public Response<String> updateStream(
            @ApiParam(value = "请求播放的url", required = false) @RequestParam("url") String url,
            @ApiParam(value = "客户端操作系统 0-ios 1-android", required = false) @RequestParam("os") Integer os,
            @ApiParam(value = "请求流 清晰度 详情 Format", required = false) @RequestParam("format") Integer format,
            @ApiParam(value = "流失效原因 详情FailState", required = false) @RequestParam("failState") Integer failState,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {
        Boolean result = searchService.updateStream(url, os.toString(), format.toString(),
                RequestSource.LEVIEW.getValue(), failState, commonParam);
        Response<String> response = new Response<String>();
        if (result == null || !result) {
            response.setResultStatus(0);
        }
        return response;
    }

    /**
     * 客户端JS解析出流地址信息后并可以播放，将本地生成的流地址上传给服务端
     * 参考：http://wiki.letv.cn/pages/viewpage.action?pageId=52863561 流程图
     * @param url
     *            请求播放的url
     * @param streams
     *            流信息
     * @param os
     *            客户端操作系统 0-ios 1-android
     * @param format
     *            请求流 清晰度 详情 Format
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "客户端JS解析出流地址信息后并可以播放，将本地生成的流地址上传给服务端", httpMethod = "GET")
    @RequestMapping("/v2/search/uploadLocalStream")
    public Response<String> uploadLocalStream(
            @ApiParam(value = "请求播放的url", required = false) @RequestParam("url") String url,
            @ApiParam(value = "流信息", required = false) @RequestParam("streams") String streams,
            @ApiParam(value = "客户端操作系统 0-ios 1-android", required = false) @RequestParam("os") Integer os,
            @ApiParam(value = "请求流 清晰度 详情 Format", required = false) @RequestParam("format") Integer format,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam) {
        List<String> streamList = new LinkedList<String>();
        for (String stream : streams.split(";")) {
            streamList.add(stream);
        }
        Boolean result = searchService.uploadLocalStream(url, streamList, os, format,
                commonParam);
        Response<String> response = new Response<String>();
        if (result == null || !result) {
            response.setResultStatus(0);
        }
        return response;
    }

    /**
     * 获取JS版本信息，乐搜开机调用
     * @return
     */
    @ApiOperation(value = "获取JS版本信息，乐搜开机调用", httpMethod = "GET")
    @RequestMapping("/v2/search/getJSVersion")
    public Response<JSVersionDto> getJSVersion() {
        return searchService.getJSVersion();
    }

    /**
     * 不合理需求
     * 电视剧，动漫，综艺，音乐强行更改subTitle
     * @param dtoList
     * @param cid
     *            新的类别ID
     */
    private void changeSubTitle(List<AlbumInfoDto> dtoList, Integer cid) {
        if (cid == null || dtoList == null || dtoList.size() == 0) {
            return;
        }

        if (VideoConstants.Category.TV == cid) {
            for (AlbumInfoDto dto : dtoList) {
                if ("album".equals(dto.getVideoType())) { // 针对专辑才修改subTitle
                    if (!dto.isEnd() && dto.getFollownum() != null && dto.getFollownum() > 0) {

                        dto.setSubTitle("更新至" + dto.getFollownum() + "集");
                    }
                }
            }
        } else if (VideoConstants.Category.MUSIC == cid) {
            for (AlbumInfoDto dto : dtoList) {
                if ("video".equals(dto.getVideoType())) {
                    String star = dto.getStarring();
                    if (star != null) {
                        if (star.startsWith(",")) {
                            star = star.substring(1);
                        }
                        if (star.endsWith(",")) {
                            star = star.substring(0, star.length() - 1);
                        }
                    }
                    dto.setSubTitle(star);
                }
            }
        } else if (VideoConstants.Category.VARIETY == cid) {
            for (AlbumInfoDto dto : dtoList) {
                if ("album".equals(dto.getVideoType())) {
                    dto.setSubTitle("更新至" + dto.getFollownum() + "期");
                }
            }
        } else if (VideoConstants.Category.CARTOON == cid) {
            for (AlbumInfoDto dto : dtoList) {
                if ("album".equals(dto.getVideoType())) { // 针对专辑才修改subTitle
                    if (dto.isEnd()
                            || (dto.getEpisodes() != null && dto.getFollownum() != null && dto.getEpisodes().equals(
                            dto.getFollownum()))) {
                        dto.setSubTitle(dto.getEpisodes() + "集全");
                    } else {
                        dto.setSubTitle("更新至" + dto.getFollownum() + "集");
                    }
                }
            }
        }
    }

    /****************************************************************
     * 下面三个接口getUserCard all 服务端确认没调用 getSearchCategory下期干掉，从搜索返回的数据获取
     ***************************/

    /**
     * 获取搜索所需要的所有频道分类（乐搜右侧），数值参见VideoConstants.Category.*；开机调用；
     * 可静态化
     * @param langCode
     *            语言参数
     * @param wcode
     *            地区参数
     * @param broadcastId
     *            播控方
     * @return
     */
    @ApiOperation(value = "获取搜索所需要的所有频道分类（乐搜右侧），数值参见VideoConstants.Category.*；开机调用；", httpMethod = "GET")
    @RequestMapping("/v2/search/getSearchCategory")
    @HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_1_HOUR })
    public PageResponse<SearchCategoryDto> getAllCategory(
            @ApiParam(value = "语言参数", required = false) @RequestParam(value = "langcode", required = false, defaultValue = "zh_cn") String langCode,
            @ApiParam(value = "地区参数", required = false) @RequestParam(value = "wcode", required = false, defaultValue = "cn") String wcode,
            @ApiParam(value = "播控方", required = false) @RequestParam(value = "broadcastId", required = false) Integer broadcastId) {
        if ("EN".equalsIgnoreCase(langCode)) {
            langCode = "en_US";
        }
        CommonParam commonParam = new CommonParam();
        commonParam.setBroadcastId(broadcastId);
        commonParam.setLangcode(langCode);
        commonParam.setWcode(wcode);

        return searchService.getAllCateogry(commonParam);
    }
}

