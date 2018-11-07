package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.List;

public class MmsStoreInfo {
    private Integer result;
    private List<FileInfo> data;

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<FileInfo> getData() {
        return this.data;
    }

    public void setData(List<FileInfo> data) {
        this.data = data;
    }

    public static class FileInfo {
        private String codeRate;
        private String duration;
        private String fileSize;
        private String md5;
        private String mid;
        private String pubTime;
        private Integer status;
        private String storeUri;

        public String getCodeRate() {
            return this.codeRate;
        }

        public void setCodeRate(String codeRate) {
            this.codeRate = codeRate;
        }

        public String getDuration() {
            return this.duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getFileSize() {
            return this.fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getMd5() {
            return this.md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getMid() {
            return this.mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getPubTime() {
            return this.pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStoreUri() {
            return this.storeUri;
        }

        public void setStoreUri(String storeUri) {
            this.storeUri = storeUri;
        }

        @Override
        public String toString() {
            return "FileInfo [codeRate=" + this.codeRate + ", duration=" + this.duration + ", fileSize="
                    + this.fileSize + ", md5=" + this.md5 + ", mid=" + this.mid + ", pubTime=" + this.pubTime
                    + ", status=" + this.status + ", storeUri=" + this.storeUri + "]";
        }

    }

    @Override
    public String toString() {
        return "MmsStoreInfo [result=" + this.result + ", data=" + this.data + "]";
    }

}
