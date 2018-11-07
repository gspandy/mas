package com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;

/**
 * 专题--超级影院，因为目前超前院线详情页使用专题配置，而模型设计上没有直接复用Subject，而是继承。
 */
public class SubjectPreLive extends Subject {

    /**
     * 超前院线id，与subjectId值相同
     */
    private String cinemaId;

    public String getCinemaId() {
        return this.cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getDataType() {
        return DataConstant.DATA_TYPE_PRELIVE;
    }
}
