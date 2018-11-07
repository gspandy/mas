package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.util.List;

/**
 * 收藏单条数据
 * Created by liudaojie on 16/9/18.
 */
public class CollectionListInfo {
    private Integer total; // 总数
    private List<CollectionInfo> rows; // 数据

    public static class CollectionInfo {
        private String lid; // globalId
        private Integer lidtype; // globalId前缀
        private Integer cid; // 分类id
        private String record; // 数据上报内容
        private Long ctime; // 收藏时间?
        private Integer platid;
        private Integer appid;
        private Integer sappid; // 子应用id
        private Long ip;
        private String region;
        private Object detail;

        public String getLid() {
            return lid;
        }

        public void setLid(String lid) {
            this.lid = lid;
        }

        public Integer getLidtype() {
            return lidtype;
        }

        public void setLidtype(Integer lidtype) {
            this.lidtype = lidtype;
        }

        public Integer getCid() {
            return cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public String getRecord() {
            return record;
        }

        public void setRecord(String record) {
            this.record = record;
        }

        public Long getCtime() {
            return ctime;
        }

        public void setCtime(Long ctime) {
            this.ctime = ctime;
        }

        public Integer getPlatid() {
            return platid;
        }

        public void setPlatid(Integer platid) {
            this.platid = platid;
        }

        public Integer getAppid() {
            return appid;
        }

        public void setAppid(Integer appid) {
            this.appid = appid;
        }

        public Integer getSappid() {
            return sappid;
        }

        public void setSappid(Integer sappid) {
            this.sappid = sappid;
        }

        public Long getIp() {
            return ip;
        }

        public void setIp(Long ip) {
            this.ip = ip;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public Object getDetail() {
            return detail;
        }

        public void setDetail(Object detail) {
            this.detail = detail;
        }
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<CollectionInfo> getRows() {
        return rows;
    }

    public void setRows(List<CollectionInfo> rows) {
        this.rows = rows;
    }
}
