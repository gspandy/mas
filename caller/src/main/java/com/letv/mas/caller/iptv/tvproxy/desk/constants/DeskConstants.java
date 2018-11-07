package com.letv.mas.caller.iptv.tvproxy.desk.constants;

public class DeskConstants {

    /**
     * 儿童桌面父母中心下页面type值
     */
    public static final int PARENTCENTER_TYPE_PLAYLIST = 1;// 父母播单
    public static final int PARENTCENTER_TYPE_TIMECONTROL = 2;// 时间控制
    public static final int PARENTCENTER_TYPE_USERINFO = 3;// 用户信息
    public static final int PARENTCENTER_TYPE_TOVIP = 4;// 升级会员
    public static final int PARENTCENTER_TYPE_GETMOVIE = 5;// 挑选动画

    /**
     * 桌面模式,0点播桌面，1儿童桌面，2音乐桌面
     */
    public static final int DESK_MODEL_PUBLIC = 0;// 点播桌面模式
    public static final int DESK_MODEL_CHILDERN = 1;// 儿童桌面模式
    public static final int DESK_MODEL_MUSIC = 2;// 音乐桌面模式

    /**
     * 打洞来源，1桌面 2 乐搜 3 外部推送 0 第三方来源through
     */
    public static final int THROUGH_RESOURCE_THIRD = 0;
    public static final int THROUGH_RESOURCE_DESK = 1;
    public static final int THROUGH_RESOURCE_LESERACH = 2;
    public static final int THROUGH_RESOURCE_EXTERNAL = 3;
    /*
     * 广播类型参数 例如：com.letv.external.new
     */
    public static final String DESK_ACTION = "com.letv.external.new";
}
