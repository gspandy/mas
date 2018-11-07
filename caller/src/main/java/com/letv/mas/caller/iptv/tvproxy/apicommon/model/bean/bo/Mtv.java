package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;

public class Mtv extends BaseData {

    private String labelIdToPlay;

    public String getLabelIdToPlay() {
        return labelIdToPlay;
    }

    public void setLabelIdToPlay(String labelIdToPlay) {
        this.labelIdToPlay = labelIdToPlay;
    }

    public int getDataType() {
        return DataConstant.DATA_TYPE_MUSIC;
    }
}
