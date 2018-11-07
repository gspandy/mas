package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.SubjectDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.SubjectPackageDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.WFSubjectDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsPageTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.notification.Commodity;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 频道模块缓存统一处理类
 * @author Jalon
 */
@Component
public class ChannelCacheDao extends BaseCacheDao {

    /**
     * 专题数据缓存
     * @param key
     * @param typeReference
     * @return
     */
    public SubjectDto getSubjectDto(String key) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_SUBJECT_DTO_CACHE_PREFIX + key,
                new LetvTypeReference<SubjectDto>() {
                });
    }

    /**
     * 专题数据缓存
     * @param key
     * @param value
     */
    public void setSubjectDto(String key, SubjectDto value) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_SUBJECT_DTO_CACHE_PREFIX + key, value,
                CommonConstants.SECONDS_OF_15_MINUTE);
    }

    /**
     * 专题数据缓存
     * @param key
     * @param typeReference
     * @return
     */
    public Map<String, SubjectPackageDto> getSubjectPackages(String key) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_SUBJECT_CACHE_PREFIX + key,
                new LetvTypeReference<Map<String, SubjectPackageDto>>() {
                });
    }

    /**
     * 专题数据缓存
     * @param key
     * @param value
     */
    public void setSubjectPackages(String key, Map<String, SubjectPackageDto> value) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_SUBJECT_CACHE_PREFIX + key, value);
    }

    /**
     * 视频营销配置数据缓存get
     */

    public Map<String, String> getVideoSaleConfig() {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_VIDEO_SALE_CONFIG_CACHE_PREFIX,
                new LetvTypeReference<Map<String, String>>() {
                });
    }

    /**
     * 视频营销配置数据缓存set
     * @param value
     */
    public void setVideoSaleConfig(Map<String, String> value) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_VIDEO_SALE_CONFIG_CACHE_PREFIX, value);
    }

    /**
     * 电影、电视剧、乐视儿童更新到**集缓存get
     * @param channelId
     * @return
     */
    public String getChannelTitle(Integer channelId) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_TITLE_FOCUS_PREFIX + channelId, String.class);
    }

    /**
     * 电影、电视剧、乐视儿童更新到**集缓存set
     * @param channelId
     * @param title
     */
    public void setChannelTitle(Integer channelId, String title) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_TITLE_FOCUS_PREFIX + channelId, title,
                CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 电影、电视剧、乐视儿童频道图get
     * @param channelId
     * @return
     */
    public String getChannelPic(String param) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_WALL_PIC_PREFIX + param, String.class);
    }

    /**
     * 电影、电视剧、乐视儿童频道图set
     * @param channelId
     * @param title
     */
    public void setChannelPic(String param, String picUrl) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_WALL_PIC_PREFIX + param, picUrl,
                CommonConstants.SECONDS_OF_1_MINUTE);
    }

    /**
     * 推荐色块色值get
     * @return
     */
    public String getBlockColor() {
        return this.cacheTemplate.get(CacheContentConstants.BLOCK_COLOR_KEY, String.class);
    }

    /**
     * 推荐色块色值set
     * @param color
     */
    public void setBlockColor(String color) {
        this.cacheTemplate.set(CacheContentConstants.BLOCK_COLOR_KEY, color);
    }

    /**
     * 获取板块ID背景图，为919活动
     * @param blockId
     * @return
     */
    public String getBlockTvPic(String blockId) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_BOLCK_TVPIC_PREFIX + blockId, String.class);
    }

    /**
     * 板块ID背景图放入缓存，为919活动
     * @param blockId
     * @return
     */
    public void setBlockTvPic(String blockId, String blockTvPic) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_BOLCK_TVPIC_PREFIX + blockId, blockTvPic);
    }

    /**
     * 根据频道id获取频道下默认码流（该配置由数据库同步至内存，使用时从内存中获取）
     * @param channelId
     * @return
     */
    public String getChannelDefaultStreamById(String channelId) {
        String defaultStream = null;
        if (StringUtils.isNotEmpty(channelId)) {
            defaultStream = this.cacheTemplate.get(CacheContentConstants.CHANNEL_DEFAULT_STREAM_KEY + channelId,
                    String.class);
        }
        return defaultStream;
    }

    /**
     * 将频道的默认码流设置到缓存里
     * @param channelId
     * @param defaultStream
     */
    public void setChannelDefaultStream(String channelId, String defaultStream) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_DEFAULT_STREAM_KEY + channelId, defaultStream);
    }

    /**
     * @param channelId
     * @return
     */
    public String getChannelNameById(String channelId) {
        String channelName = null;
        if (StringUtils.isNotEmpty(channelId)) {
            channelName = this.cacheTemplate.get(CacheContentConstants.CHANNEL_ID_CHANNELNAME_KEY + channelId,
                    String.class);
        }
        return channelName;
    }

    /**
     * set channel id-channelNames map to cache
     */
    public void setChannelNames(Map<String, String> channelIdNameMaps) {
        if (!CollectionUtils.isEmpty(channelIdNameMaps)) {
            Map<String, String> cacheMaps = new HashMap<String, String>((int) (channelIdNameMaps.size() / 0.75) + 1);
            for (Entry<String, String> cacheEntry : channelIdNameMaps.entrySet()) {
                cacheMaps.put(CacheContentConstants.CHANNEL_ID_CHANNELNAME_KEY + cacheEntry.getKey(),
                        cacheEntry.getValue());
            }
            this.cacheTemplate.mset(cacheMaps);
        }
        // this.cacheTemplate.set(CacheContentConstants.CHANNEL_DEFAULT_STREAM_KEY
        // + channelId, defaultStream);
    }

    /**
     * 年龄、性别、星期数：板块映射关系
     * @param key
     * @param value
     */
    public void setBlockMatch(String key, String value) {
        this.cacheTemplate.set(CacheContentConstants.BLOCK_MATCH_PRE + key, value);
    }

    /**
     * 年龄、性别、星期数：板块映射关系
     * @param key
     * @param value
     */
    public String getBlockMatch(String key) {
        return this.cacheTemplate.get(CacheContentConstants.BLOCK_MATCH_PRE + key, String.class);
    }

    /**
     * 板块内容缓存set
     * @param key
     * @param value
     */
    public void setCmsBlockResponse(String key, CmsBlockTpResponse value) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_BLOCK_CONTENT_KEY + key, value);
    }

    /**
     * 板块内容缓存get
     * @param key
     * @param value
     */
    public CmsBlockTpResponse getCmsBlockResponse(String key) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_BLOCK_CONTENT_KEY + key, CmsBlockTpResponse.class);
    }

    /**
     * 板块内容缓存set
     * @param key
     * @param value
     */
    public void setBlockContent(String key, List<CmsBlockTpResponse> value) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_BLOCK_CONTENT_LIST_KEY + key, value);
    }

    /**
     * 板块内容缓存get
     * @param key
     * @param value
     */
    public List<CmsBlockTpResponse> getBlockContent(String key) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_BLOCK_CONTENT_LIST_KEY + key,
                new LetvTypeReference<List<CmsBlockTpResponse>>() {
                });
    }

    /**
     * get commodity data from cache
     * @param blockId
     * @param commonParam
     * @return
     */
    public CmsBlockTpResponse getCmsCommodityData(String blockId, CommonParam commonParam) {
        String key = CacheContentConstants.COMMODITY_DATA_PREFIX + commonParam.getLangcode() + "_" + blockId;
        return this.cacheTemplate.get(key, CmsBlockTpResponse.class);
    }

    /**
     * set commodity data to cache for 30 minutes
     * @param blockId
     * @param commonParam
     * @param response
     */
    public void setCmsCommodityData(String blockId, CommonParam commonParam, CmsBlockTpResponse response) {
        String key = CacheContentConstants.COMMODITY_DATA_PREFIX + commonParam.getLangcode() + "_" + blockId;
        this.cacheTemplate.set(key, response, CommonConstants.SECONDS_OF_30_MINUTE);
    }

    /**
     * get cms commodity data by videoid or albumid
     * @param videoId
     * @param albumId
     * @param commonParam
     * @return
     */
    public Map<String, List<Commodity>> getCmsCommodityData(String videoId, String albumId, CommonParam commonParam) {
        String videoKey = CacheContentConstants.COMMODITY_DATA_PREFIX + commonParam.getLangcode() + "_" + videoId;
        String albumKey = CacheContentConstants.COMMODITY_DATA_PREFIX + commonParam.getLangcode() + "_" + albumId;
        String[] keys = { videoKey, albumKey };
        return this.cacheTemplate.mget(Arrays.asList(keys), new LetvTypeReference<List<Commodity>>() {
        });
    }

    /**
     * set cms commodity data to cache
     * @param contentId
     * @param commonParam
     * @param dataList
     */
    public void setCmsCommodityData(Map<String, List<Commodity>> valueMap) {
        this.cacheTemplate.mset(valueMap, CommonConstants.SECONDS_OF_30_MINUTE);
    }

    /**
     * 页面内容缓存set
     * @param key
     * @param value
     */
    public void setCmsPageTpResponse(String key, CmsPageTpResponse value) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_PAGE_CONTENT_KEY + key, value,
                CommonConstants.SECONDS_OF_1_MINUTE);
    }

    /**
     * 页面内容缓存get
     * @param key
     * @param value
     */
    public CmsPageTpResponse getCmsPageTpResponse(String key) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_PAGE_CONTENT_KEY + key, CmsPageTpResponse.class);
    }

    /**
     * 页面包装后缓存set
     * @param key
     * @param value
     */
    public void setPageDataResponse(String key, String value) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_PAGE_DATA_RESP_KEY + key, value);
    }

    public void setPageDataResponseData(String key, Object obj) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_PAGE_DATA_RESP_DATA_KEY + key, obj,
                CommonConstants.SECONDS_OF_1_MINUTE);
    }

    public void setPageDataResponsePlus(String key, Object obj) {
        this.cacheTemplate.set(CacheContentConstants.CHANNEL_PAGE_DATA_RESP_PLUS_KEY + key, obj,
                CommonConstants.SECONDS_OF_1_MINUTE);
    }

    /**
     * 页面包装后缓存get
     * @param key
     * @param value
     */
    public String getPageDataResponse(String key) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_PAGE_DATA_RESP_KEY + key, String.class);
    }

    public Object getPageDataResponseData(String key) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_PAGE_DATA_RESP_DATA_KEY + key, Object.class);
    }

    public Object getPageDataResponsePlus(String key) {
        return this.cacheTemplate.get(CacheContentConstants.CHANNEL_PAGE_DATA_RESP_PLUS_KEY + key, Object.class);
    }

    /**
     * levidi获取所有cpId
     * @return
     */
    public List<String> getLevidiPublisherIds() {
        return this.cacheTemplate.get(CacheContentConstants.LEVIDI_CP_ID_LIST, new LetvTypeReference<List<String>>() {
        });
    }

    /**
     * levidi: set publisherid to cache;
     * @param idList
     */
    public void setLevidiPublisherIds(List<String> idList) {
        this.cacheTemplate.set(CacheContentConstants.LEVIDI_CP_ID_LIST, idList);
    }

    /**
     * set MacWhiteList to cache;
     * @param idList
     */
    public void setMacWhiteList(Set<String> whiteList) {
        this.cacheTemplate.set(CacheContentConstants.VIDEO_PLAY_WHITE_LIST, whiteList);
    }

    /**
     * get MacWhiteList to cache;
     * @param idList
     */
    public Set<String> getMacWhiteList() {
        return this.cacheTemplate.get(CacheContentConstants.VIDEO_PLAY_WHITE_LIST,
                new LetvTypeReference<Set<String>>() {
                });
    }

    /**
     * 将排行榜取数据开始时间写入缓存
     * @param startDate
     * @param duration
     */
    public void setChartsStartDate(String startDate, int duration) {
        this.cacheTemplate.set(CacheContentConstants.CHARTS_START_DATE_CACHE, startDate, duration);
    }

    /**
     * 从缓存中读取排行取数据的时间范围
     * @return
     */
    public String getChartsStartDate() {
        return this.cacheTemplate.get(CacheContentConstants.CHARTS_START_DATE_CACHE, String.class);
    }

    /**
     * 将排行榜各个榜单的入口数据缓存起来
     * @param chartsId
     * @param value
     * @param duration
     */
    public void setChartsDto(Integer chartsId, String langcode, Object value, int duration) {
        this.cacheTemplate.set(CacheContentConstants.CHARTS_LIST_CACHE + chartsId + "_" + langcode, value, duration);
    }

    /**
     * 从缓存中读取排行榜各个榜单的入口数据
     * @param chartsId
     * @return
     */
    public Object getChartsDto(Integer chartsId, String langcode) {
        return this.cacheTemplate
                .get(CacheContentConstants.CHARTS_LIST_CACHE + chartsId + "_" + langcode, Object.class);
    }

    /**
     * 北美版收银台url
     */
    public void setUsCashierUrlDate(String url) {
        this.cacheTemplate.set(CacheContentConstants.US_CASHIER_URL, url, CommonConstants.SECONDS_OF_1_HOUR);
    }

    /**
     * 北美版收银台url
     * @return
     */
    public String getUsCashierUrlDate(CommonParam commonParam) {
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(CacheContentConstants.US_CASHIER_URL);
            return null;
        }
        return this.cacheTemplate.get(CacheContentConstants.US_CASHIER_URL, String.class);
    }

    /**
     * get WFSubject for subjectId
     * @param subjectId
     * @param commonParam
     * @return
     */
    public WFSubjectDto getWFSubjectData(String subjectId, CommonParam commonParam) {
        String key = CacheContentConstants.WFSUBJECT_DATA_PREFIX + subjectId;
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, WFSubjectDto.class);
    }

    /**
     * get WFSubject for subjectId
     * @param subjectId
     * @param commonParam
     * @return
     */
    public WFSubjectDto getWFSubjectData(String subjectId, boolean flush) {
        String key = CacheContentConstants.WFSUBJECT_DATA_PREFIX + subjectId;
        if (flush) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, WFSubjectDto.class);
    }

    /**
     * set WFSubject data to cache for 30 minutes
     * @param subjectId
     * @param commonParam
     * @param response
     */
    public void setWFSubjectData(String subjectId, CommonParam commonParam, WFSubjectDto response) {
        String key = CacheContentConstants.WFSUBJECT_DATA_PREFIX + subjectId;
        this.cacheTemplate.set(key, response, CommonConstants.SECONDS_OF_30_MINUTE);
    }

    /**
     * set loading pic
     */
    public void setLoadingPic(CommonParam commonParam, String pic) {
        String key = CacheContentConstants.LOADING_PIC_DATA;
        this.cacheTemplate.set(key, pic, CommonConstants.SECONDS_OF_15_MINUTE);
    }

    /**
     * get loading pic
     * @return
     */
    public String getLoadingPic(CommonParam commonParam) {
        String key = CacheContentConstants.LOADING_PIC_DATA;
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, String.class);
    }
}
