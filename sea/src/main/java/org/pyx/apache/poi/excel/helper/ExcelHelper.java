package org.pyx.apache.poi.excel.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pyx.apache.poi.excel.helper.exception.ExcelHelperException;
import org.pyx.apache.poi.excel.helper.util.DataAssistant;
import org.pyx.apache.poi.excel.helper.util.ExcelType;
import org.pyx.apache.poi.excel.helper.util.ExcelUtils;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * the root operation class, you'll use it to create or export excel files/{@link Workbook}
 *
 * @author pyx
 * @date 2018/12/3
 * @since JDK 1.7
 */
public class ExcelHelper {
    private ExcelHelper() {}

    /**
     * <p>
     * export as {@link Workbook}.
     * <p>
     * if you do not want to export by ExcelHelper, you can only create a workbook, then operate the workbook by yourself.
     *
     * @param type {@link ExcelType}
     * @param sheets your bussiness data.
     * @return return {@link Workbook}
     */
    public static Workbook exportWorkbook(ExcelType type, Collection<?>... sheets) {
        return ExcelUtils.create(type, sheets);
    }

    public static Workbook exportHeadLessWorkBook(ExcelType type, Collection<?>... sheets) {
        return ExcelUtils.createHeadless(type, sheets);
    }

    /**
     * export as a file.
     *
     * @param type {@link ExcelType}
     * @param sheets your bussiness data.
     * @param path file path
     */
    public static void exportFile(ExcelType type, Collection<?> sheets, String path) {
        exportFile(type, sheets, new File(path));
    }

    /**
     * export as a file.
     *
     * @param type {@link ExcelType}
     * @param sheets sheets your bussiness data.
     * @param newFile which file you'll write to.
     */
    public static void exportFile(ExcelType type, Collection<?> sheets, File newFile) {
        try (OutputStream out = new FileOutputStream(newFile)) {
            exportWorkbook(type, sheets).write(out);
        } catch (Exception e) {
            throw new ExcelHelperException("write to file faild." + e, e);
        }
    }

    /**
     * export to byte array.
     *
     * @param type {@link ExcelType}
     * @param sheets sheets sheets your bussiness data.
     * @return return a byte array with data.
     */
    public static byte[] exportByteArray(ExcelType type, Collection<?> sheets) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            exportWorkbook(type, sheets).write(out);
        } catch (IOException e) {
            throw new ExcelHelperException("write to file faild." + e, e);
        }
        return out.toByteArray();
    }

    /**
     * import from path.
     *
     * @param type {@link ExcelType}
     * @param path the file path
     * @param cls target Class<T>
     * @return return a {@link List}
     */
    public static <T> List<T> importFromPath(ExcelType type, String path, Class<T> cls) {
        File importFile = new File(path);
        if (!importFile.exists()) {
            throw new ExcelHelperException("the file " + path + " does not exist.");
        }
        return importFromFile(type, importFile, cls);
    }

    /**
     * import from file.
     *
     * @param type {@link ExcelType}
     * @param importFile the file path
     * @param cls target Class<T>
     * @return return a {@link List}
     */
    public static <T> List<T> importFromFile(ExcelType type, File importFile, Class<T> cls) {
        return importFromFile(type,importFile,cls,true);
    }

    /**
     * import from file.
     *
     * @param type {@link ExcelType}
     * @param importFile the file path
     * @param cls target Class<T>
     * @param showHead import with header
     * @return return a {@link List}
     */
    public static <T> List<T> importFromFile(ExcelType type, File importFile, Class<T> cls, boolean showHead) {
        if (!importFile.exists()) {
            throw new ExcelHelperException("the file " + importFile.getName() + " does not exist.");
        }
        InputStream in = null;
        try {
            in = new FileInputStream(importFile);
        } catch (FileNotFoundException e) {
            throw new ExcelHelperException(e);
        }
        return importFromInputStream(type, in, cls, showHead);
    }

    /**
     * import from ByteArray
     *
     * @param type {@link ExcelType}
     * @param byteArr byte array to import.
     * @param cls target Class<T>
     * @return return a {@link List}
     */
    public static <T> List<T> importFromByteArray(ExcelType type, byte[] byteArr, Class<T> cls) {
        if (ArrayUtils.isEmpty(byteArr)) {
            return new ArrayList<>();
        }
        boolean showHead = true;
        InputStream in = new ByteArrayInputStream(byteArr);
        return importFromInputStream(type, in, cls, showHead);
    }

    /**
     * import from {@link InputStream}
     *
     * @param type {@link ExcelType}
     * @param inputStream {@link InputStream}
     * @param cls target Class<T>
     * @return return a {@link List}
     */
    public static <T> List<T> importFromInputStream(ExcelType type, InputStream inputStream, Class<T> cls) {
        if (inputStream == null) {
            throw new ExcelHelperException("the inputStream does not exist.");
        }
        boolean showHead = true;
        return importFromInputStream(type, inputStream, cls, showHead);
    }

    /**
     * import from {@link InputStream}
     *
     * @param type {@link ExcelType}
     * @param inputStream {@link InputStream}
     * @param cls target Class<T>
     * @return return a {@link List}
     */
    public static <T> List<T> importFromInputStream(ExcelType type, InputStream inputStream, Class<T> cls, boolean showHead) {
        List<T> data = new ArrayList<>();
        try (Workbook workbook = type == ExcelType.XLS ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            data = DataAssistant.createDataFromSheet(sheet, cls, showHead);
        } catch (IOException e) {
            throw new ExcelHelperException(e);
        }
        return data;
    }

    /**
     * import from path.
     *
     * @param type {@link ExcelType}
     * @param path the file path
     * @param cls target Class<T>
     * @return return a {@link List}
     */
    public static <T> List<T> importHeadlessFromPath(ExcelType type, String path, Class<T> cls) {
        File importFile = new File(path);
        if (!importFile.exists()) {
            throw new ExcelHelperException("the file " + path + " does not exist.");
        }
        return importFromFile(type, importFile, cls);
    }

}
