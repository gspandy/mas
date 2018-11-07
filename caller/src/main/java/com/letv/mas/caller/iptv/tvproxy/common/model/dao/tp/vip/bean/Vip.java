package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

/**
 * 会员信息
 */
public class Vip {
    private Integer id; // 会员id
    private boolean selected;
    private Integer category; // 会员类型,见VipTpConstant.Type_Group_US
    private String name; // 会员名称
    private String pic; // 图片
    private String ext; // 描述信息
    private Integer country; // 国家
    private TrialData trialData; // 试用信息

    private Integer productId; // 会员id，与上面id相同
    private Integer typeGroup;// 同上面category

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(Integer typeGroup) {
        this.typeGroup = typeGroup;
    }

    public TrialData getTrialData() {
        return trialData;
    }

    public void setTrialData(TrialData trialData) {
        this.trialData = trialData;
    }
}
