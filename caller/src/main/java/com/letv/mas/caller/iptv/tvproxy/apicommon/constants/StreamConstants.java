package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.StreamCode;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.Stream;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoStream;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 码流相关属性
 * @author Administrator
 */
public class StreamConstants {

    public static final Map<String, Integer> STREAM_CODE_SORT_VSLUE = new HashMap<String, Integer>();// 码流排序值
    public static final Map<String, Integer> STREAM_CODE_SORT_VSLUE_SUPERLIVE = new HashMap<String, Integer>();// superlive码流排序值
    public static final Map<String, String> STREAM_CODE_NAME_MAP = new HashMap<String, String>();// 码流对应的中文名称
    public static final String CODE_NAME_99 = "99";
    public static final String CODE_NAME_180 = "180";
    public static final String CODE_NAME_350 = "350";
    public static final String CODE_NAME_800 = "800";
    public static final String CODE_NAME_1000 = "1000";
    public static final String CODE_NAME_1300 = "1300";
    public static final String CODE_NAME_720p = "720p";
    public static final String CODE_NAME_1080p = "1080p";
    public static final String CODE_NAME_1080p3m = "1080p3m";
    public static final String CODE_NAME_1080p6m = "1080p6m";
    public static final String CODE_NAME_2K = "2k";// 2k
    public static final String CODE_NAME_2K_H265 = "2k_h265";// 2k
    public static final String CODE_NAME_4K = "4k";// 4k

    public static final String CODE_NAME_3d720p = "3d720p";
    public static final String CODE_NAME_3d1080p = "3d1080p";
    public static final String CODE_NAME_3d1080p6M = "3d1080p6m";
    // 杜比新增加码流--start
    public static final String CODE_NAME_DOLBY_800 = "800_db";
    public static final String CODE_NAME_DOLBY_1300 = "1300_db";
    public static final String CODE_NAME_DOLBY_720p = "720p_db";
    public static final String CODE_NAME_DOLBY_1080p = "1080p_db";
    public static final String CODE_NAME_DOLBY_1080p6m = "1080p6m_db";
    public static final String CODE_NAME_DOLBY_2K = "2k_db";
    public static final String CODE_NAME_DOLBY_4K = "4k_db";

    public static final String CODE_NAME_DTS_800 = "800_dts";
    public static final String CODE_NAME_DTS_1300 = "1300_dts";
    public static final String CODE_NAME_DTS_720p = "720p_dts";
    public static final String CODE_NAME_DTS_1080p = "1080p_dts";
    public static final String CODE_NAME_DTS_1080p3m = "1080p3m_dts";
    public static final String CODE_NAME_DTS_1080p6m = "1080p6m_dts";
    public static final String CODE_NAME_DTS_2K = "2k_dts";
    public static final String CODE_NAME_DTS_2K_H265 = "2k_h265_dts";
    public static final String CODE_NAME_DTS_4K = "4k_dts";

    // 第三方cp码流
    public static final String CODE_NAME_CP_64 = "cp64";
    public static final String CODE_NAME_CP_200 = "cp200";
    public static final String CODE_NAME_CP_350 = "cp350";
    public static final String CODE_NAME_CP_400 = "cp400";
    public static final String CODE_NAME_CP_600 = "cp600";
    public static final String CODE_NAME_CP_800 = "cp800";
    public static final String CODE_NAME_CP_1100 = "cp1100";
    public static final String CODE_NAME_CP_1500 = "cp1500";
    public static final String CODE_NAME_CP_1800 = "cp1800";
    public static final String CODE_NAME_CP_2500 = "cp2500";

    static {
        STREAM_CODE_NAME_MAP.put(CODE_NAME_99, "原画");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_180, "极速");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_350, "流畅");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_800, "标清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1000, "标清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1300, "高清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_720p, "超清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p3m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p6m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_2K, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_2K_H265, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_4K, "4K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3d1080p, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3d720p, "超清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3d1080p6M, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_800, "标清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1300, "高清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_720p, "超清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1080p, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1080p6m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_2K, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_4K, "4K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_800, "标清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_1300, "高清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_720p, "超清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_1080p, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_1080p3m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_1080p6m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_2K, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_2K_H265, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_4K, "4K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_64, "144p");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_200, "216p");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_350, "270p");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_400, "270p");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_600, "288p");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_800, "288p");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_1100, "396p");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_1500, "432p");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_1800, "540p");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_CP_2500, "720p");

        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_350, 1);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1000, 2);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1300, 3);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_720p, 4);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p, 5);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p3m, 6);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p6m, 7);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d1080p, 8);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d720p, 9);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d1080p6M, 10);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_720p, 11);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1300, 12);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_800, 13);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1080p, 14);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_4K, 15);

        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_99, 0);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_350, 1);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_1000, 2);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_1300, 3);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_720p, 4);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_1080p, 5);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_1080p3m, 6);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_1080p6m, 7);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_3d1080p, 8);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_3d720p, 9);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_3d1080p6M, 10);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_DOLBY_720p, 11);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_DOLBY_1300, 12);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_DOLBY_800, 13);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_DOLBY_1080p, 14);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_4K, 15);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_64, 1);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_200, 2);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_350, 3);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_400, 4);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_600, 5);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_800, 6);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_1100, 7);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_1500, 8);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_1800, 9);
        STREAM_CODE_SORT_VSLUE_SUPERLIVE.put(CODE_NAME_CP_2500, 10);
    }

    public static String getSearchStremId(String streams) {
        StringBuffer sb = new StringBuffer();
        try {
            if (streams != null && streams.length() > 0) {
                String[] s = streams.split(",");
                if (s != null && s.length > 0) {
                    for (String stream : s) {
                        if ("1080p".equalsIgnoreCase(stream)) {
                            stream = LetvStreamCommonConstants.VIDEO_TYPE_MP4_PREFIX + stream.toLowerCase();
                        } else if ("350".equalsIgnoreCase(stream) || "1000".equalsIgnoreCase(stream)
                                || "1300".equalsIgnoreCase(stream) || "720p".equalsIgnoreCase(stream)
                                || "1080p6m".equalsIgnoreCase(stream) || "1080p3m".equalsIgnoreCase(stream)) {
                            stream = LetvStreamCommonConstants.VIDEO_TYPE_FLV_PREFIX + stream.toLowerCase();
                        } else if ("3d1080p".equalsIgnoreCase(stream)) {
                            stream = LetvStreamCommonConstants.VIDEO_TYPE_FLV_PREFIX + stream.toLowerCase();
                        } else if ("3d1080p6m".equalsIgnoreCase(stream)) {
                            stream = LetvStreamCommonConstants.VIDEO_TYPE_FLV_PREFIX + stream.toLowerCase();
                        } else if ("3d720p".equalsIgnoreCase(stream)) {
                            stream = LetvStreamCommonConstants.VIDEO_TYPE_FLV_PREFIX + stream.toLowerCase();
                        } else if ("720p_db".equalsIgnoreCase(stream) || "1300_db".equalsIgnoreCase(stream)
                                || "800_db".equalsIgnoreCase(stream) || "1080p6m_db".equalsIgnoreCase(stream)
                                || "4k".equalsIgnoreCase(stream)) {
                            stream = LetvStreamCommonConstants.VIDEO_TYPE_MP4_PREFIX + stream.toLowerCase();
                        }
                        String id = LetvStreamCommonConstants.SEARCH_STREAM_CODE_MAP.get(stream);
                        if (id != null) {
                            sb.append(id).append(",");
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static List<StreamCode> getStreamCode(String sort_stream, Integer cid, String langcode) {
        List<StreamCode> list = new ArrayList<StreamCode>();
        String[] streams = sort_stream.split("#");
        for (String stream : streams) {
            StreamCode v = new StreamCode();
            v.setCode(stream);
            v.setEnabled("0");
            v.setIfCharge(LetvStreamCommonConstants.ifChargeByCategoryId(cid) ? "1" : "0");
            v.setIfDownloadCharge(LetvStreamCommonConstants.STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.get(stream));
            v.setIfCanDown(LetvStreamCommonConstants.STREAM_CODE_CANDOWN.get(stream));
            v.setIfCanPlay(LetvStreamCommonConstants.STREAM_CODE_CANPLAY.get(stream));
            v.setName(LetvStreamCommonConstants.nameOf(stream, langcode));
            v.setTipText(LetvStreamCommonConstants.STREAM_CODE_TIP_MAP.get(stream));
            list.add(v);
        }
        return list;
    }

    public static List<Stream> getStreamCode(String sort_stream, String locale) {
        List<Stream> list = new ArrayList<Stream>();
        String[] streams = sort_stream.split("#");
        for (String stream : streams) {
            Stream v = new Stream();
            v.setCode(stream);
            v.setCanDown(LetvStreamCommonConstants.STREAM_CODE_CANDOWN.get(stream));
            v.setCanPlay(LetvStreamCommonConstants.STREAM_CODE_CANPLAY.get(stream));
            if (StringUtils.isNotEmpty(MessageUtils.getMessage(
                    LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream), locale))) {
                v.setName(MessageUtils.getMessage(LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream), locale));
            } else {
                v.setName(LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream));
            }

            if ("en_us".equalsIgnoreCase(locale) || "en".equalsIgnoreCase(locale)) {
                v.setBandWidth(MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", locale) + " "
                        + LetvStreamCommonConstants.STREAM_CODE_TIP_MAP.get(stream));
            } else {
                v.setBandWidth(LetvStreamCommonConstants.STREAM_CODE_TIP_MAP.get(stream)
                        + MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", locale));
            }
            list.add(v);
        }
        return list;
    }

    public static Map<String, Stream> getStreamMap(String sortStream, String locale, boolean isThirdParty) {
        Map<String, Stream> streamMap = new HashMap<String, Stream>();
        String[] streams = sortStream.split("#");
        for (String stream : streams) {
            Stream v = new Stream();
            v.setCode(stream);
            v.setCanDown(LetvStreamCommonConstants.STREAM_CODE_CANDOWN.get(stream));
            v.setCanPlay(LetvStreamCommonConstants.STREAM_CODE_CANPLAY.get(stream));
            if (StringUtils.isNotEmpty(MessageUtils.getMessage(
                    LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream), locale))) {
                v.setName(MessageUtils.getMessage(LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream), locale));
            } else {
                v.setName(LetvStreamCommonConstants.STREAM_CODE_FILTER_MAP.get(stream));
            }
            if ("en_us".equalsIgnoreCase(locale) || "en".equalsIgnoreCase(locale)) {
                v.setBandWidth(MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", locale)
                        + LetvStreamCommonConstants.STREAM_CODE_TIP_MAP.get(stream));
            } else {
                v.setBandWidth(LetvStreamCommonConstants.STREAM_CODE_TIP_MAP.get(stream)
                        + MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", locale));
            }
            v.setIfCharge(LetvStreamCommonConstants.CHARGE_STREAM_SET.contains(stream) ? 1 : 0);
            if (isThirdParty) {
                v.setIfCharge(LetvStreamCommonConstants.THIRD_PARTY_CHARGE_STREAM_SET.contains(stream) ? 1 : 0);
            } else {
                v.setIfCharge(LetvStreamCommonConstants.CHARGE_STREAM_SET.contains(stream) ? 1 : 0);
            }
            v.setKbps(LetvStreamCommonConstants.STREAM_CODE_KBPS_MAP.get(stream));
            streamMap.put(stream, v);
        }

        return streamMap;
    }

    public static Map<String, Stream> getYuanXianStreamMap(String sortStream, String locale) {
        Map<String, Stream> streamMap = new HashMap<String, Stream>();
        String[] streams = sortStream.split("#");
        for (String stream : streams) {
            Stream v = new Stream();
            v.setCode(stream);
            v.setCanDown(LetvStreamCommonConstants.STREAM_CODE_CANDOWN.get(stream));
            v.setCanPlay(LetvStreamCommonConstants.STREAM_CODE_CANPLAY.get(stream));
            if (StringUtils.isNotEmpty(MessageUtils.getMessage(
                    LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream), locale))) {
                v.setName(MessageUtils.getMessage(LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream), locale));
            } else {
                v.setName(LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream));
            }
            if ("en_us".equalsIgnoreCase(locale) || "en".equalsIgnoreCase(locale)) {
                v.setBandWidth(MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", locale)
                        + LetvStreamCommonConstants.STREAM_CODE_TIP_MAP.get(stream));
            } else {
                v.setBandWidth(LetvStreamCommonConstants.STREAM_CODE_TIP_MAP.get(stream)
                        + MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", locale));
            }
            v.setIfCharge(LetvStreamCommonConstants.YUANXIAN_CHARGE_STREAM_SET.contains(stream) ? 1 : 0);
            v.setKbps(LetvStreamCommonConstants.STREAM_CODE_KBPS_MAP.get(stream));
            streamMap.put(stream, v);
        }

        return streamMap;
    }

    public static List<Stream> getStreamCodeForYuanxian(String sort_stream, String langCode) {
        List<Stream> list = new ArrayList<Stream>();
        String[] streams = sort_stream.split("#");
        for (String stream : streams) {
            Stream v = new Stream();
            v.setCode(stream);
            v.setCanDown(LetvStreamCommonConstants.STREAM_CODE_CANDOWN.get(stream));
            v.setCanPlay(LetvStreamCommonConstants.STREAM_CODE_CANPLAY.get(stream));
            if (StringUtils.isNotEmpty(MessageUtils.getMessage(
                    LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream), langCode))) {
                v.setName(MessageUtils.getMessage(LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream), langCode));
            } else {
                v.setName(LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream));
            }
            if ("en_us".equalsIgnoreCase(langCode) || "en".equalsIgnoreCase(langCode)) {
                v.setBandWidth(MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", langCode) + " "
                        + LetvStreamCommonConstants.STREAM_CODE_TIP_MAP.get(stream));
            } else {
                v.setBandWidth(LetvStreamCommonConstants.STREAM_CODE_TIP_MAP.get(stream)
                        + MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", langCode));
            }
            list.add(v);
        }
        return list;
    }

    public static List<VideoStream> getVideoStreamForYuanXian(String sort_stream) {
        List<VideoStream> list = new ArrayList<VideoStream>();
        String[] streams = sort_stream.split("#");
        for (String stream : streams) {
            VideoStream v = new VideoStream();
            v.setCode(stream);
            v.setEnabled("0");
            v.setKbps(LetvStreamCommonConstants.STREAM_CODE_KBPS_MAP.get(stream));
            v.setIfCanDown(LetvStreamCommonConstants.STREAM_CODE_CANDOWN_V1.get(stream));
            v.setIfCharge(LetvStreamCommonConstants.STREAM_CODE_IFCHARGE_YUANXIAN_MAP.get(stream));
            v.setIfgetDown(LetvStreamCommonConstants.STREAM_CODE_ONLYDOWN.get(stream));
            v.setName(LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream));
            list.add(v);
        }
        return list;
    }

    public static List<VideoStream> getVideoStream(String sort_stream) {
        List<VideoStream> list = new ArrayList<VideoStream>();
        String[] streams = sort_stream.split("#");
        for (String stream : streams) {
            VideoStream v = new VideoStream();
            v.setCode(stream);
            v.setEnabled("0");
            v.setKbps(LetvStreamCommonConstants.STREAM_CODE_KBPS_MAP.get(stream));
            v.setIfCanDown(LetvStreamCommonConstants.STREAM_CODE_CANDOWN_V1.get(stream));
            v.setIfCharge(LetvStreamCommonConstants.STREAM_CODE_IFCHARGE_MAP.get(stream));
            v.setIfgetDown(LetvStreamCommonConstants.STREAM_CODE_ONLYDOWN.get(stream));
            v.setName(LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(stream));
            list.add(v);
        }
        return list;
    }
}
