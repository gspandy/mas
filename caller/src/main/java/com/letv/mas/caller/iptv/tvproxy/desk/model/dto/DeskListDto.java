package com.letv.mas.caller.iptv.tvproxy.desk.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;

import java.util.List;

public class DeskListDto extends BaseData {

    /**
     *
     */
    private static final long serialVersionUID = 7002487035203908508L;
    /**
     * 不分屏展示时只是用一个字段
     */
    private List<DeskDto> firstDeskData;

    /**
     * 分屏时采用此字段
     */
    private List<DeskDto> secondDeskData;

    public List<DeskDto> getFirstDeskData() {
        return this.firstDeskData;
    }

    public void setFirstDeskData(List<DeskDto> firstDeskData) {
        this.firstDeskData = firstDeskData;
    }

    public List<DeskDto> getSecondDeskData() {
        return this.secondDeskData;
    }

    public void setSecondDeskData(List<DeskDto> secondDeskData) {
        this.secondDeskData = secondDeskData;
    }

}
