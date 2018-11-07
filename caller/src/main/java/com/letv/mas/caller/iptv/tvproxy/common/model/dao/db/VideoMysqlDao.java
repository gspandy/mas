package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component(value = "v2.VideoMysqlDao")
public interface VideoMysqlDao {

    /**
     * 获得专辑下视频列表, sqlMarker: 1
     * @param albumId
     *            专辑id
     * @param start
     * @param end
     * @param videoType
     * @return
     */
    @Deprecated
    public List<VideoMysqlTable> getVideoListByPorder(@Param("pid") Long albumId, @Param("start") Integer start,
                                                      @Param("end") Integer end, @Param("broadcastId") Integer broadcastId);

    /**
     * ONLY used in the daemon task
     * @param albumId
     *            专辑id
     * @param positive
     *            是否正片
     * @param broadcastId
     * @return
     */
    public List<VideoMysqlTable> getVideoListByPorderNoLimit(@Param("pid") Long albumId,
            @Param("positive") Boolean positive, @Param("broadcastId") Integer broadcastId,
            @Param("isPushChild") Integer isPushChild);

    /**
     * 获得专辑下视频列表, sqlMarker: 2
     * @param albumId
     * @param start
     * @param end
     * @param broadcastId
     * @param orderType
     *            0降序 1升序
     * @return
     */
    @Deprecated
    public List<VideoMysqlTable> getVideoListByLimit(@Param("pid") Long albumId, @Param("start") Integer start,
            @Param("end") Integer end, @Param("broadcastId") Integer broadcastId, @Param("orderType") Integer orderType);

    /**
     * 获得视频
     * @param videoId
     *            视频id
     * @return
     */
    @Deprecated
    public VideoMysqlTable getVideoById(@Param("id") Long videoId);

    /**
     * sqlMarker: 3
     * @param albumId
     * @param positive
     * @param broadcastId
     * @return
     */
    @Deprecated
    public List<VideoMysqlTable> getVideoMysqlTableListByUpdateTime(@Param("albumId") Long albumId,
            @Param("positive") Boolean positive, @Param("broadcastId") Integer broadcastId);

    /**
     * 获得专辑下视频列表, sqlMarker: 4
     * @param albumId
     * @param orderType
     *            : 0 asc ; 1 desc
     * @param broadcastId
     * @return
     */
    @Deprecated
    public List<VideoMysqlTable> getVideoListByPorderAndCreateTime(@Param("pid") Long albumId,
            @Param("positive") Boolean positive, @Param("orderType") Integer orderType,
            @Param("broadcastId") Integer broadcastId, @Param("start") Integer start, @Param("end") Integer end);

    /**
     * 根据vrs专辑id获取总数
     * @param pid
     * @param broadcastId
     * @param vtype
     * @return
     */
    public Long getAlbumSeriesCountByPid(@Param("pid") Long pid, @Param("broadcastId") Integer broadcastId,
            @Param("vtype") Integer vtype);

    public Long getAlbumSeriesCountByPids(@Param("pids") Long[] pids, @Param("broadcastId") Integer broadcastId,
            @Param("vtype") Integer vtype);

    /**
     * 根据vrs的pid获得专辑的视频列表(分页)
     * @return
     */
    public List<VideoMysqlTable> getVideoListByPid(@Param("pid") Long pid, @Param("start") Integer start,
            @Param("end") Integer end, @Param("broadcastId") Integer broadcastId,
            @Param("orderType") Integer orderType, @Param("vtype") Integer vtype);

    /*
     * order by porder desc,release_date desc,update_time desc, sqlMarker: 5
     */
    @Deprecated
    public List<VideoMysqlTable> getVideoListByPids(@Param("pids") Long[] pids, @Param("start") Integer start,
            @Param("end") Integer end, @Param("broadcastId") Integer broadcastId,
            @Param("orderType") Integer orderType, @Param("vtype") Integer vtype);

    public void insertVideoInfo(VideoMysqlTable videoMysqlTable);

    /**
     * 更新视频
     * @param videoMysqlTable
     */
    public void updateVideoInfoById(VideoMysqlTable videoMysqlTable);

    public void deleteVideoInfoById(Long id);

    @Deprecated
    public Long getAlbumFollownumByAlbumId(Long aid);

    public List<VideoMysqlTable> get4KVideoListByStream(@Param("stream") String stream, @Param("near") String near);

    public List<VideoMysqlTable> getDrmVideoList(String near);

    public List<String> getAlbumAllStreams(Long pid);

    public List<String> getAlbumPositiveStreams(Long pid);

    public List<Long> getAllIdByPid(Long pid);

    @Deprecated
    public List<VideoMysqlTable> getAllVideoByPid(Long id);

    /**
     * 根据专辑id获取其正片剧集列表中第一个视频
     * @param pid
     * @deprecated replaced by <code>VideoTpDao.getVideoListByAlbumId()</code>
     * @return
     */
    @Deprecated
    public VideoMysqlTable getFirstPositiveVideoByPid(@Param("pid") Long pid, @Param("broadcastId") Integer broadcastId);

    public Long getFirstPositiveVideoIdByPid(@Param("pid") Long pid, @Param("broadcastId") Integer broadcastId,
            @Param("isPushChild") Integer isPushChild);

    public Long getEndPositiveVideoIdByPid(@Param("pid") Long pid, @Param("broadcastId") Integer broadcastId,
            @Param("isPushChild") Integer isPushChild);

    /**
     * @param pid
     * @deprecated replaced by <code>VideoTpDao.getVideoListByAlbumId()</code>
     * @return
     */
    @Deprecated
    public Long getEndPositiveVideoIdByPid(@Param("pid") Long pid, @Param("broadcastId") Integer broadcastId);

    /**
     * 根据专辑id获取其正片剧集列表中最新的视频
     * @param pid
     * @deprecated replaced by <code>VideoTpDao.getVideoListByAlbumId()</code>
     * @return
     */
    @Deprecated
    public VideoMysqlTable getLastPositiveVideoByPid(@Param("pid") Long pid);

    /**
     * 根据专辑id获取其正片剧集列表中哪集开始付费
     * @param pid
     * @param pay_platform
     * @param play_platform
     * @return
     */
    public VideoMysqlTable getMinChargeVideoByPid(@Param("pid") Long pid, @Param("pay_platform") String pay_platform,
            @Param("play_platform") String play_platform);

    /**
     * 根据专辑id获取其正片剧集列表中第几集
     * @param pid
     * @param video_type
     * @param porder
     * @return
     */
    public VideoMysqlTable getVideoByPidAndPorder(@Param("pid") Long pid, @Param("video_type") int video_type,
            @Param("porder") int porder);

    /**
     * 批量获取video信息
     * @param ids
     * @return
     */
    public List<VideoMysqlTable> getVideoByIds(@Param("ids") String[] ids);

    /**
     * 批量获取video信息
     * @param ids
     * @return
     */
    public List<VideoMysqlTable> getVideoListByIds(@Param("ids") List<Long> ids);
}
