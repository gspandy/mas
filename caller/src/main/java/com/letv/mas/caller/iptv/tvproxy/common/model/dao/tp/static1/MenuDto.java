package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MenuDto {
    private Integer id;// 菜单id
    private String name;// 菜单名称
    private String channelCode;// 频道号
    private Integer requestType;// 1 请求Tag列表 模板 2请求专辑列表模板，3，检索
    private Integer parentId;// 父级菜单id
    private String focusPicUrl;// 频道焦点图url
    private String parentName;// 父菜单名称
    private String isRank = "0";// 是否是排行榜菜单(产品有特殊需求)
    private Integer relationId;// 资源ID
    private Integer cid;
    private String defaultStream;
    private String sortStreamList;
    private String subCode = "";
    private String bigPic;
    private String smallPic;
    private String focus;
    private String color;
    private String focus2;

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getRelationId() {
        return this.relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getIsRank() {
        return this.isRank;
    }

    public void setIsRank(String isRank) {
        this.isRank = isRank;
    }

    public Integer getRequestType() {
        return this.requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelCode() {
        return this.channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getFocusPicUrl() {
        return this.focusPicUrl;
    }

    public void setFocusPicUrl(String focusPicUrl) {
        this.focusPicUrl = focusPicUrl;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getDefaultStream() {
        if (this.channelCode == null) {
            return LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_COMMON + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_DB + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_3D;
        }
        if (this.channelCode.indexOf("dolby") > -1) {
            return LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_DB + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_COMMON + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_3D;
        } else if (this.channelCode.indexOf("3d") > -1) {
            return LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_3D + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_COMMON + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_DB;
        } else if (this.channelCode.indexOf("1080p") > -1) {
            return LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_1080P + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_DB + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_3D;
        } else {
            return LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_COMMON + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_DB + "#"
                    + LetvStreamCommonConstants.SORT_SELECTED_STREAM_T2_3D;
        }
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

    public String getSortStreamList() {
        if (this.channelCode == null) {
            return LetvStreamCommonConstants.SORT_STREAM_T2_COMMON + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_DB
                    + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_3D;
        }
        if (this.channelCode.indexOf("dolby") > -1) {
            return LetvStreamCommonConstants.SORT_STREAM_T2_DB + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_COMMON
                    + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_3D;
        } else if (this.channelCode.indexOf("3d") > -1) {
            return LetvStreamCommonConstants.SORT_STREAM_T2_3D + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_COMMON
                    + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_DB;
        } else if (this.channelCode.indexOf("1080p") > -1) {
            return LetvStreamCommonConstants.SORT_STREAM_T2_COMMON + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_DB
                    + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_3D;
        } else {
            return LetvStreamCommonConstants.SORT_STREAM_T2_COMMON + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_DB
                    + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_3D;
        }
    }

    public String getSubCode() {
        return this.subCode;
    }

    public void setSortStreamList(String sortStreamList) {
        this.sortStreamList = sortStreamList;
    }

    public Integer getSearchTypeByUrl() {
        Integer type = -1;
        if (this.url != null) {
            String regEx = "searchType=([0-9]*)"; // 表示a或F
            Pattern pat = Pattern.compile(regEx);
            Matcher mat = pat.matcher(this.url);
            if (mat.find()) {
                type = new Integer(mat.group(1));
            }
        }
        return type;
    }

    public String getBigPic() {
        return this.bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getSmallPic() {
        return this.smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getFocus() {
        return this.focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFocus2() {
        return this.focus2;
    }

    public void setFocus2(String focus2) {
        this.focus2 = focus2;
    }
}