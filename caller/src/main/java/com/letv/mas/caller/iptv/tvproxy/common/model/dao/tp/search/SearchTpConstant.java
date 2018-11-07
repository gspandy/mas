package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;

import java.util.HashMap;
import java.util.Map;

public class SearchTpConstant {

    public static final int SEARCH_BY_DT = -2; // 数据类型
    public static final int SEARCH_BY_CATEGORY = -1; // 分类(category)
    public static final int SEARCH_BY_SUBCATEGORY = 1; // 子分类分类
    public static final int SEARCH_BY_LABEL = 2; // 标签
    public static final int SEARCH_BY_AREA = 3; // 地区
    public static final int SEARCH_BY_INITIAL_LETTER = 4; // 首字母
    public static final int SEARCH_BY_ACTOR = 5; // 主演、主持人
    public static final int SEARCH_BY_DIRECTOR = 6; // 导演
    public static final int SEARCH_BY_RELEASEDATE = 7; // 发布日期
    public static final int SEARCH_BY_AGE = 8; // 适应年龄
    public static final int SEARCH_BY_ALBUMTYPE = 9; // 专辑类型
    public static final int SEARCH_BY_ALBUMSTYLE = 10; // 专辑风格
    public static final int CARTONGRANKLIST = 11; // 动漫排行
    public static final int FILMRANKLIST = 12; // 电影排行
    public static final int TVRANKLIST = 13; // 电视剧排行
    public static final int ONLINE = 14; // 网络院线
    public static final int SEARCH_BY_TV = 16; // 电视台
    public static final int SEARCH_BY_YUANXIAN = 18; // 是否tv收费院线
    public static final int SEARCH_BY_STREAM_NEW = 19; // 码流
    public static final int SEARCH_BY_ORDER = 20;// 排序
    public static final int SEARCH_BY_STARRING_PLAY = 21;// 演员饰演
    public static final int SEARCH_BY_ALBUM_IS_END = 22;// 专辑是否完结
    public static final int SEARCH_BY_LANGUAGE = 23;// 按语言检索
    public static final int SEARCH_BY_STAR = 24;// 明星
    public static final int SEARCH_BY_STAR_GEND = 25;// 明星性别
    public static final int SEARCH_BY_STAR_PRO = 26;// 明星职业
    public static final int SEARCH_BY_HOME_MADE = 27;// 是否自制剧
    public static final int SEARCH_BY_RECORD_COMPANY = 28;// 来源
    public static final int SEARCH_BY_MMS_CMS_DATA = 29;// 媒资CMS数据
    public static final int SEARCH_BY_PID = 30;// 指定专辑id自动抓取视频列表
    public static final int SEARCH_BY_ZHUANTI_DATA = 31;// label专题列表
    public static final int SEARCH_ZHUANTI = 32;// 专题检索
    public static final int QZRANKLIST = 33;// 亲子排行榜
    public static final int SPORTRANKLIST = 34;// 体育排行榜
    public static final int ENTRANKLIST = 35;// 娱乐排行榜
    public static final int VARRANKLIST = 36;// 综艺排行榜
    public static final int MUSICRANKLIST = 37;// 音乐排行榜
    public static final int DOCRANKLIST = 38;// 纪录片排行榜
    public static final int SEARCH_BY_TAG = 39; // 汽车tag搜索
    public static final int SEARCH_RECREATION_TYPE = 40; // 娱乐类型
    public static final int SEARCH_SPORT_STYLE = 41; // 体育类型
    public static final int SEARCH_BY_VIDEO_DURATION = 42; // 按视频时长检索
    public static final int SEARCH_BY_COOPERATION = 43; // 按合作平台检索
    public static final int SEARCH_BY_PLAYSTREAMS = 49;// 按清晰充检索
    // 乐视儿童相关
    public static final int SEARCH_BY_AGE2 = 44; // 按年龄检索
    public static final int SEARCH_BY_CONTENT = 45; // 按内容属性检索
    public static final int SEARCH_BY_FUNCTION = 46; // 按功能检索
    public static final int SEARCH_BY_LANGUAGE_CHILD = 48;// 按语言检索

    public static final int SEARCH_BY_ALONE = 47; // 是否独播
    public static final int SEARCH_BY_ISPAY = 50;// 是否付费
    public static final int SEARCH_BY_BRAND = 51;// 根据品牌
    public static final int SEARCH_BY_YEAR = 52;// 年份检索

    /**
     * 是否需要将CMS接口返回值从ISO-8859-1转换为UTF-8开关值，1--“开启”，“0”关闭
     */
    public static final String SEARCH_CMS_DECODE_ISO_TO_UTF_ON = "1";

    public final static String LESO_SRC_VRS = "1"; // 专辑来源---内网媒资
    public final static String LESO_SRC_WEB = "2"; // 专辑来源---外网

    public final static String SEARCH_WCODE_HK = "HK";

    /**
     * 从搜索获取内网专辑详情页URL
     * @return
     */
    public static String getAlbumDetailURL(String wcode) {
        // TODO ligang 20151216 url应该只是一个常量，而不是方法
        return ApplicationConstants.SEARCH_I_OPEN_API_BASE_HOST + "/detail/cbase/q";
    }

    /**
     * 1.从搜索获取外网专辑详情页URL
     * 2. 获取站源列表
     */
    public static String getWebsiteURL(String wcode) {
        return ApplicationConstants.SEARCH_I_OPEN_API_BASE_HOST + "/detail/cbase/q/website";
    }

    /**
     * 获取明星详情URL
     * @return
     */
    public static String getStarDetailURL(String wcode) {
        return ApplicationConstants.VRS_I_API_BASE_HOST + "/mms/inner/star/v2/get";
    }

    /**
     * 获取搜检索URL,若不存在对应的语言环境，返回中文简体
     * @return
     */
    public static String getSearchURL(String wcode) {
        return ApplicationConstants.SEARCH_LE_SO_BASE_HOST + "/interface";
    }

    /**
     * 获取乐搜新首页专辑推荐列表URL
     * @return
     */
    public static String getMainPagePushAlbumsURL(String wcode) {
        return ApplicationConstants.SEARCH_DATA_SO_BASE_HOST + "/api/masthead";
    }

    /**
     * 获取乐搜新首页频道列表URL
     * @return
     */
    public static String getMainPageChannelsURL(String wcode) {
        return ApplicationConstants.SEARCH_DATA_SO_BASE_HOST + "/api/channel_list";
    }

    /**
     * 获取乐搜新首页频道内话题列表URL
     * @return
     */
    public static String getMainPageTopicsURL(String wcode) {
        return ApplicationConstants.SEARCH_DATA_SO_BASE_HOST + "/api/channel_topics";
    }

    /**
     * 获取乐搜新首页频道下话题内数据的URL
     * @return
     */
    public static String getMainPageTopicDataURL(String wcode) {
        return ApplicationConstants.SEARCH_DATA_SO_BASE_HOST + "/api/channel_data";

    }

    /**
     * 获取排行URL，若不存在对应的语言环境，返回中文简体
     */
    public static String getRankURL(String wcode) {
        return ApplicationConstants.SEARCH_I_TOP_BASE_HOST + "/json";
    }

    /**
     * 获取CMS版块数据的URL，若不存在对应的语言环境，返回中文简体
     */
    public static String getCMSBlockURL(String wcode) {
        return ApplicationConstants.CMS_STATIC_API_BASE_HOST + "/cmsdata/block";
    }

    /**
     * 获取搜索建议URL，若不存在对应的语言环境，返回中文简体
     * @param locale
     * @return
     */
    public static String getSuggestURL(String wcode) {
        return ApplicationConstants.SEARCH_SUGGEST_BASE_HOST + "/suggestion";
    }

    /**
     * 获取推荐的URL
     * @param wcode
     * @return
     */
    public static String getRecommendationURL(String wcode) {
        return ApplicationConstants.REC_BASE_HOST + "/tv";
    }

    /**
     * 获取卡片内容的URL，若不存在对应的语言环境，返回中文简体
     * @param locale
     * @return
     */
    public static String getCardDataURL(String wcode) {
        return ApplicationConstants.SEARCH_DATA_SO_BASE_HOST + "/data";
    }

    /**
     * 获取ip地址所在地的URL
     * @param wcode
     * @return
     */
    public static String getIPLocationURL(String wcode) {
        return ApplicationConstants.SEARCH_DATA_SO_BASE_HOST + "/location";
    }

    /**
     * 乐视儿童--年龄与内容标签value与推荐接口的value映射
     */
    private static final Map<String, String> TAG_AGE_VALUE = new HashMap<String, String>();// 年龄映射
    private static final Map<String, String> TAG_FUNCTION_VALUE = new HashMap<String, String>();// 按功能映射
    private static final Map<String, String> TAG_CONTENT_VALUE = new HashMap<String, String>();// 按内容属性映射
    private static final Map<String, String> TAG_LANGUAGE_VALUE = new HashMap<String, String>();// 按语言属性映射

    static {

        TAG_AGE_VALUE.put("1", "840001,840002,840003,840004,840005,840006,840007");
        TAG_AGE_VALUE.put("2", "840001,840002,840003");// 0-3岁
        TAG_AGE_VALUE.put("3", "840002");// 1-2岁
        TAG_AGE_VALUE.put("4", "840003");// 2-3岁
        TAG_AGE_VALUE.put("5", "840004");// 3-4岁
        TAG_AGE_VALUE.put("6", "840005");// 4-5岁
        TAG_AGE_VALUE.put("7", "840006");// 5-6岁
        TAG_AGE_VALUE.put("8", "840007");// 6-12岁

        TAG_FUNCTION_VALUE.put("1", "830001,830008,830015,830016,830017,830018,"
                + "830009,830019,830020,830021,830022,830023,830024,830025,"
                + "830010,830026,830027,830028,830029,830030,830031,830032,830033,"
                + "830011,830034,830035,830036,830037,830038,830039,830040,830041,830042,"
                + "830012,830043,830044,830045,830046,830047," + "830013,830048,830049,830050,830051,830052,830053,"
                + "830014,830054,830055,830056,830057,830058");// 不限
        TAG_FUNCTION_VALUE.put("2", "830001,830008,830015,830016,830017,830018");// 语言
        TAG_FUNCTION_VALUE.put("3", "830009,830019,830020,830021,830022,830023,830024,830025");// 兴趣
        TAG_FUNCTION_VALUE.put("4", "830010,830026,830027,830028,830029,830030,830031,830032,830033");// 认知
        TAG_FUNCTION_VALUE.put("5", "830011,830034,830035,830036,830037,830038,830039,830040,830041,830042");// 人格
        TAG_FUNCTION_VALUE.put("6", "830012,830043,830044,830045,830046,830047");// 习惯
        TAG_FUNCTION_VALUE.put("7", "830013,830048,830049,830050,830051,830052,830053");// 社交
        TAG_FUNCTION_VALUE.put("8", "830014,830054,830055,830056,830057,830058");// 体能

        TAG_CONTENT_VALUE.put("1",
                "850001,850002,850003,850004,850005,850006,850007,850008,850009,850010,850011,850012,850013");// 不限
        TAG_CONTENT_VALUE.put("2", "850001");// 儿歌
        TAG_CONTENT_VALUE.put("3", "850002");// 故事
        TAG_CONTENT_VALUE.put("4", "850003");// 音乐
        TAG_CONTENT_VALUE.put("5", "850004");// 绘本
        TAG_CONTENT_VALUE.put("6", "850005");// 动画
        TAG_CONTENT_VALUE.put("7", "850006");// 课程
        TAG_CONTENT_VALUE.put("8", "850007");// 节目
        TAG_CONTENT_VALUE.put("9", "850008");// 电影
        TAG_CONTENT_VALUE.put("10", "850009");// 纪录片
        TAG_CONTENT_VALUE.put("11", "850010");// 综艺
        TAG_CONTENT_VALUE.put("12", "850011");// 小学
        TAG_CONTENT_VALUE.put("13", "850012");// 双语
        TAG_CONTENT_VALUE.put("14", "850013");// 父母课程

        TAG_LANGUAGE_VALUE.put("1", "70001,70003,70012");// 不限
        TAG_LANGUAGE_VALUE.put("2", "70001");// 国语
        TAG_LANGUAGE_VALUE.put("3", "70003");// 英语
        TAG_LANGUAGE_VALUE.put("4", "70012");// 无对白

    }

    public static String getAgeValue(String age) {
        return TAG_AGE_VALUE.get(age);
    }

    public static String getFunctionValue(String function) {
        return TAG_FUNCTION_VALUE.get(function);
    }

    public static String getContentValue(String content) {
        return TAG_CONTENT_VALUE.get(content);
    }

    public static String getLanguageValue(String language) {
        return TAG_LANGUAGE_VALUE.get(language);
    }

    public static final String CARD_ID_PARAM_FOR_SEARCH = "102-103-104-106-105";// 去掉了猜你喜欢201

}
