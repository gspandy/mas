package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal.response;

public class UpgradeTpResponse {
    private Integer status;// 接口状态，0--失败，1--正常
    private UpgradeInfo data;// 升级信息

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UpgradeInfo getData() {
        return data;
    }

    public void setData(UpgradeInfo data) {
        this.data = data;
    }

    public static class UpgradeInfo {
        private Integer status;// 升级状态，5--强制升级，6--推荐升级，7-不升级
        private String message;// 有升级提示文案
        private String versionUrl;// 安装包下载地址
        private String description;// 版本描述
        private Long timeStamp;// 发布时间，时间戳
        private String versionName;// 版本名称
        private String dstmd5;// 升级包md5
        private Integer size;// 升级包大小
        private String title;// 升级标题
        private String confirmText;// 确认文案

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getVersionUrl() {
            return versionUrl;
        }

        public void setVersionUrl(String versionUrl) {
            this.versionUrl = versionUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(Long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getDstmd5() {
            return dstmd5;
        }

        public void setDstmd5(String dstmd5) {
            this.dstmd5 = dstmd5;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getConfirmText() {
            return confirmText;
        }

        public void setConfirmText(String confirmText) {
            this.confirmText = confirmText;
        }

    }
}
