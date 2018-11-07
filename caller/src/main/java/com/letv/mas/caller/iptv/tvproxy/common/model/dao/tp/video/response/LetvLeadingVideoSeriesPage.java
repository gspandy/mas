package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;
import java.util.List;

/**
 * 领先版专辑详情页中的某一页剧集数据
 * @author KevinYi
 */
public class LetvLeadingVideoSeriesPage implements Serializable {

    private Integer page;

    private Integer pageSize;

    private List<LetvLeadingPlayInfo> positiveSeries;

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<LetvLeadingPlayInfo> getPositiveSeries() {
        return this.positiveSeries;
    }

    public void setPositiveSeries(List<LetvLeadingPlayInfo> positiveSeries) {
        this.positiveSeries = positiveSeries;
    }

}
