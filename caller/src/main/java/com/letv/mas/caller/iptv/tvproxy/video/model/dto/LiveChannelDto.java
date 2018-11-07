package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.ResourceDto;

import java.util.List;

public class LiveChannelDto extends BaseDto {

    /**
     * 
     */
    private static final long serialVersionUID = -5733962355284263108L;
    private Long timestamp = System.currentTimeMillis();
    private List<ResourceDto> resources;
    private List<LiveProgramDto> programs;
    private Integer total;

    public LiveChannelDto() {

    }

    public LiveChannelDto(List<LiveProgramDto> programs, String subjectName) {
        this.programs = programs;
    }

    // public class Resource {
    // private String id;
    // private String type;
    // private String img929x466;
    // private String img400x300;
    // private String title;
    // private String subTitle;
    // private String shortDesc;
    // public String getId() {
    // return id;
    // }
    // public void setId(String id) {
    // this.id = id;
    // }
    // public String getType() {
    // return type;
    // }
    // public void setType(String type) {
    // this.type = type;
    // }
    // public String getImg929x466() {
    // return img929x466;
    // }
    // public void setImg929x466(String img929x466) {
    // this.img929x466 = img929x466;
    // }
    // public String getImg400x300() {
    // return img400x300;
    // }
    // public void setImg400x300(String img400x300) {
    // this.img400x300 = img400x300;
    // }
    // public String getTitle() {
    // return title;
    // }
    // public void setTitle(String title) {
    // this.title = title;
    // }
    // public String getSubTitle() {
    // return subTitle;
    // }
    // public void setSubTitle(String subTitle) {
    // this.subTitle = subTitle;
    // }
    // public String getShortDesc() {
    // return shortDesc;
    // }
    // public void setShortDesc(String shortDesc) {
    // this.shortDesc = shortDesc;
    // }
    //
    // }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<LiveProgramDto> getPrograms() {
        return programs;
    }

    public void setPrograms(List<LiveProgramDto> programs) {
        this.programs = programs;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

}
