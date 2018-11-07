package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean;

import java.util.Map;

/**
 * Created by wangli on 5/3/17.
 * 专辑信息类
 */
public class VRAlbum {

    // 专辑序号
    private String id;
    // 图片信息
    private Map<String, String> picCollections;
    // 描述信息
    private String description;
    // 副标题
    private String subTitle;
    // 专辑中文名称
    private String nameCn;
    // 得分信息
    private Float score;
    // 专辑类型
    private Map<String, String> category;
    // 专辑子类型
    private Map<String, String> subCategory;
    // 是否付费
    private Integer isPay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getPicCollections() {
        return picCollections;
    }

    public void setPicCollections(Map<String, String> picCollections) {
        this.picCollections = picCollections;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Map<String, String> getCategory() {
        return category;
    }

    public void setCategory(Map<String, String> category) {
        this.category = category;
    }

    public Map<String, String> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Map<String, String> subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
}
