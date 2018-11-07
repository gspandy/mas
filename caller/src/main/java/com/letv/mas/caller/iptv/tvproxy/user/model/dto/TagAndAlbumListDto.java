package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.LetvUserPlayListTp.LetvUserPlayList;

import java.util.ArrayList;
import java.util.List;

/**
 * 播放单，追剧,收藏dto
 * @author dunhongqin
 */
public class TagAndAlbumListDto extends BaseDto {
    private Integer listType = 1;// 1专辑 2视频
    private Integer tagId;
    private String tagName;
    private String url;
    private String realChannelCode;
    private List<LetvUserPlayList> items = new ArrayList<LetvUserPlayList>();

    public Integer getTagId() {
        return this.tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRealChannelCode() {
        return this.realChannelCode;
    }

    public void setRealChannelCode(String realChannelCode) {
        this.realChannelCode = realChannelCode;
    }

    public List<LetvUserPlayList> getItems() {
        return this.items;
    }

    public void setItems(List<LetvUserPlayList> items) {
        this.items = items;
    }

    public Integer getListType() {
        return this.listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

}
