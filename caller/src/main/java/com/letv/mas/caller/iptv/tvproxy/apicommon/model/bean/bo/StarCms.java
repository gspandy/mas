package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;

public class StarCms extends BaseData {

    /**
     *
     */
    private static final long serialVersionUID = 4388912785340432105L;
    private String starId;// 演员id
    private String name;// 演员姓名
    private Integer role;// 演员角色
    private String img;// 头像

    public int getDataType() {
        return DataConstant.DATA_TYPE_STAR;
    }

    public String getStarId() {
        return this.starId;
    }

    public void setStarId(String starId) {
        this.starId = starId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return this.role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
