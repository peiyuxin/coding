package org.pyx.apache.poi.excel.helper.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.pyx.apache.poi.excel.helper.annotation.BaseProps;
import org.pyx.apache.poi.excel.helper.annotation.CellProps;
import org.pyx.apache.poi.excel.helper.annotation.HeaderProps;
import org.pyx.apache.poi.excel.helper.cache.CacheFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Export workbook util
 *
 * @author pyx
 * @date 2018/12/3
 */
public class ExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    private ExcelUtils() {}

    /**
     * export as a {@link Workbook}, maybe include one or more sheet.
     *
     * @param sheets it's a array, every Collection will create a sheet.
     * @return return a {@link Workbook}
     */
    public static Workbook create(ExcelType type, Collection<?>... sheets) {
        if(ArrayUtils.isEmpty(sheets)) {
            return ExcelType.XLSX == type ? new SXSSFWorkbook() : new HSSFWorkbook();
        }
        return createWorkbook(type,false, sheets);
    }


    public static Workbook createHeadless(ExcelType type, Collection<?>... sheets) {
        if(ArrayUtils.isEmpty(sheets)) {
            return ExcelType.XLSX == type ? new SXSSFWorkbook() : new HSSFWorkbook();
        }
        return createWorkbook(type,true, sheets);
    }

    public static Workbook create(ExcelType type, boolean isHideHead, Collection<?>... sheets) {
        if(ArrayUtils.isEmpty(sheets)) {
            return ExcelType.XLSX == type ? new SXSSFWorkbook() : new HSSFWorkbook();
        }
        return createWorkbook(type,isHideHead,sheets);
    }

    private static Workbook createWorkbook(ExcelType type, boolean isHideHead, Collection<?>... sheets) {
        Workbook workbook = getWorkBookByType(type);
        Arrays.stream(sheets).filter(CollectionUtils::isNotEmpty).forEach(sheet -> createSheet(workbook,isHideHead,sheet));
        return workbook;
    }

    private static Workbook getWorkBookByType(ExcelType type) {
        return ExcelType.XLSX == type ? new SXSSFWorkbook() : new HSSFWorkbook();
    }

    private static void createSheet(Workbook workbook,boolean isHideHead, Collection<?> sheetData) {
        String sheetName = getSheetName(sheetData);
        Sheet sheet = workbook.createSheet(sheetName);
        Class<?> dataCls = sheetData.iterator().next().getClass();

        // excel content (header + data)
        List<String> headerValues = CacheFactory.findHeaderValues(dataCls);
        List<List<Object>> data = DataAssistant.createData(sheetData);
        CellType[] cellType = CacheFactory.findCellType(dataCls);
        int[] columnWidth = CacheFactory.findColumnWidth(dataCls);
        HeaderProps[] hps = CacheFactory.findHeaderProps(dataCls);
        CellProps[] cps = CacheFactory.findCellProps(dataCls);

        // create header row, create data rows
        if(!isHideHead) {
            createHeaderRow(sheet, headerValues, hps, workbook);
        }
        //标题行不支持多行
        int hsize = isHideHead ? 0 : 1;
        createDataRows(sheet, hsize, data, cellType, cps, workbook);
        setColumnWidth(sheet, columnWidth);
    }

    private static void createHeaderRow(Sheet sheet, List<String> headerValues, HeaderProps[] hps, Workbook workbook) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < headerValues.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headerValues.get(i));

            // cell style
            CellStyle hcs = workbook.createCellStyle();
            processCellStyle(hcs, hps[i]);
            cell.setCellStyle(hcs);
        }
    }

    private static void createDataRows(Sheet sheet, int headerRowSize, List<List<Object>> data, CellType[] cellType, CellProps[] cps, Workbook workbook) {
        for (int i = 0; i < data.size(); i++) {
            // 0 row is header, data row from 1.
            if (headerRowSize > 1){
                LOGGER.error("invalid sheet header size, pls check, hsize {}, sheet,{}",headerRowSize,sheet);
            }

            Row row = sheet.createRow(i + headerRowSize);
            List<Object> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellType(cellType[j]);
                if (cellType[j] == CellType.NUMERIC) {
                    String v = rowData.get(j).toString();
                    try {
                        cell.setCellValue(Double.parseDouble(v));
                    } catch (NumberFormatException e) {
                        // 这里处理比较极端，转换成Double类型失败时，不抛出异常，而是将String类型写入
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(v);
                    }
                } else if (cellType[j] == CellType.BOOLEAN) {
                    cell.setCellValue(Boolean.parseBoolean(rowData.get(j).toString()));
                } else {
                    cell.setCellValue(rowData.get(j).toString());
                }

                // cell style
                CellStyle hcs = workbook.createCellStyle();
                hcs.setWrapText(true);
                processCellStyle(hcs, cps[j]);
                cell.setCellStyle(hcs);
            }
        }
    }

    /**
     * process style, @Header and @Cell common props.
     *
     * @param hcs
     * @param baseProps
     */
    private static void processCellStyle(CellStyle hcs, BaseProps baseProps) {
        hcs.setAlignment(baseProps.getHorizontal());
        hcs.setVerticalAlignment(baseProps.getVertical());
    }

    private static void setColumnWidth(Sheet sheet, int[] columnWith) {
        for (int i = 0; i < columnWith.length; i++) {
            // for xlsx, before autoSizeColumn() method, must invoke trackAllColumnsForAutoSizing, otherwise every column's with will be the same.
            if (SXSSFSheet.class.isAssignableFrom(sheet.getClass())) {
                SXSSFSheet st = (SXSSFSheet) sheet;
                st.trackAllColumnsForAutoSizing();
            }

            sheet.autoSizeColumn(i);
            // 1.3 times
            int width = columnWith[i] > 0 ? columnWith[i] : sheet.getColumnWidth(i) * 13 / 10;
            sheet.setColumnWidth(i, width);
        }
    }

    private static String getSheetName(Collection<?> sheetData) {
        Class<?> dataClass = sheetData.iterator().next().getClass();
        return CacheFactory.findSheetName(dataClass);
    }
}

