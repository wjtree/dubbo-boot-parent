package com.app.api.internal;

/**
 * API 请求返回码和返回消息枚举
 */
public enum ApiCode {
    /**
     * 请求成功
     */
    SUCCESS(200, "Success"),
    /**
     * 未登录
     */
    UNAUTHORIZED(401, "Unauthorized"),
    /**
     * 系统内部错误
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    // 返回码
    private final Integer code;
    // 返回消息
    private final String message;

    ApiCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return code;
    }

    public String message() {
        return message;
    }
}