package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * CacheDao门面类，提供各业务模块CacheDao接口入口
 * @author KevinYi
 */
@Component(value = "facadeCacheDao")
public class FacadeCacheDao {
    @Resource
    private VideoCacheDao videoCacheDao;

    @Resource
    private ChannelCacheDao channelCacheDao;

    @Resource
    private RecommendationCacheDao recommendationCacheDao;

    @Resource
    private SearchCacheDao searchCacheDao;

    @Resource
    private UserCacheDao userCacheDao;

    @Resource
    private VipCacheDao vipCacheDao;

    @Resource
    private LiveCacheDao liveCacheDao;

    @Resource
    private TerminalCacheDao terminalCacheDao;

    @Resource
    private GoliveCacheDao goliveCacheDao;

    @Resource
    private LiveVideoCacheDao liveVideoCacheDao;

    @Resource
    private ActivityCacheDao activityCacheDao;

    public void setGoliveCacheDao(GoliveCacheDao goliveCacheDao) {
        this.goliveCacheDao = goliveCacheDao;
    }

    public GoliveCacheDao getGoliveCacheDao() {
        return this.goliveCacheDao;
    }

    public VideoCacheDao getVideoCacheDao() {
        return this.videoCacheDao;
    }

    public void setVideoCacheDao(VideoCacheDao videoCacheDao) {
        this.videoCacheDao = videoCacheDao;
    }

    public ChannelCacheDao getChannelCacheDao() {
        return this.channelCacheDao;
    }

    public void setChannelCacheDao(ChannelCacheDao channelCacheDao) {
        this.channelCacheDao = channelCacheDao;
    }

    public SearchCacheDao getSearchCacheDao() {
        return this.searchCacheDao;
    }

    public void setSearchCacheDao(SearchCacheDao searchCacheDao) {
        this.searchCacheDao = searchCacheDao;
    }

    public RecommendationCacheDao getRecommendationCacheDao() {
        return this.recommendationCacheDao;
    }

    public UserCacheDao getUserCacheDao() {
        return this.userCacheDao;
    }

    public void setUserCacheDao(UserCacheDao userCacheDao) {
        this.userCacheDao = userCacheDao;
    }

    public VipCacheDao getVipCacheDao() {
        return this.vipCacheDao;
    }

    public void setVipCacheDao(VipCacheDao vipCacheDao) {
        this.vipCacheDao = vipCacheDao;
    }

    public LiveCacheDao getLiveCacheDao() {
        return this.liveCacheDao;
    }

    public void setLiveCacheDao(LiveCacheDao liveCacheDao) {
        this.liveCacheDao = liveCacheDao;
    }

    public void setRecommendationCacheDao(RecommendationCacheDao recommendationCacheDao) {
        this.recommendationCacheDao = recommendationCacheDao;
    }

    public TerminalCacheDao getTerminalCacheDao() {
        return this.terminalCacheDao;
    }

    public void setTerminalCacheDao(TerminalCacheDao terminalCacheDao) {
        this.terminalCacheDao = terminalCacheDao;
    }

    public LiveVideoCacheDao getLiveVideoCacheDao() {
        return this.liveVideoCacheDao;
    }

    public ActivityCacheDao getActivityCacheDao() {
        return this.activityCacheDao;
    }
}
