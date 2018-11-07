package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 视频推广新容器配置
 */
public class ContainerConfMysqlTable {

    private Integer containerId;
    private String title;
    private Integer data_source;
    private String data_url;
    private Integer data_preloadsize;
    private Integer order_num;
    private Integer ui_plate_type;
    private Integer is_vip;

    public Integer getContainerId() {
        return this.containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getData_source() {
        return this.data_source;
    }

    public void setData_source(Integer data_source) {
        this.data_source = data_source;
    }

    public String getData_url() {
        return this.data_url;
    }

    public void setData_url(String data_url) {
        this.data_url = data_url;
    }

    public Integer getData_preloadsize() {
        return this.data_preloadsize;
    }

    public void setData_preloadsize(Integer data_preloadsize) {
        this.data_preloadsize = data_preloadsize;
    }

    public Integer getOrder_num() {
        return this.order_num;
    }

    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }

    public Integer getUi_plate_type() {
        return this.ui_plate_type;
    }

    public void setUi_plate_type(Integer ui_plate_type) {
        this.ui_plate_type = ui_plate_type;
    }

    public Integer getIs_vip() {
        return this.is_vip;
    }

    public void setIs_vip(Integer is_vip) {
        this.is_vip = is_vip;
    }

}
