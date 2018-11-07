package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * special stream video model
 */
public class SpecialVideoMysqlTable {
    private final Logger log = LoggerFactory.getLogger(SpecialVideoMysqlTable.class);
    /**
     * auto increment id
     */
    private String id;

    /**
     * video id
     */
    private String videoid;

    /**
     * album id
     */
    private String pid;

    /**
     * play streams
     */
    private String play_streams;

    /**
     * mid and play streams info
     */
    private String mid_streams;

    /**
     * the video update_time
     */
    private String update_time;

    /**
     * the type of special stream video
     */
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPlay_streams() {
        return play_streams;
    }

    public void setPlay_streams(String play_streams) {
        this.play_streams = play_streams;
    }

    public String getMid_streams() {
        return mid_streams;
    }

    public void setMid_streams(String mid_streams) {
        this.mid_streams = mid_streams;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getMidByStream(String stream) {
        if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(stream)
                && this.play_streams.indexOf(LetvStreamCommonConstants.CODE_NAME_1080p3m) > -1
                && !LetvStreamCommonConstants.contains(StringUtil.toLong(pid))) {// 如果是1080p6m,并且有3m的则取3m
            stream = LetvStreamCommonConstants.CODE_NAME_1080p3m;
        }
        Long mid = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (this.mid_streams != null) {
                Map<String, String> map = mapper.readValue(this.mid_streams, new TypeReference<Map<String, String>>() {
                });
                if (map != null) {
                    Set<Entry<String, String>> entrySet = map.entrySet();
                    for (Entry<String, String> e : entrySet) {
                        String id = e.getKey();
                        String value = e.getValue();
                        if (value == null) {
                            break;
                        }
                        String[] streams = e.getValue().split(",");
                        for (String s : streams) {
                            if (s.equalsIgnoreCase(stream)) {
                                mid = new Long(id);
                                break;
                            }
                        }
                        if (mid != null) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("getMidByStream_ error", e.getMessage(), e);
        }
        return mid;
    }

}
