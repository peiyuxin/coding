package org.pyx.apache.poi.excel.helper.util;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.CellType;

/**
 * @author pyx
 * @date 2018/12/3
 */
public class HeaderInfo {

    private CellType cellType;
    private Field field;

    public HeaderInfo(CellType cellType, Field field) {
        this.cellType = cellType;
        this.field = field;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
