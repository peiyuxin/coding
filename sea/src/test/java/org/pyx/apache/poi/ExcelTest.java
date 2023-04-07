package org.pyx.apache.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.pyx.apache.poi.excel.helper.ExcelHelper;
import org.pyx.apache.poi.excel.helper.annotation.Column;
import org.pyx.apache.poi.excel.helper.annotation.Sheet;
import org.pyx.apache.poi.excel.helper.util.ExcelType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

/**
 * @author pyx
 * @date 2018/12/3
 */
public class ExcelTest {
    /*
    @Test
    public void baseTest() throws Exception {

        ExcelType type = ExcelType.XLSX;

        ExcelHelper.importFromPath();

        Workbook workbook = ExcelHelper.exportWorkbook(type, userList);
        String path1 = type == ExcelType.XLS ? "/Users/pyx/tmp/1.xls" : "/Users/pyx/tmp/1.xlsx";
        File outFile = new File(path1);
        OutputStream out = new FileOutputStream(outFile);
        workbook.write(out);

        Workbook workbook11 = ExcelHelper.exportHeadLessWorkBook(type, userList);
        String path11 = type == ExcelType.XLS ? "/Users/pyx/tmp/11.xls" : "/Users/pyx/tmp/11.xlsx";
        File outFile11 = new File(path11);
        OutputStream out11 = new FileOutputStream(outFile11);
        workbook11.write(out11);

        String path2 = type == ExcelType.XLS ? "/Users/pyx/tmp/2.xls" : "/Users/pyx/tmp/2.xlsx";
        ExcelHelper.exportFile(type, userList, path2);

        String path3 = type == ExcelType.XLS ? "/Users/pyx/tmp/3.xls" : "/Users/pyx/tmp/3.xlsx";
        ExcelHelper.exportFile(type, userList, new File(path3));
        byte[] bs = ExcelHelper.exportByteArray(type, userList);

        String path4 = type == ExcelType.XLS ? "/Users/pyx/tmp/4.xls" : "/Users/pyx/tmp/4.xlsx";
        File file4 = new File(path4);
        OutputStream os = new FileOutputStream(file4);
        os.write(bs);
        os.close();
    }

    @Test
    public void styleTest() {
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cs.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
            HSSFCell cell = workbook.createSheet("test").createRow(0).createCell(0);
            cell.setCellStyle(cs);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("pyx");
            File file = new File("/Users/pyx/tmp/1.xls");
            OutputStream out = new FileOutputStream(file);
            workbook.write(out);
        } catch (Exception e) {
            //
            e.printStackTrace();
        }
    }

    @Test
    public void importTest() {
        String path = "/Users/pyx/tmp/1.xlsx";
        ExcelType type = ExcelType.XLSX;
        List<User> users = ExcelHelper.importFromPath(type, path, User.class);
        System.err.println(users);
    }

    @Test
    public void importTestFoi(){
        while(true) {
            String path = "/Users/pyx/tmp/detail.xlsx";
            ExcelType type = ExcelType.XLSX;
            List<OfflineIncomeExcelDetail> fois = ExcelHelper.importFromPath(type, path,
                OfflineIncomeExcelDetail.class);
            System.err.println(fois);
        }
    }

    @Test
    public void importHeadLessTest() {
        String path = "/Users/pyx/tmp/1.xlsx";
        ExcelType type = ExcelType.XLSX;
        List<User> users = ExcelHelper.importHeadlessFromPath(type, path, User.class);
        System.err.println(users);
    }

    @Test
    public void sortListTest() {
        List<Demo> demoList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Demo demo = new Demo();
            demo.name = "pyx";
            demo.height = new BigDecimal("1.80342");
            demoList.add(demo);
        }
        ExcelHelper.exportFile(ExcelType.XLS, demoList, "/Users/pyx/tmp/DemoList.xls");
        BigDecimal b = new BigDecimal(11);
    }

    @Test
    public void testDiv(){
        BigDecimal bigDecimal = new BigDecimal(Integer.toString(10));
        System.out.println(bigDecimal.divide(new BigDecimal(3),2,BigDecimal.ROUND_DOWN));
        System.out.println(bigDecimal.divide(new BigDecimal(3),2,BigDecimal.ROUND_DOWN).floatValue());
    }

    @Sheet(name = "DemoList")
    static class Demo {
        @Column(name = "姓名")
        private String name;
        @Column(name = "身高", cellType = CellType.NUMERIC)
        private BigDecimal height;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getHeight() {
            return height;
        }

        public void setHeight(BigDecimal height) {
            this.height = height;
        }

    }

     */
}
