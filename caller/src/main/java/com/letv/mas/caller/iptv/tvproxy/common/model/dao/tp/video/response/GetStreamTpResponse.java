package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.List;

import search2.extractor.ExtractData;

public class GetStreamTpResponse {
    private List<ExtractData> data;

    public List<ExtractData> getData() {
        return this.data;
    }

    public void setData(List<ExtractData> data) {
        this.data = data;
    }

    public GetStreamTpResponse() {
    }

    public GetStreamTpResponse(List<ExtractData> data) {
        this.data = data;
    }
}
