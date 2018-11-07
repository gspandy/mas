package com.letv.mas.caller.iptv.tvproxy.video.constants;

public class MobileErrorConstant {
    /**
     * 无版权
     */
    public static final String MOBILE_VIDEO_NO_COPYRIGHT = "0015";// 因版权方要求，暂不能播放
    /**
     * 用户受限判断失败-未告诉国家ip
     */
    public static final String MOBILE_VIDEO_USER_LIMIT_OR_UNHONW_IP = "0016";// 因版权方要求，暂不能观看

    /**
     * 防盗链url调度
     */
    public static final String MOBILE_VIDEO_GET_PLAYURL_FAILURE = "0204";// 因版权方要求，暂不能观看

    /**
     * 大陆地区IP受限
     */
    public static final String MOBILE_VIDEO_CN_IP_LIMIT = "0012";// 因政策原因，该内容无法提供观看

    /**
     * 文案：因版權方要求，此視頻僅支持在中國大陸地區觀看
     * 詳情請致電樂視(香港)客戶服務熱線：3128 9333
     * 或電郵至 customercare.hk@letv.com
     */
    public static final String MOBILE_VIDEO_HK_IP_LIMIT = "0037";// 海外IP受限 IP为香港

    /**
     * 因版权方要求，此视频仅支持在中国大陆地区观看
     * 详情请致电乐视网客户服务热线：
     * （+86）10109000 /（+86）010-6219 7841
     */
    public static final String MOBILE_VIDEO_HK_OVERSEA_IP_LIMIT = "0008";//

    /**
     * 视频无法播放 提交反馈
     * 刷新重试按钮
     */
    public static final String MOBILE_VIDEO_GET_DATA_FAILURE = "0017";// 获取地址/数据失败
    public static final String MOBILE_VIDEO_HOTSPOT_CHANNEL_NEED_PAY = "0029";// 热点频道如果需要付费试看
    public static final String MOBILE_VIDEO_SUBJECTID_EMPTY = "1502";// 播放专题时，但取到的专题zid是空的
    public static final String MOBILE_VIDEO_NO_VIDEOID = "1504";// 播放正常视频取不到vid
    public static final String MOBILE_VIDEO_NECESSARY_PARAMETER_MISSING = "1505";// 缺少播放的必要的参数
    public static final String MOBILE_VIDEO_DATA_MODEL_NULL = "1506";// 从接口取到的播放的videomodel为空
    public static final String MOBILE_VIDEO_MISSING_PARAMETER = "1507";// 缺少请求videofile接口的必要参数
    public static final String MOBILE_VIDEO_DOWNLOAD_FAILURE = "0018";// 下载视频文件失败
    public static final String MOBILE_VIDEO_PARSE_PLAYURL_FAILURE = "0019";// 解析url失败
    public static final String MOBILE_VIDEO_PLAY_FAILURE_REPEAT_URL_EMPTY = "1500";// 点播url播放失败，重试时发现获取调度地址为空
    public static final String MOBILE_VIDEO_PLAY_FAILURE_REPEAT_VID_EMPTY = "1501";// 点播url播放失败，重试时发现获取vid为空
    public static final String MOBILE_VIDEO_STREAM_CAN_NOT_PLAY = "0407";// 当前码流不能正确播放
    public static final String MOBILE_VIDEO_PLAY_FAILURE = "0020";// （本地视频）视频播放失败
    public static final String MOBILE_VIDEO_VIDEO_FILE_ERROR = "0036";// 视频文件接口错误
}
