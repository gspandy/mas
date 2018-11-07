package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.RecommendationTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendaionPidListResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * avoid to the Tv shows nothing, because of the service of recommendation
 * shutdown, so make cache
 */
@Component
public class RecommendationCacheDao extends BaseCacheDao {

    private final static Logger log = LoggerFactory.getLogger(RecommendationCacheDao.class);

    /**
     * 多板块推荐缓存业务--set缓存
     * @param pageid
     * @param area
     * @param cid
     * @param broadcastId
     * @param typeReference
     */
    public void setMultiBlockCache(String pageid, String area, String cid, Object obj, String logPrefix,
            CommonParam commParam) throws Exception {
        String cache_key = this.getKey(pageid, area, cid, commParam);
        String markKey = cache_key + "_mark";
        String mark = this.cacheTemplate.get(markKey, String.class);
        if (!CacheContentConstants.MARK_VALID_VALUE.equals(mark)) {// 根据标志位判断是否需要更新缓存，减少读取数据的量
            // 如果没有缓存或者已失效，需要更新数据
            this.cacheTemplate.set(markKey, CacheContentConstants.MARK_VALID_VALUE, CommonConstants.SECONDS_OF_1_DAY);
            if (obj != null) {
                this.cacheTemplate.set(cache_key, obj);
                // 为了查看缓存中的数据是否正确故打印日志（量不大，只有更新缓存的时候才会打印【多板块一天更新一次】）
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(NON_NULL);
                try {
                    String result = mapper.writeValueAsString(obj);
                    log.info(
                            logPrefix + "_setMultiBlockCache_" + "[set_time:"
                                    + TimeUtil.getDateString(new Date(), TimeUtil.SIMPLE_DATE_HOUR_MINUTE_FORMAT)
                                    + "_key:" + cache_key + "]", result);
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * the service of multiBlockCache--get cache
     * @param pageid
     * @param area
     * @param cid
     * @param broadcastId
     * @param typeReference
     * @return
     */
    public Map<String, RecommendationTpResponse> getMultiBlockCache(String pageid, String area, String cid,
                                                                    String logPrefix, CommonParam commParam) {
        String cache_key = this.getKey(pageid, area, cid, commParam);
        log.info(logPrefix + "getMultiBlockCache_key[" + cache_key + "]");
        return this.cacheTemplate.get(cache_key, new LetvTypeReference<Map<String, RecommendationTpResponse>>() {
        });
    }

    /**
     * 单板块推荐缓存业务-set缓存
     * @param categoryId
     * @param obj
     */
    // TODO liupeng 单模块和多模块推荐是不是可以统一？
    // tip:合的话也是通过if else判断(1、两种推荐的key值不一样，2、缓存的更新时间点不一样)，感觉合并的意义不是特别大。
    public void setSingleCache(Integer categoryId, Object obj, String logPrefix, CommonParam commParam) {
        String cache_key = CacheContentConstants.RECOMMENDATION_PREFIX + categoryId;
        String markKey = cache_key + "_mark";
        String mark = this.cacheTemplate.get(markKey, String.class);
        if (!CacheContentConstants.MARK_VALID_VALUE.equals(mark)) {
            this.cacheTemplate.set(markKey, CacheContentConstants.MARK_VALID_VALUE,
                    CommonConstants.SECONDS_OF_10_MINUTE);
            this.cacheTemplate.set(cache_key, obj);
            log.info(logPrefix + "setSingleCache_key[" + cache_key + "]");
        }
    }

    /**
     * 单板块推荐缓存业务-get缓存
     * @param categoryId
     * @param cls
     * @return
     */
    public <T> T getSingleCache(Integer categoryId, Class<T> cls, String logPrefix, CommonParam commParam) {
        String cache_key = CacheContentConstants.RECOMMENDATION_PREFIX + categoryId;
        log.info(logPrefix + "getSingleCache[" + cache_key + "]");
        return this.cacheTemplate.get(cache_key, cls);
    }

    /**
     * 推荐容错缓存
     * @param url
     * @param obj
     */
    public void updateRecCache(String url, Object obj, String logPrefix, CommonParam commParam) {
        String key = this.parseCacheKey(url, commParam);
        this.cacheTemplate.set(key, obj);
        log.info(logPrefix + "updateRecCache_key[" + key + "]");
    }

    /**
     * 推荐容错缓存(新)
     * @param object
     * @param pageid
     * @param area
     * @param cid
     * @param type
     *            1、多板块推荐缓存 2、单板块推荐缓存
     * @param commonParam
     */
    public void updateNewRecCache(Object object, String pageid, String area, String cid, Integer type,
            String logPrefix, CommonParam commonParam) {
        String key = null;
        switch (type) {
        case 0:
            key = this.getKey(pageid, area, cid, commonParam);
            this.cacheTemplate.set(key, object);
            break;
        case 1:
            key = CacheContentConstants.RECOMMENDATION_PREFIX + cid;
            this.cacheTemplate.set(key, object);
            break;
        }
        log.info(logPrefix + "updateNewRecCache_key[" + key + "]");
    }

    private String getKey(String pageid, String area, String cid, CommonParam commParam) {
        StringBuilder sBuilder = new StringBuilder(CacheContentConstants.RECO_RECOVERY_PREFIX);
        sBuilder.append((pageid == null || "null".equalsIgnoreCase(pageid)) ? "" : pageid)
                .append("_")
                .append(area == null || "null".equalsIgnoreCase(area) ? "" : area)
                .append("_")
                .append(cid == null || "null".equalsIgnoreCase(cid) ? "" : cid)
                .append("_")
                .append(commParam.getBroadcastId() == null || "null".equalsIgnoreCase(commParam.getBroadcastId() + "") ? ""
                        : commParam.getBroadcastId());
        return sBuilder.toString();
    }

    /**
     * ---------获取推荐容错缓存的key
     * key由公共前缀+pageid+area+cid+broadcastId组成
     * @param url
     * @return
     */
    private String parseCacheKey(String url, CommonParam commParam) {
        Map<String, String> paramMap = new HashMap<String, String>();
        if (url != null) {
            String[] params = url.split("&");
            if (params != null) {
                for (String param : params) {
                    String[] values = param.split("=");
                    if (values != null && values.length == 2) {
                        paramMap.put(values[0], values[1]);
                    }
                }
            }

            String pageid = paramMap.get("pageid");
            String area = paramMap.get("area");
            String broadcastId = paramMap.get("bc");
            String cid = paramMap.get("cid");
            commParam.setBroadcastId(StringUtil.isBlank(broadcastId) ? null : Integer.valueOf(broadcastId));
            return this.getKey(pageid, area, cid, commParam);

        }

        return null;
    }

    /**
     * @param rcType
     * @param commonParam
     * @return
     */
    public Object getLeviewCache(String rcType, CommonParam commonParam) {
        String cache_key = CacheContentConstants.LEVEIW_CACHE_KEY + rcType;
        // if (commonParam != null
        // &&
        // TerminalCommonConstant.TERMINAL_APPLICATION_LEVIEW.equalsIgnoreCase(commonParam
        // .getTerminalApplication()) && commonParam.getAppVersion() != null
        // && CommonConstants.LEVIEW_RELEASE_VERSION != null
        // &&
        // commonParam.getAppVersion().compareTo(CommonConstants.LEVIEW_RELEASE_VERSION)
        // == 0) {
        // cache_key = CacheContentConstants.RELEASE_LEVEIW_CACHE_KEY + rcType;
        // }
        if (TerminalCommonConstant.TERMINAL_APPLICATION_CIBN_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            cache_key = CacheContentConstants.CIBN_LEVEIW_CACHE_KEY + rcType;
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_WASU_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            cache_key = CacheContentConstants.WASU_LEVEIW_CACHE_KEY + rcType;
        }
        return this.cacheTemplate.get(cache_key, Object.class);
    }

    public void setLeviewCache(String rcType, Object obj, CommonParam commonParam) {
        String cache_key = CacheContentConstants.LEVEIW_CACHE_KEY + rcType;
        // if (commonParam != null
        // &&
        // TerminalCommonConstant.TERMINAL_APPLICATION_LEVIEW.equalsIgnoreCase(commonParam
        // .getTerminalApplication()) && commonParam.getAppVersion() != null
        // && CommonConstants.LEVIEW_RELEASE_VERSION != null
        // &&
        // commonParam.getAppVersion().compareTo(CommonConstants.LEVIEW_RELEASE_VERSION)
        // == 0) {
        // cache_key = CacheContentConstants.RELEASE_LEVEIW_CACHE_KEY + rcType;
        // }
        if (TerminalCommonConstant.TERMINAL_APPLICATION_CIBN_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            cache_key = CacheContentConstants.CIBN_LEVEIW_CACHE_KEY + rcType;
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_WASU_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            cache_key = CacheContentConstants.WASU_LEVEIW_CACHE_KEY + rcType;
        }
        String mark_key = cache_key + "_mark";
        String mark = this.cacheTemplate.get(mark_key, String.class);
        if (!CacheContentConstants.MARK_VALID_VALUE.equals(mark)) {
            this.cacheTemplate.set(mark_key, CacheContentConstants.MARK_VALID_VALUE,
                    CommonConstants.SECONDS_OF_10_MINUTE);
            this.cacheTemplate.set(cache_key, obj);
        }
    }

    /**
     * gc.cp21.ott.cibntv.net/api/outer/getpids 缓存
     */
    public RecommendaionPidListResponse getRecommendaionPidList() {
        String key = CacheContentConstants.GC_RECOMMENDATION_PID_LIST;
        return this.cacheTemplate.get(key, RecommendaionPidListResponse.class);
    }

    /**
     * gc.cp21.ott.cibntv.net/api/outer/getpids 设置缓存
     * @param response
     */
    public void setRecommendaionPidList(RecommendaionPidListResponse response) {
        String key = CacheContentConstants.GC_RECOMMENDATION_PID_LIST;
        this.cacheTemplate.set(key, response, CommonConstants.SECONDS_OF_5_MINUTE);
    }
}
