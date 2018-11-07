package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms;

import java.io.Serializable;
import java.util.List;

/**
 * CMS板块数据
 */
public class CmsBlockTpResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5433838841114606497L;

    private Integer cid;// 版块所属频道

    private Integer id;// 版块ID

    private String name;// 版块名称

    private Integer pid;//

    private Integer pubStatus;//

    private Long puddate;//

    private List<CmsBlockContentTpResponse> blockContent;// 版块内容列表

    /*
     * 父级板块内容
     */
    private List<CmsBlockTpResponse> subBlocks;
    /*
     * 子版块id列表
     */
    private List<String> subBlockList;

    private String startTime;

    private String endTime;

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

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPubStatus() {
        return this.pubStatus;
    }

    public void setPubStatus(Integer pubStatus) {
        this.pubStatus = pubStatus;
    }

    public Long getPuddate() {
        return this.puddate;
    }

    public void setPuddate(Long puddate) {
        this.puddate = puddate;
    }

    public List<CmsBlockContentTpResponse> getBlockContent() {
        return this.blockContent;
    }

    public void setBlockContent(List<CmsBlockContentTpResponse> blockContent) {
        this.blockContent = blockContent;
    }

    public List<CmsBlockTpResponse> getSubBlocks() {
        return this.subBlocks;
    }

    public void setSubBlocks(List<CmsBlockTpResponse> subBlocks) {
        this.subBlocks = subBlocks;
    }

    public List<String> getSubBlockList() {
        return this.subBlockList;
    }

    public void setSubBlockList(List<String> subBlockList) {
        this.subBlockList = subBlockList;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
