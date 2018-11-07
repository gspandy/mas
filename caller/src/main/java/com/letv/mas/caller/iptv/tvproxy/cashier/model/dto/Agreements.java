package com.letv.mas.caller.iptv.tvproxy.cashier.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by guoyunfeng on 2017/6/29.
 * 协议
 */
@ApiModel(value = "Agreements", description = "套餐协议信息")
public class Agreements {
    @ApiModelProperty(value = "套餐协议列表")
    public List<Agreement> agreementList;

    public List<Agreement> getAgreementList() {
        return agreementList;
    }

    public void setAgreementList(List<Agreement> agreementList) {
        this.agreementList = agreementList;
    }

    public static class Agreement {
        @ApiModelProperty(value = "套餐种类(pro:超级影视会员；renewal:连续包月超级影视会员)")
        private String type;
        @ApiModelProperty(value = "套餐协议内容")
        private String content;
        @ApiModelProperty(value = "套餐协议标题")
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
