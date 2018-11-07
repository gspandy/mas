package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.LetvUserRoleDto;

import java.util.List;

/**
 * 角色列表
 * @author jijianlong
 */
public class LetvUserRoleListDto extends BaseDto {
    private Long userid;
    private String token;
    private List<LetvUserRoleDto> roleList;

    public List<LetvUserRoleDto> getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List<LetvUserRoleDto> roleList) {
        this.roleList = roleList;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
