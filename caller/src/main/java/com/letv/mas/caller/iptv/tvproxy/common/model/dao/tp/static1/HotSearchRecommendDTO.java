package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

/**
 * 热搜推荐
 */
public class HotSearchRecommendDTO {
    private String pushFlag;
    private String categoryName;
    private String poster20;
    private String model;
    private String name;
    private Integer aid;
    private String src;
    private String subTitle;
    private String img400x300;

    public HotSearchRecommendDTO() {

    }

    public HotSearchRecommendDTO(Integer aid, String pushFlag, String categoryName, String poster20, String model,
            String name, String src, String subTitle, String img400x300) {
        this.aid = aid;
        this.pushFlag = pushFlag;
        this.categoryName = categoryName;
        this.poster20 = poster20;
        this.model = model;
        this.name = name;
        this.src = src;
        this.subTitle = subTitle;
        this.img400x300 = img400x300;
    }

    public String getPushFlag() {
        return this.pushFlag;
    }

    public void setPushFlag(String pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPoster20() {
        return this.poster20;
    }

    public void setPoster20(String poster20) {
        this.poster20 = poster20;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAid() {
        return this.aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImg400x300() {
        return img400x300;
    }

    public void setImg400x300(String img400x300) {
        this.img400x300 = img400x300;
    }

    @Override
    public String toString() {
        return "HotSearchRecommendDTO [pushFlag=" + this.pushFlag + ", categoryName=" + this.categoryName
                + ", poster20=" + this.poster20 + ", model=" + this.model + ", name=" + this.name + ", aid=" + this.aid
                + ", src=" + this.src + ", subTitle=" + this.subTitle + "]";
    }

}
