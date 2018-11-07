package com.letv.mas.caller.iptv.tvproxy.channel.constants;

import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.ChannelData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChannelConstants {

    /**
     * 1、专题模块业务码，2、超前影院模块，3、CMS板块模块
     */
    public static final int SUBJECT = 1;
    public static final int PRELIVE = 2;
    public static final int CMSBLOCK = 3;

    /**
     * 乐视儿童读取CMS板块信息，映射文件规则不同需要配置类型
     */
    public static final Map<String, String> STARTUP_PIC_URL_MAP = new HashMap<String, String>();// boot
                                                                                                // advertise
                                                                                                // pic
                                                                                                // url
                                                                                                // map
    public static final String CHILDERN_MAX_AGE_DEFULT = "7";
    public static final String CHILDERN_MIN_AGE_DEFULT = "1";
    public static final Integer[] CHANNEL_MATCH_BLOCK_AGE = { 994, 1085, 1100 };
    public static final Integer[] CHANNEL_MATCH_BLOCK_AGE_GENDER = { 991, 1083 };
    public static final Integer[] CHANNEL_MATCH_BLOCK_AGE_WEEK = { 992 };
    public static final List<Integer> CHANNEL_MATCH_BLOCK_AGE_LIST = Arrays.asList(CHANNEL_MATCH_BLOCK_AGE);
    public static final List<Integer> CHANNEL_MATCH_BLOCK_AGE_GENDER_LIST = Arrays
            .asList(CHANNEL_MATCH_BLOCK_AGE_GENDER);
    public static final List<Integer> CHANNEL_MATCH_BLOCK_AGE_WEEK_LIST = Arrays.asList(CHANNEL_MATCH_BLOCK_AGE_WEEK);

    public static Map<String, String> CHANNELID_CMS_PAGEID = new HashMap<String, String>();
    static {
        CHANNELID_CMS_PAGEID.put("0_cn", "1002981791");// 下导航
        CHANNELID_CMS_PAGEID.put("795_cn", "1002981792");// 频道墙
        CHANNELID_CMS_PAGEID.put("619_cn", "1002981799");// 电视剧
        CHANNELID_CMS_PAGEID.put("1101_cn", "1002981795");// 发现
    }

    /**
     * 特辑模块业务码
     */
    public static final int TJPACKAGE = 1;

    /**
     * 多语言ITV_MENU表名
     */
    public static final String ITV_MENU_TABLE = "ITV_MENU";

    /**
     * 排行榜语言表
     */
    public static final String ITV_CHARTS_TABLE = "ITV_CHARTS";

    /**
     * 多语言ITV_NENU name字段名称
     */
    public static final String ITV_MENU_NAME = "name";

    /**
     * 多语言ITV_CHANNELDATA_RECOMMENDATIONBLOCK表 title字段名称
     */
    public static final String ITV_CHANNELDATA_RECOMMENDATIONBLOCK_TITLE = "title";

    /**
     * 多语言ITV_CHANNELDATA_RECOMMENDATIONBLOCK表名
     */
    public static final String ITV_CHANNELDATA_RECOMMENDATIONBLOCK_TABLE = "ITV_CHANNELDATA_RECOMMENDATIONBLOCK";

    /**
     * 世界杯
     */
    public static final int WORLDCUP = 618;

    /**
     * 电视剧
     */
    public static final int TV = 619;

    /**
     * 电影
     */
    public static final int FILM = 631;

    /**
     * 动漫
     */
    public static final int CARTOON = 639;

    /**
     * 综艺
     */
    public static final int VARIETY = 647;

    /**
     * 娱乐
     */
    public static final int ENT = 660;

    /**
     * 音乐
     */
    public static final int MUSIC = 668;

    /**
     * 纪录片
     */
    public static final int DFILM = 675;

    /**
     * 亲子
     */
    public static final int PARENTING = 684;

    /**
     * 财经
     */
    public static final int CAI_JING = 692;

    /**
     * 风尚
     */
    public static final int FENG_SHANG = 700;

    /**
     * 汽车
     */
    public static final int CAR = 703;

    /**
     * 旅游
     */
    public static final int TRAVEL = 705;

    /**
     * 4K
     */
    public static final int FOUR_K = 707;

    /**
     * 3D
     */
    public static final int THREE_D = 709;

    /**
     * 杜比
     */
    public static final int DOLBY = 711;

    /**
     * 1080P
     */
    public static final int C1080P = 713;

    /**
     * 粤语
     */
    public static final int YUE_YU = 715;

    /**
     * 免费专区
     */
    public static final int MIAN_FEI_ZHUAN_QU = 717;

    /**
     * 体育
     */
    public static final int SPORTINFO = 719;

    /**
     * NBA
     */
    public static final int NBA = 732;

    /**
     * 频道
     */
    public static final int CHANNEL = 795;

    /**
     * 专题
     */
    public static final int TOPIC = 796;

    /**
     * LIVE
     */
    public static final int LIVE = 797;

    /**
     * 首页
     */
    public static final int INDEX = 798;

    /**
     * 我的
     */
    public static final int MY = 799;

    /**
     * 生活（HK）
     */
    public static final int SHENG_HUO = 870;

    /**
     * 扩展频道
     */
    public static final int EXT = 882;

    /**
     * DTS
     */
    public static final int DTS = 711;

    public static final int CHILD = 901;
    /**
     * 频道数据源
     */
    public final static int DATASOURCE_CMS = 1;// CMS
    public final static int DATASOURCE_SEARCH = 2;// 搜索
    public final static int DATASOURCE_RECOMMENDATION = 3;// 推荐
    public final static int DATASOURCE_CHARTS = 4; // 排行榜
    public final static int DATASOURCE_CMS_SERVER = 5; // 服务端映射&CMS
    public final static int DATASOURCE_CMS_CACHE = 6;// 缓存板块内容

    /**
     * 排行榜数据源
     */
    public final static int CHARTS_DATASOURCE_CMS = 1;// 排行榜读CMS
    public final static int CHARTS_DATASOURCE_DATABASE = 4; // 排行榜读本地数据库

    /*
     * 数据预加载类型
     */
    public final static int DATA_PROLOADTYPE_NONE = 1;// 不预加载
    public final static int DATA_PROLOADTYPE_PAGE1 = 2;// 第一页

    /**
     * 解析排行榜类型，1--预览，用于排行榜tab页时；2--详细数据里诶包，用于榜单详情页时
     */
    public final static int CHARTS_PARSE_CHARTS_TYPE_PREVIEW = 1;
    public final static int CHARTS_PARSE_CHARTS_TYPE_DETAIL = 2;

    /**
     * 排行榜类型，1--新片推荐榜，2--飙升榜，3--一周热播电视剧，4--一周热播电视剧，5--一周热播综艺，6--一周热播动漫
     */
    public final static int CHARTS_TYPE_FRESH_RECOMMENDATION = 1;// 新片推荐
    public final static int CHARTS_TYPE_SOARING = 2; // 飙升榜
    public final static int CHARTS_TYPE_FILM = 3; // 一周热播电影
    public final static int CHARTS_TYPE_TV = 4; // 一周热播电视剧
    public final static int CHARTS_TYPE_VARIETY = 5; // 一周热播综艺
    public final static int CHARTS_TYPE_CARTOON = 6; // 一周热播动漫
    public final static int CHARTS_TYPE_MUSIC_RANK = 7; // 音乐频道--排行榜上导航

    public static final String CHANNEL_ALBUM_ABOUT_ONLINE = "CHANNEL_ALBUM_ABOUT_ONLINE";// 正片即将上线
    public static final String CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES = "CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES";// 更新至{0}集
    public static final String CHANNEL_ALBUM_UPDATED_TO_NOWISSUE = "CHANNEL_ALBUM_UPDATED_TO_NOWISSUE";// 更新至{0}期
    public static final String CHANNEL_ALBUM_IS_END = "CHANNEL_ALBUM_IS_END";// {0}集全
    public static final String CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES_NORMAL = "CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES_NORMAL";// 更新至{0}集
    public static final String CHANNEL_ALBUM_UPDATED_TO_NOWISSUE_NORMAL = "CHANNEL_ALBUM_UPDATED_TO_NOWISSUE_NORMAL";// {0}期
    public static final String CHANNEL_UPDATE_TEXT = "CHANNEL_UPDATE_TEXT";// 更新
    public static final String CHANNEL_ALBUM_UPDATED_TO_WORD = "CHANNEL_ALBUM_UPDATED_TO_WORD";// 更新至{0}话
    public static final String CHANNEL_ALBUM_WORD_IS_END = "CHANNEL_ALBUM_WORD_IS_END";// {0}话全
    /**
     * 我的页面活动位默认图
     */
    public static final String CHANNEL_MINE_DEFAULT_IMG = "CHANNEL_MINE_DEFAULT_IMG";

    /**
     * 我的页面推荐位默认背景图
     */
    public static final String CHANNEL_MINE_RECOMMEND_DEFAULT_IMG = "CHANNEL_MINE_RECOMMEND_DEFAULT_IMG";

    /**
     * “我的”版块中子版块名称
     */
    public final static String CHANNEL_MINE_BLOCK_HUODONG = "huodong";// 活动版块
    public final static String CHANNEL_MINE_BLOCK_HUIYUAN = "huiyuan";// 会员版块

    /**
     * ui风格
     */
    public final static Integer UI_PLATE_TYPE_4 = 4;
    public final static Integer UI_PLATE_TYPE_5 = 5;

    public final static String LECOM_HOME_CATEGORY_BLOCKID = ApplicationUtils
            .get(ApplicationConstants.CMS_LECOM_HOME_CATEGORY_ID);
    public final static String LECOM_HOME_ADDON_BLOCKID = ApplicationUtils
            .get(ApplicationConstants.CMS_LECOM_HOME_ADDON_ID);

    /**
     * channel 类型
     */
    public final static Integer MOVIE_CHANNEL_TYPE = 1;

    /**
     * 视频营销数据-CMS子版块相关配置参数
     */
    public final static String VIDEO_SALE_PAGE_TITLE = "title";// 标题页面字段key
    public final static String VIDEO_SALE_PAGE_BUTTON = "button";// 按钮页面字段key
    public final static String VIDEO_SALE_PAGE_URL = "url";// 二维码页面字段key

    /**
     * 视频营销数据-CMS基础数据相关配置参数
     */
    public final static String VIDEO_SALE_BASEDATA_BUTTON_BUY = "buttonBuy";// 购买按钮文案字段key
    public final static String VIDEO_SALE_BASEDATA_BUTTON_GIFT = "buttonGift";// 领取按钮文案字段key
    public final static String VIDEO_SALE_BASEDATA_IMG = "img";// 二维码图片字段key
    public final static String VIDEO_SALE_BASEDATA_URL = "url";// 二维码地址字段key
    public final static String VIDEO_SALE_BASEDATA_BACKGROUND = "background";// 背景图字段key
    public final static String VIDEO_SALE_BASEDATA_YEAR = "year";// 背景图字段key

    /**
     * 首页第8个位置要保证是视频营销数据，所以在静态文件profile.txt中配置了下列参数
     */
    public final static String INDEX_VIDEO_SALE_BLOCKID = "INDEX_VIDEO_SALE_BLOCKID";
    public final static String INDEX_VIDEO_SALE_IMG = "INDEX_VIDEO_SALE_IMG";
    public final static String INDEX_VIDEO_SALE_DATATYPE = "INDEX_VIDEO_SALE_DATATYPE";
    public final static String INDEX_VIDEO_SALE_NAME = "INDEX_VIDEO_SALE_NAME";
    public final static String INDEX_VIDEO_SALE_SUBNAME = "INDEX_VIDEO_SALE_SUBNAME";
    public final static String INDEX_VIDEO_SALE_URL = "INDEX_VIDEO_SALE_URL";
    public final static String INDEX_VIDEO_SALE_URL_TEXT = "INDEX_VIDEO_SALE_URL_TEXT";
    public final static String INDEX_VIDEO_SALE_URL_EXPIRE_TIME = "INDEX_VIDEO_SALE_URL_EXPIRE_TIME";
    public final static String INDEX_VIDEO_SALE_URL_TITLE_ICON = "INDEX_VIDEO_SALE_URL_TITLE_ICON";

    /**
     * 排行榜Tab页内，各榜单内数据vv修改系数，参见ChannelService.loadChartsData
     */
    public static float CHANNEL_CHARTS_TYPE_SOARING_VV_COEFFICIENT = 1.0f;

    /**
     * 排行榜中适用的内容，目前仅支持电影、电视剧、动漫和综艺
     */
    private static final List<String> CHATRS_AVAILABLE_CATEGARY = new ArrayList<String>();

    /**
     * 视频营销配置文件
     */
    public static Map<String, String> VIDEO_SALE_CONFIG_MAP = null;
    /**
     * 视频营销配置文件
     */
    public static Map<String, String> CHANNEL_MATCH_PAGEID_MAP = null;
    public static String CHANNEL_MATCH_PAGEID_PRE = "CHANNEL.MATCH.PAGEID.";
    public static String CHILD_AQ_PAGEID_BY_AGE_PRE = "CHILD.AQ.PAGEID.BY.AGE.";

    static {
        CHATRS_AVAILABLE_CATEGARY.add(String.valueOf(VideoConstants.Category.FILM));
        CHATRS_AVAILABLE_CATEGARY.add(String.valueOf(VideoConstants.Category.TV));
        CHATRS_AVAILABLE_CATEGARY.add(String.valueOf(VideoConstants.Category.CARTOON));
        CHATRS_AVAILABLE_CATEGARY.add(String.valueOf(VideoConstants.Category.VARIETY));
    }

    /**
     * 判断专辑或视频分类是否适用排行榜
     * @param categary
     * @return
     */
    public static boolean isLegalChartsDataCategary(Integer categary) {
        return CHATRS_AVAILABLE_CATEGARY.contains(String.valueOf(categary));
    }

    /**
     * 色块色值，临时方案
     */
    public static final String BLOCK_COLOR_KEY = "BLOCK_COLOR_CBASE_KEY";
    public static final String BLOCK_COLOR = "#c52041,#f6922c,#7c3fab,#67bc4f";
    // 会员频道色块统一显示
    public static final String BLOCK_COLOR_VIP = "#66000000";
    public static final String PAGEID_BLOCK_COLOR_FROM_DB = "page_cms1003403722,page_cms1003371291";

    public static final int CHANNEL_UIPLATE_TYPE_BGIMG = 4;// 背景图片
    public static final int CHANNEL_UIPLATE_TYPE_ADIMG = 5;// 浮层广告
    public static final int CHANNEL_UIPLATE_TYPE_6 = 6;// 播放列表
    public static final int CHANNEL_UIPLATE_TYPE_FOCUSLIST = 7;// 焦点运营列表
    public static final int CHANNEL_UIPLATE_TYPE_MODELLIST = 8;// 右滑屏容器
    public static final int CHANNEL_UIPLATE_TYPE_VIPABOUT = 9;// 会员相关推广模块
    /** 儿童桌面使用 **/
    public static final int CHANNEL_UI_TYPE_FUNCLIST = 10;// 功能入口列表
    public static final int CHANNEL_UI_TYPE_CARTONLIST = 11;// 卡通头像列表
    public static final int CHANNEL_UI_TYPE_COURSELIST = 12;// 课程分类列表
    public static final int CHANNEL_UI_TYPE_EXTENDLIST = 13;// 可扩展运营列表

    public static final String CHANNEL_TYPE_POSITION_PREFIX = "CHANNEL_TYPE_POSITION_";
    public static final String CHANNEL_TYPE_BACKGROUND_PRE = "CHANNEL_TYPE_BACKGROUND_";

    public static final int CHANNEL_COURSE_TYPE_OFF_CLASS = 0;// 课间操
    public static final int CHANNEL_COURSE_TYPE_ON_CLASS = 1;// 课程
    public static final int CHANNEL_COURSE_TYPE_INTEREST_CLASS = 2;// 兴趣班

    /** 新版频道接口封装判断type，从cms资料库移动样式维护 **/
    public static final int CHANNEL_UITYPE_RECURL = 241;// TV版色块推荐url
    public static final int CHANNEL_UITYPE_SUBNAV = 242;// TV版二级导航
    public static final int CHANNEL_UITYPE_HOME_1 = 243;// TV版频道一屏运营
    public static final int CHANNEL_UITYPE_HOME_2 = 246;// TV版频道二屏运营
    public static final int CHANNEL_UITYPE_WALL = 247;// TV版频道墙
    public static final int CHANNEL_UITYPE_BGPIC = 21;// 背景图样式

    public static final String CHANNEL_UITYPE_RECURL_FIXPRE = "http://api.itv.cp21.ott.cibntv.net/iptv/api/new/channel/data/recommendation/list.json?rcPageid=page_cms";
    public static String CHANNEL_WALL_PIC_BLOCKID = ApplicationUtils.get(ApplicationConstants.CHANNEL_WALL_PIC_BLOCKID);

    /**
     * 第三方应用编码
     */
    public static List<String> THIRD_PART_APP_CODE = new ArrayList<String>();
    static {
        THIRD_PART_APP_CODE.add("0");// 购物应用
        THIRD_PART_APP_CODE.add("1");// golive
        THIRD_PART_APP_CODE.add("2");// 应用商店 专题
        THIRD_PART_APP_CODE.add("3");// 游戏中心
        THIRD_PART_APP_CODE.add("4");// cibn
        THIRD_PART_APP_CODE.add("5");// wasu
    }

    /***************** levidi start ********************/

    /**
     * 印度版包含频道列表的上导航cid
     */
    public static final String CID_CONTAIN_CHANNEL_LIST = "tv_home";
    public static final String LEVIDI_CHANNELCODE_SETTING = "setting";
    public static final String CP_EROS_ID = "211_-7692985549721977186";

    /***************** 印度版end ********************/

    public static final String JUMP_PARAMS_GAMECONTER_RESOURCE = "4096";
    public static final Integer JUMP_PARAMS_GAMECONTER_TYPE = 11;
    public static final String JUMP_PARAMS_GAMECONTER_TYPESTRING = "APP";
    public static final Integer CHANNEL_IS_SUBSCRIBED = 1;
    public static final Integer CHANNEL_IS_UNSUBSCRIBED = 0;
    public static final Integer BROADCAST_ID = StringUtil.toInteger(
            ApplicationUtils.get(ApplicationConstants.IPTV_STATIC_BROADCAST_ID_PARAM), 0);
    public static final String CHANNEL_CODE_LEPARTER = "LePartner";
    public static final String CHANNEL_CODE_CATEGORY = "category";

    public static final String JUMP_PARAMS_GAMECONTER_ACTION = "com.letv.tvos.gamecenter.external.new";
    public static final String JUMP_PARAMS_GAMECONTER_PACKAGENAME = "com.letv.tvos.gamecenter";
    public static Boolean PAGEDATALIST_CACHE_SWITCH = ApplicationUtils.getBoolean(
            ApplicationConstants.IPTV_PAGEDATALIST_SWITCH_PARAM, false);

    /**
     * 2016-09-26美国lecom新增需求，首页首航推荐数据供桌面应用使用，这里直接缓存在内存中
     */
    public static Map<String, List<ChannelData>> CHANNEL_DATA_REC_MAP_4_LECOM_US = new HashMap<String, List<ChannelData>>();

    /**
     * 美国lecom首航推荐数据更新策略
     */
    public static long CHANNEL_DATA_REC_LIST_LE_UPDATE_INTERVAL_MILLIS = 5 * 60 * 1000L; // 5分钟
    public static long CHANNEL_DATA_REC_LIST_LE_LAST_UPDATE_TIME_MILLIS = 0L; // 上次更新时间
    public static final Lock CHANNEL_DATA_REC_LIST_LE_UPDATE_LOCK = new ReentrantLock(); // 用于定时更新的可重入锁
    public static final String CHANNEL_DATA_REC_LIST_MEMORY_KEY_PREFIX = "CHANNEL_DATA_REC_LIST_MEM_";

    public static boolean updateChannelDataRec4LecomSDK(String key, List<ChannelData> channelDataList) {
        boolean updated = false;
        if (key != null && !CollectionUtils.isEmpty(channelDataList)) {
            CHANNEL_DATA_REC_MAP_4_LECOM_US.put(key, channelDataList);
        }
        return updated;
    }

    public static List<ChannelData> getChannelDataRecList4LecomSDK(String key) {
        return CHANNEL_DATA_REC_MAP_4_LECOM_US.get(key);
    }

    /**
     * 瀑布流专题中Package的类型
     */
    /*
     * public static final int WF_SUBJECT_CMS_TYPE_BANNER_1 = 326;// banner1
     * public static final int WF_SUBJECT_CMS_TYPE_BANNER_2 = 327;// banner2
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_1 = 328;// body1
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_2 = 329;// body2
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_3 = 330;// body3
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_4 = 331;// body4
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_5 = 332;// body5
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_6 = 333;// body6
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_7 = 334;// body7
     */

    /**
     * 测试环境
     */
    /*
     * public static final int WF_SUBJECT_CMS_TYPE_BANNER_1 = 274;// banner1
     * public static final int WF_SUBJECT_CMS_TYPE_BANNER_2 = 277;// banner2
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_1 = 280;// body1
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_2 = 283;// body2
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_3 = 286;// body3
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_4 = 289;// body4
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_5 = 292;// body5
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_6 = 295;// body6
     * public static final int WF_SUBJECT_CMS_TYPE_BODY_7 = 298;// body7
     */

    public static final int WF_SUBJECT_CMS_TYPE_BANNER_1 = ApplicationUtils.getInt(
            ApplicationConstants.WF_SUBJECT_CMS_TYPE_BANNER_1_KEY, 0);// banner1
    public static final int WF_SUBJECT_CMS_TYPE_BANNER_2 = ApplicationUtils.getInt(
            ApplicationConstants.WF_SUBJECT_CMS_TYPE_BANNER_2_KEY, 0);;// banner2
    public static final int WF_SUBJECT_CMS_TYPE_BODY_1 = ApplicationUtils.getInt(
            ApplicationConstants.WF_SUBJECT_CMS_TYPE_BODY_1_KEY, 0);;// body1
    public static final int WF_SUBJECT_CMS_TYPE_BODY_2 = ApplicationUtils.getInt(
            ApplicationConstants.WF_SUBJECT_CMS_TYPE_BODY_2_KEY, 0);;// body2
    public static final int WF_SUBJECT_CMS_TYPE_BODY_3 = ApplicationUtils.getInt(
            ApplicationConstants.WF_SUBJECT_CMS_TYPE_BODY_3_KEY, 0);;// body3
    public static final int WF_SUBJECT_CMS_TYPE_BODY_4 = ApplicationUtils.getInt(
            ApplicationConstants.WF_SUBJECT_CMS_TYPE_BODY_4_KEY, 0);;// body4
    public static final int WF_SUBJECT_CMS_TYPE_BODY_5 = ApplicationUtils.getInt(
            ApplicationConstants.WF_SUBJECT_CMS_TYPE_BODY_5_KEY, 0);;// body5
    public static final int WF_SUBJECT_CMS_TYPE_BODY_6 = ApplicationUtils.getInt(
            ApplicationConstants.WF_SUBJECT_CMS_TYPE_BODY_6_KEY, 0);;// body6
    public static final int WF_SUBJECT_CMS_TYPE_BODY_7 = ApplicationUtils.getInt(
            ApplicationConstants.WF_SUBJECT_CMS_TYPE_BODY_7_KEY, 0);;// body7

    public static final int WF_SUBJECT_TYPE_BANNER_1 = 1;// banner1
    public static final int WF_SUBJECT_TYPE_BANNER_2 = 2;// banner2
    public static final int WF_SUBJECT_TYPE_BODY_1 = 11;// body1
    public static final int WF_SUBJECT_TYPE_BODY_2 = 12;// body2
    public static final int WF_SUBJECT_TYPE_BODY_3 = 13;// body3
    public static final int WF_SUBJECT_TYPE_BODY_4 = 14;// body4
    public static final int WF_SUBJECT_TYPE_BODY_5 = 15;// body5
    public static final int WF_SUBJECT_TYPE_BODY_6 = 16;// body6
    public static final int WF_SUBJECT_TYPE_BODY_7 = 17;// body7

    /**
     * 瀑布流专题Banner中icon类型
     */
    public static final int WF_SUBJECT_BANNER_ICON_TYPE_0 = 0;// 非会员
    public static final int WF_SUBJECT_BANNER_ICON_TYPE_1 = 1;// 超级影视会员

    /**
     * 页面类型
     */
    public static final int CMS_PAGE_WF_SUBJECT_TYPE = 1;// 瀑布流专题页面

    private final static Logger log = LoggerFactory.getLogger(ChannelConstants.class);

    public static final Map<Integer, String> CHILD_AGE_PAGEID = new HashMap<Integer, String>();
    public static String CHANNEL_CHILD__PAGE_ID = ApplicationUtils.get(ApplicationConstants.CHANNEL_PAGEID_CHILD);

    public final static long CHANNEL_CHILD__PAGE_ID_UPDATE_INTERVAL = 3600000L; // 1小时
    public static long CHANNEL_CHILD__PAGE_ID_LASTUPDATE_TIME = 0L; // 上次更新时间
    public static Lock CHANNEL_CHILD__PAGE_ID_UPDATE_Lock = new ReentrantLock(); // 用于定时更新

    public static void setChildPageIdsMap() {
        if ((CHILD_AGE_PAGEID == null || CHILD_AGE_PAGEID.isEmpty())
                || (System.currentTimeMillis() - CHANNEL_CHILD__PAGE_ID_LASTUPDATE_TIME) > CHANNEL_CHILD__PAGE_ID_UPDATE_INTERVAL) {
            if (CHANNEL_CHILD__PAGE_ID_UPDATE_Lock.tryLock()) {
                try {
                    if (StringUtil.isNotBlank(CHANNEL_CHILD__PAGE_ID) && CHANNEL_CHILD__PAGE_ID.contains(";")) {
                        String[] pageIds = CHANNEL_CHILD__PAGE_ID.split(";");
                        if (pageIds.length > 0) {
                            for (String pageId : pageIds) {
                                if (StringUtil.isNotBlank(pageId) && pageId.contains(":")) {
                                    String[] age_page = pageId.split(":");
                                    CHILD_AGE_PAGEID.put(StringUtil.toInteger(age_page[0]), age_page[1]);
                                }
                            }
                        }
                        CHANNEL_CHILD__PAGE_ID_LASTUPDATE_TIME = System.currentTimeMillis();
                    }
                } catch (Exception e) {
                    log.error("setChildPageIdsMap update CHILD_AGE_PAGEID error", e);
                } finally {
                    CHANNEL_CHILD__PAGE_ID_UPDATE_Lock.unlock();
                    log.info("setChildPageIdsMap:CHANNEL_CHILD__PAGE_ID_UPDATE_Lock unlocked.");
                }
            }
        }

    }
}