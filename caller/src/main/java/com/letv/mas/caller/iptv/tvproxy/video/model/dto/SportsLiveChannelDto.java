package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 体育直播节目单项
 * @author letv
 */
public class SportsLiveChannelDto extends BaseDto {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7568290631941085694L;
    private String subjectName;// 栏目名称，如西甲、中超
    private Long timestamp = System.currentTimeMillis();

    private List<SportsLiveProgramDto> items = new ArrayList<SportsLiveProgramDto>();// 栏目下的节目单列表

    public SportsLiveChannelDto(List<SportsLiveProgramDto> list, Integer type) {
        if (type != null && type == 0) {
            this.subjectName = "体育Live";
        } else {
            this.subjectName = "体育Live";
        }
        this.items = list;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<SportsLiveProgramDto> getItems() {
        return this.items;
    }

    public void setItems(List<SportsLiveProgramDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SportsLiveChannelDto [subjectName=" + this.subjectName + ", timestamp=" + this.timestamp + ", items="
                + this.items + "]";
    }

}
