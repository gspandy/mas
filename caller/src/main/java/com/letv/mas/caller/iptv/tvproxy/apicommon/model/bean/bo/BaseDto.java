package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class BaseDto implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
