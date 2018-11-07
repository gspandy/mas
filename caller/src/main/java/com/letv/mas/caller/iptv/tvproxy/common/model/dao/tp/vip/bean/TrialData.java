package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 试用数据
 * Created by liudaojie on 16/8/25.
 */
@ApiModel(value = "TrialData", description = "试用数据")
public class TrialData {
    @ApiModelProperty(value = "单位.可能值 month year date")
    private String trialField; // 单位.可能值 month year date
    @ApiModelProperty(value = "时长")
    private Integer trialDuration; // 时长
    @ApiModelProperty(value = "单位名称")
    private String trialDurationName; // 单位名称

    public String getTrialField() {
        return trialField;
    }

    public void setTrialField(String trialField) {
        this.trialField = trialField;
    }

    public Integer getTrialDuration() {
        return trialDuration;
    }

    public void setTrialDuration(Integer trialDuration) {
        this.trialDuration = trialDuration;
    }

    public String getTrialDurationName() {
        return trialDurationName;
    }

    public void setTrialDurationName(String trialDurationName) {
        this.trialDurationName = trialDurationName;
    }
}
