package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.LetvUserRoleDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class UserRoleRequest {
    private final Logger log = LoggerFactory.getLogger(UserRoleRequest.class);

    private String username;
    private String loginTime;
    private Long userid;
    private String mac;
    private String token;
    private Long roleid;
    private String nickname;
    private String roleType;
    private String timeStart;
    private String timeEnd;
    private String duration;
    private String gender;
    private String brithday;
    private String setAge;
    private String clientip;

    public UserRoleRequest(String username, String loginTime, Long userid, String mac, String token, String nickname,
            String roleType, String timeStart, String timeEnd, String duration, String gender, String brithday,
            String clientip, String setAge) {
        this.username = username;
        this.loginTime = loginTime;
        this.userid = userid;
        this.mac = mac;
        this.token = token;
        this.nickname = nickname;
        this.roleType = roleType;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.duration = duration;
        this.gender = gender;
        this.brithday = brithday;
        this.clientip = clientip;
        this.setAge = setAge;
    }

    public UserRoleRequest(String username, Long userid, String token, Long roleid, String clientip) {
        this.username = username;
        this.userid = userid;
        this.token = token;
        this.roleid = roleid;
        this.clientip = clientip;
    }

    public UserRoleRequest(String username, String loginTime, Long userid, String mac, String token, Long roleid,
            String nickname, String roleType, String timeStart, String timeEnd, String duration, String gender,
            String brithday, String clientip, String setAge) {
        super();
        this.username = username;
        this.loginTime = loginTime;
        this.userid = userid;
        this.mac = mac;
        this.token = token;
        this.roleid = roleid;
        this.nickname = nickname;
        this.roleType = roleType;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.duration = duration;
        this.gender = gender;
        this.brithday = brithday;
        this.clientip = clientip;
        this.setAge = setAge;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (this.userid == null || this.userid < 1) {
            return UserMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        if (StringUtils.isBlank(this.token)) {
            return UserMsgCodeConstant.USER_REQUEST_DATA_TOKEN_ISBANK;
        }
        /*
         * if (StringUtils.isBlank(this.username)) {
         * return UserMsgCodeConstant.USER_REQUEST_DATA_USERNAME_ISBANK;
         * }
         */
        return UserMsgCodeConstant.REQUEST_SUCCESS;
    }

    /**
     * 删除接口参数校验
     * @return
     */
    public int assertValid4DeleteOrUpdate() {
        if (this.roleid == null || this.roleid < 1) {// 删除角色时，角色id不能为空
            return UserMsgCodeConstant.USER_REQUEST_DATA_ROLEID_ISBANK;
        }
        return this.assertValid();
    }

    /**
     * 更新角色参数校验
     * @return
     */
    public int updateAssertValid() {
        if (StringUtils.isBlank(String.valueOf(this.userid))) {
            return UserMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        if (StringUtils.isBlank(this.token)) {
            return UserMsgCodeConstant.USER_REQUEST_DATA_TOKEN_ISBANK;
        }
        return UserMsgCodeConstant.REQUEST_SUCCESS;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
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

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBrithday() {
        return this.brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getClientip() {
        return this.clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public Long getRoleid() {
        return this.roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getSetAge() {
        return this.setAge;
    }

    public void setSetAge(String setAge) {
        this.setAge = setAge;
    }

    /**
     * 增加角色接口参数
     * @return
     */
    public MultiValueMap<String, String> addParametersMap() {
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add("tk", StringUtils.trimToEmpty(this.token));
        parametersMap.add("ip", StringUtils.trimToEmpty(this.clientip));
        parametersMap.add("content", this.getContent());

        return parametersMap;
    }

    /**
     * 更新角色接口参数
     * @return
     */
    public MultiValueMap<String, Object> updateParametersMap() {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("tk", StringUtils.trimToEmpty(this.token));
        param.add("content", this.getContent());
        param.add("rid", String.valueOf(this.roleid));

        return param;
    }

    /**
     * 获取角色列表接口参数
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();
        parametersMap.put("uid", this.userid);
        return parametersMap;
    }

    /**
     * 删除角色接口参数
     * @return
     */
    public MultiValueMap<String, Object> deleteParametersMap() {
        MultiValueMap<String, Object> parametersMap = new LinkedMultiValueMap<String, Object>();
        parametersMap.add("tk", this.token);
        parametersMap.add("rid", String.valueOf(this.roleid));
        return parametersMap;
    }

    private String getContent() {
        LetvUserRoleDto LetvUserRoleDto = new LetvUserRoleDto();
        LetvUserRoleDto.setRoleid(this.roleid);
        LetvUserRoleDto.setNickName(this.nickname);
        LetvUserRoleDto.setRoleType(this.roleType);
        LetvUserRoleDto.setBrithday(this.brithday);
        LetvUserRoleDto.setGender(this.gender);
        LetvUserRoleDto.setDuration(this.duration);
        LetvUserRoleDto.setTimeStart(this.timeStart);
        LetvUserRoleDto.setTimeEnd(this.timeEnd);
        LetvUserRoleDto.setSetAge(this.setAge);
        LetvUserRoleDto.setTimestamp(System.currentTimeMillis());
        ObjectMapper m = new ObjectMapper();
        String value = null;
        try {
            value = m.writeValueAsString(LetvUserRoleDto);
        } catch (Exception e) {
            log.error("getContent_error", e.getMessage(), e);
        }
        return value;
    }

    public String getContentForUpdate(LetvUserRoleDto reRole) {
        LetvUserRoleDto LetvUserRoleDto = new LetvUserRoleDto();
        if (reRole != null) {
            LetvUserRoleDto = this.setLetvUserRoleDto(reRole);
        }
        if (this.roleid != null) {
            LetvUserRoleDto.setRoleid(this.roleid);
        }
        if (StringUtils.isNotBlank(this.nickname)) {
            LetvUserRoleDto.setNickName(this.nickname);
        }
        if (StringUtils.isNotBlank(this.roleType)) {
            LetvUserRoleDto.setRoleType(this.roleType);
        }
        if (StringUtils.isNotBlank(this.brithday)) {
            LetvUserRoleDto.setBrithday(this.brithday);
        }
        if (StringUtils.isNotBlank(this.gender)) {
            LetvUserRoleDto.setGender(this.gender);
        }
        if (StringUtils.isNotBlank(this.duration)) {
            LetvUserRoleDto.setDuration(this.duration);
        }
        if (StringUtils.isNotBlank(this.timeStart)) {
            LetvUserRoleDto.setTimeStart(this.timeStart);
        }
        if (StringUtils.isNotBlank(this.timeEnd)) {
            LetvUserRoleDto.setTimeEnd(this.timeEnd);
        }
        if (StringUtils.isNotBlank(this.setAge)) {
            LetvUserRoleDto.setSetAge(this.setAge);
        }
        LetvUserRoleDto.setTimestamp(System.currentTimeMillis());
        ObjectMapper m = new ObjectMapper();
        String value = null;
        try {
            value = m.writeValueAsString(LetvUserRoleDto);
        } catch (Exception e) {
            log.error("getContentForUpdate_error", e.getMessage(), e);
        }
        return value;
    }

    private LetvUserRoleDto setLetvUserRoleDto(LetvUserRoleDto reRole) {
        String logPrefix = "setLetvUserRoleDto_";
        LetvUserRoleDto LetvUserRoleDto = null;
        if (reRole != null) {
            LetvUserRoleDto = new LetvUserRoleDto();
            LetvUserRoleDto.setRoleType(reRole.getRoleType());
            LetvUserRoleDto.setNickName(reRole.getNickName());
            LetvUserRoleDto.setBrithday(reRole.getBrithday());
            LetvUserRoleDto.setGender(reRole.getGender());
            LetvUserRoleDto.setTimeStart(reRole.getTimeStart());
            LetvUserRoleDto.setTimeEnd(reRole.getTimeEnd());
            LetvUserRoleDto.setDuration(reRole.getDuration());
            LetvUserRoleDto.setSetAge(reRole.getSetAge());
            LetvUserRoleDto.setTimestamp(System.currentTimeMillis());
        }
        return LetvUserRoleDto;
    }

    public String getPreFixLog() {
        return "UserRoleRequest_[ userid=" + this.userid + ", mac=" + this.mac + ", roleid=" + this.roleid
                + ", roleType=" + this.roleType + ", clientip=" + this.clientip + "]";
    }

}
