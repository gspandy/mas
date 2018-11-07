package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

/**
 * 检查用户追剧收藏状态dto
 * @author dunhongqin
 */
public class CheckPlaylistDto extends BaseDto {
    private static final long serialVersionUID = 40371840952466702L;

    private String code;
    private Integer id; // 收藏id
    private Integer fromtype;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromtype() {
        return this.fromtype;
    }

    public void setFromtype(Integer fromtype) {
        this.fromtype = fromtype;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
