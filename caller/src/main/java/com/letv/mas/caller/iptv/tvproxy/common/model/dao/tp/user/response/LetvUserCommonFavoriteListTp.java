package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

/**
 * 获取专题收藏
 * @author yuehongmin
 */
public class LetvUserCommonFavoriteListTp {

    private String code;
    private LetvUserAddFavoriteData data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LetvUserAddFavoriteData getData() {
        return this.data;
    }

    public void setData(LetvUserAddFavoriteData data) {
        this.data = data;
    }

    public static class LetvUserAddFavoriteData {

        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }

}
