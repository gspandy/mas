package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.MmsTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsAlbumInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsVideoCode;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsVideoInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

/**
 * 视频模块相关工具方法类，提供一些通用方法
 * @author KevinYi
 */
public class VideoCommonUtil {
    private static final Logger log = Logger.getLogger(VideoCommonUtil.class);
    /**
     * 码流过滤校验模式，1--入库，2--展示
     */
    public static final int FILTER_VALID_STARTM_MODE_SAVE = 1;
    public static final int FILTER_VALID_STARTM_MODE_DISPLAY = 2;

    public static String getVideoIdStr(List<VideoMysqlTable> iptvVideoInfos, String separator) {
        String videoIdStr = "";
        if (!CollectionUtils.isEmpty(iptvVideoInfos)) {
            if (separator == null) {
                separator = ",";
            }
            List<String> videoIdStrList = new ArrayList<String>((int) (iptvVideoInfos.size() / 0.75) + 1);
            for (VideoMysqlTable video : iptvVideoInfos) {
                if (video != null) {
                    videoIdStrList.add(String.valueOf(video.getId()));
                }
            }
            videoIdStr = StringUtils.join(videoIdStrList, ",");
        }

        return videoIdStr;
    }

    /**
     * 判断视频或者专辑是否是正片
     * @param dataType
     *            1--视频，2--专辑
     * @param category
     *            分类id
     * @param albumType
     *            专辑正片类型
     * @param videoType
     *            视频正片类型
     * @return
     */
    public static Boolean isPositive(int dataType, Integer category, Integer albumType, Integer videoType) {
        // 电影、电视剧有正片的概念
        Boolean isPositive = true;
        if (dataType == 1) {// 视频
            if (category != null
                    && (category == 1 || category == 2 || category == 5 || category == 11 || category == 16)) {
                if (videoType == null || videoType != 180001) {
                    isPositive = false;
                }
            }
            if (category != null && category == 19) {// 乐视制造 可以上 生肖和星座和正片
                if (videoType == null || (videoType != 181204 && videoType != 181205 && videoType != 180001)) {
                    isPositive = false;
                }
            }
        } else if (dataType == 2) {// 专辑
            if (category != null && (category == 1 || category == 2 || category == 11 || category == 16)) {
                if (albumType == null || albumType != 180001) {
                    isPositive = false;
                }
            }
            if (category != null && category == 19) {// 乐视制造 可以上 生肖和星座和正片
                if (albumType == null || (albumType != 181204 && albumType != 181205 && albumType != 180001)) {
                    isPositive = false;
                }
            }
        }
        return isPositive;
    }

    /**
     * 判断是否视频或者专辑收费
     * @param payPlatForm
     * @return
     */
    public static boolean isCharge(String payPlatForm, String playPlatForm) {
        return isCharge(payPlatForm, playPlatForm, null);
    }

    public static boolean isCharge(String payPlatForm, String playPlatForm, Integer p_devType) {
        return payPlatForm != null && MmsDataUtil.isSupportPayPlatform(payPlatForm) && playPlatForm != null
                && playPlatForm.indexOf("420007") > -1 && MmsDataUtil.isCharge4Platform(payPlatForm, p_devType);
    }

    /**
     * 判断各终端的码流（一般是解析好的）是否付费TV版规范
     * @param stream
     * @return
     */
    public static final String filterTerminalValidStream(String stream) {
        if (stream == null) {
            return null;
        }
        if (stream.indexOf("_") > 0) {
            stream = stream.substring(stream.indexOf("_") + 1);
            if (stream.indexOf("3d") > -1) {
                stream = "3d" + stream.substring(0, stream.lastIndexOf("_"));
            }
        }

        if (LetvStreamCommonConstants.CODE_NAME_1080p.equals(stream)) {
            stream = LetvStreamCommonConstants.CODE_NAME_1080p6m;
        }

        if (LetvStreamCommonConstants.STREAM_CODE_FILTER_MAP.containsKey(stream)) {
            return stream;
        }

        return null;
    }

    /**
     * 判断媒资码流（一般未解析）是否符合TV端规范
     * @param stream
     * @param mode
     * @return
     */
    public static final String filterValidStream(String stream, int mode) {
        if (stream == null) {
            return null;
        }

        if (stream.equals("dolby_vision_4k")) {
            stream = LetvStreamCommonConstants.CODE_NAME_DOLBY_VISION_4K;
            return stream;
        }

        if (stream.indexOf("_") > 0) {
            stream = stream.substring(stream.indexOf("_") + 1);
            if (stream.indexOf("3d") > -1) {
                stream = "3d" + stream.substring(0, stream.lastIndexOf("_"));
            }
            if (VideoCommonUtil.isValidMode(stream, mode)) {
                return stream;
            }
        }

        return null;
    }

    /**
     * 判断TV版内码流是否合法
     * @param stream
     * @param mode
     * @return
     */
    public static final String filterLetvValidStream(String stream, int mode) {
        if (stream != null) {
            if (stream.indexOf("3d") > -1) {
                stream = "3d" + stream.substring(0, stream.lastIndexOf("_"));
            }
            if ((FILTER_VALID_STARTM_MODE_SAVE == mode && LetvStreamCommonConstants.STREAM_CODE_SAVE_FILTER_MAP
                    .containsKey(stream))
                    || (FILTER_VALID_STARTM_MODE_DISPLAY == mode && LetvStreamCommonConstants.STREAM_CODE_FILTER_MAP
                            .containsKey(stream))) {
                return stream;
            }
        }

        return null;
    }

    /**
     * get video or album episode show text
     * @param type
     *            1--not end,2--is end,3--no next episode
     * @param category
     * @param follownum
     * @return
     */
    public static String getVideoOrAlbumEpisodeText(int type, Integer category, String follownum, String langcode) {
        Object[] codeMap = { follownum };
        if (category != null && category == 11) {
            if (follownum != null && follownum.length() >= 8) {
                String year = follownum.substring(0, 4);
                String month = follownum.substring(4, 6);
                String day = follownum.substring(6, 8);
                if (!String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).equals(year)) {
                    codeMap = new Object[] { year, month, day };
                    return MessageUtils.getMessage("CHANNEL_ALBUM_UPDATED_TO_NOWISSUE_2", langcode, codeMap);
                } else {
                    codeMap = new Object[] { month, day };
                    return MessageUtils.getMessage("CHANNEL_ALBUM_UPDATED_TO_NOWISSUE_1", langcode, codeMap);
                }
            }
            return follownum;
        }
        if (type == 1) {// not end
            return MessageUtils.getMessage("CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES_NORMAL", langcode, codeMap);
        } else if (type == 2) {// is end
            return MessageUtils.getMessage("CHANNEL_ALBUM_IS_END", langcode, codeMap);
        } else if (type == 3) {// no next episode
            return MessageUtils.getMessage("CHANNEL_ALBUM_ABOUT_ONLINE", langcode);
        }
        return "";
    }

    /**
     * get video or album episode show text
     * @param type
     *            1--not end,2--is end,3--no next episode
     * @param category
     * @param follownum
     * @return
     */
    public static String getVideoOrAlbumEpisodeTextV2(int type, Integer category, String follownum, String langcode) {
        Object[] codeMap = { follownum };
        if (category != null && category == 11) {
            if (follownum != null && follownum.length() >= 8) {
                /*
                 * String year = follownum.substring(0, 4);
                 * String month = follownum.substring(4, 6);
                 * String day = follownum.substring(6, 8);
                 * if
                 * (!String.valueOf(Calendar.getInstance().get(Calendar.YEAR))
                 * .equals(year)) {
                 * codeMap = new Object[] { year, month, day };
                 * return
                 * MessageUtils.getMessage("CHANNEL_ALBUM_UPDATED_TO_NOWISSUE_2"
                 * , langcode, codeMap);
                 * } else {
                 * codeMap = new Object[] { month, day };
                 * return
                 * MessageUtils.getMessage("CHANNEL_ALBUM_UPDATED_TO_NOWISSUE_1"
                 * , langcode, codeMap);
                 * }
                 */
                return MessageUtils.getMessage("CHANNEL_ALBUM_UPDATED_TO_NOWISSUE", langcode, codeMap);
            }
            return follownum;
        }
        if (type == 1) {// not end
            return MessageUtils.getMessage("CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES_NORMAL", langcode, codeMap);
        } else if (type == 2) {// is end
            return MessageUtils.getMessage("CHANNEL_ALBUM_IS_END", langcode, codeMap);
        } else if (type == 3) {// no next episode
            return MessageUtils.getMessage("CHANNEL_ALBUM_ABOUT_ONLINE", langcode);
        }
        return "";
    }

    /**
     * filter the video by the broadcast control flag
     * @param video
     *            video info
     * @param broadcastId
     *            broadcast control flag
     * @return null if the video is filtered, otherwise the source video.
     */
    public static VideoMysqlTable filterByBroadcastId(VideoMysqlTable video, Integer broadcastId) {
        if (video != null) {
            if (checkBroadcastId(broadcastId, video.getCntv(), video.getWasu(), video.getCibn(), video.getPushflag())) {
                return video;
            }
        }
        return null;
    }

    /**
     * filter the album by the broadcast control flag
     * @param album
     *            album info
     * @param broadcastId
     *            broadcast control flag
     * @return null if the album is filtered, otherwise the source album.
     */
    public static AlbumMysqlTable filterByBroadcastId(AlbumMysqlTable album, Integer broadcastId) {
        if (album != null) {
            if (checkBroadcastId(broadcastId, album.getCntv(), album.getWasu(), album.getCibn(), album.getPushflag())) {
                return album;
            }
        }
        return null;
    }

    /**
     * get the total page number of video list.
     * @param videoTotal
     *            video total number
     * @return total page number
     */
    public static int getPageTotal(int videoTotal) {
        if (videoTotal == -1) {
            return 0;
        } else {
            return (videoTotal - 1) / VideoTpConstant.ALBUM_SERIES_PAGE_SIZE + 1;
        }
    }

    public static String getAlbumKey(long id, String region, String lang) {
        return keyBuilder(CacheContentConstants.ALBUM_KEY_PREFIX, region, lang, id);
    }

    public static String getVideoKey(long id, String region, String lang) {
        return keyBuilder(CacheContentConstants.VIDEO_KEY_PREFIX, region, lang, id);
    }

    public static String getAlbumPageKey(long albumId, String region, String langCode, String queryType) {
        return getAlbumVideoListKey(albumId, region, langCode, queryType, null);
    }

    public static String getAlbumVideoListKey(long albumId, String region, String langCode, String queryType,
            Integer page) {
        if (page == null) {
            return keyBuilder(getAlbumListPrefix(queryType), region, langCode, albumId);
        } else {
            return keyBuilder(getAlbumListPrefix(queryType), region, langCode, albumId, page);
        }
    }

    private static String getAlbumListPrefix(String queryType) {
        if (VideoTpConstant.QUERY_TYPE_NON_POSITIVE.equals(queryType)) {
            return CacheContentConstants.ALBUM_OTHER_LIST_PREFIX;
        } else if (VideoTpConstant.QUERY_TYPE_POSITIVE.equals(queryType)) {
            return CacheContentConstants.ALBUM_FEATURE_LIST_PREFIX;
        } else {
            // default
            return CacheContentConstants.ALBUM_ALL_LIST_PREFIX;
        }
    }

    private static String keyBuilder(String prefix, String region, String lang, long id, Object... others) {
        StringBuilder builder = new StringBuilder(prefix);
        if (StringUtils.isBlank(region)) {
            region = LocaleConstant.DEFAULT_WCODE;
        }
        if (StringUtils.isBlank(lang)) {
            lang = LocaleConstant.DEFAULT_LANGUAGE;
        }
        region = StringUtils.trim(region).toUpperCase();
        lang = StringUtils.trimToEmpty(lang).toUpperCase().replace("_", "-");
        builder.append("_").append(region).append("_").append(lang).append("_").append(id);
        if (others != null) {
            for (Object other : others) {
                builder.append("_").append(other);
            }
        }
        return builder.toString();
    }

    /**
     * 在码流code之前拼接对应的前缀，然后用于获取对应的vtype
     * @param stream
     * @return
     */
    public static String getVideoTypeByStream(String stream) {
        String videoType = "";
        if ("1080p".equalsIgnoreCase(stream)) {
            videoType = LetvStreamCommonConstants.VIDEO_TYPE_MP4_PREFIX + stream.toLowerCase();
        } else if ("350".equalsIgnoreCase(stream) || "1000".equalsIgnoreCase(stream) || "1300".equalsIgnoreCase(stream)
                || "720p".equalsIgnoreCase(stream) || "1080p6m".equalsIgnoreCase(stream)
                || "1080p3m".equalsIgnoreCase(stream)) {
            videoType = LetvStreamCommonConstants.VIDEO_TYPE_FLV_PREFIX + stream.toLowerCase();
        } else if ("3d1080p".equalsIgnoreCase(stream)) {
            videoType = LetvStreamCommonConstants.VIDEO_TYPE_FLV_PREFIX + "1080p_3d";
        } else if ("3d1080p6m".equalsIgnoreCase(stream)) {
            videoType = LetvStreamCommonConstants.VIDEO_TYPE_FLV_PREFIX + "1080p6m_3d";
        } else if ("3d720p".equalsIgnoreCase(stream)) {
            videoType = LetvStreamCommonConstants.VIDEO_TYPE_FLV_PREFIX + "720p_3d";
        } else if ("720p_db".equalsIgnoreCase(stream) || "1300_db".equalsIgnoreCase(stream)
                || "800_db".equalsIgnoreCase(stream) || "1080p6m_db".equalsIgnoreCase(stream)
                || LetvStreamCommonConstants.CODE_NAME_4K.equalsIgnoreCase(stream)) {
            videoType = LetvStreamCommonConstants.VIDEO_TYPE_MP4_PREFIX + stream;
        } else if ("800".equalsIgnoreCase(stream)) {
            videoType = LetvStreamCommonConstants.VIDEO_TYPE_MP4_PREFIX + stream;
        }
        return videoType;
    }

    public static VideoMysqlTable getVideoFromVrs(Long videoId, MmsVideoInfo mmsVideoInfo) {
        if (mmsVideoInfo == null) {
            return null;
        }
        VideoMysqlTable videoMysqlTable = null;

        try {
            videoMysqlTable = ParseUtil.copyBeanByParamsField(VideoMysqlTable.class, null, mmsVideoInfo);
            if (videoMysqlTable == null) {
                return null;
            }
            // 合并原vrs相应字段信息
            videoMysqlTable.initSplitProperty();
            if (VideoCommonUtil.isPositive(1, videoMysqlTable.getCategory(), null, videoMysqlTable.getVideo_type())) {
                videoMysqlTable.setVideo_attr(1);
            } else {
                videoMysqlTable.setVideo_attr(0);
            }
            // 视频名称
            if (mmsVideoInfo.getTvTitle() != null && mmsVideoInfo.getTvTitle().trim().length() > 0) {
                videoMysqlTable.setName_cn(mmsVideoInfo.getTvTitle());
            }
            if (StringUtil.isNotBlank(mmsVideoInfo.getSubTitle())) {
                videoMysqlTable.setSub_title(mmsVideoInfo.getSubTitle());
            }
            // 媒资id,去除首尾","
            if (videoMysqlTable.getMid() != null && videoMysqlTable.getMid().startsWith(",")) {
                videoMysqlTable.setMid(videoMysqlTable.getMid().substring(1));
            }
            if (videoMysqlTable.getMid() != null && videoMysqlTable.getMid().endsWith(",")) {
                videoMysqlTable.setMid(videoMysqlTable.getMid().substring(0, videoMysqlTable.getMid().length() - 1));
            }

            // 原video_info_id
            if (videoMysqlTable.getVideo_info_id() == null || videoMysqlTable.getVideo_info_id().longValue() == 0) {
                videoMysqlTable.setVideo_info_id(videoId);
            } /*
               * else{
               * videoMysqlTable.setVideo_info_id(video_info_id.longValue());
               * }
               */

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
            if (mmsVideoInfo.getPlayControlPlatformGuoguang() != null) {
                videoMysqlTable.setCibn(mmsVideoInfo.getPlayControlPlatformGuoguang());
            }
            videoMysqlTable.setCategory_name_all(videoMysqlTable.getCategory_name());
            /*
             * 2016-03-25测试环境添加逻辑，为应对媒资测试环境码流不全问题，TV服务端采用从线上同步码流数据，测试环境同步基础数据的方式规避
             * ;
             * 该逻辑仅在测试环境使用，严禁合并至trunk--by YiKun
             */
            // 码流-视频所有码流，统一格式为",{码流},{码流}..."，最后截取开头与结尾的","，保留"{码流},{码流}"形式入库
            StringBuffer playStreams = new StringBuffer(",");
            Map<Integer, String> midStreams = new HashMap<Integer, String>();// 媒资id对应的码流信息，以防止多个媒资id的时候播放找不到媒资id的问题
            Map<Integer, List<MmsVideoCode>> videocode = mmsVideoInfo.getVideocode();
            if (!CollectionUtils.isEmpty(videocode)) {
                for (Integer mid : videocode.keySet()) {
                    List<MmsVideoCode> midVideoCodes = videocode.get(mid);
                    StringBuffer midStreamsBuffer = new StringBuffer(",");
                    if (midVideoCodes != null && midVideoCodes.size() > 0) {
                        for (MmsVideoCode mmsVideoCode : midVideoCodes) {
                            String status = mmsVideoCode.getStatus();
                            if (status == null || !status.contains("300006")) {// 没有发布成功的不保存
                                continue;
                            }
                            Map<Integer, String> code = mmsVideoCode.getCode();
                            if (code != null && code.size() > 0) {
                                for (String stream : code.values()) {

                                    String filteredstream = VideoCommonUtil.filterValidStream(stream,
                                            VideoCommonUtil.FILTER_VALID_STARTM_MODE_SAVE);
                                    log.info("initIptvVideoInfo_" + videoId + ": validate stream [" + stream
                                            + "], try to store [" + filteredstream + "]");
                                    if (filteredstream == null) {
                                        continue;
                                    }
                                    if (playStreams.indexOf("," + filteredstream + ",") < 0) {
                                        playStreams.append(filteredstream).append(",");
                                    }
                                    if (midStreamsBuffer.indexOf("," + filteredstream + ",") < 0) {
                                        midStreamsBuffer.append(filteredstream).append(",");
                                    }
                                }
                            }

                        }
                    } else {
                        log.error("initIptvVideoInfo_" + videoId + "_" + mid + ": null play stream");
                    }
                    String midStreamsStr = midStreamsBuffer.toString();
                    if (midStreamsStr.startsWith(",")) {
                        // 去开头的","
                        midStreamsStr = midStreamsStr.substring(1);
                    }
                    if (midStreamsStr.endsWith(",")) {
                        midStreamsStr = midStreamsStr.substring(0, midStreamsStr.length() - 1);
                    }
                    midStreams.put(mid, midStreamsStr);
                }
            } else {
                log.error("initIptvVideoInfo_" + videoId + ": null videocode");
            }

            String playStreamsStr = playStreams.toString();
            if (playStreamsStr.startsWith(",")) {
                // 去开头的","
                playStreamsStr = playStreamsStr.substring(1);
            }
            if (playStreamsStr.endsWith(",")) {
                // 去结尾的","
                playStreamsStr = playStreamsStr.substring(0, playStreamsStr.length() - 1);
            }
            videoMysqlTable.setPlay_streams(playStreamsStr);
            log.info("initIptvVideoInfo_" + videoId + ": update streams [" + playStreamsStr + "]");

            if (midStreams != null) {
                videoMysqlTable.setMid_streams(JsonUtil.parseToString(midStreams));
            }
            // 图片
            if (mmsVideoInfo.getPicAll() != null) {
                videoMysqlTable.setPic_all(JsonUtil.parseToString(mmsVideoInfo.getPicAll()));
            }

            // 时间
            videoMysqlTable.setCreate_time(TimeUtil.parseDate(mmsVideoInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            videoMysqlTable.setUpdate_time(TimeUtil.parseDate(mmsVideoInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
            videoMysqlTable.setPushflag((videoMysqlTable.getPlay_platform() != null && videoMysqlTable
                    .getPlay_platform().indexOf("420012") > 0) ? 0 : 1);
            videoMysqlTable.setLogonum(mmsVideoInfo.getLogoNum() == null ? 0 : 1);
            if (!CollectionUtils.isEmpty(mmsVideoInfo.getDrmFlag())) {
                videoMysqlTable.setDrmFlagId(mmsVideoInfo.getDrmFlag().keySet().toString());
            }

            // 2016-04-01媒资返回数据错误，key值返回"nul"，暂取消自动解析，改为代码解析
            Map<String, String> style = mmsVideoInfo.getStyle();
            if (style != null) {
                Set<Entry<String, String>> styleEntrySet = style.entrySet();
                if (!CollectionUtils.isEmpty(styleEntrySet)) {
                    for (Entry<String, String> styleEntry : styleEntrySet) {
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
        } catch (Exception e) {
            log.info("getVideoFromVrs " + videoId + " error:" + e.getMessage());
        }

        return videoMysqlTable;
    }

    public static AlbumMysqlTable getAlbumFromVrs(Long albumId, MmsAlbumInfo mmsAlbumInfo) {
        if (mmsAlbumInfo == null) {
            return null;
        }
        AlbumMysqlTable albumMysqlTable = null;

        try {
            albumMysqlTable = ParseUtil.copyBeanByParamsField(AlbumMysqlTable.class, null, mmsAlbumInfo);
            if (mmsAlbumInfo.getPlayPlatform() == null || mmsAlbumInfo.getPlayPlatform().size() == 0) {
                albumMysqlTable.setPlay_platform(null);
            }
            if (mmsAlbumInfo.getIsPay() == null || mmsAlbumInfo.getIsPay() != 1
                    || mmsAlbumInfo.getPayPlatform() == null || mmsAlbumInfo.getPayPlatform().size() == 0) {
                // 2015-07-07与媒资后台同步，必须设置为付费且在boss后台勾选了付费平台的才算付费影片，否则将服务端对应数据付费平台清空
                albumMysqlTable.setPay_platform(null);
            }

            // for tvod icon type
            // if(albumMysqlTable != null &&
            // StringUtil.isNotBlank(albumMysqlTable.getPay_platform())){
            // if(mmsAlbumInfo.getIsCoupon() != null && mmsAlbumInfo.getIsPay()
            // != null){
            // albumMysqlTable.setPay_platform(albumMysqlTable.getPay_platform()+":"+mmsAlbumInfo.getIsPay()+":"+mmsAlbumInfo.getIsCoupon());
            // }
            // }

            // itv_album_id
            if (albumMysqlTable.getItv_album_id() == null || albumMysqlTable.getItv_album_id().longValue() == 0) {
                albumMysqlTable.setItv_album_id(albumId);
            }
            // 初始化vrs拆分的参数
            albumMysqlTable.initSplitProperty();

            // 专辑名称
            if (mmsAlbumInfo.getTvTitle() != null && mmsAlbumInfo.getTvTitle().trim().length() > 0) {
                albumMysqlTable.setName_cn(mmsAlbumInfo.getTvTitle());
            }

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
            if (mmsAlbumInfo.getPlayControlPlatformGuoguang() != null) {
                albumMysqlTable.setCibn(mmsAlbumInfo.getPlayControlPlatformGuoguang());
            }
            // TV版收费
            if (albumMysqlTable.isTVPay()) {
                albumMysqlTable.setIsyuanxian(1);
            } else {
                albumMysqlTable.setIsyuanxian(0);
            }
            // 2015-10-20 专题图片逻辑修改，添加动图逻辑
            Map<String, String> picCollections = mmsAlbumInfo.getPicCollections();
            String dynamicGraph = StringUtils.trimToNull(mmsAlbumInfo.getDynamicGraph());
            if (dynamicGraph != null) {
                if (picCollections == null) {
                    picCollections = new HashMap<String, String>();
                }
                picCollections.put(MmsTpConstant.MMS_ALBUM_DYNAMIC_GRAPH_FIELD_ANME, dynamicGraph);
            }
            if (picCollections != null) {
                albumMysqlTable.setPic_collections(JsonUtil.parseToString(picCollections));
            }

            // 分类
            // albumMysqlTable.setCategory_all(CategoryIdConstants.getOldCategory(albumMysqlTable.getCategory()));
            albumMysqlTable.setCategory_name_all(albumMysqlTable.getCategory_name());
            // 节目来源
            if (StringUtils.isBlank(albumMysqlTable.getRecord_company())) {
                albumMysqlTable.setRecord_company(mmsAlbumInfo.getrCompany());
            }
            // 时间
            albumMysqlTable.setCreate_time(TimeUtil.parseDate(mmsAlbumInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            albumMysqlTable.setUpdate_time(TimeUtil.parseDate(mmsAlbumInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
            albumMysqlTable.setVideoFollowTime(TimeUtil.parseDate(mmsAlbumInfo.getVideoFollowTime(),
                    "yyyy-MM-dd HH:mm:ss"));
            albumMysqlTable.setPushflag((albumMysqlTable.getPlay_platform() != null && albumMysqlTable
                    .getPlay_platform().indexOf("420012") > 0) ? 0 : 1);
            albumMysqlTable.setAlbum_attr(albumMysqlTable.isPositive() ? 1 : 0);
            albumMysqlTable.setNowEpisodes(mmsAlbumInfo.getNowEpisodes().toString());
            albumMysqlTable.setNowIssue(mmsAlbumInfo.getNowIssue().toString());
            albumMysqlTable.setVarietyShow(mmsAlbumInfo.getVarietyShow());
        } catch (Exception e) {
            log.info("getAlbumFromVrs " + albumId + " error:" + e.getMessage());
        }

        return albumMysqlTable;
    }

    public static String getToken(Long id, String platform) {
        String key = "1234";
        // String token = .sign(id+"tv", key, "UTF-8");
        String text = id + platform + key;
        String token = "";
        try {
            token = MessageDigestUtil.md5(text.getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            log.error("MONITOR EXCEPTION getToken is error,text:" + text, e);
        }
        return token;
    }

    private static boolean checkBroadcastId(Integer broadcastId, Integer cntv, Integer wasu, Integer cibn,
            Integer pushFlag) {
        if (pushFlag != null && pushFlag != 0 && pushFlag != 1) {
            return false;
        }
        if (broadcastId != null) {
            if ((broadcastId == CommonConstants.BROADCAST_TYPE.CNTV.getValue() && cntv != null && cntv == 0)
                    || (broadcastId == CommonConstants.BROADCAST_TYPE.WASU.getValue() && wasu != null && wasu == 0)
                    || (broadcastId == CommonConstants.BROADCAST_TYPE.CIBN.getValue() && cibn != null && cibn == 0)) {
                return false;
            }
        }
        return true;
    }

    private static final Boolean isValidMode(String stream, int mode) {
        return VideoCommonUtil.isValidSaveMode(stream, mode) || VideoCommonUtil.isValidDisplayMode(stream, mode);
    }

    private static final Boolean isValidSaveMode(String stream, int mode) {
        if (FILTER_VALID_STARTM_MODE_SAVE == mode
                && LetvStreamCommonConstants.STREAM_CODE_SAVE_FILTER_MAP.containsKey(stream)) {
            return true;
        }

        return false;
    }

    private static final Boolean isValidDisplayMode(String stream, int mode) {
        if (FILTER_VALID_STARTM_MODE_DISPLAY == mode
                && LetvStreamCommonConstants.STREAM_CODE_FILTER_MAP.containsKey(stream)) {
            return true;
        }
        return false;
    }
}
