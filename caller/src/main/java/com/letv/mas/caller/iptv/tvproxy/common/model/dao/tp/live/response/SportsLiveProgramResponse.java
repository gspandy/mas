package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 体育直播节目单返回对象
 * @author letv
 */
@XmlRootElement
public class SportsLiveProgramResponse {
    private Integer status;// 请求状态
    private String msg;// 错误信息
    private String subjectName;// 栏目名称，如西甲、中超
    private Integer subjectId;// 栏目ID
    private String programDate;
    private Long timestamp = System.currentTimeMillis();

    private List<SportsLiveProgram> items = new ArrayList<SportsLiveProgram>();// 栏目下的节目单列表

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public List<SportsLiveProgram> getItems() {
        return this.items;
    }

    public void setItems(List<SportsLiveProgram> items) {
        this.items = items;
    }

    public String getProgramDate() {
        return this.programDate;
    }

    public void setProgramDate(String programDate) {
        this.programDate = programDate;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
