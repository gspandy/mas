package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ConsumptionRecordConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class ConsumptionRecordDto extends BaseDto {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单名称
     */
    private String orderName;

    /**
     * 价格，单位：元
     */
    private String money;

    /**
     * 乐点
     */
    private Integer moneyName;

    /**
     * 当前订单作用下的会员有效期开始时间
     */
    private String createTime;

    /**
     * 当前订单作用下的会员有效期失效时间
     */
    private String cancelTime;

    /**
     * 下单时间
     */
    private String payTime;

    /**
     * 订单状态
     */
    private String statusName;

    /**
     * 订单来源 (1是vip网页，2是pc网页，3是tv，4是手机，13是机卡绑定)
     */
    private String orderFrom;

    /**
     * 订单来源,对应的中文含义
     */
    private String orderFromText;

    /**
     * 订单状态,对应的中文含义
     */
    private String statusNameText;

    /**
     * 是否支持超时支付（暂不用）
     */
    private String isShowRepay;

    /**
     * 订单类型，0--单点影片，1--单点电视剧，1001--直播内容点播，2--包月，3--包季，4--包半年，5--包年，6--包3年,
     * 7--用户中心红包赠送，包两年，8--影片打包购买，包六年，9--会员升级，40--7天免费会员，41--1元高级VIP包月，
     * 44--一月免费，45--一天免费，43--免费七天，42--自动续费包月，46--免费包季，1000--免费按天赠送
     */
    private String orderType;

    /**
     * 单点影片的专辑id，可以通过此id获取专辑信息，单点影片订单专用
     */
    private String albumId;

    /**
     * 单点影片的剩余有效时间，格式为“天：时：分”，均保留两位，即"xx:xx:xx"，单点影片订单专用
     */
    private String leftEffectiveTime;

    /**
     * 专辑缩略图地址，默认分辨率为宽x高-400x300，与播放记录中保持一致，单点订单专用
     */
    private String albumImg_400X300;

    public ConsumptionRecordDto() {
        super();
    }

    public ConsumptionRecordDto(String orderId, String orderName, String money, String moneyName, String createTime,
            String cancelTime, String statusName, String orderFrom, String payTime, String isShowRepay) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.money = money;
        this.moneyName = (moneyName == null || "".equals(moneyName)) ? 0 : (new Float(moneyName)).intValue();
        this.createTime = createTime;
        this.cancelTime = cancelTime;
        this.payTime = payTime;
        this.statusName = statusName;
        this.orderFrom = orderFrom;
        this.orderFromText = ConsumptionRecordConstants.getOrderFromByCode(orderFrom);
        this.statusNameText = ConsumptionRecordConstants.getOrderStatusByCode(statusName);
        this.isShowRepay = isShowRepay;
    }

    public String getPayTime() {
        return this.payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return this.orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getMoneyName() {
        return this.moneyName;
    }

    public void setMoneyName(Integer moneyName) {
        this.moneyName = moneyName;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCancelTime() {
        return this.cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrderFrom() {
        return this.orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderFromText() {
        return this.orderFromText;
    }

    public void setOrderFromText(String orderFromText) {
        this.orderFromText = orderFromText;
    }

    public String getStatusNameText() {
        return this.statusNameText;
    }

    public void setStatusNameText(String statusNameText) {
        this.statusNameText = statusNameText;
    }

    public String getIsShowRepay() {
        return this.isShowRepay;
    }

    public void setIsShowRepay(String isShowRepay) {
        this.isShowRepay = isShowRepay;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getLeftEffectiveTime() {
        return this.leftEffectiveTime;
    }

    public void setLeftEffectiveTime(String leftEffectiveTime) {
        this.leftEffectiveTime = leftEffectiveTime;
    }

    public String getAlbumImg_400X300() {
        return this.albumImg_400X300;
    }

    public void setAlbumImg_400X300(String albumImg_400X300) {
        this.albumImg_400X300 = albumImg_400X300;
    }
}
