package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.List;

public class CmsDataResponse {
    private static final long serialVersionUID = -6591011686372101743L;

    private Integer cid;// 版块所属频道
    private Integer id;// 版块ID
    private String name;//
    private List<CmsDataInfo> blockContent;

    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CmsDataInfo> getBlockContent() {
        return this.blockContent;
    }

    public void setBlockContent(List<CmsDataInfo> blockContent) {
        this.blockContent = blockContent;
    }

}
