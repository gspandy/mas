package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.util.ArrayList;
import java.util.List;

public class TvStreamResponseTp {
    List<TvStreamInfo> rows = new ArrayList<TvStreamInfo>();

    public List<TvStreamInfo> getRows() {
        return rows;
    }

    public void setRows(List<TvStreamInfo> rows) {
        this.rows = rows;
    }

}
