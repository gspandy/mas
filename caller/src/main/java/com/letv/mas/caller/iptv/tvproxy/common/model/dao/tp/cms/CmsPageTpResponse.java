package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms;

import java.io.Serializable;
import java.util.List;

/**
 * CMS页面
 */
public class CmsPageTpResponse implements Serializable {

    private static final long serialVersionUID = 3632036652007125705L;
    private CmsPageTpResponseData data;

    public CmsPageTpResponseData getData() {
        return this.data;
    }

    public void setData(CmsPageTpResponseData data) {
        this.data = data;
    }

    public static class CmsPageTpResponseData {

        private List<CmsPageTpResponseFrag> frags;

        public List<CmsPageTpResponseFrag> getFrags() {
            return this.frags;
        }

        public void setFrags(List<CmsPageTpResponseFrag> frags) {
            this.frags = frags;
        }

        public static class CmsPageTpResponseFrag {

            private List<CmsBlockContentTpResponse> blockContents;

            private String contentId;

            private String contentName;

            private String contentStyle;

            private String contentRid;

            private Integer contentType;

            private Integer contentManulNum;

            private String redirectPageId;

            private String redirectSubUrl;

            private String redirectSubPageId;
            /**
             * 主标题跳转地址－tv版用作配置色块推荐dataurl
             */
            private String redirectUrl;

            public List<CmsBlockContentTpResponse> getBlockContents() {
                return this.blockContents;
            }

            public void setBlockContents(List<CmsBlockContentTpResponse> blockContents) {
                this.blockContents = blockContents;
            }

            public String getContentName() {
                return this.contentName;
            }

            public void setContentName(String contentName) {
                this.contentName = contentName;
            }

            public String getContentStyle() {
                return contentStyle;
            }

            public void setContentStyle(String contentStyle) {
                this.contentStyle = contentStyle;
            }

            public String getContentRid() {
                return contentRid;
            }

            public void setContentRid(String contentRid) {
                this.contentRid = contentRid;
            }

            public Integer getContentType() {
                return contentType;
            }

            public void setContentType(Integer contentType) {
                this.contentType = contentType;
            }

            public Integer getContentManulNum() {
                return contentManulNum;
            }

            public void setContentManulNum(Integer contentManulNum) {
                this.contentManulNum = contentManulNum;
            }

            public String getRedirectUrl() {
                return redirectUrl;
            }

            public void setRedirectUrl(String redirectUrl) {
                this.redirectUrl = redirectUrl;
            }

            public String getRedirectPageId() {
                return redirectPageId;
            }

            public void setRedirectPageId(String redirectPageId) {
                this.redirectPageId = redirectPageId;
            }

            public String getRedirectSubUrl() {
                return redirectSubUrl;
            }

            public void setRedirectSubUrl(String redirectSubUrl) {
                this.redirectSubUrl = redirectSubUrl;
            }

            public String getRedirectSubPageId() {
                return redirectSubPageId;
            }

            public void setRedirectSubPageId(String redirectSubPageId) {
                this.redirectSubPageId = redirectSubPageId;
            }

            public String getContentId() {
                return contentId;
            }

            public void setContentId(String contentId) {
                this.contentId = contentId;
            }
        }
    }
}
