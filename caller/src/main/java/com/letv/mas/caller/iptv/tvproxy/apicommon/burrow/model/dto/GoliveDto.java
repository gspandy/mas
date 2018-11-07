package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;

public class GoliveDto {

    public static class GoliveAlbumBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 1L;
        private String id;// 专辑ID

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

}
