package com.letv.mas.caller.iptv.tvproxy.common.model.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * Created by leeco on 18/9/6.
 */
public class PageCacheDto {
    /**
     * 分页大小
     */
    private int size;

    /**
     * 排序参数集合：key[字段名]/value[排序类型：asc／desc，null默认为asc]
     */
    private Map<String, String> sortParams;

    /**
     * 分页数据索引集合
     */
    private List<PageCacheItem> items;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<String, String> getSortParams() {
        return sortParams;
    }

    public void setSortParams(Map<String, String> sortParams) {
        this.sortParams = sortParams;
    }

    public List<PageCacheItem> getItems() {
        return items;
    }

    public void setItems(List<PageCacheItem> items) {
        this.items = items;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
