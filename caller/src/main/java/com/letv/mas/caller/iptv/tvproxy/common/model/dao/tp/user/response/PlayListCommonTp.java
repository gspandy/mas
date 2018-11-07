package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

/**
 * 播放当,追剧和收藏调用第三方接口通用返回格式
 * @author dunhongqin
 */
public class PlayListCommonTp {

    private String code;
    private PlayList data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PlayList getData() {
        return this.data;
    }

    public void setData(PlayList data) {
        this.data = data;
    }

    public static class PlayList {

        private String msg;

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
