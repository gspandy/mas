package com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 所有需要缓存的方法，需要加上此标记
 * 例如：
 * @LocalCacheAnnotation(name="doSomething", 
 *                                           expireInMillis=LocalCacheInterface.ONE_MINUTE
 *                                           )
 *                                           String doSomething() {
 *                                           Object object =
 *                                           someService.getResult();
 *                                           return object;
 *                                           }
 *                                           标记有两个可选入参：
 *                                           - name是缓存名称，默认是被标记的方法名；
 *                                           expireInMillis是缓存过期时间
 *                                           - 默认是LocalCacheInterface.
 *                                           DEFAULT_EXPIRE_TIME
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface LocalCacheAnnotation {

    /**
     * LocalCache名称
     * @return
     */
    String name() default "";

    /**
     * 过期时间，单位是毫秒，入参参考LocalCacheInterface中的时间常量
     * @return
     */
    long expireInMillis() default 0L;
}
