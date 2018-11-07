package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.io.Serializable;

/**
 * 桌面配置
 * @author jijianlong
 */
public class DeskConfigMysqlTable implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4008127815819187376L;
    private Integer inner_id;
    private String inner_title;
    private String title;
    private String img;
    private String subtitle;
    private String action;
    private Integer type;
    private Integer model;
    private String value;
    private Integer resource;
    private Integer order;
    private Integer page;
    private String small_img;

    public Integer getInner_id() {
        return this.inner_id;
    }

    public void setInner_id(Integer inner_id) {
        this.inner_id = inner_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getModel() {
        return this.model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getResource() {
        return this.resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSmall_img() {
        return this.small_img;
    }

    public void setSmall_img(String small_img) {
        this.small_img = small_img;
    }

    public String getInner_title() {
        return this.inner_title;
    }

    public void setInner_title(String inner_title) {
        this.inner_title = inner_title;
    }

}
