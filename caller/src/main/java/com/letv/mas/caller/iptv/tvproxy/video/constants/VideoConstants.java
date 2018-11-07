package com.letv.mas.caller.iptv.tvproxy.video.constants;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.MmsTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VideoConstants {

    /** ==============================视频模块错误码=============================== */

    /** 获取详情页数据失败[抱歉，已偏离航道，请归位重试。错误：018] */
    public static final String DETAIL_PAGE_FAIL = "STV002";

    /** 获取详情页数据为空[暂无内容，错误：017] */
    public static final String DETAIL_PAGE_NULL = "STV001";

    /** 验证加密失败[签名错误] */
    public static final String VIDEO_SIG_ERROR = "9900"; // 抱歉，获取下载资源失败，请火速联系我们。错误码：025
    public static final String VIDEO_ILLEGAL_PARAMETER = "SVP002";// 抱歉，获取下载资源失败，请火速联系我们。错误码：025
    public static final String VIDEO_PLAY_NULL = "9902";// 因版权原因，该内容无法观看
    public static final String VIDEO_DOWNLOAD_NULL = "SVP004";// 未找到可下载资源
    public static final String VIDEO_DOWNLOAD_AUTHFAIL = "SVP005";// 抱歉，当前资源暂不提供下载
    public static final String VIDEO_CN_PLAY_FORBIDDEN = "SVP006";// 因政策原因，该内容无法提供观看
    public static final String VIDEO_CN_IP_FORBIDDEN = "0012";// 该内容不支持在中国大陆观看
    public static final String VIDEO_HK_IP_FORBIDDEN = "0045";// 該內容不支持在當前地區觀看
    public static final String VIDEO_NOT_CN_NOT_HK_IP_FORBIDDEN = "0008";// 该内容不支持在当前地区观看
    public static final String VIDEO_UNKNOW_IP_FORBIDDEN = "0016";// 该内容不支持在当前地区观看
    public static final String VIDEO_NOT_CN_PLAY_FORBIDDEN = "SVP007";// 因版权方要求，此视频仅支持在中国大陆播放
    public static final String VIDEO_STREAM_NOT_FOUND = "9903";// 未找到相关视频，请火速联系我们。错误码：024
    public static final String VIDEO_GET_LESO_STREAM_ADDR_ERROR = "SVP009";// 视频模块，获取leso播放流地址失败
    public static final String VIDEO_GET_VIDEO_PAY_INFO_ERROR = "SVP010";// 获取会员引导图数据失败
    public static final String VIDEO_GET_VIDEO_REACTION_ERROR = "SVP011";// 获取扫码互动数据失败
    public static final String VIDEO_GET_MMS_INFO_ERROR = "0412";// 当前视频暂无法播放，请您欣赏其他精彩资源！错误码：024
    public static final String VIDEO_GET_CPTOKEN_FAIL = "SVP014"; // 获取eros-token失败
    public static final String VIDEO_GET_PLAY_STRAM_ADDRESS_FAIL = "SVP015"; // 获取播放流地址失败
    public static final String VIDEO_PLAY_SWITCH_AUDIO_TRACK_FAIL = "SVP016"; // 切换音轨失败
    public static final String VIDEO_PLAY_CHILD_VIDEO_OFFLINE = "SVP017"; // 儿童视频下线
    public static final String VIDEO_PLAY_CHILD_ALBUM_OFFLINE = "SVP018"; // 儿童专辑下线
    public static final String VIDEO_PLAY_CHILD_SINGLE_VIDEO_OFFLINE = "SVP019"; // 儿童单视频下线
    public static final String VIDEO_GET_PLAY_STRAM_FAIL = "SVP020"; // 获取播放流地址失败
    public static final String VIDEO_PLAY_ERROR = "SVP021"; // 播放失败
    public static final String LECOM_VIDEO_PLAY_CHILD_LOCK_FORBIDDED = "SVP2001"; // 儿童锁其中状态下，视频分级受限不可播
    /** ==============================视频模块错误码=============================== */

    public static final String VIDEO_PLAY_ERROR_TIPS_CN = "VIDEO_PLAY_ERROR_TIPS_CN";// 客服电话：10109000
    public static final String VIDEO_PLAY_ERROR_TIPS_CN_COMMON = "VIDEO_ALBUM_DETAIL_ERROR_TIPS_COMMON";// 暂时同专辑使用一个key，文案都一样
    public static final String VIDEO_PLAY_ERROR_TIPS_HK = "VIDEO_PLAY_ERROR_TIPS_HK";// 客戶服務熱線：31289333
    public static final String VIDEO_PLAY_ERROR_TIPS_OVERSEAS = "VIDEO_PLAY_ERROR_TIPS_OVERSEAS";// 客服电话：（+86）10109000
    public static final String VIDEO_PLAY_ERROR_TIPS_OTHER = "VIDEO_PLAY_ERROR_TIPS_OTHER";// 客服电话：（+86）10109000
    public static final String VIDEO_ALBUM_DETAIL_ERROR_TIPS = "VIDEO_ALBUM_DETAIL_ERROR_TIPS_CN";// 客服电话：10109000
    // 通用版 客服电话:400 030 0104
    public static final String VIDEO_ALBUM_DETAIL_ERROR_TIPS_COMMON = "VIDEO_ALBUM_DETAIL_ERROR_TIPS_COMMON";
    public static final String VIDEO_PLAY_USER_ISFORBIDDEN_MSG = "VIDEO_PLAY_USER_ISFORBIDDEN_MSG";// 检测您账号有异常，会员服务已冻结

    /** 视频不支持客户端请求的码流，降码流播放的提示 */
    public static final String VIDEO_TIPMSG_REDUCESTREAM = "VIDEO.TIPMSG.REDUCESTREAM";
    /** 设备不支持4k，客户端请求4k码流播放，降码流播放的提示 */
    public static final String VIDEO_TIPMSG_UNSUPPORT_4KSTREAM = "VIDEO.TIPMSG.UNSUPPORT.4KSTREAM";
    /** 当前码流无当前音轨的提示语 */
    public static final String VIDEO_TIPMSG_NO_AUDIO_TRACK = "VIDEO.TIPMSG.NO.AUDIO.TRACK";
    /** 连播时下一个视频无当前播放码流、无当前播放音轨的提示语 */
    public static final String VIDEO_TIPMSG_NO_AUDIO_TRACK_PLAY_STREAM = "VIDEO.TIPMSG.NO.AUDIO.TRACK.PLAY.STREAM";
    /** you can play the first episode for free */
    public static final String VIDEO_TIPMSG_FOR_FREE_PLAY_FIRST_EPISODE = "VIDEO.SHIKAN.DIYIJI";
    /** you can play six minutes for free */
    public static final String VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES = "VIDEO.SHIKAN.SIXMIN";
    /** you can play three minutes for free */
    public static final String VIDEO_TIPMSG_TRY_PLAY_THREE_MINUTES = "VIDEO.SHIKAN.THREEMIN";
    /** you can play three minutes for free */
    public static final String VIDEO_TIPMSG_TRY_PLAY_ONE_MINUTES = "VIDEO.SHIKAN.ONEMIN";
    /** you can play dynamic minutes for free */
    public static final String VIDEO_TIPMSG_TRY_PLAY_DYNAMIC_MINUTES = "VIDEO.SHIKAN.DYNAMICMIN";
    /** 设备不支持全景，"当前设备暂不支持全景播放" */
    public static final String VIDEO_TIPMSG_UNSUPPORT_360_STREAM = "VIDEO.TIPMSG.UNSUPPORT.360.STREAM";

    /**
     * 视频播放文案，第X集，适用电影、电视剧频道
     */
    public static final String VIDEO_PLAY_SERIES_NUM_TEXT = "VIDEO.PLAY.SERIES.NUM.TEXT";

    /**
     * 视频播放，第X期，适用综艺频道
     */
    public static final String VIDEO_PLAY_PHASE_NUM_TEXT = "VIDEO.PLAY.PHASE.NUM.TEXT";

    /**
     * 乐视网会员TV版专享内容，您可免费试看xx分钟
     */
    public static final String VIDEO_TIPMSG_TRY_PLAY_SEVERAL_MINUTES = "VIDEO.SHIKAN.SEVERAL.MINS";

    /**
     * 乐视网会员TV版专享内容，您可免费试看xx秒
     */
    public static final String VIDEO_TIPMSG_TRY_PLAY_SEVERAL_SECONDS = "VIDEO.SHIKAN.SEVERAL.SECS";

    /**
     * 视频播放鉴权token默认有效时长，单位-毫秒
     */
    public static final long VIDEO_PLAY_TOKEN_EXPIRE_TIME_DEFAULT = 3600000L;// 300000L;

    /** Chinese Simplified */
    public static final String VIDEO_SUBTITLE_TYPE_TEXT_KEY_1000 = "VIDEO_SUBTITLE_TYPE_CHINESE_SIMPLIFIED";
    /** Traditional Chinese */
    public static final String VIDEO_SUBTITLE_TYPE_TEXT_KEY_1001 = "VIDEO_SUBTITLE_TYPE_TRADITIONAL_CHINESE";
    /** English */
    public static final String VIDEO_SUBTITLE_TYPE_TEXT_KEY_1002 = "VIDEO_SUBTITLE_TYPE_ENGLISH";
    /** Simplified Chinese and English */
    public static final String VIDEO_SUBTITLE_TYPE_TEXT_KEY_1003 = "VIDEO_SUBTITLE_TYPE_SIMPLIFIED_CHINESE_AND_ENGLISH";
    /** Traditional Chinese and English */
    public static final String VIDEO_SUBTITLE_TYPE_TEXT_KEY_1004 = "VIDEO_SUBTITLE_TYPE_TRADITIONAL_CHINESE_AND_ENGLISH";

    /** High-2, Dolby high rate */
    public static final String VIDEO_AUDIO_TYPE_TEXT_KEY_1003 = "VIDEO_AUDIO_TRACK_KBPS_TYPE_DOLBY";
    /** High-3, dts high rate */
    public static final String VIDEO_AUDIO_TYPE_TEXT_KEY_1004 = "VIDEO_AUDIO_TRACK_KBPS_TYPE_DTS";

    /** Mandarin */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1000 = "LANGUAGE_TEXT_MANDARIN";// Mandarin
    /** Cantonese */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1001 = "LANGUAGE_TEXT_CANTONESE";// Cantonese
    /** English */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1002 = "LANGUAGE_TEXT_ENGLISH";// English
    /** Korean */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1003 = "LANGUAGE_TEXT_KOREAN";// Korean
    /** Japanese */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1004 = "LANGUAGE_TEXT_JAPANESE";// Japanese
    /** Russian */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1005 = "LANGUAGE_TEXT_RUSSIAN";// Russian
    /** French */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1006 = "LANGUAGE_TEXT_FRENCH";// French
    /** German */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1007 = "LANGUAGE_TEXT_GERMAN";// German
    /** Italian language */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1008 = "LANGUAGE_TEXT_ITALIAN";// Italian
    /** Spanish */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1009 = "LANGUAGE_TEXT_SPANISH";// Spanish
    /** Other */
    public static final String VIDEO_LANG_TYPE_TEXT_KEY_1010 = "LANGUAGE_TEXT_OTHER";// Other

    public static final String VIDEO_ALBUM_DETAIL_TAG_SCORE = "VIDEO_ALBUM_DETAIL_TAG_SCORE";
    public static final String VIDEO_ALBUM_DETAIL_TAG_RANK = "VIDEO_ALBUM_DETAIL_TAG_RANK";
    public static final String VIDEO_ALBUM_DETAIL_TAG_VIEWCOUNT = "VIDEO_ALBUM_DETAIL_TAG_VIEWCOUNT";

    /** Letv中中国地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_CN = { MmsTpConstant.VIDEO_LANG_TYPE_1000,
            MmsTpConstant.VIDEO_LANG_TYPE_1001, MmsTpConstant.VIDEO_LANG_TYPE_1002, MmsTpConstant.VIDEO_LANG_TYPE_1003,
            MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1005, MmsTpConstant.VIDEO_LANG_TYPE_1006,
            MmsTpConstant.VIDEO_LANG_TYPE_1007, MmsTpConstant.VIDEO_LANG_TYPE_1008, MmsTpConstant.VIDEO_LANG_TYPE_1009 };
    /** Letv中香港地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_HK = { MmsTpConstant.VIDEO_LANG_TYPE_1001,
            MmsTpConstant.VIDEO_LANG_TYPE_1000, MmsTpConstant.VIDEO_LANG_TYPE_1002, MmsTpConstant.VIDEO_LANG_TYPE_1003,
            MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1005, MmsTpConstant.VIDEO_LANG_TYPE_1006,
            MmsTpConstant.VIDEO_LANG_TYPE_1007, MmsTpConstant.VIDEO_LANG_TYPE_1008, MmsTpConstant.VIDEO_LANG_TYPE_1009 };
    /** Letv中美国|英国地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_US = { MmsTpConstant.VIDEO_LANG_TYPE_1002,
            MmsTpConstant.VIDEO_LANG_TYPE_1009, MmsTpConstant.VIDEO_LANG_TYPE_1006, MmsTpConstant.VIDEO_LANG_TYPE_1007,
            MmsTpConstant.VIDEO_LANG_TYPE_1008, MmsTpConstant.VIDEO_LANG_TYPE_1000, MmsTpConstant.VIDEO_LANG_TYPE_1001,
            MmsTpConstant.VIDEO_LANG_TYPE_1003, MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1005 };
    /** Letv中韩国地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_KR = { MmsTpConstant.VIDEO_LANG_TYPE_1003,
            MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1002, MmsTpConstant.VIDEO_LANG_TYPE_1000,
            MmsTpConstant.VIDEO_LANG_TYPE_1001, MmsTpConstant.VIDEO_LANG_TYPE_1005, MmsTpConstant.VIDEO_LANG_TYPE_1006,
            MmsTpConstant.VIDEO_LANG_TYPE_1007, MmsTpConstant.VIDEO_LANG_TYPE_1008, MmsTpConstant.VIDEO_LANG_TYPE_1009 };
    /** Letv中日本地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_JP = { MmsTpConstant.VIDEO_LANG_TYPE_1004,
            MmsTpConstant.VIDEO_LANG_TYPE_1003, MmsTpConstant.VIDEO_LANG_TYPE_1002, MmsTpConstant.VIDEO_LANG_TYPE_1000,
            MmsTpConstant.VIDEO_LANG_TYPE_1001, MmsTpConstant.VIDEO_LANG_TYPE_1005, MmsTpConstant.VIDEO_LANG_TYPE_1006,
            MmsTpConstant.VIDEO_LANG_TYPE_1007, MmsTpConstant.VIDEO_LANG_TYPE_1008, MmsTpConstant.VIDEO_LANG_TYPE_1009 };
    /** Letv中俄国地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_RU = { MmsTpConstant.VIDEO_LANG_TYPE_1005,
            MmsTpConstant.VIDEO_LANG_TYPE_1002, MmsTpConstant.VIDEO_LANG_TYPE_1000, MmsTpConstant.VIDEO_LANG_TYPE_1001,
            MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1003, MmsTpConstant.VIDEO_LANG_TYPE_1006,
            MmsTpConstant.VIDEO_LANG_TYPE_1007, MmsTpConstant.VIDEO_LANG_TYPE_1008, MmsTpConstant.VIDEO_LANG_TYPE_1009 };
    /** Letv中法国地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_FR = { MmsTpConstant.VIDEO_LANG_TYPE_1006,
            MmsTpConstant.VIDEO_LANG_TYPE_1002, MmsTpConstant.VIDEO_LANG_TYPE_1007, MmsTpConstant.VIDEO_LANG_TYPE_1008,
            MmsTpConstant.VIDEO_LANG_TYPE_1009, MmsTpConstant.VIDEO_LANG_TYPE_1000, MmsTpConstant.VIDEO_LANG_TYPE_1001,
            MmsTpConstant.VIDEO_LANG_TYPE_1003, MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1005 };
    /** Letv中德国地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_DE = { MmsTpConstant.VIDEO_LANG_TYPE_1007,
            MmsTpConstant.VIDEO_LANG_TYPE_1002, MmsTpConstant.VIDEO_LANG_TYPE_1006, MmsTpConstant.VIDEO_LANG_TYPE_1008,
            MmsTpConstant.VIDEO_LANG_TYPE_1009, MmsTpConstant.VIDEO_LANG_TYPE_1000, MmsTpConstant.VIDEO_LANG_TYPE_1001,
            MmsTpConstant.VIDEO_LANG_TYPE_1003, MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1005 };
    /** Letv中意大利地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_IT = { MmsTpConstant.VIDEO_LANG_TYPE_1008,
            MmsTpConstant.VIDEO_LANG_TYPE_1002, MmsTpConstant.VIDEO_LANG_TYPE_1006, MmsTpConstant.VIDEO_LANG_TYPE_1007,
            MmsTpConstant.VIDEO_LANG_TYPE_1009, MmsTpConstant.VIDEO_LANG_TYPE_1000, MmsTpConstant.VIDEO_LANG_TYPE_1001,
            MmsTpConstant.VIDEO_LANG_TYPE_1003, MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1005 };
    /** Letv中西班牙地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_ES = { MmsTpConstant.VIDEO_LANG_TYPE_1009,
            MmsTpConstant.VIDEO_LANG_TYPE_1002, MmsTpConstant.VIDEO_LANG_TYPE_1006, MmsTpConstant.VIDEO_LANG_TYPE_1007,
            MmsTpConstant.VIDEO_LANG_TYPE_1008, MmsTpConstant.VIDEO_LANG_TYPE_1000, MmsTpConstant.VIDEO_LANG_TYPE_1001,
            MmsTpConstant.VIDEO_LANG_TYPE_1003, MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1005 };
    /** Letv中其他地区音轨优先级 */
    public static final String[] VIDEO_AUDIO_TRACK_LANG_TYPE_OTHER = { MmsTpConstant.VIDEO_LANG_TYPE_1002,
            MmsTpConstant.VIDEO_LANG_TYPE_1000, MmsTpConstant.VIDEO_LANG_TYPE_1001, MmsTpConstant.VIDEO_LANG_TYPE_1006,
            MmsTpConstant.VIDEO_LANG_TYPE_1007, MmsTpConstant.VIDEO_LANG_TYPE_1008, MmsTpConstant.VIDEO_LANG_TYPE_1009,
            MmsTpConstant.VIDEO_LANG_TYPE_1003, MmsTpConstant.VIDEO_LANG_TYPE_1004, MmsTpConstant.VIDEO_LANG_TYPE_1005 };
    /** Letv中中国地区字幕优先级 */
    public static final String[] VIDEO_SUBTITLE_TYPE_CN = { MmsTpConstant.VIDEO_SUBTITLE_TYPE_1000,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1003, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1001,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1004, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1002 };
    /** Letv中香港地区字幕优先级 */
    public static final String[] VIDEO_SUBTITLE_TYPE_HK = { MmsTpConstant.VIDEO_SUBTITLE_TYPE_1001,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1004, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1000,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1003, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1002 };
    /** Letv中非中国、香港地区字幕优先级 ；除了中国大陆、香港外，其他区域字幕优先级和美国的一样 */
    public static final String[] VIDEO_SUBTITLE_TYPE_US = { MmsTpConstant.VIDEO_SUBTITLE_TYPE_1002,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1003, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1004,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1000, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1001 };

    /** Le中中国地区和除香港、美国外的其他地区字幕优先级 */
    public static final String[] LE_VIDEO_SUBTITLE_TYPE_CN = { MmsTpConstant.VIDEO_SUBTITLE_TYPE_1000,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1003, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1001,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1004, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1002 };
    /** Le中香港地区字幕优先级 */
    public static final String[] LE_VIDEO_SUBTITLE_TYPE_HK = { MmsTpConstant.VIDEO_SUBTITLE_TYPE_1001,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1004, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1000,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1003, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1002 };
    /** Le中非中国、香港地区字幕优先级 ；除了中国大陆、香港外，其他区域字幕优先级和美国的一样 */
    public static final String[] LE_VIDEO_SUBTITLE_TYPE_US = { MmsTpConstant.VIDEO_SUBTITLE_TYPE_1002,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1003, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1004,
            MmsTpConstant.VIDEO_SUBTITLE_TYPE_1000, MmsTpConstant.VIDEO_SUBTITLE_TYPE_1001 };

    /** mp4码流类型 */
    public static final int VIDEO_VTYPE_MP4 = 9;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_180 = 58;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_350 = 21;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_800 = 13;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_1300 = 22;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_720P = 51;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_1080P3M = 52;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_1080P6M = 53;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_4K = 54;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_720P_3D = 30;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_1080P6M_3D = 32;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_180_H265 = 127;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_350_H265 = 128;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_800_H265 = 129;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_1300_H265 = 130;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_720P_H265 = 131;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_1080P3M_H265 = 132;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_1080P6M_H265 = 133;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_2K_H265 = 134;// mp4码流类型
    public static final int VIDEO_VTYPE_MP4_4K_M_H265 = 135;// mp4码流类型

    /**
     * CDE播放媒介，m3u8和MP4流
     */
    public static final String VIDEO_PLAY_MEDIA_FORMAT_EXT_M3U8_DEFAULT = "ext=m3u8";
    public static final String VIDEO_PLAY_MEDIA_FORMAT_MEDIATYPE_MP4_DEFAULT = "mediatype=mp4";

    /** 低 */
    public static final String[] VIDEO_VTYPE_ATYPE_ARRAY_1 = { MmsTpConstant.VIDEO_AUDIO_TYPE_1000 };
    /** 低，高2，高3 */
    public static final String[] VIDEO_VTYPE_ATYPE_ARRAY_2 = { MmsTpConstant.VIDEO_AUDIO_TYPE_1000,
            MmsTpConstant.VIDEO_AUDIO_TYPE_1003, MmsTpConstant.VIDEO_AUDIO_TYPE_1004 };
    /** 中，高2，高3 */
    public static final String[] VIDEO_VTYPE_ATYPE_ARRAY_3 = { MmsTpConstant.VIDEO_AUDIO_TYPE_1001,
            MmsTpConstant.VIDEO_AUDIO_TYPE_1003, MmsTpConstant.VIDEO_AUDIO_TYPE_1004 };
    /** 高1，高2，高3 */
    public static final String[] VIDEO_VTYPE_ATYPE_ARRAY_4 = { MmsTpConstant.VIDEO_AUDIO_TYPE_1002,
            MmsTpConstant.VIDEO_AUDIO_TYPE_1003, MmsTpConstant.VIDEO_AUDIO_TYPE_1004 };

    public static final Integer TERMINAL_X60 = 4;

    /**
     * 视频和专辑“类型”名称文案前缀，完整key是VIDEO_AND_ALBUM_TYPE_NAME_TEXT_PREFIX+{VideoType}
     * 或VIDEO_AND_ALBUM_TYPE_NAME_TEXT_PREFIX+{AlbumType}
     */
    public static final String VIDEO_AND_ALBUM_TYPE_NAME_TEXT_PREFIX = "VIDEO.AND.ALBUM.TYPE.NAME.";

    /**
     * 视频类型
     * @author wanglonghu
     *         2016-07-13，该定义调整到MmsTpConstant中
     */
    public interface VideoType {

        public static final int ZHENG_PIAN = 180001;// 正片

        public static final int YU_GAO_PIAN = 180002;// 预告片

        public static final int XING_ZUO = 181204;// 星座

        public static final int SHENG_XIAO = 181205;// 生肖
    }

    /**
     * 专辑类型
     * @author wanglonghu
     */
    public interface AlbumType {

        public static final int ZHENG_PIAN = 1;// 正片

        public static final int TV = 181031;// TV 版

        public static final int OVA = 181032;// OVA版

        public static final int JU_CHANG = 181033;// 剧场版

        public static final int OAD = 181034;// OAD版181034
    }

    /**
     * 专辑详情页使用常量
     * @author wanglonghu
     */
    public interface AlbumDetail {

        public static final int ALBUM_RELATION_POSTFIX_JI = 1;// 季

        public static final int ALBUM_RELATION_POSTFIX_BU = 2;// 部

        public static final int ALBUM_RELATION_POSTFIX_CHANG = 3;// 场

        public static final int ALBUM_RELATION_POSTFIX_JIE = 4;// 届

        public static final String DFILM_IS_VARIETY_YES = "1";// 纪录片是综艺类型

        public static final String DFILM_IS_VARIETY_NO = "0";// 纪录片不是综艺类型

        public static final String ALBUM_IS_VARITY_SHOW = "1"; // 专辑按照综艺样式展示
    }

    /**
     * 内容类型分类
     * @author wanglonghu
     */
    public interface Category {

        public static final int FILM = 1;// 电影

        public static final int TV = 2;// 电视剧

        public static final int ENT = 3;// 娱乐

        public static final int SPORT = 4;// 体育

        public static final int CARTOON = 5;// 动漫

        public static final int ZIXUN = 1009;// 资讯

        public static final int YUAN_CHUANG = 7;// 原创

        public static final int OTHER = 8;// 其他

        public static final int MUSIC = 9;// 音乐

        public static final int FUNNY = 10;// 搞笑

        public static final int VARIETY = 11;// 综艺

        public static final int KE_JIAO = 12;// 科教

        public static final int SHENG_HUO = 13;// 生活

        public static final int CAR = 14;// 汽车

        public static final int DFILM = 16;// 纪录片

        public static final int GONG_KAI_KE = 17;// 公开课

        public static final int LETV_MADE = 19;// 乐视制造

        public static final int FENG_SHANG = 20;// 风尚

        public static final int CAI_JING = 22;// 财经

        public static final int TRAVEL = 23;// 旅游

        public static final int HOTSPOT = 30;// 热点

        public static final int QU_YI = 32;// 曲艺

        public static final int XI_QU = 33;// 戏曲

        public static final int PARENTING = 34;// 亲子

        public static final int AD = 36;// 广告

        public static final int TEACH = 1021;// 教育

        public static final int TEACH_CHILD = 542015;// 教育频道下的幼儿用作乐视儿童

    }

    /**
     * 地区常量，视频的地区属性
     * @author wanglonghu
     */
    public interface Area {
        public static final String CN = "50001";// 中国大陆

        public static final String HK = "50002";// 香港

        public static final String TW = "50003";// 台湾

        public static final String US = "50071";// 美国
    }

    /**
     * 演员角色
     * @author wanglonghu
     */
    public interface ActorRole {
        public static final int STARRING = 0;// 主演

        public static final int DIRECTOR = 1;// 导演

        public static final int SCRIPTWRITER = 2;// 编剧

        public static final int PRODUCER = 3;// 制片人

        public static final int COMPERE = 4;// 主持人
    }

    /**
     * 音乐专辑类型
     * @author wanglonghu
     */
    public interface MusicSubCategory {

        public static final String CONCERT = "31375";// 演唱会

        public static final String AWARDS = "31377";// 颁奖礼

        public static final String MUSIC_FILM = "31378";// 音乐电影

        public static final String DFILM = "31380";// 纪录片
    }

    public interface NonMemberGuideType {
        /*
         * 引导用户类型：1会员专享，2码流付费，3单点影片，4卡顿
         */
        public static final Integer userType_1 = 1;
        public static final Integer userType_2 = 2;
        public static final Integer userType_3 = 3;
        public static final Integer userType_4 = 4;
        /*
         * 指定默认套餐、支付方式
         */
        public static final String PackageType = "2";
        public static final String PayType = "5";
        public static final String imgurl = "";
        /*
         * 引导用户图片
         */
        public static final String userType_1_img = "1";
        public static final String userType_2_img = "2";
        public static final String userType_3_img = "3";
        public static final String userType_4_img = "4";
    }

    /**
     * 视频播放互动--互动数据类型，1--动态效果，2--投票选项，3--分享
     */
    public static final String VIDEO_REACTION_TYPE_1 = "1";
    public static final String VIDEO_REACTION_TYPE_2 = "2";
    public static final String VIDEO_REACTION_TYPE_3 = "3";

    /**
     * v2.5 一屏焦点图个数
     */
    public static final int SCREEN_SIZE = 15;

    /**
     * 美国le 一屏焦点图个数
     */
    public static final int LE_SCREEN_SIZE = 10;

    /**
     * boss v2统一鉴权--专辑鉴权中，服务端mock的storepath;
     * 需求背景是，专辑鉴权中，也需要取一个视频的storepath(和点播鉴权中的storepath是同一个意思)参与鉴权，服务端处理该业务较麻烦，
     * 且只要鉴权是否成功及不成功时的付费信息，而不要token信息，storepath在此常见下意义不大，故这里mock
     */
    public static final String BOSS_V2_ALBUM_PLAY_AUTH_MOCK_STOREPATH = "tv_mock_storepath.mp4";

    /**
     * 非会员播放卡顿时展示的默认图片URL
     */
    public static final String VIDEO_PLAY_PAUSE_DEFAULT_IMG = ApplicationUtils
            .get(ApplicationConstants.VIDEO_PLAY_PAUSE_DEFAULT_IMG);

    /**
     * 是否展示暂停广告页面默认配置，0--不展示，1或null或其他值则展示；默认展示
     */
    public static final String VIDEO_PLAY_SHOW_PAUSE_AD_PAGE_DEFAULT = ApplicationUtils
            .get(ApplicationConstants.VIDEO_PLAY_SHOW_PAUSE_AD_PAGE_DEFAULT);

    /**
     * 是否展示浮层广告，null或0或其他值--都不展示，1--仅非会员展示广告，2--会员展示广告，3--全是展示
     */
    public static final String VIDEO_PLAY_FLOAT_AD_POLICY_DEFAULT = ApplicationUtils
            .get(ApplicationConstants.VIDEO_PLAY_FLOAT_AD_POLICY_DEFAULT);

    /**
     * 播放接口模拟非会员账户登录参数配置
     */
    public static final boolean VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN = "1".equals(ApplicationUtils
            .get(ApplicationConstants.VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN_SWITCH));
    public static final String VIDEO_PLAY_MOOK_NOT_VIP_LOGIN_USERNAME = ApplicationUtils
            .get(ApplicationConstants.VIDEO_PLAY_MOOK_NOT_VIP_LOGIN_USERNAME);
    public static final String VIDEO_PLAY_MOOK_NOT_VIP_LOGIN_USERID = ApplicationUtils
            .get(ApplicationConstants.VIDEO_PLAY_MOOK_NOT_VIP_LOGIN_USERID);

    /**
     * 收费类型
     * @author wanglonghu
     */
    public static final Integer[] CHARGE_CATEGORY = { Category.FILM, Category.TV, Category.CARTOON, Category.LETV_MADE,
            Category.DFILM, Category.VARIETY, Category.MUSIC };

    public static final Set<Integer> CHARGE_CATEGORY_SET = new HashSet<Integer>();

    public static Boolean ifChargeByCategoryId(Integer categoryId) {
        Boolean isCharge = false;
        if (CHARGE_CATEGORY_SET.contains(categoryId)) {
            isCharge = true;
        }
        return isCharge;
    }

    // refer: http://wiki.letv.cn/display/LETVINNO/02_model
    public static String[] LETV_SERIES_TV = { "Android TV on MStar Amber3", "msm8960", "LeTVX60",
            "Android TV on MStar Amber3 S50", "Android TV on MStar Amber3 S40", "Android TV on MStar Amber3 S50_hotel",
            "LeTVX60_hotel", "Android TV on MStar Amber3 S50_hotel_C", "LeTVX60_hotel_C", "MStar Android TV",
            "LeTVX60_MAX70", "MStar Android TV_S250F", "Android TV on MStar Amber3 S40_hotel_C",
            "MStar Android TV_S240F", "MStar Android TV_S250F_THTF", "GS39", "MStar Android TV_S240F_hotel_C",
            "Android TV on MStar Amber3 MXYTV", "S255U", "Letv S50", "S50", "Letv S40", "S40", "Letv X50 Air",
            "X50 Air", "Letv X55 Air", "X55 Air", "Letv S50 Air", "S50 Air", "Letv GS39", "GS39", "Letv S40 Air",
            "S40 Air", "Letv X3-65", "X3-65", "Letv S43 Air", "S43 Air", "Letv Max3-65", "Max3-65", "Letv X3-55",
            "X3-55", "Letv X3-40", "X3-40", "Letv X3-50 UHD", "X3-50 UHD", "Letv X3-43", "X3-43", "Letv S40 Air L",
            "S40 Air L", "Letv Max4-70", "Max4-70", "Letv X3-55 Pro", "X3-55 Pro", "Letv Max-120", "Max-120",
            "Letv Max3-120", "Max3-120", "Letv_X910_whole-netcom", "Letv CES65", "CES65", "Letv X3-50", "X3-50",
            "Letv Max4-65 Curved", "Max4-65 Curved", "Letv_X500_default", "Letv X3-43S", "X3-43S", "Letv X3-40S",
            "X3-40S", "Letv X4-50", "X4-50", "Letv X4-50 Pro", "X4-50 Pro", "Letv uMax120", "uMax120", "Le_X507_india",
            "Letv X4-40", "X4-40", "Letv X4-43", "X4-43", "Letv X4-49", "X4-49", "Letv X4-49 Pro", "X4-49 Pro",
            "Letv X4-55", "X4-55", "Letv X4-55 Curved", "X4-55 Curved", "Letv X4-65", "X4-65", "Letv X4-65 Curved",
            "X4-65 Curved", "uMAX85", "uMAX120S", "X4-43Pro" };

    public static String[] LETV_SERIES_BOX = { "C2", "Letv C1", "Letv C1S", "Letv C1S", "Letv C1S", "Letv C1S",
            "Letv G1", "Letv New C1S", "Letv T1S", "Letv U1", "Letv U2", "Letv U3", "Letv U4", "U3", "U4C", "U4S" };

    public static String[] LETV_SUPPORT_4K_SERIES = { "C2_C2", "MStar Android TV", "S255U" };

    public static Set<String> LETV_SERIES_TV_SET = new HashSet<String>();

    public static Set<String> LETV_SERIES_BOX_SET = new HashSet<String>();

    public static Set<String> LETV_SUPPORT_4K_SERIES_SET = new HashSet<String>();

    /**
     * 字幕--类型与文案的对应关系集合
     */
    public static Map<String, String> subTitleMap = new HashMap<String, String>();

    /**
     * 音质码率--类型与文案的对应关系集合
     */
    public static Map<String, String> audioTypeMap = new HashMap<String, String>();

    /**
     * 音频语言--类型与文案的对应关系集合
     */
    public static Map<String, String> langTypeMap = new HashMap<String, String>();

    /**
     * 区域和默认字幕类型对应关系集合
     */
    public static Map<String, String[]> defaultSubtitleTypeMap = new HashMap<String, String[]>();

    /**
     * TerminalApplication、Sealareas和默认字幕类型对应关系集合
     */
    public static Map<String, Map<String, String[]>> defaultTerminalSealareasSubtitleTypeMap = new HashMap<String, Map<String, String[]>>();

    /**
     * 区域和默认音轨语种类型对应关系集合
     */
    public static Map<String, String[]> defaultAudtioTrackLangTypeMap = new HashMap<String, String[]>();

    /**
     * 区域和默认音轨语种类型对应关系集合
     */
    public static Map<String, String[]> vtypeAtypeMap = new HashMap<String, String[]>();

    public static final int HAS_BELOW_YES = 1;// 降码流标识
    public static final int HAS_BELOW_NO = 0;// 未降码流标识

    /**
     * 作品库内、外网专辑视频前缀
     */
    public static final String LETV_ALBUM_PREFIX = "110_";
    public static final String LETV_VIDEO_PREFIX = "101_";
    public static final String WEBSITE_ALBUM_PREFIX = "205_";
    public static final String WEBSITE_VIDEO_PREFIX = "202_";

    /**
     * 播放模式，0--TV版播放，1--乐视儿童播放，2--投屏播放
     */
    public static int PLAY_MODEL_COMMON = 0;
    public static int PLAY_MODEL_CHILD = 1;
    public static int PLAY_MODEL_TOUPING = 2;

    /**
     * 投屏资源类型，1--点播，2--直播，3--轮播，4--卫视台，在投屏点播逻辑中，该值可不传，不影响业务
     */
    public static final int PLAY_TOUPING_SOURCE_TYPE_VIDEO_PLAY = 1;
    public static final int PLAY_TOUPING_SOURCE_TYPE_LIVE = 2;
    public static final int PLAY_TOUPING_SOURCE_TYPE_LUNBO = 3;
    public static final int PLAY_TOUPING_SOURCE_TYPE_WEISHI = 4;

    /**
     * 专辑内各种视频播放列表的类型定义，0--所有剧集，1--正片剧集（选集），2--预告，3--周边视频，4--推荐
     */
    public static final int ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ALL = 0;
    public static final int ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE = 1;
    public static final int ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_PRE = 2;
    public static final int ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ATTACHING = 3;
    public static final int ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_REC = 4;

    /**
     * 专辑剧集按分页获取
     */
    public static final int ALBUM_VIDEO_SERIES_IS_PAGED = 1;

    /**
     * 播放下拉列表获取方式，1--根据vid，2--根据分页数据
     */
    public static final int VIDEO_PLAY_SERIES_PAGE_GET_BY_VIDEO_ID = 1;
    public static final int VIDEO_PLAY_SERIES_PAGE_GET_BY_PAGE = 2;

    /**
     * 播放下拉列表业务类型，1--专辑详情页，2--播放下拉列表
     */
    public static final int VIDEO_PLAY_SERIES_PAGE_FUNCTION_TYPE_ALBUM_DETAIL = 1;
    public static final int VIDEO_PLAY_SERIES_PAGE_FUNCTION_TYPE_PLAY_LIST = 2;

    /**
     * 2016-11-03，Lecom播放白名单使用的默认IP；联调发现媒资le接口不能像大陆接口一样支持防盗链接口clientip传null，
     * 这里先兼容
     */
    public static final String PLAY_WHITE_LIST_DEFAULT_CLIENT_IP = ApplicationUtils
            .get(ApplicationConstants.VIDEO_PLAY_WHITE_LIST_DEFAULT_CLIENT_IP);

    /**
     * 使用新版本cde的TV Le版本号，新版本cde可以识别新的splatid(513)，而老版本只能识别504
     */
    public static final int PLAY_HAS_NEW_CDE_LE_VERSION = 277;

    static {
        for (Integer categoryId : CHARGE_CATEGORY) {
            CHARGE_CATEGORY_SET.add(categoryId);
        }

        for (String letvSeries : LETV_SERIES_TV) {
            LETV_SERIES_TV_SET.add(letvSeries);
        }

        for (String letvSeries : LETV_SERIES_BOX) {
            LETV_SERIES_BOX_SET.add(letvSeries.toUpperCase());
        }

        for (String series : LETV_SUPPORT_4K_SERIES) {
            LETV_SUPPORT_4K_SERIES_SET.add(series);
        }

        subTitleMap.put(MmsTpConstant.VIDEO_SUBTITLE_TYPE_1000, VIDEO_SUBTITLE_TYPE_TEXT_KEY_1000);
        subTitleMap.put(MmsTpConstant.VIDEO_SUBTITLE_TYPE_1001, VIDEO_SUBTITLE_TYPE_TEXT_KEY_1001);
        subTitleMap.put(MmsTpConstant.VIDEO_SUBTITLE_TYPE_1002, VIDEO_SUBTITLE_TYPE_TEXT_KEY_1002);
        subTitleMap.put(MmsTpConstant.VIDEO_SUBTITLE_TYPE_1003, VIDEO_SUBTITLE_TYPE_TEXT_KEY_1003);
        subTitleMap.put(MmsTpConstant.VIDEO_SUBTITLE_TYPE_1004, VIDEO_SUBTITLE_TYPE_TEXT_KEY_1004);

        audioTypeMap.put(MmsTpConstant.VIDEO_AUDIO_TYPE_1000, "");// 低音质，不要括号，所以为空
        audioTypeMap.put(MmsTpConstant.VIDEO_AUDIO_TYPE_1001, "");// 中音质，不要括号，所以为空
        audioTypeMap.put(MmsTpConstant.VIDEO_AUDIO_TYPE_1002, "");// 高1音质，不要括号，所以为空
        audioTypeMap.put(MmsTpConstant.VIDEO_AUDIO_TYPE_1003, VIDEO_AUDIO_TYPE_TEXT_KEY_1003);// 高2音质，括号中显示dolby
        audioTypeMap.put(MmsTpConstant.VIDEO_AUDIO_TYPE_1004, VIDEO_AUDIO_TYPE_TEXT_KEY_1004);// 高3音质，括号中显示DTS

        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1000, VIDEO_LANG_TYPE_TEXT_KEY_1000);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1001, VIDEO_LANG_TYPE_TEXT_KEY_1001);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1002, VIDEO_LANG_TYPE_TEXT_KEY_1002);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1003, VIDEO_LANG_TYPE_TEXT_KEY_1003);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1004, VIDEO_LANG_TYPE_TEXT_KEY_1004);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1005, VIDEO_LANG_TYPE_TEXT_KEY_1005);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1006, VIDEO_LANG_TYPE_TEXT_KEY_1006);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1007, VIDEO_LANG_TYPE_TEXT_KEY_1007);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1008, VIDEO_LANG_TYPE_TEXT_KEY_1008);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1009, VIDEO_LANG_TYPE_TEXT_KEY_1009);
        langTypeMap.put(MmsTpConstant.VIDEO_LANG_TYPE_1010, VIDEO_LANG_TYPE_TEXT_KEY_1010);

        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.CN, VIDEO_SUBTITLE_TYPE_CN);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.HK, VIDEO_SUBTITLE_TYPE_HK);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.US, VIDEO_SUBTITLE_TYPE_US);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.UK, VIDEO_SUBTITLE_TYPE_US);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.KR, VIDEO_SUBTITLE_TYPE_US);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.JP, VIDEO_SUBTITLE_TYPE_US);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.RU, VIDEO_SUBTITLE_TYPE_US);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.FR, VIDEO_SUBTITLE_TYPE_US);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.DE, VIDEO_SUBTITLE_TYPE_US);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.IT, VIDEO_SUBTITLE_TYPE_US);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.ES, VIDEO_SUBTITLE_TYPE_US);
        defaultSubtitleTypeMap.put(LocaleConstant.Wcode.OTHER, VIDEO_SUBTITLE_TYPE_US);

        Map<String, String[]> leSubtitleTypeMap = new HashMap<String, String[]>();
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.CN, LE_VIDEO_SUBTITLE_TYPE_CN);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.HK, LE_VIDEO_SUBTITLE_TYPE_HK);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.US, LE_VIDEO_SUBTITLE_TYPE_US);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.UK, VIDEO_SUBTITLE_TYPE_US);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.KR, VIDEO_SUBTITLE_TYPE_US);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.JP, VIDEO_SUBTITLE_TYPE_US);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.RU, VIDEO_SUBTITLE_TYPE_US);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.FR, VIDEO_SUBTITLE_TYPE_US);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.DE, VIDEO_SUBTITLE_TYPE_US);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.IT, VIDEO_SUBTITLE_TYPE_US);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.ES, VIDEO_SUBTITLE_TYPE_US);
        leSubtitleTypeMap.put(LocaleConstant.SalesArea.OTHER, VIDEO_SUBTITLE_TYPE_US);

        defaultTerminalSealareasSubtitleTypeMap.put(TerminalCommonConstant.TERMINAL_APPLICATION_LETV,
                defaultSubtitleTypeMap);
        defaultTerminalSealareasSubtitleTypeMap.put(TerminalCommonConstant.TERMINAL_APPLICATION_LETV_COMMON,
                defaultSubtitleTypeMap);
        defaultTerminalSealareasSubtitleTypeMap.put(TerminalCommonConstant.TERMINAL_APPLICATION_CIBN,
                defaultSubtitleTypeMap);
        defaultTerminalSealareasSubtitleTypeMap.put(TerminalCommonConstant.TERMINAL_APPLICATION_LE, leSubtitleTypeMap);

        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.CN, VIDEO_AUDIO_TRACK_LANG_TYPE_CN);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.HK, VIDEO_AUDIO_TRACK_LANG_TYPE_HK);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.US, VIDEO_AUDIO_TRACK_LANG_TYPE_US);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.UK, VIDEO_AUDIO_TRACK_LANG_TYPE_US);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.KR, VIDEO_AUDIO_TRACK_LANG_TYPE_KR);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.JP, VIDEO_AUDIO_TRACK_LANG_TYPE_JP);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.RU, VIDEO_AUDIO_TRACK_LANG_TYPE_RU);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.FR, VIDEO_AUDIO_TRACK_LANG_TYPE_FR);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.DE, VIDEO_AUDIO_TRACK_LANG_TYPE_DE);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.IT, VIDEO_AUDIO_TRACK_LANG_TYPE_IT);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.ES, VIDEO_AUDIO_TRACK_LANG_TYPE_ES);
        defaultAudtioTrackLangTypeMap.put(LocaleConstant.Wcode.OTHER, VIDEO_AUDIO_TRACK_LANG_TYPE_OTHER);

        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4), VIDEO_VTYPE_ATYPE_ARRAY_1);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_180), VIDEO_VTYPE_ATYPE_ARRAY_1);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_350), VIDEO_VTYPE_ATYPE_ARRAY_1);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_800), VIDEO_VTYPE_ATYPE_ARRAY_2);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_1300), VIDEO_VTYPE_ATYPE_ARRAY_3);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_720P), VIDEO_VTYPE_ATYPE_ARRAY_3);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_1080P3M), VIDEO_VTYPE_ATYPE_ARRAY_4);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_1080P6M), VIDEO_VTYPE_ATYPE_ARRAY_4);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_4K), VIDEO_VTYPE_ATYPE_ARRAY_4);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_720P_3D), VIDEO_VTYPE_ATYPE_ARRAY_3);
        vtypeAtypeMap.put(String.valueOf(VIDEO_VTYPE_MP4_1080P6M_3D), VIDEO_VTYPE_ATYPE_ARRAY_4);
    }

    /**
     * 根据字幕类型返回对应的文案key值
     * @param type
     * @return
     */
    public static String getVideoSubtitleTextKey(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        return subTitleMap.get(type);
    }

    /**
     * 根据语种类型返回对应的文案key值
     * @param type
     * @return
     */
    public static String getVideoLangTextKey(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        return langTypeMap.get(type);
    }

    /**
     * 根据码率类型返回对应的文案key值
     * @param type
     * @return
     */
    public static String getVideoKpbsTextKey(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        return audioTypeMap.get(type);
    }

    /**
     * 根据区域获取默认的字幕类型
     * @param wcode
     * @return
     */
    public static String[] getDefaultSubtitleType(String wcode) {
        if (StringUtils.isBlank(wcode)) {
            return null;
        }
        return defaultSubtitleTypeMap.get(wcode);
    }

    public static String[] getDefaultSubtitleType(CommonParam commonParam) {
        String[] subtitleTypes = null;
        if (!StringUtils.isBlank(commonParam.getTerminalApplication())
                && !StringUtils.isBlank(commonParam.getSalesArea())) {
            Map<String, String[]> subtitleTypeMap = defaultTerminalSealareasSubtitleTypeMap.get(StringUtils
                    .lowerCase(commonParam.getTerminalApplication()));
            if (subtitleTypeMap != null) {
                subtitleTypes = subtitleTypeMap.get(StringUtils.upperCase(commonParam.getSalesArea()));
            }
        }

        return subtitleTypes;
    }

    /**
     * 根据区域获取默认的音轨语种类型
     * @param wcode
     * @return
     */
    public static String[] getDefaultAudioTrackLangType(String wcode) {
        if (StringUtils.isBlank(wcode)) {
            return null;
        }
        return defaultAudtioTrackLangTypeMap.get(wcode);
    }

    /**
     * 根据码流类型获取该码流支持的音质类型列表
     * @param vtype
     * @return
     */
    public static String[] getAtypeByVtype(Integer vtype) {
        if (vtype == null) {
            return null;
        }
        return vtypeAtypeMap.get(String.valueOf(vtype.intValue()));
    }
}
