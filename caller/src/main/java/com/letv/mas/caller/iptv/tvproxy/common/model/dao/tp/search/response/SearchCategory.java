package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchCategory implements Serializable {
    private static final long serialVersionUID = 3396998843774482835L;
    private Integer category; // 分类ID 0:为明星
    private String category_name; // 分类名称
    private Integer count; // 分类总数
    private Integer dataType; // 数据类型:1、专辑 2、视频

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDataType() {
        return this.dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
