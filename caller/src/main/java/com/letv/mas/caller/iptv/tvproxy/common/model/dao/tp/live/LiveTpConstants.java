package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

/**
 * 直播业务第三方接口相关参数，如第三方接口URL，第三方定义参数
 * @author KevinYi
 */
public class LiveTpConstants {

    /**
     * 直播大厅分类id，3--娱乐，4--体育，9--音乐
     */
    public static final int LIVE_ROOM_CATEGORY_ID_ENTERTAINMENT = 3;
    public static final int LIVE_ROOM_CATEGORY_ID_SPORT = 4;
    public static final int LIVE_ROOM_CATEGORY_ID_MUSIC = 9;
    public static final int LIVE_ROOM_CATEGORY_ID_FINANCE = 22;

    /**
     * 直播业务平台编号，1007--TV直播（包含直播大厅和直播专题），1034--直播SDK，1050825005--超前院线，1060113002-
     * 乐视视频直播投屏 ， 1061017002 通用版
     */
    public static final String LIVE_SPLITID_TV_LIVE_ROOM = "1007";
    public static final String LIVE_SPLITID_SDK = "1034";
    public static final String LIVE_SPLITID_PRELIVE = "1050825005";
    public static final String LIVE_SPLITID_TOUPING = "1060113002";
    public static final String LIVE_SPLATID_COMMON = "1061017002";

    /**
     * 直播大厅数据直播状态， 1--未开始，2--直播中，3--回看，4--已结束
     */
    public static final int LIVE_STATUS_NOTSTART = 1;
    public static final int LIVE_STATUS_START = 2;
    public static final int LIVE_STATUS_REPLAY = 3;
    public static final int LIVE_STATUS_END = 4;

    /**
     * 直播SDK默认CMS页面id，目前为海信SDK使用
     */
    public static final String LIVE_TP_CHANNEL_DEFULT_CODE = "1002888538";

    /**
     * 直播大厅分类名，目前TV对接了体育--sports，财经--finance，资讯--information，娱乐--entertainment，
     * 游戏--game，音乐--music，品牌--brand，其他--other；其中sports展示逻辑有特殊需求，这里单独标记
     */
    public static final String LIVE_ROOM_CHANNEL_SPORT = "sports";

    /**
     * 直播回看文案配置key，有回看开始和结束时间，展示“{回看开始}--{结束时间}”；无回看开始时间但有回看结束时间，展示“截止至{结束时间}”，
     * 无回看结束时间，展示“当前可回看”
     */
    public static final String LIVE_REPLAY_NO_END_TIME = "LIVE.REPLAY.NOT.END.TIME"; // 无回看结束时间，展示“当前可回看”
    public static final String LIVE_REPLAY_NO_START_END_TIME = "LIVE.REPLAY.NO.START.END.TIME"; // 无回看开始时间但有回看结束时间，展示“截止至{结束时间}”
    public static final String LIVE_REPLAY_START_END_TIME = "LIVE.REPLAY.START.END.TIME"; // 有回看开始和结束时间，展示“{回看开始}--{结束时间}”
    public static final String LIVE_REPLAY_UNSUPPORT = "LIVE.REPLAY.UNSUPPORT"; // 回看开始时间等于回看结束时间，展示“暂不支持回看”

    /**
     * 直播大厅--查询若干条直播信息URL
     * http://api.live.letv.com/v1/liveRoom/multi/{splatid}?ids={liveIds}
     */
    public static final String LIVE_ROOM_MULTI_LIVE_PROGRAM_GET_URL = ApplicationConstants.API_LIVE_LETV_BASE_HOST
            + "/v1/liveRoom/multi/";

    /**
     * 获取live直播首页数据
     * http://d.itv.letv.com/live/superlive/homepage.json?splatClient=tvlive&
     * region=cn&langcode=zh_CN&braodcastId=0;
     */
    public static final String LIVE_HOME_PAGE_URL = "http://d.itv.letv.com/live/superlive/homepage.json";

    /**
     * 获取live直播首页数据
     * http://d.itv.letv.com/live/superlive/peopleNum.json?channelid=&
     * channelEname=&channelType=
     */
    public static final String LIVE_PEOPLE_COUNT_GET_URL = "http://d.itv.letv.com/live/superlive/peopleNum.json";

    /**
     * 获取直播频道数据URL
     */
    public static final String LIVE_CHANNEL_BASE_URL = ApplicationConstants.API_LIVE_LETV_BASE_HOST
            + "/v1/channel/letv/";

    /**
     * 直播专题--用户鉴权URL
     */
    public static final String BOSS_YUANXIAN_PERMISION_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_PERMISION);

    /**
     * 直播大厅根据设备过滤数据配置文件Url，一般为静态文件地址
     */
    public static final String LIVE_ROOM_FILTER_LIVES_CONFIG_FILE_URL = ApplicationUtils
            .get(ApplicationConstants.LIVE_LIVEROOM_FILTER_LIVES_CONFIG_FILE_URL);

    /**
     * 弹幕开关
     */
    public static final Boolean CHATROOM_SWITCH = ApplicationUtils
            .getBoolean(ApplicationConstants.IPTV_LIVE_CHAT_ROOM_SWITCH);

}
