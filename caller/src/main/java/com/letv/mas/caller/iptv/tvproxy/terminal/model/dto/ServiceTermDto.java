package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import java.io.Serializable;

/**
 * Service Terms dto, show any service terms text.
 * @author KevinYi
 */
public class ServiceTermDto implements Serializable {

    /**
     * type of this service term, 1--Privacy Policy, 2--Terms of Service
     */
    private Integer termType;

    /**
     * url of the term text; designed for the feature
     */
    private String dataUrl;

    /**
     * text content
     */
    private String content;

    /**
     * 1--plain text, 2--formatted html
     */
    private Integer contentType;

    public Integer getTermType() {
        return termType;
    }

    public void setTermType(Integer termType) {
        this.termType = termType;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

}
