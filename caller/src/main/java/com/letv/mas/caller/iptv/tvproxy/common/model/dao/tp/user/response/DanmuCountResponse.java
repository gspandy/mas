package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

public class DanmuCountResponse {

    private Integer code;

    private DanmuCount data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DanmuCount getData() {
        return data;
    }

    public void setData(DanmuCount data) {
        this.data = data;
    }

    public class DanmuCount {

        private Integer pidCount;
        private Integer vidCount;

        public Integer getPidCount() {
            return pidCount;
        }

        public void setPidCount(Integer pidCount) {
            this.pidCount = pidCount;
        }

        public Integer getVidCount() {
            return vidCount;
        }

        public void setVidCount(Integer vidCount) {
            this.vidCount = vidCount;
        }

    }
}
