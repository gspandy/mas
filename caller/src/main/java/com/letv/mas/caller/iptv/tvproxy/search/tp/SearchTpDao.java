package com.letv.mas.caller.iptv.tvproxy.search.tp;

import com.alibaba.fastjson.JSONArray;
import com.letv.mas.caller.iptv.tvproxy.common.dao.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.search.SearchTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import search2.proto.SearchRequest;
import serving.GenericServing;
import serving.GenericServingRequest;
import serving.GenericServingResponse;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class SearchTpDao extends BaseTpDao {
    private final static Logger log = LoggerFactory.getLogger(SearchTpDao.class);

    @Resource
    private GenericServing.Iface searchServing;


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
                                //params.put("payPlatform", MmsDataUtil.getPayPlatform(commonParam.getP_devType()));
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
}
