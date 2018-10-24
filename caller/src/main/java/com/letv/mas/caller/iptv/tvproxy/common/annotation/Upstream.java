package com.letv.mas.caller.iptv.tvproxy.common.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface Upstream {
    public enum Type {
        NET,
        SQL,
        CACHE
    }
    String[] resource() default {};
    Type[] resourceTy() default {};
}
