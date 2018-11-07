package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LiveProjResponseTp implements Serializable {
    List<LiveProjectTp> rows = new ArrayList<LiveProjectTp>();

    public List<LiveProjectTp> getRows() {
        return rows;
    }

    public void setRows(List<LiveProjectTp> rows) {
        this.rows = rows;
    }
}
