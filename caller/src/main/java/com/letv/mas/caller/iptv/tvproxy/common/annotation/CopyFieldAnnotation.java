package com.letv.mas.caller.iptv.tvproxy.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Inherited
public @interface CopyFieldAnnotation {
    /**
     * 对应bean的属性名称
     * @return
     */
    public String value() default "";

    /**
     * 是否拷贝
     * @return
     */
    public boolean isCopy() default true;

    /**
     * 是否完整拷到到目标对象，不做拆分
     * @return
     */
    public boolean isSplit() default false;

    /**
     * 待拷贝对象map中key对应对象的属性名称
     * @return
     */
    public String mapKey() default "";

    /**
     * 待拷贝对象map中value对应对象的属性名称
     * @return
     */
    public String mapValue() default "";

    /**
     * 待拷贝对象中是否将数组或Map或集合的值合并为String
     * @return
     */
    public boolean valueToString() default true;
}
