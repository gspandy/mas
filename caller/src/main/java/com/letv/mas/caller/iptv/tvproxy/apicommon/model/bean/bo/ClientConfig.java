package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import java.util.List;

/**
 * 客户端配置
 */
public class ClientConfig {

    private ClientConfigLeso leso;

    public ClientConfigLeso getLeso() {
        return this.leso;
    }

    public void setLeso(ClientConfigLeso leso) {
        this.leso = leso;
    }

    public static class ClientConfigLeso {

        private List<ClientConfigLesoVideoSource> videoSourceList;

        private String ua;

        public List<ClientConfigLesoVideoSource> getVideoSourceList() {
            return this.videoSourceList;
        }

        public void setVideoSourceList(List<ClientConfigLesoVideoSource> videoSourceList) {
            this.videoSourceList = videoSourceList;
        }

        public String getUa() {
            return this.ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public static class ClientConfigLesoVideoSource {

            private int sourceId;

            private String sourceEName;

            private String sourceCName;

            private int isTrySee;

            private int trySeeTime;

            private int isAutoPlay;

            private String ua;

            private String js;

            private int isJsDoReload;

            private String autoPlayJs;

            public String getAutoPlayJs() {
                return this.autoPlayJs;
            }

            public void setAutoPlayJs(String autoPlayJs) {
                this.autoPlayJs = autoPlayJs;
            }

            public int getIsJsDoReload() {
                return this.isJsDoReload;
            }

            public void setIsJsDoReload(int isJsDoReload) {
                this.isJsDoReload = isJsDoReload;
            }

            public String getJs() {
                return this.js;
            }

            public void setJs(String js) {
                this.js = js;
            }

            public int getSourceId() {
                return this.sourceId;
            }

            public void setSourceId(int sourceId) {
                this.sourceId = sourceId;
            }

            public String getSourceEName() {
                return this.sourceEName;
            }

            public void setSourceEName(String sourceEName) {
                this.sourceEName = sourceEName;
            }

            public String getSourceCName() {
                return this.sourceCName;
            }

            public void setSourceCName(String sourceCName) {
                this.sourceCName = sourceCName;
            }

            public int getIsTrySee() {
                return this.isTrySee;
            }

            public void setIsTrySee(int isTrySee) {
                this.isTrySee = isTrySee;
            }

            public int getTrySeeTime() {
                return this.trySeeTime;
            }

            public void setTrySeeTime(int trySeeTime) {
                this.trySeeTime = trySeeTime;
            }

            public int getIsAutoPlay() {
                return this.isAutoPlay;
            }

            public void setIsAutoPlay(int isAutoPlay) {
                this.isAutoPlay = isAutoPlay;
            }

            public String getUa() {
                return this.ua;
            }

            public void setUa(String ua) {
                this.ua = ua;
            }
        }
    }
}
