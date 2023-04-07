package org.pyx.apache.poi.excel.helper.exception;

/**
 * @author pyx
 * @date 2018/12/3
 */
public class ExcelHelperException extends RuntimeException{

    private static final long serialVersionUID = -8228129537495017399L;

    public ExcelHelperException() {
        super();
    }

    public ExcelHelperException(String message) {
        super(message);
    }

    public ExcelHelperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelHelperException(Throwable cause) {
        super(cause);
    }
}
