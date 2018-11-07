package com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;

import java.util.List;

public class RecommendationSarrDto<T> extends BaseData {

    /**
     * 
     */
    private static final long serialVersionUID = 7047825304129274594L;

    private String cid;// 类别
    private String title;// cp名称
    private List<T> dataList;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

}
