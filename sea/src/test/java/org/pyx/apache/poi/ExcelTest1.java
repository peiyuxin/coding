package org.pyx.apache.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.pyx.apache.poi.excel.helper.util.HeaderInfo;

/**
 * @author pyx
 * @date 2023/4/4
 */
public class ExcelTest1 {
    public static void main(String[] args) {
        HashMap smap = new HashMap();
        File f = new File("/Users/pyx/Downloads/人大MBA复试成绩.xlsx");
        try {
            Workbook workbook = null;
            try {
                workbook = new XSSFWorkbook(f);
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
            Sheet sheet = workbook.getSheetAt(0);
            //Row row = sheet.getRow(7);
            Iterator<Row> rows = sheet.rowIterator();
            rows.next();
            Row r = rows.next();
            Cell c0 = r.getCell(1);
            if(null != c0){
                System.out.println(c0.getStringCellValue());
            }
            Cell c = r.getCell(6);
            if(null != c){
                System.out.println(c.getStringCellValue());
            }
            List data = new ArrayList<>();
            while (rows.hasNext()) {
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();
                Cell cell0 = row.getCell(1);
                String no = "";
                double fenshu = 0;
                if(null != cell0){
                    no = cell0.getStringCellValue();
                    System.out.println(cell0.getStringCellValue());
                }
                Cell cell = row.getCell(6);
                if(null != cell){
                    try {
                        //System.out.println(cell.getCellStyle().toString());
                        fenshu = cell.getNumericCellValue();
                        System.out.println(cell.getNumericCellValue());
                        smap.put(no,fenshu);
                        fenshu = 0;
                    } catch (Exception e) {
                        System.out.println(cell.getStringCellValue());
                        throw new RuntimeException(e);
                    }
                }
                /*while (cells.hasNext()) {
                    Cell cell = cells.next();
                    System.out.println(cell.getStringCellValue());
                }*/
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        deal(smap);
    }

    private static void deal(HashMap<String,Double> smap) {
        String path = "/Users/pyx/Downloads/2023人大复试名单.xlsx";
        File f1 = new File("/Users/pyx/Downloads/2023人大复试名单.xlsx");
        try {
            Workbook workbook = null;
            try {
                workbook = new XSSFWorkbook(f1);
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
            Sheet sheet = workbook.getSheetAt(0);
            //Row row = sheet.getRow(7);
            Iterator<Row> rows = sheet.rowIterator();
            rows.next();
            rows.next();
            while (rows.hasNext()) {
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();
                Cell cell0 = row.getCell(1);
                String no = "";
                Double fenshu = 0.0;
                if(null != cell0){
                    Double nod = cell0.getNumericCellValue();
                   /* BigDecimal bigd = new BigDecimal(nod.toString());
                    no = bigd.toString();
                    System.out.println("读取的no"+no);
                    fenshu = smap.get(no);
                    System.out.println(no+"读取的分数："+fenshu);*/
                    no = ds.format(nod).trim();
                    System.out.println("读取的no"+no);
                    fenshu = smap.get(no);
                    if (null != fenshu){
                        System.out.println(no+"读取的分数："+fenshu);
                        Cell cell = row.getCell(7);
                        if(cell == null){
                            cell = row.createCell(7);
                        }
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(fenshu);
                    }

                }
            }
            FileOutputStream fos = new FileOutputStream(path);
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static DecimalFormat ds = new DecimalFormat("0");

}
