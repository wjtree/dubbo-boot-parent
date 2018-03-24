package com.app.api.internal;

import java.io.Serializable;

/**
 * API 请求统一返回对象
 *
 * @param <T>
 */
public class ApiResult<T> implements Serializable {
    /**
     * API 请求返回码
     */
    private Integer code;
    /**
     * API 请求返回消息
     */
    private String message;
    /**
     * API 请求地址
     */
    private String url;
    /**
     * API 请求时间戳
     */
    private Long timestamp;
    /**
     * API 请求成功时的返回结果
     */
    private T data;

    private static final long serialVersionUID = -2054892760595161990L;

    public ApiResult() {
    }

    public ApiResult(T data) {
        this.code = ApiCode.SUCCESS.code();
        this.message = ApiCode.SUCCESS.message();
        this.url = null;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public ApiResult(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.url = null;
        this.timestamp = System.currentTimeMillis();
        this.data = null;
    }

    public ApiResult(Integer code, String message, String url) {
        this.code = code;
        this.message = message;
        this.url = url;
        this.timestamp = System.currentTimeMillis();
        this.data = null;
    }

    public ApiResult(Integer code, String message, String url, Long timestamp, T data) {
        this.code = code;
        this.message = message;
        this.url = url;
        this.timestamp = timestamp;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}