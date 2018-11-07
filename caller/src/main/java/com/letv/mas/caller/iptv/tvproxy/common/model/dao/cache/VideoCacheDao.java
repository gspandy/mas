package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoDataWrapper;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache.dto.VideoCacheIndex;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.StatRankWeekData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.TpCallBack;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean.VRAlbum;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.TotalCountStatTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.TrailerVideoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GetPayChannelResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GetVideoReactionConfigResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsStore;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.WaterMarkImage;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.IptvVideoCacheInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MD5Util;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import serving.GenericDetailResponse;

import static com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoDataWrapper.STATUS_OK;

/**
 * 视频模块CacheDao，提供专辑、视频数据缓存读写接口，封装缓存Key值拼装逻辑；
 * 注意设计思路，这里仅封装操作缓存的逻辑，还是添加业务逻辑（如采用缓存+数据库等策略），需要考虑
 * @author KevinYi
 * @author liudaojie
 * @since 2016-03-14 仅封装操作缓存的逻辑
 */
@Component
public class VideoCacheDao extends BaseCacheDao {
    private static Logger logger = LoggerFactory.getLogger(VideoCacheDao.class);

    /**
     * 从缓存获取专辑
     * @param albumId
     *            专辑id
     * @param lang
     *            语言
     * @return
     */
    @Deprecated
    public AlbumMysqlTable getAlbum(Long albumId, String lang) {
        return getAlbumEx(albumId, lang, null);
    }

    /**
     * 从缓存获取专辑
     * @param albumId
     *            专辑id
     * @param lang
     *            语言
     * @param cpid
     *            内容方id
     * @return
     */
    @Deprecated
    public AlbumMysqlTable getAlbumEx(Long albumId, String lang, String cpid) {
        AlbumMysqlTable album = null;
        if (albumId != null) {
            album = this.cacheTemplate.get(this.getAlbumKey(albumId + "", lang, cpid), AlbumMysqlTable.class);
            if (album == null) {
                logger.info("getAlbumByIdAndLang_" + albumId + "_" + lang + ":Get album is null.");
            }
        }
        return album;
    }

    /**
     * 从缓存获取专辑
     * @param albumIds
     *            缓存专辑批量id
     * @param lang
     *            语言
     * @return
     */
    @Deprecated
    public Map<String, AlbumMysqlTable> getAlbums(List<Long> albumIds, String lang) {
        Map<String, AlbumMysqlTable> albumMap = null;
        if (albumIds != null && albumIds.size() > 0) {
            List<String> albumIdList = new ArrayList<String>();
            for (Long albumId : albumIds) {
                albumIdList.add(String.valueOf(albumId));
            }
            albumMap = getAlbumsEx(albumIdList, lang);
        }
        return albumMap;
    }

    /**
     * 从缓存获取专辑
     * @param albumIds
     *            缓存专辑批量id：albumId_cpid
     * @param lang
     *            语言
     * @return
     */
    @Deprecated
    public Map<String, AlbumMysqlTable> getAlbumsEx(List<String> albumIds, String lang) {
        Map<String, AlbumMysqlTable> albumMap = null;
        long begin = System.currentTimeMillis();

        if (albumIds != null && albumIds.size() > 0) {
            albumMap = new HashMap<String, AlbumMysqlTable>();
            Set<String> keySets = new HashSet<>();
            String key = null;
            for (String albumId : albumIds) {
                String[] albumIdArr = albumId.split("_");
                if (albumIdArr.length < 2) {
                    key = this.getAlbumKey(albumIdArr[0] + "", lang);
                } else {
                    key = this.getAlbumKey(albumIdArr[0] + "", lang, albumIdArr[1]);
                }
                if (StringUtil.isNotBlank(key)) {
                    keySets.add(key);
                }
                // if (keySets.size() == 10) {
                // mgetAlbums(keySets, albumMap);
                // keySets.clear();
                // }
            }
            if (keySets.size() > 0) {
                mgetAlbums(keySets, albumMap);
                keySets.clear();
            }
        }

        // System.out.println("getAlbumsEx(): timeCost=" +
        // (System.currentTimeMillis() - begin));
        return albumMap;
    }

    private void mgetAlbums(Set<String> keySets, Map<String, AlbumMysqlTable> albumMap) {
        if (keySets != null && keySets.size() > 0 && albumMap != null) {
            List<String> keys = new ArrayList<String>();
            keys.addAll(keySets);
            Map<String, AlbumMysqlTable> temp = this.cacheTemplate.mget(keys, AlbumMysqlTable.class, 10);
            keys.clear();
            if (temp != null) {
                for (AlbumMysqlTable value : temp.values()) {
                    if (value != null) {
                        albumMap.put(value.getId() + "", value);
                    }
                }
                temp.clear();
            }
        }
    }

    /**
     * 将专辑放入缓存
     * @param albumId
     *            专辑id
     * @param lang
     *            语言
     * @param album
     *            专辑
     */
    @Deprecated
    public void setAlbum(Long albumId, String lang, AlbumMysqlTable album) {
        if (albumId != null && album != null) {
            this.cacheTemplate.set(this.getAlbumKey(albumId + "", lang, album.getCoopPlatform()), album);
        }
    }

    /**
     * 批量插入缓存
     * @param albums
     *            专辑map
     * @param lang
     *            语言
     * @return
     */
    @Deprecated
    public void setAlbums(List<AlbumMysqlTable> albums, String lang) {
        if (albums == null || albums.size() == 0) {
            return;
        }
        Map<String, AlbumMysqlTable> albumMap = null;
        if (albums != null && albums.size() > 0) {
            albumMap = new HashMap<String, AlbumMysqlTable>();
            for (AlbumMysqlTable album : albums) {
                if (album != null) {
                    String key = this.getAlbumKey(album.getId() + "", lang, album.getCoopPlatform());
                    if (StringUtil.isNotBlank(key)) {
                        albumMap.put(key, album);
                    }
                    if (albumMap.size() == 10) {
                        msetAlbums(albumMap);
                        albumMap.clear();
                    }
                }
            }
            if (albumMap.size() > 0) {
                msetAlbums(albumMap);
                albumMap.clear();
            }
        }
    }

    private void msetAlbums(Map<String, AlbumMysqlTable> albumMap) {
        if (albumMap != null && albumMap.size() > 0) {
            this.cacheTemplate.mset(albumMap);
        }
    }

    /**
     * 批量插入缓存
     * @param videos
     *            视频map
     * @param lang
     *            语言
     * @return
     */
    @Deprecated
    public void setVideos(List<VideoMysqlTable> videos, String lang) {
        if (videos == null || videos.size() == 0) {
            return;
        }
        Map<String, VideoMysqlTable> videoMap = null;
        if (videos != null && videos.size() > 0) {
            videoMap = new HashMap<String, VideoMysqlTable>();
            for (VideoMysqlTable video : videos) {
                if (video != null) {
                    String key = this.getVideoKey(video.getId(), lang, video.getCoopPlatform());
                    if (StringUtil.isNotBlank(key)) {
                        videoMap.put(key, video);
                    }
                    if (videoMap.size() == 10) {
                        msetVideos(videoMap);
                        videoMap.clear();
                    }
                }
            }
            if (videoMap.size() > 0) {
                msetVideos(videoMap);
                videoMap.clear();
            }
        }
    }

    private void msetVideos(Map<String, VideoMysqlTable> videoMap) {
        if (videoMap != null && videoMap.size() > 0) {
            this.cacheTemplate.mset(videoMap);
        }
    }

    /**
     * 从缓存删除专辑
     * @param albumId
     *            专辑id
     * @param lang
     *            语言
     * @return
     */
    @Deprecated
    public int deleteAlbum(Long albumId, String lang) {
        return deleteAlbumEx(albumId, lang, null);
    }

    /**
     * 从缓存删除专辑
     * @param albumId
     *            专辑id
     * @param lang
     *            语言
     * @param cpid
     *            内容方id
     * @return
     */
    @Deprecated
    public int deleteAlbumEx(Long albumId, String lang, String cpid) {
        int result = CacheConstants.SUCCESS;
        if (albumId != null) {
            result = this.cacheTemplate.delete(this.getAlbumKey(albumId + "", lang, cpid));
        }
        return result;
    }

    /**
     * 从缓存获取视频
     * @param videoId
     *            视频id
     * @param lang
     *            语言
     * @return 缓存视频信息
     */
    @Deprecated
    public VideoMysqlTable getVideo(Long videoId, String lang) {
        return getVideoEx(videoId, lang, null);
    }

    /**
     * 从缓存获取视频
     * @param videoId
     *            视频id
     * @param lang
     *            语言
     * @param cpid
     *            内容方id
     * @return 缓存视频信息
     */
    @Deprecated
    public VideoMysqlTable getVideoEx(Long videoId, String lang, String cpid) {
        VideoMysqlTable video = null;
        if (videoId != null) {
            video = this.cacheTemplate.get(this.getVideoKey(videoId, lang, cpid), VideoMysqlTable.class);
            if (video == null) {
                logger.info("getVideoById_" + videoId + "_" + lang + ":Get video is null.");
            }
        }
        return video;
    }

    /**
     * 从缓存获取专辑
     * @param videoIds
     *            视频批量id
     * @param lang
     *            语言
     * @return
     */
    @Deprecated
    public Map<String, VideoMysqlTable> getVideos(List<Long> videoIds, String lang) {
        Map<String, VideoMysqlTable> videoMap = null;
        if (videoIds != null && videoIds.size() > 0) {
            List<String> videoIdList = new ArrayList<String>();
            for (Long videoId : videoIds) {
                videoIdList.add(String.valueOf(videoId));
            }
            videoMap = getVideosEx(videoIdList, lang);
        }
        return videoMap;
    }

    @Deprecated
    public Map<String, VideoMysqlTable> getVideosEx(List<String> videoIds, String lang) {
        Map<String, VideoMysqlTable> videoMap = null;
        long begin = System.currentTimeMillis();

        if (videoIds != null && videoIds.size() > 0) {
            videoMap = new HashMap<String, VideoMysqlTable>();
            Set<String> keySets = new HashSet<>();
            String key = null;
            for (String id : videoIds) {
                String[] videoIdArr = id.split("_");
                if (videoIdArr.length < 2) {
                    key = this.getVideoKey(Long.parseLong(videoIdArr[0]), lang);
                } else {
                    key = this.getVideoKey(Long.parseLong(videoIdArr[0]), lang, videoIdArr[1]);
                }
                if (StringUtil.isNotBlank(key)) {
                    keySets.add(key);
                }
                // if (keySets.size() == 10) {
                // mgetVideos(keySets, videoMap);
                // keySets.clear();
                // }
            }
            if (keySets.size() > 0) {
                mgetVideos(keySets, videoMap);
                keySets.clear();
            }
        }

        // System.out.println("getVideosEx(): timeCost=" +
        // (System.currentTimeMillis() - begin));
        return videoMap;
    }

    private void mgetVideos(Set<String> keySets, Map<String, VideoMysqlTable> videoMap) {
        if (keySets != null && keySets.size() > 0 && videoMap != null) {
            List<String> keys = new ArrayList<String>();
            keys.addAll(keySets);
            Map<String, VideoMysqlTable> temp = this.cacheTemplate.mget(keys, VideoMysqlTable.class, 10);
            keys.clear();
            if (temp != null) {
                for (VideoMysqlTable value : temp.values()) {
                    if (value != null) {
                        videoMap.put(value.getId() + "", value);
                    }
                }
                temp.clear();
            }
        }
    }

    @Deprecated
    public Map<Long, List<ChargeInfo>> getAlbumsChargeInfos(List<Long> albumIds) {
        Map<Long, List<ChargeInfo>> chargeInfoMap = null;

        if (albumIds != null && albumIds.size() > 0) {
            chargeInfoMap = new HashMap<>();
            List<String> albumChargeInfoCacheKey = new ArrayList<>();
            for (Long albumId : albumIds) {
                String cacheKey = this.convertAlbumIdToChargeInfoCacheKey(albumId);
                if (cacheKey != null) {
                    albumChargeInfoCacheKey.add(cacheKey);
                }
            }
            Map<String, List<ChargeInfo>> albumIdChargeInfoMap = this.cacheTemplate.mget(albumChargeInfoCacheKey,
                    new LetvTypeReference<List<ChargeInfo>>() {
                    });
            if (albumIdChargeInfoMap != null) {
                for (Map.Entry<String, List<ChargeInfo>> chargeInfos : albumIdChargeInfoMap.entrySet()) {
                    Long albumId = this.convertChargeInfoCacheKeyToAlbumId(chargeInfos.getKey());
                    if (albumId != null) {
                        chargeInfoMap.put(albumId, chargeInfos.getValue());
                    }
                }
            }
        }
        return chargeInfoMap;
    }

    /**
     * 批量添加多个album的chargeinfo缓存
     * @param chargeInfos
     */
    @Deprecated
    public void setAlbumsChargeInfos(Map<Long, List<ChargeInfo>> chargeInfos) {
        if (chargeInfos != null) {
            Map<String, List<ChargeInfo>> chargeInfoMap = new HashMap<>();
            for (Map.Entry<Long, List<ChargeInfo>> chargeInfo : chargeInfos.entrySet()) {
                String cacheKey = this.convertAlbumIdToChargeInfoCacheKey(chargeInfo.getKey());
                if (cacheKey != null && chargeInfo.getValue() != null) {
                    chargeInfoMap.put(cacheKey, chargeInfo.getValue());
                }
            }
            if (chargeInfoMap != null) {
                // TODO 缓存时间需要再考虑
                this.cacheTemplate.mset(chargeInfoMap);
            }
        }
    }

    /**
     * 添加某album的chargeinfo缓存
     * @param albumId
     * @param chargeInfos
     */
    public void setAlbumChargeInfos(Long albumId, List<ChargeInfo> chargeInfos) {
        if (chargeInfos != null) {
            String cacheKey = this.convertAlbumIdToChargeInfoCacheKey(albumId);
            // TODO 缓存时间需要再考虑
            this.cacheTemplate.set(cacheKey, chargeInfos);
        }
    }

    private String convertAlbumIdToChargeInfoCacheKey(Long albumId) {
        String cacheKey = null;
        if (albumId != null) {
            String cachePrefix = "album_chargeinfo_";
            cacheKey = cachePrefix + albumId;
        }
        return cacheKey;
    }

    private Long convertChargeInfoCacheKeyToAlbumId(String cacheKey) {
        Long albumId = null;
        String cachePrefix = "album_chargeinfo_";
        try {
            albumId = Long.valueOf(cacheKey.split(cachePrefix)[1]);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return albumId;
    }

    private String getVideoByPidAndPorderKey(Long pid, int video_type, int porder, String lang) {
        Assert.notNull(pid);
        String key = null;
        if (StringUtil.isBlank(lang)) {
            key = this.getCacheKey(CacheContentConstants.E_VIDEOMYSQLTABLE_ID, pid, "_", video_type, "_", porder);
        } else {
            key = this.getCacheKey(CacheContentConstants.E_VIDEOMYSQLTABLE_ID, lang, "_", pid, "_", video_type, "_",
                    porder);
        }
        if (StringUtil.isNotBlank(key)) {
            key = MD5Util.md5(key);
        }
        return key;
    }

    /**
     * 从缓存获取专辑下的第几集视频
     */
    public VideoMysqlTable getVideoByPidAndPorder(Long pid, int video_type, int porder, String lang) {
        VideoMysqlTable video = null;
        if (pid != null) {
            video = this.cacheTemplate.get(this.getVideoByPidAndPorderKey(pid, video_type, porder, lang),
                    VideoMysqlTable.class);
            if (video == null) {
                logger.info("getVideoByPidAndPorder_" + pid + "_" + video_type + "_" + porder + "_" + lang
                        + ":Get video is null.");
            }
        }
        return video;
    }

    public void setVideoByPidAndPorder(Long pid, int video_type, int porder, String lang, VideoMysqlTable video) {
        if (video != null) {
            this.cacheTemplate.set(this.getVideoByPidAndPorderKey(pid, video_type, porder, lang), video);
        }
    }

    /**
     * 将视频放入缓存
     * @param videoId
     *            视频id
     * @param lang
     *            语言
     * @param video
     *            视频
     */
    @Deprecated
    public void setVideo(Long videoId, String lang, VideoMysqlTable video) {
        if (videoId != null && video != null) {
            this.cacheTemplate.set(this.getVideoKey(videoId, lang, video.getCoopPlatform()), video);
        }
    }

    /**
     * 从缓存删除视频
     * @param videoId
     *            视频id
     * @param lang
     *            语言
     * @return
     */
    @Deprecated
    public int deleteVideo(Long videoId, String lang) {
        return deleteVideoEx(videoId, lang, null);
    }

    /**
     * 从缓存删除视频
     * @param videoId
     *            视频id
     * @param lang
     *            语言
     * @param cpid
     *            内容方id
     * @return
     */
    @Deprecated
    public int deleteVideoEx(Long videoId, String lang, String cpid) {
        int result = CacheConstants.SUCCESS;
        if (videoId != null) {
            result = this.cacheTemplate.delete(this.getVideoKey(videoId, lang, cpid));
        }
        return result;
    }

    /**
     * 从缓存获取水印信息
     * @param cid
     *            categoryId
     * @param pid
     *            专辑id
     * @return
     */
    public List<WaterMarkImage> getWaterMark(Integer cid, Long pid) {
        List<WaterMarkImage> waterMarkImageList = null;
        if (cid != null && pid != null) {
            waterMarkImageList = this.cacheTemplate.get(this.getWaterMarkKey(cid, pid),
                    new LetvTypeReference<List<WaterMarkImage>>() {
                    });
            if (waterMarkImageList == null) {
                logger.info("getWaterMark_" + cid + "_" + pid + ":Get data is null");
            }
        }
        return waterMarkImageList;
    }

    /**
     * 水印信息放入缓存，有过期时间
     * @param cid
     *            categoryId
     * @param pid
     *            专辑id
     * @param time
     *            过期时间
     * @param waterMarkImageList
     *            水印实体类
     * @return
     */
    // TODO liudaojie 待最新的TV版稳定后，这个功能可以去掉了
    public void setWaterMark(Integer cid, Long pid, List<WaterMarkImage> waterMarkImageList, Integer time) {
        if (cid != null && pid != null && waterMarkImageList != null) {
            if (time == null || time < 0) {
                time = 0;// 默认永久
            }
            this.cacheTemplate.set(this.getWaterMarkKey(cid, pid), waterMarkImageList, time);
        }
    }

    /**
     * 获取乐视儿童播放列表缓存
     * @param albumId
     *            专辑id
     * @return
     */
    public List<IptvVideoCacheInfoDto> getVideoChildPlayList(Long albumId) {
        List<IptvVideoCacheInfoDto> iptvVideoCacheInfoDtoList = null;
        if (albumId != null) {
            iptvVideoCacheInfoDtoList = this.cacheTemplate.get(
                    this.getCacheKey(CacheContentConstants.E_IptvVideoChildInfo_ID, albumId),
                    new LetvTypeReference<List<IptvVideoCacheInfoDto>>() {
                    });
            if (iptvVideoCacheInfoDtoList == null) {
                logger.info("getVideoChildPlayList_" + albumId + ":Get data is null");
            }
        }
        return iptvVideoCacheInfoDtoList;
    }

    /**
     * 将乐视儿童播放列表放入缓存
     * @param albumId
     *            专辑id
     * @param iptvVideoCacheInfoDtoList
     *            乐视儿童缓存实体
     * @return
     */
    public void setVideoChildPlayList(Long albumId, List<IptvVideoCacheInfoDto> iptvVideoCacheInfoDtoList) {
        if (albumId != null && iptvVideoCacheInfoDtoList != null) {
            this.cacheTemplate.set(this.getCacheKey(CacheContentConstants.E_IptvVideoChildInfo_ID, albumId),
                    iptvVideoCacheInfoDtoList);
        }
    }

    /**
     * 将乐视儿童播放列表放入缓存,有过期时间
     * @param albumId
     *            专辑id
     * @param iptvVideoCacheInfoDtoList
     *            乐视儿童缓存实体
     * @return
     */
    public void setVideoChildPlayList(Long albumId, List<IptvVideoCacheInfoDto> iptvVideoCacheInfoDtoList, int time) {
        if (albumId != null && iptvVideoCacheInfoDtoList != null) {
            this.cacheTemplate.set(this.getCacheKey(CacheContentConstants.E_IptvVideoChildInfo_ID, albumId),
                    iptvVideoCacheInfoDtoList, time);
        }
    }

    /**
     * 将乐视儿童播放列表从缓存中清除
     * @param albumId
     *            专辑id
     * @return
     */
    public int deleteVideoChildPlayList(Long albumId) {
        int result = CacheConstants.SUCCESS;
        if (albumId != null) {
            result = this.cacheTemplate
                    .delete(this.getCacheKey(CacheContentConstants.E_IptvVideoChildInfo_ID, albumId));
        }
        return result;
    }

    /**
     * 从缓存中获取“是否展示暂停广告页面”配置
     * @return
     */
    public Integer getShowPauseAdPage() {
        return this.cacheTemplate.get(CacheContentConstants.VIDEO_SHOW_PLAY_PAUSE_AD_PAGE, Integer.class);
    }

    /**
     * 从缓存中获取“是否展示浮层广告”配置
     * @return
     */
    public Integer getShowPlayFloatAd() {
        return this.cacheTemplate.get(CacheContentConstants.VIDEO_PLAY_FLOAT_AD_POLICY, Integer.class);
    }

    /**
     * 从缓存中获取播放token过期时间
     * 对于包含会员权益token信息的播放请求失效时长，单位-毫秒，0--永久有效，>0--固定秒数内有效，其他值--非法数据，默认永久有效
     * 播放接口会调用
     * @return
     */
    public Long getVideoPlayTokenExpireTime() {
        return this.cacheTemplate.get(CacheContentConstants.VIDEO_PLAY_TOKEN_EXPIRE_TIME_DEFAULT_KEY, Long.class);
    }

    /**
     * 获取专辑缓存key
     * @param albumId
     *            专辑id
     * @param lang
     *            语言
     * @return 视频缓存key
     */
    @Deprecated
    private String getAlbumKey(String albumId, String lang) {
        return getAlbumKey(albumId, lang, null);
    }

    /**
     * 获取专辑缓存key
     * @param albumId
     *            专辑id
     * @param lang
     *            语言
     * @param cpid
     *            内容方id
     * @return 视频缓存key
     */
    private String getAlbumKey(String albumId, String lang, String cpid) {
        Assert.notNull(albumId);
        String key = null;
        if (StringUtil.isBlank(lang)) {
            key = this.getCacheKey(CacheContentConstants.E_ALBUMMYSQLTABLE_ID, albumId);
        } else {
            key = this.getCacheKey(CacheContentConstants.E_ALBUMMYSQLTABLE_ID, lang, "_", albumId);
        }
        // if (StringUtil.isNotBlank(cpid)) {
        // key = key + "_" + cpid;
        // }
        return key;
    }

    /**
     * 获取视频缓存key
     * @param videoId
     *            视频id
     * @param lang
     *            语言
     * @return 视频缓存key
     */
    @Deprecated
    private String getVideoKey(Long videoId, String lang) {
        return getVideoKey(videoId, lang, null);
    }

    /**
     * 获取视频缓存key
     * @param videoId
     *            视频id
     * @param lang
     *            语言
     * @param cpid
     *            内容方id
     * @return 视频缓存key
     */
    @Deprecated
    private String getVideoKey(Long videoId, String lang, String cpid) {
        Assert.notNull(videoId);
        String key = null;
        if (StringUtil.isBlank(lang)) {
            key = this.getCacheKey(CacheContentConstants.E_VIDEOMYSQLTABLE_ID, videoId);
        } else {
            key = this.getCacheKey(CacheContentConstants.E_VIDEOMYSQLTABLE_ID, lang, "_", videoId);
        }
        // if (!StringUtil.isBlank(cpid)) {
        // key = key + "_" + cpid;
        // }
        return key;
    }

    /**
     * 获取水印缓存key
     * @param cid
     *            专辑分类id
     * @param pid
     *            专辑id
     * @return
     */
    private String getWaterMarkKey(Integer cid, Long pid) {
        return this.getCacheKey(CacheContentConstants.D_VIDEOMYSQLTABLE_WATERMARK_LIST, cid, "_", pid);
    }

    private String getCacheKey(Object... keys) {
        StringBuilder tmpKey = new StringBuilder();
        for (Object key : keys) {
            if (key != null) {
                tmpKey.append(key);
            }
        }
        return tmpKey.toString();
    }

    public GetVideoReactionConfigResponse getVideoReactionConfigData() {
        return this.cacheTemplate.get(CacheContentConstants.VIDEO_REACTION_CONFIG_DATA,
                GetVideoReactionConfigResponse.class);
    }

    public void setVideoReactionConfigData(GetVideoReactionConfigResponse response) {
        this.cacheTemplate.set(CacheContentConstants.VIDEO_REACTION_CONFIG_DATA, response,
                CommonConstants.SECONDS_OF_1_MINUTE);
    }

    /**
     * 获取频道付费信息
     * @return
     */
    public GetPayChannelResponse getPayChannel() {
        return this.cacheTemplate.get(CacheContentConstants.BOSS_PAY_CHANNEL_DATA, GetPayChannelResponse.class);
    }

    /**
     * 设置频道付费信息
     * @param response
     */
    public void setPayChannel(GetPayChannelResponse response) {
        this.cacheTemplate.set(CacheContentConstants.BOSS_PAY_CHANNEL_DATA, response,
                CommonConstants.SECONDS_OF_5_MINUTE);
    }

    /**
     * sarr 设置专辑信息
     * @param albumId
     * @param albumDetail
     * @param commonParam
     */
    public void setWebsiteAlbumInfo(String albumId, GenericDetailResponse albumDetail, CommonParam commonParam) {
        String key = this.getAlbumKey(albumId, commonParam.getLangcode());
        String mark_key = key + "_mark";
        String mark = this.cacheTemplate.get(mark_key, String.class);
        if (!CacheContentConstants.MARK_VALID_VALUE.equals(mark)) {
            this.cacheTemplate.set(mark_key, CacheContentConstants.MARK_VALID_VALUE,
                    CacheContentConstants.CACHE_EXPIRES_ONE_MONTH);
            this.cacheTemplate.set(key, albumDetail);
        }
    }

    /**
     * sarrs 获取专辑信息
     * @param albumId
     * @param commonParam
     * @return
     */
    public GenericDetailResponse getWebsiteAlbumInfo(String albumId, CommonParam commonParam) {
        String key = this.getAlbumKey(albumId, commonParam.getLangcode());
        return this.cacheTemplate.get(key, GenericDetailResponse.class);
    }

    public String getPauseImg() {
        return this.cacheTemplate.get(CacheContentConstants.VIDEO_PLAY_PAUSE_IMG, String.class);
    }

    /*
     * =============================================================
     * the following new methods for the hot data cache without DB
     * =============================================================
     */

    // redis cache statistics
    private AtomicLong albumHitCounter = new AtomicLong(0);
    private AtomicLong albumMissCounter = new AtomicLong(0);
    private AtomicLong videoHitCounter = new AtomicLong(0);
    private AtomicLong videoMissCounter = new AtomicLong(0);
    private AtomicLong videoListHitCounter = new AtomicLong(0);
    private AtomicLong videoListMissCounter = new AtomicLong(0);

    public AlbumVideoDataWrapper getAlbum(Long id, String region, String lang) {
        String key = VideoCommonUtil.getAlbumKey(id, region, lang);
        AlbumVideoDataWrapper album = cacheTemplate.get(key, AlbumVideoDataWrapper.class);
        if (album == null) {
            logger.info("[VideoCache]Album is NOT in the cache, key:" + key);
            albumMissCounter.incrementAndGet();
        } else {
            albumHitCounter.incrementAndGet();
        }
        return album;
    }

    public void setAlbum(Long id, String region, String lang, AlbumVideoDataWrapper album) {
        int timeout = album.getStatus() == STATUS_OK ? getTimeout() : getBlockedTimeout();
        cacheTemplate.set(VideoCommonUtil.getAlbumKey(id, region, lang), album, timeout);
    }

    public void deleteAlbum(Long id, String region, String lang) {
        cacheTemplate.delete(VideoCommonUtil.getAlbumKey(id, region, lang));
    }

    /**
     * 从缓存获取视频
     * @param id
     *            视频id
     * @param region
     *            地区
     * @param lang
     *            语言
     * @return 缓存视频信息
     */
    public AlbumVideoDataWrapper getVideo(Long id, String region, String lang) {
        String key = VideoCommonUtil.getVideoKey(id, region, lang);
        AlbumVideoDataWrapper video = cacheTemplate.get(key, AlbumVideoDataWrapper.class);
        if (video == null) {
            logger.info("[VideoCache]Video is NOT in the cache, key:" + key);
            videoMissCounter.incrementAndGet();
        } else {
            videoHitCounter.incrementAndGet();
        }
        return video;
    }

    /**
     * 将视频放入缓存
     * @param id
     *            视频id
     * @param region
     *            地区
     * @param lang
     *            语言
     * @param video
     *            缓存视频信息
     */
    public void setVideo(Long id, String region, String lang, AlbumVideoDataWrapper video) {
        int timeout = video.getStatus() == STATUS_OK ? getTimeout() : getBlockedTimeout();
        cacheTemplate.set(VideoCommonUtil.getVideoKey(id, region, lang), video, timeout);
    }

    public void deleteVideo(Long id, String region, String lang) {
        cacheTemplate.delete(VideoCommonUtil.getVideoKey(id, region, lang));
    }

    public List<VideoMysqlTable> getVideoList(Long albumId, String region, String langCode, String queryType,
            Integer page) {
        String key = VideoCommonUtil.getAlbumVideoListKey(albumId, region, langCode, queryType, page);
        List<VideoMysqlTable> videoList = cacheTemplate.get(key, new LetvTypeReference<List<VideoMysqlTable>>() {
        });
        if (videoList == null) {
            logger.info("[VideoCache]:Video list is NOT in the cache, key:" + key);
            videoListMissCounter.incrementAndGet();
        } else {
            videoListHitCounter.incrementAndGet();
        }
        return videoList;
    }

    public List<List<VideoMysqlTable>> getVideoLists(long albumId, String region, String langCode, String queryType,
            int start, int end) {
        List<String> keys = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            keys.add(VideoCommonUtil.getAlbumVideoListKey(albumId, region, langCode, queryType, i));
        }
        Map<String, List<VideoMysqlTable>> values = cacheTemplate.mget(keys,
                new LetvTypeReference<List<VideoMysqlTable>>() {
                });
        List<List<VideoMysqlTable>> results = new LinkedList<>();
        for (String key : keys) {
            if (values == null) {
                results.add(null);
                videoListMissCounter.incrementAndGet();
            } else {
                List<VideoMysqlTable> list = values.get(key);
                if (list != null) {
                    videoListHitCounter.incrementAndGet();
                } else {
                    videoListMissCounter.incrementAndGet();
                }
                results.add(list);
            }
        }
        return results;
    }

    public void setVideoList(Long albumId, String region, String langCode, String queryType, Integer page,
            List<VideoMysqlTable> videoList) {
        cacheTemplate.set(VideoCommonUtil.getAlbumVideoListKey(albumId, region, langCode, queryType, page), videoList,
                getTimeout());
    }

    public void deleteVideoList(Long albumId, String region, String langCode, String queryType, Integer page) {
        cacheTemplate.delete(VideoCommonUtil.getAlbumVideoListKey(albumId, region, langCode, queryType, page));
    }

    public Integer getAlbumPageInfo(Long albumId, String region, String langCode, String queryType) {
        String key = VideoCommonUtil.getAlbumPageKey(albumId, region, langCode, queryType);
        return cacheTemplate.get(key, Integer.class);
    }

    public void setAlbumPageInfo(Long albumId, String region, String langCode, String queryType, Integer totalVideo) {
        int timeout = Objects.equals(totalVideo, -1) ? getBlockedTimeout() : getTimeout();
        cacheTemplate.set(VideoCommonUtil.getAlbumPageKey(albumId, region, langCode, queryType), totalVideo, timeout);
    }

    public void deleteAlbumPageInfo(Long albumId, String region, String langCode, String queryType) {
        cacheTemplate.delete(VideoCommonUtil.getAlbumPageKey(albumId, region, langCode, queryType));
    }

    public Stats getStats() {
        return new Stats();
    }

    public TotalCountStatTpResponse getAlbumTotalCountStat(Long albumId, CommonParam commonParam) {
        return cacheTemplate.get(genAlbumTotalCountStatCacheKey(albumId, commonParam), TotalCountStatTpResponse.class);
    }

    public int setAlbumTotalCountStat(Long albumId, int duration, TotalCountStatTpResponse response,
            CommonParam commonParam) {
        return cacheTemplate.set(genAlbumTotalCountStatCacheKey(albumId, commonParam), response, duration);
    }

    public void deleteAlbumTotalCountStat(Long albumId, CommonParam commonParam) {
        cacheTemplate.delete(genAlbumTotalCountStatCacheKey(albumId, commonParam));
    }

    public Map<Long, TotalCountStatTpResponse> getAlbumTotalCountStats(List<Long> albumIds, CommonParam commonParam) {
        Map<Long, TotalCountStatTpResponse> ret = null;
        if (albumIds != null && albumIds.size() > 0) {
            List<String> keys = new ArrayList<String>();
            String key = null;
            for (Long albumId : albumIds) {
                key = this.genAlbumTotalCountStatCacheKey(albumId, commonParam);
                if (StringUtil.isNotBlank(key)) {
                    keys.add(key);
                }
            }
            if (keys.size() > 0) {
                Map<String, TotalCountStatTpResponse> cacheMap = this.cacheTemplate.mget(keys,
                        TotalCountStatTpResponse.class);
                if (cacheMap != null) {
                    ret = new HashMap<Long, TotalCountStatTpResponse>();
                    TotalCountStatTpResponse totalCountStatTpResponse = null;
                    for (Long albumId : albumIds) {
                        key = this.genAlbumTotalCountStatCacheKey(albumId, commonParam);
                        ret.put(albumId, cacheMap.get(key));
                    }
                    cacheMap.clear();
                }
                keys.clear();
            }
        }
        return ret;
    }

    public void setAlbumTotalCountStats(List<TotalCountStatTpResponse> totalCountStatTpResponses, int duration,
            CommonParam commonParam, TpCallBack tpCallBack) {
        Map<String, TotalCountStatTpResponse> cacheMap = null;
        if (totalCountStatTpResponses != null && totalCountStatTpResponses.size() > 0) {
            String key = null;
            cacheMap = new HashMap<String, TotalCountStatTpResponse>();
            for (TotalCountStatTpResponse totalCountStatTpResponse : totalCountStatTpResponses) {
                if (StringUtil.isNotBlank(totalCountStatTpResponse.getId())) {
                    key = this.genAlbumTotalCountStatCacheKey(Long.parseLong(totalCountStatTpResponse.getId()),
                            commonParam);
                    if (StringUtil.isNotBlank(key)) {
                        if (null != tpCallBack && !tpCallBack.canDo(totalCountStatTpResponse)) {
                            continue;
                        }
                        cacheMap.put(key, totalCountStatTpResponse);
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            if (cacheMap.size() > 0) {
                this.cacheTemplate.mset(cacheMap, duration);
                cacheMap.clear();
            }
        }
    }

    private String genAlbumTotalCountStatCacheKey(Long albumId, CommonParam commonParam) {
        return CacheContentConstants.VIDEO_ALBUM_TOTAL_COUNT_STAT_PREFIX
                + StringUtils.upperCase(commonParam.getTerminalApplication()) + "."
                + StringUtils.upperCase(commonParam.getSalesArea()) + "." + albumId;
    }

    private int getTimeout() {
        return StringUtil.toInteger(ApplicationUtils.get(ApplicationConstants.GLOBAL_VIDEO_CACHE_TIMEOUT),
                CommonConstants.SECONDS_OF_1_DAY * 15);
    }

    private int getBlockedTimeout() {
        return StringUtil.toInteger(ApplicationUtils.get(ApplicationConstants.GLOBAL_VIDEO_BLOCKED_CACHE_TIMEOUT),
                CommonConstants.SECONDS_OF_1_HOUR);
    }

    // redis cache statistics
    public class Stats {
        public final long albumHit = albumHitCounter.get();
        public final long albumMiss = albumMissCounter.get();
        public final long videoHit = videoHitCounter.get();
        public final long videoMiss = videoMissCounter.get();
        public final long videoListHit = videoListHitCounter.get();
        public final long videoListMiss = videoListMissCounter.get();
    }

    /**
     * Set mac white list to cache.
     * @param area
     * @param device
     * @param dataList
     */
    public void setMacWhiteList(String area, String device, List<String> dataList) {
        String key = "macwhitelist_" + StringUtils.trimToEmpty(area).toUpperCase() + "_"
                + StringUtils.trimToEmpty(device).toUpperCase();
        this.cacheTemplate.set(key, dataList, 120);
    }

    /**
     * Get mac white list from cache.
     * @param area
     * @param device
     * @return
     */
    public List<String> getMacWhiteList(String area, String device) {
        String key = "macwhitelist_" + StringUtils.trimToEmpty(area).toUpperCase() + "_"
                + StringUtils.trimToEmpty(device).toUpperCase();
        return this.cacheTemplate.get(key, new LetvTypeReference<List<String>>() {
        });
    }

    /**
     * 获取收银台的推荐列表缓存
     * @param albumId
     * @return
     */
    public VRAlbum getCashierAlbum(Integer albumId) {
        VRAlbum vrAlbum = null;
        if (albumId != null) {
            vrAlbum = this.cacheTemplate.get(
                    this.getCacheKey(CacheContentConstants.CASHIER_PAY_RECOMMEND_ALBUM_ID, albumId), VRAlbum.class);
            if (vrAlbum == null) {
                logger.info("getVRAlbum_" + albumId + ":Get data is null");
            }
        }
        return vrAlbum;
    }

    /**
     * 将收银台的推荐列表放入缓存
     * @param albumId
     * @param vrAlbum
     */
    public void setCashierAlbum(Integer albumId, VRAlbum vrAlbum) {
        if (albumId != null && vrAlbum != null) {
            this.cacheTemplate.set(this.getCacheKey(CacheContentConstants.CASHIER_PAY_RECOMMEND_ALBUM_ID, albumId),
                    vrAlbum);
        }
    }

    /**
     * 将收银台的推荐列表放入缓存
     * @param albumId
     * @param vrAlbum
     * @param time
     */
    public void setCashierAlbum(Integer albumId, VRAlbum vrAlbum, int time) {
        if (albumId != null && vrAlbum != null) {
            this.cacheTemplate.set(this.getCacheKey(CacheContentConstants.CASHIER_PAY_RECOMMEND_ALBUM_ID, albumId),
                    vrAlbum, time);
        }
    }

    /**
     * 将收银台的推荐列表从缓存中删除
     * @param albumId
     * @return
     */
    public int deleteCashierAlbum(Integer albumId) {
        int result = CacheConstants.SUCCESS;
        if (albumId != null) {
            result = this.cacheTemplate.delete(this.getCacheKey(CacheContentConstants.CASHIER_PAY_RECOMMEND_ALBUM_ID,
                    albumId));
        }
        return result;
    }

    public void deleteCache(String key) {
        cacheTemplate.delete(key);
    }

    /**
     * get pids for freeTemp
     * @return
     */
    public String getFreeTempPids(CommonParam commonParam) {
        String key = CacheContentConstants.FREE_TEMP_DATA_PREFIX;
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, String.class);
    }

    /**
     * set pids for freeTemp
     */
    public void setFreeTempPids(String pids, CommonParam commonParam) {
        String key = CacheContentConstants.FREE_TEMP_DATA_PREFIX;
        this.cacheTemplate.set(key, pids, CommonConstants.SECONDS_OF_10_MINUTE);
    }

    /**
     * 根据albumId获取缓存中最新统计的专辑StatRankWeekData信息
     * @param albumId
     * @return
     */
    public StatRankWeekData getLatestRankStatByAlbumId(String albumId) {
        StatRankWeekData statRankWeekData = null;
        if (StringUtil.isNotBlank(albumId)) {
            String key = CacheContentConstants.LATEST_STAT_RANK + albumId;
            statRankWeekData = this.cacheTemplate.get(key, StatRankWeekData.class);
        }
        return statRankWeekData;
    }

    /**
     * 根据albumId缓存StatRankWeekData信息，
     * 缓存时间10分钟
     * @param albumId
     * @param statRankWeekData
     */
    public void setLatestRankStatByAlbumId(String albumId, StatRankWeekData statRankWeekData) {
        if (StringUtil.isNotBlank(albumId)) {
            String key = CacheContentConstants.LATEST_STAT_RANK + albumId;
            this.cacheTemplate.set(key, statRankWeekData, CommonConstants.SECONDS_OF_10_MINUTE);
        }
    }

    /**
     * 根据categoryId获取缓存中最新统计的分类StatRankWeekData信息
     * @param categoryId
     * @return
     */
    public List<StatRankWeekData> getLatestRankStatListByCategoryId(String categoryId) {
        List<StatRankWeekData> statRankWeekDataList = null;
        if (StringUtil.isNotBlank(categoryId)) {
            String key = CacheContentConstants.LATEST_STAT_RANK + categoryId;
            statRankWeekDataList = this.cacheTemplate.get(key, List.class);
        }
        return statRankWeekDataList;
    }

    /**
     * 根据categoryId缓存StatRankWeekData信息，
     * 缓存时间10分钟
     * @param categoryId
     * @param statRankWeekDataList
     */
    public void setLatestRankStatListByCategoryId(String categoryId, List<StatRankWeekData> statRankWeekDataList) {
        if (StringUtil.isNotBlank(categoryId)) {
            String key = CacheContentConstants.LATEST_STAT_CATEGORY_RANK + categoryId;
            this.cacheTemplate.set(key, statRankWeekDataList, CommonConstants.SECONDS_OF_10_MINUTE);
        }
    }

    /**
     * get Trailer video
     * @return
     */
    public VideoMysqlTable getTrailerVideoMysqlTable(CommonParam commonParam) {
        String key = CacheContentConstants.TRAILER_VIDEO_DATA_VIDEOTABLE;
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, VideoMysqlTable.class);
    }

    /**
     * set Trailer video
     */
    public void setTrailerVideoMysqlTable(CommonParam commonParam, VideoMysqlTable videoMysqlTable) {
        String key = CacheContentConstants.TRAILER_VIDEO_DATA_VIDEOTABLE;
        this.cacheTemplate.set(key, videoMysqlTable, CommonConstants.SECONDS_OF_15_MINUTE);
    }

    /**
     * get Trailer video
     * @return
     */
    public VideoMysqlTable getTrailerVideoMysqlTableV2(CommonParam commonParam, Integer categoryId) {
        String key = CacheContentConstants.TRAILER_VIDEO_DATA_VIDEOTABLE + "_" + categoryId;
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, VideoMysqlTable.class);
    }

    /**
     * set Trailer video
     */
    public void setTrailerVideoMysqlTableV2(CommonParam commonParam, VideoMysqlTable videoMysqlTable, Integer categoryId) {
        String key = CacheContentConstants.TRAILER_VIDEO_DATA_VIDEOTABLE + "_" + categoryId;
        this.cacheTemplate.set(key, videoMysqlTable, CommonConstants.SECONDS_OF_15_MINUTE);
    }

    /**
     * get Trailer video
     * @return
     */
    public MmsStore getTrailerMmsStore(Long vid, Long mid, String videoType, Boolean expectTs, Integer splatId,
                                       String bsChannel, Integer broadcastId, CommonParam commonParam) {
        String key = this.getTrailerMmsStoreKey(vid, mid, videoType, expectTs, splatId, bsChannel, broadcastId);
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, MmsStore.class);
    }

    /**
     * set Trailer video
     */
    public void setTrailerMmsStore(Long vid, Long mid, String videoType, Boolean expectTs, Integer splatId,
            String bsChannel, Integer broadcastId, MmsStore mmsStore) {
        String key = this.getTrailerMmsStoreKey(vid, mid, videoType, expectTs, splatId, bsChannel, broadcastId);
        this.cacheTemplate.set(key, mmsStore, CommonConstants.SECONDS_OF_5_MINUTE);
    }

    public TrailerVideoDto getTrailerVideoDto(String clientRequestStream, Integer model, Boolean support3d,
                                              String supportStream, CommonParam commonParam) {
        String key = this.getTrailerVideoKey(clientRequestStream, model, support3d, supportStream, commonParam);
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, TrailerVideoDto.class);
    }

    /**
     * set Trailer video
     */
    public void setTrailerVideoDto(String clientRequestStream, Integer model, Boolean support3d, String supportStream,
            CommonParam commonParam, TrailerVideoDto videoDto) {
        String key = this.getTrailerVideoKey(clientRequestStream, model, support3d, supportStream, commonParam);
        this.cacheTemplate.set(key, videoDto, CommonConstants.SECONDS_OF_5_MINUTE);
    }

    /**
     * get Trailer video cache key
     */
    private String getTrailerVideoKey(String clientRequestStream, Integer model, Boolean support3d,
            String supportStream, CommonParam commonParam) {
        StringBuilder tmpKey = new StringBuilder();
        tmpKey.append(CacheContentConstants.TRAILER_VIDEO_DATA_VIDEODTO_PREFIX);
        /*
         * tmpKey.append(videoMysqlTable.getId());
         * tmpKey.append("_");
         */
        tmpKey.append(clientRequestStream);
        tmpKey.append("_");
        tmpKey.append(supportStream);
        tmpKey.append("_");
        tmpKey.append(model);
        tmpKey.append("_");
        tmpKey.append(support3d);
        String key = MD5Util.md5(tmpKey.toString());
        return key;
    }

    private String getTrailerMmsStoreKey(Long vid, Long mid, String videoType, Boolean expectTs, Integer splatId,
            String bsChannel, Integer broadcastId) {
        StringBuilder tmpKey = new StringBuilder();
        tmpKey.append(CacheContentConstants.TRAILER_VIDEO_DATA_MMS_PREFIX);
        tmpKey.append(vid);
        tmpKey.append("_");
        tmpKey.append(mid);
        tmpKey.append("_");
        tmpKey.append(videoType);
        tmpKey.append("_");
        tmpKey.append(expectTs);
        tmpKey.append("_");
        tmpKey.append(splatId);
        tmpKey.append("_");
        tmpKey.append(bsChannel);
        tmpKey.append("_");
        tmpKey.append(broadcastId);
        String key = tmpKey.toString();/* MD5Util.md5(tmpKey.toString()); */
        return key;
    }

    /**
     * 防盗链行为记录频控（R）
     * @return
     */
    public String getAntiReport(String devId, CommonParam commonParam) {
        String key = CacheContentConstants.ACT_REPORT_ANTI_REPLAY_KEY_PREFIX + devId;
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, String.class);
    }

    /**
     * 防盗链行为记录频控（W）
     * @return
     */
    public void setAntiReport(String devId, CommonParam commonParam) {
        String key = CacheContentConstants.ACT_REPORT_ANTI_REPLAY_KEY_PREFIX + devId;
        String mac = commonParam.getMac();
        if (StringUtil.isBlank(mac)) {
            mac = "";
        }
        this.cacheTemplate.set(key, mac, CommonConstants.SECONDS_OF_30_MINUTE);
    }

    /**
     * 获取专辑视频索引缓存集合
     * @param albumId
     *            专辑id
     * @return 视频索引缓存集合
     */
    public List<VideoCacheIndex> getAlbumVideoIndexes(String albumId, CommonParam commonParam) {
        String key = CacheContentConstants.E_ALBUM_VIDEOINDEXES_ID + albumId;
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, new LetvTypeReference<List<VideoCacheIndex>>() {
        });
    }

    /**
     * 保存专辑视频索引缓存集合
     * @param albumId
     * @param videoCacheIndexes
     */
    public void setAlbumVideoIndexes(String albumId, List<VideoCacheIndex> videoCacheIndexes) {
        String key = CacheContentConstants.E_ALBUM_VIDEOINDEXES_ID + albumId;
        this.cacheTemplate.set(key, videoCacheIndexes, getTimeout());
    }

}
