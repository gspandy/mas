package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@XmlRootElement
@JsonAutoDetect
@JsonPropertyOrder({ "statusCode", "data" })
public class MmsStore {
    private String statusCode;// 返回状态 1001：成功；1002：必填参数为空；1003参数不合法；1004：密钥不合法
    private List<MmsInfo> data;
    private Integer playStatus;// 是否允许播放 0：正常，1：禁播
    private String country;// 用户当前所在地国家码

    public List<MmsInfo> getData() {
        return this.data;
    }

    public void setData(List<MmsInfo> data) {
        this.data = data;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(Integer playStatus) {
        this.playStatus = playStatus;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
