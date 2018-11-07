package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签与itvalbum的关系表
 * @author letv
 */
public class ItvLabelResource implements Serializable {
    private static final long serialVersionUID = 1567416705733553886L;
    private Integer id;
    private Integer labelId;// itvLabel.id
    private Integer itvAlbumId;// iptvalbum.id
    private Integer categoryId;// 分类ID
    private Integer isNew = 0;// 是否推新
    private Integer isTop = 0;// 是否置顶
    private Integer isAuto = 1;// 是否是程序自动推
    private Integer orderNum;// 排序号
    private Integer status;// 是否有效，1有效，0无效
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private String createdBy;// 创建人
    private String updatedBy;// 更新人

    // 添加标签视频属性
    private String pic;// 视频图片，默认是专辑海报，主要是由编辑上传
    private Integer videoInfoId;// 视频信息id
    private String codeVersion;// 码流
    private Integer resourceType;// 专辑为0、视频为1 、专题为2
    private String title;// 视频title

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsTop() {
        return this.isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getLabelId() {
        return this.labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getItvAlbumId() {
        return this.itvAlbumId;
    }

    public void setItvAlbumId(Integer itvAlbumId) {
        this.itvAlbumId = itvAlbumId;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getIsNew() {
        return this.isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsAuto() {
        return this.isAuto;
    }

    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getVideoInfoId() {
        return this.videoInfoId;
    }

    public void setVideoInfoId(Integer videoInfoId) {
        this.videoInfoId = videoInfoId;
    }

    public String getCodeVersion() {
        return this.codeVersion;
    }

    public void setCodeVersion(String codeVersion) {
        this.codeVersion = codeVersion;
    }

    public Integer getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

}
