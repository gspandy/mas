package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LiveConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveProgram;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveRoomChannel;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class LiveCacheDao extends BaseCacheDao {

    /**
     * 获取体育直播缓存数据，主要用于体育频道里的体育直播大厅展示数据
     * @param broadcastId
     * @return
     */
    public List<LiveProgram> getSportsLivePrograms(Integer broadcastId) {
        String key = CacheContentConstants.CHANNEL_SPORTS_LIVE_DATA + broadcastId;
        return this.cacheTemplate.get(key, new LetvTypeReference<List<LiveProgram>>() {
        });
    }

    /**
     * 设置体育直播缓存数据，主要用于体育频道里的体育直播大厅展示数据
     * @param broadcastId
     * @param liveList
     */
    public void setSportsLivePrograms(Integer broadcastId, List<LiveProgram> liveList) {
        String key = CacheContentConstants.CHANNEL_SPORTS_LIVE_DATA + broadcastId;
        this.cacheTemplate.set(key, liveList, CommonConstants.SECONDS_OF_1_MONTH);
    }

    /**
     * 删除体育直播缓存，避免体育频道里一直展示历史数据而直播大厅无该场直播
     * @param broadcastId
     */
    public void deleteSportsLivePrograms(Integer broadcastId) {
        String key = CacheContentConstants.CHANNEL_SPORTS_LIVE_DATA + broadcastId;
        this.cacheTemplate.delete(key);
    }

    /**
     * 将直播大厅数据添加到缓存
     * @param broadcastId
     * @param channelId
     * @param liveDto
     */
    public void setLiveRoomLiveDto(Integer broadcastId, Integer channelId, LiveDto liveDto) {
        this.cacheTemplate.set(CacheContentConstants.E_LIVE_DTO + broadcastId + "-" + channelId, liveDto);
    }

    /**
     * 兼容美国版直播缓存
     * @param broadcastId
     * @param channelId
     * @param liveDto
     */
    public void setLiveRoomLiveDto4Us(String broadcastKey, Integer channelId, LiveDto liveDto) {
        this.cacheTemplate.set(CacheContentConstants.E_LIVE_DTO + broadcastKey + "-" + channelId, liveDto);
    }

    /**
     * 从缓存中获取直播大厅数据
     * @param broadcastId
     * @param channelId
     * @return
     */
    public LiveDto getLiveRoomLiveDto(String broadcastId, Integer channelId) {
        return this.cacheTemplate.get(CacheContentConstants.E_LIVE_DTO + broadcastId + "-" + channelId, LiveDto.class);
    }

    /**
     * 根据一些直播id拼接得到的key获取缓存中的直播数据
     * @param liveKeys
     * @return
     */
    public Map<String, LiveProgram> getLiveProgramByLiveIds(String[] liveKeys) {
        if (liveKeys == null || liveKeys.length == 0) {
            return null;
        }
        return this.cacheTemplate.mget(Arrays.asList(liveKeys), LiveProgram.class);
    }

    /**
     * 批量将直播数据添加到缓存中
     * @param obj
     */
    public void setLiveProgramsToCache(Map<String, Object> obj, Integer duration) {
        if (duration == null) {
            this.cacheTemplate.mset(obj, CommonConstants.SECONDS_OF_1_MONTH);
        } else {
            this.cacheTemplate.mset(obj, duration);
        }
    }

    public void setLiveProgramsToCache2(Map<String, LiveProgram> obj) {
        this.cacheTemplate.mset(obj, CommonConstants.SECONDS_OF_1_YEAR);
    }

    /**
     * 从缓存中获取直播大厅刷新时间
     * @return
     */
    public Long getLiveRoomRefreshTime() {
        return this.cacheTemplate.get(CacheContentConstants.LIVE_LIST_REAL_TIME_DATA_REFRESH_INTERVAL, Long.class);
    }

    /**
     * 获取直播大厅数据
     * @return
     */
    public List<LiveRoomChannel> getLiveRoomChannelList() {
        return this.cacheTemplate.get(CacheContentConstants.LIVE_ROOM_CHANNEL_CACHE_KEY,
                new LetvTypeReference<List<LiveRoomChannel>>() {
                });
    }

    /**
     * 将直播大厅数据添加到缓存中
     * @param liveRoomChannelList
     */
    public void setLiveRoomChannelList(List<LiveRoomChannel> liveRoomChannelList) {
        this.cacheTemplate.set(CacheContentConstants.LIVE_ROOM_CHANNEL_CACHE_KEY, liveRoomChannelList);
    }

    /**
     * 获取直播缓存
     * @param liveId
     * @return
     */
    public LiveProgram getLiveData(String liveId, String splatId, Integer broadcastId) {
        if (broadcastId == null) {
            broadcastId = CommonConstants.LETV;
        }
        if (StringUtil.isBlank(splatId)) {
            splatId = LiveConstants.SPLITID.get("ROOM");
        }
        StringBuilder key = new StringBuilder(CacheContentConstants.E_LIVE_TOPIC_LIST);
        key.append(broadcastId).append("-").append(splatId).append("-").append(liveId);
        return this.cacheTemplate.get(key.toString(), LiveProgram.class);
    }

}
