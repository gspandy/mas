package com.letv.mas.caller.iptv.tvproxy.common.model.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Map;

/**
 * Created by leeco on 18/9/6.
 */
public class PageCacheItem {
    private String bizId;
    private Map<String, Object> sortFields;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Map<String, Object> getSortFields() {
        return sortFields;
    }

    public void setSortFields(Map<String, Object> sortFields) {
        this.sortFields = sortFields;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
