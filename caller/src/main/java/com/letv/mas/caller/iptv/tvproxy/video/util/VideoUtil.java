package com.letv.mas.caller.iptv.tvproxy.video.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.JumpUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.StreamConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.SubscribeInfoV2;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.video.authentication.PlayAuthV2;
import com.letv.mas.caller.iptv.tvproxy.video.constants.ChargeTypeConstants;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.Stream;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoHot;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoUtil {
    private static final Logger log = LoggerFactory.getLogger(VideoUtil.class);

    private static final String REGEX = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";// 特殊字符

    public static Map<String, Stream> PRE_SET_STREAM = new HashMap<String, Stream>();

    public static Map<String, Stream> PRE_SET_STREAM_ZH_CN = new HashMap<String, Stream>();
    public static Map<String, Stream> PRE_SET_STREAM_ZH_HK = new HashMap<String, Stream>();
    public static Map<String, Stream> PRE_SET_STREAM_EN_US = new HashMap<String, Stream>();

    public static Map<String, Stream> PRE_SET_YUANXIAN_STREAM_ZH_CN = new HashMap<String, Stream>();
    public static Map<String, Stream> PRE_SET_YUANXIAN_STREAM_ZH_HK = new HashMap<String, Stream>();
    public static Map<String, Stream> PRE_SET_YUANXIAN_STREAM_EN_US = new HashMap<String, Stream>();

    public static Stream USER_SETTING_STREAMS_ZH_CN = new Stream();
    public static Stream USER_SETTING_STREAMS_ZH_HK = new Stream();
    public static Stream USER_SETTING_STREAMS_EN_US = new Stream();

    public static interface validateMmsStore {
        public static final int SUCCESS = 0;
        public static final int NULL_STORE = 1;
        public static final int VIDEO_CN_PLAY_FORBIDDEN = 2;
        public static final int VIDEO_NOT_CN_PLAY_FORBIDDEN = 3;
        public static final int STORE_STATUS_NOT_SUCCESS = 4;
        public static final int INFO_NULL = 5;
        public static final int VIDEO_HK_PLAY_FORBIDDEN = 6;
        public static final int VIDEO_NOT_CN_NOT_HK_PLAY_FORBIDDEN = 7;
        public static final int VIDEO_UNKNOW_PLAY_FORBIDDEN = 8;
    }

    /**
     * 媒资内容分级字典MAP
     */
    public static Map<String, DictTp> DICT_MAP = new HashMap<String, DictTp>();

    public final static long NO_AREA_PLAY_RESTRICT_MAC_UPDATE_INTERVAL = 3600000L; // 1小时
    // 3600000L
    public static long NO_AREA_PLAY_RESTRICT_MAC_LASTUPDATE_TIME = 0L; // 上次更新时间
    public static Lock NO_AREA_PLAY_RESTRICT_MAC_UPDATE_Lock = new ReentrantLock(); // 用于定时更新

    /**
     * mac白名单
     */
    private static Set<String> NO_AREA_PLAY_RESTRICT_MAC_SET = new HashSet<String>();

    /**
     * 可多机器登录userId白名单
     */
    public static Set<String> MUTIL_MACHINE_LOGIN_USERID_WHITE_SET = new HashSet<String>();

    /**
     * 获得子平台ID
     * @param wcode
     * @param terminalApplication
     * @param terminalSeries
     * @return
     */
    public static int getSplatId(Integer model, CommonParam commonParam) {
        int splatId = 0;

        if (model != null && VideoConstants.PLAY_MODEL_TOUPING == model) {
            // 投屏播放特殊分配
            splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_TV_TOUPING;
        } else if (VideoTpConstant.CIBN_BSCHANNEL_OPERATORS.equalsIgnoreCase(commonParam.getBsChannel())) {
            splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_CIBN_OPERATORS_PC;// 国广版本第三方合作播放
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) {

            // splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_OVERSEA;
            // TV le使用推荐升级方式，线上有多个版本，这里针对不同集成了不同版本CDE的TV Le版本下发不同splatid
            int appCodeInt = StringUtil.toInteger(commonParam.getAppCode(), 0);
            if (appCodeInt >= VideoConstants.PLAY_HAS_NEW_CDE_LE_VERSION) {
                splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_TV_LECOM_US;
            } else {
                splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_OVERSEA;
            }
        } else if (ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(commonParam.getWcode())) {
            if (ProductLineConstants.LETV_COMMON.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_COMMON;
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_FISH.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
                splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_FISH;
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_CHILD_APP.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
                Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
                String terminalSeries = StringUtil.dealNull(commonParam.getTerminalSeries());
                // if
                // (VideoConstants.LETV_SERIES_TV_SET.contains(commonParam.getTerminalSeries()))
                // {
                if (!VideoConstants.LETV_SERIES_BOX_SET.contains(terminalSeries.toUpperCase())) {
                    splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD;
                    if (TerminalUtil.supportAntiReport(commonParam)) {
                        splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_TV;
                    } else if (ac >= 40) {
                        splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_TV;
                    } else {
                        splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_TV;
                    }
                } else {
                    splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD;
                    if (TerminalUtil.supportAntiReport(commonParam)) {
                        splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_BOX;
                    } else if (ac >= 40) {
                        splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_BOX;
                    } else {
                        splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_BOX;
                    }
                }
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_ALONE.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
                // Integer ac =
                // TerminalUtil.parseAppCode(commonParam.getAppCode());
                String terminalSeries = StringUtil.dealNull(commonParam.getTerminalSeries());
                // if
                // (VideoConstants.LETV_SERIES_TV_SET.contains(commonParam.getTerminalSeries()))
                // {
                if (!VideoConstants.LETV_SERIES_BOX_SET.contains(terminalSeries.toUpperCase())) {
                    splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_TV;
                } else {
                    splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_BOX;
                }
            } else {
                String terminalSeries = StringUtil.dealNull(commonParam.getTerminalSeries());
                // if
                // (VideoConstants.LETV_SERIES_TV_SET.contains(commonParam.getTerminalSeries()))
                // {
                if (!VideoConstants.LETV_SERIES_BOX_SET.contains(terminalSeries.toUpperCase())) {
                    splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_TV;
                    if (TerminalUtil.supportAntiReport(commonParam)) {
                        splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_TV_NEW;
                    }
                } else {
                    splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_BOX;
                    if (TerminalUtil.supportAntiReport(commonParam)) {
                        splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_BOX_NEW;
                    }
                }
            }
        } else {
            splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_OVERSEA;
        }

        return splatId;
    }

    /**
     * 根据码流获得VTYPE值
     * @param streamCode
     * @return
     */
    public static String getVType(String streamCode, boolean isMarlin, String terminalSeries) {
        StringBuffer vType = new StringBuffer();
        if (isMarlin) {
            vType = vType
                    .append(LetvStreamCommonConstants.MARLIN_STREAM_CODE_MAP
                            .get((LetvStreamCommonConstants.VIDEO_TYPE_MP4_PREFIX + streamCode)) != null ? LetvStreamCommonConstants.MARLIN_STREAM_CODE_MAP
                            .get((LetvStreamCommonConstants.VIDEO_TYPE_MP4_PREFIX + streamCode))
                            : LetvStreamCommonConstants.MARLIN_STREAM_CODE_MAP
                                    .get((LetvStreamCommonConstants.VIDEO_TYPE_FLV_PREFIX + streamCode)));
        } else if (StringUtils.isNotEmpty(streamCode)) {
            String[] scArray = streamCode.split(",");
            for (String sc : scArray) {
                if (TerminalTool.SUPPORT_H265_DEVICES.contains(terminalSeries)) {
                    vType = vType.append(LetvStreamCommonConstants.SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.get(sc));
                } else {
                    vType = vType.append(LetvStreamCommonConstants.VTYPE_REDUCED_MAP.get(sc));
                }
            }
        }

        return vType.toString();
    }

    /**
     * 是否请求TS流(m3u8形式)
     * @param stream
     * @return
     */
    public static Boolean expectTS(String stream, boolean isMarlin) {
        // 3D、杜比码流统一不走m3u8，而是走mp4流
        if (StringUtils.isNotEmpty(stream)
                && ((LetvStreamCommonConstants.CODE_NAME_DOLBY_720p.equalsIgnoreCase(stream)
                        || LetvStreamCommonConstants.CODE_NAME_DOLBY_1300.equalsIgnoreCase(stream)
                        || LetvStreamCommonConstants.CODE_NAME_DOLBY_1000.equalsIgnoreCase(stream)
                        || LetvStreamCommonConstants.CODE_NAME_DOLBY_1080p.equalsIgnoreCase(stream)
                        || LetvStreamCommonConstants.CODE_NAME_DOLBY_1080p_ATMOS.equalsIgnoreCase(stream)
                        || stream.toLowerCase().indexOf(LetvStreamCommonConstants.CODE_NAME_3D) > -1 || isMarlin))) {
            return false;
        }
        return true;
    }

    /**
     * 根据码流获得媒资id
     * @param pid
     * @param playStream
     * @param videoStreams
     * @param midStreams
     * @return
     */
    public static Long getMidByStream(Long pid, String playStream, String videoStreams, String midStreams) {

        Long mid = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (midStreams != null) {
                Map<String, String> map = mapper.readValue(midStreams, new TypeReference<Map<String, String>>() {
                });
                if (map != null) {
                    Set<Entry<String, String>> entrySet = map.entrySet();
                    for (Entry<String, String> e : entrySet) {
                        String id = e.getKey();
                        String value = e.getValue();
                        if (value == null) {
                            break;
                        }
                        String[] streams = e.getValue().split(",");
                        for (String s : streams) {
                            if (s.equalsIgnoreCase(playStream)) {
                                mid = new Long(id);
                                break;
                            }
                        }
                        if (mid != null) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("getMidByStream_ error", e.getMessage(), e);
        }
        return mid;
    }

    /**
     * 判断是否请求DRM marlin码流(only HK for now)
     * @param drmFlagId
     * @return
     */
    public static boolean isMarlin(String drmFlagId, String wcode) {
        boolean isMarlin = false;

        if (ProductLineConstants.WCODE.LETV_HK.equalsIgnoreCase(wcode) && StringUtils.isNotEmpty(drmFlagId)) {
            isMarlin = drmFlagId.contains("642002");
        }

        return isMarlin;
    }

    /**
     * 专辑是否按照综艺样式展示
     */
    public static boolean isVarietyShow(AlbumMysqlTable album, CommonParam commonParam) {
        // 美国不做综艺样式的展示逻辑
        boolean isVarietyShow = false;
        if (album != null && StringUtils.isNotBlank(album.getVarietyShow()) && album.getCategory() != null) {
            Integer categoryId = album.getCategory();
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())
                    && (categoryId == MmsTpConstant.MMS_CATEGARY_TV || categoryId == MmsTpConstant.MMS_CATEGARY_VARIETY
                            || categoryId == MmsTpConstant.MMS_CATEGARY_CARTOON
                            || categoryId == MmsTpConstant.MMS_CATEGARY_DFILM || categoryId == MmsTpConstant.MMS_CATEGARY_EDUCATION)) {
                // 2017-01-06
                // 专辑详情页和播放下拉列表页剧集需求统一，仅电视剧/综艺/动漫/纪录片/教育频道考虑媒资后台“是否剧集展示-varietyShow”字段
                isVarietyShow = VideoConstants.AlbumDetail.ALBUM_IS_VARITY_SHOW.equals(album.getVarietyShow());
            } else if ((TerminalCommonConstant.TERMINAL_APPLICATION_CHILD_APP.equalsIgnoreCase(commonParam
                    .getTerminalApplication()) || TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_ALONE
                    .equalsIgnoreCase(commonParam.getTerminalApplication()))) {
                if (categoryId == VideoConstants.Category.CARTOON || categoryId == VideoConstants.Category.PARENTING
                        || categoryId == VideoConstants.Category.TEACH
                        || categoryId == VideoConstants.Category.TEACH_CHILD) {// 其他分类
                    if (VideoCommonUtil.isPositive(2, album.getCategory(), album.getAlbum_type(), null)
                            && VideoConstants.AlbumDetail.ALBUM_IS_VARITY_SHOW.equals(album.getVarietyShow())) { // 正片专辑且勾选“栏目”
                        isVarietyShow = true;
                    }
                } else if (categoryId == VideoConstants.Category.VARIETY) { // 综艺类默认为xxx期，除少数个别的
                    if (VideoCommonUtil.isPositive(2, album.getCategory(), album.getAlbum_type(), null)
                            || VideoConstants.AlbumDetail.ALBUM_IS_VARITY_SHOW.equals(album.getVarietyShow())) { // 正片专辑且勾选“栏目”
                        isVarietyShow = true;
                    }
                }
            } else {
                if (categoryId == VideoConstants.Category.TV) {// 电视剧的判断
                    if (VideoCommonUtil.isPositive(2, album.getCategory(), album.getAlbum_type(), null)
                            && VideoConstants.AlbumDetail.ALBUM_IS_VARITY_SHOW.equals(album.getVarietyShow())) { // 正片专辑且勾选“栏目”
                        isVarietyShow = true;
                    }
                }
            }
        }

        return isVarietyShow;
    }

    /**
     * 获得片头、片尾时间
     * @param cid
     * @param video
     * @return
     */
    public static VideoHot getHeadTailInfo(Integer categoryId, Integer bTime, Integer eTime, Integer model) {
        VideoHot h = new VideoHot();
        if ((VideoConstants.Category.TV == categoryId) || (model != null && model == 1)) {
            h.setT(bTime == null ? 0 : bTime * 1000);
            h.setF(eTime == null ? 0 : eTime * 1000);
        }
        return h;
    }

    /**
     * 根据格式 获取最终url
     */
    public static String getFinalPlayUrlByFormat(String playUrl, String ifCharge, String terminalSeries,
            Boolean expectTS, Boolean expectTSActual, Boolean dispach302, Boolean exceptNomalActual, Integer termid,
            Boolean clientDeside) {
        String actualPlayUrl = playUrl;
        if (StringUtils.isEmpty(ifCharge)) {
            ifCharge = "0";
        }
        if (StringUtils.isEmpty(terminalSeries)) {
            terminalSeries = "un";
        }
        terminalSeries = StringFilter(terminalSeries);
        if (termid == null) {
            termid = 4;// 电视
            if (TerminalTool.isBox(terminalSeries)) {
                termid = 3;// 盒子
            }
        }
        Boolean actual = false;
        if (Boolean.TRUE == expectTS) {// m3u8文件
            actualPlayUrl = playUrl + "&tag=tvts&termid=" + termid + "&ostype=android&pay=" + ifCharge + "&hwtype="
                    + terminalSeries;// termid: 0：未知 1：PC端 2：移动端(移动设备)
            // 3：盒端(各种型号盒子) 4：电视(超级电视/海信电视)
            if (Boolean.TRUE == expectTSActual) {
                actual = true;
            }
        } else if (Boolean.TRUE == dispach302) {// 期待302跳转
            if (clientDeside) {
                playUrl += "&format=0";
            }
            actualPlayUrl = playUrl + "&tag=tv&termid=" + termid + "&ostype=android&pay=" + ifCharge + "&hwtype="
                    + terminalSeries;// termid: 0：未知 1：PC端 2：移动端(移动设备)
            // 3：盒端(各种型号盒子) 4：电视(超级电视/海信电视)
        } else {// 获得正常文件
            if (!clientDeside) {
                playUrl += "&expect=6&format=2";
            }
            actualPlayUrl = playUrl + "&tag=tv&termid=" + termid + "&ostype=android&pay=" + ifCharge + "&hwtype="
                    + terminalSeries;// 6个节点用于多线程 /format 0:302跳转；1json 2xml
            if (exceptNomalActual) {
                actual = true;
            }
        }
        if (actual) {// 获得真实播放地址
            // CdnDispatchResponse cndDispatchResponse =
            // this.facadeRestClient.getPlayRestClient().cndDispatchResponse(
            // actualPlayUrl);
            // List<String> urlList =
            // cndDispatchResponse.getNodelist().getUrls();
            // if (urlList.size() > 0) {
            // actualPlayUrl = urlList.get(0);
            // }
        }
        return actualPlayUrl;
    }

    /**
     * 过滤特殊字符
     * @param str
     * @return
     */
    public static String StringFilter(String str) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(str);
        String result = m.replaceAll("").trim();
        try {
            result = URLEncoder.encode(result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("StringFilter_ error", e.getMessage(), e);
        }
        return result;
    }

    /**
     * 验证合法性
     * @param store
     * @return
     */
    public static int validateMmsStore(MmsStore store) {
        int validateResult = validateMmsStore.SUCCESS;// 默认校验成功

        if (store == null) {
            validateResult = validateMmsStore.NULL_STORE;// 获得媒资播放文件为空
        } else {
            if (store.getPlayStatus() != null && 1 == store.getPlayStatus()) {
                if (StringUtils.isNotEmpty(store.getCountry()) && !store.getCountry().equalsIgnoreCase("XX")
                        && !store.getCountry().equalsIgnoreCase("0")) {
                    if (store.getCountry().equalsIgnoreCase("CN")) { // 在大陆被禁
                        validateResult = validateMmsStore.VIDEO_CN_PLAY_FORBIDDEN;
                    } else if (store.getCountry().equalsIgnoreCase("HK")) { // 在香港被禁
                        validateResult = validateMmsStore.VIDEO_HK_PLAY_FORBIDDEN;
                    } else { // 在除大陆和香港的国家被禁
                        validateResult = validateMmsStore.VIDEO_NOT_CN_NOT_HK_PLAY_FORBIDDEN;
                    }
                } else { // 不知所在地区
                    validateResult = validateMmsStore.VIDEO_UNKNOW_PLAY_FORBIDDEN;
                }
            } else if (!store.getStatusCode().equalsIgnoreCase(VideoTpConstant.VIDEO_PLAY_MMS_DATA_CODE_SUCCESS)) { // 状态为失败
                validateResult = validateMmsStore.STORE_STATUS_NOT_SUCCESS;
            } else if ((store.getData() == null) || (store.getData().size() < 1)
                    || (store.getData().get(0).getInfos() == null) || (store.getData().get(0).getInfos().size() < 1)) { // data为空时
                validateResult = validateMmsStore.INFO_NULL;
            }
        }

        return validateResult;
    }

    /**
     * 获得VIP G3调度地址
     * 获取规则:如果boss鉴权通过,则将用户token,uid及uinfo信息拼接playUrl参数中,否则直接返回形参playUrl
     * @param playUrl
     *            原调度地址
     * @param userPlayAuth
     *            鉴权bean,里面应存放鉴权的响应信息
     * @return
     */
    public static String getVIPUrl(PlayAuthV2 userPlayAuth, String playUrl) {
        String userPlayUrl = playUrl;
        /*
         * VIP用户加入token,uid及uinfo
         */
        if (userPlayAuth != null && userPlayAuth.isAuthenticationPass()) {
            userPlayUrl = playUrl + "&token=" + StringUtils.trimToEmpty(userPlayAuth.getToken()) + "&uid="
                    + StringUtils.trimToEmpty(userPlayAuth.getTokenUserId());
            if (StringUtils.isNotEmpty(userPlayAuth.getUinfo())) {
                userPlayUrl = userPlayUrl + "&uinfo=" + userPlayAuth.getUinfo();
            }
        }

        return userPlayUrl;
    }

    /**
     * 获得VIP G3调度地址
     * @return
     */
    public static String getVIPUrl(Boolean login, UserPlayAuth userPlayAuth, String playUrl, CommonParam commonParam) {
        String userPlayUrl = playUrl;

        // VIP 用户加入token和uid
        if ((userPlayAuth != null) && userPlayAuth.getStatus() != null) {
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())
                    || CommonConstants.PLAY_SERVICE_OPEN == userPlayAuth.getStatus()) {
                // le直接拼接token，其他版本只有鉴权成功才拼接token
                userPlayUrl = playUrl + "&token=" + StringUtils.trimToEmpty(userPlayAuth.getToken()) + "&uid="
                        + StringUtils.trimToEmpty(userPlayAuth.getTokenUserId());
            }
            if (StringUtils.isNotEmpty(userPlayAuth.getUinfo())) {
                userPlayUrl = userPlayUrl + "&uinfo=" + userPlayAuth.getUinfo();
            }
        }

        return userPlayUrl;
    }

    /**
     * 播放 loading 页显示名称
     * @param categoryId
     * @param albumName
     * @param videoName
     * @param order
     * @param stream
     * @param starring
     * @param locale
     * @param videoAttr
     * @return
     */
    public static String getPlayShowName(Integer categoryId, String albumName, String videoName, Integer order,
            String stream, String starring, Integer videoAttr, CommonParam commonParam) {
        return getPlayShowName(categoryId, albumName, videoName, order, stream, starring, videoAttr, commonParam, null);
    }

    public static String getPlayShowName(Integer categoryId, String albumName, String videoName, Integer order,
            String stream, String starring, Integer videoAttr, CommonParam commonParam, AlbumMysqlTable albumMysqlTable) {
        StringBuffer sb = new StringBuffer();
        if (categoryId == null || videoAttr == null || videoAttr == 0) {
            return videoName + " " + LetvStreamCommonConstants.nameOf(stream, commonParam.getLangcode());
        }
        if (categoryId == VideoConstants.Category.FILM) {// 电影
            sb.append(albumName);
        } else if (categoryId == VideoConstants.Category.CARTOON || categoryId == VideoConstants.Category.TV
                || categoryId == VideoConstants.Category.TEACH) {// 电视剧、动漫
            sb.append(albumName);
            if (order != null
                    && (null != albumMysqlTable && null != albumMysqlTable.getEpisode() && albumMysqlTable.getEpisode() > 1)) {
                sb.append(" ").append(
                        MessageUtils.getMessage(VideoConstants.VIDEO_PLAY_SERIES_NUM_TEXT, commonParam.getLangcode(),
                                new Integer[] { order }));
            }
        } else if (categoryId == VideoConstants.Category.VARIETY) {// 综艺
            sb.append(albumName);
            if (order != null
            /*
             * && (null != albumMysqlTable && null !=
             * albumMysqlTable.getEpisode() && albumMysqlTable.getEpisode() > 1)
             */) {
                sb.append(" ").append(
                        MessageUtils.getMessage(VideoConstants.VIDEO_PLAY_PHASE_NUM_TEXT, commonParam.getLangcode(),
                                new String[] { String.valueOf(order) }));
            }
        } else {
            // if (categoryId == VideoConstants.Category.MUSIC
            // &&
            // !TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
            // .getTerminalApplication())) {
            // //sb.append(starring == null ? "" : starring.replace(",",
            // "")).append(" ").append(videoName);
            // sb.append(videoName);
            // } else {
            // sb.append(videoName);
            // }
            sb.append(videoName);
            if (order != null
            /*
             * && (null != albumMysqlTable && null !=
             * albumMysqlTable.getEpisode() && albumMysqlTable.getEpisode() > 1)
             */) {
                if (null != albumMysqlTable && albumMysqlTable.isVarietyStyleShow()) { // xxx期
                    sb.append(" ").append(
                            MessageUtils.getMessage(VideoConstants.VIDEO_PLAY_PHASE_NUM_TEXT,
                                    commonParam.getLangcode(), new String[] { String.valueOf(order) }));
                } else { // xxx集
                    sb.append(" ").append(
                            MessageUtils.getMessage(VideoConstants.VIDEO_PLAY_SERIES_NUM_TEXT,
                                    commonParam.getLangcode(), new Integer[] { order }));
                }
            }
        }
        // le。com 播放 loading 页 名称不拼接码流
        // if
        // (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication()))
        // {
        // return sb.toString();
        // } else {
        // String streamName = LetvStreamCommonConstants.nameOf(stream,
        // commonParam.getLangcode());
        // return sb.append(" ").append(streamName).toString();
        // }
        return sb.toString();
    }

    /**
     * 返回试看时长
     * @param category
     * @param tailTime
     * @param duration
     * @return
     */
    public static Long getTryPlayTime(Integer category, Integer tailTime, Long duration) {
        Long trytime = 0l;
        if (VideoConstants.Category.TV == category || VideoConstants.Category.CARTOON == category) {// 电视剧、动漫有片尾返回片尾时间、否则返回时长
            if (tailTime != null && tailTime > 0) {
                trytime = new Long(tailTime);
            } else if (duration != null && duration > 0) {
                trytime = duration;
            }
        } else {// 电影返回6分钟
            trytime = new Long(1000 * 60 * 6);
        }
        return trytime;
    }

    /**
     * 获得预置码流
     * @return
     */
    public static Map<String, Stream> getPreSetStream(String locale, boolean isThirdParty) {
        if ("zh_hk".equalsIgnoreCase(locale)) {
            if (CollectionUtils.isEmpty(PRE_SET_STREAM_ZH_HK)) {
                PRE_SET_STREAM_ZH_HK = StreamConstants.getStreamMap(LetvStreamCommonConstants.ALL_STREAMS, locale,
                        isThirdParty);
            }

            return PRE_SET_STREAM_ZH_HK;
        } else if ("en_us".equalsIgnoreCase(locale)) {
            if (CollectionUtils.isEmpty(PRE_SET_STREAM_EN_US)) {
                PRE_SET_STREAM_EN_US = StreamConstants.getStreamMap(LetvStreamCommonConstants.ALL_STREAMS, locale,
                        isThirdParty);
            }

            return PRE_SET_STREAM_EN_US;
        } else {
            PRE_SET_STREAM_ZH_CN = StreamConstants.getStreamMap(LetvStreamCommonConstants.ALL_STREAMS, locale,
                    isThirdParty);

            return PRE_SET_STREAM_ZH_CN;
        }
    }

    /**
     * 获得预置码流
     * @return
     */
    public static Map<String, Stream> getYuanXianPreSetStream(String locale) {
        if ("zh_hk".equalsIgnoreCase(locale)) {
            if (CollectionUtils.isEmpty(PRE_SET_YUANXIAN_STREAM_ZH_HK)) {
                PRE_SET_YUANXIAN_STREAM_ZH_HK = StreamConstants.getYuanXianStreamMap(
                        LetvStreamCommonConstants.ALL_STREAMS, locale);
            }

            return PRE_SET_YUANXIAN_STREAM_ZH_HK;
        } else if ("en_us".equalsIgnoreCase(locale)) {
            if (CollectionUtils.isEmpty(PRE_SET_YUANXIAN_STREAM_EN_US)) {
                PRE_SET_YUANXIAN_STREAM_EN_US = StreamConstants.getYuanXianStreamMap(
                        LetvStreamCommonConstants.ALL_STREAMS, locale);
            }

            return PRE_SET_YUANXIAN_STREAM_EN_US;
        } else {
            if (CollectionUtils.isEmpty(PRE_SET_YUANXIAN_STREAM_ZH_CN)) {
                PRE_SET_YUANXIAN_STREAM_ZH_CN = StreamConstants.getYuanXianStreamMap(
                        LetvStreamCommonConstants.ALL_STREAMS, locale);
            }

            return PRE_SET_YUANXIAN_STREAM_ZH_CN;
        }
    }

    /**
     * 获得用户设置码流列表
     * @param langCode
     * @param terminalApplication
     * @return
     */
    public static Stream getUserSettringStreams(CommonParam commonParam) {

        if (LocaleConstant.Langcode.ZH_HK.equalsIgnoreCase(commonParam.getLangcode())) {
            if (CollectionUtils.isEmpty(USER_SETTING_STREAMS_ZH_HK.getPlayStreams())
                    || CollectionUtils.isEmpty(USER_SETTING_STREAMS_ZH_HK.getLiveStreams())) {
                USER_SETTING_STREAMS_ZH_HK.setPlayStreams(getUserSettingPlayStreams(commonParam));
                USER_SETTING_STREAMS_ZH_HK.setLiveStreams(getUserSettingLiveStreams(commonParam.getLangcode()));
            }
            return USER_SETTING_STREAMS_ZH_HK;
        } else if (LocaleConstant.Langcode.EN_US.equalsIgnoreCase(commonParam.getLangcode())) {
            if (CollectionUtils.isEmpty(USER_SETTING_STREAMS_EN_US.getPlayStreams())
                    || CollectionUtils.isEmpty(USER_SETTING_STREAMS_EN_US.getLiveStreams())) {
                USER_SETTING_STREAMS_EN_US.setPlayStreams(getUserSettingPlayStreams(commonParam));
                USER_SETTING_STREAMS_EN_US.setLiveStreams(getUserSettingLiveStreams(commonParam.getLangcode()));
            }

            return USER_SETTING_STREAMS_EN_US;
        } else {
            if (CollectionUtils.isEmpty(USER_SETTING_STREAMS_ZH_CN.getPlayStreams())
                    || CollectionUtils.isEmpty(USER_SETTING_STREAMS_ZH_CN.getLiveStreams())) {
                USER_SETTING_STREAMS_ZH_CN.setPlayStreams(getUserSettingPlayStreams(commonParam));
                USER_SETTING_STREAMS_ZH_CN.setLiveStreams(getUserSettingLiveStreams(commonParam.getLangcode()));
            }

            return USER_SETTING_STREAMS_ZH_CN;
        }
    }

    private static List<Stream> getUserSettingPlayStreams(CommonParam commonParam) {
        List<Stream> playStreams = new ArrayList<Stream>();
        String allPlayStream = LetvStreamCommonConstants.USER_SETTING_PLAY_STREAM;// 通用版客户端过滤4K

        String[] streamArr = allPlayStream.split("#");
        for (String key : streamArr) {
            Stream stream = new Stream();
            stream.setName(LetvStreamCommonConstants.nameOf(key, commonParam.getLangcode()));
            stream.setCode(key);
            if (key.equals(LetvStreamCommonConstants.CODE_NAME_720p)) {
                stream.setIsDefault(true);
            }
            if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equals(key)
                    || LetvStreamCommonConstants.CODE_NAME_1080p.equals(key)) {
                stream.setKbps(LetvStreamCommonConstants.getMbps(LetvStreamCommonConstants.CODE_NAME_1080p3m));
            } else {
                stream.setKbps(LetvStreamCommonConstants.getMbps(LetvStreamCommonConstants.CODE_NAME_800.equals(key) ? LetvStreamCommonConstants.CODE_NAME_1000
                        : key));
            }
            stream.setBandWidth(LetvStreamCommonConstants.getTipText(key, commonParam.getLangcode()));
            playStreams.add(stream);
        }

        return playStreams;
    }

    private static List<Stream> getUserSettingLiveStreams(String langCode) {
        List<Stream> liveStreams = new ArrayList<Stream>();
        String allLiveStream = LetvStreamCommonConstants.USER_SETTING_LIVE_STREAM;
        String[] liveStreamArr = allLiveStream.split("#");
        for (String key : liveStreamArr) {
            Stream stream = new Stream();
            stream.setName(LetvStreamCommonConstants.nameOf(key, langCode));
            stream.setCode(key);
            if (key.equals(LetvStreamCommonConstants.CODE_NAME_1300)) {
                stream.setIsDefault(true);
            }
            if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equals(key)
                    || LetvStreamCommonConstants.CODE_NAME_1080p.equals(key)) {
                stream.setKbps(LetvStreamCommonConstants.getMbps(LetvStreamCommonConstants.CODE_NAME_1080p3m));
            } else {
                stream.setKbps(LetvStreamCommonConstants.getMbps(LetvStreamCommonConstants.CODE_NAME_800.equals(key) ? LetvStreamCommonConstants.CODE_NAME_1000
                        : key));
            }
            stream.setBandWidth(LetvStreamCommonConstants.getLiveTipText(key, langCode));
            liveStreams.add(stream);
        }

        return liveStreams;
    }

    public static String[] sortStream(String[] streams) {
        String[] streamArr = LetvStreamCommonConstants.ALL_STREAMS.split("#");
        String[] sortStreams = new String[streams.length - 1];
        int index = 0;
        for (String key : streamArr) {
            if (ArrayUtils.contains(streams, key)) {
                sortStreams[index] = key;
                index++;
            }
        }

        return sortStreams;
    }

    public static String transPlay6M2Play3M(String playStream, String videoStreams, Long pid) {
        if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(playStream)
                && videoStreams.indexOf(LetvStreamCommonConstants.CODE_NAME_1080p3m) > -1
                && !LetvStreamCommonConstants.contains(pid)) {// 如果是1080p6m,并且有3m的则取3m
            playStream = LetvStreamCommonConstants.CODE_NAME_1080p3m;
        }

        return playStream;
    }

    public static MmsFile getMmsFileByVTypeOrder(String playStream, MmsStore mmsStore) {

        MmsFile compatibleMmsFile = null;
        if (!ArrayUtils.isEmpty(LetvStreamCommonConstants.PLAY_REDUCED_MAP.get(playStream))) {
            Integer[] vtypesInOrder = LetvStreamCommonConstants.PLAY_REDUCED_MAP.get(playStream);

            for (Integer vtype : vtypesInOrder) {
                MmsInfo mmsInfo = mmsStore.getData().get(0);
                List<MmsFile> mmsFiles = mmsInfo.getInfos();
                for (MmsFile mmsFile : mmsFiles) {
                    if (mmsFile != null && vtype.intValue() == mmsFile.getVtype().intValue()) {
                        compatibleMmsFile = mmsFile;
                        break;
                    }
                }
                if (compatibleMmsFile != null) {
                    break;
                }
            }
        }

        if (compatibleMmsFile == null) {
            MmsInfo mmsInfo = mmsStore.getData().get(0);
            compatibleMmsFile = mmsInfo.getInfos().get(0);
        }

        return compatibleMmsFile;
    }

    public static String getCibnUrlWithParam(String playUrl, String videoId, String audioId) {
        playUrl = playUrl + "&p1=2&p2=21&vid=" + videoId + "&a_idx=" + audioId;

        return playUrl;
    }

    public static String getLetvUrlWithParam(String playUrl, String videoId, String audioId) {
        playUrl = playUrl + "&p1=2&p2=21&vid=" + videoId + "&a_idx=" + audioId;

        return playUrl;
    }

    public static String getCibnBsChannelUrlWithParam(String playUrl, String videoId, String audioId) {
        playUrl = playUrl + "&p1=2&p2=29&p3=op_cibn_201606&vid=" + videoId + "&a_idx=" + audioId;
        return playUrl;
    }

    /**
     * 获取专辑更新期数
     * @param Num
     * @param format1
     * @param format2
     * @param format3
     * @return
     */
    public static String getFollowNum(Integer Num, String format1, String format2, String format3) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat df = new SimpleDateFormat(format1);
            SimpleDateFormat df1 = new SimpleDateFormat(format2);
            SimpleDateFormat df2 = new SimpleDateFormat(format3);
            Date followDate = sdf.parse(Num.toString());
            Date nowDate = new Date();
            String followStr = null;
            String nowStr = null;
            String followNum = "";
            followStr = df.format(followDate);
            nowStr = df.format(nowDate);
            if (Integer.parseInt(followStr) < Integer.parseInt(nowStr)) {
                followNum = df1.format(followDate);
            } else {
                followNum = df2.format(followDate);
            }
            return followNum;
        } catch (ParseException e) {
            log.warn("getFollowNum error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * 码流过滤的统一方法
     * @param playStreams
     *            视频支持的码流
     * @param model
     *            1--儿童模式
     * @param commonParam
     *            通用参数
     * @return
     */
    public static String[] playStreamFilter(String[] playStreams, Integer model, CommonParam commonParam) {
        return playStreamFilter(playStreams, model, commonParam, null);
    }

    public static String[] playStreamFilter(String[] playStreams, Integer model, CommonParam commonParam,
            VideoDto videoPlay) {
        if (playStreams == null || playStreams.length == 0) {
            return new String[0];
        }

        if ((model != null && model == 1) || (null != videoPlay && null != videoPlay.getCategoryId())) {
            List<String> slist = new ArrayList<String>();
            for (String s : playStreams) {
                if (s != null && ((model != null && model == 1 && !s.contains("3d") && !s.contains("1080")) // 儿童去掉3d及1080码流
                        || (null != videoPlay && (!s.contains("1080") || // 非电影频道去掉1080码流
                        s.contains("1080") && videoPlay.getCategoryId() == VideoConstants.Category.FILM)))) {
                    slist.add(s);
                }
            }
            playStreams = slist.toArray(new String[slist.size()]);
        }
        if (commonParam != null && TerminalTool.isNotSupportDbSeriesCode(commonParam.getTerminalSeries())) {
            List<String> slist = new ArrayList<String>();
            for (String s : playStreams) {
                if (s != null && !s.contains("db")) {
                    slist.add(s);
                }
            }
            playStreams = slist.toArray(new String[slist.size()]);
        }
        return playStreams;
    }

    public static void playStreamFilter(List<Stream> streams, Integer categoryId, CommonParam commonParam) {
        if (categoryId != null && categoryId.intValue() == VideoConstants.Category.FILM)
            return;

        Iterator<Stream> iterator = streams.iterator();
        while (iterator.hasNext()) {
            Stream stream = iterator.next();
            if (stream.getCode().contains("1080")) {
                iterator.remove();
            }
        }
    }

    /**
     * 判断是否是音乐片段类型
     * @param video
     * @return
     */
    public static boolean isMusicSegment(VideoMysqlTable video) {
        // Integer category = video.getCategory();
        // if ((category != null) && (VideoConstants.Category.MUSIC ==
        // category)) {
        // Integer videoType = video.getVideo_type();
        // if ((videoType != null) && ((videoType == 180005) || (videoType ==
        // 182202))) {
        // return true;
        // }
        // }
        return false;
    }

    public static boolean updatePlayMacWhiteList(Set<String> macWhiteList) {
        if (macWhiteList != null) {
            HashSet<String> newData = new HashSet<String>();
            newData.addAll(macWhiteList);
            NO_AREA_PLAY_RESTRICT_MAC_SET = newData;
            return true;
        }
        return false;
    }

    public static boolean macWhiteListIsEmpty() {
        boolean result = NO_AREA_PLAY_RESTRICT_MAC_SET == null || NO_AREA_PLAY_RESTRICT_MAC_SET.size() == 0;
        return result;
    }

    public static boolean isMacCanPlayNoArea(String mac) {
        boolean result = false;
        if (NO_AREA_PLAY_RESTRICT_MAC_SET != null) {
            result = NO_AREA_PLAY_RESTRICT_MAC_SET.contains(mac);
        }
        return result;
    }

    public static void clearMacWhiteList() {
        NO_AREA_PLAY_RESTRICT_MAC_SET = null;
    }

    public static String rebuildPlayUrl(String url, CommonParam commonParam) {
        String ret = url;

        if (null != commonParam) {
            Integer broadcastId = commonParam.getBroadcastId();
            if ((broadcastId != null) /*
                                       * && (CommonConstants.CIBN ==
                                       * broadcastId)
                                       */) {
                // if (StringUtil.isNotBlank(commonParam.getMac())) {
                // ret += "&mac=" + commonParam.getMac();
                // }
                ret += "&bc=" + String.valueOf(broadcastId);
            }
            if (StringUtil.isNotBlank(commonParam.getMac())) {
                ret += "&mac=" + commonParam.getMac();
                ret += "&devid=" + commonParam.getMac();
            }
            if (TerminalUtil.supportAntiReport(commonParam)) {
                ret += "&sign_dev=" + commonParam.getDevSign();
            }
        }

        return ret;
    }

    public static void rebuildVideoDto(AlbumMysqlTable albumMysqlTable, VideoMysqlTable videoMysqlTable,
            CommonParam commonParam, VideoDto videoPlay, Map<String, SubscribeInfoV2> subscribeInfoV2Map) {
        // 童话侠项目: 特殊处理广告会员标识
        String pay_platform = null;
        if (null != albumMysqlTable) {
            pay_platform = albumMysqlTable.getPay_platform();
        } else {
            if (null != videoMysqlTable) {
                pay_platform = videoMysqlTable.getPay_platform();
            }
        }

        Boolean switcher = ApplicationUtils.getBoolean(ApplicationConstants.IPTV_USER_LEPAY_ISTEST);
        boolean isTest = (null != switcher && switcher.booleanValue());

        // ========================增设家庭会员影片类型(B)============================= //
        if (null != videoMysqlTable) {
            List<ChargeInfoDto> chargeInfoDtos = JumpUtil
                    .genChargeInfos(videoMysqlTable.getPay_platform(), commonParam);
            if (null != chargeInfoDtos && chargeInfoDtos.size() > 0) {
                Iterator<ChargeInfoDto> chargeInfoDtoIterator = chargeInfoDtos.iterator();
                ChargeInfoDto chargeInfoDto = null;
                Integer devType = null;
                while (chargeInfoDtoIterator.hasNext()) {
                    chargeInfoDto = chargeInfoDtoIterator.next();
                    devType = MmsDataUtil.getDevType(chargeInfoDto.getPlatForm());
                    if (null != chargeInfoDto && null != commonParam.getP_devType() && null != devType
                            && devType.intValue() == commonParam.getP_devType().intValue()) {
                        continue;
                    } else {
                        chargeInfoDtoIterator.remove();
                    }
                }
                videoPlay.setChargeInfos(chargeInfoDtos);
            }
        }
        // ========================增设家庭会员影片类型(E)============================= //

        if (/*
             * (StringUtil.isNotBlank(albumMysqlTable.getSource_id()) &&
             * albumMysqlTable.getSource_id().contains("200037")) ||
             * (StringUtil.isNotBlank(videoMysqlTable.getSource_id()) &&
             * videoMysqlTable.getSource_id().contains("200037"))
             */
        ChargeInfo.isHomeVip(MmsDataUtil.getChargeTypeFromPlatform(pay_platform, commonParam.getP_devType()))) {
            if (videoPlay.getPlayType() == ChargeTypeConstants.chargePolicy.FREE) { // 免费
                boolean vipIsCibn = false;
                boolean vipIsHome = false;
                if (null != subscribeInfoV2Map) {
                    for (Entry<String, SubscribeInfoV2> entry : subscribeInfoV2Map.entrySet()) {
                        if (VipTpConstant.LEPAY_VIP_TYPE.getVipByVendor(isTest, entry.getValue().getVendorNo())
                                .getType() == VipTpConstant.LEPAY_VIP_TYPE.LE_TV.getType()) {
                            vipIsCibn = !entry.getValue().getIsExpire();
                        } else if (VipTpConstant.LEPAY_VIP_TYPE.getVipByVendor(isTest, entry.getValue().getVendorNo())
                                .getType() == VipTpConstant.LEPAY_VIP_TYPE.LE_HOME.getType()) {
                            vipIsHome = !entry.getValue().getIsExpire();
                        }
                    }
                }

                if (null != videoPlay.getVip() && videoPlay.getVip().intValue() == 1 && !vipIsHome) {
                    videoPlay.setIsAdVip(0); // 超影会员
                } else if (vipIsHome) {
                    videoPlay.setIsAdVip(1); // 家庭会员
                } else {
                    videoPlay.setIsAdVip(0);
                }
            } else { // 非会员
                videoPlay.setIsAdVip(0);
            }
        } else {
            // 处理特定case：童话镇来源的视频 & 非家庭会员 & 超级影视会员＊免费 ＝》 有广告
            // if ((StringUtil.isNotBlank(albumMysqlTable.getSource_id()) &&
            // albumMysqlTable.getSource_id().contains("200037")) ||
            // (StringUtil.isNotBlank(videoMysqlTable.getSource_id()) &&
            // videoMysqlTable.getSource_id().contains("200037"))) {
            // if (videoPlay.getPlayType() ==
            // ChargeTypeConstants.chargePolicy.FREE) {
            // if (null != videoPlay.getVip() && videoPlay.getVip().intValue()
            // == 1) {
            // videoPlay.setIsAdVip(0);
            // } else {
            // videoPlay.setIsAdVip(videoPlay.getVip());
            // }
            // } else {
            // videoPlay.setIsAdVip(videoPlay.getVip());
            // }
            // } else {
            // videoPlay.setIsAdVip(videoPlay.getVip());
            // }
            videoPlay.setIsAdVip(videoPlay.getVip());
        }

        // 非会员可用所有码率(超清/高清/流畅)观看童话侠内容；
        if (null != videoPlay.getVip() && videoPlay.getVip().intValue() == 0) {
            String sourceid = null;
            if (null != albumMysqlTable && StringUtil.isNotBlank(albumMysqlTable.getSource_id())) {
                sourceid = albumMysqlTable.getSource_id();
            } else if (null != videoMysqlTable && StringUtil.isNotBlank(videoMysqlTable.getSource_id())) {
                sourceid = videoMysqlTable.getSource_id();
            }

            String sourceids = ApplicationUtils.get(ApplicationConstants.VRS_ALLSTREAMS_SOURCEIDS);
            if (StringUtil.isNotBlank(sourceids)) {
                Set<String> ids = StringUtil.getStringToSet(sourceids, ",");
                if (null != sourceid && null != ids && ids.contains(sourceid)
                        && videoPlay.getPlayType() == ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM) {
                    videoPlay.setPlayType(ChargeTypeConstants.chargePolicy.FREE);
                }
            }
        }
    }
}
