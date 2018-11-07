package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

public class LiveUserPlayAuth {
    private Integer code;// 本次请求是否正常返回，如果成功返回0,如果有异常返回1
    private UserAuth values;

    public static class UserAuth {
        private String status;// 是否可以播放，为0代表不可播放，为1代表可以播放
        private String token;// 防盗链token，如果用户无权观看，则返回空串，有权观看返回token
        private Integer chargetType;// 1.点播且包月，点播和包月类型都可以看 2包月只有包月类型才可以看
        private String isUserBought;// 用户是否购买过此影片，买过为1,没买过为0
        private String ticketSize;// 可用观影券数量，仅当不可以播放时返回
        private Boolean isCellVip;
        private Long cellVipCancelTime;
        private String msg;
        private Integer authFrom;// 路由器鉴权是否通过 1:通过
        private String isForbidden;// 是否被封禁
        private Integer tryLookTime; // 香港手机领先版影片试看时长
        private String price;// 价格
        private String vipPrice;// 会员价格

        public String getVipPrice() {
            return vipPrice;
        }

        public void setVipPrice(String vipPrice) {
            this.vipPrice = vipPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getTryLookTime() {
            return tryLookTime;
        }

        public void setTryLookTime(Integer tryLookTime) {
            this.tryLookTime = tryLookTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Integer getChargetType() {
            return chargetType;
        }

        public void setChargetType(Integer chargetType) {
            this.chargetType = chargetType;
        }

        public String getIsUserBought() {
            return isUserBought;
        }

        public void setIsUserBought(String isUserBought) {
            this.isUserBought = isUserBought;
        }

        public String getTicketSize() {
            return ticketSize;
        }

        public void setTicketSize(String ticketSize) {
            this.ticketSize = ticketSize;
        }

        public Boolean getIsCellVip() {
            return isCellVip;
        }

        public void setIsCellVip(Boolean isCellVip) {
            this.isCellVip = isCellVip;
        }

        public Long getCellVipCancelTime() {
            return cellVipCancelTime;
        }

        public void setCellVipCancelTime(Long cellVipCancelTime) {
            this.cellVipCancelTime = cellVipCancelTime;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getAuthFrom() {
            return authFrom;
        }

        public void setAuthFrom(Integer authFrom) {
            this.authFrom = authFrom;
        }

        public String getIsForbidden() {
            return isForbidden;
        }

        public void setIsForbidden(String isForbidden) {
            this.isForbidden = isForbidden;
        }

        @Override
        public String toString() {
            return "UserAuth [status=" + status + ", token=" + token + ", chargetType=" + chargetType
                    + ", isUserBought=" + isUserBought + ", ticketSize=" + ticketSize + ", isCellVip=" + isCellVip
                    + ", cellVipCancelTime=" + cellVipCancelTime + ", msg=" + msg + ", authFrom=" + authFrom
                    + ", isForbidden=" + isForbidden + "]";
        }

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UserAuth getValues() {
        return values;
    }

    public void setValues(UserAuth values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "UserPlayAuth [code=" + code + ", values=" + values + "]";
    }

}
