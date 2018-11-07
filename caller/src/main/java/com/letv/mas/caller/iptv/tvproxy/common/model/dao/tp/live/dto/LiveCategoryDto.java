package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto;

import java.util.List;
import java.util.Map;

public class LiveCategoryDto {
    private String categoryId; // 分类id
    private String categoryName; // 分类名称
    private String categoryClient; // 分类所属的版权方
    private Map<String, String> categoryMultiTitle; // 分类名称的多语言
    private String categoryPic; // 分类图片
    private String color; // 分类的背景色
    private String parentCgId; // 上级分类id;
    private String categoryType; // 分类数据类型
    private String placeHolder; // 是否为占位分类
    private Integer dataSource;// 数据来源：1cms ,2直播部门
    private Integer dataType;// 1直播数据、2频道数据,3.直播、频道混合
    private Integer liveCounts;// 如果是直播分类，显示正在直播数
    private List<LiveChannelDto> channelList; // 分类下的频道列表
    private Integer startIndex; // 首屏数据index(直播分类)

    public LiveCategoryDto() {
    }

    public LiveCategoryDto(String cid, String cname, String cpic, String ccolor, int ds) {
        categoryId = cid;
        categoryName = cname;
        categoryPic = cpic;
        color = ccolor;
        dataSource = ds;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getLiveCounts() {
        return liveCounts;
    }

    public void setLiveCounts(Integer liveCounts) {
        this.liveCounts = liveCounts;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryClient() {
        return categoryClient;
    }

    public void setCategoryClient(String categoryClient) {
        this.categoryClient = categoryClient;
    }

    public Map<String, String> getCategoryMultiTitle() {
        return categoryMultiTitle;
    }

    public void setCategoryMultiTitle(Map<String, String> categoryMultiTitle) {
        this.categoryMultiTitle = categoryMultiTitle;
    }

    public String getCategoryPic() {
        return categoryPic;
    }

    public void setCategoryPic(String categoryPic) {
        this.categoryPic = categoryPic;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<LiveChannelDto> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<LiveChannelDto> channelList) {
        this.channelList = channelList;
    }

    public String getParentCgId() {
        return parentCgId;
    }

    public void setParentCgId(String parentCgId) {
        this.parentCgId = parentCgId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }
}
