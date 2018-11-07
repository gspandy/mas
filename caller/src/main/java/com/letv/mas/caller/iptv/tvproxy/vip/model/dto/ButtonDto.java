package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ButtonDto", description = "观星活动按钮")
public class ButtonDto extends BaseData {

    @ApiModelProperty(value = "按钮文案")
    private String buttonText;// 按钮文案
    @ApiModelProperty(value = "排序")
    private Integer order = 1;// 排序

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
