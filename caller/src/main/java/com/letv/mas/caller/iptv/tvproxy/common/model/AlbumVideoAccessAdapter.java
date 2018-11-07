package com.letv.mas.caller.iptv.tvproxy.common.model;

import java.lang.reflect.Field;
import java.util.*;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.SQLConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache.VideoCacheDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache.dto.VideoCacheIndex;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.AlbumMysqlDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.VideoMysqlDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.MmsTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.WorksAlbumInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.WorksResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.WorksVideoInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.PageCacheDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.PageCacheItem;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * Adapter class for {@link VideoMysqlDao} and {@link VideoCacheDao}
 */
@Repository
public class AlbumVideoAccessAdapter implements AlbumVideoAccess {
    private final static boolean DB_SWITCH;

    static {
        Boolean db = ApplicationUtils.getBoolean(ApplicationConstants.IPTV_OBTAIN_DATA_FROM_DB_SWITCH);
        DB_SWITCH = db != null && db;
    }

    @Autowired
    private AlbumMysqlDao albumMysqlDao;
    @Autowired
    private VideoMysqlDao videoMysqlDao;
    @Autowired
    private VideoCacheDao videoCacheDao;
    @Autowired
    private VideoTpDao videoTpDao;

    @Override
    public AlbumMysqlTable getAlbum(Long id, CommonParam commonParam) {
        AlbumMysqlTable album = videoCacheDao.getAlbum(id, null);
        if (album == null && DB_SWITCH && StringUtil.isBlank(commonParam.getCpid())) {
            album = albumMysqlDao.getAlbumById(id);
            if (album != null) {
                videoCacheDao.setAlbum(id, null, album);
            }
        } else if (album == null && StringUtil.isNotBlank(commonParam.getCpid())) {
            WorksResponse<WorksAlbumInfo> worksResponse = videoTpDao.getWorksAlbumByAlbumId(id);
            if (null != worksResponse) {
                album = MmsDataUtil.transferAlbumData(worksResponse.getData());
            }
            if (album != null) {
                videoCacheDao.setAlbum(id, null, album);
            }
        }
        return VideoCommonUtil.filterByBroadcastId(album, commonParam.getBroadcastId());
    }

    @Override
    public Map<String, AlbumMysqlTable> getAlbums(List<Long> ids, CommonParam commonParam) {
        List<String> idList = new ArrayList<String>();
        for (Long id : ids) {
            idList.add(String.valueOf(id));
        }
        return this.getAlbumsEx(idList, commonParam);
    }

    /**
     * @param ids
     *            包含其他cp的集合，id格式为aid_cpid
     * @param commonParam
     * @return
     */
    @Override
    public Map<String, AlbumMysqlTable> getAlbumsEx(List<String> ids, CommonParam commonParam) {
        if (ids == null || ids.size() <= 0) {
            return null;
        }
        Map<String, AlbumMysqlTable> albums = videoCacheDao.getAlbumsEx(ids, null);
        if ((albums == null || albums.size() < ids.size()) && DB_SWITCH) {
            List<Long> dbIds = null;
            List<String> worksIds = null;
            if (albums == null) {
                dbIds = new ArrayList<Long>();
                worksIds = new ArrayList<String>();
                String[] idArrTmp = null;
                for (String id : ids) {
                    idArrTmp = id.split("_");
                    if (idArrTmp.length > 1) {
                        worksIds.add(idArrTmp[0]);
                    } else {
                        dbIds.add(Long.parseLong(idArrTmp[0]));
                    }
                }
            } else {
                dbIds = new ArrayList<Long>();
                worksIds = new ArrayList<String>();
                String[] idArrTmp = null;
                for (String id : ids) {
                    idArrTmp = id.split("_");
                    AlbumMysqlTable temp = albums.get(idArrTmp[0]);
                    if (temp == null) {
                        if (idArrTmp.length > 1) {
                            worksIds.add(idArrTmp[0]);
                        } else {
                            dbIds.add(Long.parseLong(idArrTmp[0]));
                        }
                    }
                    temp = null;
                }
            }
            if (null != dbIds && dbIds.size() > 0) { // 原自有专辑
                List<AlbumMysqlTable> albumMysqlTableList = albumMysqlDao.getAlbumListByIdList(dbIds);
                if (albumMysqlTableList != null && albumMysqlTableList.size() > 0) {
                    if (albums == null) {
                        albums = new HashMap<String, AlbumMysqlTable>();
                    }
                    for (AlbumMysqlTable temp : albumMysqlTableList) {
                        if (temp != null) {
                            albums.put(temp.getId() + "", temp);
                        }
                    }
                    videoCacheDao.setAlbums(albumMysqlTableList, null);
                    albumMysqlTableList.clear();
                }
                // dbIds.clear();
            }

            if (null != worksIds && worksIds.size() > 0) { // 3rd专辑
                // TODO: [验证] 作品库接口读取落缓存
                String wids = StringUtil.getListToString(worksIds, ",");
                Map<String, WorksAlbumInfo> worksAlbumInfos = videoTpDao.getWorksAlbumsByAlbumIds(wids, commonParam);
                AlbumMysqlTable albumMysqlTable = null;
                if (null != worksAlbumInfos && worksAlbumInfos.size() > 0) {
                    List<AlbumMysqlTable> albumMysqlTableList = new ArrayList<AlbumMysqlTable>();
                    if (albums == null) {
                        albums = new HashMap<String, AlbumMysqlTable>();
                    }
                    for (Map.Entry<String, WorksAlbumInfo> worksAlbumInfoEntry : worksAlbumInfos.entrySet()) {
                        albumMysqlTable = null;
                        if (null != worksAlbumInfoEntry.getValue()) {
                            albumMysqlTable = MmsDataUtil.transferAlbumData(worksAlbumInfoEntry.getValue());
                            albumMysqlTableList.add(albumMysqlTable);
                        }
                        albums.put(worksAlbumInfoEntry.getKey(), albumMysqlTable);
                    }
                    if (albumMysqlTableList != null && albumMysqlTableList.size() > 0) {
                        videoCacheDao.setAlbums(albumMysqlTableList, null);
                        albumMysqlTableList.clear();
                    }
                }
            }
        }
        return albums;
    }

    @Override
    public VideoMysqlTable getVideo(Long id, CommonParam commonParam) {
        VideoMysqlTable video = videoCacheDao.getVideo(id, null);
        if (video == null && DB_SWITCH && StringUtil.isBlank(commonParam.getCpid())) {
            video = videoMysqlDao.getVideoById(id);
            if (video != null) {
                videoCacheDao.setVideo(id, null, video);
            }
        }
        return VideoCommonUtil.filterByBroadcastId(video, commonParam.getBroadcastId());
    }

    @Override
    public Map<String, VideoMysqlTable> getVideos(List<Long> ids, CommonParam commonParam) {
        List<String> idList = new ArrayList<String>();
        for (Long id : ids) {
            idList.add(String.valueOf(id));
        }
        return this.getVideosEx(idList, commonParam);
    }

    @Override
    public Map<String, VideoMysqlTable> getVideosEx(List<String> ids, CommonParam commonParam) {
        if (ids == null || ids.size() <= 0) {
            return null;
        }
        Map<String, VideoMysqlTable> videos = videoCacheDao.getVideosEx(ids, null);
        if ((videos == null || videos.size() < ids.size()) && DB_SWITCH) {
            List<Long> dbIds = null;
            List<String> worksIds = null;
            if (videos == null) {
                dbIds = new ArrayList<Long>();
                worksIds = new ArrayList<String>();
                String[] idArrTmp = null;
                for (String id : ids) {
                    idArrTmp = id.split("_");
                    if (idArrTmp.length > 1) {
                        worksIds.add(idArrTmp[0]);
                    } else {
                        dbIds.add(Long.parseLong(idArrTmp[0]));
                    }
                }
            } else {
                dbIds = new ArrayList<Long>();
                worksIds = new ArrayList<String>();
                String[] idArrTmp = null;
                for (String id : ids) {
                    idArrTmp = id.split("_");
                    VideoMysqlTable temp = videos.get(idArrTmp[0]);
                    if (temp == null) {
                        if (idArrTmp.length > 1) {
                            worksIds.add(idArrTmp[0]);
                        } else {
                            dbIds.add(Long.parseLong(idArrTmp[0]));
                        }
                    }
                    temp = null;
                }
            }

            if (dbIds.size() > 0) { // 原自有视频
                List<VideoMysqlTable> videoMysqlTableList = videoMysqlDao.getVideoListByIds(dbIds);
                if (videoMysqlTableList != null && videoMysqlTableList.size() > 0) {
                    if (videos == null) {
                        videos = new HashMap<String, VideoMysqlTable>();
                    }
                    for (VideoMysqlTable temp : videoMysqlTableList) {
                        if (temp != null) {
                            videos.put(temp.getId() + "", temp);
                        }
                    }
                    videoCacheDao.setVideos(videoMysqlTableList, null);
                    videoMysqlTableList.clear();
                }
                dbIds.clear();
            }

            if (null != worksIds && worksIds.size() > 0) { // 3rd视频集
                // TODO: 作品库接口读取落缓存，目前上游不支持按vids批量获取视频
            }
        }
        return videos;
    }

    @Override
    public List<VideoMysqlTable> getVideoList(Long albumId, String queryType, Integer porder, Integer pageSize,
            Integer page, CommonParam commonParam) {
        // handle getVideoListByPorder in addPlayList as a special case
        if (StringUtil.isNotBlank(commonParam.getCpid())) {
            // TODO: [验证]作品库接口
            return this.getVideoRange(albumId, queryType, porder, (page - 1) * pageSize, page * pageSize, commonParam,
                    1);
        } else {
            return videoMysqlDao.getVideoListByPorder(albumId, 0, Integer.MAX_VALUE, commonParam.getBroadcastId());
        }
    }

    /**
     * 分页检索专辑视频集合
     * @param albumId
     * @param sortParams
     * @param filterParams
     * @param start
     * @param end
     * @param pageSize
     * @param commonParam
     * @return
     */
    private List<VideoMysqlTable> getWorkVideosRange(Long albumId, LinkedHashMap<String, String> sortParams,
            final Map<String, String> filterParams, Integer start, Integer end, Integer pageSize,
            CommonParam commonParam) {
        List<VideoMysqlTable> videoList = null;

        // 获取专辑缓存索引表
        List<VideoCacheIndex> cacheIndexList = videoCacheDao.getAlbumVideoIndexes(String.valueOf(albumId), commonParam);

        if (null == cacheIndexList || cacheIndexList.size() == 0) {
            // 如果未生成专辑索引，则从作品库获取
            List<WorksVideoInfo> worksVideoInfos = this.videoTpDao.getWorksVideoListByAlbumId(albumId, null, null,
                    null, null);
            if (null != worksVideoInfos && worksVideoInfos.size() > 0) {
                videoList = MmsDataUtil.transferVideoData(worksVideoInfos);
                videoCacheDao.setVideos(videoList, null);
                // 创建索引表缓存
                cacheIndexList = PageCacheUtil.trans4CacheIndexes(videoList, VideoCacheIndex.class);
                videoCacheDao.setAlbumVideoIndexes(String.valueOf(albumId), cacheIndexList);
            }
        }

        // 条件过滤
        if (null != filterParams && filterParams.size() > 0) {
            cacheIndexList = ListUtil.filter(cacheIndexList, new ListUtil.ListHook<VideoCacheIndex>() {
                @Override
                public boolean test(VideoCacheIndex videoCacheIndex) {
                    String filterParam = null;
                    boolean ret = false;
                    for (Map.Entry<String, String> param : filterParams.entrySet()) {
                        filterParam = param.getValue();
                        Class clazz = videoCacheIndex.getClass();
                        Field field = null;
                        try {
                            field = clazz.getDeclaredField(param.getKey());
                            if (null != field) {
                                field.setAccessible(true);
                                if (filterParam.equals(String.valueOf(field.get(videoCacheIndex)))) { // 保留
                                    ret = true;
                                } else if (filterParam.equals("!" + field.get(videoCacheIndex))) { // 剔除
                                    ret = false;
                                    continue;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return ret;
                }
            });
        }

        // 基于缓存索引排序及分页
        if (pageSize != null && (pageSize < 0 || pageSize > VideoTpConstant.ALBUM_SERIES_PAGE_SIZE)) {
            pageSize = VideoTpConstant.ALBUM_SERIES_PAGE_SIZE;
        }
        if (null == start) {
            start = 0;
        }
        if (null == end) {
            end = cacheIndexList.size();
        }
        List<Long> vids = new ArrayList<Long>();
        if (null != sortParams && !sortParams.isEmpty()) {
            PageCacheDto pageCacheDto = PageCacheUtil.buildPageCacheDto(cacheIndexList, VideoCacheIndex.class);
            pageCacheDto.setSortParams(sortParams);
            pageCacheDto = PageCacheUtil.sort(pageCacheDto);
            // 分页
            List<PageCacheItem> cacheItemList = pageCacheDto.getItems();
            cacheItemList = cacheItemList.subList(start < cacheIndexList.size() ? start : 0,
                    end <= cacheIndexList.size() ? end : cacheIndexList.size());
            for (PageCacheItem item : cacheItemList) {
                vids.add(Long.parseLong(item.getBizId()));
            }
        } else {
            // 分页
            cacheIndexList = cacheIndexList.subList(start < cacheIndexList.size() ? start : 0,
                    end <= cacheIndexList.size() ? end : cacheIndexList.size());
            for (VideoCacheIndex item : cacheIndexList) {
                vids.add(Long.parseLong(item.getId()));
            }
        }

        // 提取视频缓存
        if (null != vids && vids.size() > 0) {
            Map<String, VideoMysqlTable> videos = ((null == videoList || videoList.size() == 0) ? videoCacheDao
                    .getVideos(vids, null) : ListUtil.list2Map(videoList, "id"));
            if (null != videos && !videos.isEmpty()) {
                videoList = new ArrayList<VideoMysqlTable>();
                VideoMysqlTable videoMysqlTable = null;
                for (Long vid : vids) {
                    videoMysqlTable = videos.get(String.valueOf(vid));
                    if (null != videoMysqlTable) {
                        videoList.add(videoMysqlTable);
                    }
                }
            }
        }

        return videoList;
    }

    @Override
    public List<VideoMysqlTable> getVideoRange(Long albumId, String queryType, Integer porder, Integer start,
            Integer end, CommonParam commonParam, int sqlMarker) {
        Integer broadcastId = commonParam.getBroadcastId();
        List<VideoMysqlTable> videoList = null;
        int orderType = porder != null && porder == 1 ? SQLConstants.SQL_ORDER_DESC : SQLConstants.SQL_ORDER_ASC;
        // POSITIVE and ALL is true, QUERY_TYPE_NON_POSITIVE is false
        boolean isPositive = !VideoTpConstant.QUERY_TYPE_NON_POSITIVE.equals(queryType);

        if (null != start && null != end && (end - start > 3000)) { // avoid to
                                                                    // get a lot
                                                                    // of videos
                                                                    // to
                                                                    // shutdown
                                                                    // service!!!
            end = start + 3000;
        }

        switch (sqlMarker) {
        case 1:
            // porder正序，create_time倒序
            if (StringUtil.isNotBlank(commonParam.getCpid())) {
                LinkedHashMap<String, String> sortParams = new LinkedHashMap<String, String>();
                sortParams.put("porder", "asc");
                sortParams.put("time", "desc");
                videoList = this.getWorkVideosRange(albumId, sortParams, null, start, end, null, commonParam);
            } else {
                videoList = videoMysqlDao.getVideoListByPorder(albumId, start, end, broadcastId);
            }
            break;
        case 2:
            // porder升降序
            if (StringUtil.isNotBlank(commonParam.getCpid())) {
                LinkedHashMap<String, String> sortParams = new LinkedHashMap<String, String>();
                sortParams.put("porder", orderType == SQLConstants.SQL_ORDER_DESC ? "desc" : "asc");
                videoList = this.getWorkVideosRange(albumId, sortParams, null, start, end, null, commonParam);
            } else {
                videoList = videoMysqlDao.getVideoListByLimit(albumId, start, end, broadcastId, orderType);
            }
            break;
        case 3:
            // video_attr是否正片，porder正序
            if (StringUtil.isNotBlank(commonParam.getCpid())) {
                // List<WorksVideoInfo> worksVideoInfos =
                // this.videoTpDao.getWorksVideoListByAlbumId(albumId, null,
                // null, null, isPositive ?
                // String.valueOf(MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN) :
                // null);
                // videoList = MmsDataUtil.transferVideoData(worksVideoInfos);
                // if (!isPositive && null != videoList && videoList.size() > 0)
                // {
                // videoList = Lambda.filter(new Predicate<VideoMysqlTable>() {
                // public boolean apply(VideoMysqlTable item) {
                // return !item.isPositive();
                // }
                // },
                // videoList);
                // }
                LinkedHashMap<String, String> sortParams = new LinkedHashMap<String, String>();
                sortParams.put("porder", orderType == SQLConstants.SQL_ORDER_DESC ? "desc" : "asc");
                Map<String, String> filterParams = new HashMap<String, String>();
                filterParams.put("videoType", isPositive ? String.valueOf(MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN)
                        : "!" + MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN);
                videoList = this.getWorkVideosRange(albumId, sortParams, filterParams, start, end, null, commonParam);
            } else {
                videoList = videoMysqlDao.getVideoMysqlTableListByUpdateTime(albumId, isPositive, broadcastId);
            }
            break;
        case 4:
            // video_attr是否正片,porder、create_time正序，或porder、create_time倒序
            if (StringUtil.isNotBlank(commonParam.getCpid())) {
                // List<WorksVideoInfo> worksVideoInfos =
                // this.videoTpDao.getWorksVideoListByAlbumId(albumId, null,
                // "porder", orderType == SQLConstants.SQL_ORDER_DESC ? 1 : 0,
                // null);
                // videoList = MmsDataUtil.transferVideoData(worksVideoInfos);
                // if (!isPositive && null != videoList && videoList.size() > 0)
                // {
                // videoList = Lambda.filter(new Predicate<VideoMysqlTable>() {
                // public boolean apply(VideoMysqlTable item) {
                // return !item.isPositive();
                // }
                // },
                // videoList);
                // }
                LinkedHashMap<String, String> sortParams = new LinkedHashMap<String, String>();
                sortParams.put("porder", orderType == SQLConstants.SQL_ORDER_DESC ? "desc" : "asc");
                sortParams.put("time", orderType == SQLConstants.SQL_ORDER_DESC ? "desc" : "asc");
                Map<String, String> filterParams = new HashMap<String, String>();
                filterParams.put("videoType", isPositive ? String.valueOf(MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN)
                        : "!" + MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN);
                videoList = this.getWorkVideosRange(albumId, sortParams, filterParams, start, end, null, commonParam);
            } else {
                videoList = videoMysqlDao.getVideoListByPorderAndCreateTime(albumId, isPositive, orderType,
                        broadcastId, start, end);
            }
            break;
        case 5:
            // video_attr是否正片,porder、release_date正序，或porder、release_date、update_time倒序
            if (StringUtil.isNotBlank(commonParam.getCpid())) {
                // List<WorksVideoInfo> worksVideoInfos =
                // this.videoTpDao.getWorksVideoListByAlbumId(albumId, null,
                // "porder", orderType == SQLConstants.SQL_ORDER_DESC ? 1 : 0,
                // null);
                // videoList = MmsDataUtil.transferVideoData(worksVideoInfos);
                // if (!isPositive && null != videoList && videoList.size() > 0)
                // {
                // videoList = Lambda.filter(new Predicate<VideoMysqlTable>() {
                // public boolean apply(VideoMysqlTable item) {
                // return !item.isPositive();
                // }
                // },
                // videoList);
                // }
                LinkedHashMap<String, String> sortParams = new LinkedHashMap<String, String>();
                sortParams.put("porder", orderType == SQLConstants.SQL_ORDER_DESC ? "desc" : "asc");
                sortParams.put("time", orderType == SQLConstants.SQL_ORDER_DESC ? "desc" : "asc");
                Map<String, String> filterParams = new HashMap<String, String>();
                filterParams.put("videoType", isPositive ? String.valueOf(MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN)
                        : "!" + MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN);
                videoList = this.getWorkVideosRange(albumId, sortParams, filterParams, start, end, null, commonParam);
            } else {
                Long[] pids = new Long[] { albumId };
                videoList = videoMysqlDao.getVideoListByPids(pids, start, end, broadcastId, orderType, 1);
            }
        }
        return videoList;
    }

    @Override
    public Integer getVideoTotalNum(Long albumId, String queryType, CommonParam commonParam) {
        List<Long> idList = null;
        if (StringUtil.isNotBlank(commonParam.getCpid())) {
            // TODO: 作品库接口
            List<VideoMysqlTable> videoMysqlTables = this.getVideoRange(albumId, queryType, null, null, null,
                    commonParam, 1);
            return videoMysqlTables.size();
        } else {
            idList = videoMysqlDao.getAllIdByPid(albumId);
        }
        if (idList != null) {
            return idList.size();
        }
        return null;
    }

    @Override
    public AlbumVideoCacheStats getStats() {
        return null;
    }

    @Override
    public VideoMysqlTable getMinChargeVideoByPid(Long pid, String pay_platform, String play_platform,
            CommonParam commonParam) {
        if (StringUtil.isNotBlank(commonParam.getCpid())) {
            // TODO: 作品库接口
            Map<String, String> filterParams = new HashMap<String, String>();
            filterParams.put("videoType", String.valueOf(MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN));
            List<VideoMysqlTable> videoList = this.getWorkVideosRange(pid, null, filterParams, null, null, null,
                    commonParam);
            if (null != videoList) {
                // TODO: 获取付费类型匹配的第1条
                return VideoCommonUtil.filterByBroadcastId(videoList.get(0), commonParam.getBroadcastId());
            }
        } else {
            VideoMysqlTable video = videoMysqlDao.getMinChargeVideoByPid(pid, pay_platform, play_platform);
            return VideoCommonUtil.filterByBroadcastId(video, commonParam.getBroadcastId());
        }

        return null;
    }

    @Override
    public VideoMysqlTable getVideoByPidAndPorder(Long pid, int video_type, int porder, CommonParam commonParam) {
        if (StringUtil.isNotBlank(commonParam.getCpid())) {
            // TODO: [验证]作品库接口
            Map<String, String> filterParams = null;
            if (video_type > 0) {
                filterParams = new HashMap<String, String>();
                filterParams.put("videoType", String.valueOf(video_type));
                filterParams.put("porder", String.valueOf(porder));
            }
            List<VideoMysqlTable> videoList = this.getWorkVideosRange(pid, null, filterParams, null, null, null,
                    commonParam);
            if (null != videoList) {
                return VideoCommonUtil.filterByBroadcastId(videoList.get(0), commonParam.getBroadcastId());
            }
        } else {
            /*
             * VideoMysqlTable video =
             * videoCacheDao.getVideoByPidAndPorder(pid,video_type,porder,
             * null);
             * if (video == null && DB_SWITCH) {
             * video =
             * videoMysqlDao.getVideoByPidAndPorder(pid,video_type,porder);
             * if (video != null) {
             * videoCacheDao.setVideoByPidAndPorder(pid,video_type,porder, null,
             * video);
             * }
             * }
             */
            VideoMysqlTable video = videoMysqlDao.getVideoByPidAndPorder(pid, video_type, porder);
            return VideoCommonUtil.filterByBroadcastId(video, commonParam.getBroadcastId());
        }

        return null;
    }

    /**
     * 批量获取视频信息集合
     * @param ids
     * @param commonParam
     *            : 如果id中存在cpid的情况，则置其不为空！
     * @return
     */
    @Override
    public List<VideoMysqlTable> getVideoList(String[] ids, CommonParam commonParam) {
        List<String> dbIds = new ArrayList<String>();
        List<String> worksIds = new ArrayList<String>();
        LinkedHashMap<String, VideoMysqlTable> retList = StringUtil.isNotBlank(commonParam.getCpid()) ? new LinkedHashMap<String, VideoMysqlTable>()
                : null;
        List<VideoMysqlTable> videos = null;

        String[] idArrTmp = null;
        for (String id : ids) {
            idArrTmp = id.split("_");
            if (idArrTmp.length > 1) {
                worksIds.add(idArrTmp[0]);
            } else {
                dbIds.add(idArrTmp[0]);
            }
            if (null != retList) {
                retList.put(idArrTmp[0], null);
            }
        }

        if (dbIds.size() > 0) {
            videos = videoMysqlDao.getVideoByIds(ids);
            if (null != retList) {
                for (VideoMysqlTable video : videos) {
                    if (null != video) {
                        retList.put(String.valueOf(video.getId()), video);
                    }
                }
            }
        }

        if (worksIds.size() > 0) {
            // TODO: 作品库接口读取落缓存，目前上游不支持按vids批量获取视频
            if (null != retList) {
                for (VideoMysqlTable video : videos) {
                    if (null != video) {
                        retList.put(String.valueOf(video.getId()), video);
                    }
                }
            }
        }

        if (null != retList) {
            videos = new ArrayList(retList.entrySet());
        }

        return videos;
    }
}
