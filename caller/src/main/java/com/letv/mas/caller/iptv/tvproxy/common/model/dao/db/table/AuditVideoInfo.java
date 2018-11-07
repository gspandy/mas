package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * Entity class for the table iptv.AUDIT_VIDEO_INFO
 */
public class AuditVideoInfo {
    private long id;
    private long pid;
    private int mid;
    private int categoryId;
    private String nameCn;
    private String remark;
    private String storePaths;
    private String playStreams;
    private int cibnContentSync;
    private int wasuContentSync;
    private int tvPlayPlatform;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStorePaths() {
        return storePaths;
    }

    public void setStorePaths(String storePaths) {
        this.storePaths = storePaths;
    }

    public String getPlayStreams() {
        return playStreams;
    }

    public void setPlayStreams(String playStreams) {
        this.playStreams = playStreams;
    }

    public int getCibnContentSync() {
        return cibnContentSync;
    }

    public void setCibnContentSync(int cibnContentSync) {
        this.cibnContentSync = cibnContentSync;
    }

    public int getWasuContentSync() {
        return wasuContentSync;
    }

    public void setWasuContentSync(int wasuContentSync) {
        this.wasuContentSync = wasuContentSync;
    }

    public int getTvPlayPlatform() {
        return tvPlayPlatform;
    }

    public void setTvPlayPlatform(int tvPlayPlatform) {
        this.tvPlayPlatform = tvPlayPlatform;
    }

}
