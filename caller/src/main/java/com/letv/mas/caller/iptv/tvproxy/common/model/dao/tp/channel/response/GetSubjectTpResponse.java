package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 调用第三方接口获取特辑包信息封装类
 * @author liyunlong
 */
public class GetSubjectTpResponse {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 特辑包数据主体
     */
    private SubjectDataTpResponse data;

    /**
     * 返回的信息
     */
    private String msg;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SubjectDataTpResponse getData() {
        return this.data;
    }

    public void setData(SubjectDataTpResponse data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 特辑包数据主体
     * @author liyunlong
     */
    public static class SubjectDataTpResponse implements Serializable {
        private static final long serialVersionUID = 3228743896199803097L;

        /**
         *
         */
        private String cid;
        /*
         * 专题频道分类
         */
        private String category;
        /*
         * 二级频道分类
         */
        private String subcategory;

        /**
        *
        */
        private Long ctime;

        /**
         * 特辑简介
         */
        private String description;

        /**
         * 移动/M站焦点图：640*178 （使用PC焦点图自动压图）
         */
        private String focusMPic;

        /**
         * PC/Pad焦点图：1440*400
         */
        private String focusPic;

        /**
         * 特辑ID
         */
        private String id;

        /**
         * 排序
         */
        private Integer listOrder;

        /**
         * 名称
         */
        private String name;

        /**
         * 16:9比例的图片
         */
        private String pic169;

        /**
         * 3:4比例的图片
         */
        private String pic34;

        /**
         * 4:3比例的图片
         */
        private String pic43;

        /**
         * 推送平台
         */
        private String playPlatform;

        /**
         * 发布名称
         */
        private String pubName;

        /**
         * 分享描述
         */
        private String shareDesc;

        /**
         * 分享图片
         */
        private String shareImg;

        /**
         * 分享的播放平台
         */
        private Map<String, String> sharePlayPlatformMap;

        /**
         * 分享标题
         */
        private String shareTitle;

        /**
         * 可分享的
         */
        private Integer shareable;

        /**
        *
        */
        private Integer site;

        /**
        *
        */
        private String tag;

        /**
         * 模板
         */
        private TemplateJson templateJson;

        /**
         * 特辑包里的专题包数据列表
         */
        private List<SubjectTjPackageTpResponse> tjPackages;
        /*
         * 专题里的包id列表
         */
        private List<String> packageIds;
        /**
         * TV版图片
         */
        private String tvPic;

        public List<String> getPackageIds() {
            return this.packageIds;
        }

        public void setPackageIds(List<String> packageIds) {
            this.packageIds = packageIds;
        }

        public String getCid() {
            return this.cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public Long getCtime() {
            return this.ctime;
        }

        public void setCtime(Long ctime) {
            this.ctime = ctime;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFocusMPic() {
            return this.focusMPic;
        }

        public void setFocusMPic(String focusMPic) {
            this.focusMPic = focusMPic;
        }

        public String getFocusPic() {
            return this.focusPic;
        }

        public void setFocusPic(String focusPic) {
            this.focusPic = focusPic;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getListOrder() {
            return this.listOrder;
        }

        public void setListOrder(Integer listOrder) {
            this.listOrder = listOrder;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic169() {
            return this.pic169;
        }

        public void setPic169(String pic169) {
            this.pic169 = pic169;
        }

        public String getPic34() {
            return this.pic34;
        }

        public void setPic34(String pic34) {
            this.pic34 = pic34;
        }

        public String getPic43() {
            return this.pic43;
        }

        public void setPic43(String pic43) {
            this.pic43 = pic43;
        }

        public String getPlayPlatform() {
            return this.playPlatform;
        }

        public void setPlayPlatform(String playPlatform) {
            this.playPlatform = playPlatform;
        }

        public String getPubName() {
            return this.pubName;
        }

        public void setPubName(String pubName) {
            this.pubName = pubName;
        }

        public String getShareDesc() {
            return this.shareDesc;
        }

        public void setShareDesc(String shareDesc) {
            this.shareDesc = shareDesc;
        }

        public String getShareImg() {
            return this.shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public Map<String, String> getSharePlayPlatformMap() {
            return this.sharePlayPlatformMap;
        }

        public void setSharePlayPlatformMap(Map<String, String> sharePlayPlatformMap) {
            this.sharePlayPlatformMap = sharePlayPlatformMap;
        }

        public String getShareTitle() {
            return this.shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public Integer getShareable() {
            return this.shareable;
        }

        public void setShareable(Integer shareable) {
            this.shareable = shareable;
        }

        public Integer getSite() {
            return this.site;
        }

        public void setSite(Integer site) {
            this.site = site;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public TemplateJson getTemplateJson() {
            return this.templateJson;
        }

        public void setTemplateJson(TemplateJson templateJson) {
            this.templateJson = templateJson;
        }

        public List<SubjectTjPackageTpResponse> getTjPackages() {
            return this.tjPackages;
        }

        public void setTjPackages(List<SubjectTjPackageTpResponse> tjPackages) {
            this.tjPackages = tjPackages;
        }

        public String getTvPic() {
            return this.tvPic;
        }

        public void setTvPic(String tvPic) {
            this.tvPic = tvPic;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSubcategory() {
            return this.subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public static class TemplateJson implements Serializable {
            private static final long serialVersionUID = -8728050189524828119L;

            /**
             * TV模板，值如“tv_1”
             */
            private String tvTemplate;

            /**
             * pc模板，值如“pc_1”
             */
            private String pcTemplate;

            /**
             * 移动模板，值如“mobile_1”
             */
            private String phoneTemplate;

            public String getTvTemplate() {
                return this.tvTemplate;
            }

            public void setTvTemplate(String tvTemplate) {
                this.tvTemplate = tvTemplate;
            }

            public String getPcTemplate() {
                return this.pcTemplate;
            }

            public void setPcTemplate(String pcTemplate) {
                this.pcTemplate = pcTemplate;
            }

            public String getPhoneTemplate() {
                return this.phoneTemplate;
            }

            public void setPhoneTemplate(String phoneTemplate) {
                this.phoneTemplate = phoneTemplate;
            }

        }

    }
}
