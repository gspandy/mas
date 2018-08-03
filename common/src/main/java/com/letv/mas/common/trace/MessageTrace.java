package com.letv.mas.common.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dalvikchen on 18/8/1.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageTrace {
    String spanName() default "MessageTraceSpan";

    String tagName() default "MessageTraceTag";

    String eventName() default "MessageTrace";

    String msgId() default "";

    String msg() default "";
}
