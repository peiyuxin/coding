package org.pyx.apache.poi.excel.helper.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.pyx.apache.commons.constant.Constant;
import org.pyx.apache.poi.excel.helper.annotation.Column;
import org.pyx.apache.poi.excel.helper.cache.CacheFactory;
import org.pyx.apache.poi.excel.helper.exception.ExcelHelperException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pyx
 * @date 2018/12/3
 */
public class DataAssistant {
    protected static final Logger LOGGER = LoggerFactory.getLogger(DataAssistant.class);

    private DataAssistant() {}

    public static List<List<Object>> createData(Collection<?> sheetData) {

        List<List<Object>> result = new ArrayList<>();

        Class<?> dataCls = sheetData.iterator().next().getClass();
        List<Field> fields = CacheFactory.findFields(dataCls);
        Iterator<?> dataIterator = sheetData.iterator();
        while (dataIterator.hasNext()) {
            List<Object> rowData = new ArrayList<>(fields.size());
            Object data = dataIterator.next();
            for (Field field : fields) {
                try {
                    Object value = field.get(data);
                    // when cell is null, pre process it, avoid NPE.
                    if (Objects.isNull(value)) {
                        CellType ct = field.getAnnotation(Column.class).cellType();
                        if (ct == CellType.STRING) {
                            value = "";
                        } else if (ct == CellType.NUMERIC) {
                            value = 0;
                        } else if (ct == CellType.BOOLEAN) {
                            value = false;
                        }
                    }
                    rowData.add(value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new ExcelHelperException(e);
                }
            }
            result.add(rowData);
        }
        return result;
    }

    public static <T> List<T> createDataFromSheet(Sheet sheet, Class<T> cls,boolean showHead) {
        Iterator<Row> rows = sheet.rowIterator();
        return createDateFromSheet(rows, cls, showHead);
    }

    private static <T> List<T> createDateFromSheet(Iterator<Row> rows, Class<T> cls,boolean showHead) {
        Map<Integer, HeaderInfo> headerInfoMap = null;
        if(showHead) {
            headerInfoMap = proceeHeaderInfo(rows, cls);
            LOGGER.info("headerInfoMap from proceeHeaderInfo from excel");
        }else{
            headerInfoMap = CacheFactory.findHeaderInfo(cls);
            LOGGER.info("headerInfoMap from CacheFactory(cls)");
        }


        List<T> data = new ArrayList<>();
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.cellIterator();
            Constructor<T> c = null;
            try {
                c = cls.getDeclaredConstructor();
            } catch (NoSuchMethodException | SecurityException e) {
                throw new ExcelHelperException("Domain: " + cls.getSimpleName() + " has no default constructor.");
            }
            T domain;
            try {
                domain = c.newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
                throw new ExcelHelperException(e1);
            }
            while (cells.hasNext()) {
                Cell cell = cells.next();
                HeaderInfo headerInfo = headerInfoMap.get(cell.getColumnIndex());

                if("".equalsIgnoreCase(cell.toString().trim())){
                    continue;
                }

                if(headerInfo == null){
                    int rowNo = row.getRowNum();
                    int cellNo = cell.getColumnIndex();
                    String errMsg ="第"+ (rowNo+1) +"行第"+ (cellNo+1) +"列无对应表头字段，请修改";
                    LOGGER.error(errMsg);
                    throw new ExcelHelperException("Excel表格有误！ "+errMsg);
                }

                Field field = headerInfo.getField();

                try {
                    Object cellValue = getCellValue(cell, headerInfo.getCellType(), field);
                    field.set(domain, cellValue);
                } catch (RuntimeException | IllegalAccessException e) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String cellStr = cell.getStringCellValue();
                    System.err.println("cell.getColumnIndex:"+cell.getColumnIndex());
                    LOGGER.error("cellStr:{} row.getRowNum:{}, cell.getColumnIndex:{},cell.getCellType:{},field.getType",
                        cellStr,row.getRowNum(),cell.getColumnIndex(),headerInfo.getCellType(),field.getType());
                    throw new ExcelHelperException(e);
                }
            }
            data.add(domain);
        }
        return data;
    }

    private static Object getCellValue(Cell cell, CellType headCellType, Field field) throws IllegalStateException{
        Object cellValue = null;
        CellType ct = cell.getCellTypeEnum();
        Class<?> type = field.getType();
        // 由于通过cell.getCellTypeEnum()获取的类型和实体定义的类型可能不一致，所以这里以实体类型为准而不能以cell.getCellTypeEnum()为准，进行强转，否则调用field.set()时候报类型错误
        if (headCellType == CellType.STRING) {
            //不是最佳实践，应该使用number类型读取
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String cellStr = cell.getStringCellValue();
            cellValue = getCellValue(cellStr, type);
        } else if (headCellType == CellType.NUMERIC) {
            if(ct == CellType.NUMERIC){
                cellValue = getCellValue(cell.getNumericCellValue(), type);
            }else {
                String cellStr = cell.getStringCellValue();
                cellValue = getCellValue(cellStr, type);
            }
        } else if (headCellType == CellType.BOOLEAN) {
            cellValue = getCellValue(cell.getBooleanCellValue(), type);
        }
        return cellValue;
    }

    private static Object getCellValue(Object cellValue, Class<?> type) {
        String cv = cellValue.toString();
        Object value = null;
        if (Objects.equals(type, Integer.class) || Objects.equals(type, int.class)) {
            value = Double.valueOf(new BigDecimal(cv).toPlainString()).intValue();
        } else if (Objects.equals(type, Float.class) || Objects.equals(type, float.class)) {
            value = Float.valueOf(new BigDecimal(cv).toPlainString());
        } else if (Objects.equals(type, Long.class) || Objects.equals(type, long.class)) {
            String numStr = new BigDecimal(cv).toPlainString();
            if (numStr.contains(Constant.DOT)){
                value = Double.valueOf(new BigDecimal(cv).toPlainString()).longValue();
            }else{
                value = Long.valueOf(new BigDecimal(cv).toPlainString());
            }
        } else if (Objects.equals(type, Short.class) || Objects.equals(type, short.class)) {
            value = Short.valueOf(new BigDecimal(cv).toPlainString());
        } else if (Objects.equals(type, Character.class) || Objects.equals(type, char.class)) {
            value = Character.valueOf(cv.toCharArray()[0]);
        } else if (Objects.equals(type, String.class)) {
            value = cv;
        }else if (Objects.equals(type, Double.class) || Objects.equals(type, double.class)) {
            value = Double.valueOf(new BigDecimal(cv).toPlainString());
        } else if (Objects.equals(type, Boolean.class) || Objects.equals(type, boolean.class)) {
            value = Boolean.valueOf(cv);
        } else if (Objects.equals(type, BigDecimal.class)) {
            value = new BigDecimal(cv);
        }
        return value;
    }

    public static Map<Integer, HeaderInfo> proceeHeaderInfo(Iterator<Row> rows, Class<?> cls) {
        if (rows.hasNext()) {
            Row header = rows.next();
            return CacheFactory.findHeaderInfo(cls, header);
        }
        return new HashMap<>(0);
    }
}
