package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MACBlacklistInterceptorAnnotation {
}
