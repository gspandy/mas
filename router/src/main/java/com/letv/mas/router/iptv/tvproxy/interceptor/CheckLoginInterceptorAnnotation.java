package com.letv.mas.router.iptv.tvproxy.interceptor;

import java.lang.annotation.*;

/**
 * Created by leeco on 18/11/6.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CheckLoginInterceptorAnnotation {
}
