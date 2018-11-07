package com.letv.mas.caller.iptv.tvproxy.common.util;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LiveUtil {

    private final static Logger logger = LoggerFactory.getLogger(LiveUtil.class);

    public static final ConcurrentMap<String, LiveDto> liveCache = new ConcurrentHashMap<String, LiveDto>();

    /**
     * 从内存中获得直播列表
     * @param broadcastId
     * @param channelId
     * @return
     */
    public static LiveDto getLiveFromMemory(Integer broadcastId, Integer channelId, CommonParam commonParam) {
        // 默认取播控为0，频道id为797的数据
        if (broadcastId == null) {
            broadcastId = 0;
        }
        if (channelId == null) {
            channelId = 797;
        }
        String key = "";
        if (TerminalUtil.isLetvUs(commonParam)) {
            key = CacheContentConstants.E_LIVE_DTO + StringUtils.trimToEmpty(commonParam.getLangcode()) + "-"
                    + broadcastId + "-" + channelId;
        } else {
            key = CacheContentConstants.E_LIVE_DTO + broadcastId + "-" + channelId;
        }
        LiveDto live = liveCache.get(key);
        logger.info("liveCache:op[get]key[" + key + "]");

        return live;
    }

    /**
     * 把LIVE数据放入内存
     * @param broadcastId
     * @param channelId
     * @param liveDto
     */
    public static void setLiveToMemory(Integer broadcastId, Integer channelId, CommonParam commonParam, LiveDto liveDto) {
        if (liveDto == null) {
            return;
        }
        // 默认放播控为0，频道id为797的数据
        if (broadcastId == null) {
            broadcastId = 0;
        }
        if (channelId == null) {
            channelId = 797;
        }
        String key = "";
        if (TerminalUtil.isLetvUs(commonParam)) {
            key = CacheContentConstants.E_LIVE_DTO + StringUtils.trimToEmpty(commonParam.getLangcode()) + "-"
                    + broadcastId + "-" + channelId;
        } else {
            key = CacheContentConstants.E_LIVE_DTO + broadcastId + "-" + channelId;
        }
        LiveDto live = liveCache.putIfAbsent(key, liveDto);
        if (live != null) {
            liveCache.replace(key, liveDto);
            logger.info("liveCache:op[update]key[" + key + "]");
        } else {
            logger.info("liveCache:op[save]key[" + key + "]");
        }
    }

    /**
     * 将直播id组装成格式形如"E_LiveTopicList - 播控平台id - 子平台id - 直播id"的字段
     * @param id
     *            直播ID
     * @param broadcastId
     *            播控方，0--Letv，1--CNTV，2--CIBN，3--WASU
     * @param splatId
     *            直播业务平台编号 ，1007--TV直播（包含直播大厅和直播专题），1034--直播SDK
     * @return
     */
    public static String buildLiveKey(String id, int broadcastId, String splatId) {
        StringBuilder s = new StringBuilder();
        s.append(CacheContentConstants.E_LIVE_TOPIC_LIST);
        s.append(broadcastId);
        s.append("-");
        s.append(splatId);
        s.append("-");
        s.append(id);
        return s.toString();
    }

    /**
     * 老方法，解决线上bug临时修改，2.2.32上线，2.2.33删除
     * @param id
     *            直播ID
     * @param broadcastId
     *            播控方，0--Letv，1--CNTV，2--CIBN，3--WASU
     * @param splatId
     *            直播业务平台编号 ，1007--TV直播（包含直播大厅和直播专题），1034--直播SDK
     * @return
     */
    @Deprecated
    public static String buildLiveKeyOld(String id, int broadcastId, String splatId) {
        StringBuilder s = new StringBuilder();
        s.append(CacheContentConstants.E_LIVE_TOPIC_LIST);
        s.append(broadcastId);
        s.append(splatId);
        s.append(id);
        return s.toString();
    }

    /**
     * 构造直播大厅中过滤直播数据时所需的相关key，key的规则为{terminalSeries}:{broadcastId}:{splatId}；
     * 通过该key可以（比如从Map中）拿到对应的需要过滤掉的直播id列表；
     * 2015-09-23，李宇春演唱会特殊需求，需要在不同设备上过滤数据；因为定时刷出来的直播大厅数据，仅考虑了播控方、splatId，
     * 而没有考虑设备信息，所以无法在缓存的直播大厅数据中修改，而是在最后返回时才筛选；过滤数据的配置放在配置文件中（静态文件服务器上），
     * 启动加载或手动刷新；
     * @param terminalSeries
     * @param broadcastId
     * @param splatId
     * @return
     */
    public static String buildLiveRoomFilterKey(String terminalSeries, int broadcastId, String splatId) {
        StringBuilder s = new StringBuilder();
        s.append(StringUtils.trimToEmpty(terminalSeries).toLowerCase().replaceAll(" ", "_")); // 注意这里特殊处理了terminalSeries中的空格
        s.append("_");
        s.append(broadcastId);
        s.append("_");
        s.append(splatId);
        return s.toString();
    }

    /**
     * 构造直播大厅中过滤直播数据时，过滤数据的规范表达式，方便查找数据，格式如",{liveId},"；
     * 业务背景参见buildLiveRoomFilterKey
     * @param liveId
     * @return
     */
    public static String buildLiveRoomFilterLiveKey(String liveId) {
        StringBuilder s = new StringBuilder();
        s.append(",");
        s.append(StringUtils.trimToEmpty(liveId));
        s.append(",");
        return s.toString();
    }

    /**
     * 直播（轮播、卫视）投屏缓存key组装方法，格式为E_2_LC_{splatid}_{sourceType}_{id}
     * @param id
     * @param sourceType
     *            投屏资源类型，1--点播，2--直播，3--轮播，4--卫视台，在投屏点播逻辑中，该值可不传，不影响业务
     * @param splatid
     * @return
     */
    public static String buildToupingLiveChannelKey(String id, Integer sourceType, String splatid) {
        StringBuilder keyBuilder = new StringBuilder(CacheContentConstants.E_2_LIVE_CHANNEL_KEY_PREFIX);
        keyBuilder.append(splatid).append("_").append(sourceType).append("_").append(id);
        return keyBuilder.toString();
    }

}
