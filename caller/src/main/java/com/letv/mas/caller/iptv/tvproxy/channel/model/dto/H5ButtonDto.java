package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.ButtonDto;

/**
 * 跳h5页面的按钮信息封装类
 * @author liyunlong
 */
public class H5ButtonDto extends ButtonDto {

    public H5ButtonDto(String title, Integer type, Integer browserType, Integer openType) {
        super(title, type, browserType, openType);
    }

    /**
     * 跳H5的URL
     */
    private String jumpUrl;

    /**
     * 预约成功标题文案
     */
    private String orderSuccessText;

    public String getJumpUrl() {
        return this.jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getOrderSuccessText() {
        return this.orderSuccessText;
    }

    public void setOrderSuccessText(String orderSuccessText) {
        this.orderSuccessText = orderSuccessText;
    }

}
