package com.letv.mas.router.iptv.tvproxy.model.dto;

import io.swagger.annotations.ApiModel;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by leeco on 18/10/10.
 */
@ApiModel(value = "BaseDto", description = "基础入参")
public class BaseDto {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
