package org.pyx.apache.poi.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author pyx
 * @date 2018/12/3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Sheet {

    /**
     * sheet's name, must not be empty
     *
     * @return return sheet's name
     */
    String name();
}
