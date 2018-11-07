package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.List;

public class SuggestTpResponse {

    private String eid;
    private String experiment_bucket_str;
    private String trigger_str;
    private List<Suggest> data_list;

    public String getEid() {
        return this.eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
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

    public List<Suggest> getData_list() {
        return this.data_list;
    }

    public void setData_list(List<Suggest> data_list) {
        this.data_list = data_list;
    }

    public static class Suggest {
        private String name;// 名称
        private String category;// 分类
        private String global_id;

        public String getGlobal_id() {
            return this.global_id;
        }

        public void setGlobal_id(String global_id) {
            this.global_id = global_id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

}
