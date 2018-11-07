package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms;

import java.io.Serializable;

/**
 * CMS板块数据
 */
public class CmsStyleTpResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6776448800602559274L;
    private Long id;
    private String description;
    private String name;
    private String pic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public static class CmsStyleOpsSettingTpResponse {
        private String contentRows;
        private String contentSetting;
        private String lineHeightCeiling;
        private String nameHeight;
        private String nameType;
        private String sortStyle;
        private String spacing;

        public String getContentRows() {
            return contentRows;
        }

        public void setContentRows(String contentRows) {
            this.contentRows = contentRows;
        }

        public String getContentSetting() {
            return contentSetting;
        }

        public void setContentSetting(String contentSetting) {
            this.contentSetting = contentSetting;
        }

        public String getLineHeightCeiling() {
            return lineHeightCeiling;
        }

        public void setLineHeightCeiling(String lineHeightCeiling) {
            this.lineHeightCeiling = lineHeightCeiling;
        }

        public String getNameHeight() {
            return nameHeight;
        }

        public void setNameHeight(String nameHeight) {
            this.nameHeight = nameHeight;
        }

        public String getNameType() {
            return nameType;
        }

        public void setNameType(String nameType) {
            this.nameType = nameType;
        }

        public String getSortStyle() {
            return sortStyle;
        }

        public void setSortStyle(String sortStyle) {
            this.sortStyle = sortStyle;
        }

        public String getSpacing() {
            return spacing;
        }

        public void setSpacing(String spacing) {
            this.spacing = spacing;
        }

    }
}
