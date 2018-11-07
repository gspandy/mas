package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.BurrowPanoResource;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;

public class PanoDto {

    public static class PanoAlbumBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 8426907142213815403L;
        private Integer resource;// @BurrowPanoResource
        private String aid;// 专辑ID
        private String src;
        private String pushflag;
        private String categoryid;

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getPushflag() {
            return pushflag;
        }

        public void setPushflag(String pushflag) {
            this.pushflag = pushflag;
        }

        public Integer getResource() {
            return resource;
        }

        public void setResource(BurrowPanoResource burrowPanoResource) {
            this.resource = burrowPanoResource.getResource();
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

    }

    public static class PanoStarBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 7758581758771200895L;
        private int resource;// @BurrowPanoResource
        private String starId;// 明星ID

        public int getResource() {
            return resource;
        }

        public void setResource(BurrowPanoResource burrowPanoResource) {
            this.resource = burrowPanoResource.getResource();
        }

        public String getStarId() {
            return starId;
        }

        public void setStarId(String starId) {
            this.starId = starId;
        }

    }

}
