package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class SuggestDto extends BaseDto {
    private static final long serialVersionUID = -4824680980571379273L;
    private String name;// 名称
    private String category;// 分类
    private String globalId;

    public SuggestDto() {
    }

    public SuggestDto(String name, String category, String globalId) {
        this.name = name;
        this.category = category;
        this.globalId = globalId;
    }

    public String getGlobalId() {
        return this.globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
