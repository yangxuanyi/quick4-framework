package org.quick4.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     */
    Class<? extends Annotation> value();

}
