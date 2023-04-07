package org.pyx.apache.poi.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * sheet header's properties
 * @author pyx
 * @date 2018/12/3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {

    /**
     * Horizontal Aign
     *
     * @return return horizontal align
     */
    HorizontalAlignment horizontal() default HorizontalAlignment.CENTER;

    /**
     * Vertical Align
     *
     * @return return vertical align
     */
    VerticalAlignment vertical() default VerticalAlignment.CENTER;
}
