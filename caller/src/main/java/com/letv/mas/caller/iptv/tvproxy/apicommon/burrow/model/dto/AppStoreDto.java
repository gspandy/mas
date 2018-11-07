package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.BurrowPackageName;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;

public class AppStoreDto {

    public static class AppStoreBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 4706482579029764353L;
        private String pkg;// 要跳转的应用包名
        private String from;// 跳转来源应用包名 @BurrowPackageName

        public String getPkg() {
            return pkg;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(BurrowPackageName burrowPackageName) {
            this.from = burrowPackageName.getFrom();
        }

    }

}
