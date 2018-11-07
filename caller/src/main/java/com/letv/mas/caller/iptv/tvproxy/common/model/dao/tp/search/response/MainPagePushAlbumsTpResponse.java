package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.List;

/**
 * 乐搜新首页推荐的专辑列表
 */
public class MainPagePushAlbumsTpResponse {
    private String status; // 接口调用状态 0:成功
    private String message;
    private MastHeadContent content;
    private String eid;
    private String experiment_bucket_str;
    private String trigger_str;
    private String module;

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getExperiment_bucket_str() {
        return this.experiment_bucket_str;
    }

    public void setExperiment_bucket_str(String experiment_bucket_str) {
        this.experiment_bucket_str = experiment_bucket_str;
    }

    public String getTrigger_str() {
        return this.trigger_str;
    }

    public void setTrigger_str(String trigger_str) {
        this.trigger_str = trigger_str;
    }

    public String getEid() {
        return this.eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public boolean success() {
        return "0".equals(this.status);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MastHeadContent getContent() {
        return this.content;
    }

    public void setContent(MastHeadContent content) {
        this.content = content;
    }

    public static class MastHeadContent {
        private List<MastHeadObject> masthead;

        public List<MastHeadObject> getMasthead() {
            return this.masthead;
        }

        public void setMasthead(List<MastHeadObject> masthead) {
            this.masthead = masthead;
        }
    }

    public static class MastHeadObject {
        private List<MainPageAlbum> albums;
        private String description;
        private String name;

        public List<MainPageAlbum> getAlbums() {
            return this.albums;
        }

        public void setAlbums(List<MainPageAlbum> albums) {
            this.albums = albums;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
