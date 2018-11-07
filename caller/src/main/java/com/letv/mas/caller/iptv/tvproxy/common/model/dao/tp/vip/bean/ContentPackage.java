package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import java.util.List;

/**
 * 内容包信息
 */
public class ContentPackage {
    private Integer total;// 总数
    private List<ContentInfo> cInfo; // 内容包具体信息

    public static class ContentInfo {
        private Long cid; // 内容ID（ctype=1是传专辑ID，ctype=2时传轮播台ID，ctype=3是视频id,ctype=4是直播场次）
        private Integer ctype; // 内容类型

        public Long getCid() {
            return cid;
        }

        public void setCid(Long cid) {
            this.cid = cid;
        }

        public Integer getCtype() {
            return ctype;
        }

        public void setCtype(Integer ctype) {
            this.ctype = ctype;
        }
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ContentInfo> getcInfo() {
        return cInfo;
    }

    public void setcInfo(List<ContentInfo> cInfo) {
        this.cInfo = cInfo;
    }
}
