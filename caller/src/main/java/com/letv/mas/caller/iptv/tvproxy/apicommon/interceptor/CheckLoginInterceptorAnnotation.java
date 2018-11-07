package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import java.lang.annotation.*;

/**
 * 响应头拦截器注解
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CheckLoginInterceptorAnnotation {
}
