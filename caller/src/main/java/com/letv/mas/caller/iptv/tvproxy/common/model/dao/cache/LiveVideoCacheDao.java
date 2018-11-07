package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoDataWrapper;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import org.springframework.stereotype.Component;

@Component
public class LiveVideoCacheDao extends BaseCacheDao {

    private static final String LIVE_VIDEO_WRAPPER_CACHE_KEY = "com-video-wr-";// 通用播放接口视频缓存key前缀
    private static final String LIVE_ALBUM_WRAPPER_CACHE_KEY = "com-album-wr-";// 通用播放接口专辑缓存key前缀
    private static final int VIDEO_OR_ALBUM_CACHE_TIME = 1296000;// 通用播放接口视频缓存15天

    public AlbumVideoDataWrapper getVideoWrapper(Long videoId, CommonParam commonParam) {
        String key = getVideoWrapperCacheKey(videoId, commonParam);
        return this.cacheTemplate.get(key, AlbumVideoDataWrapper.class);
    }

    public void setVideoWrapper(Long videoId, CommonParam commonParam, AlbumVideoDataWrapper videoWrapper) {
        String key = getVideoWrapperCacheKey(videoId, commonParam);
        this.cacheTemplate.set(key, videoWrapper, VIDEO_OR_ALBUM_CACHE_TIME);
    }

    public AlbumVideoDataWrapper getAlbumWrapper(Long albumId, CommonParam commonParam) {
        String key = getAlbumWrapperCacheKey(albumId, commonParam);
        return this.cacheTemplate.get(key, AlbumVideoDataWrapper.class);
    }

    public void setAlbumWrapper(Long albumId, CommonParam commonParam, AlbumVideoDataWrapper albumWrapper) {
        String key = getAlbumWrapperCacheKey(albumId, commonParam);
        this.cacheTemplate.set(key, albumWrapper, VIDEO_OR_ALBUM_CACHE_TIME);
    }

    private String getVideoWrapperCacheKey(Long videoId, CommonParam commonParam) {
        StringBuilder sb = new StringBuilder(LIVE_VIDEO_WRAPPER_CACHE_KEY);
        sb.append(videoId).append("-").append(commonParam.getP_devType()).append("-").append(commonParam.getLangcode());
        return sb.toString();
    }

    private String getAlbumWrapperCacheKey(Long albumId, CommonParam commonParam) {
        StringBuilder sb = new StringBuilder(LIVE_ALBUM_WRAPPER_CACHE_KEY);
        sb.append(albumId).append("-").append(commonParam.getP_devType()).append("-").append(commonParam.getLangcode());
        return sb.toString();
    }
}
