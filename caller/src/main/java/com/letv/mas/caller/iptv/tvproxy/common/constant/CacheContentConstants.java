package com.letv.mas.caller.iptv.tvproxy.common.constant;


import org.apache.commons.lang.StringUtils;

/**
 * 缓存内容静态配，用于取代RedisConstants，2016-01-11
 * @author KevinYi
 */
public class CacheContentConstants {

    /**
     * 视频缓存key前缀
     */
    public static final String E_VIDEOMYSQLTABLE_ID = "E_VideoMysqlTable-";
    public static final String VIDEO_KEY_PREFIX = "Video";// 新缓存前缀

    /**
     * 投屏播放的视频缓存key前缀，其中2表示播放模式为投屏播放（0--TV版播放，1--儿童播放，2--投屏播放）
     */
    public static final String E_2_VIDEOMYSQLTABLE_KEY_PREFIX = "E_2_VideoMysqlTable-";

    /**
     * 直播投屏缓存key前缀，其中2表示播放模式为投屏播放（0--TV版播放，1--儿童播放，2--投屏播放）
     */
    public static final String E_2_LIVE_CHANNEL_KEY_PREFIX = "E_2_LC_";

    /**
     * 缓存中标志位有效时的值
     */
    public static final String MARK_VALID_VALUE = "1";

    // 频道二级标签 检索缓存
    public final static String SEARCH_MENU_RETRIEVE_ID = "Search_Menu_Retrieve_";
    // 频道二级标签CMS类型缓存
    public final static String SEARCH_MENU_CMS_ID = "Search_Menu_Cms_";
    // 频道二级标签 猜你喜欢 缓存
    public final static String SEARCH_MENU_USERLIKE_ID = "Search_Menu_UserLike_";
    // 频道二级标签 labelId 缓存
    public final static String SEARCH_MENU_BYLABELID_ID = "Search_Menu_ByLabelId_";
    // 频道二级标签 label专题 缓存
    public final static String SEARCH_MENU_LABEL_ID = "Search_Menu_Label_";
    // 频道二级标签 专辑id 缓存
    public final static String SEARCH_MENU_BYALBUMID_ID = "Search_Menu_ByAlbumId_";
    // 频道二级标签 请求的方法为searchtypes时的key
    public final static String SEARCHTYPES_ID = "Search_Menu_SearchTypes_";

    /**
     * 排行数据放入Redis中的key的统一前缀
     */
    public final static String SEARCH_MENU_RANK_ID = "rank_redis_prefix_";
    /**
     * 专题传输数据缓存key，格式为"key+{subjectId}"
     */
    public static final String CHANNEL_SUBJECT_DTO_CACHE_PREFIX = "CHANNEL_SUBJECT_DTO_CACHE_";
    /**
     * 专题数据缓存key，格式为"key+{subjectId}"
     */
    public static final String CHANNEL_SUBJECT_CACHE_PREFIX = "CHANNEL_SUBJECT_CACHE_";
    /**
     * 视频营销配置数据缓存key
     */
    public static final String CHANNEL_VIDEO_SALE_CONFIG_CACHE_PREFIX = "CHANNEL_VIDEO_SALE_CONFIG_CACHE_";
    /**
     * 板块背景图缓存key前缀，实际key为缓存key+bolockID
     */
    public final static String CHANNEL_BOLCK_TVPIC_PREFIX = "CHANNEL_BOLCK_TVPIV_";

    /**
     * 频道墙更新到*集key前缀，实际key为缓存key+channelID
     */
    public final static String CHANNEL_TITLE_FOCUS_PREFIX = "CHANNEL_TITLE_FOCUS1_";
    /**
     * 频道墙更新前三个频道图片，实际key为缓存key+terminalApplication+channelID
     */
    public final static String CHANNEL_WALL_PIC_PREFIX = "CHANNEL_WALL_PIC_";
    /**
     * 色块色值，临时方案
     */
    public static final String BLOCK_COLOR_KEY = "BLOCK_COLOR_CBASE_KEY";

    /**
     * 直播部门已上线直播大厅缓存数据key
     */
    public static final String LIVE_ROOM_CHANNEL_CACHE_KEY = "LIVE_ROOM_CHANNEL";

    /**
     * 体育频道直播大厅数据缓存
     */
    public static final String CHANNEL_SPORTS_LIVE_DATA = "channel_sports_live_data_";

    public static final String E_LIVE_DTO = "E_LiveDto-";

    /**
     * 刷新单场或若干场直播实时信息的时间间隔缓存Key值，业务意义参见LiveDto.interval
     */
    public static final String LIVE_LIST_REAL_TIME_DATA_REFRESH_INTERVAL = "LIVE_LIST_REAL_TIME_DATA_REFRESH_INTERVAL";

    /**
     * 缓存角色信息key前缀
     */
    public static final String USER_CACHE_FOR_ROLE_USERID = "USER_CACHE_FOR_ROLE_";

    /**
     * 缓存用户基本信息前缀
     */
    public static final String USER_CACHE_FOR_USER_INFO = "USERID_";

    /**
     * 用户eros token
     */
    public static final String USER_EROS_TOKEN = "USER_EROS_TOKEN-";

    public static final String TPSDK_BSCHANNEL_COMMON_PREFIX = "TPSDK_BSCHANNEL_";

    /**
     * 播放下拉列表（/play/list.json）接口投屏业务启用领先版数据开关key，值为1--启用，0--不启用
     */
    public static final String VIDEO_PLAY_LIST_LETV_LEADING_SWITCH = "VIDEO_PLAY_LIST_LETV_LEADING_SWITCH";

    /**
     * 乐视儿童播放列表缓存id
     */
    public static final String E_IptvVideoChildInfo_ID = "E_IptvVideoChildInfo-";

    /**
     * 播放--是否展示暂停广告页面
     */
    public static final String VIDEO_SHOW_PLAY_PAUSE_AD_PAGE = "VIDEO_SHOW_PLAY_PAUSE_AD_PAGE";

    /**
     * 是否展示浮层广告策略，null或0或其他值--都不展示，1--仅非会员展示广告，2--会员展示广告，3--全是展示
     */
    public static final String VIDEO_PLAY_FLOAT_AD_POLICY = "VIDEO_PLAY_FLOAT_AD_POLICY";

    /**
     * 视频播放鉴权token默认有效时长的缓存key
     */
    public static final String VIDEO_PLAY_TOKEN_EXPIRE_TIME_DEFAULT_KEY = "VIDEO_PLAY_TOKEN_EXPIRE_TIME_DEFAULT";

    /**
     * 没有弹幕的视频id缓存key
     */
    public static final String NO_DANMU_VIDEO_IDS_KEY = "NO_DANMU_VIDEO_IDS";

    public static final String E_ALBUMMYSQLTABLE_ID = "E_AlbumMysqlTable-";// 专辑缓存key前缀
    public static final String ALBUM_KEY_PREFIX = "Album";// 新专辑缓存key前缀
    public static final String E_ALBUM_VIDEOINDEXES_ID = "E_Album_VideoIndexes-";// 作品库专辑视频索引集合缓存key前缀

    public static final String D_VIDEOMYSQLTABLE_WATERMARK_LIST = "D_VideoMysqlTable_waterMark_list-";

    /**
     * 扫码互动后台数据配置
     */
    public static final String VIDEO_REACTION_CONFIG_DATA = "video_reaction_config_data";

    /**
     * 会员模块收银台用户协议文案
     */
    public static final String VIP_USER_AGGREMENT = "Vip_User_Aggrement_";

    /**
     * 收银台支付渠道排序策略键名
     */
    public static final String VIP_PAYMENT_CHANNEL_ORDER = "Vip_Payment_Channel_Order";

    /**
     * 缓存用户不可以参加一元包月活动的主键前缀
     */
    public static final String VIP_CANNOT_ONEFORMONTH_KEY_PREFIX = "yeepay";
    public static final String VIP_CANNOT_ONEFORMONTH_VALUE = "0";

    /**
     * 会员模块，用户账户会员有效期截至时间缓存key，需拼接userid使用，取值参见VipAccountTpResponse.
     * seniorendtime说明
     */
    public static final String VIP_USER_VIP_END_TIME = "VIP_USER_VIP_END_TIME_";

    /**
     * 通过userid和deviceKey获取用户账户会员有效期截至时间缓存key；
     * 2015-09-09，受机卡问题影响，用户登录机卡设备上时，会员有效期可能会不同；
     * 这样设计，会导致因机卡设备数量，导致数据副本，但可以规避mac副本数量带来的影响
     * @param userid
     * @param deviceKey
     *            设备暗码
     * @return
     */
    public static final String getUserVipEndTimeKey(String userid, String deviceKey) {
        return VIP_USER_VIP_END_TIME + userid + "_" + StringUtils.trimToEmpty(deviceKey);
    }

    /**
     * 套餐包顺序放缓村的key值
     */
    public static final String PACKAGE_ORDER_KEY = "packageorder";

    /**
     * 超前院线专题数据缓存key，格式为"key+{subjectId}"
     */
    public static final String CHANNEL_PRELIVE_SUBJECT_CONTENT_CACHE_PREFIX = "CHANNEL_PRELIVE_SUBJECT_CONTENT_CACHE_";

    /**
     * 超前院线按钮配置数据缓存key
     */
    public static final String CHANNEL_PRELIVE_BUTTON_CONFIG_CACHE_PREFIX = "CHANNEL_PRELIVE_BUTTON_CONFIG_CACHE";

    public static final String E_OFF_LINE_ALBUMMYSQLTABLE_ID = "E_OFF_LINE_AlbumMysqlTable-";

    public static final String E_DEL_ALBUMMYSQLTABLE_ID = "E_DEL_AlbumMysqlTable-";

    public static final String E_OFF_LINE_VIDEOMYSQLTABLE_ID = "E_OFF_LINE_VideoMysqlTable-";

    public static final String E_DEL_VIDEOMYSQLTABLE_ID = "E_DEL_VideoMysqlTable-";

    public static final String E_LIVE_TOPIC_LIST = "E_LiveTopicList";

    /**
     * 能够参加“定向弹窗”活动（1元包月）的3000用户
     */
    public static final String DIRECTIONAL_PUSH_USER_41 = "Directional_Push_User_41";

    /**
     * 用户中心username-userid映射缓存key
     */
    public static final String USERNAME_USERID_MAP = "User_Username_Userid_Map_";

    /**
     * 收银台套餐排序策略键名，使用套餐id与英文逗号拼接形式
     */
    // @Deprecated
    // public static final String VIP_PRICE_PACKAGE_ID_ORDER =
    // "Vip_PricePackage_Id_Order";

    /**
     * 用户中心username-loginname映射缓存key，使用时还需拼接usernanme
     */
    public static final String USER_USERNAME_LOGINNAME_MAP = "User_Username_Loginname_Map_";

    /**
     * 会员模块，用户账户会员类型缓存key，需拼接userid使用，取值参见VipAccountTpResponse.isvip说明
     */
    public static final String VIP_USER_VIP_TYPE = "VIP_USER_VIP_TYPE_";

    /**
     * 排行榜需求中，统计一周播放量定时操作的最近一次操作时间，格式为"yyyyMMdd"
     */
    public static final String CHANNEL_CHATRS_WEEK_RANK_NEWEST_DATE = "WEEK_RANK_NEWEST_DATE";

    /**
     * 播放--非会员播放卡顿图片URL
     */
    public static final String VIDEO_PLAY_PAUSE_IMG = "VIDEO_PLAY_PAUSE_IMG";

    /**
     * 播放逻辑，是否需要模拟非会员账户登录key
     */
    public static final String VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN_KEY = "VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN";

    /**
     * 搜索模块，是否需要将CMS接口返回值从ISO-8859-1转换为UTF-8，"1"需要需要转换，"0"--不需要
     */
    public static final String SEARCH_CMS_DECODE_ISO_TO_UTF = "SEARCH_CMS_DECODE_ISO_TO_UTF";

    /**
     * 单点收费中，特殊处理的专辑id，这些专辑里的视频categary将被设置为1（电影）以满足电视剧的单点需求；作为2.8.2中单点的兼容策略
     */
    public static final String SINGLE_CHARGE_SPECIAL_ALBUM_IDS = "SINGLE_CHARGE_SPECIAL_ALBUM_IDS";

    /**
     * 超前影院缓存CMS数据
     */
    public final static String CHANNEL_PRELIVE_SUBJECT_PREFIX = "CHANNEL_PRELIVE_SUBJECT_PREFIX_";

    /**
     * 超前点映按钮配置缓存key
     */
    public final static String CHANNEL_PRELIVE_PROFILE_CACHE_KEY = "IPTV_CONF_CHANNEL_PROFILE";

    /**
     * 儿童桌面管理中服务端配置表信息根据model不同存入redis
     */
    public final static String DESK_CONFIG_MODEL_TYPE = "DESK_CONFIG_MODEL_TYPE_";

    /**
     * 乐视儿童父级板块id_年龄_性别_周数映射关系存储到redis
     */
    public final static String CHILDERN_BLOCKMATCH_BY_AGE_GENDER_WEEK = "CHILDERN_BLOCKMATCH_";

    public static final String D_LIVECHANNELDTO_CHANNELID_SIZE_BROADCASTID = "D_LiveChannelDto-";

    public static final String D_DEL_IptvVideoInfos_ALBUM_ID = "D_DEL_IptvVideoInfos_album_id-";// 删除的专辑所拥有的视频

    public static final String D_IPTVVIDEOINFO_WATERMARK_LIST = "D_IptvvideoInfo_waterMark_list-";// 视频的水印信息

    /**
     * BOSS付费频道信息缓存key值
     */
    public static final String BOSS_PAY_CHANNEL_DATA = "pay_channel_data";// 数据缓存key

    public static String getLiveChannelDtoKey(String channelId, Integer size, Integer broadcastId) {
        return D_LIVECHANNELDTO_CHANNELID_SIZE_BROADCASTID + channelId + "-" + size + "-" + broadcastId;
    }

    // 频道二级标签 专辑id 缓存
    public final static String SEARCH_MENU_BYALBUMID_id = "Search_Menu_ByAlbumId_";

    public static final String buildEKey(String e, Long id) {
        return e + id;
    }

    /**
     * 多板块推荐缓存key前缀
     */
    public final static String RECO_RECOVERY_PREFIX = "RECO_RECOVERY_";

    /**
     * 退出播放缓存key
     */
    public final static String RECOMMENDATION_PREFIX = "RECO_0001_";

    /**
     * 乐见桌面缓存key
     */
    public static final String LEVEIW_CACHE_KEY = "LEVIEW";

    /**
     * 发布会乐见桌面缓存key
     */
    public static final String RELEASE_LEVEIW_CACHE_KEY = "RELEASE_LEVIEW";

    /**
     * 发布会版乐见出临时数据缓存key
     */
    public static final String RELEASE_LEVEIW_TMP_DATA_CACHE_KEY = "RELEASE_LEVIEW_TMP_DATA";

    /**
     * 国广桌面缓存key
     */
    public static final String CIBN_LEVEIW_CACHE_KEY = "CIBN_LEVIEW";

    /**
     * 华数桌面缓存key
     */
    public static final String WASU_LEVEIW_CACHE_KEY = "WASU_LEVIEW";

    /**
     * 视频营销专题url缓存前缀
     */
    public static final String VIDEO_SALE_URL_PREFIX = "video_sale_url_prefix_";

    /**
     *
     */
    public static final String MMS_TASK_VIDEO_OFFLINE_LIST_KEY = "MMS_TASK_VIDEO_OFFLINE_LIST";

    /**
     *
     */
    public static final String MMS_TASK_ALBUM_OFFLINE_LIST_KEY = "MMS_TASK_ALBUM_OFFLINE_LIST";

    /**
     * channel default stream cache key prefix
     */
    public static final String CHANNEL_DEFAULT_STREAM_KEY = "channel_default_stream_";

    /**
     * 缓存10秒
     */
    public static final int CACHE_EXPIRES_TEN_SECONDS = 10;

    /**
     * 缓存一分钟
     */
    public static final int CACHE_EXPIRES_ONE_MINUTE = 60;

    /**
     * 缓存一小时
     */
    public static final int CACHE_EXPIRES_ONE_HOUR = 60 * 60;

    /**
     * 缓存一天
     */
    public static final int CACHE_EXPIRES_ONE_DAY = CACHE_EXPIRES_ONE_HOUR * 24;

    /**
     * 缓存一月
     */
    public static final int CACHE_EXPIRES_ONE_MONTH = CACHE_EXPIRES_ONE_DAY * 30;
    /**
     * 板块映射关系前缀
     */
    public static final String BLOCK_MATCH_PRE = "BLOCK_MATCH_PRE";
    /**
     * cms板块缓存前缀
     */
    public static final String CHANNEL_BLOCK_CONTENT_KEY = "CHANNEL_BLOCK_CONTENT_";
    /**
     * cms页面缓存前缀
     */
    public static final String CHANNEL_PAGE_CONTENT_KEY = "CHANNEL_PAGE_CONTENT_";
    /**
     * cms页面包装string缓存前缀
     */
    public static final String CHANNEL_PAGE_DATA_RESP_KEY = "CHANNEL_PAGE_DATA_RESP_";
    public static final String CHANNEL_PAGE_DATA_RESP_DATA_KEY = "CHANNEL_PAGE_DATA_RESP_DATA_";
    public static final String CHANNEL_PAGE_DATA_RESP_PLUS_KEY = "CHANNEL_PAGE_DATA_RESP_PLUS_";

    /**
     * 乐导视板块缓存前缀
     */
    public static final String CHANNEL_BLOCK_CONTENT_LIST_KEY = "CHANNEL_BLOCK_CONTENT_LIST_";

    /**
     * channel id-channelName cache key prefix
     */
    public static final String CHANNEL_ID_CHANNELNAME_KEY = "CHANNEL_ID_CHANNELNAME_";

    /**
     * golive应用缓存deviceKey的值
     */
    public static final String USER_GOLIVE_DEVICEKEY_CACHE = "golive_";

    /**
     * TV版跳转体育APP配置缓存key
     */
    public static final String SPORTS_JUMP_CONFIG_LETV_KEY = "SPORTS_JUMP_CONFIG_LETV";

    /**
     * 乐搜版跳转体育APP配置缓存key
     */
    public static final String SPORTS_JUMP_CONFIG_LESO_KEY = "SPORTS_JUMP_CONFIG_LESO";

    /**
     * Set commodity data to cache key prefix
     */
    public static final String COMMODITY_DATA_PREFIX = "commodity_";

    /**
     * golive desktop navigation cache key
     */
    public static final String GOLIVE_DESKTOP_NAVIGATION_KEY = "GoliveDesktopNavigationKey_";

    /**
     * golive desktop videos by navigation cache key
     */
    public static final String GOLIVE_DESKTOP_VIDEOS_KEY = "GoliveDesktopVideosKey_";

    /**
     * golive data update signal
     */
    public static final String GOLIVE_MSG_CACHEKEY = "GoLiveMsgCacheKey";

    /**
     * golive desktop all data cache key
     */
    public static final String GOLIVE_DESKTOP_KEY = "GoliveDesktopKey_";
    public static final String GOLIVE_DESKTOP_KEY2 = "GoliveDesktopKey2_";

    /**
     * 360全景视频-码流缓存key前缀，完整格式为VIDEO_SPECIAL_STREAM_360_{videoId}
     */

    public static final String VIDEO_SPECIAL_STREAM_360_PREFIX = "VIDEO_SPECIAL_STREAM_360_";

    /**
     * 服务端下发features的缓存key前缀
     */
    public static final String TERMINAL_CONFIG = "terminal_";

    public static final String LEVIDI_CP_ID_LIST = "Levidi_CpIds";
    /**
     * 播放白名单
     */
    public static final String VIDEO_PLAY_WHITE_LIST = "Video_Play_WhiteList";
    /**
     * 缓存专辑分页信息前缀
     */
    public static final String ALBUM_ALL_LIST_PREFIX = "AlbumAllList";
    public static final String ALBUM_FEATURE_LIST_PREFIX = "AlbumFeatureList";
    public static final String ALBUM_OTHER_LIST_PREFIX = "AlbumOtherList";

    /**
     * 频道推荐数据列表最终结果缓存key前缀，使用方式是CHANNEL_DATA_REC_{terminalApplication}_{
     * rcPageid}
     */
    // public static final String CHANNEL_DATA_REC_LIST_KEY_PREFIX =
    // "CHANNEL_DATA_REC_";

    public static final String VIP_INFO_V2 = "vip_info_v2_";

    public static final String TERMINAL_SERVICE_TERM_PREFIX = "SERVICE_TERM_";

    /**
     * 视频模块，专辑播放、评论数缓存前缀，完整key为VIDEO_ALBUM_TOTAL_COUNT_STAT_PREFIX+{
     * terminalApplication}.toupercase+{region/salesArea}.toupercase+{albunId}
     */
    public static final String VIDEO_ALBUM_TOTAL_COUNT_STAT_PREFIX = "ALBUM.TOTAL.COUNT.STAT.";

    public static final String USER_NEW_ADD_COLLECTION_CACHE_KEY_PREFIX = "USER_NEW_ADD_COLLECTION_CACHE_";

    public static final String USER_CHILD_LOCK_PREFIX = "USER_CHILD_LOCK_";

    public static final String USER_BALANCE_PREFIX = "USER_BALANCE_";

    public static final boolean IS_USER_BALANCE = true;

    /**
     * 排行榜开始时间加个缓存
     */
    public static final String CHARTS_START_DATE_CACHE = "charts_start_date";
    /**
     * 排行榜入口页各个榜单入口数据加缓存
     */
    public static final String CHARTS_LIST_CACHE = "charts_list_";

    /**
     * 收银台的支付成功也推荐缓存id
     */
    public static final String CASHIER_PAY_RECOMMEND_ALBUM_ID = "CASHIER_PAY_RECOMMEND_ALBUM_ID_";

    /**
     * 观星模版
     */
    public static final String ACTIVITY_USER_CACHE_KEY = "ACTIVITY_CACHE_USER_KEY_";

    public static final String ACTIVITY_DIRECT_CACHE_KEY = "ACTIVITY_DIRECT_CACHE_KEY_";

    public static final String ACTIVITY_NO_DATA_USER_CACHE_KEY = "ACTIVITY_NO_DATA_CACHE_USER_KEY";

    /**
     * gc.cp21.ott.cibntv.net/api/outer/getpids
     */
    public static final String GC_RECOMMENDATION_PID_LIST = "GC_RECOMMENDATION_PID_LIST";

    /**
     * 设备配置信息缓存key
     */
    public static final String LETV_TERMINAL_DEVICE_CONFIG_KEY = "LETV_TERMINAL_DEVICE_CONFIG";

    /**
     * 应用配置信息缓存key
     */
    public static final String LETV_TERMINAL_APP_CONFIG_KEY = "LETV_TERMINAL_APP_CONFIG";

    /**
     * 北美版收银台URL
     */
    public static final String US_CASHIER_URL = "us_chshier_url";

    /**
     * Set wfsubject data to cache key prefix
     */
    public static final String WFSUBJECT_DATA_PREFIX = "wf_subject_";

    /**
     * free time limit
     */
    public static final String FREE_TEMP_DATA_PREFIX = "free_temp_subject";

    /**
     * 最新统计排行榜缓存前缀
     */
    public static final String LATEST_STAT_RANK = "latest_stat_rank";

    /**
     * 最新统计分类排行榜缓存前缀
     */
    public static final String LATEST_STAT_CATEGORY_RANK = "latest_stat_category_rank";

    //
    public final static String ALBUM_DP_MORE_KEY_PREFIX = "ALBUM_DP_MORE_KEY_";

    /**
     * loading pic
     */
    public static final String LOADING_PIC_DATA = "loading_pic_data";

    /**
     * continue play video table
     */
    public static final String TRAILER_VIDEO_DATA_VIDEOTABLE = "trailer_video_data_videotable";

    /**
     * continue play video table
     */
    public static final String TRAILER_VIDEO_DATA_VIDEODTO_PREFIX = "trailer_video_data_video_";

    /**
     * continue play video table
     */
    public static final String TRAILER_VIDEO_DATA_MMS_PREFIX = "trailer_video_data_mms_";

    /**
     * continue play video table
     */
    public static final String ACT_REPORT_ANTI_REPLAY_KEY_PREFIX = "act_report_anti_replay_";
}
