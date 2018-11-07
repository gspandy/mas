package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveTpConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LiveConstants {

    public static final String LIVE_LIVETIME_Y_YESTODAY = "LIVE.LIVETIME.Y_YESTODAY";// 前天
    public static final String LIVE_LIVETIME_YESTODAY = "LIVE.LIVETIME.YESTODAY";// 昨天
    public static final String LIVE_LIVETIME_T_TOMORROW = "LIVE.LIVETIME.T_TOMORROW";// 后天
    public static final String LIVE_LIVETIME_TOMORROW = "LIVE.LIVETIME.TOMORROW";// 明天
    public static final String LIVE_LIVETIME_TODAY = "LIVE.LIVETIME.TODAY";// 今天，即
    public static final String LIVE_ACTIVITY_DESCRIPTION = "LIVE.ACTIVITY.DESCRIPTION";// 活动描述

    /**
     * 直播大厅数据查询类型,1--直播大厅数据列表，2--单场(或若干场)直播
     */
    public static final String LIVE_LIST_QUERY_TYPE_LIVE_ROOM = "1";
    public static final String LIVE_LIST_QUERY_TYPE_MULTI = "2";

    public static final String LIVE_ROOM_SPLITID_KEY = "ROOM";
    public static final String LIVE_SDK_SPLITID_KEY = "SDK";

    public static final Map<String, String> SPLITID = new HashMap<String, String>();

    public static final String LIVE_LIST = "1";// 直播业务
    public static final String LIVE_PORJECT = "2";// 直播 专题

    /**
     * 刷新单场或若干场直播实时信息的时间间隔默认值，这段时间内的所有刷新请求只执行一次；该逻辑由客户端执行，服务端只返回时间值，单位毫秒，默认30000
     */
    public static final Long LIVE_LIST_REAL_TIME_DATA_REFRESH_INTERVAL_DEFAULT = 30000L;

    /**
     * 直播SDK渠道--CMS配置列表映射，key--bsChannel，value--页面id
     */
    public static final Map<String, String> LIVE_TP_CHANNEL_CODE_MAP = new HashMap<String, String>();

    /**
     * 直播缓存数据操作明令，1--直播大厅数据过滤
     */
    public static final int LIVE_CACHE_DATA_MANAGE_OPERATE_COMMAND_LIVE_ROOM_FILTER = 1;

    public static final int LIVE_SPORT_NAME_SHOW_STYLE_0 = 0;// 体育直播无主队客队，直播名称为第三方title字段
    public static final int LIVE_SPORT_NAME_SHOW_STYLE_1 = 1; // 体育直播 ，直播名称为主队客队

    /**
     * TV端定义直播状态，1--预告，2--直播中，3--集锦（暂不使用），4--回看
     */
    public static final Integer PREVIEW = 1;
    public static final Integer LIVE = 2;
    public static final Integer JI_JIN = 3;
    public static final Integer HUI_KAN = 4;

    /**
     * 直播资源类型，1--普通直播，2--轮播，3--卫视台
     */
    public static final int LIVE_SOURCE_TYPE_LIVE = 1;
    public static final int LIVE_SOURCE_TYPE_LUNBO = 2;
    public static final int LIVE_SOURCE_TYPE_WEISHI = 3;

    public static Map<Integer, String> STATE_MAP = new HashMap<Integer, String>();
    public static Map<String, String> LIVE_TIME_MAP = new HashMap<String, String>();// 直播时间

    /**
     * 直播大厅中，各种类型（乐视自有）设备需要过滤掉的直播信息Map；
     * key--格式规则为{terminalSeries}:{broadcastId}:{splatId}；
     * value--直播id列表，多个直播id使用英文逗号分隔链接，但每个直播id必须满足",{liveId},"的格式，以帮助准确查找；
     * 2015-09-23，李宇春演唱会特殊需求，需要在不同设备上过滤数据；因为定时刷出来的直播大厅数据，仅考虑了播控方、splatId，
     * 而没有考虑设备信息，所以无法在缓存的直播大厅数据中修改，而是在最后返回时才筛选；过滤数据的配置放在配置文件中（静态文件服务器上），
     * 启动加载或手动刷新；
     */
    public static final Map<String, String> LIVE_ROOM_FILTER_DEVICE_LIVE_MAP = new HashMap<String, String>();

    /**
     * live中terminalApplication--splatid映射
     */
    private static final Map<String, String> LIVE_TERMINALAPPLICATION_SPLATID_MAP = new HashMap<String, String>();

    static {
        SPLITID.put("ROOM", "1007");// 直播大厅
        SPLITID.put("SDK", "1034");// 直播sdk
        SPLITID.put("PRE_LIVE", LiveTpConstants.LIVE_SPLITID_PRELIVE);// 超前点映

        LIVE_TERMINALAPPLICATION_SPLATID_MAP.put(TerminalCommonConstant.TERMINAL_APPLICATION_LIXIAOLU,
                LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
        LIVE_TERMINALAPPLICATION_SPLATID_MAP.put(TerminalCommonConstant.TERMINAL_APPLICATION_LETV,
                LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
        LIVE_TERMINALAPPLICATION_SPLATID_MAP.put(TerminalCommonConstant.TERMINAL_APPLICATION_LETV_COMMON,
                LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
        LIVE_TERMINALAPPLICATION_SPLATID_MAP.put(TerminalCommonConstant.TERMINAL_APPLICATION_LESO,
                LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
        LIVE_TERMINALAPPLICATION_SPLATID_MAP.put(TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI,
                LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
        LIVE_TERMINALAPPLICATION_SPLATID_MAP.put(TerminalCommonConstant.TERMINAL_APPLICATION_LETV_HK,
                LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
        LIVE_TERMINALAPPLICATION_SPLATID_MAP.put(TerminalCommonConstant.TERMINAL_APPLICATION_LETV_US,
                LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
        LIVE_TERMINALAPPLICATION_SPLATID_MAP.put(TerminalCommonConstant.TERMINAL_APPLICATION_LE,
                LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
    }

    static {
        LIVE_TP_CHANNEL_CODE_MAP.put("hisense", LiveTpConstants.LIVE_TP_CHANNEL_DEFULT_CODE);// 直播大厅
        LIVE_TP_CHANNEL_CODE_MAP.put("test", "1001631083");// 直播sdk
    }

    static {
        STATE_MAP.put(PREVIEW, "LIVE.STATE.PREVIEW");// 预告
        STATE_MAP.put(LIVE, "LIVE.STATE.LIVE");// Live
        STATE_MAP.put(JI_JIN, "LIVE.STATE.JIJIN");// 集锦
        STATE_MAP.put(HUI_KAN, "LIVE.STATE.HUIKAN");// 回看

        LIVE_TIME_MAP.put(
                TimeUtil.getDateString(TimeUtil.truncateDay(Calendar.getInstance()), TimeUtil.DATE_MM_DD_FORMAT1),
                LIVE_LIVETIME_TODAY);// 今天
        LIVE_TIME_MAP.put(TimeUtil.getDateString(TimeUtil.startOfDayTomorrow(), TimeUtil.DATE_MM_DD_FORMAT1),
                LIVE_LIVETIME_TOMORROW);// 明天
        Calendar ttomorrow = TimeUtil.startOfDayTomorrow();
        ttomorrow.add(Calendar.DAY_OF_MONTH, 1);
        LIVE_TIME_MAP.put(TimeUtil.getDateString(ttomorrow, TimeUtil.DATE_MM_DD_FORMAT1), LIVE_LIVETIME_T_TOMORROW);// 后天
        LIVE_TIME_MAP.put(TimeUtil.getDateString(TimeUtil.startOfDayYesterday(), TimeUtil.DATE_MM_DD_FORMAT1),
                LIVE_LIVETIME_YESTODAY);// 昨天
        LIVE_TIME_MAP.put(TimeUtil.getDateString(TimeUtil.backOneDay(TimeUtil.startOfDayYesterday()),
                TimeUtil.DATE_MM_DD_FORMAT1), LIVE_LIVETIME_Y_YESTODAY);// 前天
    }

    /**
     * 弹幕开关
     */
    public static final Boolean CHATROOM_SWITCH = ApplicationUtils
            .getBoolean(ApplicationConstants.IPTV_LIVE_CHAT_ROOM_SWITCH);

    /**
     * @param terminalApplication
     * @param defaultSplatId
     * @return
     */
    public static String getLiveSplatIdByTerminalApplication(String terminalApplication, String defaultSplatId) {
        String splatId = LIVE_TERMINALAPPLICATION_SPLATID_MAP.get(terminalApplication);
        return splatId != null ? splatId : defaultSplatId;
    }
}
