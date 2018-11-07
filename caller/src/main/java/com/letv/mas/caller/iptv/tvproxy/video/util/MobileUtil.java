package com.letv.mas.caller.iptv.tvproxy.video.util;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.LiveUserPlayAuth;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.JsonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.video.constants.MobileConstant;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.Stream;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoHot;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class MobileUtil {

    public static Map<String, Stream> PRE_SET_STREAM = new HashMap<String, Stream>();

    public static Map<String, Stream> PRE_SET_STREAM_ZH_CN = new HashMap<String, Stream>();
    public static Map<String, Stream> PRE_SET_STREAM_ZH_HK = new HashMap<String, Stream>();
    public static Map<String, Stream> PRE_SET_STREAM_EN_US = new HashMap<String, Stream>();

    /**
     * 判断专辑是否是手机端付费
     * @param isPay
     * @param payPlatform
     * @return
     */
    public static Boolean isMobPay(Integer isPay, String payPlatform) {
        if (isPay != null && isPay == 1 && StringUtils.isNotBlank(payPlatform) && payPlatform.contains("141003")) {
            return true;
        }
        return false;
    }

    /**
     * 获得片头、片尾时间
     * @param cid
     * @param video
     * @return
     */
    public static VideoHot getHeadTailInfo(Integer categoryId, Integer bTime, Integer eTime) {
        VideoHot h = new VideoHot();
        if (categoryId != null && VideoConstants.Category.TV == categoryId) {
            h.setT(bTime == null ? 0 : bTime * 1000);
            h.setF(eTime == null ? 0 : eTime * 1000);
        }
        return h;
    }

    public static String getPic(String picCollections, Integer width, Integer hight) {
        if (StringUtil.isBlank(picCollections)) {
            return "";
        }
        try {
            Map<String, String> picAll = JsonUtil.parse(picCollections, new LetvTypeReference<Map<String, String>>() {
            });
            return picAll.get(width + "*" + hight);
        } catch (Exception e) {
        }

        return "";
    }

    public static String getPic(Integer width, Integer hight, String picAll, String transCodePrefix) {
        if (picAll == null && transCodePrefix == null) {
            return "";
        }
        String img = null;
        try {
            if (picAll != null) {
                Map<String, String> picAllMap = JsonUtil.parse(picAll, new LetvTypeReference<Map<String, String>>() {
                });
                img = picAllMap.get(width + "*" + hight);
            }
        } catch (Exception e) {

        }
        if (transCodePrefix != null && transCodePrefix.trim().length() > 0 && img == null) {
            img = transCodePrefix + "/thumb/2_" + width + "_" + hight + ".jpg";
        }

        return img == null ? "" : img;
    }

    /**
     * 根据码流获得VTYPE值
     * @param streamCode
     * @return
     */
    public static String getVType(String streamCode) {
        String vType = null;
        if (StringUtils.isNotEmpty(streamCode)) {
            vType = MobileConstant.VTYPE_REDUCED_MAP.get(streamCode);
        }

        return vType;
    }

    /**
     * 获得子平台ID
     * @param wcode
     * @param terminalApplication
     * @param terminalSeries
     * @return
     */
    public static int getSplatId(String wcode, String terminalApplication, String terminalSeries, String businessId) {
        int splatId = 1503;

        Integer splatid = MobileConstant.COMMON_PLAYER_SPLATID.get(businessId);
        if (splatid != null) {
            splatId = splatid.intValue();
        }

        return splatId;
    }

    /**
     * 获得子平台ID
     * @param wcode
     * @param terminalApplication
     * @param terminalSeries
     * @return
     */
    public static int getPlatId(String wcode, String terminalApplication, String terminalSeries, String businessId) {
        int splatId = 15;

        Integer splatid = MobileConstant.COMMON_PLAYER_PLATID.get(businessId);
        if (splatid != null) {
            splatId = splatid.intValue();
        }

        return splatId;
    }

    /**
     * 获得VIP G3调度地址
     * @return
     */
    public static String getVIPUrl(String uid, LiveUserPlayAuth userPlayAuth, String playUrl) {
        String userPlayUrl = playUrl;

        // VIP 用户加入token和uid
        if (userPlayAuth != null && userPlayAuth.getCode() != null && userPlayAuth.getCode().intValue() == 0
                && userPlayAuth.getValues() != null && "1".equalsIgnoreCase(userPlayAuth.getValues().getStatus())) {
            userPlayUrl = playUrl + "&token=" + userPlayAuth.getValues().getToken() + "&uid=" + uid;
        }

        return userPlayUrl;
    }

    public static String getMobileUrlWithParam(String playUrl, String vid) {
        playUrl = playUrl + "&p1=0&p2=00&p3=008&format=1&sign=mb&dname=mobile&expect=3&tag=mobile";
        if (StringUtils.isNotEmpty(vid)) {
            playUrl = playUrl + "&vid=" + vid;
        }
        return playUrl;
    }

    /**
     * m3v=3 10s ts
     * @param playUrl
     * @return
     */
    public static String getMobileUrl4MediaPlayer(String playUrl) {
        playUrl = playUrl + "&ext=m3u8&m3v=3";

        return playUrl;
    }

    /**
     * 获得预置码流
     * @return
     */
    public static Map<String, Stream> getPreSetStream(String locale, String sourceSite) {
        if ("zh_hk".equalsIgnoreCase(locale)) {
            if (CollectionUtils.isEmpty(PRE_SET_STREAM_ZH_HK)) {
                PRE_SET_STREAM_ZH_HK = MobileConstant.getStreamMap(MobileConstant.ALL_STREAMS, locale, sourceSite);
            }

            return PRE_SET_STREAM_ZH_HK;
        } else if ("en_us".equalsIgnoreCase(locale)) {
            if (CollectionUtils.isEmpty(PRE_SET_STREAM_EN_US)) {
                PRE_SET_STREAM_EN_US = MobileConstant.getStreamMap(MobileConstant.ALL_STREAMS, locale, sourceSite);
            }

            return PRE_SET_STREAM_EN_US;
        } else {
            if (CollectionUtils.isEmpty(PRE_SET_STREAM_ZH_CN)) {
                PRE_SET_STREAM_ZH_CN = MobileConstant.getStreamMap(MobileConstant.ALL_STREAMS, locale, sourceSite);
            }

            return PRE_SET_STREAM_ZH_CN;
        }
    }

    /**
     * 根据p_devType参数获取用户所属平台
     * @param commonParam
     * @return
     */
    public static String getPlatform(CommonParam commonParam) {
        String platform = MobileConstant.DEVICE_TERMINAL_PLATFORM.get(String.valueOf(commonParam.getP_devType()));
        if (platform == null) {
            platform = MobileConstant.TERMINAL_PLATFORM_ALL;
        }
        return platform;
    }

    /**
     * 调用媒资接口获取调度地址需要的tss参数
     * @param commonParam
     * @return
     */
    public static String getTss4VideoPlay(CommonParam commonParam) {
        String tss = "no";
        Integer deviceType = commonParam.getP_devType();
        if (deviceType != null) {
            switch (deviceType.intValue()) {
            case MobileConstant.TERMINAL_DEVICE_TYPE_MOBILE:
                tss = "ios";
                break;
            case MobileConstant.TERMINAL_DEVICE_TYPE_TV:
                tss = "tvts";
                break;
            default:
                break;
            }
        }

        return tss;
    }

    /**
     * 对视频码流进行排序
     * @param videoStreams
     *            视频码流
     * @param sortRule
     *            排序规则
     * @return
     */
    public static String sortVideoStreams(String videoStreams, String sortRule) {
        if (StringUtil.isNotBlank(videoStreams)) {
            // 1080p3m替换成1080p6m
            videoStreams = videoStreams.replace("1080p3m", "1080p6m");
            if (StringUtil.isBlank(sortRule)) {
                return videoStreams;
            }
            List<String> finalList = new LinkedList<String>();
            List<String> streamList = Arrays.asList(videoStreams.split(","));
            String[] sorts = sortRule.split("#");
            for (String sort : sorts) {
                if (streamList.contains(sort)) {
                    finalList.add(sort);
                }
            }
            if (!CollectionUtils.isEmpty(finalList)) {
                Collections.reverse(finalList);
                return StringUtil.getListToString(finalList, ",");
            }
        }

        return "";
    }

    /**
     * live点播只要极速、流畅、标清、高清、超清五种码流类型，所以需要过滤一下码流
     * @param videoStreams
     * @return
     */
    public static String getStreams4LivePlay(String videoStreams) {
        if (StringUtil.isNotBlank(videoStreams)) {
            StringBuilder sb = new StringBuilder();
            for (String s : videoStreams.split(",")) {
                if (MobileConstant.PLAY_MOBILE_USE_STREAM.contains(s)) {
                    if (sb.length() == 0) {
                        sb.append(s);
                    } else {
                        sb.append(",").append(s);
                    }
                }
            }
            return sb.toString();
        }

        return null;
    }
}
