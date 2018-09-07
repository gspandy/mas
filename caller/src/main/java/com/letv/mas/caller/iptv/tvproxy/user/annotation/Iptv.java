package com.letv.mas.caller.iptv.tvproxy.user.annotation;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnProperty(value = "spring.cloud.iptv.enabled", havingValue= "true", matchIfMissing = false)
@Inherited
public @interface Iptv {
}
