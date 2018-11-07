package com.letv.mas.caller.iptv.tvproxy.video.service;

import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoDataWrapper;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsAlbumInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsVideoInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsVideoList;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;
import com.letv.mas.caller.iptv.tvproxy.video.constants.MobileConstant;
import com.letv.mas.caller.iptv.tvproxy.video.util.MobileUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class LiveVideoService extends BaseService {
    public static final Logger log = Logger.getLogger(LiveVideoService.class);

    public AlbumVideoDataWrapper getVideoWrapper(Long videoId, CommonParam commonParam) {
        AlbumVideoDataWrapper videoWrapper = this.facadeCacheDao.getLiveVideoCacheDao().getVideoWrapper(videoId,
                commonParam);
        if (videoWrapper == null) {
            videoWrapper = getVideoWrapperFromVrs(videoId, commonParam);
            if (videoWrapper != null) {
                this.facadeCacheDao.getLiveVideoCacheDao().setVideoWrapper(videoId, commonParam, videoWrapper);
            }
        }

        return videoWrapper;
    }

    public AlbumVideoDataWrapper getAlbumWrapper(Long albumId, CommonParam commonParam) {
        AlbumVideoDataWrapper albumWrapper = this.facadeCacheDao.getLiveVideoCacheDao().getAlbumWrapper(albumId,
                commonParam);
        if (albumWrapper == null) {
            albumWrapper = getAlbumWrapperFromVrs(albumId, commonParam);
            if (albumWrapper != null) {
                this.facadeCacheDao.getLiveVideoCacheDao().setAlbumWrapper(albumId, commonParam, albumWrapper);
            }
        }

        return albumWrapper;
    }

    private AlbumVideoDataWrapper getVideoWrapperFromVrs(Long videoId, CommonParam commonParam) {
        AlbumVideoDataWrapper videoWrapper = new AlbumVideoDataWrapper();
        VideoMysqlTable videoMysqlTable = null;

        try {
            String region = commonParam.getSalesAreaTmp();
            if (StringUtil.isBlank(region)) {
                commonParam.getWcodeTmp();
            }
            String platform = MobileUtil.getPlatform(commonParam);
            MmsResponse<MmsVideoInfo> mmsResponse = this.facadeTpDao.getCommonVideoTpDao().getMmsVideoById(videoId,
                    platform, region, commonParam.getLangcode(), false);
            if (mmsResponse == null) {
                log.warn("getVideoWrapperFromVrs_" + videoId + "_" + region + "_" + platform + " response is null.");
                videoWrapper.setStatus(AlbumVideoDataWrapper.STATUS_NO_COPYRIGHT);
            } else if (mmsResponse.getData() == null) {
                if ("4001".equals(mmsResponse.getStatusCode())) {
                    log.warn("getVideoWrapperFromVrs_" + videoId + "_" + region + "_" + platform + " video is block.");
                    videoWrapper.setStatus(AlbumVideoDataWrapper.STATUS_BLOCKED);
                } else {
                    log.warn("getVideoWrapperFromVrs_" + videoId + "_" + region + "_" + platform + " video is null.");
                    videoWrapper.setStatus(AlbumVideoDataWrapper.STATUS_NOT_EXIST);
                }
            } else {
                videoMysqlTable = VideoCommonUtil.getVideoFromVrs(videoId, mmsResponse.getData());
                if ((videoMysqlTable != null && !MobileConstant.TERMINAL_PLATFORM_ALL.equalsIgnoreCase(platform))
                        && (videoMysqlTable.getPlay_platform() == null || videoMysqlTable.getPlay_platform().indexOf(
                                platform) == -1)) {
                    videoMysqlTable = null;
                    videoWrapper.setStatus(AlbumVideoDataWrapper.STATUS_NO_COPYRIGHT);
                }
                if (videoMysqlTable != null) {
                    videoMysqlTable.setPlay_streams(MobileUtil.sortVideoStreams(videoMysqlTable.getPlay_streams(),
                            MobileConstant.ALL_STREAMS));
                }
                videoWrapper.setVideo(videoMysqlTable);
            }
        } catch (Exception e) {
            log.info("getVideoWrapperFromVrs_" + videoId + "_" + commonParam.getP_devType() + "error:" + e.getMessage());
        }

        return videoWrapper;
    }

    private AlbumVideoDataWrapper getAlbumWrapperFromVrs(Long albumId, CommonParam commonParam) {
        AlbumVideoDataWrapper albumWrapper = new AlbumVideoDataWrapper();
        AlbumMysqlTable albumMysqlTable = null;

        try {
            String region = commonParam.getSalesAreaTmp();
            if (StringUtil.isBlank(region)) {
                commonParam.getWcodeTmp();
            }
            String platform = MobileUtil.getPlatform(commonParam);
            MmsResponse<MmsAlbumInfo> mmsResponse = this.facadeTpDao.getCommonVideoTpDao().getMmsAlbumById(albumId,
                    platform, commonParam.getLangcode(), region, false);
            if (mmsResponse == null) {
                log.warn("getAlbumWrapperFromVrs_" + albumId + "_" + region + "_" + platform + " response is null.");
                albumWrapper.setStatus(AlbumVideoDataWrapper.STATUS_NO_COPYRIGHT);
            } else if (mmsResponse.getData() == null) {
                if ("3001".equals(mmsResponse.getStatusCode())) {
                    log.warn("getAlbumWrapperFromVrs_" + albumId + "_" + region + "_" + platform + " album is block.");
                    albumWrapper.setStatus(AlbumVideoDataWrapper.STATUS_BLOCKED);
                } else {
                    log.warn("getAlbumWrapperFromVrs_" + albumId + "_" + region + "_" + platform + " album is null.");
                    albumWrapper.setStatus(AlbumVideoDataWrapper.STATUS_NOT_EXIST);
                }
            } else {
                albumMysqlTable = VideoCommonUtil.getAlbumFromVrs(albumId, mmsResponse.getData());
                if ((albumMysqlTable != null && !MobileConstant.TERMINAL_PLATFORM_ALL.equalsIgnoreCase(platform))
                        && (albumMysqlTable.getPlay_platform() == null || albumMysqlTable.getPlay_platform().indexOf(
                                platform) == -1)) {
                    albumMysqlTable = null;
                    albumWrapper.setStatus(AlbumVideoDataWrapper.STATUS_NO_COPYRIGHT);
                }
                if (albumMysqlTable != null) {
                    Long videoId = null;
                    Integer porder = -1;// 默认获取专辑下升序视频列表
                    // 综艺节目首播视频id应该是最新的一个
                    if (albumMysqlTable.getCategory() != null && albumMysqlTable.getCategory() == 11) {
                        porder = 1;// 综艺获取最新一个视频，取倒序
                    }
                    MmsResponse<MmsVideoList> videoListResponse = this.facadeTpDao.getCommonVideoTpDao()
                            .getVideoListByAlbumId(albumId, null, 1, 1, porder, platform, region,
                                    commonParam.getLangcode(), VideoTpConstant.QUERY_TYPE_POSITIVE);
                    if (videoListResponse == null || videoListResponse.getData() == null
                            || CollectionUtils.isEmpty(videoListResponse.getData().getVideoInfo())) {
                        log.info("getAlbumWrapperFromVrs_" + albumId + " get video list info failure.");
                    } else {
                        videoId = videoListResponse.getData().getVideoInfo().get(0).getPid();
                    }
                    albumMysqlTable.setVideoId(videoId);
                }
                albumWrapper.setAlbum(albumMysqlTable);
            }
        } catch (Exception e) {
            log.info("getAlbumWrapperFromVrs_" + albumId + "_" + commonParam.getP_devType() + "error:" + e.getMessage());
        }

        return albumWrapper;
    }
}
