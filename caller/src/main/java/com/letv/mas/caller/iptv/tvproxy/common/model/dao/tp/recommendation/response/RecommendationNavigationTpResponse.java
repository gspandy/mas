package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

import java.util.List;

public class RecommendationNavigationTpResponse {

    private List<RecommendationNavigationTpResponseRec> rec;// 推荐导航的列表

    public List<RecommendationNavigationTpResponseRec> getRec() {
        return this.rec;
    }

    public void setRec(List<RecommendationNavigationTpResponseRec> rec) {
        this.rec = rec;
    }

}
