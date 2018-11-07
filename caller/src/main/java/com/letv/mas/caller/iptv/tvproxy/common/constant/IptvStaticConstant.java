package com.letv.mas.caller.iptv.tvproxy.common.constant;

/**
 * TV版静态文件URL常量类
 * @author KevinYi
 */
public class IptvStaticConstant {

    /**
     * 直播部门直播大厅列表数据，目前仅简单配置，实现快速可配置化，格式如：LiveRoomChannel.ename:categoryId;
     * LiveRoomChannel.ename:categoryId;...;LiveRoomChannel.ename:categoryId
     */
    public static final String LIVE_ROOM_CHANNEL_CONTENT_URL = ApplicationConstants.I_STATIC_ITV_BASE_HOST
            + "/api/live/liveRoomChannel.txt";

    /**
     * hk mac white list static file url
     */
    public static final String HK_MAC_WHITE_LIST_URL = ApplicationConstants.I_STATIC_ITV_BASE_HOST
            + "/api/hkmaclist/hkmaclist.txt";

    /**
     * <<<<<<< HEAD
     * play mac white list static file base url; whole url is
     * {MAC_WHITE_LIST_BASE_URL} + file name
     */
    public static final String MAC_WHITE_LIST_BASE_URL = ApplicationConstants.I_STATIC_ITV_BASE_HOST
            + "/api/whiteList/mac/";

    public static final String MAC_BLACK_LIST_BASE_URL = ApplicationConstants.I_STATIC_ITV_BASE_HOST
            + "/api/conf/video/blacklist.txt";

    /**
     * =======
     * >>>>>>> 30e67316078e6e45e6baf3913458cdddf929c61c
     * user id white list static file url
     */
    public static final String USERID_WHITE_LIST_URL = ApplicationConstants.I_STATIC_ITV_BASE_HOST
            + "/api/whiteList/userIdList.txt";
}
