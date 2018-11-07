package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

public class BroadcastWhiteList {

    private Long id;
    private Long object_id;
    private Integer level;
    private Integer type;
    private Date create_time;
    private String reason;
    private String create_by;
    private Integer broadcast;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObject_id() {
        return this.object_id;
    }

    public void setObject_id(Long object_id) {
        this.object_id = object_id;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreate_by() {
        return this.create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Integer getBroadcast() {
        return this.broadcast;
    }

    public void setBroadcast(Integer broadcast) {
        this.broadcast = broadcast;
    }

    @Override
    public String toString() {
        return "BroadcastWhiteList{" + "id=" + id + ", object_id=" + object_id + ", level=" + level + ", type=" + type
                + ", create_time=" + create_time + ", reason='" + reason + '\'' + ", create_by='" + create_by + '\''
                + ", broadcast=" + broadcast + '}';
    }
}
