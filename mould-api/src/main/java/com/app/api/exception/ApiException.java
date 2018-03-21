package com.app.api.exception;

public class ApiException extends Exception {
    private int errCode = 500;

    private String errMsg = "系统内部错误";

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(int errCode, String errMsg) {
        super("[" + errCode + "] " + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}