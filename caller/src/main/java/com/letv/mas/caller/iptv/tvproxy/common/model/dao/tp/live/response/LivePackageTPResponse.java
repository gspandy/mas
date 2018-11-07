package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.util.ArrayList;
import java.util.List;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response.LiveProjectResponseTp.ProjectInfo;

public class LivePackageTPResponse {
    private Integer code;
    private ProjectPackage data;
    private String msg;

    public LivePackageTPResponse() {

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ProjectPackage getData() {
        return data;
    }

    public void setData(ProjectPackage data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ProjectPackage {

        private List<ProjectInfo> dataList = new ArrayList<ProjectInfo>();// 专辑列表
        private String id;
        private String ptype;// 直播包类型
        private String name;

        public String getPtype() {
            return ptype;
        }

        public void setPtype(String ptype) {
            this.ptype = ptype;
        }

        public List<ProjectInfo> getDataList() {
            return dataList;
        }

        public void setDataList(List<ProjectInfo> dataList) {
            this.dataList = dataList;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
