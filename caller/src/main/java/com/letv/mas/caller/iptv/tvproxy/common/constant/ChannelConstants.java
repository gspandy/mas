package com.letv.mas.caller.iptv.tvproxy.common.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 频道
 */
public class ChannelConstants {
    private final static Logger log = LoggerFactory.getLogger(ChannelConstants.class);

    /**
     * 杜比
     */
    public static final int DOLBY = 711;

    /**
     * 粤语
     */
    public static final int YUE_YU = 715;

    /**
     * 内容预加载方式
     */
    public final static int CONTENT_PROLOADTYPE_NONE = 1;// 不预加载
    public final static int CONTENT_PROLOADTYPE_PAGE1 = 2;// 第一页
    public final static int CONTENT_PROLOADTYPE_RANDOM = 3;// 随机

    public static final Map<String, String> JUMP_CHANNEL = new HashMap<String, String>();

    static {
        JUMP_CHANNEL.put("1", "631");
        JUMP_CHANNEL.put("2", "619");
        JUMP_CHANNEL.put("3", "660");
        JUMP_CHANNEL.put("4", "719");
        JUMP_CHANNEL.put("5", "639");
        JUMP_CHANNEL.put("9", "668");
        JUMP_CHANNEL.put("11", "647");
        JUMP_CHANNEL.put("14", "703");
        JUMP_CHANNEL.put("16", "675");
        JUMP_CHANNEL.put("20", "700");
        JUMP_CHANNEL.put("22", "692");
        JUMP_CHANNEL.put("23", "705");
        JUMP_CHANNEL.put("34", "684");
    }
    public static final Map<Integer, String> CHILD_AGE_PAGEID = new HashMap<Integer, String>();
    public static String CHANNEL_CHILD__PAGE_ID = ApplicationUtils.get(ApplicationConstants.CHANNEL_PAGEID_CHILD);
    public static String CHANNEL_WALL_PIC_BLOCKID = ApplicationUtils.get(ApplicationConstants.CHANNEL_WALL_PIC_BLOCKID);

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

    public enum CHANNEL {
        index("972", "index"), // 首页
        film("1003", "film"), // , "电影"
        tv("991", "tv"), // , "电视剧"
        entertainment("1032", "entertainment"), // , "娱乐"
        sportinfo("1078", "sportinfo"), // , "体育"
        cartoon("1307", "cartoon"), // , "动漫"
        music("1041", "music"), // , "音乐"
        complex("1019", "complex"), // , "综艺"
        documentary("1051", "documentary"), // , "纪录片"
        live("1097", "live"), // , "LIVE"
        qinzi("1061", "qinzi"), // , "亲子"
        financial("1069", "financial"),
        topic("1098", "topic"), // 专题首页
        my("1011", "my");

        private String bId;
        private String channel;

        private CHANNEL(String bId, String channel) {
            this.bId = bId;
            this.channel = channel;
        }

        public String getChannel() {
            return this.channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getbId() {
            return this.bId;
        }

        public void setbId(String bId) {
            this.bId = bId;
        }

    }

    /**
     * TV2.5 大首页频道列表
     * @author wanglonghu
     */
    public enum CHANNEL_INDEX {
        IMG929X466("973", "index", 0), // "推荐",
        IMG400X300("974", "index", 1), // "推荐",
        TV("975", "tv", 2), // "电视剧",
        FILM("976", "film", 3), // "电影",
        QIN_ZI("977", "qinzi", 4), // "亲子",
        CARTOON("978", "cartoon", 5), // "动漫",
        SPORT("979", "sport", 6), // "体育",
        NBA("980", "nba", 7), // /"NBA",
        COMPLEX("981", "complex", 8), // "综艺",
        ENTERTAINMENT("982", "entertainment", 9), // "娱乐",
        MUSIC("986", "music", 10), // "音乐",
        DOCUMENTARY("987", "documentary", 11), // "纪录片",
        HOTSPOT("988", "hotspot", 12), // "热点",
        TOPIC("989", "topic", 13), // "专题",
        FOUR_K("990", "4k", 14);// "4K",

        private String blockId;
        private String channel;
        private Integer position;// 0、1代表第一屏焦点图，排序时无需修改

        private CHANNEL_INDEX(String blockId, String channel, Integer pos) {
            this.blockId = blockId;
            this.channel = channel;
            this.position = pos;
        }

        public String getBlockId() {
            return this.blockId;
        }

        public void setBlockId(String blockId) {
            this.blockId = blockId;
        }

        public String getChannel() {
            return this.channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public Integer getPosition() {
            return this.position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

    }

    public static final String BLOCK_CONFIG_TYPE_AGE = "age";
    public static final String BLOCK_CONFIG_TYPE_GENDER = "gender";
    public static final String BLOCK_CONFIG_TYPE_WEEK = "week";

    public static final Integer BROADCAST_ID = StringUtil.toInteger(
            ApplicationUtils.get(ApplicationConstants.IPTV_STATIC_BROADCAST_ID_PARAM), 0);

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


}
