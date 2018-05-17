package com.letv.mas.caller.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by leeco on 18/4/28.
 */
//@RefreshScope
@Component
public class RouterConfigDto implements Serializable {

    // 对应git配置文件里的keys

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String managerZone="";

    public String getManagerZone() {
        return managerZone;
    }

    public void setManagerZone(String managerZone) {
        this.managerZone = managerZone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("managerZone=").append(this.managerZone)
                .append("");
        return sb.toString();
    }
}
