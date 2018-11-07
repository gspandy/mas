package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.util.HashMap;
import java.util.Map;

public class CategoryIdCodeMapping {
    public static final Integer ALL = 0;
    public static final Integer ALL_FILM = 0;
    public static final Integer ALL_TV = 0;
    public static final Integer ALL_CARTOON = 0;
    public static final Integer ALL_3DFILM = 0;
    public static final Integer ALL_ENT = 0;
    public static final Integer ALL_MUSIC = 0;
    public static final Integer ALL_VARIETY = 0;
    public static final Integer ALL_DFILM = 0;
    public static final Integer ALL_RLIST = 0;

    public static final String CHANNELCODE_FILM = "film";
    public static final String CHANNELCODE_TV = "tv";
    public static final String CHANNELCODE_CARTOON = "cartoon";
    public static final String CHANNELCODE_3DFILM = "3Dfilm";
    public static final String CHANNELCODE_ENT = "ent";
    public static final String CHANNELCODE_MUSIC = "music";
    public static final String CHANNELCODE_VARIETY = "variety";
    public static final String CHANNELCODE_DFILM = "dfilm";
    public static final String CHANNELCODE_RLIST = "rlist";
    public static final String CHANNELCODE_YUANXIAN = "yuanxian";

    public static final String CHANNELCODE_ALL = "all";
    public static final String CHANNELCODE_LETVMK = "letvmk";
    public static final String CHANNELCODE_LETVCP = "letvcp";
    // private final static Map<String, Integer> CategoryIdCodeMap = new
    // HashMap<String, Integer>();
    // private final static Map<String, String> CHANNEL_CODE_TO_NAME = new
    // HashMap<String, String>();
    private static final Map<String, Integer> MENU_CID_MAP = new HashMap<String, Integer>();
    static {
        // CategoryIdCodeMap.put(CHANNELCODE_FILM, CategoryId.FILM_TYPE);
        // CategoryIdCodeMap.put(CHANNELCODE_TV, CategoryId.TV_PROGRAM_TYPE);
        // CategoryIdCodeMap.put(CHANNELCODE_CARTOON, CategoryId.CARTOON_TYPE);
        // CategoryIdCodeMap.put(CHANNELCODE_3DFILM, CategoryId.FILM_TYPE);
        // CategoryIdCodeMap.put(CHANNELCODE_ENT,
        // CategoryId.ENTERTAINMENT_TYPE);
        // CategoryIdCodeMap.put(CHANNELCODE_MUSIC, CategoryId.MUSIC_TYPE);
        // CategoryIdCodeMap.put(CHANNELCODE_VARIETY, CategoryId.VARIETY_TYPE);
        // CategoryIdCodeMap.put(CHANNELCODE_DFILM, CategoryId.DFILM_TYPE);
        // CategoryIdCodeMap.put(CHANNELCODE_RLIST, CategoryId.RLIST_TYPE);
        // CategoryIdCodeMap.put(CHANNELCODE_LETVCP, CategoryId.LETV_MAKE);
        // CategoryIdCodeMap.put(CHANNELCODE_LETVMK, CategoryId.LETV_TYPE);
        //
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_FILM, "电影");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_TV, "电视剧");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_CARTOON, "动漫");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_3DFILM, "3D电影");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_ENT, "娱乐");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_MUSIC, "音乐");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_VARIETY, "综艺");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_DFILM, "纪录片");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_LETVMK, "制造");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_LETVCP, "出品");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_RLIST, "排行榜");
        // CHANNEL_CODE_TO_NAME.put(CHANNELCODE_ALL, "全部");
        MENU_CID_MAP.put("250", 3);// 娱乐<------>娱乐
        MENU_CID_MAP.put("252", 1);// 网络院线<------>电影
        MENU_CID_MAP.put("256", 4);// 体育<------>体育
        MENU_CID_MAP.put("264", 9);// 音乐<------>音乐
        MENU_CID_MAP.put("267", 1);// 电影<------>电影
        MENU_CID_MAP.put("268", 5);// 动漫<------>动漫
        MENU_CID_MAP.put("269", 16);// 纪录片<------>纪录片
        MENU_CID_MAP.put("272", 11);// 综艺<------>综艺
        MENU_CID_MAP.put("273", 0);// 首页<------>首页
        MENU_CID_MAP.put("274", 1);// 3D频道<------>电影
        MENU_CID_MAP.put("275", 1);// 1080P<------>电影
        MENU_CID_MAP.put("277", 1);// 杜比频道<------>电影
        MENU_CID_MAP.put("279", 1);// 最新上架<------>电影
        MENU_CID_MAP.put("280", 1);// TOP热播<------>电影
        MENU_CID_MAP.put("281", 1);// 经典影院<------>电影
        MENU_CID_MAP.put("282", 1);// 港台<------>电影
        MENU_CID_MAP.put("283", 1);// 欧美<------>电影
        MENU_CID_MAP.put("284", 1);// 日韩<------>电影
        MENU_CID_MAP.put("362", 2);// 电视剧<------>电视剧
        MENU_CID_MAP.put("437", 4);// 免费专区<------>体育
        MENU_CID_MAP.put("525", 1);// 粤语频道<------>电影
        MENU_CID_MAP.put("549", 11);// 我是歌手<------>综艺
        MENU_CID_MAP.put("562", 4);// NBA<------>体育
        MENU_CID_MAP.put("308", 11);// 选秀<------>综艺
        MENU_CID_MAP.put("309", 11);// 情感<------>综艺
        MENU_CID_MAP.put("310", 11);// 时尚<------>综艺
        MENU_CID_MAP.put("311", 11);// 访谈<------>综艺
        MENU_CID_MAP.put("312", 11);// 生活<------>综艺
        MENU_CID_MAP.put("313", 11);// 播报<------>综艺
        MENU_CID_MAP.put("315", 1);// 院线电影<------>电影
        MENU_CID_MAP.put("317", 1);// 3D电影<------>电影
        MENU_CID_MAP.put("319", 1);// 电影<------>电影
        MENU_CID_MAP.put("321", 1);// 杜比电影<------>电影
        MENU_CID_MAP.put("323", 4);// 国内足球<------>体育
        MENU_CID_MAP.put("324", 4);// 国际足球<------>体育
        MENU_CID_MAP.put("325", 4);// 篮球<------>体育
        MENU_CID_MAP.put("326", 4);// 高尔夫<------>体育
        MENU_CID_MAP.put("329", 16);// 探索空间<------>纪录片
        MENU_CID_MAP.put("330", 16);// 军事<------>纪录片
        MENU_CID_MAP.put("331", 16);// 历史时空<------>纪录片
        MENU_CID_MAP.put("332", 16);// 人物<------>纪录片
        MENU_CID_MAP.put("333", 16);// 文化生活<------>纪录片
        MENU_CID_MAP.put("334", 16);// 社会万象<------>纪录片
        MENU_CID_MAP.put("345", 3);// 娱乐出品<------>娱乐
        MENU_CID_MAP.put("346", 3);// 最新资讯<------>娱乐
        MENU_CID_MAP.put("347", 3);// 独家报道<------>娱乐
        MENU_CID_MAP.put("349", 9);// 首播推荐<------>音乐
        MENU_CID_MAP.put("350", 9);// 华语金曲<------>音乐
        MENU_CID_MAP.put("351", 9);// 欧美乐坛<------>音乐
        MENU_CID_MAP.put("352", 9);// 日系音乐<------>音乐
        MENU_CID_MAP.put("353", 9);// 韩流风暴<------>音乐
        MENU_CID_MAP.put("295", 5);// 新上架<------>动漫
        MENU_CID_MAP.put("365", 2);// 最新<------>电视剧
        MENU_CID_MAP.put("367", 2);// 排行<------>电视剧
        MENU_CID_MAP.put("368", 2);// 经典剧场<------>电视剧
        MENU_CID_MAP.put("369", 2);// 内地<------>电视剧
        MENU_CID_MAP.put("370", 2);// 欧美<------>电视剧
        MENU_CID_MAP.put("371", 2);// 韩剧<------>电视剧
        MENU_CID_MAP.put("372", 2);// 港台<------>电视剧
        MENU_CID_MAP.put("376", 2);// 电视剧首页<------>电视剧
        MENU_CID_MAP.put("377", 1);// 电影首页<------>电影
        MENU_CID_MAP.put("297", 5);// 日本<------>动漫
        MENU_CID_MAP.put("413", 2);// 自制剧<------>电视剧
        MENU_CID_MAP.put("414", 1);// 微电影<------>电影
        MENU_CID_MAP.put("298", 5);// 欧美<------>动漫
        MENU_CID_MAP.put("419", 4);// 体育首页<------>体育
        MENU_CID_MAP.put("420", 11);// 强档推荐<------>综艺
        MENU_CID_MAP.put("421", 2);// 电视剧<------>电视剧
        MENU_CID_MAP.put("422", 5);// 动漫<------>动漫
        MENU_CID_MAP.put("423", 1);// 1080P首页<------>电影
        MENU_CID_MAP.put("424", 9);// 音乐首页<------>音乐
        MENU_CID_MAP.put("426", 16);// 纪录片首页<------>纪录片
        MENU_CID_MAP.put("299", 5);// 国产<------>动漫
        MENU_CID_MAP.put("300", 5);// 排行<------>动漫
        MENU_CID_MAP.put("429", 4);// 网球<------>体育
        MENU_CID_MAP.put("430", 4);// 综合体育<------>体育
        MENU_CID_MAP.put("431", 4);// 性感花边<------>体育
        MENU_CID_MAP.put("432", 3);// 精彩推荐<------>娱乐
        MENU_CID_MAP.put("433", 3);// 娱乐首页<------>娱乐
        MENU_CID_MAP.put("434", 11);// 经典回顾<------>综艺
        MENU_CID_MAP.put("435", 11);// 排行<------>综艺
        MENU_CID_MAP.put("436", 11);// 综艺首页<------>综艺
        MENU_CID_MAP.put("378", 5);// 动漫首页<------>动漫
        MENU_CID_MAP.put("438", 4);// 免费专区首页<------>体育
        MENU_CID_MAP.put("439", 4);// 体育专区<------>体育
        MENU_CID_MAP.put("440", 3);// 娱乐专区<------>娱乐
        MENU_CID_MAP.put("487", 16);// 实验室<------>纪录片
        MENU_CID_MAP.put("488", 0);// 首页<------>
        MENU_CID_MAP.put("490", 0);// 首页<------>
        MENU_CID_MAP.put("493", 0);// 首页<------>
        MENU_CID_MAP.put("415", 5);// 亲子<------>动漫
        MENU_CID_MAP.put("499", 0);// 首页<------>
        MENU_CID_MAP.put("502", 0);// 首页<------>
        MENU_CID_MAP.put("505", 0);// 首页<------>
        MENU_CID_MAP.put("508", 0);// 首页<------>
        MENU_CID_MAP.put("511", 0);// 首页<------>
        MENU_CID_MAP.put("514", 0);// 首页<------>
        MENU_CID_MAP.put("516", 1);// 首页<------>
        MENU_CID_MAP.put("518", 0);// 首页<------>
        MENU_CID_MAP.put("521", 0);// 首页<------>
        MENU_CID_MAP.put("523", 0);// 首页<------>
        MENU_CID_MAP.put("427", 5);// 连载<------>动漫
        MENU_CID_MAP.put("526", 1);// 粤语首页<------>电影
        MENU_CID_MAP.put("527", 1);// 电影<------>电影
        MENU_CID_MAP.put("528", 5);// 动漫<------>动漫
        MENU_CID_MAP.put("529", 0);// 首页<------>
        MENU_CID_MAP.put("531", 9);// 音乐<------>音乐
        MENU_CID_MAP.put("532", 9);// Live生活<------>音乐
        MENU_CID_MAP.put("533", 4);// NBA<------>体育
        MENU_CID_MAP.put("534", 4);// CBA<------>体育
        MENU_CID_MAP.put("535", 4);// 中超<------>体育
        MENU_CID_MAP.put("536", 4);// 亚冠<------>体育
        MENU_CID_MAP.put("537", 4);// 国足<------>体育
        MENU_CID_MAP.put("538", 4);// 英超<------>体育
        MENU_CID_MAP.put("539", 4);// 西甲<------>体育
        MENU_CID_MAP.put("540", 4);// 意甲<------>体育
        MENU_CID_MAP.put("541", 4);// 德甲<------>体育
        MENU_CID_MAP.put("542", 4);// 法甲<------>体育
        MENU_CID_MAP.put("543", 4);// 欧冠<------>体育
        MENU_CID_MAP.put("544", 4);// 世界杯<------>体育
        MENU_CID_MAP.put("545", 4);// 欧篮<------>体育
        MENU_CID_MAP.put("546", 4);// 网球<------>体育
        MENU_CID_MAP.put("547", 4);// 高尔夫<------>体育
        MENU_CID_MAP.put("548", 4);// 综合<------>体育
        MENU_CID_MAP.put("428", 5);// 剧场版<------>动漫
        MENU_CID_MAP.put("550", 0);// 首页<------>
        MENU_CID_MAP.put("553", 11);// 我是歌手首页<------>综艺
        MENU_CID_MAP.put("554", 11);// 整期回顾<------>综艺
        MENU_CID_MAP.put("555", 11);// 精彩看点<------>综艺
        MENU_CID_MAP.put("556", 11);// 追踪报道<------>综艺
        MENU_CID_MAP.put("557", 11);// 独家策划<------>综艺
        MENU_CID_MAP.put("558", 11);// 本季歌手<------>综艺
        MENU_CID_MAP.put("559", 11);// 乐视自制<------>综艺
        MENU_CID_MAP.put("560", 11);// 第一季回顾<------>综艺
        MENU_CID_MAP.put("561", 11);// 原唱PK<------>综艺
        MENU_CID_MAP.put("496", 0);// 首页<------>
        MENU_CID_MAP.put("563", 4);// 官方最佳<------>体育
        MENU_CID_MAP.put("564", 4);// 战报集锦<------>体育
        MENU_CID_MAP.put("565", 4);// 每日综述<------>体育
        MENU_CID_MAP.put("566", 4);// 精华片段<------>体育
        MENU_CID_MAP.put("567", 4);// 高清录播<------>体育
        MENU_CID_MAP.put("568", 4);// 迈阿密热火<------>体育
        MENU_CID_MAP.put("569", 4);// 洛杉矶湖人<------>体育
        MENU_CID_MAP.put("570", 4);// 休斯敦火箭<------>体育
        MENU_CID_MAP.put("571", 4);// NBA诸强<------>体育
        MENU_CID_MAP.put("572", 4);// 性感NBA<------>体育
        MENU_CID_MAP.put("575", 4);// NBA首页<------>体育
        MENU_CID_MAP.put("576", 0);// 首页<------>
        MENU_CID_MAP.put("577", 3);// 娱乐热播<------>娱乐
        MENU_CID_MAP.put("578", 3);// 明星八卦<------>娱乐
        MENU_CID_MAP.put("579", 1);// 影视资讯<------>电影
        MENU_CID_MAP.put("580", 11);// 综艺看点<------>综艺
        MENU_CID_MAP.put("581", 9);// 音乐资讯<------>音乐
        MENU_CID_MAP.put("582", 3);// 独家策划<------>娱乐
        MENU_CID_MAP.put("583", 3);// 最新资讯<------>娱乐
        MENU_CID_MAP.put("584", 11);// 幕后花絮<------>综艺
        MENU_CID_MAP.put("585", 11);// 纪录片<------>综艺

        MENU_CID_MAP.put("598", 1);// 纪录片<------>电影
        MENU_CID_MAP.put("602", 1);// 4K电影<------>电影
        MENU_CID_MAP.put("603", 2);// 4K电视剧<------>电视剧
        MENU_CID_MAP.put("604", 1);// 4K首页<------>电影
        MENU_CID_MAP.put("605", 9);// 4K音乐<------>音乐

        MENU_CID_MAP.put("586", 34);// 亲子<------>动漫
        MENU_CID_MAP.put("587", 34);// 首页<------>动漫
        MENU_CID_MAP.put("588", 34);// 搜索<------>动漫
        MENU_CID_MAP.put("589", 34);// 轮播<------>动漫
        MENU_CID_MAP.put("590", 34);// 亲子首页<------>动漫
        MENU_CID_MAP.put("591", 34);// 益智<------>动漫
        MENU_CID_MAP.put("592", 34);// 冒险<------>动漫
        MENU_CID_MAP.put("593", 34);// 轻松<------>动漫
        MENU_CID_MAP.put("594", 34);// 宠物<------>动漫
        MENU_CID_MAP.put("595", 34);// 孕婴<------>动漫
        MENU_CID_MAP.put("596", 34);// 生活秀<------>动漫
        MENU_CID_MAP.put("597", 34);// 排行榜<------>动漫

        MENU_CID_MAP.put("610", 5);// 专题首页<------>专题
        MENU_CID_MAP.put("609", 5);// 专题首页<------>专题
    }

    public static final Integer getCidByMid(Integer mid) {
        String midTmp = mid == null ? null : mid + "";
        return MENU_CID_MAP.get(midTmp);
    }

    // public static Integer getCategoryId(String CategoryCode) {
    // if (StringUtils.isBlank(CategoryCode)) {// 如果CategoryCode 为空，默认为电影
    // CategoryCode = CHANNELCODE_FILM;
    // }
    // if (CategoryIdCodeMap.get(CategoryCode) == null) {// 如果没有获取到CategoryId
    // // ，那么默认为电影
    // return CategoryId.FILM_TYPE;
    // }
    // return CategoryIdCodeMap.get(CategoryCode);
    // }
    //
    // public static String getChannelName(String channelCode) {
    // String nameString = CHANNEL_CODE_TO_NAME.get(channelCode);
    // if (nameString == null) {
    // nameString = "全部";
    // }
    // return nameString;
    // }
    //
    // public static boolean isBroadcastPositive(Integer categoryID) {
    // if (categoryID == null) {
    // return false;
    // }
    // if (categoryID == CategoryId.FILM_TYPE || categoryID ==
    // CategoryId.TV_PROGRAM_TYPE
    // || categoryID == CategoryId.LETV_MAKE || categoryID ==
    // CategoryId.LETV_TYPE) {
    // return true;
    // }
    // return false;
    // }
    //
    // public interface CategoryId {
    // int FILM_TYPE = 4;
    // int FILM_1080P = 1080;
    // int FILM_720P = 720;
    // int TV_PROGRAM_TYPE = 5;
    // int CARTOON_TYPE = 6;
    // int ENTERTAINMENT_TYPE = 86; // 娱乐
    // int LETV_TYPE = 164;// 乐视制造
    // int LETV_MAKE = 202; // 乐视出品
    // int DISCUSSION_TYPE = 92;
    // int MUSIC_TYPE = 66;// 音乐频道
    // int VARIETY_TYPE = 78; // 综艺
    // int DFILM_TYPE = 111;
    // int RLIST_TYPE = -100;// 排行
    // int FILM3D = 91;
    // int SPORT_TYPE = 221;
    //
    // /**
    // * add by yuehongmin 2012/04/17
    // */
    // int FASHION_TYPE = 186; // 风尚
    // int CAR_TYPE = 169; // 汽车
    // int TVPROGRAM_TYPE = 77;// 电视节目
    //
    // int NOFREE = 1; // 网络院线低码流收费
    // }

}
