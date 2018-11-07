package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.SearchCategory;

import java.util.List;

public class SearchStarCategoryDto extends BaseDto {
    private static final long serialVersionUID = 7318709361797313335L;
    private List<SearchCategory> category_count_list;

    public List<SearchCategory> getCategory_count_list() {
        return this.category_count_list;
    }

    public void setCategory_count_list(List<SearchCategory> category_count_list) {
        this.category_count_list = category_count_list;
    }

}
