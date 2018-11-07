package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.request.CanPlayRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import search2.extractor.ExtractData;
import search2.extractor.ExtractRequest;
import search2.extractor.FlvcdExtractService;
import search2.extractor.RequestSource;

@Component("v2.VideoTpDao")
public class VideoTpDao extends BaseTpDao {
    @Autowired(required = false)
    private SessionCache sessionCache;

    private final static Logger log = LoggerFactory.getLogger(VideoTpDao.class);
    private final static Logger IP_MASKING_LOG = LoggerFactory.getLogger("ipmaskingLog");
    private final static Map<String, Integer> MARLIN_STREAM_CODE_MAP = new HashMap<String, Integer>();
    private final static Integer PLATID = 5;
    private final static Integer LETVTV_SUB_PLATID = 501;
    private final static String VRS_SITE = ApplicationUtils.get(ApplicationConstants.GLOBAL_VIDEO_VRS_SITE, "CN");
    public final static Map<Integer, String> CODE_STREAM_MAP = new HashMap<Integer, String>();

    private final static int PLAT_ID = 5;
    private final static int PLAT_ID_CIBN_BSCHANNEL = 6;// 国广版本第三方合作的platid

    private final static int PLAY_ID = 0;// 0点播 1直播 2下载 目前不能区分播放还是下载。所以固定为0；

    private final static int RESULT_FORMAT = 1;// 1:json 2:xml

    private final static double VERSION = 2.0;

    public final static Map<String, Integer> STREAM_CODE_MAP = new HashMap<String, Integer>();

    /**
     * 终端默认价格包类型
     */
    public static final int TERMINAL_PRICE_PACKAGE_TYPE = 9;

    //@Resource
    private FlvcdExtractService.Iface videoServing;

    private final static String KEY = "sefUI(*q3JKsdf[[k%-90sdf";

    static {
        STREAM_CODE_MAP.put("flv_350", 1);
        STREAM_CODE_MAP.put("flv_vip", 8);
        STREAM_CODE_MAP.put("mp4", 9);
        STREAM_CODE_MAP.put("flv_live", 10);
        STREAM_CODE_MAP.put("union_low", 11);
        STREAM_CODE_MAP.put("union_high", 12);
        STREAM_CODE_MAP.put("mp4_800", 13);
        STREAM_CODE_MAP.put("flv_1000", 16);
        STREAM_CODE_MAP.put("flv_1300", 17);
        STREAM_CODE_MAP.put("flv_720p", 18);
        STREAM_CODE_MAP.put("mp4_1080p", 19);
        STREAM_CODE_MAP.put("flv_1080p6m", 20);
        STREAM_CODE_MAP.put("mp4_350", 21);
        STREAM_CODE_MAP.put("mp4_1300", 22);
        STREAM_CODE_MAP.put("mp4_800_db", 23);
        STREAM_CODE_MAP.put("mp4_1300_db", 24);
        STREAM_CODE_MAP.put("mp4_720p_db", 25);
        STREAM_CODE_MAP.put("mp4_1080p6m_db", 26);
        STREAM_CODE_MAP.put("flv_yuanhua", 27);
        STREAM_CODE_MAP.put("mp4_yuanhua", 28);
        STREAM_CODE_MAP.put("flv_3d720p", 29);// 之前写的错误数据，保留
        STREAM_CODE_MAP.put("flv_720p_3d", 29);
        STREAM_CODE_MAP.put("mp4_3d720p", 30);// 之前写的错误数据，保留
        STREAM_CODE_MAP.put("mp4_720p_3d", 30);
        STREAM_CODE_MAP.put("flv_3d1080p6m", 31);// 之前写的错误数据，保留
        STREAM_CODE_MAP.put("flv_1080p6m_3d", 31);
        STREAM_CODE_MAP.put("mp4_3d1080p6m", 32);// 之前写的错误数据，保留
        STREAM_CODE_MAP.put("mp4_1080p6m_3d", 32);
        STREAM_CODE_MAP.put("flv_3d1080p", 33);// 之前写的错误数据，保留
        STREAM_CODE_MAP.put("flv_1080p_3d", 33);
        STREAM_CODE_MAP.put("mp4_3d1080p", 34);// 之前写的错误数据，保留
        STREAM_CODE_MAP.put("mp4_1080p_3d", 34);
        STREAM_CODE_MAP.put("flv_1080p3m", 35);
        STREAM_CODE_MAP.put("mp4_4k", 54);
        STREAM_CODE_MAP.put("350_360", 161);
        STREAM_CODE_MAP.put("800_360", 162);
        STREAM_CODE_MAP.put("dolby_vision_4k", 252);
        STREAM_CODE_MAP.put("dolbyvision_4k_dv", 252);

        MARLIN_STREAM_CODE_MAP.put("flv_350", 72);
        MARLIN_STREAM_CODE_MAP.put("flv_1000", 74);
        MARLIN_STREAM_CODE_MAP.put("flv_1300", 76);
        MARLIN_STREAM_CODE_MAP.put("flv_720p", 78);
        MARLIN_STREAM_CODE_MAP.put("mp4_1080p", 80);
        MARLIN_STREAM_CODE_MAP.put("flv_1080p6m", 82);
        MARLIN_STREAM_CODE_MAP.put("mp4_350", 72);
        MARLIN_STREAM_CODE_MAP.put("mp4_1300", 76);
        MARLIN_STREAM_CODE_MAP.put("mp4_800_db", 92);
        MARLIN_STREAM_CODE_MAP.put("mp4_1300_db", 76);
        MARLIN_STREAM_CODE_MAP.put("mp4_720p_db", 96);
        MARLIN_STREAM_CODE_MAP.put("mp4_1080p6m_db", 100);
        MARLIN_STREAM_CODE_MAP.put("flv_720p_3d", 110);
        MARLIN_STREAM_CODE_MAP.put("mp4_720p_3d", 110);
        MARLIN_STREAM_CODE_MAP.put("flv_1080p6m_3d", 114);
        MARLIN_STREAM_CODE_MAP.put("mp4_1080p6m_3d", 114);
        MARLIN_STREAM_CODE_MAP.put("flv_1080p_3d", 98);
        MARLIN_STREAM_CODE_MAP.put("mp4_1080p_3d", 98);
        MARLIN_STREAM_CODE_MAP.put("flv_1080p3m", 80);
        MARLIN_STREAM_CODE_MAP.put("mp4_4k", 86);

        CODE_STREAM_MAP.put(1, "flv_350");
        CODE_STREAM_MAP.put(8, "flv_vip");
        CODE_STREAM_MAP.put(9, "mp4");
        CODE_STREAM_MAP.put(10, "flv_live");
        CODE_STREAM_MAP.put(11, "union_low");
        CODE_STREAM_MAP.put(12, "union_high");
        CODE_STREAM_MAP.put(13, "mp4_800");
        CODE_STREAM_MAP.put(16, "flv_1000");
        CODE_STREAM_MAP.put(17, "flv_1300");
        CODE_STREAM_MAP.put(18, "flv_720p");
        CODE_STREAM_MAP.put(19, "mp4_1080p");
        CODE_STREAM_MAP.put(20, "flv_1080p6m");
        CODE_STREAM_MAP.put(21, "mp4_350");
        CODE_STREAM_MAP.put(22, "mp4_1300");
        CODE_STREAM_MAP.put(23, "mp4_800_db");
        CODE_STREAM_MAP.put(24, "mp4_1300_db");
        CODE_STREAM_MAP.put(25, "mp4_720p_db");
        CODE_STREAM_MAP.put(26, "mp4_1080p6m_db");
        CODE_STREAM_MAP.put(27, "flv_yuanhua");
        CODE_STREAM_MAP.put(28, "mp4_yuanhua");
        CODE_STREAM_MAP.put(29, "flv_720p_3d");
        CODE_STREAM_MAP.put(30, "mp4_720p_3d");
        CODE_STREAM_MAP.put(31, "flv_1080p6m_3d");
        CODE_STREAM_MAP.put(32, "mp4_1080p6m_3d");
        CODE_STREAM_MAP.put(33, "flv_1080p_3d");
        CODE_STREAM_MAP.put(34, "mp4_1080p_3d");
        CODE_STREAM_MAP.put(35, "flv_1080p3m");
        CODE_STREAM_MAP.put(54, "mp4_4k");
        CODE_STREAM_MAP.put(161, "350_360");
        CODE_STREAM_MAP.put(162, "800_360");
        CODE_STREAM_MAP.put(252, "dolby_vision_4k");
    }

    /**
     * 播放鉴权
     * @param pid
     * @param userName
     * @param userId
     * @param pricePackageType
     * @param mac
     * @param deviceKey
     * @param isFromCntv
     * @param isFromCibn
     * @param wcode
     * @param storePath
     * @param userIp
     * @return
     */
    public UserPlayAuth getUserPlayAuth(Long pid, Long userId, Integer pricePackageType, Boolean isFromCntv,
                                        Boolean isFromCibn, String storePath, CommonParam commonParam) {
        String logPrefix = "getUserPlayAuth_" + commonParam.getMac() + "_" + userId + "_" + pid + "_"
                + commonParam.getDeviceKey();
        UserPlayAuth response = new UserPlayAuth();

        try {
            StringBuilder subUrl = new StringBuilder(VideoTpConstant.VIDEO_PLAY_URL);
            subUrl.append("?pid=").append(pid);
            if (commonParam.getUsername() != null) {
                subUrl.append("&uname=").append(commonParam.getUsername());
            }
            if (userId != null) {
                subUrl.append("&userid=").append(userId);
            }

            if (pricePackageType == null) {
                subUrl.append("&end=").append(TERMINAL_PRICE_PACKAGE_TYPE);
            } else {
                subUrl.append("&end=").append(pricePackageType);
            }

            subUrl.append("&from=tv").append("&subend=").append(getSubEnd(isFromCntv, isFromCibn));

            if (commonParam.getMac() != null) {
                subUrl.append("&mac=").append(commonParam.getMac());
            }
            if (storePath != null) {
                subUrl.append("&storepath=").append(storePath);
            }
            if (commonParam.getDeviceKey() != null) {
                subUrl.append("&devicekey=").append(commonParam.getDeviceKey());
            }
            subUrl.append("&ispay=1&termid=4");
            if (commonParam.getClientIp() != null) {
                subUrl.append("&userIp=").append(commonParam.getClientIp());
            }
            // http://yuanxian.letv.com/letv/getService.ldo?
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, UserPlayAuth.class);
            }

            if ((response != null) && (response.getStatus() != null)
                    && (response.getStatus() == VideoTpConstant.VIDEO_PLAY_SERVICE_UNOPEN.intValue())) {
                String errorCode = response.getErrorCode();
                if (errorCode == null) {
                    errorCode = ErrorCodeThirdpartyConstant.CONSUMPTION_ERROR_CODE_0000;
                }
                String errorMessage = ErrorCodeConstant.getThirdMessage(errorCode,
                        ErrorCodeConstant.LETV_THIRDPARTY_CONSUME_MAP);
                response.setErrorCode(errorCode);
                response.setErrorMsg(errorMessage);
            }

        } catch (Exception e) {
            response.setStatus(VideoTpConstant.VIDEO_PLAY_SERVICE_OPEN);
            log.error(logPrefix + " return error, will open play service: ", e);
        }
        return response;
    }

    /**
     * 播放鉴权
     * @param pid
     * @param userId
     * @param storePath
     * @return
     */
    public LiveUserPlayAuth getUserPlayAuth4Live(Long pid, String userId, String storePath, String imei,
                                                 String deviceKey, String clientIp) {
        LiveUserPlayAuth response = new LiveUserPlayAuth();
        StringBuilder url = new StringBuilder();
        // VideoCanPlayCheckRequest request = new VideoCanPlayCheckRequest();
        // request.setPid(pid);
        // request.setUserid(userId);
        // request.setStorepath(storePath);
        // request.setDevicekey(deviceKey);
        // request.setImei(imei);
        // request.setFrom("cell");
        // request.setEnd(VipTpConstant.TERMINAL_PRICE_PACKAGE_TYPE);
        // request.setUserIp(clientIp);// 美国版没有该参数
        try {
            response = this.restTemplate.getForObject(url.toString(), LiveUserPlayAuth.class);
            this.log.info("getUserPlayAuth4Live_" + userId + "_" + url.toString() + "[result]:" + response);
        } catch (Exception e) {
            this.log.error(
                    "getUserPlayAuth4Live_" + userId + "_" + url.toString() + "[Exception]:" + e.getStackTrace(), e);
        }
        return response;
    }

    /**
     * 防盗链 获取播放调度地址
     * @param request
     * @return
     */
    public MmsStore getMmsPlayInfo(String clientIp, Long vid, Long mid, String videoType, Boolean expectTs,
                                   Integer splatId, String mac, String bsChannel, Integer broadcastId) {
        MmsStore response = null;
        String result = null;

        String logPrefix = "getMmsPlayInfo_" + mac + "_" + vid + "_" + mid + "_" + clientIp;

        for (int i = 0; i < 3; i++) {
            // 尝试请求3次，成功则终止
            try {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("mmsid", mid);
                if (VideoTpConstant.CIBN_BSCHANNEL_OPERATORS.equalsIgnoreCase(bsChannel)) {
                    paramMap.put("platid", PLAT_ID_CIBN_BSCHANNEL);
                } else {
                    paramMap.put("platid", PLAT_ID);
                }
                paramMap.put("playid", PLAY_ID);
                paramMap.put("splatid", splatId);
                paramMap.put("key", getKey(mid));
                paramMap.put("format", RESULT_FORMAT);
                paramMap.put("version", VERSION);
                paramMap.put("vid", vid);
                paramMap.put("vtype", videoType);
                paramMap.put("region", VRS_SITE);
                if (expectTs) {
                    paramMap.put("videoformat", "tvts");
                    paramMap.put("tss", "tvts");
                } else {
                    paramMap.put("tss", "no");
                }
                paramMap.put("clientip", clientIp);
                if (broadcastId != null && CommonConstants.CIBN == broadcastId) {
                    paramMap.put("bc", broadcastId);
                } else {
                    paramMap.put("bc", "");
                }

                if (MmsDataUtil.existPayPlatform(this.sessionCache)) {
                    paramMap.put("payid", MmsDataUtil.getPayPlatform(this.sessionCache));
                }

                // http://i.api.letv.com/geturl?mmsid={mmsid}&platid={platid}&splatid={splatid}&version={version}&playid={playid}&vtype={vtype}&tss={tss}&clientip={clientip}
                result = this.restTemplate.getForObject(VideoTpConstant.TP_VIDEO_GET_URL, String.class, paramMap);

                if (StringUtil.isNotBlank(result)) {
                    log.info(logPrefix + ": invoke return length is  [" + result.length() + "]");
                    response = objectMapper.readValue(result, MmsStore.class);
                    // 打印ip屏蔽，请运维纠正
                    if (response != null && response.getData() == null) {
                        IP_MASKING_LOG.info(logPrefix + ":" + "ip=" + clientIp + ",country=" + response.getCountry());
                    }
                }
            } catch (Exception e) {
                log.error(logPrefix + " return error: ", e);
            }
            if (response != null) {
                break;
            }
        }

        return response;
    }

    private static String getSubEnd(Boolean isFromCntv, Boolean isFromCibn) {
        return isFromCntv ? "7" : isFromCibn ? "12" : "";
    }

    /**
     * 获得md5值(md5(mmsid + 私有密钥 + platid))
     * @param mmsId
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static String getKey(Long mmsId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String str = mmsId + KEY + PLAT_ID;
        String md5 = MessageDigestUtil.md5(str.getBytes());
        return md5;
    }

    /**
     * 访问非会员卡顿图接口
     * http://price.zhifu.letv.com/tv_nonmember_guide/get_list
     */
    public VideoGuideResponse getTvNonMemberGuideImg() {
        VideoGuideResponse response = null;
        String logPrefix = "getTvNonMemberGuideImg_";
        try {
            String result = this.restTemplate
                    .getForObject(VideoTpConstant.VIDEO_NONMENBER_GUIDE_LIST_GET, String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, VideoGuideResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 根据ip获取所属国家信息
     * @param ip
     *            客户端ip
     * @return
     */
    public LocationTpDto getCountry(String ip) {
        LocationTpDto response = null;
        String logPrefix = "getCountry";
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ip", ip);
            String result = this.restTemplate.getForObject(
                    ApplicationUtils.get(ApplicationConstants.VRS_MMS_INNER_GET_IPGEO), String.class, paramMap);
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, LocationTpDto.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 专辑付费信息接口
     * http://price.zhifu.letv.com/pinfo/get/price?pid={pid}
     */
    public GetAlbumChargeInfoTpResponse getAlbumChargeInfo(Long pid) {
        GetAlbumChargeInfoTpResponse response = null;
        String logPrefix = "getAlbumChargeInfo_" + pid;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VideoTpConstant.VIP_ALBUM_CHARGE_INFO_GET).append("?");
            if (pid != null) {
                subUrl.append("pid=").append(pid);
            }
            // http://price.zhifu.letv.com/pinfo/get/price?pid={pid}
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info(logPrefix + "|url:" + subUrl + "|invoke return:" + result);
            if (StringUtil.isNotBlank(result)) {
                if (!result.contains("false")) {
                    response = objectMapper.readValue(result, GetAlbumChargeInfoTpResponse.class);
                }
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    public GetVideoReactionTpResponse getVideoReactionInfo(String type, String albumId, String videoId, String logPrefix) {
        GetVideoReactionTpResponse response = null;
        StringBuilder url = new StringBuilder(VideoTpConstant.VIDEO_REACTION_URL).append("?");
        url.append("type=").append(type);
        url.append("&vid=").append(videoId);
        url.append("&pid=").append(albumId);
        try {
            // http://hd.my.letv.com/action/video?type={type}&vid={videoId}&pid={albumId}
            // String url = VIDEO_REACTION_URL;
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetVideoReactionTpResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 获取互动后台配置了互动数据的专辑或者视频信息
     * @return
     */
    public GetVideoReactionConfigResponse getVideoReactionConfig() {
        String logPrefix = "getVideoReactionConfig_";
        GetVideoReactionConfigResponse response = null;

        try {
            // http://hd.my.letv.com/votestatic/config_3.json
            String url = VideoTpConstant.VIDEO_REACTION_CONFIG_INFO_URL;
            String result = this.restTemplate.getForObject(url, String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetVideoReactionConfigResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 获取BOSS的付费频道信息
     * @param getVideoPlayRequest
     * @param logPrefix
     * @return
     */
    public GetPayChannelResponse getPayChannel(String logPrefix) {
        GetPayChannelResponse response = null;
        try {
            if (response == null) {
                // http://price.zhifu.letv.com/movie_channel/get_pay_list
                String result = this.restTemplate.getForObject(VideoTpConstant.VIDEO_PAY_CHANNEL_GET, String.class);
                log.info(logPrefix + ": invoke return [" + result + "]");

                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, GetPayChannelResponse.class);
                }
            }

        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 会员账号针对付费频道加速
     * @param getChannelValidateRequest
     * @param logPrefix
     * @return
     */
    public GetPlayUserTokenResponse getPayChannelTokenInfo(Long userId, String storePath, String logPrefix) {
        GetPlayUserTokenResponse response = null;
        try {
            StringBuilder url = new StringBuilder();
            url.append(VideoTpConstant.VIDEO_PAY_CHANNEL_USER_AUTH_GET).append("?").append("from=tokenAndUinfo") // 来自哪一端，默认值为：tokenAndUinfo
                    .append("&termid=4") // 会员加速的终端 ,tv端为4
                    .append("&vipType=2"); // 会员类型，默认传2
            if (userId != null) {
                url.append("&userid=").append(userId);
            }
            if (storePath != null) {
                url.append("&storepath=").append(storePath);
            }
            url.append("&sign=").append(this.getSign(userId, storePath));
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetPlayUserTokenResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    private String getSign(Long userId, String storePath) {
        StringBuilder sb = new StringBuilder();
        // 签名规则:MD5(key+storePath+userId)
        String signKey = "f8bb39f11dbda8c03efa1cd0250c9556";
        sb.append(signKey).append(StringUtils.trimToEmpty(storePath)).append(userId);
        try {
            return MessageDigestUtil.md5(sb.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            log.error("getSign_ error", e.getMessage(), e);
        }
        return "";
    }

    @Deprecated
    public MmsResponse<MmsAlbumInfo> getMmsAlbumById(Long id, Integer type, String... regions) {
        MmsResponse<MmsAlbumInfo> response = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            // params.put("type", type);
            params.put("amode", type);
            params.put("format", "json");
            params.put("token", this.getToken(id));
            params.put("platform", CommonConstants.TV_PLATFROM_CODE);
            String url = ApplicationUtils.get(ApplicationConstants.VRS_MMS_ALBUMINFO_GET);
            if (regions != null && regions.length > 0) {
                for (String region : regions) {
                    String urlTmp = url + "&region=" + region;
                    String result = this.restTemplate.getForObject(urlTmp, String.class, params);
                    log.info("[url]:" + urlTmp + "[params]:" + params + "[result]:" + result);
                    if (StringUtil.isNotBlank(result)) {
                        result = result.replaceAll("　", " ");
                        response = objectMapper.readValue(result, new TypeReference<MmsResponse<MmsAlbumInfo>>() {
                        });
                    }
                    if (response != null && response.getData() != null) {
                        break;
                    }
                }
            } else {
                String result = this.restTemplate.getForObject(url, String.class, params);
                log.info("[url]:" + url + "[params]:" + params + "[result]:" + result);
                if (StringUtil.isNotBlank(result)) {
                    result = result.replaceAll("　", " ");
                    response = objectMapper.readValue(result, new TypeReference<MmsResponse<MmsAlbumInfo>>() {
                    });
                }
            }
        } catch (Exception e) {
            log.error("MONITOR EXCEPTION getMmsAlbumById is error,id:" + id + "_" + e.getMessage(), e);
        }
        return response;
    }

    /**
     * 获取媒资专辑信息
     * @param id
     *            专辑ID
     * @param region
     *            地域
     * @param lang
     *            语言
     * @return 专辑信息
     */
    public MmsResponse<MmsAlbumInfo> getMmsAlbumById(Long id, String region, String lang) {
        return getMmsAlbumById(id, region, lang, false);
    }

    public MmsResponse<MmsAlbumInfo> getMmsAlbumById(Long id, String region, String lang, boolean hasHit) {
        return this.getMmsAlbumById(id, region, lang, hasHit, false);
    }

    /**
     * 获取媒资专辑信息
     * @param id
     *            专辑ID
     * @param region
     *            地域
     * @param lang
     *            语言
     * @param hasHit
     *            是否获取屏蔽专辑信息
     * @return 专辑信息
     */
    public MmsResponse<MmsAlbumInfo> getMmsAlbumById(Long id, String region, String lang, boolean hasHit,
            boolean useHttpClientV2) {
        MmsResponse<MmsAlbumInfo> response = null;
        try {
            StringBuilder url = new StringBuilder(VideoTpConstant.TP_ALBUM_INFO_GET);
            url.append("id=").append(id);
            // 控制专辑信息输出 0：全量 (只支持单个id格式) 1：精简(支持多个id格式)
            url.append("&amode=0");
            url.append("&token=").append(this.getToken(id));
            url.append("&platform=").append(CommonConstants.TV_PLATFROM);
            url.append("&lang=").append(StringUtils.trimToEmpty(lang));
            url.append("&region=").append(VRS_SITE);
            if (hasHit) {
                // 当hit=1时, 返回被屏蔽专辑信息
                url.append("&hit=1");
            }
            // 可播地区白名单,
            // 如：whiteList=US,允许美国播放.该参数可以与region结合使用，当传入whiteList=US&region=US表示获取美国站，允许美国播放的数据.
            url.append("&whiteList=").append(StringUtils.trimToEmpty(region));

            // http://i.api.letv.com/mms/inner/albumInfo/get?id={id}&amode={amode}&format={format}&token={token}&platform={platform}&lang={lang}&region={region}&whiteList={whiteList}
            String result = null;
            if (useHttpClientV2) {
                String[] urls = { url.toString() };
                Map<String, String> resultMap = this.restTemplate.multiHttpRequests(urls, "GET", null, null);
                result = resultMap.get(url.toString());
            } else {
                result = this.restTemplate.getForObject(url.toString(), String.class);
            }
            if (StringUtil.isNotBlank(result)) {
                log.info("getMmsAlbumById_" + id + "_" + region + "_" + lang + " result length: " + result.length());
                result = result.replaceAll("　", " ");
                response = objectMapper.readValue(result, new TypeReference<MmsResponse<MmsAlbumInfo>>() {
                });
            }
        } catch (Exception e) {
            log.error("MONITOR EXCEPTION getMmsAlbumById is error,id:" + id + "_" + e.getMessage(), e);
        }
        return response;

    }

    @Deprecated
    public MmsResponse<MmsVideoInfo> getMmsVideoById(Long id, Integer type, Integer vmode, String... regions) {
        MmsResponse<MmsVideoInfo> response = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            params.put("type", type);
            params.put("token", this.getToken(id));
            params.put("platform", CommonConstants.TV_PLATFROM);
            params.put("vmode", vmode);
            String url = ApplicationUtils.get(ApplicationConstants.VRS_MMS_VIDEOINFO_INNER_GET);
            if (regions != null && regions.length > 0) {
                for (String region : regions) {
                    String urlTmp = url + "&region=" + region;
                    String result = this.restTemplate.getForObject(urlTmp, String.class, params);
                    log.info("[url]:" + urlTmp + "[params]:" + params + "[result]:" + result);
                    if (StringUtil.isNotBlank(result)) {
                        result = result.replaceAll("　", " ");
                        response = objectMapper.readValue(result, new TypeReference<MmsResponse<MmsVideoInfo>>() {
                        });
                    }
                    if (response != null && response.getData() != null) {
                        break;
                    }
                }
            } else {
                String result = this.restTemplate.getForObject(url, String.class, params);
                log.info("[url]:" + url + "[params]:" + params + "[result]:" + result);
                if (StringUtil.isNotBlank(result)) {
                    result = result.replaceAll("　", " ");
                    response = objectMapper.readValue(result, new TypeReference<MmsResponse<MmsVideoInfo>>() {
                    });
                }
            }
        } catch (Exception e) {
            log.error("MONITOR EXCEPTION getMmsVideoById is error,id:" + id, e);
        }
        return response;
    }

    /**
     * 获取媒资视频信息
     * @param id
     *            视频ID
     * @param region
     *            地域
     * @param lang
     *            语言
     * @return 视频信息
     */
    public MmsResponse<MmsVideoInfo> getMmsVideoById(Long id, String region, String lang) {
        return getMmsVideoById(id, region, lang, false);
    }

    /**
     * 获取媒资视频信息
     * @param id
     *            视频ID
     * @param region
     *            地域
     * @param lang
     *            语言
     * @param hasHit
     *            是否获取屏蔽视频信息
     * @return 视频信息
     */
    public MmsResponse<MmsVideoInfo> getMmsVideoById(Long id, String region, String lang, boolean hasHit) {
        MmsResponse<MmsVideoInfo> response = null;
        try {
            StringBuilder url = new StringBuilder(VideoTpConstant.TP_VIDEO_INFO_GET);
            url.append("id=").append(id);
            // 1视频基本信息 2视频基本信息+码率
            url.append("&type=2");
            // 控制视频信息输出 0：全量 1：精简 2:乐拍专用模式 3-播放记录专用(精简信息)
            url.append("&vmode=0");
            url.append("&token=").append(this.getToken(id));
            url.append("&platform=").append(CommonConstants.TV_PLATFROM);
            url.append("&lang=").append(StringUtils.trimToEmpty(lang));
            url.append("&region=").append(VRS_SITE); // 站点信息
            if (hasHit) {
                // 当hit=1时, 返回被屏蔽视频信息
                url.append("&hit=1");
            }
            // 可播地区白名单,
            // 如：whiteList=US,允许美国播放.该参数可以与region结合使用，当传入whiteList=US&region=US表示获取美国站，允许美国播放的数据.
            url.append("&whiteList=").append(StringUtils.trimToEmpty(region));

            // http://i.api.letv.com/mms/inner/video/get?id={id}&type={type}&token={token}&platform={platform}&vmode={vmode}&lang={lang}&region={region}&whiteList={whiteList}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                log.info("getMmsVideoById_" + id + "_" + region + "_" + lang + " result length:" + result.length());
                result = result.replaceAll("　", " ");
                response = objectMapper.readValue(result, new TypeReference<MmsResponse<MmsVideoInfo>>() {
                });
            }
        } catch (Exception e) {
            log.error("MONITOR EXCEPTION getMmsVideoById is error,id:" + id, e);
        }
        return response;
    }

    /**
     * 获取视频播放水印
     * @param cid
     *            分类id
     * @param pid
     *            专辑id
     * @return
     */
    public MmsResponse<WaterMark> getWaterMark(Integer cid, Long pid) {
        MmsResponse<WaterMark> response = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cid", cid);
            params.put("pid", (pid != null && pid > 0) ? pid + "" : "");
            params.put("format", "json");
            params.put("token", this.getToken(new Long(cid)));
            params.put("platform", CommonConstants.TV_PLATFROM);
            String result = this.restTemplate.getForObject(
                    ApplicationUtils.get(ApplicationConstants.VRS_MMS_LIVEWATERMARK_INNER_GET), String.class, params);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new TypeReference<MmsResponse<WaterMark>>() {
                });
            }
        } catch (Exception e) {
            log.error("获取视频水印异常", e);
        }
        return response;
    }

    public MmsDictionnaryInfo getMmsDictionnaryInfoById(Integer id) throws Exception {
        MmsDictionnaryInfo response = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            String result = this.restTemplate.getForObject(
                    ApplicationUtils.get(ApplicationConstants.VRS_MMS_DIC_OUT_GET), String.class, params);
            log.info("[url]:" + ApplicationUtils.get(ApplicationConstants.VRS_MMS_DIC_OUT_GET) + "[params]:" + params
                    + "[result]:" + result);
            if (StringUtil.isNotBlank(result)) {
                result = result.replaceAll("　", " ");
                response = objectMapper.readValue(result, MmsDictionnaryInfo.class);
            }
        } catch (Exception e) {
            if (e instanceof JsonMappingException) {
                log.error("getMmsVideoById is error,id:" + id, e);
            } else {
                log.error("MONITOR EXCEPTION getMmsVideoById is error,id:" + id, e);
            }
            throw new Exception(e.getMessage());
        }
        return response;
    }

    private String getToken(Long id) {
        String key = "1234";
        // String token = .sign(id+"tv", key, "UTF-8");
        String text = id + CommonConstants.TV_PLATFROM + key;
        String token = "";
        try {
            token = MessageDigestUtil.md5(text.getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            log.error("MONITOR EXCEPTION getToken is error,text:" + text, e);
        }
        return token;
    }

    /**
     * v2.5 从新媒资库获得数据
     * @param blockId
     * @return
     */
    public CmsDataResponse getCmsDataByBlockId_v25(String url) {
        CmsDataResponse response = null;
        try {
            String result = this.restTemplate.getForObject(url, String.class);
            if (StringUtil.isNotBlank(result)) {
                // result = new String(s.getBytes("ISO-8859-1"));
                response = objectMapper.readValue(result, CmsDataResponse.class);
                return response;
            }
        } catch (Exception e) {
            log.error("getCmsDataByBlockId_v25_ error", e.getMessage(), e);
            return null;
        }

        return null;
    }

    /**
     * 从媒资库获得演员信息，包含图片
     * @param id
     *            明星leid
     * @param langcode
     *            语言
     * @return
     */
    public MmsResponse<List<CmsActorResponse>> getCmsStar(String id, String langcode) {
        MmsResponse<List<CmsActorResponse>> response = null;
        if (StringUtils.isNotBlank(id)) {
            String logPrefix = "getCmsStar_" + id;
            StringBuilder url = new StringBuilder(VideoTpConstant.VIDEO_VRS_MMS_STAR_GET);
            try {
                url.append("?id=").append(id).append("&platform=").append(420007);
                if (StringUtils.isNotBlank(langcode)) {
                    url.append("&lang=").append(langcode);
                }
                String result = this.restTemplate.getForObject(url.toString(), String.class);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new TypeReference<MmsResponse<List<CmsActorResponse>>>() {
                    });
                }
                return response;
            } catch (Exception e) {
                log.error(logPrefix + " get cms star failed. url=[" + url + "], message:" + e.getMessage(), e);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 防盗链接口封装
     * 文档：http://wiki.letv.cn/pages/viewpage.action?pageId=44515493#id-防盗链接口文档-
     * 说明
     * @param mmsids
     *            媒资ID
     * @param videoType
     *            码流类型
     * @param expectTs
     *            下载类型：true 为 tv 版；false 为 mp4
     * @return MmsStore
     */
    public MmsStore getPlayUrlByVid_V3(String mmsids, String videoType, Boolean expectTs) {
        MmsStore response = null;
        try {
            String subUrl = VideoTpConstant.VIDEO_VRS_MMSVIDEO_URL + "?";
            subUrl += "mmsid=" + mmsids;
            subUrl += "&platid=" + PLATID;
            subUrl += "&playid=0";// 0点播 1直播 2下载 目前不能区分播放还是下载。所以固定为0；
            subUrl += "&splatid=" + LETVTV_SUB_PLATID;
            subUrl += "&key=" + this.getKey(mmsids);
            subUrl += "&format=1";// 1:json 2:xml
            subUrl += "&version=2.0";
            subUrl += "&playid=2";// 0点播 1直播 2下载

            if (videoType != null && videoType.length() > 0) {
                String[] varr = videoType.split(",");
                String vtype = "";
                for (String v : varr) {
                    if (v != null && v.length() > 0) {
                        vtype += STREAM_CODE_MAP.get(v) + ",";
                    }
                }
                if (vtype.length() > 0) {
                    vtype = vtype.substring(0, vtype.length() - 1);
                }
                subUrl += "&vtype=" + vtype;
            }

            Boolean isTs = expectTs;
            if (isTs == null) {
                isTs = false;
            }
            if (isTs) {
                subUrl += "&videoformat=tvts";
                subUrl += "&tss=tvts";
            } else {
                subUrl += "&tss=no";
            }
            String result = this.restTemplate.getForObject(subUrl, String.class);
            log.info("getPlayUrlByVid_V3_" + mmsids + "|url:" + subUrl + "|result:" + result + ".");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, MmsStore.class);
            }
        } catch (Exception e) {
            log.error("获取调度地址异常" + e.getMessage(), e);
        }
        return response;
    }

    // TODO 验证playUrl_V2和getMmsPlayInfo的区别 by liudaojie
    /**
     * 防盗链 获取播放调度地址
     * @param clientIp
     *            客户端ip
     * @param vid
     *            视频id
     * @param mid
     *            媒资id
     * @param videoType
     *            码流类型（1、9、13、16...）
     * @param expectTs
     * @param actionType
     * @param splatId
     *            所属子平台的ID
     * @param isMarlin
     *            是否是DRM
     * @param broadcastId
     * @return
     */
    public MmsStore getPlayUrl_V2(String clientIp, Long vid, Long mid, String videoType, Boolean expectTs,
            String actionType, Integer splatId, boolean isMarlin, Integer broadcastId) {
        MmsStore response = null;
        for (int i = 0; i < 3; i++) {
            try {
                String result = null;
                StringBuilder subUrl = new StringBuilder(VideoTpConstant.VIDEO_VRS_MMSVIDEO_URL);

                subUrl.append("?mmsid=").append(mid).append("&platid=").append(PLATID) // 所属平台的ID，
                        .append("&playid=0");// 0点播 1直播 2下载 目前不能区分播放还是下载。所以固定为0

                if (splatId != null) {
                    subUrl.append("&splatid=").append(splatId);
                }

                if (MmsDataUtil.existPayPlatform(this.sessionCache)) {
                    subUrl.append("&payid=").append(MmsDataUtil.getPayPlatform(this.sessionCache));
                }

                subUrl.append("&key=").append(this.getKey(mid + "")).append("&format=1").append("&version=2.0");

                if (broadcastId != null && broadcastId > 0) {
                    subUrl.append("&bc=").append(broadcastId);
                }
                if (vid != null) {
                    subUrl.append("&vid=").append(vid);
                }

                String playid = "0";
                if ("download".equalsIgnoreCase(actionType)) {
                    playid = "2";
                }
                subUrl.append("&playid=").append(playid);// 0点播 1直播 2下载

                if (videoType != null && videoType.length() > 0) {
                    if (isMarlin) {
                        subUrl.append("&vtype=").append(MARLIN_STREAM_CODE_MAP.get(videoType));
                    } else {
                        if (videoType.equalsIgnoreCase(LetvStreamCommonConstants.VIDEO_TYPE_MP4_PREFIX
                                + LetvStreamCommonConstants.CODE_NAME_4K)) {
                            subUrl.append("&vtype=65,45,55,54");// 取全部的4K
                        } else {
                            subUrl.append("&vtype=").append(STREAM_CODE_MAP.get(videoType));
                        }
                    }
                }

                Boolean isTs = expectTs;
                if (isTs == null) {
                    isTs = false;
                }
                if (isTs) {
                    subUrl.append("&videoformat=tvts").append("&tss=tvts");
                } else {
                    subUrl.append("&tss=no");
                }

                if (StringUtils.isNotEmpty(clientIp)) {
                    subUrl.append("&clientip=").append(clientIp);
                }
                // http://i.api.letv.com/geturl?mmsid={mmsid}&platid=5&splatid={splatid}&key={key}&bc={broadcastId}&vid={vid}&vtype={vtype}&videoformat={videoformat}&tss={tss}&clientip={clientip}
                result = this.restTemplate.getForObject(subUrl.toString(), String.class);

                log.info("getPlayUrl_V2_" + mid + "|url:" + subUrl + "|result:" + result);

                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, MmsStore.class);
                    break;
                }
            } catch (Exception e) {
                log.error("获取调度地址异常, message:" + e.getMessage() + " try counts:[" + i + "]", e);
            }
        }

        return response;
    }

    public MmsStoreInfo getFileRealInfo(String mid) {
        MmsStoreInfo response = null;
        String url = ApplicationUtils.get(ApplicationConstants.VRS_MMS_VIDEO_URLS_GET_INNER);
        Map<String, String> params = new HashMap<>();
        params.put("mid", mid);
        for (int i = 0; i < 3; i++) {
            try {
                String result = this.restTemplate.getForObject(url, String.class, params);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, MmsStoreInfo.class);
                    break;
                }
            } catch (Exception e) {
                log.error("获取数据结果异常" + e.getMessage() + " 次数:" + i, e);
            }
        }

        return response;
    }

    /**
     * 调用消费中心判断是否可以播放
     * http://yuanxian.letv.com/letv/getService.ldo?pid={pid}&uname
     * ={uname}&end={end} &from=tv
     * @param request
     * @return
     */
    @Deprecated
    public CanPlayResult canPlay(CanPlayRequest request) {
        CanPlayResult response = new CanPlayResult();
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("pid", request.getProductId() + "");
            params.put("uname", request.getUsername());
            params.put("end", request.getTerminal() + "");
            params.put("subend",
                    request.getIsFromCntv() != null && request.getIsFromCntv() ? 7 + ""
                            : request.getIsFromCibn() != null && request.getIsFromCibn() ? 12 + "" : "");
            params.put("mac", request.getCntvMac());
            params.put("deviceKey", request.getDeviceKey());
            if (request.getStorePath() != null) {
                params.put("storepath", request.getStorePath());
            }
            String result = this.restTemplate.getForObject(
                    ApplicationUtils.get(ApplicationConstants.BOSS_YUANXIAN_VIDEO_CANPLAY_CHECK), String.class, params);
            if (StringUtil.isNotBlank(result)) {
                ObjectMapper m = new ObjectMapper();
                response = m.readValue(result, CanPlayResult.class);
            }
            if (response.getStatus() == VideoTpConstant.VIDEO_PLAY_SERVICE_UNOPEN) {
                String errorCode = response.getErrorCode();
                if (errorCode == null) {
                    errorCode = ErrorCodeThirdpartyConstant.CONSUMPTION_ERROR_CODE_0000;
                }
                String errorMessage = ErrorCodeConstant.getThirdMessage(errorCode,
                        ErrorCodeConstant.LETV_THIRDPARTY_CONSUME_MAP);
                response.setErrorCode(errorCode);
                response.setErrorMsg(errorMessage);
            }
            log.info("[url]:" + ApplicationUtils.get(ApplicationConstants.BOSS_YUANXIAN_VIDEO_CANPLAY_CHECK)
                    + "[params]:" + params + "[result]:" + result);
        } catch (Exception e) {
            response.setStatus(VideoTpConstant.VIDEO_PLAY_SERVICE_OPEN);
            log.error("username:" + request.getUsername() + "播放异常 albumId:" + request.getProductId() + e.getMessage(),
                    e);
            log.error(e.getMessage(), e);
        }
        return response;
    }

    /**
     * 获取真实播放地址
     * @param cdnDispatchUrl
     * @param uriVariables
     * @return
     */
    public CdnDispatchResponse cndDispatchResponse(String cdnDispatchUrl, Object... uriVariables) {
        CdnDispatchResponse response = new CdnDispatchResponse();
        try {
            HttpHeaders requestHeaders = this.createHttpHeader(MediaType.APPLICATION_XML.toString(), "UTF-8");
            HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);
            ResponseEntity<CdnDispatchResponse> entity = this.restTemplate.exchange(cdnDispatchUrl, HttpMethod.GET,
                    requestEntity, CdnDispatchResponse.class, uriVariables);
            if (entity != null) {
                response = entity.getBody();
            }
        } catch (Exception e) {
            log.error("获取播放信息异常 cdnDispatchUrl:" + cdnDispatchUrl + ". ErrorMsg:" + e.getMessage(), e);
        }
        return response;
    }

    /**
     * orderType 分页排序方式排序 -1:按照集数porder升序，1:按照集数porder降序，默认1
     * page 第几页 用于分页用,默认值:1
     * pageSize 每页数量 用于分页用，最大不超过60
     * albumId 专辑ID
     * @param albumId
     * @return
     */
    public AlbumSerialsVideoInfo<?> getAlbumSerialVideoList(Long albumId, Integer orderType, Integer page,
            Integer pageSize) {
        AlbumSerialsVideoInfo<?> albumSerialsVideoInfo = null;
        StringBuilder url = new StringBuilder(ApplicationUtils.get(ApplicationConstants.VRS_MMS_ALBUM_SERIALIS_LIST));
        url.append("?p=").append(420007);// p 平台 420007代表tv
        if (orderType != null && (orderType == 1 || orderType == -1)) {
            url.append("&o=").append(orderType);
        } else {
            url.append("&o=").append(1); // 默认降序
        }
        if (page != null && page > 0) {
            url.append("&b=").append(page);
        }
        if (pageSize != null && pageSize > 0) {
            url.append("&s=").append(pageSize);
        }
        if (albumId != null) {
            url.append("&id=").append(albumId);
        }
        try {
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                albumSerialsVideoInfo = objectMapper.readValue(result,
                        new TypeReference<AlbumSerialsVideoInfo<AlbumSerialsVideoInfo.SerialsVideoInfo>>() {
                        });
            }
        } catch (Exception e) {
            log.error("class:VideoTpDao  method:getAlbumSerialVideoList " + " return error: ", e);
        }
        return albumSerialsVideoInfo;
    }

    /**
     * 根据专辑Id从vrs获取专辑信息
     * albumId 专辑Id
     * @param albumId
     * @return
     */
    public AlbumSerialsVideoInfo<?> getAlbumInfoById(Integer albumId) {
        AlbumSerialsVideoInfo<?> albumSerialsVideoInfo = null;
        StringBuilder url = new StringBuilder(ApplicationUtils.get(ApplicationConstants.VRS_MMS_ALBUM_INFO_GET));
        url.append("?platform=tv");
        if (albumId != null) {
            url.append("&id=").append(albumId);
        }
        try {
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                albumSerialsVideoInfo = objectMapper.readValue(result,
                        new TypeReference<AlbumSerialsVideoInfo<List<AlbumSerialsVideoInfo.VideoInfo>>>() {
                        });
            }
        } catch (Exception e) {
            log.error("class:VideoTpDao  method:getAlbumInfoById " + " return error: ", e);
        }
        return albumSerialsVideoInfo;
    }

    /**
     * 调用领先版点播接口；
     * @param videoId
     * @param userId
     * @param imei
     * @param stream
     * @param sig
     * @param commonParam
     * @return
     */
    public LetvLeadingCommonResponse<LetvLeadingPlayInfo> getLetvLeadingPlayInfo(Long videoId, String imei,
            String stream, String routerId, CommonParam commonParam) {
        String logPrefix = "getLetvLeadingPlayInfo_" + videoId + "_" + commonParam.getUserId() + "_"
                + commonParam.getMac() + "_" + imei + "_" + stream;
        LetvLeadingCommonResponse<LetvLeadingPlayInfo> response = null;
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("videoid", videoId);
        paramsMap.put("uid", StringUtils.trimToEmpty(commonParam.getUserId()));
        paramsMap.put("mac", StringUtils.trimToEmpty(commonParam.getMac()));
        paramsMap.put("devKey", StringUtils.trimToEmpty(commonParam.getDeviceKey()));
        paramsMap.put("stream", StringUtils.trimToEmpty(stream));
        paramsMap.put("devId", StringUtils.trimToEmpty(imei));
        paramsMap.put("sig", StringUtils.trimToEmpty(VideoTpConstant.getLetvLeadingPlaySig(videoId, null)));
        paramsMap.put("terminalSeries", StringUtils.trimToEmpty(commonParam.getTerminalSeries()));
        paramsMap.put("wcode", StringUtils.trimToEmpty(commonParam.getWcode()));
        paramsMap.put("langcode", StringUtils.trimToEmpty(commonParam.getLangcode()));
        // 美国le。com,传给领先版的terminalApplication = tvle
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            paramsMap.put("tvapp", "tvle");
        } else {
            paramsMap.put("tvapp", "tvserver");
        }

        HttpHeaders requestHeaders = HttpCommonUtil.createHttpHeader(MediaType.APPLICATION_JSON_VALUE, "UTF-8");
        requestHeaders.set(VideoTpConstant.LETV_LEADING_REQUEST_HEADER_REOUTER_ID_KEY, routerId);
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
        try {
            ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                    VideoTpConstant.LETV_LEADING_GET_PLAY_INFO_URL, HttpMethod.GET, requestEntity, String.class,
                    paramsMap);
            String result = responseEntity.getBody();
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result,
                        new LetvTypeReference<LetvLeadingCommonResponse<LetvLeadingPlayInfo>>() {
                        });
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: " + " return error: ", e);
        }

        return response;
    }

    /**
     * 获取领先版播放下拉列表
     * @param albumId
     * @param pageNum
     * @return
     */
    public LetvLeadingCommonResponse<LetvLeadingAlbumSeries> getLetvLeadingAlbumSeriesInfo(Long albumId,
            Integer pageNum, CommonParam commonParam) {
        String logPrefix = "getLetvLeadingAlbumSeriesInfo_" + albumId + "_" + pageNum;
        LetvLeadingCommonResponse<LetvLeadingAlbumSeries> response = null;
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("page", pageNum);
        paramsMap.put("albumid", albumId);

        // String url = VideoTpConstant.LETV_LEADING_ALBUN_SERIES_URL;
        String url = null;
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            url = VideoTpConstant.LETV_LEADING_ALBUN_SERIES_DYN_URL;
            paramsMap.put("tvapp", "tvle");
        } else {
            url = VideoTpConstant.LETV_LEADING_ALBUN_SERIES_URL;
        }

        try {
            String result = this.restTemplate.getForObject(url, String.class, paramsMap);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result,
                        new LetvTypeReference<LetvLeadingCommonResponse<LetvLeadingAlbumSeries>>() {
                        });
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: " + " return error: ", e);
        }

        return response;
    }

    /**
     * help method create HTTP headers.
     * @return
     */
    private HttpHeaders createHttpHeader(String acceptMediaType, String acceptCharset) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Accept", acceptMediaType);
        requestHeaders.set("Accept-Charset", acceptCharset);
        return requestHeaders;
    }

    // md5(md5(mmsid + 私有密钥 + platid))
    private String getKey(String mmsId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String str = mmsId + KEY + PLATID;
        String md5 = MessageDigestUtil.md5(str.getBytes());
        return md5;
    }

    private ExtractRequest getExtractRequest(String contentId, String token, String tokenSecret, Integer deviceType,
            String clientIp) throws Exception {
        ExtractRequest request = new ExtractRequest();

        Map<String, Object> extraDataMap = new HashMap<String, Object>();
        extraDataMap.put("content_id", contentId);
        extraDataMap.put("token", token);
        extraDataMap.put("token_secret", tokenSecret);
        extraDataMap.put("device_type", deviceType);
        extraDataMap.put("client_ip", clientIp);
        request.setExtra_data(objectMapper.writeValueAsString(extraDataMap));

        request.setRequest_source(RequestSource.INDIA_TV);
        request.setEid(0);
        return request;
    }

    /**
     * 获取印度作品库播放流信息
     * @param contentId
     *            内容id
     * @param token
     * @param tokenSecret
     * @param deviceType
     *            见{@link UserConstants.ErosConstant.DeviceType}
     * @param commonParam
     * @return
     */
    public GetStreamTpResponse getStream(String contentId, String token, String tokenSecret, Integer deviceType,
            CommonParam commonParam) {
        String logPrefix = "getStream_" + contentId + "_" + token + "_" + tokenSecret + "_" + commonParam.getClientIp();
        GetStreamTpResponse response = null;
        for (int i = 0; i < 2; i++) {// 防止第一次取流出现意外
            try {
                ExtractRequest extractRequest = this.getExtractRequest(contentId, token, tokenSecret, deviceType,
                        commonParam.getClientIp());
                List<ExtractData> list = this.videoServing.GetStream(extractRequest, false);// 此处注意要用false，true为同步解析，只用于人工调试，不可大批量调用
                if (list != null && list.size() > 0) {
                    response = new GetStreamTpResponse(list);
                    for (ExtractData data : list) {
                        log.info(logPrefix + " invoke return:[" + data.toString() + "]");
                    }
                    break;
                }
            } catch (Exception e) {
                response = null;
                log.error(logPrefix + " request video stream address fail. " + e.getMessage(), e);
            }
        }
        return response;
    }

    public MmsResponse<MmsVideoList> getVideoListByAlbumId(Long albumId, String region, String lang, String queryType,
            Integer page) {
        return getVideoListByAlbumId(albumId, null, page, VideoTpConstant.ALBUM_SERIES_PAGE_SIZE, -1, region, lang,
                queryType);
    }

    /**
     * 获取专辑下视频列表数据
     * @param albumId
     *            专辑id
     * @param videoId
     *            该专辑下的视频id，返回vid所在页视频列表，并输出vid所在当前页码，必须与参数s共同使用。此时参数 b 无效
     * @param page
     *            第几页，用于分页使用，从1开始
     * @param pageSize
     *            每页数量 用于分页用，最大不超过60
     * @param porder
     *            分页排序方式排序，-1:按照集数porder升序，1:按照集数porder降序，默认-1
     * @param region
     *            地区
     * @param lang
     *            语言
     * @param queryType
     *            查询类型，0:非正片视频，1:正片视频，2:所有视频
     * @return
     */
    public MmsResponse<MmsVideoList> getVideoListByAlbumId(Long albumId, Long videoId, Integer page, Integer pageSize,
            Integer porder, String region, String lang, String queryType) {
        if (page == null || page < 0) {
            page = 1;
        }
        if (pageSize == null || pageSize < 0 || pageSize > VideoTpConstant.ALBUM_SERIES_PAGE_SIZE) {
            pageSize = VideoTpConstant.ALBUM_SERIES_PAGE_SIZE;
        }
        if (porder == null) {
            porder = -1;
        }
        MmsResponse<MmsVideoList> response = null;
        StringBuilder url = new StringBuilder();
        if (VideoTpConstant.QUERY_TYPE_POSITIVE.equals(queryType)) {// 获取专辑下正片视频
            url.append(VideoTpConstant.VIDEO_LIST_POSITIVE);
        } else if (VideoTpConstant.QUERY_TYPE_NON_POSITIVE.equals(queryType)) {// 获取专辑下非正片视频
            url.append(VideoTpConstant.VIDEO_LIST_NON_POSITIVE);
        } else {// 获取专辑下所有视频
            url.append(VideoTpConstant.VIDEO_LIST_ALL);
        }
        url.append("id=").append(albumId);// 专辑id
        url.append("&b=").append(page);// 第几页，用于分页使用
        url.append("&s=").append(pageSize);// 每页数量 用于分页用，最大不超过60
        url.append("&o=").append(porder);// 分页排序方式排序，-1:按照集数porder升序，1:按照集数porder降序，默认-1
        if (videoId != null) {
            // 此时参数 b 无效
            url.append("&vid=").append(videoId);// 该专辑下的视频id，返回vid所在页视频列表，并输出vid所在当前页码，必须与参数s共同使用。
        }
        url.append("&platform=").append("tv");// 播放平台
        url.append("&p=").append("420007");// 指定播放平台，可传空
        url.append("&lang=").append(lang);// 语言
        url.append("&region=").append(region);// 地区
        try {
            // http://i.api.letv.com/mms/inner/albumInfo/getVideoList?id={id}&b={b}&s={s}&o={o}&vid={vid}&platform={platform}&p={p}&lang={lang}&region={region}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<MmsResponse<MmsVideoList>>() {
                });
            }
        } catch (Exception e) {
            log.error("getPositiveVideoListByAlbumId return error: " + " return error: ", e);
        }

        return response;
    }

    /**
     * 获取youtube播放数据
     * @param externalId
     * @param commonParam
     * @return
     */
    public String getYoutubeContent(String externalId, CommonParam commonParam) {
        final String requestUrl = new StringBuilder("http://www.youtube.com/get_video_info?html5=1&video_id=")
                .append(externalId).append("&eurl&el=adunit").toString();
        String result = null;
        try {
            result = this.restTemplate.getForObject(requestUrl, String.class);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
            }
        } catch (Exception e) {
            this.log.error("getYoutubeContent_" + externalId + " return error: ", e);
        }
        return result;
    }

    /**
     * 刷新详情页动态接口
     * @param albumId
     * @param commonParam
     * @param logPrefix
     * @return
     */
    public boolean flushAlbumDetailMore(String albumId, CommonParam commonParam, String logPrefix) {
        final String requestUrl = ApplicationConstants.IPTV_I_API_BASE_HOST
                + "/iptv/api/new/video/album/detailandseries/more.json?flush={flush}&albumId={albumId}&terminalApplication={terminalApplication}&langcode={langcode}&broadcastId={broadcastId}";
        String result = null;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("albumId", albumId);
        params.put("flush", 1);
        params.put("terminalApplication", commonParam.getTerminalApplication());
        params.put("langcode", commonParam.getLangcode());
        params.put("broadcastId", commonParam.getBroadcastId());
        try {
            // http://10.58.89.189/iptv/api/new/video/album/detailandseries/more?flush=1&albumId=39215
            result = restTemplate.getForObject(requestUrl, String.class, params);
        } catch (Exception e) {
            this.log.error(logPrefix + "flushAlbumDetailMore_" + " return error: ", e);
        }

        return StringUtil.isNotBlank(result);
    }

    /**
     * [作品库]获取专辑详情数据
     * @param albumId
     *            专辑id
     * @return
     */
    public WorksResponse<WorksAlbumInfo> getWorksAlbumByAlbumId(Long albumId) {

        WorksResponse<WorksAlbumInfo> response = null;
        StringBuilder url = new StringBuilder();
        url.append(VideoTpConstant.WORKS_ALBUM_INFO_GET);
        url.append("id=").append(albumId);

        try {
            // http://10.112.33.186:8093/works/inner/material/albumDetailById?id=25064597
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<WorksResponse<WorksAlbumInfo>>() {
                });
            }
        } catch (Exception e) {
            log.error("getVideoListByAlbumId() return error: ", e);
        }

        return response;
    }

    /**
     * [作品库]批量获取专辑详情数据集
     * @param ids
     * @param commonParam
     * @return
     */
    public Map<String, WorksAlbumInfo> getWorksAlbumsByAlbumIds(String ids, CommonParam commonParam) {
        Map<String, WorksAlbumInfo> ret = null;
        String logPrefix = "getWorksAlbumsByAlbumIds_" + commonParam.getMac() + "_";
        WorksResponse<WorksAlbumInfo> response = null;
        final VideoTpDao videoTpDao = this;

        HttpHandler httpHandler = new HttpHandler() {
            @Override
            public String[] buildHttpGetUrls(int total, int pageSize, Object obj) {
                return null;
            }

            @Override
            public String[] buildHttpGetUrls(String ids, Object obj) {
                String[] idArr = null, urls = null;
                if (StringUtil.isNotBlank(ids)) {
                    idArr = StringUtils.split(ids, ",");
                    urls = new String[idArr.length];
                    StringBuilder subUrl = new StringBuilder();
                    StringBuilder params = new StringBuilder();
                    CommonParam commonParam = null;
                    if (obj instanceof CommonParam) {
                        commonParam = (CommonParam) obj;
                    }
                    for (int i = 0, length = idArr.length; i < length; i++) {
                        subUrl.setLength(0);
                        params.setLength(0);
                        subUrl.append(VideoTpConstant.WORKS_ALBUM_INFO_GET).append("?");
                        params.append("id=").append(idArr[i]);
                        subUrl.append(params);
                        urls[i] = subUrl.toString();
                    }
                }
                return urls;
            }

            @Override
            public WorksAlbumInfo formatResponse(Object result) throws Exception {
                WorksAlbumInfo ret = null;
                if (null != result) {
                    if (result instanceof String) {
                        WorksResponse<WorksAlbumInfo> worksResponse = objectMapper.readValue((String) result,
                                new TypeReference<WorksResponse<WorksAlbumInfo>>() {
                                });
                        if (worksResponse != null && worksResponse.getData() != null) {
                            ret = worksResponse.getData();
                        }
                    }
                }
                return ret;
            }

            @Override
            public void log(String log, Exception e) {
                videoTpDao.log.info(log, e);
            }
        };
        ret = this.getHttpPackages(ids, commonParam, httpHandler, WorksAlbumInfo.class, logPrefix);
        return ret;
    }

    /**
     * [作品库]分页获取专辑下视频列表数据
     * @param albumId
     *            专辑id
     * @param page
     *            第几页，用于分页使用，从1开始
     * @param pageSize
     *            每页数量 默认10，最大50
     * @param orderField
     *            排序字段: id, porder。默认使用porder
     * @param orderType
     *            排序方式: 1 降序，0 升序。 默认0
     * @param videoType
     *            查询类型，180000-其他 180001-正片 180002-花絮 180003-预告 不传-返回所有
     * @return
     */
    public WorksResponse<WorksVideoList> getWorksVideoListByAlbumId(Long albumId, Integer page, Integer pageSize,
            String orderField, Integer orderType, String videoType) {
        if (page == null || page < 0) {
            page = 1;
        }
        if (pageSize == null || pageSize < 0 || pageSize > VideoTpConstant.ALBUM_SERIES_PAGE_SIZE) {
            pageSize = VideoTpConstant.ALBUM_SERIES_PAGE_SIZE;
        }
        if (StringUtil.isBlank(orderField)) {
            orderField = "porder";
        }
        if (orderType == null) {
            orderType = 0;
        }

        WorksResponse<WorksVideoList> response = null;
        StringBuilder url = new StringBuilder();
        url.append(VideoTpConstant.WORKS_VIDEO_LIST_ALL);
        url.append("pid=").append(albumId);
        url.append("&pageNo=").append(page);
        url.append("&pageSize=").append(pageSize);
        url.append("&order=").append(orderField);
        url.append("&orderDesc=").append(orderType);
        if (StringUtil.isNotBlank(videoType)) {
            url.append("&videoType=").append(videoType);
        }

        try {
            // http://10.112.33.186:8093/works/inner/material/videoList?pid=21840384&videoType=180001&pageNo=1&pageSize=30&order=porder&orderDesc=0
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<WorksResponse<WorksVideoList>>() {
                });
            }
        } catch (Exception e) {
            log.error("getVideoListByAlbumId() return error: ", e);
        }

        return response;
    }

    /**
     * [作品库]打包获取专辑下所有视频列表数据
     * @param albumId
     *            专辑id
     * @param pageSize
     *            每页数量 默认10，最大50
     * @param orderField
     *            排序字段: id, porder。默认使用porder
     * @param orderType
     *            排序方式: 1 降序，0 升序。 默认0
     * @param videoType
     *            查询类型，180000-其他 180001-正片 180002-花絮 180003-预告 不传-返回所有
     * @return
     */
    public List<WorksVideoInfo> getWorksVideoListByAlbumId(final Long albumId, Integer pageSize, String orderField,
            Integer orderType, String videoType) {
        String logPrefix = "getVideoListByAlbumId_" + albumId + "_";
        List<WorksVideoInfo> ret = null;
        List<WorksResponse> response = null;
        final VideoTpDao videoTpDao = this;
        int totalCount = 0;

        if (pageSize == null || pageSize < 0 || pageSize > VideoTpConstant.ALBUM_SERIES_PAGE_SIZE) {
            pageSize = VideoTpConstant.ALBUM_SERIES_PAGE_SIZE;
        }

        // 1. 获取首页数据＋total值
        WorksResponse<WorksVideoList> worksResponsePageOne = this.getWorksVideoListByAlbumId(albumId, 1, pageSize,
                orderField, orderType, videoType);
        if (null != worksResponsePageOne && null != worksResponsePageOne.getData()) {
            totalCount = worksResponsePageOne.getData().getTotal();
            if (null != worksResponsePageOne.getData().getVideoList()
                    && worksResponsePageOne.getData().getVideoList().size() > 0) {
                ret = new ArrayList<WorksVideoInfo>();
                ret.addAll(worksResponsePageOne.getData().getVideoList());
            }
        }

        // 2. 利用step1返回的total值，走批量请求从第二页开始获取分页数据
        if (null != ret && totalCount > pageSize) {
            HttpHandler httpHandler = new HttpHandler() {
                @Override
                public String[] buildHttpGetUrls(String ids, Object obj) {
                    return null;
                }

                @Override
                public String[] buildHttpGetUrls(int total, int pageSize, Object obj) {
                    String[] urls = null;
                    int pageNum = 2, pageCount = 0;
                    if (total <= 0) {
                        total = VideoTpConstant.ALBUM_SERIES_PAGE_SIZE;
                        pageCount = 1;
                    } else {
                        pageCount = (total % pageSize == 0) ? total / pageSize : total / pageSize + 1;
                    }
                    urls = new String[pageCount - pageNum + 1];
                    StringBuilder subUrl = new StringBuilder();
                    StringBuilder params = new StringBuilder();
                    CommonParam commonParam = null;
                    if (null != obj) {
                        if (obj instanceof CommonParam) {
                            commonParam = (CommonParam) obj;
                        } else if (obj instanceof HashMap) {
                            for (Map.Entry<String, Object> param : ((HashMap<String, Object>) obj).entrySet()) {
                                params.append(param.getKey()).append("=").append(String.valueOf(param.getValue()))
                                        .append("&");
                            }
                        }
                    }
                    for (int i = pageNum; i <= pageCount; i++) {
                        subUrl.setLength(0);
                        subUrl.append(VideoTpConstant.WORKS_VIDEO_LIST_ALL);
                        if (null != params && params.length() > 0) {
                            subUrl.append(params);
                        }
                        subUrl.append("pageNo=").append(i);
                        subUrl.append("&pageSize=").append(pageSize);
                        urls[i - pageNum] = subUrl.toString();
                    }
                    return urls;
                }

                @Override
                public WorksResponse formatResponse(Object result) throws Exception {
                    WorksResponse<WorksVideoList> ret = null;
                    if (null != result) {
                        if (result instanceof String) {
                            if (StringUtil.isNotBlank(((String) result))) {
                                ret = objectMapper.readValue((String) result,
                                        new LetvTypeReference<WorksResponse<WorksVideoList>>() {
                                        });
                            }
                        }
                    }
                    return ret;
                }

                @Override
                public void log(String log, Exception e) {
                    videoTpDao.log.info(log, e);
                }
            };

            HashMap params = new HashMap();
            params.put("pid", albumId);
            if (StringUtil.isNotBlank(orderField)) {
                params.put("order", orderField);
            }
            if (null != orderType) {
                params.put("orderDesc", orderType);
            }
            if (StringUtil.isNotBlank(videoType)) {
                params.put("videoType", videoType);
            }
            response = this.getHttpPackages(totalCount, pageSize.intValue(), params, httpHandler, WorksResponse.class,
                    logPrefix);

            if (null != response) {
                WorksVideoList worksVideoList = null;
                for (WorksResponse worksResponse : response) {
                    if (null != worksResponse && null != worksResponse.getData()) {
                        worksVideoList = ((WorksVideoList) worksResponse.getData());
                        if (null != worksVideoList.getVideoList() && worksVideoList.getVideoList().size() > 0) {
                            ret.addAll(worksVideoList.getVideoList());
                        } else { // 不容许有异常数据！
                            ret = null;
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }
}
