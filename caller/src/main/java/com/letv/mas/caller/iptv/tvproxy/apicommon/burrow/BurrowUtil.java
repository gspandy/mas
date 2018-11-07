package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.CibnSdkDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.GoliveDto.GoliveAlbumBurrowDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.LiveSdkDto.TVLiveBurrowDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.LiveSdkDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.SportsDto.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.PanoDto.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.AppStoreDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.GameSdkDto.GameCenterBurrowDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.MusicDto.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.TVDto.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.WasuDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.IconConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.JumpData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.BurrowUtilsV3;
import com.letv.mas.caller.iptv.tvproxy.common.util.JsonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BurrowUtil {
    private final static Logger log = LoggerFactory.getLogger(BurrowUtil.class);

    private static Map<String, AbstractBurrow> burrows = new HashMap<String, AbstractBurrow>();
    private static BurrowUtil burrowUtil = new BurrowUtil();

    static {
        burrows.put("search_cibn", new PanoSearchBurrow(burrowUtil, new PanoDesktopBurrow(burrowUtil)));
        burrows.put("search_desk", new PanoDesktopBurrow(burrowUtil));
        burrows.put("cinema_desk", new GoliveBurrow(burrowUtil));
        burrows.put(TerminalCommonConstant.TERMINAL_APPLICATION_LEVIEW, new LeviewBurrow(burrowUtil));
        burrows.put(TerminalCommonConstant.TERMINAL_APPLICATION_CIBN_LEVIEW, new CibnLeviewBurrow(burrowUtil));
        burrows.put(TerminalCommonConstant.TERMINAL_APPLICATION_WASU_LEVIEW, new WasuLeviewBurrow(burrowUtil));
        burrows.put("child_cibn", new ChildBurrow(burrowUtil));
        burrows.put("lekids_launcher", new ChildBurrow(burrowUtil));
        burrows.put("letv", new TvBurrow(burrowUtil));
        burrows.put("media_cibn", new TvBurrow(burrowUtil));
    }

    public static BaseData buildBurrow(BaseData data, CommonParam commonParam) {
        AbstractBurrow abstractBurrow = burrows.get(commonParam.getTerminalApplication() != null ? commonParam
                .getTerminalApplication().toLowerCase() : null);
        if (abstractBurrow == null) {
            abstractBurrow = burrows.get("media_cibn");
        }
        return abstractBurrow.burrowObj(data, commonParam);
    }

    /**
     * 超级影视--专辑
     * @param data
     * @param tVAlbumBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowTVAlbum(BaseData data, TVAlbumBurrowDto tVAlbumBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_TV_ALBUM, tVAlbumBurrowDto, commonParam);
    }

    /**
     * 超级影视--视频
     * @param data
     * @param tVVideoBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowTVVideo(BaseData data, TVVideoBurrowDto tVVideoBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_TV_VIDEO, tVVideoBurrowDto, commonParam);
    }

    /**
     * 超级影视--专题
     * @param data
     * @param tVSubjectBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowTVSubject(BaseData data, TVSubjectBurrowDto tVSubjectBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_TV_SUBJECT, tVSubjectBurrowDto, commonParam);
    }

    /**
     * 超级影视--容器
     * @param data
     * @param tVContainer
     * @param commonParam
     * @return
     */
    public BaseData burrowTVContainer(BaseData data, TVContainer tVContainer, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_TV_CONTAINER, tVContainer, commonParam);
    }

    /**
     * 超级影视--浏览器
     * @param data
     * @param tVBrowseDto
     * @param commonParam
     * @return
     */
    public BaseData burrowTVBrowse(BaseData data, TVBrowseDto tVBrowseDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_TV_BORROW, tVBrowseDto, commonParam);
    }

    /**
     * 超级影视--直播
     * @param data
     * @param commonParam
     * @return
     */
    public BaseData burrowTVLive(BaseData data, TVLive tVLive, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_TV_LIVE, tVLive, commonParam);
    }

    /**
     * 儿童--专辑
     * @param data
     * @param tVAlbumBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowChildAlbum(BaseData data, TVAlbumBurrowDto tVAlbumBurrowDto, CommonParam commonParam) {
        tVAlbumBurrowDto.setModel(1);// 儿童传1
        return buildBurrowV2(data, BurrowObj.LE_CHILD_ALBUM, tVAlbumBurrowDto, commonParam);
    }

    /**
     * 儿童--视频
     * @param data
     * @param tVVideoBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowChildVideo(BaseData data, TVVideoBurrowDto tVVideoBurrowDto, CommonParam commonParam) {
        tVVideoBurrowDto.setModel(1);// 儿童传1
        return buildBurrowV2(data, BurrowObj.LE_TV_VIDEO, tVVideoBurrowDto, commonParam);
    }

    /**
     * 儿童--专题
     * @param data
     * @param tVSubjectBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowChildSubject(BaseData data, TVSubjectBurrowDto tVSubjectBurrowDto, CommonParam commonParam) {
        tVSubjectBurrowDto.setModel(1);// 儿童传1
        return buildBurrowV2(data, BurrowObj.LE_CHILD_SUBJECT, tVSubjectBurrowDto, commonParam);
    }

    /**
     * 应用商店
     * @param data
     * @param appStoreBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowAppStore(BaseData data, AppStoreDto.AppStoreBurrowDto appStoreBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_APPSTORE, appStoreBurrowDto, commonParam);
    }

    /**
     * 音乐--单曲
     * @param data
     * @param musicSingleBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowMusicSingle(BaseData data, MusicSingleBurrowDto musicSingleBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_MUSIC_SINGLE, musicSingleBurrowDto, commonParam);
    }

    /**
     * 音乐--专辑
     * @param data
     * @param musicAlbumBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowMusicAlbum(BaseData data, MusicAlbumBurrowDto musicAlbumBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_MUSIC_ALBUM, musicAlbumBurrowDto, commonParam);
    }

    /**
     * 游戏中心
     * @param data
     * @param gameCenterBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowGameCenter(BaseData data, GameCenterBurrowDto gameCenterBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_GAME_ALBUM, gameCenterBurrowDto, commonParam);
    }

    /**
     * 万象-专辑详情
     * @param data
     * @param panoAlbumBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowPanoAlbum(BaseData data, PanoAlbumBurrowDto panoAlbumBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_PANO_ALBUM, panoAlbumBurrowDto, commonParam);
    }

    /**
     * 万象--明星详情
     * @param data
     * @param panoStarBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowPanoStar(BaseData data, PanoStarBurrowDto panoStarBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_PANO_STAR, panoStarBurrowDto, commonParam);
    }

    /**
     * 体育--视频
     * @param data
     * @param sportsVideoBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowSportsVideo(BaseData data, SportsVideoBurrowDto sportsVideoBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_SPORTS_VIDEO, sportsVideoBurrowDto, commonParam);
    }

    /**
     * 体育--专题
     * @param data
     * @param sportsSubjectBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowSportsSubject(BaseData data, SportsSubjectBurrowDto sportsSubjectBurrowDto,
            CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_SPORTS_SUBJECT, sportsSubjectBurrowDto, commonParam);
    }

    /**
     * TVLIVE--轮播
     * @param data
     * @param tVLiveBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowLiveLunbo(BaseData data, TVLiveBurrowDto tVLiveBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_LIVE_CHANNEL, tVLiveBurrowDto, commonParam);
    }

    /**
     * TVLIVE--卫视台
     * @param data
     * @param tVLiveBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowLiveWeishi(BaseData data, LiveSdkDto.TVLiveBurrowDto tVLiveBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_LIVE_WEISHI, tVLiveBurrowDto, commonParam);
    }

    /**
     * TVLIVE--直播
     * @param data
     * @param tVLiveBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowLive(BaseData data, TVLiveBurrowDto tVLiveBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_LIVE_LIVE, tVLiveBurrowDto, commonParam);
    }

    /**
     * GOLIVE--专辑详情
     * @param data
     * @param goliveAlbumBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowGoliveAlbum(BaseData data, GoliveAlbumBurrowDto goliveAlbumBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.LE_GOLIVE_ALBUM, goliveAlbumBurrowDto, commonParam);
    }

    /**
     * 跳转CIBN--专辑
     * @param data
     * @param cibnAlbumBurrowDto
     * @param commonParam
     * @return
     */
    public BaseData burrowCIBNAlbum(BaseData data, CibnSdkDto.CibnAlbumBurrowDto cibnAlbumBurrowDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.CIBN_ALBUM, cibnAlbumBurrowDto, commonParam);
    }

    /**
     * 跳转Wasu--专辑
     * @param data
     * @param wasuDto
     * @param commonParam
     * @return
     */
    public BaseData burrowWasuAlbum(BaseData data, WasuDto wasuDto, CommonParam commonParam) {
        return buildBurrowV2(data, BurrowObj.WASU_ALBUM, wasuDto, commonParam);
    }

    /**
     * data为要封装的原始对象，BurrowObj为跳转对象，param为模板参数，param如果为null则默认模板参数为原始对象data
     * @param data
     *            原始对象
     * @param burrow
     *            跳转对象
     * @param param
     *            模板参数
     * @param commonParam
     * @return
     */
    // @SuppressWarnings({ "rawtypes", "unchecked" })
    // protected static BaseData buildBurrowV2(BaseData data, BurrowObj burrow,
    // Object param, CommonParam commonParam) {
    // String logPrefix = "buildBurrow_";
    // String value = null;
    // JumpData jumpData = null;
    // Map paramMap = null;
    // try {
    // if (param == null) {
    // paramMap = JsonUtil.parse(JsonUtil.parseToString(data), Map.class);
    // } else {
    // if (param instanceof String) {
    // paramMap = JsonUtil.parse(param.toString(), Map.class);
    // } else {
    // paramMap = JsonUtil.parse(JsonUtil.parseToString(param), Map.class);
    // }
    // }
    // // 判断应用内跳外跳
    // if (paramMap != null) {
    // if (commonParam.getTerminalApplication() != null &&
    // StringUtil.isNotBlank(burrow.getAppName())
    // &&
    // burrow.getAppName().toLowerCase().indexOf(commonParam.getTerminalApplication())
    // == -1) {
    // paramMap.put("0", DataConstant.DATA_TYPE_EXT_APP);
    // } else {
    // paramMap.put("0", DataConstant.DATA_TYPE_BLANK);
    // }
    // }
    // value = BurrowUtils.get(burrow.getKey(), paramMap);
    // jumpData = JsonUtil.parse(value, JumpData.class);
    // // 无jump对象或者参数列表为空直接返回空对象
    // if (jumpData == null) {
    // return null;
    // }
    // // 兼容老的跳转逻辑
    // if (commonParam.getTerminalApplication() != null
    // &&
    // (TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(commonParam
    // .getTerminalApplication())
    // ||
    // TerminalCommonConstant.TERMINAL_APPLICATION_LETV.equalsIgnoreCase(commonParam
    // .getTerminalApplication())
    // ||
    // TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
    // .getTerminalApplication()) ||
    // TerminalCommonConstant.TERMINAL_APPLICATION_LE
    // .equalsIgnoreCase(commonParam.getTerminalApplication()))) {
    // data.setJump(JsonUtil.parse(JsonUtil.parseToString(jumpData.getValue()),
    // JumpData.class));
    // } else {
    // data.setJump(jumpData);
    // }
    // } catch (Exception e) {
    // log.error(logPrefix + "value: " + value + " jumpData is null?" +
    // (jumpData == null) + e);
    // }
    // return data;
    // }
    /**
     * data为要封装的原始对象，BurrowObj为跳转对象，param为模板参数，param如果为null则默认模板参数为原始对象data
     * @param data
     *            原始对象
     * @param burrow
     *            跳转对象
     * @param param
     *            模板参数
     * @param commonParam
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected static BaseData buildBurrowV2(BaseData data, BurrowObj burrow, Object param, CommonParam commonParam) {
        String logPrefix = "buildBurrow_";
        String value = null;
        JumpData jumpData = null;
        if (param == null) {
            param = data;
        }
        try {
            // 兼容一下老得配置方式
            if (param instanceof String) {
                Map tmpMap = new HashMap();
                // String str = "{\"value\":" + param.toString() + "}";
                tmpMap.put("value", JsonUtil.parse(param.toString(), Map.class));
                // tmpMap = JsonUtil.parse(param.toString(), Map.class);
                param = tmpMap;

            }
            Map resultMap = BurrowUtilsV3.get(burrow.getKey(), param);
            if (resultMap == null) {
                log.error(logPrefix + "obtain template map Exception:");
                return null;
            }
            /**
             * 判断应用内跳外跳,封装相应的跳转对象(应用内跳二层结构，应用外跳三层结构)
             */
            jumpData = new JumpData();
            if (commonParam.getTerminalApplication() != null && StringUtil.isNotBlank(burrow.getAppName())
                    && burrow.getAppName().toLowerCase().indexOf(commonParam.getTerminalApplication()) == -1) {
                jumpData.setType(DataConstant.DATA_TYPE_EXT_APP);
                jumpData.setValue(resultMap.get("value"));
                jumpData.setExtend(resultMap.get("extend"));
            } else {
                Map tmpMap = (Map) resultMap.get("value");
                if (tmpMap != null) {
                    jumpData.setValue(tmpMap.get("value"));
                    int type = tmpMap.get("type") == null ? 0 : (Integer) tmpMap.get("type");
                    jumpData.setType(type);
                } else {
                    log.error(logPrefix + "obtain inner value Exception:");
                }
            }
            data.setJump(jumpData);
        } catch (Exception e) {
            log.error(logPrefix + "value: " + value + " jumpData is null?" + (jumpData == null), e);
        }
        return data;
    }

    /**
     * 打洞模板
     * @author liupeng5
     */
    public enum BurrowObj {
        LE_TV_BASEBLOCK("1-baseblock", "超级影视-baseblock通用模板", "letv|media_cibn"),
        LE_TV_STATICBLOCK("1-staticblock", "超级影视-staticblock通用模板", "letv|media_cibn"),
        LE_TV_ALBUM("1-1", "超级影视-专辑", "letv|media_cibn"),
        LE_TV_VIDEO("1-2", "超级影视-视频", "letv|media_cibn"),
        LE_TV_SUBJECT("1-3", "超级影视-专题", "letv|media_cibn"),
        LE_TV_CHANNEL("1-6", "超级影视-频道", "letv|media_cibn"),
        LE_TV_BORROW("1-9", "超级影视-浏览器", "letv|media_cibn"),
        LE_TV_PRELIVE("1-22", "超级影视-超前院线", "letv|media_cibn"),
        LE_TV_CONTAINER("1-28", "超级影视-容器", "letv|media_cibn"),
        LE_TV_LIVE("1-4", "超级影视-直播", "letv|media_cibn"),
        LE_TV_PAGE("1-15", "超级影视-页面", "letv|media_cibn"),
        LE_TV_STAR("1-27", "超级影视-明星详情", "letv|media_cibn"),
        LE_TV_CHANNELLIST("1-45", "超级影视-频道墙", "letv|media_cibn"),
        LE_TV_MINE("1-48", "超级影视-我的", "letv|media_cibn"),
        LE_TV_INDEX("1-47", "超级影视-首页", "letv|media_cibn"),
        LE_TV_LIVEROOM("1-46", "超级影视-直播大厅", "letv|media_cibn"),
        LE_TV_GUIDE("1-42", "超级影视-乐导视", "letv|media_cibn"),
        LE_TV_MUSIC("1-20", "超级影视-音乐台", "letv|media_cibn"),

        LE_APPSTORE_COMMON("2-0", "应用商店通用模板", "appstore"),
        LE_APPSTORE("2-1", "调起应用商店", "appstore"),
        LE_APPSTORE_SUBJECT("2-2", "应用商店专题", "appstore"),

        LE_MUSIC_SINGLE("3-1", "音乐-单曲", "music"),
        LE_MUSIC_ALBUM("3-2", "音乐-专辑", "music"),

        LE_CHILD_BASEBLOCK("5-baseblock", "乐视儿童-baseblock通用模板", "letv|child_cibn"),
        LE_CHILD_STATICBLOCK("5-staticblock", "乐视儿童-staticblock通用模板", "letv|child_cibn"),
        LE_CHILD_ALBUM("5-1", "乐视儿童-专辑", "letv|child_cibn"),
        LE_CHILD_VIDEO("5-2", "超级影视-视频", "letv|child_cibn"),
        LE_CHILD_SUBJECT("5-3", "超级影视-专题", "letv|child_cibn"),
        LE_CHILD_CHANNEL("5-6", "超级影视-频道", "letv|child_cibn"),
        LE_CHILD_BORROW("5-9", "超级影视-浏览器", "letv|child_cibn"),
        LE_CHILD_PRELIVE("5-22", "超级影视-超前院线", "letv|child_cibn"),
        LE_CHILD_CONTAINER("5-28", "超级影视-容器", "letv|child_cibn"),
        LE_CHILD_LIVE("5-4", "超级影视-直播", "letv|child_cibn"),
        LE_CHILD_PAGE("5-15", "超级影视-页面", "letv|child_cibn"),
        LE_CHILD_STAR("5-27", "超级影视-明星详情", "letv|child_cibn"),
        LE_CHILD_CHANNELLIST("5-45", "超级影视-频道墙", "letv|child_cibn"),
        LE_CHILD_MINE("5-48", "超级影视-我的", "letv|child_cibn"),
        LE_CHILD_INDEX("5-47", "超级影视-首页", "letv|child_cibn"),
        LE_CHILD_LIVEROOM("5-46", "超级影视-直播大厅", "letv|child_cibn"),
        LE_CHILD_GUIDE("5-42", "超级影视-乐导视", "letv|child_cibn"),

        LE_PANO_ALBUM("6-1", "乐搜-专辑详情页", "search_cibn"),
        LE_PANO_STAR("6-2", "乐搜-明星详情页", "search_cibn"),
        LE_PANO_MINE("6-3", "乐搜-首页", "search_cibn"),
        LE_PANO_LIVEDETAIL("6-4", "乐搜-直播详情页", "search_cibn"),

        LE_SPORTS_VIDEO("7-1", "体育-点播", "sport"),
        LE_SPORTS_SUBJECT("7-2", "体育-专题", "sport"),
        LE_SPORTS_LIVE("7-3", "体育-直播", "sport"),

        LE_LIVE_LIVE("8-1", "live-直播", "live"),
        LE_LIVE_CHANNEL("8-2", "live-轮播", "live"),
        LE_LIVE_WEISHI("8-3", "live-卫视台", "live"),

        LE_GAME_ALBUM("9-1", "游戏中心专辑详情页", "game"),
        LE_GAME_START("9-2", "启动游戏", "game"),

        LE_GOLIVE_COMMON("10-0", "golive通用结构", "golive"),
        LE_GOLIVE_ALBUM("10-1", "golive专辑详情页", "golive"),

        LE_SHOP_COMMON("11-0", "购物通用结构", "shop"),
        LE_SHOP_ITEMDETAIL("11-1", "购物商品详情", "shop"),

        CIBN_COMMON("12-0", "cibn通用结构", "cibn"),
        CIBN_INDEX("12-1", "cibn首页", "cibn"),
        CIBN_ALBUM("12-2", "cibn专辑", "cibn"),
        CIBN_PLAY_LOG("12-3", "cibn播放记录", "cibn"),
        CIBN_VIDEO("12-4", "cibn视频", "cibn"),
        CIBN_SEARCH_INDEX("12-5", "cibn搜索首页", "cibn"),
        CIBN_SUBJECT("12-6", "cibn专题", "cibn"),
        CIBN_CHANNEL_LIST("12-7", "cibn频道列表", "cibn"),
        CIBN_VIP_TAB("12-8", "cibn会员页", "cibn"),
        CIBN_CHANNEL_PAGE("12-9", "cibn活动页", "cibn"),
        CIBN_SERIES_EPISODE("12-10", "cibn某一剧集", "cibn"),

        WASU_COMMON("13-0", "wasu通用结构", "wasu"),
        WASU_ALBUM("13-1", "wasu专辑", "wasu"),
        WASU_VIDEO("13-2", "wasu单视频", "wasu"),
        WASU_INDEX_VIP("13-3", "wasu首页会员", "wasu"),
        WASU_FAV("13-4", "wasu收藏", "wasu"),
        WASU_PLAY_LOG("13-5", "wasu播放记录", "wasu"),
        WASU_SERIES_EPISODE("13-6", "wasu某一剧集", "wasu");

        private String key;
        private String name;
        private String appName;

        BurrowObj(String key, String name, String appName) {
            this.key = key;
            this.name = name;
            this.appName = appName;
        }

        public String getKey() {
            return this.key;
        }

        public String getName() {
            return this.name;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

    }

    /**
     * app包名，用于打洞时from参数，通用
     * @author chenjian
     */
    public enum BurrowPackageName {
        PANO_SEARCH("com.letv.leso"), // 万象搜索
        PANO_DESKTOP("com.stv.plugin.search");// 万象桌面

        private String name;

        BurrowPackageName(String name) {
            this.name = name;
        }

        public String getFrom() {
            return this.name;
        }
    }

    /**
     * 游戏中心打洞 GcResource 参数列表
     * http://wiki.letv.cn/pages/viewpage.action?pageId=54272236 游戏中心附件
     * @author chenjian
     */
    public enum GameCenterGcResource {
        PANO(10241, "万象搜索与桌面共用"),
        LEVIEW(10242, "乐见桌面"),
        CHILD(4096, "儿童"),
        LETV(10243, "乐视视频");
        private int gcResource;
        private String remark;

        GameCenterGcResource(int gcResource, String remark) {
            this.gcResource = gcResource;
            this.remark = remark;
        }

        public int getGcResource() {
            return this.gcResource;
        }

    }

    /**
     * 乐搜打洞 resource 参数列表
     * http://wiki.letv.cn/pages/viewpage.action?pageId=54283863
     * @author chenjian
     */
    public enum BurrowPanoResource {
        PANO_DESKTOP(1, "万象桌面"),
        TV(2, "乐视视频"),
        LEVIEW(3, "乐见桌面"),
        PANO_SEARCH(14, "万象搜索");

        private int resource;
        private String remark;

        BurrowPanoResource(int resource, String remark) {
            this.resource = resource;
            this.remark = remark;
        }

        public int getResource() {
            return this.resource;
        }
    }

    /**
     * 超级影视打洞(儿童共用) resource 参数列表
     * http://wiki.letv.cn/pages/viewpage.action?pageId=53164256
     * @author chenjian
     */
    public enum BurrowTVResource {
        PANO_DESKTOP(1, "万象桌面"),
        PANO_SEARCH(2, "万象搜索"),
        GOLIVE(16, "同步院线");

        private int resource;
        private String remark;

        BurrowTVResource(int resource, String remark) {
            this.resource = resource;
            this.remark = remark;
        }

        public int getResource() {
            return this.resource;
        }
    }

    /**
     * icon角标类型
     * @param src
     * @param ifCharge
     * @param stream
     * @param cid
     * @return
     */
    public static String getIconType(Integer src, String ifCharge, List<Stream> streams, Integer cid) {
        String iconType = IconConstants.ICON_TYPE_NOICON;
        if (StringUtil.isNotBlank(ifCharge) && "1".equals(ifCharge)) {// 会员
            iconType = IconConstants.ICON_TYPE_VIP;
        } else if (streams != null) {// 码流
            for (Stream stream : streams) {
                if (stream.getCode().toLowerCase().indexOf("4k") > -1) {
                    iconType = IconConstants.ICON_TYPE_4K;
                } else if (stream.getCode().toLowerCase().indexOf("1080p") > -1) {
                    iconType = IconConstants.ICON_TYPE_1080P;
                } else if (stream.getCode().toLowerCase().indexOf("3d") > -1) {
                    iconType = IconConstants.ICON_TYPE_3D;
                } else if (stream.getCode().toLowerCase().indexOf("db") > -1) {
                    iconType = IconConstants.ICON_TYPE_DU;
                }
            }
        } else if (cid != null) {// 视频分类
            iconType = IconConstants.ICON_TYPE_CID;
        }
        return iconType;
    }

    /**
     * icon角标类型
     * @param src
     * @param ifCharge
     * @param stream
     * @param cid
     * @return
     */
    @Deprecated
    public static String getThridPartyIconType(Integer src, String ifCharge, List<Stream> streams, Integer cid) {
        String iconType = IconConstants.ICON_TYPE_NOICON;
        if (StringUtil.isNotBlank(ifCharge) && "1".equals(ifCharge)) {// 会员
            iconType = IconConstants.ICON_TYPE_VIP;
        } else if (streams != null) {// 码流
            for (Stream stream : streams) {
                if (stream.getCode().toLowerCase().indexOf("4k") > -1) {
                    iconType = IconConstants.ICON_TYPE_4K;
                } else if (stream.getCode().toLowerCase().indexOf("1080p") > -1) {
                    iconType = IconConstants.ICON_TYPE_1080P;
                } else if (stream.getCode().toLowerCase().indexOf("3d") > -1) {
                    iconType = IconConstants.ICON_TYPE_3D;
                } else if (stream.getCode().toLowerCase().indexOf("db") > -1) {
                    iconType = IconConstants.ICON_TYPE_DU;
                }
            }
        } else if (cid != null) {// 视频分类
            iconType = IconConstants.ICON_TYPE_CID;
        }
        return iconType;
    }

}
