package com.letv.mas.caller.iptv.tvproxy.terminal.constant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 终端模块常量类
 * @author KevinYi
 */
public class TerminalApiConstant {

    /**
     * 支持4K设备系列更新配置；更新间隔为1小时
     */
    public final static long SUPPORT_4K_TERMINAL_SERIES_UPDATE_INTERVAL = 3600000L; // 4k设备列表更新间隔，单位-毫秒
    public static long SUPPORT_4K_TERMINAL_SERIES_LASTUPDATE_TIME = 0L; // 上次更新时间，单位-毫秒
    public final static Lock SUPPORT_4K_TERMINAL_SERIES_UPDATE_Lock = new ReentrantLock(); // 用于定时更新

    /**
     * 跳转体育APP添加业务，跳转方式，0--TV版播放，1--仅提示文案，2--跳转APP
     */
    public static final String SPORTS_JUMP_ACTION_TV = "0";
    public static final String SPORTS_JUMP_ACTION_TIPS = "1";
    public static final String SPORTS_JUMP_ACTION_APP = "2";

    /**
     * 体育app跳转配置文件中(map类型数据结构)key值分隔符，英文逗号
     */
    public static final String SPORTS_JUMP_CONFIG_KEY_SEPARATOR = ",";
}
