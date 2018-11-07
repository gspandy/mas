package com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leeco on 2017/11/16.
 * @author caidongfang
 */
public class PeriodTvLog<T> {
    private List<T> recentlyItems = new ArrayList<T>();

    private List<T> lastWeekItems = new ArrayList<T>();

    private List<T> ealierItems = new ArrayList<T>();

    public List<T> getRecentlyItems() {
        return this.recentlyItems;
    }

    public List<T> getLastWeekItems() {
        return this.lastWeekItems;
    }

    public List<T> getEalierItems() {
        return this.ealierItems;
    }

    public void setRecentlyItems(List<T> recentlyItems) {
        this.recentlyItems = recentlyItems;
    }

    public void setLastWeekItems(List<T> lastWeekItems) {
        this.lastWeekItems = lastWeekItems;
    }

    public void setEalierItems(List<T> ealierItems) {
        this.ealierItems = ealierItems;
    }

    public PeriodTvLog() {

    }

    public PeriodTvLog(List<T> recentlyItems, List<T> lastWeekItems, List<T> ealierItems) {
        this.recentlyItems = recentlyItems;
        this.lastWeekItems = lastWeekItems;
        this.ealierItems = ealierItems;
    }
}
