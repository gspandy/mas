package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.util.List;
import java.util.Map;

/**
 * 角色播单
 * @author jijianlong
 */
public class RolePlayListTp {

    private String code;

    private RolePlayListData data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RolePlayListData getData() {
        return this.data;
    }

    public void setData(RolePlayListData data) {
        this.data = data;
    }

    public static class RolePlayListData {

        private String count;
        private Integer page;
        private List<RolePlayList> list;

        public String getCount() {
            return this.count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public Integer getPage() {
            return this.page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public List<RolePlayList> getList() {
            return this.list;
        }

        public void setList(List<RolePlayList> list) {
            this.list = list;
        }

    }

    public static class RolePlayList {

        private Long cid;
        private Long pid;
        private Long vid;
        private String title;
        private String subTitle;
        private Map<Integer, String> categroy;
        private String pic;
        private String videoStatus;
        private Map<Integer, String> downloadPlatform;
        private Map<Integer, String> albumType;
        private Integer episode;
        private Map<Integer, String> subCategory;

        public Long getCid() {
            return this.cid;
        }

        public void setCid(Long cid) {
            this.cid = cid;
        }

        public Long getPid() {
            return this.pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public Long getVid() {
            return this.vid;
        }

        public void setVid(Long vid) {
            this.vid = vid;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return this.subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public Map<Integer, String> getCategroy() {
            return this.categroy;
        }

        public void setCategroy(Map<Integer, String> categroy) {
            this.categroy = categroy;
        }

        public String getPic() {
            return this.pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getVideoStatus() {
            return this.videoStatus;
        }

        public void setVideoStatus(String videoStatus) {
            this.videoStatus = videoStatus;
        }

        public Map<Integer, String> getDownloadPlatform() {
            return this.downloadPlatform;
        }

        public void setDownloadPlatform(Map<Integer, String> downloadPlatform) {
            this.downloadPlatform = downloadPlatform;
        }

        public Map<Integer, String> getAlbumType() {
            return this.albumType;
        }

        public void setAlbumType(Map<Integer, String> albumType) {
            this.albumType = albumType;
        }

        public Integer getEpisode() {
            return this.episode;
        }

        public void setEpisode(Integer episode) {
            this.episode = episode;
        }

        public Map<Integer, String> getSubCategory() {
            return this.subCategory;
        }

        public void setSubCategory(Map<Integer, String> subCategory) {
            this.subCategory = subCategory;
        }

    }

}
