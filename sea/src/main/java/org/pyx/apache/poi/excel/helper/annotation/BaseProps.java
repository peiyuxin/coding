package org.pyx.apache.poi.excel.helper.annotation;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * @author pyx
 * @date 2018/12/3
 */
public class BaseProps {

    /**
     * The enumeration value indicating horizontal alignment of a cell
     */
    private HorizontalAlignment horizontal;

    /**
     * This enumeration value indicates the type of vertical alignment for a cell
     */
    private VerticalAlignment vertical;

    public HorizontalAlignment getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(HorizontalAlignment horizontal) {
        this.horizontal = horizontal;
    }

    public VerticalAlignment getVertical() {
        return vertical;
    }

    public void setVertical(VerticalAlignment vertical) {
        this.vertical = vertical;
    }
}
