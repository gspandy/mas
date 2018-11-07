package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;

public class WatchingFocusCache {
    // @Protobuf(fieldType = FieldType.STRING,order = 1)
    private String desc;

    // @Protobuf(fieldType = FieldType.INT32,order = 2)
    private Integer id;

    // @Protobuf(fieldType = FieldType.INT64,order = 3)
    private Long time;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = TimeUtil.hhmmss2Timestamp(time);
    }
}
