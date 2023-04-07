package org.pyx.apache.poi.excel.helper.util;

import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.pyx.apache.poi.excel.helper.annotation.Cell;
import org.pyx.apache.poi.excel.helper.annotation.CellProps;
import org.pyx.apache.poi.excel.helper.annotation.Column;
import org.pyx.apache.poi.excel.helper.annotation.Header;
import org.pyx.apache.poi.excel.helper.annotation.HeaderProps;
import org.pyx.apache.poi.excel.helper.annotation.PropsAnno;
import org.pyx.apache.poi.excel.helper.annotation.Sheet;
import org.pyx.apache.poi.excel.helper.cache.CacheFactory;
import org.pyx.apache.poi.excel.helper.exception.ExcelHelperException;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * 根据对象注解获取excel信息
 *
 * @author pyx
 * @date 2018/12/3
 */
public final class ClassAssistant {
    private ClassAssistant(){}

    static Function<Field, Integer> sortFunction = f -> f.getAnnotation(Column.class).order();


    public static String getSheetName(Class<?> cls) {
        String sheetName = ClassUtils.getSimpleName(cls);
        if (cls.isAnnotationPresent(Sheet.class)) {
            Sheet sheetAnno = cls.getAnnotation(Sheet.class);
            sheetName = sheetAnno.name();
        }
        return sheetName;
    }

    public static List<String> getHeaderValues(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        List<String> headerValues = fields.stream().map(field -> field.getAnnotation(Column.class).name())
            .collect(Collectors.toList());

        return headerValues;
    }


    /**
     * 获取经过Column注解排序后的list
     *
     * @param dataCls
     * @return
     */
    public static List<Field> getAllFields(Class<?> dataCls) {
        List<Class<?>> superClsList = ClassUtils.getAllSuperclasses(dataCls);
        // add myself
        superClsList.add(dataCls);
        List<Field> fields = new ArrayList<>();
        superClsList.stream().filter(superCls -> !Objects.equals(superCls,Object.class))
            .forEach(clazz->ClassAssistant.addFields(clazz,fields));
        List<Field> sortFields = sortFields(fields);
        return sortFields;
    }

    private static void addFields(Class<?> clazz, List<Field> fieldList) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (canAccessPrivateMethods()) {
                try {
                    field.setAccessible(true);
                } catch (Exception e) {
                    // ignored.
                }
            }
            if (field.isAccessible() && !fieldList.contains(field) && field.isAnnotationPresent(Column.class)) {
                fieldList.add(field);
            }
        }
    }


    private static List<Field> sortFields(List<Field> oriFields) {
        Column col = oriFields.get(0).getAnnotation(Column.class);
        col.order();

        List<Field> sortedFields = oriFields.stream().sorted(Comparator.comparing(sortFunction))
            .collect(Collectors.toList());
        return sortedFields;
    }

    private static boolean canAccessPrivateMethods() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            return false;
        }
        return true;
    }

    public static int[] getColumnWidth(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        int[] columnWidth = new int[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            int width = fields.get(i).getAnnotation(Column.class).width();
            columnWidth[i] = width > 0 ? width : 0;
        }
        return columnWidth;
    }

    public static CellType[] getCellType(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        CellType[] cellType = new CellType[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            cellType[i] = fields.get(i).getAnnotation(Column.class).cellType();
        }
        return cellType;
    }

    /**
     *
     * @param cls
     * @return
     */
    public static HeaderProps[] getHeaderProps(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        HeaderProps[] hps = new HeaderProps[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            Header anno = fields.get(i).getAnnotation(Header.class);
            HeaderProps hp = new HeaderProps();
            try {
                anno = anno == null ? PropsAnno.class.getDeclaredField("props").getAnnotation(Header.class) : anno;
                hp.setHorizontal(anno.horizontal());
                hp.setVertical(anno.vertical());
                hps[i] = hp;
            } catch (NoSuchFieldException | SecurityException e) {
                throw new ExcelHelperException(e);
            }
        }
        return hps;
    }

    public static CellProps[] getCellProps(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        CellProps[] hps = new CellProps[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            Cell anno = fields.get(i).getAnnotation(Cell.class);
            CellProps cp = new CellProps();
            try {
                anno = anno == null ? PropsAnno.class.getDeclaredField("props").getAnnotation(Cell.class) : anno;
                cp.setHorizontal(anno.horizontal());
                cp.setVertical(anno.vertical());
                hps[i] = cp;
            } catch (NoSuchFieldException | SecurityException e) {
                throw new ExcelHelperException(e);
            }
        }
        return hps;
    }

    public static Map<Integer, HeaderInfo> getHeaderInfo(Class<?> cls, Row header) {
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = header.cellIterator();
        List<Field> fields = ClassAssistant.getAllFields(cls);
        Map<Integer, HeaderInfo> headerInfo = new HashMap<>(fields.size());
        org.apache.poi.ss.usermodel.Cell firstCell = header.getCell(0);
        Field field1st = fields.get(0);
        Object fheaderName = null;
        if(firstCell.getCellTypeEnum().equals(CellType.NUMERIC)){
            fheaderName = firstCell.getNumericCellValue();
        }else {
            fheaderName = firstCell.getStringCellValue();
        }
        Column col1st = field1st.getAnnotation(Column.class);
        if (!Objects.equals(fheaderName, col1st.name())) {
            throw new ExcelHelperException("缺少标题行请检查");
        }

        while (cellIterator.hasNext()) {
            org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
            String headerName = cell.getStringCellValue();
            boolean noDone = true;

            for (Field field : fields) {
                Column col = field.getAnnotation(Column.class);
                String name = col.name();
                if (Objects.equals(headerName, name)) {
                    HeaderInfo hi = new HeaderInfo(col.cellType(), field);
                    headerInfo.put(cell.getColumnIndex(), hi);
                    noDone = false;
                    break;
                }
            }
            //fixme 加固
            if(noDone && cell.getColumnIndex()< fields.size()){
                Field field = fields.get(cell.getColumnIndex());
                Column col = field.getAnnotation(Column.class);
                String name = col.name();
                HeaderInfo hi = new HeaderInfo(col.cellType(), field);
                headerInfo.put(cell.getColumnIndex(), hi);
            }
        }

        if(headerInfo.size()<fields.size()){
            for (int i = 0; i < fields.size(); i++) {
                if(!headerInfo.containsKey(i)){
                    Field field = fields.get(i);
                    Column col = field.getAnnotation(Column.class);
                    String name = col.name();
                    HeaderInfo hi = new HeaderInfo(col.cellType(), field);
                    headerInfo.put(i, hi);
                }
            }
        }
        return headerInfo;
    }

    public static Map<Integer, HeaderInfo> getHeaderInfo(Class<?> cls) {
        List<Field> fields = ClassAssistant.getAllFields(cls);
        CellType[] cellType = CacheFactory.findCellType(cls);

        Map<Integer, HeaderInfo> headerInfo = new HashMap<>(fields.size());
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            Column col = field.getAnnotation(Column.class);
            String name = col.name();
            HeaderInfo hi = new HeaderInfo(col.cellType(), field);
            headerInfo.put(i, hi);
        }
        return headerInfo;
    }

}
