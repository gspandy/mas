package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import java.lang.annotation.*;

/**
 * API层HTTP参数拦截器注解，目前策略为，只有添加注解的方法才会启用拦截器
 * @author KevinYi
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HttpParameterInterceptorAnnotation {

}
