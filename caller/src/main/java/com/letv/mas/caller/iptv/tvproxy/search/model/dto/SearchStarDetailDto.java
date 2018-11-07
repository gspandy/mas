package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.StarDetailTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;

public class SearchStarDetailDto extends BaseDto {
    private static final long serialVersionUID = -3900290126650251358L;
    private String area;
    private String birthday;
    private String createTime;
    private String deleted;
    private String description;
    private String display;
    private String englishname;
    private String gender;
    private String id;
    private String isAvail;
    private String isactor;
    private String isdirector;
    private String isend;
    private String name;
    private String othername;
    private String pinyinabb;
    private String postH1;
    private String postH2;
    private String postOrigin;
    private String postS1;
    private String postS2;
    private String postS3;
    private String professional;
    private String pushflag;
    private String status;
    private String truename;
    private String updateTime;
    private String updateUid;
    private String userId;

    public SearchStarDetailDto() {
    }

    public SearchStarDetailDto(StarDetailTpResponse.StarDetailDto detail) {
        if (detail != null) {
            this.area = detail.getAreaName();
            this.birthday = TimeUtil.getDateStringFromLong(detail.getBirthday(), TimeUtil.SHORT_DATE_FORMAT);

            this.description = detail.getDescription();
            this.englishname = detail.getEnglishname();
            if (detail.getId() != null) {
                this.id = detail.getId().toString();
            }
            this.name = detail.getName();
            this.othername = detail.getOthername();
            this.postS1 = detail.getHeadPicWeb34() + "/34_300_400.jpg";
            this.postS2 = detail.getPostS2();
            this.postS3 = detail.getPostS3();
            this.professional = detail.getProfessional();
        }
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeleted() {
        return this.deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getEnglishname() {
        return this.englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsAvail() {
        return this.isAvail;
    }

    public void setIsAvail(String isAvail) {
        this.isAvail = isAvail;
    }

    public String getIsactor() {
        return this.isactor;
    }

    public void setIsactor(String isactor) {
        this.isactor = isactor;
    }

    public String getIsdirector() {
        return this.isdirector;
    }

    public void setIsdirector(String isdirector) {
        this.isdirector = isdirector;
    }

    public String getIsend() {
        return this.isend;
    }

    public void setIsend(String isend) {
        this.isend = isend;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOthername() {
        return this.othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    public String getPinyinabb() {
        return this.pinyinabb;
    }

    public void setPinyinabb(String pinyinabb) {
        this.pinyinabb = pinyinabb;
    }

    public String getPostH1() {
        return this.postH1;
    }

    public void setPostH1(String postH1) {
        this.postH1 = postH1;
    }

    public String getPostH2() {
        return this.postH2;
    }

    public void setPostH2(String postH2) {
        this.postH2 = postH2;
    }

    public String getPostOrigin() {
        return this.postOrigin;
    }

    public void setPostOrigin(String postOrigin) {
        this.postOrigin = postOrigin;
    }

    public String getPostS1() {
        return this.postS1;
    }

    public void setPostS1(String postS1) {
        this.postS1 = postS1;
    }

    public String getPostS2() {
        return this.postS2;
    }

    public void setPostS2(String postS2) {
        this.postS2 = postS2;
    }

    public String getPostS3() {
        return this.postS3;
    }

    public void setPostS3(String postS3) {
        this.postS3 = postS3;
    }

    public String getProfessional() {
        return this.professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getPushflag() {
        return this.pushflag;
    }

    public void setPushflag(String pushflag) {
        this.pushflag = pushflag;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTruename() {
        return this.truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUid() {
        return this.updateUid;
    }

    public void setUpdateUid(String updateUid) {
        this.updateUid = updateUid;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
