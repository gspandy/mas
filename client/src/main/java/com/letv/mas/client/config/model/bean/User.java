package com.letv.mas.client.config.model.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 动态属性配置Bean获取配置
 *
 * Created by David.Liu on 18/5/23.
 */

@Component
@ConfigurationProperties(prefix = "sc.user")
public class User {
    private Long id;

    private String name;

    private String desc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
