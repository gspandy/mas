package com.letv.mas.caller.iptv.tvproxy.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * 单数据响应类
 * @param <T>
 */
@XmlRootElement
@ApiModel(value="BaseResponse", description="响应类")
public class Response<T> extends BaseResponse {

    /**
     * 数据
     */
    @ApiModelProperty(value = "返回数据")
    private T data;

    /**
     * 补充数据
     */
    @ApiModelProperty(value = "补充数据")
    private Map<String, Object> plus;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getPlus() {
        return plus;
    }

    public void setPlus(Map<String, Object> plus) {
        this.plus = plus;
    }
}
