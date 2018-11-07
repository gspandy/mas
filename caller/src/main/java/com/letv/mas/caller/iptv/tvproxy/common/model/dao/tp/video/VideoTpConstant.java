package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 视频相关第三方调用接口常量
 * @author liudaojie
 */
public final class VideoTpConstant {

    private final static Logger log = LoggerFactory.getLogger(VideoTpConstant.class);

    /**
     * 领先版点播签名校验key值
     */
    public static final String LETV_LEADING_MOBILE_LEAD_MD5_KEY = "RzOnIYtf2yRcc$Bf";

    /**
     * 领先版播放鉴权中，涉及路由鉴权中，header中的路由id key值
     */
    public static final String LETV_LEADING_REQUEST_HEADER_REOUTER_ID_KEY = "router-id";

    /**
     * 领先版剧集列表分页大小，固定100
     */
    public static final int LETV_LEADING_ALBUM_SERIES_PAGE_SIZE = 100;

    /**
     * 国广版本第三方合作bsChannel的参数传值
     */
    public static final String CIBN_BSCHANNEL_OPERATORS = "operators";

    public static final String PATH = "/letv/itv-timer-job/data_tmp";
    public static final String RMI_SERVER_ADDR = ApplicationUtils
            .get(ApplicationConstants.IPTV_STATIC_SERVER_ADDR_PARAM);
    public static final int RMI_SERVER_PORT = 21099;
    public static final String RMI_SERVER_NAME = "fileupload";

    /**
     * 专辑下视频列表分页大小，默认50
     */
    public static final int ALBUM_SERIES_PAGE_SIZE = 50;

    public static final String QUERY_TYPE_NON_POSITIVE = "0";// 查询非正片视频
    public static final String QUERY_TYPE_POSITIVE = "1";// 查询正片视频
    public static final String QUERY_TYPE_ALL = "2";// 查询全部视频

    /**
     * tv video play cde splatid;
     * 500--老盒子；501--超级电视；502--通用版第三方；503--自有版盒子；504--海外版；505--乐看搜索；506--在线影院；
     * 507--电视购物；508--国广后台审核；509--点播TV版投屏，由领先版下发；510--乐视儿童--小鱼版；511--TV版HTML5播放；
     * 512--乐视视频三方通用版本（TV/盒子）；513--电视TV ；529--超级电视NEW ；530--乐视盒子NEW ；531--乐视TV儿童
     * ；532--乐视盒子儿童
     * Le--美国版；514--电视TV版--香港版;601--PC，这里用来支持国广版本第三方合作播放
     */
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_BOX_OLD = 500;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV = 501;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_COMMON = 502;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_BOX = 503;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_OVERSEA = 504;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LESO = 505;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_VIDEO_ONLINE = 506;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_SHOPPING = 507;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_CIBN_CHECK = 508;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_TOUPING = 509;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_FISH = 510;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_H5 = 511;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_COMMON = 512;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_LECOM_US = 513;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_HK = 514;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD = 521;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_BOX_NEW = 529;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_NEW = 530;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_TV = 531;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_BOX = 532;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_CIBN_OPERATORS_PC = 601;

    /**
     * 从媒资获取播放调度地址
     */
    public static final String VIDEO_VRS_MMSVIDEO_URL = ApplicationConstants.VRS_I_API_BASE_HOST + "/geturl";

    /**
     * 付费频道获取token即加速接口
     */
    public static final String VIDEO_PAY_CHANNEL_USER_AUTH_GET = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/getService.ldo";

    /**
     * 获取明星基本信息
     */
    public static final String VIDEO_VRS_MMS_STAR_GET = ApplicationConstants.VRS_I_API_BASE_HOST
            + "/mms/inner/star/v2/get";

    /**
     * 获得视频播放地址/新版防盗链接口
     */
    public static final String TP_VIDEO_GET_URL = ApplicationUtils.get(ApplicationConstants.VRS_MMS_PLAY_GET_URL);

    /**
     * 获取专辑信息url
     */
    public static final String TP_ALBUM_INFO_GET = ApplicationConstants.VRS_I_API_BASE_HOST
            + "/mms/inner/albumInfo/get?";

    public static final String TP_VIDEO_INFO_GET = ApplicationConstants.VRS_I_API_BASE_HOST + "/mms/inner/video/get?";

    public static final String VIDEO_LIST_ALL = ApplicationConstants.VRS_I_API_BASE_HOST
            + "/mms/inner/albumInfo/getAllVideoList?";

    public static final String VIDEO_LIST_POSITIVE = ApplicationConstants.VRS_I_API_BASE_HOST
            + "/mms/inner/albumInfo/getVideoList?";
    public static final String VIDEO_LIST_NON_POSITIVE = ApplicationConstants.VRS_I_API_BASE_HOST
            + "/mms/inner/albumInfo/getOtherVideoList?";

    public static final String WORKS_VIDEO_LIST_ALL = ApplicationConstants.WORKS_I_API_BASE_HOST
            + "/works/inner/material/videoList?";
    public static final String WORKS_ALBUM_INFO_GET = ApplicationConstants.WORKS_I_API_BASE_HOST
            + "/works/inner/material/albumDetailById?";
    /**
     * 获取boss配置的非会员引导信息
     */
    public static final String VIDEO_NONMENBER_GUIDE_LIST_GET = ApplicationConstants.BOSS_PRICE_ZHIFU_BASE_HOST
            + "/tv_nonmember_guide/get_list";

    /**
     * 播放扫码活动url
     */
    public static final String VIDEO_REACTION_URL = ApplicationConstants.HD_MY_LETV_BASE_HOST + "/action/video";

    /**
     * 获取扫码互动后台配置了互动信息的专辑或者视频URL
     */
    public static final String VIDEO_REACTION_CONFIG_INFO_URL = ApplicationConstants.HD_MY_LETV_BASE_HOST
            + "/votestatic/config_3.json";

    /**
     * 配置未登录查询单片价格
     */
    public static final String VIP_ALBUM_CHARGE_INFO_GET = ApplicationConstants.BOSS_PRICE_ZHIFU_BASE_HOST
            + "/pinfo/get/price";

    /**
     * 获取付费频道URL
     */
    public static final String VIDEO_PAY_CHANNEL_GET = ApplicationConstants.BOSS_PRICE_ZHIFU_BASE_HOST
            + "/movie_channel/get_pay_list";

    /**
     * 播放VIP鉴权接口
     * termid 终端ID 0：未知 1：PC端 2:移动端 3：盒端 4：电视(超级电视/海信电视)
     */
    public static final String VIDEO_PLAY_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/getService.ldo";

    /**
     * 领先版点播播放接口
     */
    public static final String LETV_LEADING_GET_PLAY_INFO_URL = ApplicationConstants.D_ITV_LETV_BASE_HOST
            + "/mobile/video/play/get.json?uid={uid}&terminalApplication={tvapp}&videoid={videoid}&mac={mac}&bsChannel=eui_letv&devKey={devKey}&sKey=&appVersion=&terminalBrand=letv&stream={stream}&terminalSeries={terminalSeries}&pcode=&wcode={wcode}&langcode={langcode}&devId={devId}&sig={sig}";

    /**
     * 领先版专辑详情页
     */
    public static final String LETV_LEADING_ALBUN_SERIES_URL = ApplicationConstants.S_ITV_LETV_BASE_HOST
            + "/s/d/mobile/video/albumInfo/get.json?act=1&page={page}&albumid={albumid}";

    /**
     * 领先版专辑详情页动态接口
     */
    public static final String LETV_LEADING_ALBUN_SERIES_DYN_URL = ApplicationConstants.D_ITV_LETV_BASE_HOST
            + "/mobile/video/albumInfo/get.json?act=1&page={page}&albumid={albumid}&terminalApplication={tvapp}";

    /**
     * 媒资防盗链接口返回的状态码， 1001：成功；1002：必填参数为空；1003参数不合法；1004：密钥不合法；参见MmsStore
     */
    public static final String VIDEO_PLAY_MMS_DATA_CODE_SUCCESS = "1001";

    /**
     * boss播放鉴权接口中，定义的TV端对应终端编号
     */
    public static final Integer VIDEO_PLAY_CHECK_AUTH_TERMINAL_TYPE = 9;

    /**
     * boss播放鉴权接口返回的鉴权结果状态码，1--播放服务已开通，0--播放服务未开通
     */
    public static final Integer VIDEO_PLAY_SERVICE_OPEN = 1;
    public static final Integer VIDEO_PLAY_SERVICE_UNOPEN = 0;

    /**
     * 媒资专辑剧集排序规则，-1--正序，1--倒序
     */
    public static final int VIDEO_MMS_SERIES_ORDER_ASC = -1;
    public static final int VIDEO_MMS_SERIES_ORDER_DESC = 1;

    /**
     * 生成领先版播放签名校验key
     * @param videoId
     * @param albumId
     * @param timestamp
     * @param sig
     * @return
     */
    public static String getLetvLeadingPlaySig(Long videoId, Long albumId) {
        String validSig = null;
        String md5Str = "";

        if (videoId != null) {
            md5Str = md5Str + videoId;
        }
        if (albumId != null) {
            md5Str = md5Str + albumId;
        }
        try {
            validSig = MessageDigestUtil.md5((md5Str + LETV_LEADING_MOBILE_LEAD_MD5_KEY).getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("getLetvLeadingPlaySig:" + e.getMessage(), e);
        }

        return validSig;
    }
}
