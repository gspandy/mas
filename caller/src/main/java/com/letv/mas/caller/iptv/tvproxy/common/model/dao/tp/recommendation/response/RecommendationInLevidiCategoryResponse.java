package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

import java.io.Serializable;
import java.util.List;

public class RecommendationInLevidiCategoryResponse implements Serializable {

    private static final long serialVersionUID = -2516945579808314689L;

    private String status;

    private List<SarrsCategory> data;

    public static class SarrsCategory implements Serializable {

        private static final long serialVersionUID = 8651093881811799040L;

        private String name;

        private String show_name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShow_name() {
            return show_name;
        }

        public void setShow_name(String show_name) {
            this.show_name = show_name;
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SarrsCategory> getData() {
        return data;
    }

    public void setData(List<SarrsCategory> data) {
        this.data = data;
    }

}
