package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.io.IOException;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.MmsTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


/**
 * Transfer MMS data to *MysqlTable data format
 */
public class MmsDataUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MmsDataUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AlbumMysqlTable transferAlbumData(MmsAlbumInfo mmsAlbumInfo) {
        AlbumMysqlTable albumMysqlTable = new AlbumMysqlTable();
        try {
            albumMysqlTable = ParseUtil.copyBeanByParamsField(AlbumMysqlTable.class, albumMysqlTable, mmsAlbumInfo);
        } catch (Exception e) {
            LOGGER.error("Failed to copy fields to albumMysqlTable", e);
            return null;
        }
        if (albumMysqlTable == null) {
            return null;
        }
        if (mmsAlbumInfo.getPlayPlatform() == null || mmsAlbumInfo.getPlayPlatform().size() == 0) {
            albumMysqlTable.setPlay_platform(null);
        }
        if (mmsAlbumInfo.getIsPay() == null || mmsAlbumInfo.getIsPay() != 1 || mmsAlbumInfo.getPayPlatform() == null
                || mmsAlbumInfo.getPayPlatform().size() == 0) {
            // 2015-07-07与媒资后台同步，必须设置为付费且在boss后台勾选了付费平台的才算付费影片，否则将服务端对应数据付费平台清空
            albumMysqlTable.setPay_platform(null);
        }

        // for tvod icon type
        // v1
        // changeDataForTvod(albumMysqlTable, mmsAlbumInfo);
        // v2
        // rebuildData4ChargeInfo(albumMysqlTable, mmsAlbumInfo);

        // 专辑名称
        if (mmsAlbumInfo.getTvTitle() != null && mmsAlbumInfo.getTvTitle().trim().length() > 0) {
            albumMysqlTable.setName_cn(mmsAlbumInfo.getTvTitle());
        }
        if (mmsAlbumInfo.getPlayControlPlatformGuoguang() != null) {
            albumMysqlTable.setCibn(mmsAlbumInfo.getPlayControlPlatformGuoguang());
        }
        // 专辑表里的码流字段不需要赋值
        // setAlbumStream(albumMysqlTable, mmsAlbumInfo);

        // 2015-10-20 专题图片逻辑修改，添加动图逻辑
        Map<String, String> picCollections = mmsAlbumInfo.getPicCollections();
        String dynamicGraph = StringUtils.trimToNull(mmsAlbumInfo.getDynamicGraph());
        if (dynamicGraph != null) {
            if (picCollections == null) {
                picCollections = new HashMap<>();
            }
            picCollections.put(MmsTpConstant.MMS_ALBUM_DYNAMIC_GRAPH_FIELD_ANME, dynamicGraph);
        }
        if (picCollections != null) {
            try {
                String value = objectMapper.writeValueAsString(picCollections);
                albumMysqlTable.setPic_collections(value);
                albumMysqlTable.setPicMap(picCollections);
            } catch (Exception e) {
                LOGGER.error("Failed to writer value for picCollections", e);
            }
        }
        // 节目来源
        if (StringUtils.isBlank(albumMysqlTable.getRecord_company())) {
            albumMysqlTable.setRecord_company(mmsAlbumInfo.getrCompany());
        }
        // 时间
        albumMysqlTable.setCreate_time(TimeUtil.parseDate(mmsAlbumInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        albumMysqlTable.setUpdate_time(TimeUtil.parseDate(mmsAlbumInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
        albumMysqlTable
                .setVideoFollowTime(TimeUtil.parseDate(mmsAlbumInfo.getVideoFollowTime(), "yyyy-MM-dd HH:mm:ss"));
        albumMysqlTable.setPushflag((albumMysqlTable.getPlay_platform() != null && albumMysqlTable.getPlay_platform()
                .indexOf("420012") > 0) ? 0 : 1);
        albumMysqlTable.setAlbum_attr(albumMysqlTable.isPositive() ? 1 : 0);
        albumMysqlTable.setNowEpisodes(mmsAlbumInfo.getNowEpisodes());
        albumMysqlTable.setNowIssue(mmsAlbumInfo.getNowIssue());
        albumMysqlTable.setVarietyShow(mmsAlbumInfo.getVarietyShow());

        // itv_album_id
        if (albumMysqlTable.getItv_album_id() == null || albumMysqlTable.getItv_album_id() == 0) {
            albumMysqlTable.setItv_album_id(albumMysqlTable.getId());
        }

        parseAlbumInfo(albumMysqlTable);
        return albumMysqlTable;
    }

    public static AlbumMysqlTable transferAlbumData(WorksAlbumInfo worksAlbumInfo) {
        AlbumMysqlTable albumMysqlTable = new AlbumMysqlTable();
        try {
            albumMysqlTable = ParseUtil.copyBeanByParamsField(AlbumMysqlTable.class, albumMysqlTable, worksAlbumInfo);
        } catch (Exception e) {
            LOGGER.error("Failed to copy fields to albumMysqlTable", e);
            return null;
        }
        if (albumMysqlTable == null) {
            return null;
        }

        // 时间
        albumMysqlTable.setCreate_time(TimeUtil.parseDate(worksAlbumInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        albumMysqlTable.setUpdate_time(TimeUtil.parseDate(worksAlbumInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
        albumMysqlTable.setVideoFollowTime(TimeUtil.parseDate(worksAlbumInfo.getVideoFollowTime(),
                "yyyy-MM-dd HH:mm:ss"));
        albumMysqlTable.setPushflag((albumMysqlTable.getPlay_platform() != null && albumMysqlTable.getPlay_platform()
                .indexOf("420012") > 0) ? 0 : 1);
        albumMysqlTable.setAlbum_attr(albumMysqlTable.isPositive() ? 1 : 0);

        // itv_album_id
        if ((albumMysqlTable.getItv_album_id() == null || albumMysqlTable.getItv_album_id() == 0)
                && StringUtil.isNotBlank(worksAlbumInfo.getSubLeid())) {
            albumMysqlTable.setItv_album_id(Long.parseLong(worksAlbumInfo.getSubLeid()));
        }

        parseAlbumInfo(albumMysqlTable);
        return albumMysqlTable;
    }

    public static VideoMysqlTable transferVideoData(MmsVideoInfo mmsVideoInfo) {
        VideoMysqlTable videoMysqlTable = new VideoMysqlTable();
        try {
            videoMysqlTable = ParseUtil.copyBeanByParamsField(VideoMysqlTable.class, videoMysqlTable, mmsVideoInfo);
        } catch (Exception e) {
            LOGGER.error("Failed to copy fields to videoMysqlTable", e);
            return null;
        }
        if (videoMysqlTable == null) {
            return null;
        }
        if (mmsVideoInfo.getPlayPlatform() == null || mmsVideoInfo.getPlayPlatform().size() == 0) {
            videoMysqlTable.setPlay_platform(null);
        }
        // 视频名称
        if (mmsVideoInfo.getTvTitle() != null && mmsVideoInfo.getTvTitle().trim().length() > 0) {
            videoMysqlTable.setName_cn(mmsVideoInfo.getTvTitle());
        }
        if (StringUtil.isNotBlank(mmsVideoInfo.getSubTitle())) {
            videoMysqlTable.setSub_title(mmsVideoInfo.getSubTitle());
        }
        if (mmsVideoInfo.getPlayControlPlatformGuoguang() != null) {
            videoMysqlTable.setCibn(mmsVideoInfo.getPlayControlPlatformGuoguang());
        }
        // 图片
        if (mmsVideoInfo.getPicAll() != null) {
            try {
                String value = objectMapper.writeValueAsString(mmsVideoInfo.getPicAll());
                videoMysqlTable.setPic_all(value);
                videoMysqlTable.setPicMap(mmsVideoInfo.getPicAll());
            } catch (Exception e) {
                LOGGER.error("Failed to write value for PicAll", e);
            }
        }

        // 时间
        videoMysqlTable.setCreate_time(TimeUtil.parseDate(mmsVideoInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        videoMysqlTable.setUpdate_time(TimeUtil.parseDate(mmsVideoInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
        videoMysqlTable.setPushflag((videoMysqlTable.getPlay_platform() != null && videoMysqlTable.getPlay_platform()
                .indexOf("420012") > 0) ? 0 : 1);
        videoMysqlTable.setLogonum(mmsVideoInfo.getLogoNum() == null ? 0 : 1);
        if (!CollectionUtils.isEmpty(mmsVideoInfo.getDrmFlag())) {
            videoMysqlTable.setDrmFlagId(mmsVideoInfo.getDrmFlag().keySet().toString());
        }

        // 2016-04-01媒资返回数据错误，key值返回"nul"，暂取消自动解析，改为代码解析
        Map<String, String> style = mmsVideoInfo.getStyle();
        if (style != null) {
            Set<Map.Entry<String, String>> styleEntrySet = style.entrySet();
            if (!CollectionUtils.isEmpty(styleEntrySet)) {
                for (Map.Entry<String, String> styleEntry : styleEntrySet) {
                    Integer styleKey = StringUtil.toInteger(styleEntry.getKey(), null);
                    if (styleKey != null && styleKey > 0) {
                        videoMysqlTable.setStyle(styleKey);
                        videoMysqlTable.setStyle_name(styleEntry.getValue());
                        break;
                    }
                }
            }
        }
        // 2015-10-03,设置TV首次上映时间
        Map<String, String> platformFirstOnTime = mmsVideoInfo.getPlatformFirstOnTime();
        if (!CollectionUtils.isEmpty(platformFirstOnTime)) {
            // CommonConstants.TV_PLAY_PLAT_FROM
            videoMysqlTable.setTvFirstOnTime(platformFirstOnTime.get(CommonConstants.TV_PLAY_PLAT_FROM));
        }
        setVideoStream(videoMysqlTable, mmsVideoInfo);
        parseVideoInfo(videoMysqlTable);
        return videoMysqlTable;
    }

    public static VideoMysqlTable transferVideoData(WorksVideoInfo worksVideoInfo) {
        VideoMysqlTable videoMysqlTable = new VideoMysqlTable();
        try {
            videoMysqlTable = ParseUtil.copyBeanByParamsField(VideoMysqlTable.class, videoMysqlTable, worksVideoInfo);
        } catch (Exception e) {
            LOGGER.error("Failed to copy fields to videoMysqlTable", e);
            return null;
        }
        if (videoMysqlTable == null) {
            return null;
        }
        if (StringUtil.isBlank(worksVideoInfo.getPlayPlatform())) {
            videoMysqlTable.setPlay_platform(null);
        }

        // 时间
        videoMysqlTable.setCreate_time(TimeUtil.parseDate(worksVideoInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        videoMysqlTable.setUpdate_time(TimeUtil.parseDate(worksVideoInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
        videoMysqlTable.setPushflag((videoMysqlTable.getPlay_platform() != null && videoMysqlTable.getPlay_platform()
                .indexOf("420012") > 0) ? 0 : 1);

        parseVideoInfo(videoMysqlTable);
        return videoMysqlTable;
    }

    public static List<VideoMysqlTable> transferVideoData(List<WorksVideoInfo> worksVideoInfos) {
        List<VideoMysqlTable> videoMysqlTables = null;
        VideoMysqlTable videoMysqlTable = null;

        if (null != worksVideoInfos && worksVideoInfos.size() > 0) {
            videoMysqlTables = new ArrayList<VideoMysqlTable>();
        }

        for (WorksVideoInfo worksVideoInfo : worksVideoInfos) {
            videoMysqlTable = MmsDataUtil.transferVideoData(worksVideoInfo);
            if (null != videoMysqlTable) {
                videoMysqlTables.add(videoMysqlTable);
            }
        }

        return videoMysqlTables;
    }

    public static VideoMysqlTable transferVideoBriefData(MmsVideoInfo mmsVideoInfo) {
        VideoMysqlTable videoMysqlTable = new VideoMysqlTable();
        videoMysqlTable.setId(mmsVideoInfo.getId());
        videoMysqlTable.setPid(mmsVideoInfo.getPid());
        videoMysqlTable.setName_cn(mmsVideoInfo.getNameCn());
        videoMysqlTable.setSub_title(mmsVideoInfo.getSubTitle());
        videoMysqlTable.setCategory(mmsVideoInfo.getCategory().keySet().iterator().next());
        videoMysqlTable.setDuration(mmsVideoInfo.getDuration());
        videoMysqlTable.setPorder(mmsVideoInfo.getPorder());
        videoMysqlTable.setEpisode(mmsVideoInfo.getEpisode());
        videoMysqlTable.setDescription(mmsVideoInfo.getDescription());
        videoMysqlTable.setRelease_date(mmsVideoInfo.getReleaseDate());
        Map<Integer, String> guest = mmsVideoInfo.getGuest();
        if (!CollectionUtils.isEmpty(guest)) {
            StringBuilder sb = new StringBuilder();
            for (String g : guest.values()) {
                if (sb.length() == 0) {
                    sb.append(g);
                } else {
                    sb.append(",").append(g);
                }
            }
            videoMysqlTable.setGuest(sb.toString());
        }
        Map<String, String> playPlatform = mmsVideoInfo.getPlayPlatform();
        if (!CollectionUtils.isEmpty(playPlatform)) {
            StringBuilder sb = new StringBuilder();
            for (String pp : playPlatform.keySet()) {
                if (sb.length() == 0) {
                    sb.append(pp);
                } else {
                    sb.append(",").append(pp);
                }
            }
            videoMysqlTable.setPlay_platform(sb.toString());
        }
        Map<String, String> payPlatform = mmsVideoInfo.getPayPlatform();
        if (!CollectionUtils.isEmpty(payPlatform)) {
            StringBuilder sb = new StringBuilder();
            for (String pp : payPlatform.keySet()) {
                if (sb.length() == 0) {
                    sb.append(pp);
                } else {
                    sb.append(",").append(pp);
                }
            }
            videoMysqlTable.setPay_platform(sb.toString());
        } else {
            videoMysqlTable.setPay_platform("");
        }
        Map<Integer, String> videoType = mmsVideoInfo.getVideoType();
        if (!CollectionUtils.isEmpty(videoType)) {
            Iterator<Integer> iterator = videoType.keySet().iterator();
            videoMysqlTable.setVideo_type(iterator.next());
        }
        if (mmsVideoInfo.getPlayPlatform() == null || mmsVideoInfo.getPlayPlatform().size() == 0) {
            videoMysqlTable.setPlay_platform(null);
        }
        videoMysqlTable.setTrans_code_prefix(mmsVideoInfo.getTransCodePrefix());
        // 视频名称
        if (mmsVideoInfo.getTvTitle() != null && mmsVideoInfo.getTvTitle().trim().length() > 0) {
            videoMysqlTable.setName_cn(mmsVideoInfo.getTvTitle());
        }
        if (mmsVideoInfo.getPlayControlPlatformGuoguang() != null) {
            videoMysqlTable.setCibn(mmsVideoInfo.getPlayControlPlatformGuoguang());
        }
        // 图片
        if (mmsVideoInfo.getPicAll() != null) {
            try {
                String picAll = objectMapper.writeValueAsString(mmsVideoInfo.getPicAll());
                videoMysqlTable.setPic_all(picAll);
            } catch (Exception e) {
                LOGGER.error("Failed to write value for picAll", e);
            }
        }

        // 时间
        videoMysqlTable.setCreate_time(TimeUtil.parseDate(mmsVideoInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        videoMysqlTable.setUpdate_time(TimeUtil.parseDate(mmsVideoInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
        videoMysqlTable.setPushflag((videoMysqlTable.getPlay_platform() != null && videoMysqlTable.getPlay_platform()
                .indexOf("420012") > 0) ? 0 : 1);
        videoMysqlTable.setLogonum(mmsVideoInfo.getLogoNum() == null ? 0 : 1);
        if (!CollectionUtils.isEmpty(mmsVideoInfo.getDrmFlag())) {
            videoMysqlTable.setDrmFlagId(mmsVideoInfo.getDrmFlag().keySet().toString());
        }

        // 2016-04-01媒资返回数据错误，key值返回"nul"，暂取消自动解析，改为代码解析
        Map<String, String> style = mmsVideoInfo.getStyle();
        if (style != null) {
            for (Map.Entry<String, String> styleEntry : style.entrySet()) {
                Integer styleKey = StringUtil.toInteger(styleEntry.getKey(), null);
                if (styleKey != null && styleKey > 0) {
                    videoMysqlTable.setStyle(styleKey);
                    videoMysqlTable.setStyle_name(styleEntry.getValue());
                    break;
                }
            }
        }
        // 2015-10-03,设置TV首次上映时间
        Map<String, String> platformFirstOnTime = mmsVideoInfo.getPlatformFirstOnTime();
        if (!CollectionUtils.isEmpty(platformFirstOnTime)) {
            // CommonConstants.TV_PLAY_PLAT_FROM
            videoMysqlTable.setTvFirstOnTime(platformFirstOnTime.get(CommonConstants.TV_PLAY_PLAT_FROM));
        }
        setVideoStream(videoMysqlTable, mmsVideoInfo);
        parseVideoInfo(videoMysqlTable);
        return videoMysqlTable;
    }

    private static void setVideoStream(VideoMysqlTable videoMysqlTable, MmsVideoInfo mmsVideoInfo) {
        Set<String> playStreams = new HashSet<>();
        Map<Integer, String> midStreamsMap = new HashMap<>();// 媒资id对应的码流信息，以防止多个媒资id的时候播放找不到媒资id的问题
        Map<Integer, List<MmsVideoCode>> videoCode = mmsVideoInfo.getVideocode();
        if (videoCode != null) {
            for (Map.Entry<Integer, List<MmsVideoCode>> entry : videoCode.entrySet()) {
                Set<String> midStreams = new HashSet<>();
                if (entry.getValue() != null) {
                    for (MmsVideoCode mmsVideoCode : entry.getValue()) {
                        String status = mmsVideoCode.getStatus();
                        if (status == null || !status.contains("300006")) {// 没有发布成功的不保存
                            continue;
                        }
                        Map<Integer, String> code = mmsVideoCode.getCode();
                        if (code != null && !code.isEmpty()) {
                            for (String stream : code.values()) {
                                if (stream.startsWith("mp4") || stream.startsWith("dolby_vision")) {
                                    String filteredStream = VideoCommonUtil.filterValidStream(stream,
                                            VideoCommonUtil.FILTER_VALID_STARTM_MODE_SAVE);
                                    if (filteredStream != null) {
                                        playStreams.add(filteredStream);
                                        midStreams.add(filteredStream);
                                    }
                                }
                            }
                        }
                    }
                }
                midStreamsMap.put(entry.getKey(), getStreamsStr(midStreams));
            }
        }

        videoMysqlTable.setPlay_streams(getStreamsStr(playStreams));
        try {
            String value = objectMapper.writeValueAsString(midStreamsMap);
            videoMysqlTable.setMid_streams(value);
        } catch (IOException e) {
            LOGGER.error("Failed to write value for midStreamsMap", e);
        }
    }

    private static void parseVideoInfo(VideoMysqlTable videoMysqlTable) {
        if (videoMysqlTable == null) {
            return;
        }
        if (!videoMysqlTable.isTVCopyright()) {
            return;
        }
        // 所属专辑信息
        videoMysqlTable.setItv_album_id(videoMysqlTable.getPid());

        // 合并原vrs相应字段信息
        videoMysqlTable.initSplitProperty();

        // 视频是否为正片
        if (videoMysqlTable.isPositive()) {
            videoMysqlTable.setVideo_attr(1);
        } else {
            videoMysqlTable.setVideo_attr(0);
        }

        // 媒资id,去除首尾","
        if (videoMysqlTable.getMid() != null) {
            String mid = videoMysqlTable.getMid();
            int begin = 0, end = 0;
            if (mid.startsWith(",")) {
                begin = 1;
            }
            if (mid.endsWith(",") && mid.length() > 1) {
                end = mid.length() - 1;
            }
            if (begin > 0 || end > 0) {
                videoMysqlTable.setMid(mid.substring(begin, end));
            }
        }

        // 原video_info_id
        if (videoMysqlTable.getVideo_info_id() == null || videoMysqlTable.getVideo_info_id() == 0) {
            videoMysqlTable.setVideo_info_id(videoMysqlTable.getId());
        }

        if (videoMysqlTable.getCntv() == null) {
            videoMysqlTable.setCntv(1);
        }
        if (videoMysqlTable.getWasu() == null) {
            videoMysqlTable.setWasu(0);
        }
        if (videoMysqlTable.getCibn() == null) {
            videoMysqlTable.setCibn(0);
        }
        if (videoMysqlTable.getStatus() == null) {
            videoMysqlTable.setStatus(1);
        }
        // 分类
        videoMysqlTable.setCategory_name_all(videoMysqlTable.getCategory_name());
    }

    private static void parseAlbumInfo(AlbumMysqlTable albumMysqlTable) {
        if (albumMysqlTable == null) {
            return;
        }
        if (!albumMysqlTable.isTVCopayright()) {
            return;
        }

        // 初始化vrs拆分的参数
        albumMysqlTable.initSplitProperty();

        if (albumMysqlTable.getCntv() == null) {
            albumMysqlTable.setCntv(1);
        }
        if (albumMysqlTable.getWasu() == null) {
            albumMysqlTable.setWasu(0);
        }
        if (albumMysqlTable.getCibn() == null) {
            albumMysqlTable.setCibn(0);
        }
        if (albumMysqlTable.getStatus() == null) {
            albumMysqlTable.setStatus(1);
        }
        // TV版收费
        if (albumMysqlTable.isTVPay()) {
            albumMysqlTable.setIsyuanxian(1);
        } else {
            albumMysqlTable.setIsyuanxian(0);
        }
        // 分类
        albumMysqlTable.setCategory_name_all(albumMysqlTable.getCategory_name());
    }

    private static String getStreamsStr(Set<String> streams) {
        if (streams == null || streams.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String s : streams) {
            if (builder.length() > 0) {
                builder.append(",").append(s);
            } else {
                builder.append(s);
            }
        }
        return builder.toString();
    }

    /**
     * TODO: 专辑－分端付费配置
     * @param albumMysqlTable
     * @param mmsAlbumInfo
     */
    public static void changeDataForTvod(AlbumMysqlTable albumMysqlTable, MmsAlbumInfo mmsAlbumInfo) {
        if (albumMysqlTable != null && StringUtil.isNotBlank(albumMysqlTable.getPay_platform())) {
            // String[] payPlatforms =
            // albumMysqlTable.getPay_platform().split(",");
            // StringBuilder sb = new StringBuilder();
            // if (null != payPlatforms && payPlatforms.length > 0) {
            // for (int i = 0, len = payPlatforms.length; i < len; i++) {
            //
            // }
            // }
            if (albumMysqlTable.getPay_platform().indexOf(";") == -1) {
                if (mmsAlbumInfo.getIsCoupon() != null && mmsAlbumInfo.getIsPay() != null
                        && mmsAlbumInfo.getPayType() != null) {
                    albumMysqlTable.setPay_platform(albumMysqlTable.getPay_platform() + ";" + mmsAlbumInfo.getIsPay()
                            + ";" + mmsAlbumInfo.getPayType() + ";" + mmsAlbumInfo.getIsCoupon());
                }
            }
        }
    }

    /**
     * TODO: 视频－分端付费配置
     * @param videoMysqlTable
     * @param mmsVideoInfo
     */
    public static void changeVideoDataForTvod(VideoMysqlTable videoMysqlTable, MmsVideoInfo mmsVideoInfo) {
        if (videoMysqlTable != null && StringUtil.isNotBlank(videoMysqlTable.getPay_platform())) {
            if (videoMysqlTable.getPay_platform().indexOf(";") == -1) {
                if (mmsVideoInfo.getIsCoupon() != null && mmsVideoInfo.getIsPay() != null
                        && mmsVideoInfo.getPayType() != null) {
                    videoMysqlTable.setPay_platform(videoMysqlTable.getPay_platform() + ";" + mmsVideoInfo.getIsPay()
                            + ";" + mmsVideoInfo.getPayType() + ";" + mmsVideoInfo.getIsCoupon());
                }
            }
        }
    }

    public static boolean rebuildData4ChargeInfos(Object objMysqlTable, List<ChargeInfo> chargeInfos) {
        boolean ret = true;
        for (int i = 0, len = chargeInfos.size(); i < len; i++) {
            if (!rebuildData4ChargeInfo(objMysqlTable, chargeInfos.get(i))) {
                ret = false;
            }
        }
        return ret;
    }

    public static boolean rebuildData4ChargeInfo(Object objMysqlTable, Object objInfo) {
        boolean ret = false;
        AlbumMysqlTable albumMysqlTable = null;
        VideoMysqlTable videoMysqlTable = null;
        if (null != objMysqlTable && objMysqlTable instanceof AlbumMysqlTable) {
            albumMysqlTable = (AlbumMysqlTable) objMysqlTable;
        } else if (null != objMysqlTable && objMysqlTable instanceof VideoMysqlTable) {
            videoMysqlTable = (VideoMysqlTable) objMysqlTable;
        } else {
            return ret;
        }

        if (albumMysqlTable != null) {
            albumMysqlTable
                    .setPay_platform(attachChargeInfo(albumMysqlTable.getPay_platform(), genChargeInfo(objInfo)));
            ret = true;
        } else if (videoMysqlTable != null) {
            videoMysqlTable
                    .setPay_platform(attachChargeInfo(videoMysqlTable.getPay_platform(), genChargeInfo(objInfo)));
            ret = true;
        }
        return ret;
    }

    private static ChargeInfo genChargeInfo(Object objInfo) {
        ChargeInfo chargeInfo = new ChargeInfo();
        MmsAlbumInfo mmsAlbumInfo = null;
        MmsVideoInfo mmsVideoInfo = null;
        if (null != objInfo && objInfo instanceof MmsAlbumInfo) {
            mmsAlbumInfo = (MmsAlbumInfo) objInfo;
            chargeInfo.setPid(String.valueOf(mmsAlbumInfo.getId()));
            if (null != mmsAlbumInfo.getPayPlatform()) {
                StringBuilder sb = new StringBuilder();
                Iterator iter = mmsAlbumInfo.getPayPlatform().entrySet().iterator();
                Map.Entry entry = null;
                while (iter.hasNext()) {
                    entry = (Map.Entry) iter.next();
                    sb.append(entry.getKey()).append(",");
                }
                if (sb.length() > 0) { // remove the last ","
                    sb.replace(sb.length() - 1, sb.length(), "");
                }
                chargeInfo.setChargePlatform(sb.toString());
            }
            chargeInfo.setIsCharge(String.valueOf(mmsAlbumInfo.getIsPay()));
            if (null != mmsAlbumInfo.getPayType()) {
                chargeInfo.setChargeType(formatPayType(String.valueOf(mmsAlbumInfo.getPayType())));
            }
            chargeInfo.setIscoupon(String.valueOf(mmsAlbumInfo.getIsCoupon()));
        } else if (null != objInfo && objInfo instanceof MmsVideoInfo) {
            mmsVideoInfo = (MmsVideoInfo) objInfo;
            chargeInfo.setPid(String.valueOf(mmsVideoInfo.getId()));
            if (null != mmsVideoInfo.getPayPlatform()) {
                StringBuilder sb = new StringBuilder();
                Iterator iter = mmsVideoInfo.getPayPlatform().entrySet().iterator();
                Map.Entry entry = null;
                while (iter.hasNext()) {
                    entry = (Map.Entry) iter.next();
                    sb.append(entry.getKey()).append(",");
                }
                if (sb.length() > 0) { // remove the last ","
                    sb.replace(sb.length() - 1, sb.length(), "");
                }
                chargeInfo.setChargePlatform(sb.toString());
            }
            chargeInfo.setIsCharge(String.valueOf(mmsVideoInfo.getIsPay()));
            if (null != mmsVideoInfo.getPayType()) {
                chargeInfo.setChargeType(formatPayType(mmsVideoInfo.getPayType()));
            }
            chargeInfo.setIscoupon(String.valueOf(mmsVideoInfo.getIsCoupon()));
        } else if (null != objInfo && objInfo instanceof ChargeInfo) {
            chargeInfo = (ChargeInfo) objInfo;
        }
        return chargeInfo;
    }

    public static String formatPayType(String payType) {
        String ret = payType;

        if (payType.startsWith("86")) { // v1 => v2
            if (payType.equals("860001")) {
                ret = "0";
            } else if (payType.equals("860002")) {
                ret = "1";
            } else if (payType.equals("860003")) {
                ret = "2";
            }
        } else {
            if (payType.equals("0")) { // v2 => v1
                ret = "860001";
            } else if (payType.equals("1")) {
                ret = "860002";
            } else if (payType.equals("2")) {
                ret = "860003";
            }
        }

        return ret;
    }

    /**
     * TODO: 目前db针对IPTV_ALBUM_INFO.pay_platform（vchar255）针对各个终端平台做了部分付费策略配置存储，
     * 长期策略得增加chargeInfo（text）字段来保存全部配置属性！
     * 统计合成平台付费策略配置：pay_platform:getIsPay;getPayType;getIsCoupon,
     * pay_platform:getIsPay;getPayType;getIsCoupon...
     * @param pay_platform
     * @param chargeInfo
     * @return
     */
    private static String attachChargeInfo(String pay_platform, ChargeInfo chargeInfo) {
        String ret = pay_platform;
        if (null != chargeInfo) {
            StringBuilder sb = new StringBuilder();
            if (StringUtil.isNotBlank(pay_platform)) {
                String[] pay_platforms = null;
                String chargeInfo_141007 = null;
                if (pay_platform.indexOf(";") == -1 || pay_platform.indexOf(":") >= 0) {
                    // 含V2 pay_platform:getIsPay;getPayType;getIsCoupon
                    pay_platforms = pay_platform.split(",");
                } else { // 清洗老数据V1 ;getIsPay;getPayType;getIsCoupon
                    String[] pay_platform_tmp = pay_platform.split(";");
                    if (pay_platform_tmp.length > 1) {
                        pay_platforms = pay_platform_tmp[0].split(",");
                        pay_platform_tmp[2] = formatPayType(pay_platform_tmp[2]); // 格式化payType，节省字典存储
                        chargeInfo_141007 = StringUtil.getArrayToString(
                                Arrays.copyOfRange(pay_platform_tmp, 1, pay_platform_tmp.length), ";");
                    }
                }
                if (null != pay_platforms) {
                    int index = 0;
                    for (int i = 0, len = pay_platforms.length; i < len; i++) {
                        if (pay_platforms[i].contains(":")) { // V2-data
                            // String[] pay_platform_tmp =
                            // pay_platforms[i].split(":");
                            if (null != chargeInfo.getChargePlatform()
                                    && pay_platforms[i].contains(chargeInfo.getChargePlatform())) {
                                sb.append(genPayPlatformFromChargeInfo(chargeInfo));
                            } else { // follow vrs
                                sb.append(pay_platforms[i]);
                                index++;
                            }
                            sb.append(",");
                        } else { // v1-data
                            if (null != chargeInfo.getChargePlatform()
                                    && pay_platforms[i].contains(chargeInfo.getChargePlatform())) {
                                sb.append(genPayPlatformFromChargeInfo(chargeInfo));
                            } else {
                                if (null != chargeInfo_141007) {
                                    if (pay_platforms[i].equals(CommonConstants.TV_PAY_CODE)) {
                                        sb.append(pay_platforms[i]).append(":").append(chargeInfo_141007);
                                    } else { // follow vrs
                                        sb.append(pay_platforms[i]);
                                    }
                                } else { // follow vrs
                                    sb.append(pay_platforms[i]);
                                }
                                index++;
                            }
                            sb.append(",");
                        }
                    }
                    if (index == pay_platforms.length) { // add new one!
                        sb.append(genPayPlatformFromChargeInfo(chargeInfo)).append(",");
                    }
                }
            } else {
                sb.append(genPayPlatformFromChargeInfo(chargeInfo)).append(",");
            }
            if (sb.length() > 0) { // remove the last ","
                sb.replace(sb.length() - 1, sb.length(), "");
            }
            ret = sb.toString();
        }
        return ret;
    }

    private static String genPayPlatformFromChargeInfo(ChargeInfo chargeInfo) {
        StringBuilder sb = new StringBuilder();
        if (StringUtil.isNotBlank(chargeInfo.getChargePlatform())) {
            sb.append(chargeInfo.getChargePlatform());
            if (StringUtil.isNotBlank(chargeInfo.getIsCharge())) {
                sb.append(":").append(chargeInfo.getIsCharge());
            } else {
                sb.append(":").append("0");
            }
            if (StringUtil.isNotBlank(chargeInfo.getChargeType()) || StringUtil.isNotBlank(chargeInfo.getVipInfo())) {
                if (StringUtil.isBlank(chargeInfo.getChargeType())) {
                    chargeInfo.setChargeType("0");
                }
                if (StringUtil.isNotBlank(chargeInfo.getVipInfo())
                        && StringUtil.getStringToSet(chargeInfo.getVipInfo(), ",").contains("16")) {
                    // 特殊扩展支持超级家庭会员身份！！！
                    sb.append(";").append("16-" + chargeInfo.getChargeType());
                } else {
                    sb.append(";").append(chargeInfo.getChargeType());
                }
            } else {
                sb.append(";").append("0");
            }
            if (StringUtil.isNotBlank(chargeInfo.getIscoupon())) {
                sb.append(";").append(chargeInfo.getIscoupon());
            } else {
                sb.append(";").append("0");
            }
        }
        return sb.toString();
    }

    public static boolean existPayPlatform(SessionCache sessionCache) {
        if (null == sessionCache) {
            return false;
        }
        Object obj = sessionCache.getCommObj("p_devType");
        if (null != obj && obj instanceof String) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPayPlatform(SessionCache sessionCache) {
        String platform = "";
        if (null == sessionCache) {
            return platform;
        }
        Object obj = sessionCache.getCommObj("p_devType");
        if (null != obj && obj instanceof String) {
            platform = getPayPlatform((String) sessionCache.getCommObj("p_devType"));
        }
        return platform;
    }

    public static String getPayPlatform(Integer devType) {
        String platform = "";
        if (null != devType) {
            platform = getPayPlatform(String.valueOf(devType));
        } else { // 兼容老版本
            platform = "141007";
        }
        return platform;
    }

    public static String getPayPlatform(String devType) {
        String platform = "";
        CommonConstants.PAY_PLATFORM_TYPE pay_platform_type = CommonConstants.PAY_PLATFORM_TYPE.getByDevType(devType);
        if (null != pay_platform_type) {
            platform = pay_platform_type.getPlatform();
        }
        return platform;
    }

    public static Integer getDevType(String platform) {
        Integer devType = null;
        CommonConstants.PAY_PLATFORM_TYPE pay_platform_type = CommonConstants.PAY_PLATFORM_TYPE.getByPlatform(platform);
        if (null != pay_platform_type) {
            devType = pay_platform_type.getDevType();
        }
        return devType;
    }

    public static boolean isSupportPayPlatform(String platforms, Integer devType) {
        boolean ret = false;
        if (null == devType) { // 兼容老版本
            ret = platforms.contains("141007");
        } else {
            String plaform = getPayPlatform(devType);
            if (StringUtil.isNotBlank(plaform)) {
                ret = platforms.contains(plaform);
            }
        }
        return ret;
    }

    public static boolean isSupportPayPlatform(String platform) {
        boolean ret = false;

        for (CommonConstants.PAY_PLATFORM_TYPE pay_platform_type : CommonConstants.PAY_PLATFORM_TYPE.values()) {
            if (platform.contains(pay_platform_type.getPlatform())) { // V1
                ret = true;
                break;
            }
        }

        return ret;

    }

    public static String getPlayPlatform(Integer devType) {
        return CommonConstants.TV_PLATFROM_CODE;
    }

    public static boolean isSupportPayPlatform(List<String> platforms) {
        return filterUnSupportedPayPlatform(platforms).size() > 0;
    }

    public static List<String> filterUnSupportedPayPlatform(List<String> platforms) {
        List<String> ret = new ArrayList<String>();

        for (CommonConstants.PAY_PLATFORM_TYPE pay_platform_type : CommonConstants.PAY_PLATFORM_TYPE.values()) {
            if (platforms.contains(pay_platform_type.getPlatform())) {
                ret.add(pay_platform_type.getPlatform());
            }
        }

        return ret;
    }

    /**
     * @param payInfo
     *            : pay_platform:getIsPay;getPayType;getIsCoupon
     * @param chargeInfo
     */
    private static void rebuildChargeInfo(String payInfo, ChargeInfo chargeInfo) {
        if (StringUtil.isNotBlank(payInfo)) {
            String[] payInfosTmp = payInfo.split(":");

            // =============修正老数据未设置付费信息问题 @dengliwei (B)==============//
            if (payInfosTmp.length == 1) {
                chargeInfo.setIsCharge("1");
                chargeInfo.setChargeType("1");
                chargeInfo.setIscoupon("0");
                return;
            }
            // =============修正老数据未设置付费信息问题 @dengliwei (E)==============//

            String[] payInfos = payInfosTmp[1].split(";");
            chargeInfo.setChargePlatform(payInfosTmp[0]);
            if (payInfos.length > 0) {
                chargeInfo.setIsCharge(payInfos[0]);
                if (payInfos.length > 1) {
                    chargeInfo.setChargeType(payInfos[1]);
                } else {
                    chargeInfo.setChargeType("1");
                }
                if (payInfos.length > 2) {
                    chargeInfo.setIscoupon(payInfos[2]);
                } else {
                    chargeInfo.setIscoupon("0");
                }
            } else {
                chargeInfo.setIsCharge("1");
                chargeInfo.setChargeType("1");
                chargeInfo.setIscoupon("0");
            }
        } else { // 默认
            chargeInfo.setChargePlatform(CommonConstants.TV_PAY_CODE);
            chargeInfo.setIsCharge("0");
            chargeInfo.setChargeType("1");
            chargeInfo.setIscoupon("0");
        }
    }

    public static Map<String, ChargeInfo> parserPayPlatform(String payPlatform) {
        Map<String, ChargeInfo> ret = null;
        // payPlatform = "141010:0;0;0,141011:0;0;0,141007:0;16-0;0"; // TC:
        // 超级家庭会员 & 超影会员并存
        if (StringUtil.isNotBlank(payPlatform)) {
            try {
                String[] pay_platforms = null;
                String chargeInfo_141007 = null;
                if (payPlatform.indexOf(";") == -1 || payPlatform.indexOf(":") >= 0) { // v2
                    // 含V2 pay_platform:getIsPay;getPayType;getIsCoupon
                    pay_platforms = payPlatform.split(",");
                } else { // v1
                    String[] pay_platform_tmp = payPlatform.split(";");
                    if (pay_platform_tmp.length > 1) {
                        pay_platforms = pay_platform_tmp[0].split(",");
                        pay_platform_tmp[2] = formatPayType(pay_platform_tmp[2]); // 格式化payType，节省字典存储
                        chargeInfo_141007 = StringUtil.getArrayToString(
                                Arrays.copyOfRange(pay_platform_tmp, 1, pay_platform_tmp.length), ";");
                    }
                }

                if (null != pay_platforms) {
                    ret = new HashMap<String, ChargeInfo>();
                    ChargeInfo chargeInfo = null;
                    for (int i = 0, len = pay_platforms.length; i < len; i++) {
                        chargeInfo = new ChargeInfo();
                        if (null != chargeInfo_141007) { // v1
                            if (pay_platforms[i].equals(CommonConstants.TV_PAY_CODE)) {
                                rebuildChargeInfo(pay_platforms[i] + ":" + chargeInfo_141007, chargeInfo);
                            } else {
                                rebuildChargeInfo(pay_platforms[i], chargeInfo);
                            }
                        } else {
                            rebuildChargeInfo(pay_platforms[i], chargeInfo);
                        }
                        ret.put(chargeInfo.getChargePlatform(), chargeInfo);
                    }
                }
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public static List<ChargeInfo> parserPayDetail(String payDetail) {
        List<ChargeInfo> ret = null;

        if (StringUtil.isNotBlank(payDetail)) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(payDetail);
                ChargeInfo chargeInfo = null;
                if (null != jsonObject) {
                    ret = new ArrayList<ChargeInfo>();
                    for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                        chargeInfo = JSON.parseObject(JSON.toJSONString(entry.getValue()), ChargeInfo.class);
                        if (null != chargeInfo) {
                            chargeInfo.setChargePlatform(entry.getKey());
                            ret.add(chargeInfo);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }

        return ret;
    }

    public static ChargeInfo getChargeInfoFromPayDetail(Integer devType, Object payDetail) {
        ChargeInfo ret = null;
        List<ChargeInfo> chargeInfos = null;
        if (payDetail instanceof String) {
            chargeInfos = parserPayDetail((String) payDetail);
        } else if (payDetail instanceof List) {
            chargeInfos = (List) payDetail;
        }
        if (null != chargeInfos) {
            for (int i = 0; i < chargeInfos.size(); i++) {
                if (StringUtil.isNotBlank(chargeInfos.get(i).getChargePlatform())) {
                    Integer devTypeTmp = getDevType(chargeInfos.get(i).getChargePlatform());
                    if (null != devTypeTmp && devTypeTmp.intValue() == devType.intValue()) {
                        ret = chargeInfos.get(i);
                        break;
                    }
                }
            }
        }
        return ret;
    }

    public static boolean isCharge4Platform(String pay_platform, Integer p_devType) {
        if (pay_platform != null && isSupportPayPlatform(pay_platform, p_devType)) {
            Map<String, ChargeInfo> chargeInfoMap = parserPayPlatform(pay_platform);
            if (null != chargeInfoMap) {
                ChargeInfo chargeInfo = chargeInfoMap.get(getPayPlatform(p_devType));
                if (null != chargeInfo) {
                    return "1".equals(chargeInfo.getIsCharge());
                }
            }
            return true;
        }
        return false;
    }

    public static String getChargeTypeFromPlatform(String payPlatform, Integer p_devType) {
        String ret = null;
        ChargeInfo chargeInfo = null;
        Map<String, ChargeInfo> chargeInfoMap = parserPayPlatform(payPlatform);
        if (null != chargeInfoMap) {
            chargeInfo = chargeInfoMap.get(getPayPlatform(p_devType));
            ret = chargeInfo.getChargeType();
        }
        return ret;
    }
}
