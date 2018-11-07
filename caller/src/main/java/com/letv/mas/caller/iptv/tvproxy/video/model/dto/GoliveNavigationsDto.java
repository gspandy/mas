package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;

import java.util.List;

public class GoliveNavigationsDto {

    private String name;// 导航名称
    private List<BaseData> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BaseData> getList() {
        return list;
    }

    public void setList(List<BaseData> list) {
        this.list = list;
    }

    public static class GoliveResultDto extends BaseData {
        private static final long serialVersionUID = 3902696351109830314L;
        private String id;// 内容ID
        private int src;// 1内网 2外网
        private String subSrc;// src=2时，具体代表哪些外网数据
        private Integer subjectType;// 专题类型
        private String name;// 内容标题
        private String actor;// 演员列表
        private String score;// 评分
        private String introduction;// 简介
        private String jumpImg;// 主推模版跳转背景图
        private String ipc;// interest people count 感兴趣人数
        private String tagImg;// 海报角标
        private String url;// h5 链接
        private Integer openType;// h5 交互模式
        private String price;// 价格
        private String poster;// 海报
        private String subName;// 副标题
        private Long startTime;// 电影上映时间或直播，话剧演出时间
        private Long endTime;// 电影下映时间或直播，话剧演出时间
        private Integer saleStatus;// 售票状态
        private String message;// 主推模板上移后的预留信息例 “本周最火爆片子”

        public GoliveResultDto(String id, int src, String subSrc, int dataType, Integer subjectType, String name,
                String actor, String score, String introduction, String jumpImg, String ipc, String tagImg, String url,
                Integer openType, String price, String poster, String subName, Long startTime, Long endTime,
                Integer saleStatus, String message, CommonParam commonParam) {
            this.id = id;
            this.src = src;
            this.subSrc = subSrc;
            this.setDataType(dataType);
            this.subjectType = subjectType;
            this.name = name;
            this.actor = actor;
            this.score = score;
            this.introduction = introduction;
            this.jumpImg = jumpImg;
            this.ipc = ipc;
            this.tagImg = tagImg;
            this.url = url;
            this.openType = openType;
            this.price = price;
            this.poster = poster;
            this.subName = subName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.saleStatus = saleStatus;
            this.message = message;
            BurrowUtil.buildBurrow(this, commonParam);
        }

        public Integer getSubjectType() {
            return subjectType;
        }

        public void setSubjectType(Integer subjectType) {
            this.subjectType = subjectType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getSaleStatus() {
            return saleStatus;
        }

        public void setSaleStatus(Integer saleStatus) {
            this.saleStatus = saleStatus;
        }

        public Long getStartTime() {
            return startTime;
        }

        public void setStartTime(Long startTime) {
            this.startTime = startTime;
        }

        public int getSrc() {
            return src;
        }

        public void setSrc(int src) {
            this.src = src;
        }

        public String getSubSrc() {
            return subSrc;
        }

        public void setSubSrc(String subSrc) {
            this.subSrc = subSrc;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public String getSubName() {
            return subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getOpenType() {
            return openType;
        }

        public void setOpenType(Integer openType) {
            this.openType = openType;
        }

        public String getTagImg() {
            return tagImg;
        }

        public void setTagImg(String tagImg) {
            this.tagImg = tagImg;
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

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getJumpImg() {
            return jumpImg;
        }

        public void setJumpImg(String jumpImg) {
            this.jumpImg = jumpImg;
        }

        public String getIpc() {
            return ipc;
        }

        public void setIpc(String ipc) {
            this.ipc = ipc;
        }

    }

}
