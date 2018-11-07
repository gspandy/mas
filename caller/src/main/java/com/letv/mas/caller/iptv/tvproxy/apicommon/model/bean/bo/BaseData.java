package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 数据基类
 */
@ApiModel(value = "BaseData", description = "数据基类")
public class BaseData extends BaseDto implements Serializable {

    private static final long serialVersionUID = 260658910785630733L;

    @ApiModelProperty(value = "跳转数据集合")
    private JumpData<?, ?> jump;

    @ApiModelProperty(value = "跳转类型")
    private int dataType;

    public JumpData<?, ?> getJump() {
        return jump;
    }

    public void setJump(JumpData<?, ?> jump) {
        this.jump = jump;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}