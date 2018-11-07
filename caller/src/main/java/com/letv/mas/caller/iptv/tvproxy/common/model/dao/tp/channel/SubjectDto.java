package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel;

import java.io.Serializable;
import java.util.List;

/**
 * CMS专题信息封装类，包含专题基本信息，和专题下配置的专辑包、视频包、直播包等
 * @author KevinYi
 */
public class SubjectDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4758611377137071491L;

    /**
     * 专题ID
     */
    private String id;

    /**
     * 专题名称
     */
    private String name;

    /**
     * 副标题
     */
    private String subName;

    /**
     * 图片
     */
    private String tvPic;

    /**
     * 专题类型，1--专辑专题，0--视频专题，2--直播专题聚合页，3--直播专题详情页，4--多专辑专题包，5--时间轴，
     * 参见SubjectConstant.SUBJECTTYPE_*
     */
    private Integer subjectType;

    /**
     * 专题发布名称
     */
    private String pubName;

    /**
     *
     */
    private String pvcid;

    /**
     * 运营上报使用，由CMS接口返回
     */
    private String cid;

    /**
     * 专辑包、视频包、直播包列表，列表内不区分包类型，按序混合存放
     */
    private List<SubjectPackageDto> packageList;

    /**
     * 儿童多视频包专题的一个角标主题透明图
     */
    private String cornerPic;

    /**
     * 专题Id
     */
    private String subjectId;

    private Integer dataType;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getTvPic() {
        return this.tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

    public Integer getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public String getPubName() {
        return this.pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getPvcid() {
        return this.pvcid;
    }

    public void setPvcid(String pvcid) {
        this.pvcid = pvcid;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<SubjectPackageDto> getPackageList() {
        return this.packageList;
    }

    public void setPackageList(List<SubjectPackageDto> packageList) {
        this.packageList = packageList;
    }

    public String getCornerPic() {
        return cornerPic;
    }

    public void setCornerPic(String cornerPic) {
        this.cornerPic = cornerPic;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}