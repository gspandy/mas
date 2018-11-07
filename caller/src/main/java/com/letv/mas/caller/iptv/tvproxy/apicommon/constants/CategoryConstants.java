package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

import java.util.HashMap;
import java.util.Map;

public class CategoryConstants {
    public static final int ALL = 0; // 全部
    public static final int FILM = 1;// 电影CategoryID
    public static final int TV = 2;// 电视剧CategoryID
    public static final int ENT = 3; // 娱乐CategoryID
    public static final int SPORT = 4; // 体育CategoryID
    public static final int CARTOON = 5;// 动漫CategoryID
    public static final int MUSIC = 9;// 音乐CategoryID
    public static final int VARIETY = 11;// 综艺CategoryID
    public static final int CAR = 14; // 汽车CategoryID
    public static final int RECORD = 16; // 纪录片CategoryID
    public static final int FASHION = 20; // 风尚CategoryID
    public static final int CAIJING = 22; // 财经CategoryID
    public static final int TRAVELING = 23; // 旅游
    public static final int HOT = 30; // 热点CategoryID
    public static final int PARENT = 34; // 亲子CategoryID
    public static final int EDUCATION = 1021;// 教育
    public static final int APPSTORE = 76;// 应用商店
    public static final int GAMECENTER = 77;// 游戏中心

    /**
     * 搜索分类的map
     * key为categoryId,value为id对应的名称的国际化的key
     */
    public static Map<Integer, String> SEARCH_CATEGORY_MAP = new HashMap<Integer, String>();
    static {
        SEARCH_CATEGORY_MAP.put(ALL, "SEARCH.CATEGORY.ALL");
        SEARCH_CATEGORY_MAP.put(TV, "SEARCH.CATEGORY.TV");
        SEARCH_CATEGORY_MAP.put(FILM, "SEARCH.CATEGORY.FILM");
        SEARCH_CATEGORY_MAP.put(PARENT, "SEARCH.CATEGORY.PARENTING");
        SEARCH_CATEGORY_MAP.put(CARTOON, "SEARCH.CATEGORY.CARTOON");
        SEARCH_CATEGORY_MAP.put(VARIETY, "SEARCH.CATEGORY.VARIETY");
        SEARCH_CATEGORY_MAP.put(SPORT, "SEARCH.CATEGORY.SPORT");
        SEARCH_CATEGORY_MAP.put(ENT, "SEARCH.CATEGORY.ENT");
        SEARCH_CATEGORY_MAP.put(MUSIC, "SEARCH.CATEGORY.MUSIC");
        // SEARCH_CATEGORY_MAP.put(HOT, "SEARCH.CATEGORY.HOTSPOT");
        SEARCH_CATEGORY_MAP.put(FASHION, "SEARCH.CATEGORY.FENGSHANG");
        SEARCH_CATEGORY_MAP.put(RECORD, "SEARCH.CATEGORY.DFILM");
        SEARCH_CATEGORY_MAP.put(CAR, "SEARCH.CATEGORY.CAR");
        SEARCH_CATEGORY_MAP.put(CAIJING, "SEARCH.CATEGORY.CAIJING");
        SEARCH_CATEGORY_MAP.put(TRAVELING, "SEARCH.CATEGORY.TRAVEL");
        SEARCH_CATEGORY_MAP.put(EDUCATION, "SEARCH.CATEGORY.EDUCATION");
    }

}
