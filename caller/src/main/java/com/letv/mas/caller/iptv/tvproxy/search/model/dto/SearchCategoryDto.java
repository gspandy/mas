package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class SearchCategoryDto extends BaseDto {
    private Integer categoryId;
    private Integer newCategoryId;// 所有分类 牵移到 搜索结果后 删除该字段
    private String categoryName;
    private Integer count;

    public SearchCategoryDto() {
    }

    public SearchCategoryDto(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.newCategoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getNewCategoryId() {
        return this.newCategoryId;
    }

    public void setNewCategoryId(Integer newCategoryId) {
        this.newCategoryId = newCategoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
