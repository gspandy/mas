package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.List;

/**
 * 搜索结果返回对象
 * 搜索结果是mix混排
 */
public class SearchResultTpResponse {

    private List<SearchCategory> category_count_list; // 明星作品类别

    public List<SearchCategory> getCategory_count_list() {
        return this.category_count_list;
    }

    public void setCategory_count_list(List<SearchCategory> category_count_list) {
        this.category_count_list = category_count_list;
    }

}
