package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

public class RecommendaionByPidResponse {
    private String status;
    private String code;
    private String msg;
    private String serverTime;

    private Ent entity;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public Ent getEntity() {
        return entity;
    }

    public void setEntity(Ent entity) {
        this.entity = entity;
    }

    public static class Ent {
        private String name; // 名称
        private String icon; // 图标
        private String subTitle; // 副标题
        private String downloadCount; // 下载数
        private String[] accessories; // 播放平台
        private Integer type; // 播放地址
        private String value; // 图片地址

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(String downloadCount) {
            this.downloadCount = downloadCount;
        }

        public String[] getAccessories() {
            return accessories;
        }

        public void setAccessories(String[] accessories) {
            this.accessories = accessories;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
