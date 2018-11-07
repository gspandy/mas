package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

public class MmsHotAlbumMonitorMysqlTable {

    private Integer id;

    // 专辑Id
    private Integer album_id;

    // 专辑名称
    private String album_name;

    // 监控起始时间
    private String start_time;

    // 监控结束时间
    private String end_time;

    // 每周几监控
    private String weekend_days;

    // 监控的时间点
    private String monitor_time;

    // 是否可以延迟检测
    private Integer is_delay;// 0 不可延迟 1 可以延迟

    // 延迟时间(分钟)
    private Integer delay_time;

    // 是否删除
    private Integer deleted;// 0 不可删除 1 可删除

    // 创建时间
    private String create_time;

    // 修改时间
    private String update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(Integer album_id) {
        this.album_id = album_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getWeekend_days() {
        return weekend_days;
    }

    public void setWeekend_days(String weekend_days) {
        this.weekend_days = weekend_days;
    }

    public String getMonitor_time() {
        return monitor_time;
    }

    public void setMonitor_time(String monitor_time) {
        this.monitor_time = monitor_time;
    }

    public Integer getIs_delay() {
        return is_delay;
    }

    public void setIs_delay(Integer is_delay) {
        this.is_delay = is_delay;
    }

    public Integer getDelay_time() {
        return delay_time;
    }

    public void setDelay_time(Integer delay_time) {
        this.delay_time = delay_time;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }
}
