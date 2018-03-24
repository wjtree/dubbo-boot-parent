package com.app.api.exception;

import com.app.api.internal.ApiCode;

/**
 * API 异常，包含错误码和错误消息
 */
public class ApiException extends RuntimeException {
    private Integer errCode = ApiCode.INTERNAL_SERVER_ERROR.code();

    private String errMsg = ApiCode.INTERNAL_SERVER_ERROR.message();

    private static final long serialVersionUID = 5785494190339545205L;

    public ApiException() {
        super();
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

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ApiException(Integer errCode, String errMsg) {
        // 装载异常消息 detailMessage
        super("[" + errCode + "] " + errMsg);

        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ApiException(ApiCode apiCode) {
        // 装载异常消息 detailMessage
        super("[" + apiCode.code() + "] " + apiCode.message());

        this.errCode = apiCode.code();
        this.errMsg = apiCode.message();
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}