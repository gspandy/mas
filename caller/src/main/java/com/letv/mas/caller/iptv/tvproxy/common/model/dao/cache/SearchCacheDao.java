package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ItvLabelResource;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.JSVersionDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.RankingListTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.SearchMaybeLikeTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import serving.GenericServingResponse;

@Component
public class SearchCacheDao extends BaseCacheDao {

    private final static Logger log = LoggerFactory.getLogger(SearchCacheDao.class);

    /**
     * 二级标签 检索设置缓存（searchType方法）
     * @param id
     *            二级标签id
     * @param page
     *            页码
     * @param result
     */
    public void setSearchType(String id, Integer page, GenericServingResponse result) {
        String key = CacheContentConstants.SEARCHTYPES_ID + id + "_" + page;
        this.cacheTemplate.set(key, result, CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 二级标签 从检索获取缓存（searchType方法）
     * @param id
     *            二级标签id
     * @param page
     *            页码
     * @return
     */
    public GenericServingResponse getSearchType(String id, Integer page) {
        String key = CacheContentConstants.SEARCHTYPES_ID + id + "_" + page;
        return this.cacheTemplate.get(key, GenericServingResponse.class);
    }

    /**
     * 二级标签 检索设置缓存
     * @param categoryId
     *            分类id
     * @param searchContent
     *            检索内容
     * @param page
     *            页码
     * @param result
     */
    public void setSearchMenuRetrieve(Integer categoryId, String searchContent, Integer page,
            GenericServingResponse result) {
        String key = CacheContentConstants.SEARCH_MENU_RETRIEVE_ID + categoryId + "_" + searchContent + "_" + page;
        this.cacheTemplate.set(key, result);
    }

    /**
     * 二级标签 从检索获取缓存
     * @param categoryId
     *            分类id
     * @param searchContent
     *            检索内容
     * @param page
     *            页码
     * @return
     */
    public GenericServingResponse getSearchMenuRetrieve(Integer categoryId, String searchContent, Integer page) {
        String key = CacheContentConstants.SEARCH_MENU_RETRIEVE_ID + categoryId + "_" + searchContent + "_" + page;
        return this.cacheTemplate.get(key, GenericServingResponse.class);
    }

    /**
     * 二级标签 设置CMS数据缓存
     * @param blockId
     *            版块id
     * @param response
     */
    public void setSearchMenuCMSBlock(String blockId, CmsBlockTpResponse response) {
        String key = CacheContentConstants.SEARCH_MENU_CMS_ID + blockId;
        this.cacheTemplate.set(key, response, CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 二级标签 获取CMS缓存数据
     * @param blockId
     *            版块id
     * @return
     */
    public CmsBlockTpResponse getSearchMenuCMSBlock(String blockId) {
        String key = CacheContentConstants.SEARCH_MENU_CMS_ID + blockId;
        return this.cacheTemplate.get(key, CmsBlockTpResponse.class);
    }

    /**
     * 二级标签 设置label专题数据缓存
     * @param categoryId
     *            分类id
     * @param searchValue
     *            检索内容
     * @param page
     *            页码
     * @param list
     */
    public void setSearchMenuLabelData(Integer categoryId, String searchValue, Integer page, List<ItvLabelResource> list) {
        String key = CacheContentConstants.SEARCH_MENU_LABEL_ID + categoryId + "_" + searchValue + "_" + page;
        this.cacheTemplate.set(key, list, CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 二级标签 获取label专题数据缓存
     * @param categoryId
     *            分类id
     * @param searchValue
     *            检索内容
     * @param page
     *            页码
     * @return
     */
    public List<ItvLabelResource> getSearchMenuLabelData(Integer categoryId, String searchValue, Integer page) {
        String key = CacheContentConstants.SEARCH_MENU_LABEL_ID + categoryId + "_" + searchValue + "_" + page;
        return this.cacheTemplate.get(key, new LetvTypeReference<List<ItvLabelResource>>() {
        });
    }

    /**
     * 二级标签 设置多个专辑下视频列表缓存
     * @param pids
     *            专辑id，多个以逗号分隔
     * @param page
     *            页码
     * @param videoList
     */
    public void setSearchMenuByAlbumId(String pids, Integer page, List<VideoMysqlTable> videoList) {
        String key = CacheContentConstants.SEARCH_MENU_BYALBUMID_ID + pids + "_" + page;
        this.cacheTemplate.set(key, videoList, CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 二级标签 获取多个专辑下视频列表缓存
     * @param pids
     *            专辑id，多个以逗号分隔
     * @param page
     *            页码
     */
    public List<VideoMysqlTable> getSearchMenuByAlbumId(String pids, Integer page) {
        String key = CacheContentConstants.SEARCH_MENU_BYALBUMID_ID + pids + "_" + page;
        return this.cacheTemplate.get(key, new LetvTypeReference<List<VideoMysqlTable>>() {
        });
    }

    /**
     * 二级标签 设置label数据缓存
     * @param categoryId
     *            分类id
     * @param searchValue
     *            检索内容
     * @param page
     *            页码
     * @param resList
     */
    public void setSearchMenuByLabelId(Integer categoryId, String searchValue, Integer page,
            List<ItvLabelResource> resList) {
        String key = CacheContentConstants.SEARCH_MENU_BYLABELID_ID + categoryId + "_" + searchValue + "_" + page;
        this.cacheTemplate.set(key, resList, CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 二级标签 获取label数据缓存
     * @param categoryId
     *            分类id
     * @param searchValue
     *            检索内容
     * @param page
     *            页码
     * @return
     */
    public List<ItvLabelResource> getSearchMenuByLabelId(Integer categoryId, String searchValue, Integer page) {
        String key = CacheContentConstants.SEARCH_MENU_BYLABELID_ID + categoryId + "_" + searchValue + "_" + page;
        return this.cacheTemplate.get(key, new LetvTypeReference<List<ItvLabelResource>>() {
        });
    }

    /**
     * 二级标签 设置猜你喜欢缓存数据
     * @param area
     *            rec_0001表示相关推荐，rec_0002表示首页及频道页个性化，rec_0003表示轮播个性化推荐
     * @param cid
     *            0、首页你可能喜欢 -1、乐视推荐台专辑列表
     * @param pageSize
     *            数据总量
     * @param response
     */
    public void setSearchMenuUserLike(String area, String cid, Integer pageSize, SearchMaybeLikeTpResponse response) {
        String key = CacheContentConstants.SEARCH_MENU_USERLIKE_ID + area + "_" + cid + "_" + pageSize;
        this.cacheTemplate.set(key, response, CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 二级标签 获取猜你喜欢缓存数据
     * @param area
     *            rec_0001表示相关推荐，rec_0002表示首页及频道页个性化，rec_0003表示轮播个性化推荐
     * @param cid
     *            0、首页你可能喜欢 -1、乐视推荐台专辑列表
     * @param pageSize
     *            数据总量
     * @return
     */
    public SearchMaybeLikeTpResponse getSearchMenuUserLike(String area, String cid, Integer pageSize) {
        String key = CacheContentConstants.SEARCH_MENU_USERLIKE_ID + area + "_" + cid + "_" + pageSize;
        return this.cacheTemplate.get(key, SearchMaybeLikeTpResponse.class);
    }

    /**
     * 二级标签 获取排行榜缓存数据
     * @param url
     *            第三方接口url
     * @return
     */
    public RankingListTpResponse getRankingList(String url) {
        String key = CacheContentConstants.SEARCH_MENU_RANK_ID + url;
        return this.cacheTemplate.get(key, RankingListTpResponse.class);
    }

    /**
     * 二级标签 设置排行榜缓存数据
     * @param url
     *            第三方接口url
     * @param response
     */
    public void setRankingList(String url, RankingListTpResponse response) {
        String key = CacheContentConstants.SEARCH_MENU_RANK_ID + url;
        this.cacheTemplate.set(key, response, CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 获取JS版本信息（定时任务中设置，这里只获取）
     * @return
     */
    public JSVersionDto getJSVersion() {
        return this.cacheTemplate.get(JSVersionDto.SEARCH_JSVERSION_CACHEKEY, JSVersionDto.class);
    }
}
