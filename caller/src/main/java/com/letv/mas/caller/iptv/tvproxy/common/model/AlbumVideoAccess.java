package com.letv.mas.caller.iptv.tvproxy.common.model;

import java.util.List;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;

/**
 * Album and video access utility
 */
public interface AlbumVideoAccess {

    /**
     * Get album information from cache, when it's absent in cache, get it from
     * VRS then update cache.
     * @param id
     *            album id
     * @param commonParam
     *            common parameters
     * @return the album information
     */
    AlbumMysqlTable getAlbum(Long id, CommonParam commonParam);

    /**
     * Get albums information from cache, when it's absent in cache, get it from
     * VRS then update cache.
     * @param ids
     *            album id array
     * @param commonParam
     *            common parameters
     * @return the album information
     */
    Map<String, AlbumMysqlTable> getAlbums(List<Long> ids, CommonParam commonParam);

    Map<String, AlbumMysqlTable> getAlbumsEx(List<String> ids, CommonParam commonParam);

    /**
     * Get video information from cache, when it's absent in cache, get it from
     * VRS then update cache.
     * @param id
     *            video id
     * @param commonParam
     *            common parameters
     * @return the video information
     */
    VideoMysqlTable getVideo(Long id, CommonParam commonParam);

    /**
     * Get video information from cache, when it's absent in cache, get it from
     * VRS then update cache.
     * @param ids
     *            video id
     * @param commonParam
     *            common parameters
     * @return the video information
     */
    Map<String, VideoMysqlTable> getVideos(List<Long> ids, CommonParam commonParam);

    Map<String, VideoMysqlTable> getVideosEx(List<String> ids, CommonParam commonParam);

    /**
     * Get paged video list of an album information from cache, when it's absent
     * in cache, get it from VRS then update cache.
     * @param albumId
     *            album id
     * @param queryType
     *            query type, 0: non-feature; 1: feature; 2: all.
     * @param porder
     *            sort order, -1: normal; 1: reversed
     * @param pageSize
     *            page size
     * @param page
     *            page number
     * @param commonParam
     *            common parameters
     * @return brief video information list of the requested page
     */
    List<VideoMysqlTable> getVideoList(Long albumId, String queryType, Integer porder, Integer pageSize, Integer page,
            CommonParam commonParam);

    /**
     * Get video list of an album information with specified start and end from
     * the cache, when it's absent in cache, get it from VRS then update cache.
     * @param albumId
     *            album id
     * @param queryType
     *            query type, 0: non-feature; 1: feature; 2: all.
     * @param porder
     *            sort order, -1: normal; 1: reversed
     * @param start
     *            start position, start from 0, included
     * @param end
     *            end position, start from 0, included
     * @param commonParam
     *            common parameters
     * @param sqlMarker
     *            marker to identify the legacy methods in VideoMysqlDao, users
     *            can ignore it and assign 0
     * @return brief video information list of the requested page
     */
    List<VideoMysqlTable> getVideoRange(Long albumId, String queryType, Integer porder, Integer start, Integer end,
            CommonParam commonParam, int sqlMarker);

    /**
     * Get the total number of the videos belongs to the album
     * @param albumId
     *            album id
     * @param queryType
     *            query type
     * @param commonParam
     *            common parameters
     * @return total number of the videos
     */
    Integer getVideoTotalNum(Long albumId, String queryType, CommonParam commonParam);

    /**
     * Get the statistics of local cache and cache
     * @return the statistics of local cache and cache
     */
    AlbumVideoCacheStats getStats();

    VideoMysqlTable getMinChargeVideoByPid(Long pid, String pay_platform, String play_platform, CommonParam commonParam);

    VideoMysqlTable getVideoByPidAndPorder(Long pid, int video_type, int porder, CommonParam commonParam);

    List<VideoMysqlTable> getVideoList(String[] ids, CommonParam commonParam);
}
