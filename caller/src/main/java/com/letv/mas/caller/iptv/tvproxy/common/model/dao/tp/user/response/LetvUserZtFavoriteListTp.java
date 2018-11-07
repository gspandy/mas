package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.util.List;

/**
 * 专题收藏
 * @author yuehongmin
 */
public class LetvUserZtFavoriteListTp {

    private String code;
    private LetvUserPlayListData data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LetvUserPlayListData getData() {
        return this.data;
    }

    public void setData(LetvUserPlayListData data) {
        this.data = data;
    }

    public static class LetvUserPlayListData {

        private Integer total;
        private List<LetvUserPlayList> list;

        public List<LetvUserPlayList> getList() {
            return this.list;
        }

        public void setList(List<LetvUserPlayList> list) {
            this.list = list;
        }

        public Integer getTotal() {
            return this.total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

    }

    public static class LetvUserPlayList {

        private Integer id;
        private String cid;
        private Long vid;
        private Integer fromtype;
        private String title;
        private String pic;
        private String subname;
        private String url;
        private String videoPic;
        private String categroy;
        private Long pid;
        private String videoStatus;
        private String category_url;
        private String subTitle;
        private Integer follownum;// 跟播到第几集
        private Integer episodes;// 总集数

        public Integer getFollownum() {
            return follownum;
        }

        public void setFollownum(Integer follownum) {
            this.follownum = follownum;
        }

        public Integer getEpisodes() {
            return episodes;
        }

        public void setEpisodes(Integer episodes) {
            this.episodes = episodes;
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCid() {
            return this.cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public Long getVid() {
            return this.vid;
        }

        public void setVid(Long vid) {
            this.vid = vid;
        }

        public Integer getFromtype() {
            return this.fromtype;
        }

        public void setFromtype(Integer fromtype) {
            this.fromtype = fromtype;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return this.pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getSubname() {
            return this.subname;
        }

        public void setSubname(String subname) {
            this.subname = subname;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCategory_url() {
            return this.category_url;
        }

        public void setCategory_url(String category_url) {
            this.category_url = category_url;
        }

        public String getVideoPic() {
            return this.videoPic;
        }

        public void setVideoPic(String videoPic) {
            this.videoPic = videoPic;
        }

        public String getCategroy() {
            return this.categroy;
        }

        public void setCategroy(String categroy) {
            this.categroy = categroy;
        }

        public Long getPid() {
            return this.pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public String getVideoStatus() {
            return this.videoStatus;
        }

        public void setVideoStatus(String videoStatus) {
            this.videoStatus = videoStatus;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

    }

}
