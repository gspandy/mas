package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

public class Platform {
    private static final long serialVersionUID = 2760938630640743156L;

    private Long id;
    private String name;
    private String hardware_code;
    private String serial;
    private String platform; // android iphone flash html5
    private String description;
    private Date create_time;
    private String created_by;
    private Date update_time;
    private String updated_by;
    private Integer status; // 逻辑删除状态 0为有效 、1为删除

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHardware_code() {
        return this.hardware_code;
    }

    public void setHardware_code(String hardware_code) {
        this.hardware_code = hardware_code;
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getCreated_by() {
        return this.created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Date getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getUpdated_by() {
        return this.updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
