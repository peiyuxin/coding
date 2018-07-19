package org.pyx.annotation.definition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pyx
 * @date 2018/7/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Delegate {
    Class<?> source() default DefaultType.class;

    String name();

    Class<?>[] parameterTypes();

    BindingMode mode() default BindingMode.OVERRIDE;

    short priority() default Priorities.PRIORITY_DEFAULT;

    Class<?>[] exclusions() default {};

    enum BindingMode {
        OVERRIDE,
        APPEND
    }

    class DefaultType {
    }
}
