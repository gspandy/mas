package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.Map;

/**
 * 可配置的支付渠道封装类
 * @author KevinYi
 */
public class PaymentChannelDto extends BaseDto {

    /**
     * 支付渠道映射，key--产品类型，如1--单点，2--套餐；value--对应的支付渠道列表，其内容即为可用的支付渠道，其顺序即为收银台上排序
     */
    private Map<String, String> paymentChannelMap;

    public Map<String, String> getPaymentChannelMap() {
        return this.paymentChannelMap;
    }

    public void setPaymentChannelMap(Map<String, String> paymentChannelMap) {
        this.paymentChannelMap = paymentChannelMap;
    }

}
