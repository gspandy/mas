package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import org.springframework.util.CollectionUtils;
import search2.extractor.ExtractData;
import search2.extractor.Format;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetStreamDto {
    /**
     * 相关的wiki http://wiki.letv.cn/pages/viewpage.action?pageId=48638050
     */
    private List<String> streamList; // 流地址（分段）
    private Integer format; // 请求的清晰度
    private Set<Integer> allowedFormats;// 解析支持的清晰度集合
    private List<String> apiList; // 二次运算的内部接口 wiki中有说明
    private String rule; // 二次运算特殊规则 wiki中有说明

    public GetStreamDto() {
    }

    public GetStreamDto(ExtractData data) {
        this.streamList = data.getStream_list();
        if (data.getRequest_format() != null) {
            this.format = data.getRequest_format().getValue();
        }
        if (!CollectionUtils.isEmpty(data.getAllowed_formats())) {
            this.allowedFormats = new HashSet<Integer>();
            for (Format temp : data.getAllowed_formats()) {
                this.allowedFormats.add(temp.getValue());
            }
        }
        this.apiList = data.getApi_list();
        this.rule = data.getRule();

    }

    public List<String> getStreamList() {
        return this.streamList;
    }

    public void setStreamList(List<String> streamList) {
        this.streamList = streamList;
    }

    public Integer getFormat() {
        return this.format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public Set<Integer> getAllowedFormats() {
        return this.allowedFormats;
    }

    public void setAllowedFormats(Set<Integer> allowedFormats) {
        this.allowedFormats = allowedFormats;
    }

    public List<String> getApiList() {
        return this.apiList;
    }

    public void setApiList(List<String> apiList) {
        this.apiList = apiList;
    }

    public String getRule() {
        return this.rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

}
