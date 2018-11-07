package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

/**
 * 动态搜索分类dto
 * @author chenjian
 */
public class SearchCategoryDto2 extends BaseDto {
    private static final long serialVersionUID = 1920674406703510907L;
    public static final int allCategory = 0; // 0代表全部
    public static final int categoryCategory = 1;// 1代表根据categoryId分类
    public static final int dataTypeCategory = 2; // 2代表根据dt分类
    private Integer type;// 分类类别
    private String name;// 分类名称
    private String categoryId;// 分类值
    private String dt;

    public SearchCategoryDto2(Integer type, String name, String categoryId, String dt) {
        this.type = type;
        this.name = name;
        this.categoryId = categoryId;
        this.dt = dt;
    }

    public SearchCategoryDto2(String name, String categoryId, String dt) {
        this.name = name;
        this.categoryId = categoryId;
        this.dt = dt;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDt() {
        return this.dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

}
