package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 近七天播放数，统计记录
 */
public class StatRankWeekData {

    private Long id;// 主键
    private String pid;// 专辑id
    private String cid;// 分类id ,0：总排行、1电影、2电视剧、5动漫、11综艺
    private Long vv;// 播放数
    private Long uv;// 播放用户数
    private String stat_date;// 统计日期
                             // ，即：20150616表示[20150610-20150616]日期内播放数、播放用户数
    private Integer rank;// vv排行数

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Long getVv() {
        return vv;
    }

    public void setVv(Long vv) {
        this.vv = vv;
    }

    public Long getUv() {
        return uv;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public String getStat_date() {
        return stat_date;
    }

    public void setStat_date(String stat_date) {
        this.stat_date = stat_date;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
