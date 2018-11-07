package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;
import java.util.List;

public class GetPayChannelResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5634900541831113814L;

    /**
     * 响应码，0：成功，1：表示失败
     */
    private String code;

    /**
     * 返回的信息
     */
    private String msg;

    private List<PayChannel> data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PayChannel> getData() {
        return this.data;
    }

    public void setData(List<PayChannel> data) {
        this.data = data;
    }

    public static class PayChannel implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = -5291262549354666241L;

        /**
         * 频道id
         */
        private String channelId;

        /**
         * 频道名称
         */
        private String channelName;

        /**
         * 付费类型；0点播，1点播且包月，2包月，3免费但TV包月收费，4包年，5码流付费
         */
        private Integer chargeType;

        /**
         * 是否付费；0表示免费;1表示付费
         */
        private Integer isCharge;

        /**
         * 价格
         */
        private String price;

        public String getChannelId() {
            return this.channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelName() {
            return this.channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public Integer getChargeType() {
            return this.chargeType;
        }

        public void setChargeType(Integer chargeType) {
            this.chargeType = chargeType;
        }

        public Integer getIsCharge() {
            return this.isCharge;
        }

        public void setIsCharge(Integer isCharge) {
            this.isCharge = isCharge;
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

    }

}
