package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.io.Serializable;

public class JSVersionDto implements Serializable {

    public static final String SEARCH_JSVERSION_CACHEKEY = "SearchJSVersionCacheKey";

    private static final long serialVersionUID = 1316697908294204427L;
    private String jsCode;
    private Integer version;

    public JSVersionDto() {

    }

    public JSVersionDto(String jsCode, Integer version) {
        this.jsCode = jsCode;
        this.version = version;
    }

    public String getJsCode() {
        return this.jsCode;
    }

    public void setJsCode(String jsCode) {
        this.jsCode = jsCode;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
