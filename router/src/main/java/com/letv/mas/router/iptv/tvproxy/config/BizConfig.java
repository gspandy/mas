package com.letv.mas.router.iptv.tvproxy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leeco on 18/10/9.
 * refer: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
 */
@RefreshScope
@Component
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "biz")
@Data
public class BizConfig {

    /**
     * 第三方接口地址集合
     */
    private Map<String, String> exUrls = new HashMap<>();

    /**
     * 需授权认证的开发接口集合
     */
    private Map<String, String> authUrls = new HashMap<>();
}
