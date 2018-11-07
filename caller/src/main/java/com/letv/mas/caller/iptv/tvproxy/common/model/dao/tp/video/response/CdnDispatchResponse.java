package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "watcher")
public class CdnDispatchResponse {

    private String host;
    private String geo;
    private String desc;
    private String flag;
    private Integer expect;
    private Integer actual;
    private NodeList nodelist;

    public CdnDispatchResponse() {

    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getGeo() {
        return this.geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getExpect() {
        return this.expect;
    }

    public void setExpect(Integer expect) {
        this.expect = expect;
    }

    public Integer getActual() {
        return this.actual;
    }

    public void setActual(Integer actual) {
        this.actual = actual;
    }

    public NodeList getNodelist() {
        return this.nodelist;
    }

    public void setNodelist(NodeList nodelist) {
        this.nodelist = nodelist;
    }

    public static class NodeList {

        List<String> urls;

        @XmlElement(name = "node")
        public List<String> getUrls() {
            return this.urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
