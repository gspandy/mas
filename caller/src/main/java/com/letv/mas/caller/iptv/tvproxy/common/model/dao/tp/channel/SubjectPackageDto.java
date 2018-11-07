package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel;

import java.io.Serializable;
import java.util.List;

/**
 * 专题内（专辑、视频或直播）数据包信息封装类，
 * 对应CmsSubjectTpResponse.CmsSubjectDataTpResponse.CmsSubjectTjPackageTpResponse
 * @author KevinYi
 */
public class SubjectPackageDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2901320257544290902L;

    /**
     * 剧集包、视频包或直播包的id，可以根据该id获取包内具体数据，如剧集、视频或直播列表
     */
    private String id;

    /**
     * json域
     */
    private String jsonField;

    /**
     * 包名
     */
    private String name;

    /**
     * 包的排序，porder越小越靠前
     */
    private Integer porder;

    /**
     * 特辑包类型，1-专辑包，2-视频包，3-直播包
     */
    private Integer ptype;

    /**
     * 所属专题id
     */
    private String tjId;

    /**
     * 专辑包图片
     */
    private String packagePic;

    /**
     * 剧集包、视频包或直播包数据列表
     */
    private List<SubjectPackageDataDto> dataList;

    private Integer dataListSize;

    public Integer getDataListSize() {
        return dataListSize;
    }

    public void setDataListSize(Integer dataListSize) {
        this.dataListSize = dataListSize;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonField() {
        return this.jsonField;
    }

    public void setJsonField(String jsonField) {
        this.jsonField = jsonField;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPorder() {
        return this.porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public Integer getPtype() {
        return this.ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public String getTjId() {
        return this.tjId;
    }

    public void setTjId(String tjId) {
        this.tjId = tjId;
    }

    public String getPackagePic() {
        return this.packagePic;
    }

    public void setPackagePic(String packagePic) {
        this.packagePic = packagePic;
    }

    public List<SubjectPackageDataDto> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<SubjectPackageDataDto> dataList) {
        this.dataList = dataList;
    }

}
