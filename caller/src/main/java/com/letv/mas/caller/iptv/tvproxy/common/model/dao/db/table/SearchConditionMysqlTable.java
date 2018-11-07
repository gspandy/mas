package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 搜索条件表
 */
public class SearchConditionMysqlTable {
    private String id;
    /**
     * 条件类型
     */
    private Integer sc_type;

    /**
     * 条件名
     */
    private String sc_name;

    /**
     * 条件值
     */
    private String sc_value;

    /**
     * 是否搜索项
     */
    private Integer is_sc_item;

    /**
     * 排序
     */
    private Integer order_num;

    /**
     * 检索条件状态, 0:失效 1:有效 2:有效，隐藏条件，但不在客户端界面展示
     */
    private Integer status;

    /**
     * 内容分类 TODO 废弃
     */
    private Integer category_id;

    public Integer getSc_type() {
        return sc_type;
    }

    public void setSc_type(Integer sc_type) {
        this.sc_type = sc_type;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }

    public String getSc_value() {
        return sc_value;
    }

    public void setSc_value(String sc_value) {
        this.sc_value = sc_value;
    }

    public Integer getIs_sc_item() {
        return is_sc_item;
    }

    public void setIs_sc_item(Integer is_sc_item) {
        this.is_sc_item = is_sc_item;
    }

    public Integer getOrder_num() {
        return order_num;
    }

    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
