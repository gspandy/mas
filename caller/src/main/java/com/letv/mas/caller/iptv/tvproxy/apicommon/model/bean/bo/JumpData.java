package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 数据基类
 */
@ApiModel(value = "JumpData", description = "跳转数据基类")
public class JumpData<T, E> extends BaseDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8804619215619988910L;

    /**
     * 跳转类型
     */
    @ApiModelProperty(value = "跳转类型")
    private Integer type;

    /**
     * 跳转信息体
     */
    @ApiModelProperty(value = "跳转信息体")
    private T Value;

    /**
     * 扩展对象，承接action
     */
    @ApiModelProperty(value = "扩展对象，承接action")
    private E extend;

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public T getValue() {
        return this.Value;
    }

    public void setValue(T value) {
        this.Value = value;
    }

    public E getExtend() {
        return extend;
    }

    public void setExtend(E extend) {
        this.extend = extend;
    }
}
