package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.io.Serializable;

public class LetvUserRoleDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2244950189791393764L;
    /*
     * 角色id
     */
    private Long roleid;
    private String roleType;
    /*
     * 本角色生日
     */
    private String brithday;
    /*
     * 性别 M：男，F：女
     */
    private String gender;
    private String nickName;
    /*
     * 播放时间控制
     */
    private String timeStart;
    private String timeEnd;
    private String duration;
    private String setAge;
    private Long timestamp;

    public Long getRoleid() {
        return this.roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getBrithday() {
        return this.brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTimeStart() {
        return this.timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return this.timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSetAge() {
        return this.setAge;
    }

    public void setSetAge(String setAge) {
        this.setAge = setAge;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
