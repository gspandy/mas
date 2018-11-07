package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;

/**
 * 数据
 */
public class DataConstant {

    /*
     * 数据类型
     */
    public final static int DATA_TYPE_BLANK = 0;// 无意义的数据，特殊场景下使用，表示无业务意义
    public final static int DATA_TYPE_ALBUM = 1;// 专辑
    public final static int DATA_TYPE_VIDEO = 2;// 视频
    public final static int DATA_TYPE_SUBJECT = 3;// 专题
    public final static int DATA_TYPE_LIVE = 4;// 直播
    public final static int DATA_TYPE_TVSTATION = 5;// 乐视台（轮播台)
    public final static int DATA_TYPE_CHANNEL = 6;// 频道
    public final static int DATA_TYPE_SEARCH = 7;// 搜索
    public final static int DATA_TYPE_RETRIEVE = 8;// 检索
    public final static int DATA_TYPE_BROWSER = 9;// 外跳浏览器
    public final static int DATA_TYPE_LIST = 10;// 数据列表
    public final static int DATA_TYPE_LIST_RECOMMENDATION = 11;// 数据列表：猜你喜欢
    public final static int DATA_TYPE_LIST_RANK = 13;// 数据列表：排行
    public final static int DATA_TYPE_MULTILIST = 14;// 多板块数据列表
    public final static int DATA_TYPE_MULTILIST_RECOMMENDATION = 12;// 多板块数据列表：猜你喜欢
    public final static int DATA_TYPE_PAGE = 15;// 定制页面
    public final static int DATA_TYPE_EXTENSION = 16;// 扩展程序
    public final static int DATA_TYPE_MALE = 17;// 男人模式－－没用到
    public final static int DATA_TYPE_FAMLE = 18;// 女人模式－－没用到
    public final static int DATA_TYPE_CHILDREN = 19;// 儿童模式
    public final static int DATA_TYPE_MUSIC = 20;// 音乐台
    public final static int DATA_TYPE_CHECKSTAND = 21;// 收银台
    public final static int DATA_TYPE_PRELIVE = 22;// 超前影院
    public final static int DATA_TYPE_TOPIC_ACTIVITY = 23;// 919主题活动，一般走单板块
    public final static int DATA_TYPE_PLAY_CENTER = 26;// 游戏中心
    public final static int DATA_TYPE_STAR = 27;// 明星数据跳转明星详情
    public final static int DATA_TYPE_CONTAINER = 28;// 正片推广容器--芈月传
    public final static int DATA_TYPE_VIDEO_SALE = 29;// 视频营销数据类型
    public final static int DATA_TYPE_MESSAGE_PUSH = 30;// push消息推送
    public final static int DATA_TYPE_DOWN_NAVIGATION = 31;// 下导航频道类型

    public final static int DATA_TYPE_PURCHASE_MEMBERSHIP = 32;// 购买会员
    public final static int DATA_TYPE_PLAY_SETTINGS = 33;// 播放设置
    public final static int DATA_TYPE_HISTORY = 34;// 播放记录
    public final static int DATA_TYPE_NET_SPEED_TEST = 35;// 网络测速
    public final static int DATA_TYPE_WATCHLIST = 36;// 追剧收藏
    public final static int DATA_TYPE_CONTACT_US = 37;// 联系我们
    public final static int DATA_TYPE_ABOUT_US = 38;// 关于我们
    public final static int DATA_TYPE_RECEIVE_MEMBERSHIP = 39;// 领取会员

    public final static int DATA_TYPE_SUBJECT_FAV = 40;// 专题收藏
    public final static int DATA_TYPE_EXT_APP = 41;// 外部应用跳转
    public final static int DATA_TYPE_LEGUIDE = 42;// 乐导视
    public final static int DATA_TYPE_SUBJECT_LIST = 43;// 专题列表
    public final static int DATA_TYPE_RANK_LIST = 44;// 排行榜列表
    public final static int DATA_TYPE_CHANNEL_LIST = 45;// 频道列表
    public final static int DATA_TYPE_LIVE_LIST = 46;// 直播大厅
    public final static int DATA_TYPE_INDEX_LIST = 47;// TV版首页
    public final static int DATA_TYPE_MINE_LIST = 48;// TV版我的
    public final static int DATA_TYPE_VIP_LIST = 49;// tv版会员
    public final static int DATA_TYPE_DISCOVER_LIST = 50;// TV版发现
    public final static int DATA_TYPE_APP_LIST = 51;// applist
    public final static int DATA_TYPE_FREE_VIP_ACTIVE = 52;// 开通会员白条
    public final static int DATA_TYPE_FREE_VIP_PAYBACK = 53;// 会员白条还款
    public final static int DATA_TYPE_LEPARTER = 54;// leparter
    public final static int DATA_TYPE_SOARING_LIST = 61;// 飙升榜列表
    public final static int DATA_TYPE_WF_SUBJECT = 62;// 瀑布流专题
    public final static int DATA_TYPE_UNKOWN = Integer.MAX_VALUE;// 未知类型！

    /**
     * levidi type类型定义
     */
    public final static int DATA_TYPE_CATEGORY_LIST = 10000;// category分类
    public final static int DATA_TYPE_SETTINGS_LIST = 10001;// Settings navbar
    public final static int DATA_TYPE_HOME_LIST = 10002;// Home navbar
    public final static int DATA_TYPE_PUBLISHER_LIST = 10003;// Publisher navbar
    public final static int DATA_TYPE_CONTENT_LIST = 10004;// 内容列表
    public final static int DATA_TYPE_SUBSCRIPTION = 10005;// 订阅
    /**
     * 儿童桌面打洞数据类型，已有数据类型使用TV版统一的，下边为新增跳转
     */
    public final static int DATA_TYPE_CHILD_PLAYLOG = 101;// 播放记录
    public final static int DATA_TYPE_CHILD_PARENTCONTER = 102;// 父母中心
    public final static int DATA_TYPE_CHILD_CARTON = 103;// 卡通形象
    public final static int DATA_TYPE_CHILD_COURSE = 104;// 课程表
    public final static int DATA_TYPE_CHILD_LABLE = 105;// 标签分类检索页
    public final static int DATA_TYPE_CHILD_HOME = 106;// 儿童首页
    public final static int DATA_TYPE_CHILD_VIDEOLABEL = 107;// 儿童播放检索

    /**
     * PC播放地址
     */
    public final static String PC_PLAY_URL = ApplicationConstants.WEBSITE_WWW_LE_COM + "/ptv/vplay/";

    /** 页面跳转类型,0--无响应 */
    public final static int OPENTYPE_NOTHING = 0;// 无响应
    /** 页面跳转类型,1--直接跳收银台 */
    public final static int OPENTYPE_CHECKOUTER = 1;// 直接跳收银台
    /** 页面跳转类型,2--先跳H5再跳收银台 */
    public final static int OPENTYPE_H5_AND_CHECKOUT = 2;// 先跳H5再跳收银台
    /** 页面跳转类型,3--只跳H5 */
    public final static int OPENTYPE_H5 = 3;// 只跳H5
    /** 页面跳转类型,4--跳超前院线，定制业务 */
    public final static int OPENTYPE_PRELIVE = 4;// 跳超前院线，定制业务
    /** 页面跳转类型,5--跳919活动页 */
    public final static int OPENTYPE_919_ACTIVITY = 5;// 跳919活动页
    /** 页面跳转类型,6--跳视频营销 */
    public final static int OPENTYPE_VIDEO_SALE = 6;// 跳视频营销
    /** 活动类型，下浮层活动 */
    public final static int OPENTYPE_BOTTOM_ACTIVITY = 7;// 下浮层活动

    /** 1--打开外置浏览器 */
    public final static int BROWSER_TYPE_EXTERNAL = 1;// 打开外置浏览器
    /** 2--打开内置浏览器 */
    public final static int BROWSER_TYPE_BUILTIN = 2;// 打开内置浏览器
    /** 打开浏览器后的交互，0--不做响应，无交互 */
    public final static int BROWSER_OPENTYPE_NONE = 0;
    /** 打开浏览器后的交互，1--跳TV版收银台 */
    public final static int BROWSER_OPENTYPE_CHECKOUT = 1;
    /** 打开浏览器后的交互，2--跳频道页 */
    public final static int BROWSER_OPENTYPE_CHANNEL = 2;
    /** 打开浏览器后的交互，3--h5页面支持交互 */
    public final static int BROWSER_OPENTYPE_INTERACTIVE = 3;

    /**
     * 请求数据方式，1--先第三方接口，请求不到再请求缓存，2--先缓存，缓存请求不到则请求第三方接口
     */
    public final static int GET_DATA_CATEGARY_TP_CACHE = 1;
    public final static int GET_DATA_CATEGARY_CACHE_TP = 2;

}
