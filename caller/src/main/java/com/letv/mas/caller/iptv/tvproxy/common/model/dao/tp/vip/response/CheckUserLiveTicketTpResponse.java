package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import org.springframework.util.CollectionUtils;

/**
 * 查询用户是否购买过某一直播券请求响应封装类
 * @author KevinYi
 */
public class CheckUserLiveTicketTpResponse {

    private Integer status; // 接口响应状态，0--失败，1--成功
    private List<UserLiveTicket> packages; // 直播券列表

    public CheckUserLiveTicketTpResponse() {
        super();
    }

    /**
     * 判断用户是否已购买过直播，这里仅验证音乐直播
     * @return
     */
    public boolean hasBoughtLiveTicket() {
        if (this.status != null && this.status == 1 && !CollectionUtils.isEmpty(this.packages)) {
            for (UserLiveTicket ticket : this.packages) {
                if (ticket.getType() != null && VipTpConstant.LIVE_TYPE_TICKET == ticket.getType()
                        && ticket.getStatus() != null && ticket.getStatus() == 1 && ticket.getCount() != null
                        && ticket.getCount() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<UserLiveTicket> getPackages() {
        return this.packages;
    }

    public void setPackages(List<UserLiveTicket> packages) {
        this.packages = packages;
    }

    public static class UserLiveTicket {

        /**
         * 直播是否可用，0--不可用，1--可用
         */
        private Integer status;

        /**
         * 直播类型，1--直播券，2--轮次包，预定义类型，暂无数据，3--赛季包，4--球队宝
         */
        private Integer type;

        /**
         * 已购直播数量，仅在直播类型是直播券时才有值
         */
        private Integer count;

        public UserLiveTicket() {
            super();
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getCount() {
            return this.count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

    }
}
