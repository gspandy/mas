package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.bean.ExtendParam;

/**
 * 会员消费Dto，支持购买套餐和单点，使用支付宝、微信、拉卡拉、乐点4种支付渠道
 * @author yikun
 */
public class PurchaseVipDto extends BaseDto {

    /**
     * 订单号
     */
    private String corderid;

    /**
     * 支付平台流水
     */
    private String ordernumber;

    /**
     * 支付二维码链接，支付宝、微信、拉卡拉支付时使用
     */
    private String url;

    /**
     * 订单状态，0--失败，1--成功；乐点支付时使用
     */
    private Integer orderStatus;

    /**
     * 会员有效期开始时间；乐点支付时使用
     */
    private String vipStartDate;

    /**
     * 会员有效期结束时间；乐点支付时使用
     */
    private String vipEndDate;

    /**
     * 扩展参数，一些写死的参数由服务端写死，然后提供给客户端
     */
    private ExtendParam extendParam;

    public PurchaseVipDto() {
        super();
    }

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getVipStartDate() {
        return this.vipStartDate;
    }

    public void setVipStartDate(String vipStartDate) {
        this.vipStartDate = vipStartDate;
    }

    public String getVipEndDate() {
        return this.vipEndDate;
    }

    public void setVipEndDate(String vipEndDate) {
        this.vipEndDate = vipEndDate;
    }

    public String getOrdernumber() {
        return this.ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public ExtendParam getExtendParam() {
        return this.extendParam;
    }

    public void setExtendParam(ExtendParam extendParam) {
        this.extendParam = extendParam;
    }
}