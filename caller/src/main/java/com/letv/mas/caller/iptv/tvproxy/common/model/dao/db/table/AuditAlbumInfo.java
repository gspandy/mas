package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * Entity class for the table iptv.AUDIT_ALBUM_INFO
 */
public class AuditAlbumInfo {
    private long id;
    private int categoryId;
    private String nameCn;
    private String remark;
    private int tvPlayPlatform;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getTvPlayPlatform() {
        return tvPlayPlatform;
    }

    public void setTvPlayPlatform(int tvPlayPlatform) {
        this.tvPlayPlatform = tvPlayPlatform;
    }

}
