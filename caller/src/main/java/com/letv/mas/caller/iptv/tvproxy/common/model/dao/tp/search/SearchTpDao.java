package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search;

import com.fasterxml.jackson.core.type.TypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.RankingListTpResponse.RankingList;
import com.alibaba.fastjson.JSONArray;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.MmsDataUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import search2.extractor.*;
import search2.proto.SearchRequest;
import serving.*;

import javax.annotation.Resource;
import java.util.*;

@Component
public class SearchTpDao extends BaseTpDao {
    private final static Logger log = LoggerFactory.getLogger(SearchTpDao.class);
    //@Resource
    private FlvcdExtractService.Iface playStreamServing;

    @Resource
    private GenericServing.Iface searchServing;

    //@Resource
    private GenericServing.Iface panoSearchServing;

    //@Resource
    private GenericDetailServing.Iface detailServing;

    /*@Autowired(required = false)
    private SessionCache sessionCache;*/

    /**
     * 搜索接口
     */
    public GenericServingResponse search(String from, String dt, Integer page, Integer pageSize, String word,
            String categoryId, Integer subCategoryId, String ph, String src, Integer mix, String order,
            String searchContent, String splatid, String leIds, String eid, String repo_type, String ispay,
            String albumFilter, String videoFilter, String jf, String sf, String stat, String countryArea,
            String productId, String pushChild, String pcp, CommonParam commonParam) {
        String logPrefix = "search_" + from + "_" + dt + "_" + page + "_" + pageSize + "_" + word + "_" + categoryId
                + "_" + ph + "_" + src + "_" + mix + "_" + order + "_" + searchContent + "_" + splatid + "_" + leIds
                + "_" + eid + "_" + repo_type + "_" + ispay + "_" + albumFilter + "_" + videoFilter + "_" + jf + "_"
                + sf + "_" + stat + "_" + countryArea + "_" + pushChild;
        String paramsStr = null;
        GenericServingResponse response = null;
        try {
            long stime = System.currentTimeMillis();
            GenericServingRequest mGenericServingRequest = this.getSearchRequest(from, dt, page, pageSize, word,
                    categoryId, subCategoryId, ph, src, mix, order, searchContent, splatid, leIds, eid, repo_type,
                    ispay, albumFilter, videoFilter, jf, sf, stat, countryArea, productId, null, pushChild, pcp, null,
                    commonParam);
            response = this.searchServing.Serve(mGenericServingRequest);
            long end = System.currentTimeMillis();
            if (response != null && response.getSearch_response() != null) {
                log.info(logPrefix + " " + response.getSearch_response().getEid());
            }
            if (response == null || response.getSearch_response() == null
                    || CollectionUtils.isEmpty(response.getSearch_response().getServing_result_list())) {
                if (mGenericServingRequest != null && mGenericServingRequest.getSearch_request() != null
                        && mGenericServingRequest.getSearch_request().getAll_param() != null) {
                    Map<String, String> params = mGenericServingRequest.getSearch_request().getAll_param();
                    if (params != null && params.size() > 0) {
                        try {
                            paramsStr = " GenericServing.Iface search_params :" + JSONArray.toJSONString(params);
                        } catch (Exception e) {
                            log.info("GenericServing.Iface search_params to json is error" + e.getMessage());
                        }
                    }
                }
                if (paramsStr != null) {
                    logPrefix = paramsStr;
                }
                if (response == null) {
                    log.info(logPrefix + " !!!!!!!!!!!!!! response is null");
                } else if (response.getSearch_response() == null) {
                    log.info(logPrefix + " !!!!!!!!!!!!!! response.getSearch_response() is null");
                } else {
                    log.info(logPrefix
                            + " !!!!!!!!!!!!!! response.getSearch_response().getServing_result_list() is empty");
                }
            }
            if (commonParam != null && commonParam.getDebug() != null && SessionCache.getSession() != null) {
                int debugFlag = commonParam.getDebug();
                if (debugFlag == 1 || debugFlag == 2) {
                    if (mGenericServingRequest != null && mGenericServingRequest.getSearch_request() != null
                            && mGenericServingRequest.getSearch_request().getAll_param() != null) {
                        Map<String, String> params = mGenericServingRequest.getSearch_request().getAll_param();
                        if (params != null && params.size() > 0) {
                            try {
                                paramsStr = " GenericServing.Iface search_params :" + JSONArray.toJSONString(params);
                            } catch (Exception e) {

                            }
                        }
                    }
                    if (paramsStr != null) {
                        logPrefix = paramsStr;
                    }
                    String url = logPrefix;
                    String data = "";
                    if (debugFlag == 2) {
                        if (response != null) {
                            if (response.getSearch_response() != null) {
                                data = response.getSearch_response().toString();
                            } else {
                                data = response.toString();
                            }
                        }
                    }
                    String result = "|500|0|" + (end - stime) + "|0";
                    if (response != null) {
                        result = "|200|" + response.toString().getBytes().length + "|" + (end - stime) + "|0";
                    }
                    SessionCache.getSession().setResponse(url, data, result);
                }
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * 万象搜索接口
     */
    public GenericServingResponse panoSearch(String from, String dt, Integer page, Integer pageSize, String word,
            String ph, Integer mix, String splatid, String limitRst, String pcp, String categoryId, String eid,
            CommonParam commonParam) {
        String logPrefix = "search_" + from + "_" + dt + "_" + page + "_" + pageSize + "_" + word + "_" + "_" + ph
                + "_" + mix + "_" + splatid + "_" + limitRst;
        GenericServingResponse response = null;
        try {
            response = this.panoSearchServing.Serve(this.getSearchRequest(from, dt, page, pageSize, word, categoryId,
                    null, ph, null, mix, null, null, splatid, null, eid, null, null, null, null, null, null, null,
                    null, null, limitRst, null, null, "super_search", commonParam));
            if (response != null && response.getSearch_response() != null) {
                log.info(logPrefix + " " + response.getSearch_response().getEid());
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * 搜索接口、万象搜索接口参数拼接
     * @param from
     *            搜索来源,客户端传入 乐搜-tv_01 tv版-tv_02
     * @param dt
     *            搜索数据类型 参照CategoryConstant的SEARCH_DATA_TYPE
     * @param page
     *            页码
     * @param pageSize
     *            每页大小
     * @param word
     *            搜索关键字
     * @param categoryId
     *            搜索分类id
     * @param ph
     *            版权限制
     * @param src
     *            内外网数据 内网-1 外网-2
     * @param mix
     *            是否为混合数据 1为是 不传为否
     * @param order
     *            排序
     * @param searchContent
     *            检索内容
     * @param splatid
     *            直播后台对应的一个值，不同的值对应一条产品线，编辑根据这个值在不同的产品线勾选对应的直播内容
     * @param leIds
     *            明星乐词id
     * @param eid
     *            上一次搜索唯一标识
     * @param repo_type
     *            0表示请求国内数据 1表示请求海外数据 搜索系统默认为0
     * @param ispay
     *            是否付费
     * @param albumFilter
     *            专辑过滤
     * @param videoFilter
     *            视频过滤
     * @param jf
     *            搜索模块 1.标准 2.tv兼容 3.明星相关4.调试
     * @param sf
     *            搜索限定字段
     * @param stat
     *            搜索结果类别限定 all-所有 album-专辑 video-视频
     * @param productId
     *            会员id
     * @param limitRst
     *            是否限定搜索结果数量
     * @param live_builder_type
     *            万象写死super_search即可，直播数据会拆为多条返回，不再是聚合页
     * @param commonParam
     * @return
     */
    private GenericServingRequest getSearchRequest(String from, String dt, Integer page, Integer pageSize, String word,
            String categoryId, Integer subCategoryId, String ph, String src, Integer mix, String order,
            String searchContent, String splatid, String leIds, String eid, String repo_type, String ispay,
            String albumFilter, String videoFilter, String jf, String sf, String stat, String countryArea,
            String productId, String limitRst, String pushChild, String pcp, String live_builder_type,
            CommonParam commonParam) {

        GenericServingRequest servingRequest = new GenericServingRequest();
        SearchRequest searchRequest = new SearchRequest();
        servingRequest.setSearch_request(searchRequest);
        Map<String, String> params = new HashMap<String, String>();
        searchRequest.setAll_param(params);

        params.put("hl", "0");// 高亮显示，老逻辑
        params.put("pano_stat", "1");// 新标签，写死即可
        if (!StringUtils.isBlank(stat)) {
            params.put("stat", stat);
        }
        if (!StringUtils.isBlank(from)) {
            params.put("from", from);
        }
        if (!StringUtils.isBlank(dt)) {
            params.put("dt", dt);
        }
        if (page != null) {
            params.put("pn", page.toString());
        }
        if (pageSize != null) {
            params.put("ps", pageSize.toString());
        }
        if (!StringUtil.isBlank(word)) {
            params.put("wd", word);
        } else {
            params.put("stype", "1");// 检索时需要加上该参数
        }
        // 2016-02-17添加该逻辑，搜索接口提供给桌面后台使用时categoryId固定传0，此时服务端过滤该值，表示搜索所有
        if (categoryId != null) {
            if (categoryId.contains(",") || StringUtil.toInteger(categoryId, -1) != 0) {
                params.put("cg", categoryId.toString());
            } else {
                params.put("cg", "");
            }
        }

        params.put("request_time", String.valueOf(System.currentTimeMillis()));
        if (StringUtil.isNotBlank(productId)) { // add-on的会员id
            params.put("vipIds", productId);
        }

        if (subCategoryId != null) {
            params.put("sc", subCategoryId.toString());
        }
        if (!StringUtil.isBlank(ph)) {
            params.put("ph", ph);
        }
        if (!StringUtil.isBlank(src)) {
            params.put("src", src);
        }
        if (mix != null) {
            params.put("mix", mix.toString());
        }
        if (!StringUtil.isBlank(commonParam.getMac())) {
            params.put("lc", commonParam.getMac());
            params.put("device_id", commonParam.getMac());
        }
        if (!StringUtil.isBlank(commonParam.getUserId())) {
            params.put("uid", commonParam.getUserId());
        }
        if (!StringUtil.isBlank(commonParam.getInstallVersion())) {
            params.put("version", commonParam.getInstallVersion());
        }
        if (!StringUtil.isBlank(commonParam.getClientIp())) {
            params.put("client_ip", commonParam.getClientIp());
        }

        if (!StringUtil.isBlank(commonParam.getLangcode())) {
            params.put("lang", commonParam.getLangcode().toLowerCase());
        }
        if (!StringUtil.isBlank(commonParam.getWcode())) {
            params.put("city_info", commonParam.getWcode().toUpperCase() + "_0_0_0");
        }
        if (!StringUtil.isBlank(order)) {
            params.put("or", order);
        }
        if (!StringUtil.isBlank(splatid)) {
            params.put("splatid", splatid);
        }
        if (!StringUtil.isBlank(leIds)) {
            params.put("leIds", leIds);
        }
        if (!StringUtil.isBlank(eid)) {
            params.put("eid", eid);
        }
        if (!StringUtil.isBlank(repo_type)) {
            params.put("repo_type", repo_type);
        }
        if (!StringUtil.isBlank(ispay)) {
            params.put("ispay", ispay);
        }
        if (!StringUtil.isBlank(albumFilter)) {
            params.put("album_filter", albumFilter);
        }
        if (!StringUtil.isBlank(videoFilter)) {
            params.put("video_filter", videoFilter);
        }
        if (!StringUtil.isBlank(jf)) {
            params.put("jf", jf);
        }
        if (!StringUtil.isBlank(sf)) {
            params.put("sf", sf);
        }
        if (!StringUtil.isBlank(commonParam.getSalesArea())) {
            params.put("sales_area", commonParam.getSalesArea());
        }
        if (!StringUtil.isBlank(countryArea)) {
            params.put("user_setting_country", countryArea);
        }
        if (!StringUtil.isBlank(limitRst)) {
            params.put("limit_rst", limitRst);
        }
        if (!StringUtil.isBlank(commonParam.getDisplayAppId())) {
            params.put("displayAppId", commonParam.getDisplayAppId());
        }
        if (!StringUtil.isBlank(commonParam.getDisplayPlatformId())) {
            params.put("displayPlatformId", commonParam.getDisplayPlatformId());
        }
        if (StringUtil.isNotBlank(pushChild) && "1".equals(pushChild)) {// 是否推送到儿童
            params.put("pushChild", pushChild);
        }
        if (!StringUtil.isBlank(pcp)) {
            params.put("pcp", pcp);
        }
        if (!StringUtil.isBlank(live_builder_type)) {
            params.put("live_builder_type", live_builder_type);
        }
        params.put("request_time", String.valueOf(System.currentTimeMillis()));

        if (!StringUtils.isBlank(searchContent)) {
            this.putSearchContent(params, searchContent, commonParam);
        }
        return servingRequest;
    }

    private void putSearchContent(Map<String, String> params, String searchContent, CommonParam commonParam) {
        if (StringUtils.isNotBlank(searchContent)) {
            String[] sc = searchContent.split(";");
            for (String sg : sc) {
                String[] s = sg.split(":");
                if (s == null || s.length < 2) {
                    continue;
                }
                int searchType = 0;
                try {
                    searchType = Integer.parseInt(String.valueOf(s[0]));
                } catch (Exception e) {
                    continue;
                }
                String searchValue = s[1];
                if (!StringUtils.isNotBlank(searchValue) || "all".equalsIgnoreCase(searchValue)
                        || "null".equalsIgnoreCase(searchValue)) {
                    continue;
                }

                switch (searchType) {
                case SearchTpConstant.SEARCH_BY_DT: // -2：数据的类型; dt=1,2,3,4
                    // 兼容老版本仍然传dt的情况，如果检索条件中有dt,则覆盖原有dt值
                    params.put("dt", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_CATEGORY: // 分类检索
                    // 兼容老版本仍然传categoryid的情况，如果检索条件中有categoryid,则覆盖原有categoryid值
                    params.put("cg", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_SUBCATEGORY:// 1:子分类检索
                    params.put("sc", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_AREA: // 3:地区检索
                    params.put("area", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_ACTOR: // 5:演员检索
                    params.put("actorName", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_DIRECTOR:// 6:导演检索
                    params.put("directory", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_RELEASEDATE:// 7:年份检索
                    String year = searchValue;
                    if (year != null) {
                        if ("1990-1999".equals(year)) { // 90年代
                            year = "199x";
                        } else if ("2000-2009".equals(year)) { // 00年代
                            year = "200x";
                        } else if ("2,3,4".equals(year)) { // 更早
                            year = "198x,197x,196x";
                        }
                    }
                    params.put("releaseYearDecade", year);
                    break;
                case SearchTpConstant.SEARCH_BY_AGE:// 8:年龄检索
                    params.put("fitAge", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_ALBUMTYPE: // 9:专辑类型检索
                    params.put("vtp", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_ALBUMSTYLE: // 10: 专辑风格
                    params.put("popStyle", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_TV: // 16电视台ID
                    params.put("tvid", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_STREAM_NEW: // 19 码流过滤
                    params.put("playStreamFeatures", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_ORDER:// 20 排序
                    params.put("or", searchValue);
                    params.put("stt", "1"); // 1:倒序 0:升序
                    break;
                case SearchTpConstant.SEARCH_BY_ALBUM_IS_END:// 22是否完结
                    params.put("isEnd", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_LANGUAGE:// 23语言
                    params.put("language", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_STAR_GEND: // 25 明星类别
                    params.put("singerType", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_HOME_MADE:// 27 是否自制剧
                    params.put("isHomemade", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_TAG: // 39 tag搜索
                    // tag检索走关键字搜索
                    params.put("tag", searchValue);
                    break;
                case SearchTpConstant.SEARCH_RECREATION_TYPE: // 40 娱乐类型
                    params.put("recreationType", searchValue);
                    break;
                case SearchTpConstant.SEARCH_SPORT_STYLE: // 41 体育类型
                    params.put("style", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_VIDEO_DURATION: // 42 视频时长
                    params.put("dur", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_COOPERATION: // 43 按照合作平台
                    params.put("coopPlatform", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_AGE2: // 44 按照年龄 乐视儿童
                    // params.put("ag",
                    // SearchTpConstant.getAgeValue(searchValue));
                    params.put("ageSection", SearchTpConstant.getAgeValue(searchValue));
                    break;
                case SearchTpConstant.SEARCH_BY_CONTENT: // 45 按照内容属性 乐视儿童
                    // params.put("secondCg",
                    // SearchTpConstant.getContentValue(searchValue));
                    params.put("childCate", SearchTpConstant.getContentValue(searchValue));
                    break;
                case SearchTpConstant.SEARCH_BY_FUNCTION: // 46 按照功能 乐视儿童
                    // params.put("st",
                    // SearchTpConstant.getFunctionValue(searchValue));
                    params.put("funcProperty", SearchTpConstant.getFunctionValue(searchValue));
                    break;
                case SearchTpConstant.SEARCH_BY_ALONE: // 47 是否独播
                    params.put("playMark", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_LANGUAGE_CHILD: // 48 按照语言 乐视儿童
                    params.put("lg", SearchTpConstant.getLanguageValue(searchValue));
                    break;
                case SearchTpConstant.SEARCH_BY_PLAYSTREAMS:// 49 按照清晰度 检索
                    params.put("cv", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_ISPAY:// 50 是否付费
                    params.put("ispay", searchValue);
                    if (searchValue != null && searchValue.equals("1")) {
                        if (null != commonParam) {
                            params.put("payPlatform", MmsDataUtil.getPayPlatform(commonParam.getP_devType()));
                        } else {
                            params.put("payPlatform", "141007");
                        }
                    }
                    break;
                case SearchTpConstant.SEARCH_BY_BRAND:// 51 电视品牌
                    params.put("coopPlatform", searchValue);
                    break;
                case SearchTpConstant.SEARCH_BY_YEAR:// 52 年份
                    params.put("yr", searchValue);
                    break;
                }
            }
        }

    }

    /**
     * 获取CMS版块数据
     * @param blockId
     *            版块ID
     * @return
     */
    public CMSBlockTpResponse getCMSBlockData(String blockId, CommonParam commonParam) {
        String logPrefix = "getCMSBlockData_" + blockId;
        CMSBlockTpResponse response = null;
        if (blockId == null || "".equals(blockId.trim())) {
            return null;
        }
        try {
            String url = SearchTpConstant.getCMSBlockURL(commonParam.getWcode()) + "/" + blockId + ".json";
            String result = this.restTemplate.getForObject(url, String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, CMSBlockTpResponse.class);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * >>>>>>> cda4f72b3ef60864045cfc5ce159a5dc409578c2
     * 调用第三方，获取排行数据
     * @param url
     *            频道url
     * @return
     */
    public RankingListTpResponse getRankingList(String subUrl, CommonParam commonParam) {
        String logPrefix = "getRankingList_" + subUrl;
        List<RankingList> rankingResult = null;
        List<RankingList> rankingList = null;
        RankingListTpResponse response = null;

        try {
            String url = SearchTpConstant.getRankURL(commonParam.getWcode()) + "/" + subUrl;
            String result = this.restTemplate.getForObject(url, String.class);
            if (StringUtil.isNotBlank(result)) {
                result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
                rankingResult = objectMapper.readValue(result, new TypeReference<List<RankingList>>() {
                });
            }
            if (!CollectionUtils.isEmpty(rankingResult)) {
                rankingList = new LinkedList<RankingList>();
                response = new RankingListTpResponse(rankingList);
                // 从中过滤TV版版权的视频
                for (RankingList rank : rankingResult) {
                    String pushflag = rank.getPushflag();
                    if (pushflag != null) {
                        List<String> flagList = java.util.Arrays.asList(pushflag.split(","));
                        if (flagList.contains(CommonConstants.TV_PLAY_PLAT_FROM)) {
                            rankingList.add(rank);
                        }
                    } else {
                        Map<String, String> playPlatform = rank.getPlayPlatform();
                        if (playPlatform != null && playPlatform.containsKey(CommonConstants.TV_PLAY_PLAT_FROM)) {
                            rankingList.add(rank);
                        }
                    }
                }
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }

        return response;
    }

    /**
     * 获取搜索建议列表
     * @param p
     *            来源
     * @param type
     *            搜索内容的类型，例如按笔画，按拼音
     * @param query
     *            搜索的内容
     * @param method
     *            搜索内容的方法，例如根据名称搜索
     * @param commonParam
     * @return
     */
    public SuggestTpResponse getSuggestList(String p, String type, String query, String method, String from,
                                            String splatid, String dt, String ph, String countryArea, String pushChild, String src, Integer num,
                                            CommonParam commonParam) {
        String logPrefix = "getSuggestList_" + p + "_" + type + "_" + query + "_" + method + "_" + from + "_" + splatid
                + "_" + dt;
        SuggestTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getSuggestURL(commonParam.getWcode()));
            url.append("?jf=1");
            if (!StringUtil.isBlank(p)) {
                url.append("&p=").append(p);
            }
            if (!StringUtil.isBlank(type)) {
                url.append("&t=").append(type);
            }
            if (!StringUtil.isBlank(query)) {
                url.append("&q=").append(query);
            }
            if (!StringUtil.isBlank(method)) {
                url.append("&m=").append(method);
            }
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }
            if (!StringUtil.isBlank(commonParam.getClientIp())) {
                url.append("&client_ip=").append(commonParam.getClientIp());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&city_info=").append(commonParam.getWcode().toUpperCase()).append("_0_0_0");
            }
            if (!StringUtil.isBlank(commonParam.getLangcode())) {
                url.append("&lang=").append(commonParam.getLangcode());
            }
            if (!StringUtil.isBlank(splatid)) {
                url.append("&splatid=").append(splatid);
            }
            if (!StringUtil.isBlank(commonParam.getMac())) {
                url.append("&lc=").append(commonParam.getMac());
            }
            if (!StringUtil.isBlank(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isBlank(dt)) {
                url.append("&dt=").append(dt);
            }
            if (!StringUtil.isBlank(ph)) {
                url.append("&ph=").append(ph);
            }
            if (!StringUtil.isBlank(commonParam.getInstallVersion())) {
                url.append("&version=").append(commonParam.getInstallVersion());
            }
            if (!StringUtil.isBlank(commonParam.getMac())) {
                url.append("&device_id=").append(commonParam.getMac());
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
            }
            if (!StringUtil.isBlank(countryArea)) {
                url.append("&user_setting_country=").append(countryArea);
            }
            if (!StringUtil.isBlank(commonParam.getDisplayAppId())) {
                url.append("&displayAppId=").append(commonParam.getDisplayAppId());
            }
            if (!StringUtil.isBlank(commonParam.getDisplayPlatformId())) {
                url.append("&displayPlatformId=").append(commonParam.getDisplayPlatformId());
            }
            if (!StringUtil.isBlank(pushChild) && "1".equals(pushChild)) {
                url.append("&pushChild=").append(pushChild);
            }
            if (!StringUtil.isBlank(from) && "tv_lechild12".equals(from)) {
                url.append("&cg=1021&sc=542015");
            }
            if (!StringUtil.isBlank(src)) {
                url.append("&src=").append(src);
            }
            if (num != null) {
                url.append("&term_num=").append(num);
            }

            url.append("&request_time=").append(System.currentTimeMillis());
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, SuggestTpResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix, e);
        }

        return response;
    }

    /**
     * 获取乐搜首页专辑推荐列表
     * @param platform
     *            平台tv为tv
     * @param ph
     *            版权
     * @param commonParam
     * @return
     */
    public MainPagePushAlbumsTpResponse getMainPagePushAlbums(String platform, String ph, String from, String splatid,
                                                              String displayAppId, String displayPlatformId, CommonParam commonParam) {
        String logPrefix = "getMainPagePushAlbums_" + platform + "_" + ph + "_" + from + "_" + splatid + "_"
                + displayAppId + "_" + displayPlatformId;
        MainPagePushAlbumsTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getMainPagePushAlbumsURL(commonParam.getWcode()));
            if (!StringUtil.isBlank(ph)) {
                url.append("?ph=").append(ph);
            }
            if (!StringUtil.isBlank(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isBlank(commonParam.getMac())) {
                url.append("&dev_id=").append(commonParam.getMac());
                url.append("&device_id=").append(commonParam.getMac());
            }
            if (!StringUtil.isBlank(commonParam.getTerminalApplication())) {
                url.append("&dev_model=").append(commonParam.getTerminalApplication());
            }
            if (!StringUtil.isBlank(commonParam.getLangcode())) {
                url.append("&language=").append(commonParam.getLangcode());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&region=").append(commonParam.getWcode());
            }
            if (!StringUtil.isBlank(platform)) {
                url.append("&platform=").append(platform);
            }
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }
            if (!StringUtil.isBlank(commonParam.getClientIp())) {
                url.append("&client_ip=").append(commonParam.getClientIp());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&city_info=").append(commonParam.getWcode().toUpperCase()).append("_0_0_0");
            }
            if (!StringUtil.isBlank(splatid)) {
                url.append("&splatid=").append(splatid);
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
                url.append("&src=1");
            }
            if (!StringUtil.isBlank(displayAppId)) {
                url.append("&displayAppId=").append(displayAppId);
            }
            if (!StringUtil.isBlank(displayPlatformId)) {
                url.append("&displayPlatformId=").append(displayPlatformId);
            }
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, MainPagePushAlbumsTpResponse.class);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }

        return response;
    }

    /**
     * 获取乐搜新首页频道列表
     * @param platform
     *            平台tv为tv
     * @param ph
     *            版权
     * @param commonParam
     * @return
     */
    public MainPageChannelsTpResponse getMainPageChannels(String platform, String ph, String from, String splatid,
                                                          String displayAppId, String displayPlatformId, CommonParam commonParam) {
        String logPrefix = "getMainPageChannels_" + platform + "_" + ph + "_" + from + "_" + splatid + "_"
                + displayAppId + "_" + displayPlatformId;
        MainPageChannelsTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getMainPageChannelsURL(commonParam.getWcode()));
            if (!StringUtil.isBlank(ph)) {
                url.append("?ph=").append(ph);
            }
            if (!StringUtil.isBlank(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isBlank(commonParam.getMac())) {
                url.append("&dev_id=").append(commonParam.getMac());
            }
            if (!StringUtil.isBlank(commonParam.getTerminalApplication())) {
                url.append("&dev_model=").append(commonParam.getTerminalApplication());
            }
            if (!StringUtil.isBlank(commonParam.getLangcode())) {
                url.append("&language=").append(commonParam.getLangcode());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&region=").append(commonParam.getWcode());
            }
            if (!StringUtil.isBlank(platform)) {
                url.append("&platform=").append(platform);
            }
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }
            if (!StringUtil.isBlank(commonParam.getClientIp())) {
                url.append("&client_ip=").append(commonParam.getClientIp());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&city_info=").append(commonParam.getWcode().toUpperCase()).append("_0_0_0");
            }
            if (!StringUtil.isBlank(splatid)) {
                url.append("&splatid=").append(splatid);
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
                url.append("&src=1");
            }
            if (!StringUtil.isBlank(displayAppId)) {
                url.append("&displayAppId=").append(displayAppId);
            }
            if (!StringUtil.isBlank(displayPlatformId)) {
                url.append("&displayPlatformId=").append(displayPlatformId);
            }
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, MainPageChannelsTpResponse.class);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }

        return response;
    }

    /**
     * 获取乐搜新首页频道下话题列表
     * @param platform
     *            平台
     * @param ph
     *            版权
     * @param channelId
     *            频道ID
     * @param commonParam
     * @return
     */
    public MainPageTopicsTpResponse getMainPageTopics(String platform, String ph, String channelId, String from,
            String splatid, String displayAppId, String displayPlatformId, CommonParam commonParam) {
        String logPrefix = "getMainPageTopics_" + platform + "_" + ph + "_" + channelId + "_" + from + "_" + splatid
                + "_" + displayAppId + "_" + displayPlatformId;
        MainPageTopicsTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getMainPageTopicsURL(commonParam.getWcode()));
            if (!StringUtil.isBlank(ph)) {
                url.append("?ph=").append(ph);
            }
            if (!StringUtil.isBlank(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isBlank(commonParam.getMac())) {
                url.append("&dev_id=").append(commonParam.getMac());
            }
            if (!StringUtil.isBlank(commonParam.getTerminalApplication())) {
                url.append("&dev_model=").append(commonParam.getTerminalApplication());
            }
            if (!StringUtil.isBlank(commonParam.getLangcode())) {
                url.append("&language=").append(commonParam.getLangcode());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&region=").append(commonParam.getWcode());
            }
            if (!StringUtil.isBlank(platform)) {
                url.append("&platform=").append(platform);
            }
            if (!StringUtil.isBlank(channelId)) {
                url.append("&chid=").append(channelId);
            }
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }
            if (!StringUtil.isBlank(commonParam.getClientIp())) {
                url.append("&client_ip=").append(commonParam.getClientIp());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&city_info=").append(commonParam.getWcode().toUpperCase()).append("_0_0_0");
            }
            if (!StringUtil.isBlank(splatid)) {
                url.append("&splatid=").append(splatid);
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
                url.append("&src=1");
            }
            if (!StringUtil.isBlank(displayAppId)) {
                url.append("&displayAppId=").append(displayAppId);
            }
            if (!StringUtil.isBlank(displayPlatformId)) {
                url.append("&displayPlatformId=").append(displayPlatformId);
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, MainPageTopicsTpResponse.class);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }

        return response;
    }

    /**
     * 获取乐搜新首页频道topic下的视频数据
     * @param platform
     *            平台
     * @param ph
     *            版权
     * @param channelId
     *            频道ID
     * @param subtopic
     *            话题ID
     * @param page
     *            页码
     * @param pageSize
     *            每页数量
     * @param history
     *            历史
     * @param commonParam
     * @return
     */
    public MainPageTopicDataTpResponse getMainPageTopicData(String platform, String ph, String channelId,
            String subtopic, Integer page, Integer pageSize, String history, String from, String splatid,
            String displayAppId, String displayPlatformId, CommonParam commonParam) {
        String logPrefix = "getMainPageTopics_" + platform + "_" + ph + "_" + channelId + "_" + subtopic + "_" + page
                + "_" + pageSize + "_" + history + "_" + from + "_" + splatid + "_" + displayAppId + "_"
                + displayPlatformId;
        MainPageTopicDataTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getMainPageTopicDataURL(commonParam.getWcode()));
            if (!StringUtil.isBlank(ph)) {
                url.append("?ph=").append(ph);
            }
            if (!StringUtil.isBlank(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isBlank(commonParam.getMac())) {
                url.append("&dev_id=").append(commonParam.getMac());
            }
            if (!StringUtil.isBlank(commonParam.getTerminalApplication())) {
                url.append("&dev_model=").append(commonParam.getTerminalApplication());
            }
            if (!StringUtil.isBlank(commonParam.getLangcode())) {
                url.append("&language=").append(commonParam.getLangcode());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&region=").append(commonParam.getWcode());
            }
            if (!StringUtil.isBlank(platform)) {
                url.append("&platform=").append(platform);
            }
            if (!StringUtil.isBlank(channelId)) {
                url.append("&chid=").append(channelId);
            }
            if (!StringUtil.isBlank(subtopic)) {
                url.append("&subtopic=").append(subtopic);
            }
            if (page != null && pageSize != null) {
                url.append("&pn=").append(page).append("&ps=").append(pageSize);
            }
            if (!StringUtil.isBlank(history)) {
                url.append("&history=").append(history);
            }
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }
            if (!StringUtil.isBlank(commonParam.getClientIp())) {
                url.append("&client_ip=").append(commonParam.getClientIp());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&city_info=").append(commonParam.getWcode().toUpperCase()).append("_0_0_0");
            }
            if (!StringUtil.isBlank(splatid)) {
                url.append("&splatid=").append(splatid);
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
                url.append("&src=1");
            }
            if (!StringUtil.isBlank(displayAppId)) {
                url.append("&displayAppId=").append(displayAppId);
            }
            if (!StringUtil.isBlank(displayPlatformId)) {
                url.append("&displayPlatformId=").append(displayPlatformId);
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, MainPageTopicDataTpResponse.class);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }

        return response;
    }

    /**
     * 根据IP获取地域信息
     * @param ip
     * @param wcode
     * @return
     */
    public IpLocationResponse getIpLocation(String ip, String splatid, CommonParam commonParam) {
        String logPrefix = "getIpLocation_" + ip + "_" + splatid;
        IpLocationResponse response = null;
        String url = null;
        try {
            if (ip != null) {
                url = SearchTpConstant.getIPLocationURL(commonParam.getWcode()) + "?ip=" + ip + "&city_info="
                        + commonParam.getWcode() + "_0_0_0";
                if (!StringUtil.isBlank(splatid)) {
                    url = url + "&splatid=" + splatid;
                }
                String result = this.restTemplate.getForObject(url, String.class);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, IpLocationResponse.class);
                }
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }

        return response;
    }

    /**
     * 内网搜索专辑详情页
     * @param aid
     *            专辑id
     * @param type
     *            类型 SearchTpConstant.LESO_SRC_VRS SearchTpConstant.LESO_SRC_WEB
     * @param from
     *            请求来源
     * @param ph
     *            版权
     * @param page
     *            页码
     * @param pageSize
     *            每页数量
     * @param commonParam
     * @return
     */
    public AlbumDetailTpResponse getAlbumDetail(Long aid, String type, String from, String ph, Integer page,
            Integer pageSize, CommonParam commonParam) {
        String logPrefix = "getAlbumDetail_" + aid + "_" + type + "_" + from + "_" + ph + "_" + page + "_" + pageSize;
        AlbumDetailTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getAlbumDetailURL(commonParam.getLangcode()));
            /**
             * 搜索详情页默认不出专辑下视频列表
             * vlf=0代表获取专辑下视频列表，从第0个开始
             * vls=65535,表示专辑下视频列表获取65535个
             */
            url.append("?vlf=0&vls=65535");
            if (!StringUtil.isBlank(ph)) {
                url.append("&ph=").append(ph);
            }
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }

            // 如果是香港地区且不是香港水货
            if (SearchTpConstant.SEARCH_WCODE_HK.equalsIgnoreCase(commonParam.getWcode())
                    && !"hk".equalsIgnoreCase(commonParam.getT_wcode())) {
                url.append("&sk=hk_album_").append(aid);
            } else {
                // 内网数据
                if (SearchTpConstant.LESO_SRC_VRS.equals(type)) {
                    url.append("&sk=newmms_vrs_a_").append(aid);
                } else {
                    url.append("&sk=newmms_leso_a_").append(aid);
                }
                // 大陆详情页的聚集列表可以进行分页
                if (page != null && pageSize != null) {
                    url.append("&pn=").append(page).append("&ps=").append(pageSize);
                }
                url.append("&uniqueId=").append(System.currentTimeMillis());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&city_info=").append(commonParam.getWcode().toUpperCase()).append("_0_0_0");
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, AlbumDetailTpResponse.class);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }

        return response;
    }

    /**
     * 结果页中，从外网搜索专辑详情页
     * @param aid
     *            专辑id
     * @param site
     *            站源信息
     * @param from
     *            请求来源
     * @param commonParam
     * @return
     */
    public AlbumDetailWebsiteTpResponse getAlbumDetailWebSite(Long aid, String site, String from,
            CommonParam commonParam) {
        String logPrefix = "getAlbumDetailWebSite_" + aid + "_" + site + "_" + from;
        AlbumDetailWebsiteTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getWebsiteURL(commonParam.getWcode()));
            url.append("?uniqueId=").append(System.currentTimeMillis());
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }
            if (WebsiteTpResponse.WEB_SITE.LETV.getPinYin().equals(site)) {
                url.append("&sk=newvrs_a_").append(aid).append("_").append(site);
            } else {
                url.append("&sk=newleso_a_").append(aid).append("_").append(site);
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
            }
            String result = this.restTemplate.getForObject(url.toString(), String.class);

            if (StringUtil.isNotBlank(result)) {
                List<AlbumDetailWebsiteTpResponse.AlbumDetailWebSiteDto> list = objectMapper.readValue(result,
                        new TypeReference<List<AlbumDetailWebsiteTpResponse.AlbumDetailWebSiteDto>>() {
                        });
                response = new AlbumDetailWebsiteTpResponse(list);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * 搜索专辑所有站源信息
     * @param aid
     *            专辑id
     * @param type
     *            类型 SearchTpConstant.LESO_SRC_VRS SearchTpConstant.LESO_SRC_WEB
     * @param ph
     *            版权
     * @param from
     *            请求来源
     * @param commonParam
     * @return
     */
    public WebsiteTpResponse getWebSite(Long aid, String type, String ph, String from, CommonParam commonParam) {
        String logPrefix = "getWebSite_" + aid + "_" + type + "_" + ph + "_" + from;
        WebsiteTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getWebsiteURL(commonParam.getWcode()));
            url.append("?uniqueId=").append(System.currentTimeMillis());
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }
            if (!StringUtil.isBlank(ph)) {
                url.append("&ph=").append(ph);
            }
            if (SearchTpConstant.LESO_SRC_WEB.equals(type)) {
                url.append("&sk=newleso_a_").append(aid);
            } else {
                url.append("&sk=newvrs_a_").append(aid);
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
            }
            String result = this.restTemplate.getForObject(url.toString(), String.class);

            if (StringUtil.isNotBlank(result)) {
                List<WebsiteTpResponse.WebsiteDto> list = objectMapper.readValue(result, new TypeReference<List<WebsiteTpResponse.WebsiteDto>>() {
                });
                response = new WebsiteTpResponse(list);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }

        return response;
    }

    /**
     * 获取明星详情，包含明星简介等数据
     * @param id
     *            明星id
     * @param commonParam
     * @return
     */
    public StarDetailTpResponse getStarDetail(String id, CommonParam commonParam) {
        String logPrefix = "getStarDetail_" + id;
        StarDetailTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getStarDetailURL(commonParam.getWcode()));
            if (!StringUtil.isBlank(id)) {
                url.append("?id=").append(id);
            }
            if (!StringUtil.isBlank(commonParam.getLangcode())) {
                url.append("&lang=").append(commonParam.getLangcode());
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, StarDetailTpResponse.class);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * 获取明星作品类别
     * @param word
     *            关键字
     * @param session
     * @param ph
     *            版权
     * @param page
     *            页码
     * @param pageSize
     *            每页数据量
     * @param from
     *            请求来源
     * @param commonParam
     * @return
     */
    public SearchResultTpResponse getStarCategory(String word, String session, String ph, Integer page,
            Integer pageSize, String from, String splatid, String displayAppId, String displayPlatformId,
            CommonParam commonParam) {
        String logPrefix = "getStarCategory_" + word + "_" + session + "_" + ph + "_" + page + "_" + pageSize + "_"
                + from + "_" + splatid + "_" + displayAppId + "_" + displayPlatformId;
        SearchResultTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getSearchURL(commonParam.getWcode()));
            /**
             * dt=1,2取专辑+视频数据
             * album_filter=category:1|2|5|11专辑取电影，电视剧,动漫,综艺数据
             * video_filter=category:9|11 视频取音乐，综艺频道数据
             * jf=3 搜素结果按照明星详情模式给出
             * or=3 按照热度排序
             * sf=starringName,directoryName,actorName指定搜索于为媒资这些字段
             * 搜索wd、sa、q三个参数都代表关键字,可通用
             */
            url.append("?dt=1,2&album_filter=category:1|2|5|11&video_filter=category:9|11&jf=3&or=3&sf=starringName,directoryName,actorName&stat=all");
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }
            if (!StringUtil.isBlank(word)) {
                url.append("&sa=").append(word);
            }
            if (!StringUtil.isBlank(session)) {
                url.append("&session=").append(session);
            }
            if (page != null && pageSize != null) {
                url.append("&pn=").append(page).append("&ps=").append(pageSize);
            }
            if (!StringUtil.isBlank(ph)) {
                url.append("&ph=").append(ph);
            }
            if (!StringUtil.isBlank(commonParam.getLangcode())) {
                url.append("&lang=").append(commonParam.getLangcode());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&region=").append(commonParam.getWcode());
                url.append("&city_info=").append(commonParam.getWcode().toUpperCase()).append("_0_0_0");
            }
            if (!StringUtil.isBlank(commonParam.getMac())) {
                url.append("&lc=").append(commonParam.getMac());
            }
            if (!StringUtil.isBlank(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isBlank(commonParam.getInstallVersion())) {
                url.append("&version=").append(commonParam.getInstallVersion());
            }
            if (!StringUtil.isBlank(commonParam.getClientIp())) {
                url.append("&client_ip=").append(commonParam.getClientIp());
            }
            if (!StringUtil.isBlank(splatid)) {
                url.append("&splatid=").append(splatid);
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
                url.append("&src=1");
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (!StringUtil.isBlank(displayAppId)) {
                url.append("&displayAppId=").append(displayAppId);
            }
            if (!StringUtil.isBlank(displayPlatformId)) {
                url.append("&displayPlatformId=").append(displayPlatformId);
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, SearchResultTpResponse.class);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * 获取 猜你喜欢
     * @param pt
     *            写死0004
     * @param lc
     *            mac
     * @param area
     *            rec_0001表示相关推荐，rec_0002表示首页及频道页个性化，rec_0003表示轮播个性化推荐
     * @param num
     *            请求数量
     * @param cid
     *            0、首页你可能喜欢 -1、乐视推荐台专辑列表
     * @param history
     *            历史
     * @param pid
     *            暂不传
     * @param commonParam
     * @return
     */
    public SearchMaybeLikeTpResponse getSearchMaybeLike(String pt, String lc, String area, Integer num, String cid,
            String history, Long pid, CommonParam commonParam) {
        String logPrefix = "getSearchMaybeLike_" + pt + "_" + lc + "_" + area + "_" + num + "_" + cid + "_" + history
                + "_" + pid;
        SearchMaybeLikeTpResponse hotSearchRecommendResponse = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getRecommendationURL(commonParam.getWcode()));
            if (!StringUtil.isBlank(commonParam.getUserId())) {
                url.append("?uid=").append(commonParam.getUserId());
            } else {
                url.append("?uid=0");
            }
            if (!StringUtil.isBlank(lc)) {
                url.append("&lc=").append(lc);
            } else {
                url.append("&lc=");
            }
            if (!StringUtil.isBlank(area)) {
                url.append("&area=").append(area);
            }
            if (num != null) {
                url.append("&num=").append(num);
            }
            if (cid == null) {
                url.append("&cid=0");
            } else {
                url.append("&cid=").append(cid);
            }
            if (pid != null) {
                url.append("&pid=").append(pid);
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
                url.append("&bc=").append(commonParam.getBroadcastId());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&region=").append(commonParam.getWcode());
            }
            if (!StringUtil.isBlank(commonParam.getLangcode())) {
                url.append("&lang=").append(commonParam.getLangcode());
            }
            if (!StringUtil.isBlank(history)) {
                url.append("&history=").append(history);
            } else {
                url.append("&history=");
            }
            if (!StringUtil.isBlank(pt)) {
                url.append("&pt=").append(pt);
            }
            url.append("&versiontype=IntelligentOperation").append("&action=rec").append("&disable_record_exposure=1");
            String res = this.restTemplate.getForObject(url.toString(), String.class);

            if (res != null) {
                hotSearchRecommendResponse = objectMapper.readValue(res, SearchMaybeLikeTpResponse.class);
            }
        } catch (Exception e) {
            hotSearchRecommendResponse = null;
            log.error(logPrefix, e);
        }

        return hotSearchRecommendResponse;
    }

    /**
     * 获取指定服务的id获取数据
     * @param cardId
     *            数据服务id，多个id以-分隔，例如302-102-103-201-304-305，可以不填，不填则为默认订阅服务102-
     *            201-306-103-301-307
     * @param history
     *            用户观看历史，传递用户最近观看的10个以内视频，按历史时刻顺序，vid1是最新观看的视频，格式如下：
     *            "vid1-vid2-vid3-vid4-vid5-vid6-vid7-vid8-vid9-vid10"
     *            ，此字段只在猜我喜欢有用
     * @param num
     *            结果数量，可以不填，默认是12
     * @param src
     *            搜索范围 1为内网
     * @param ph
     *            版权
     * @param platform
     *            播放平台 其中tv、pcw、和m分别表示tv端、pc网页端、和移动端
     * @param commonParam
     * @return
     */

    public SearchCardDataResponse getCardData(String cardId, String history, Integer num, String src, String ph,
            String platform, String splatid, String from, String displayAppId, String displayPlatformId,
            CommonParam commonParam) {
        String logPrefix = "getCardData_" + "_" + cardId + "_" + history + "_" + num + "_" + src + "_" + ph + "_"
                + platform + "_" + splatid + "_" + from + "_" + displayAppId + "_" + displayPlatformId;
        SearchCardDataResponse response = null;
        try {
            StringBuilder url = new StringBuilder(SearchTpConstant.getCardDataURL(commonParam.getWcode()));
            if (!StringUtil.isBlank(ph)) {
                url.append("?ph=").append(ph);
            }
            if (!StringUtil.isBlank(platform)) {
                url.append("&platform=").append(platform);
            }
            if (!StringUtil.isBlank(cardId)) {
                url.append("&card_id=").append(cardId);
            } else {
                url.append("&card_id=");
            }
            if (num != null) {
                url.append("&num=").append(num);
            }
            if (!StringUtil.isBlank(history)) {
                url.append("&history=").append(history);
            }
            if (!StringUtil.isBlank(commonParam.getLangcode())) {
                url.append("&lang=").append(commonParam.getLangcode());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&region=").append(commonParam.getWcode());
            }
            if (!StringUtil.isBlank(commonParam.getWcode())) {
                url.append("&city_info=").append(commonParam.getWcode().toUpperCase()).append("_0_0_0");
            }
            if (!StringUtil.isBlank(commonParam.getUserId())) {
                url.append("&user_id=").append(commonParam.getUserId());
            }
            if (!StringUtil.isBlank(splatid)) {
                url.append("&splatid=").append(splatid);
            }
            if (!StringUtil.isBlank(from)) {
                url.append("&from=").append(from);
            }
            if (!StringUtil.isBlank(src)) {
                url.append("&src=").append(src);
            }
            if (!StringUtil.isBlank(commonParam.getSalesArea())) {
                url.append("&sales_area=").append(commonParam.getSalesArea());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                url.append("&pcp=").append(commonParam.getBroadcastId());
            }
            if (!StringUtil.isBlank(commonParam.getClientIp())) {
                url.append("&client_ip=").append(commonParam.getClientIp());
            }
            if (!StringUtil.isBlank(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isBlank(commonParam.getInstallVersion())) {
                url.append("&version=").append(commonParam.getInstallVersion());
            }
            if (!StringUtil.isBlank(commonParam.getMac())) {
                url.append("&device_id=").append(commonParam.getMac());
            }
            if (!StringUtil.isBlank(displayAppId)) {
                url.append("&displayAppId=").append(displayAppId);
            }
            if (!StringUtil.isBlank(displayPlatformId)) {
                url.append("&displayPlatformId=").append(displayPlatformId);
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, SearchCardDataResponse.class);
            }

        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * 获取流信息
     * @param url
     *            请求播放的url
     * @param os
     *            请求的操作系统
     * @param format
     *            清晰度详情 Format
     * @param source
     *            请求来源 TV 乐见等
     * @return
     */
    public GetStreamTpResponse getStream(String url, String os, String format, Integer source, CommonParam commonParm) {
        String logPrefix = "getStream_" + url + "_" + os + "_" + format + "_" + source;
        GetStreamTpResponse response = null;
        try {
            ExtractRequest extractRequest = this.getExtractRequest(url, os, format, source);
            List<ExtractData> list = this.playStreamServing.GetStream(extractRequest, false);// 此处注意要用false，true为同步解析，只用于人工调试，不可大批量调用
            if (list != null) {
                response = new GetStreamTpResponse(list);
            }
        } catch (Exception e) {
            response = null;
            log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * 更新流地址
     * @param url
     *            请求播放的url
     * @param os
     *            请求的操作系统
     * @param format
     *            清晰度详情 Format
     * @param source
     *            请求来源 TV 乐见等
     * @param failState
     *            流失败的原因 详情见FailState
     */
    public Boolean updateStream(String url, String os, String format, Integer source, Integer failState,
            CommonParam commonParam) {
        String logPrefix = "updateStream_" + url + "_" + os + "_" + format + "_" + source;
        Boolean result = false;
        try {
            ExtractRequest extractRequest = this.getExtractRequest(url, os, format, source);
            result = this.playStreamServing.Update(extractRequest, FailState.findByValue(failState));
        } catch (Exception e) {
            result = false;
            log.error(logPrefix, e);
        }
        return result;
    }

    private ExtractRequest getExtractRequest(String url, String os, String format, Integer source) {
        ExtractRequest request = new ExtractRequest();
        request.setUrl(url);

        Set<OSType> os_types = new HashSet<OSType>();
        request.setOs_types(os_types);
        String[] oss = os.split(";");
        for (String temp : oss) {
            os_types.add(OSType.findByValue(Integer.parseInt(temp)));
        }

        Set<Format> formats = new HashSet<Format>();
        request.setFormats(formats);
        String[] formatsTemp = format.split(";");
        for (String temp : formatsTemp) {
            formats.add(Format.findByValue(Integer.parseInt(temp)));
        }

        request.setRequest_source(RequestSource.findByValue(source));
        return request;
    }

    /**
     * 上传客户端解析的流
     * @param url
     *            请求播放的Url
     * @param streamList
     *            解析的流地址
     * @param os
     *            请求的操作系统
     * @param format
     *            请求的流清晰度
     */
    public Boolean uploadLocalStream(String url, List<String> streamList, Integer os, Integer format,
            CommonParam commonParam) {
        String logPrefix = "UploadLocalStream_" + url + "_" + streamList + "_" + os + "_" + format;
        Boolean result = false;
        try {
            ExtractData extractData = new ExtractData();
            extractData.setStream_list(streamList);
            extractData.setOs_type(OSType.findByValue(os));
            extractData.setRequest_format(Format.findByValue(format));
            result = true;
        } catch (Exception e) {
            result = false;
            log.error(logPrefix, e);
        }
        return result;
    }

    /**
     * 印度版获取专辑详情
     * @param albumIds
     * @return
     */
    public GenericDetailResponse getWebsiteDetails(String albumId, Short page, Short pageSize) {
        String logPrefix = "SearchTpDao_getSarrAlbumDetail_";
        GenericDetailResponse response = null;
        GenericDetailRequest detailRequest = new GenericDetailRequest();
        // TODO 印度版临时方案，专辑详情剧集分页，后面会单独提供接口，与专辑的基本信息分开
        detailRequest.setVideo_list_num(pageSize);
        detailRequest.setVideo_list_start_idx(page);
        List<String> ids = new ArrayList<String>();
        ids.add(albumId);
        detailRequest.setIds(ids);
        try {
            response = this.detailServing.GetDetailInfo(detailRequest);
        } catch (Exception e) {
            log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * 获取作品库专辑或视频详情
     * @param idList
     *            globalIdList
     * @param page
     * @param pageSize
     * @return
     */
    public GenericDetailResponse getDetails(List<String> idList, Short page, Short pageSize) {
        GenericDetailResponse response = null;
        GenericDetailRequest detailRequest = new GenericDetailRequest();
        // TODO 印度版临时方案，专辑详情剧集分页，后面会单独提供接口，与专辑的基本信息分开
        detailRequest.setVideo_list_num(pageSize);
        detailRequest.setVideo_list_start_idx(page);
        detailRequest.setIds(idList);
        try {
            response = this.detailServing.GetDetailInfo(detailRequest);
        } catch (Exception e) {
            log.error("getSarrAlbumDetail_" + detailRequest.toString(), e);
        }
        return response;
    }

    // private String getWcode(CommonParam commonParam) {
    // if (commonParam.getT_wcode() != null && commonParam.getT_wcode() != "") {
    // return commonParam.getT_wcode();
    // }
    // return commonParam.getWcode();
    // }
}
