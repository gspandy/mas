package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.GameCenterGcResource;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;

public class GameSdkDto {

    public static class GameCenterBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 3999239095976377482L;
        private long id;// 要跳转的游戏appid
        private Integer gc_resource;// 跳转游戏中心来源app @BurrowGcResource 新
        private Integer resource;// 跳转游戏中心来源app @BurrowGcResource 旧
        private String appPackageName;// 应用包名

        public Integer getGc_resource() {
            return gc_resource;
        }

        public void setGc_resource(GameCenterGcResource gcResource) {
            this.gc_resource = gcResource.getGcResource();
        }

        public Integer getResource() {
            return resource;
        }

        public void setResource(GameCenterGcResource gcResource) {
            this.resource = gcResource.getGcResource();
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getAppPackageName() {
            return appPackageName;
        }

        public void setAppPackageName(String appPackageName) {
            this.appPackageName = appPackageName;
        }

    }

}
