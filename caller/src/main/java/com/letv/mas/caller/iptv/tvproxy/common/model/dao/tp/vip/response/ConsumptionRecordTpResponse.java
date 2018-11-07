package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 用户消费记录第三方接口返回值封装类
 * @author yikun
 */
public class ConsumptionRecordTpResponse {

    /**
     * 消费记录总数
     */
    private Integer total;

    /**
     *
     */
    private Integer cnt;

    /**
     * 消费记录列表
     */
    private List<ConsumptionRecord> data;

    public ConsumptionRecordTpResponse() {
    }

    public ConsumptionRecordTpResponse(Integer total, Integer cnt, List<ConsumptionRecord> data) {
        this.total = total;
        this.cnt = cnt;
        this.data = data;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCnt() {
        return this.cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<ConsumptionRecord> getData() {
        return this.data;
    }

    public void setData(List<ConsumptionRecord> data) {
        this.data = data;
    }

    public static class ConsumptionRecord {

        /**
        *
        */
        private String action;

        /**
         * apk version，参见VipTpConstant。BROADCAST_APK_VERSION_*
         */
        private Integer av;

        /**
        *
        */
        private String pakBuyCount;

        /**
         * 支付渠道
         */
        private Integer payChannel;

        /**
         * 子终端来源
         */
        private String subOrderFrom;

        /**
         * 用户id
         */
        private String userId;

        /**
         * 用户IP，一般是mac
         */
        private String userIp;

        /**
         * 订单状态，0--未开通未支付，1--已开通已支付，2--已过期
         * 2016-03-29 0,未支付 |1,支付 |2,失效|3,退款|4,关闭
         */
        private String status;

        /**
         * 用户名称
         */
        private String userName;

        /**
         * 订单ID
         */
        private String orderId;

        /**
         * 订单来源 (1是vip网页，2是pc网页，3是tv，4是手机，13是机卡绑定套餐)
         */
        private String orderFrom;

        /**
         * 订单类型，0--单点影片，1--单点电视剧，1001--直播内容点播，2--包月，3--包季，4--包半年，5--包年，6--包3年,
         * 7--用户中心红包赠送，包两年，8--影片打包购买，包六年，9--会员升级，40--7天免费会员，41--1元高级VIP包月，
         * 44--一月免费，45--一天免费，43--免费七天，42--自动续费包月，46--免费包季，1000--免费按天赠送
         */
        private String orderType;

        /**
         * 本次消费记录对应的会员有效期失效时间
         */
        private String cancelTime;

        /**
         * 本次消费记录对应的会员有效期生效时间
         */
        private String createTime;

        /**
         * 单点影片的专辑id或者视频id
         */
        private String aid2;

        /**
         * 单点影片的专辑id或者视频id
         */
        private String aid;

        /**
         * 消费金额，单位：元
         */
        private String money;

        /**
         *
         */
        private String waveUrl;

        /**
         * 订单名称
         */
        private String orderName;

        /**
         * 商品名称，一般和orderName一致，但有例外
         */
        private String aidName;

        /**
         * 支付类型
         */
        private String payType;

        /**
         * 消费记录状态名称，0待付, -1 (单片)点播过期,1(单片)点播成功,-2包月过期,2包月中
         */
        private String statusName;

        /**
         * 对应乐点价格
         */
        private String moneyName;

        /**
         * 消费时间
         */
        private String payTime;

        /**
         * 是否支持超时支付（暂不用）
         */
        private String isShowRepay;

        public ConsumptionRecord() {
        }

        public ConsumptionRecord(String status, String userName, String orderId, String orderFrom, String orderType,
                String cancelTime, String createTime, String aid2, String aid, String money, String waveUrl,
                String orderName, String aidName, String payType, String statusName, String moneyName, String payTime,
                String isShowRepay) {
            this.status = status;
            this.userName = userName;
            this.orderId = orderId;
            this.orderFrom = orderFrom;
            this.orderType = orderType;
            this.cancelTime = cancelTime;
            this.createTime = createTime;
            this.aid2 = aid2;
            this.aid = aid;
            this.money = money;
            this.waveUrl = waveUrl;
            this.orderName = orderName;
            this.aidName = aidName;
            this.payType = payType;
            this.statusName = statusName;
            this.moneyName = moneyName;
            this.payTime = payTime;
            this.isShowRepay = isShowRepay;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserName() {
            return this.userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderFrom() {
            return this.orderFrom;
        }

        public void setOrderFrom(String orderFrom) {
            this.orderFrom = orderFrom;
        }

        public String getOrderType() {
            return this.orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getCancelTime() {
            return this.cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAid2() {
            return this.aid2;
        }

        public void setAid2(String aid2) {
            this.aid2 = aid2;
        }

        public String getAid() {
            return this.aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getMoney() {
            return this.money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getWaveUrl() {
            return this.waveUrl;
        }

        public void setWaveUrl(String waveUrl) {
            this.waveUrl = waveUrl;
        }

        public String getOrderName() {
            return this.orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getAidName() {
            return this.aidName;
        }

        public void setAidName(String aidName) {
            this.aidName = aidName;
        }

        public String getPayType() {
            return this.payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getStatusName() {
            return this.statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getMoneyName() {
            return this.moneyName;
        }

        public void setMoneyName(String moneyName) {
            this.moneyName = moneyName;
        }

        public String getPayTime() {
            return this.payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getIsShowRepay() {
            return this.isShowRepay;
        }

        public void setIsShowRepay(String isShowRepay) {
            this.isShowRepay = isShowRepay;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Integer getAv() {
            return this.av;
        }

        public void setAv(Integer av) {
            this.av = av;
        }

        public String getPakBuyCount() {
            return this.pakBuyCount;
        }

        public void setPakBuyCount(String pakBuyCount) {
            this.pakBuyCount = pakBuyCount;
        }

        public Integer getPayChannel() {
            return this.payChannel;
        }

        public void setPayChannel(Integer payChannel) {
            this.payChannel = payChannel;
        }

        public String getSubOrderFrom() {
            return this.subOrderFrom;
        }

        public void setSubOrderFrom(String subOrderFrom) {
            this.subOrderFrom = subOrderFrom;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserIp() {
            return this.userIp;
        }

        public void setUserIp(String userIp) {
            this.userIp = userIp;
        }

    }
}
