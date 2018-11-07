package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.util.ArrayList;
import java.util.List;

public class LiveProjectResponseTp {
    private Integer code;
    private ProjectData data;
    private String msg;

    public LiveProjectResponseTp() {

    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ProjectData getData() {
        return this.data;
    }

    public void setData(ProjectData data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ProjectData {
        private String description;// 专辑简介
        private String name;// 专辑名称
        private String pubName;// 发布名称
        private String tag;// 标签
        private String tvPic = "";// 背景图地址
        private ProjectPackage tjPackage = new ProjectPackage();
        private List<ProjectPackage> tjPackages;
        /*
         * 专题里的包id列表
         */
        private List<String> packageIds;

        public ProjectData() {

        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPubName() {
            return this.pubName;
        }

        public void setPubName(String pubName) {
            this.pubName = pubName;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTvPic() {
            return this.tvPic;
        }

        public void setTvPic(String tvPic) {
            this.tvPic = tvPic;
        }

        public List<ProjectPackage> getTjPackages() {
            return this.tjPackages;
        }

        public void setTjPackages(List<ProjectPackage> tjPackages) {
            this.tjPackages = tjPackages;
        }

        public ProjectPackage getTjPackage() {
            return this.tjPackage;
        }

        public void setTjPackage(ProjectPackage tjPackage) {
            this.tjPackage = tjPackage;
        }

        public List<String> getPackageIds() {
            return this.packageIds;
        }

        public void setPackageIds(List<String> packageIds) {
            this.packageIds = packageIds;
        }

    }

    public static class ProjectPackage {

        private String ptype;// 直播包类型
        private List<ProjectInfo> dataList = new ArrayList<ProjectInfo>();// 专辑列表

        public String getPtype() {
            return this.ptype;
        }

        public void setPtype(String ptype) {
            this.ptype = ptype;
        }

        public List<ProjectInfo> getDataList() {
            return this.dataList;
        }

        public void setDataList(List<ProjectInfo> dataList) {
            this.dataList = dataList;
        }

    }

    public static class ProjectInfo {
        private String id;// 直播id
        private String rname;// 专辑名称
        private String subTitle;// 副标题
        private String rid;// 场次id
        private String pic43;// 原图地址

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRname() {
            return this.rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public String getSubTitle() {
            return this.subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getRid() {
            return this.rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getPic43() {
            return this.pic43;
        }

        public void setPic43(String pic43) {
            this.pic43 = pic43;
        }

    }
}
