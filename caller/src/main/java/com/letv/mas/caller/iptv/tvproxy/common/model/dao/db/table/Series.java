package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

public class Series {
    private static final long serialVersionUID = 103571507218363001L;

    private Long id;
    private String name;
    private String hardware_code;// 品牌编码
    private Long brand_id;
    private Date create_time;
    private String created_by;
    private Date update_time;
    private String updated_by;
    private Integer status;// 0：有效；1：无效
    private Integer is_cntv_block;// 是否Cntv显示（1显示）

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

    public Long getBrand_id() {
        return this.brand_id;
    }

    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
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

    public Integer getIs_cntv_block() {
        return this.is_cntv_block;
    }

    public void setIs_cntv_block(Integer is_cntv_block) {
        this.is_cntv_block = is_cntv_block;
    }

}
