package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 查询绑卡信息返回数据封装类
 * @author liyunlong
 */
public class GetBindInfoTpResponse {
    /**
     * 状态
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errormsg;

    /**
     * 绑定银行列表
     */
    private List<BandInfo> bandinfo;

    public static class BandInfo {
        /**
         * 银行名称
         */
        private String bankname;

        /**
         * 银行卡号
         */
        private String cardno;

        /**
         * 绑定id
         */
        private String identityid;

        public String getBankname() {
            return this.bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getCardno() {
            return this.cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }

        public String getIdentityid() {
            return this.identityid;
        }

        public void setIdentityid(String identityid) {
            this.identityid = identityid;
        }
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrormsg() {
        return this.errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public List<BandInfo> getBandinfo() {
        return this.bandinfo;
    }

    public void setBandinfo(List<BandInfo> bandinfos) {
        this.bandinfo = bandinfos;
    }

}
