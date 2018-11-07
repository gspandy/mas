package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse.SubjectDataTpResponse;

/**
 * CMS版块数据第三方响应
 * @author dunhongqin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CMSBlockTpResponse implements Serializable {
    private static final long serialVersionUID = -8944821018723736017L;
    private Integer cid;// 版块所属频道
    private Integer id;// 版块ID
    private String name;//
    private List<CMSBlockData> blockContent;

    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CMSBlockData> getBlockContent() {
        return this.blockContent;
    }

    public void setBlockContent(List<CMSBlockData> blockContent) {
        this.blockContent = blockContent;
    }

    public static class CMSBlockData implements Serializable {
        private static final long serialVersionUID = -3488566714163586157L;
        private String content;// 版块内容对应的ID（专辑ID或视频ID）
        private Object album;
        private Video video;
        private String pushflag;// 推送平台
        private String pic1;
        private String pic2;
        private String androidUrl;
        private String iosUrl;
        private String shorDesc;
        private String subTitle;
        private String tagUrl;
        private String tag;
        private String tvUrl;
        private String url;
        private String title;
        private Map<String, String> playPlatform;
        private String type;
        private String tvPic;
        private String position;
        private String skipType;
        private String skipUrl;
        private String skipPage;
        private String zidType = "";

        /**
         * 专题包信息，用于解析专题包模板信息
         */
        private SubjectDataTpResponse kztInfo;

        public class Video implements Serializable {
            private static final long serialVersionUID = 1914195184638342512L;
            private String cid;
            private String chanName;

            public String getCid() {
                return this.cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getChanName() {
                return this.chanName;
            }

            public void setChanName(String chanName) {
                this.chanName = chanName;
            }
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getAlbum() {
            return this.album;
        }

        public void setAlbum(Object album) {
            this.album = album;
        }

        public Video getVideo() {
            return this.video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public String getPushflag() {
            return this.pushflag;
        }

        public void setPushflag(String pushflag) {
            this.pushflag = pushflag;
        }

        public String getPic1() {
            return this.pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return this.pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public Boolean is_Album() {
            if (this.album != null) {
                return true;
            }
            return false;
        }

        public Boolean is_Video() {
            if (this.video != null) {
                return true;
            }
            return false;
        }

        public String getAndroidUrl() {
            return this.androidUrl;
        }

        public void setAndroidUrl(String androidUrl) {
            this.androidUrl = androidUrl;
        }

        public String getIosUrl() {
            return this.iosUrl;
        }

        public void setIosUrl(String iosUrl) {
            this.iosUrl = iosUrl;
        }

        public String getShorDesc() {
            return this.shorDesc;
        }

        public void setShorDesc(String shorDesc) {
            this.shorDesc = shorDesc;
        }

        public String getSubTitle() {
            return this.subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getTagUrl() {
            return this.tagUrl;
        }

        public void setTagUrl(String tagUrl) {
            this.tagUrl = tagUrl;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTvUrl() {
            return this.tvUrl;
        }

        public void setTvUrl(String tvUrl) {
            this.tvUrl = tvUrl;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Map<String, String> getPlayPlatform() {
            return this.playPlatform;
        }

        public void setPlayPlatform(Map<String, String> playPlatform) {
            this.playPlatform = playPlatform;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTvPic() {
            return this.tvPic;
        }

        public void setTvPic(String tvPic) {
            this.tvPic = tvPic;
        }

        public String getPosition() {
            return this.position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getSkipType() {
            return this.skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getSkipUrl() {
            return this.skipUrl;
        }

        public void setSkipUrl(String skipUrl) {
            this.skipUrl = skipUrl;
        }

        public String getSkipPage() {
            return this.skipPage;
        }

        public void setSkipPage(String skipPage) {
            this.skipPage = skipPage;
        }

        public String getZidType() {
            return this.zidType;
        }

        public void setZidType(String zidType) {
            this.zidType = zidType;
        }

        public SubjectDataTpResponse getKztInfo() {
            return this.kztInfo;
        }

        public void setKztInfo(SubjectDataTpResponse kztInfo) {
            this.kztInfo = kztInfo;
        }

    }
}
