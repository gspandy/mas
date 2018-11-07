package com.letv.mas.caller.iptv.tvproxy.common.model;

import com.google.common.cache.CacheStats;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache.VideoCacheDao;

public class AlbumVideoCacheStats {
    private final VideoCacheDao.Stats cacheStats;
    private final CacheStats localCacheStats;

    public AlbumVideoCacheStats(VideoCacheDao.Stats cacheStats, CacheStats localCacheStats) {
        this.cacheStats = cacheStats;
        this.localCacheStats = localCacheStats;
    }

    public long getAlbumHit() {
        return cacheStats.albumHit;
    }

    public long getAlbumMiss() {
        return cacheStats.albumMiss;
    }

    public long getAlbumRequestCount() {
        return cacheStats.albumHit + cacheStats.albumMiss;
    }

    public double getAlbumHitRate() {
        long total = cacheStats.albumHit + cacheStats.albumMiss;
        return total == 0 ? 1.0 : (double) cacheStats.albumHit / total;
    }

    public long getVideoHit() {
        return cacheStats.videoHit;
    }

    public long getVideoMiss() {
        return cacheStats.videoMiss;
    }

    public long getVideoRequestCount() {
        return cacheStats.videoHit + cacheStats.videoMiss;
    }

    public double getVideoHitRate() {
        long total = cacheStats.videoHit + cacheStats.videoMiss;
        return total == 0 ? 1.0 : (double) cacheStats.videoHit / total;
    }

    public long getVideoListHit() {
        return cacheStats.videoListHit;
    }

    public long getVideoListMiss() {
        return cacheStats.videoListMiss;
    }

    public long getVideoListRequestCount() {
        return cacheStats.videoListHit + cacheStats.videoListMiss;
    }

    public double getVideoListHitRate() {
        long total = cacheStats.videoListHit + cacheStats.videoListMiss;
        return total == 0 ? 1.0 : (double) cacheStats.videoListHit / total;
    }

    public long getLocalRequestCount() {
        if (localCacheStats == null) {
            return 0;
        }
        return localCacheStats.requestCount();
    }

    public long getLocalHitCount() {
        if (localCacheStats == null) {
            return 0;
        }
        return localCacheStats.hitCount();
    }

    public double getLocalHitRate() {
        if (localCacheStats == null) {
            return 0;
        }
        return localCacheStats.hitRate();
    }

    public long getLocalEvictionCount() {
        if (localCacheStats == null) {
            return 0;
        }
        return localCacheStats.evictionCount();
    }
}
