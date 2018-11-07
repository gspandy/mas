package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

public class MmsChangeLog {
    private String id;
    private String object_id;
    private Integer action_type;
    private Integer process_status;
    private Integer data_status = 0;// log是否为初始化数据（1：初始化数据）

    /**
     * 数值越大，优先级越高
     */
    private Integer p_level = 0;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject_id() {
        return this.object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public Integer getAction_type() {
        return this.action_type;
    }

    public void setAction_type(Integer action_type) {
        this.action_type = action_type;
    }

    public Integer getProcess_status() {
        return this.process_status;
    }

    public void setProcess_status(Integer process_status) {
        this.process_status = process_status;
    }

    public Integer getData_status() {
        return this.data_status;
    }

    public void setData_status(Integer data_status) {
        this.data_status = data_status;
    }

    public Integer getP_level() {
        return this.p_level;
    }

    public void setP_level(Integer p_level) {
        this.p_level = p_level;
    }

}
