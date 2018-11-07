package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

public class IptvDictionnaryInfo {
    private Integer id;
    private Integer old_id;
    private String name;
    private Integer type_id;
    private String type_name;
    private Integer fid;
    private String channel_id;
    private Integer property_type;
    private Integer deleted;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOld_id() {
        return this.old_id;
    }

    public void setOld_id(Integer old_id) {
        this.old_id = old_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType_id() {
        return this.type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return this.type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Integer getFid() {
        return this.fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getChannel_id() {
        return this.channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public Integer getProperty_type() {
        return this.property_type;
    }

    public void setProperty_type(Integer property_type) {
        this.property_type = property_type;
    }

    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

}
