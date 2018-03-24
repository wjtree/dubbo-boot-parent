package com.app.api.internal;

import com.app.api.exception.ApiException;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.Map;

/**
 * API 请求统一返回结果工具类
 */
public class ApiUtil {
    /**
     * API 请求成功时的返回结果
     *
     * @param data 返回结果
     * @param <T>  返回结果数据类型
     * @return ApiResult
     */
    public static <T> ApiResult result(T data) {
        return new ApiResult<>(data);
    }

    /**
     * API 请求失败时的返回结果
     *
     * @param code    请求返回码
     * @param message 请求返回消息
     * @return ApiResult
     */
    public static ApiResult result(Integer code, String message) {
        return new ApiResult(code, message);
    }

    /**
     * API 请求失败时的返回结果
     *
     * @param code    请求返回码
     * @param message 请求返回消息
     * @param url     请求地址
     * @return ApiResult
     */
    public static ApiResult result(Integer code, String message, String url) {
        return new ApiResult(code, message, url);
    }

    /**
     * API 请求失败时的返回结果
     *
     * @param e   ApiException
     * @param url 请求地址
     * @return ApiResult
     */
    public static ApiResult result(ApiException e, String url) {
        return new ApiResult(e.getErrCode(), e.getErrMsg(), url);
    }

    /**
     * API 请求失败时的返回结果
     *
     * @param e   Exception
     * @param url 请求地址
     * @return ApiResult
     */
    public static ApiResult result(Exception e, String url) {
        return new ApiResult(null, e.getMessage(), url);
    }

    /**
     * API 请求返回结果
     *
     * @param code      请求返回码
     * @param message   请求返回消息
     * @param url       请求地址
     * @param timestamp 请求时间戳，服务器时间
     * @param data      请求成功时的返回结果
     * @param <T>       返回结果数据类型
     * @return ApiResult
     */
    public static <T> ApiResult result(Integer code, String message, String url, Long timestamp, T data) {
        return new ApiResult<>(code, message, url, timestamp, data);
    }

    /**
     * API 请求失败时的返回结果
     *
     * @param message 请求返回消息
     * @param url     请求地址
     * @return Map
     */
    public static Map<String, ?> result(String message, String url) {
        Map<String, ?> map = null;
        try {
            ApiResult result = new ApiResult(null, message, url);
            map = PropertyUtils.describe(result);
            map.remove("class");
        } catch (Exception e) {
            // ignore
        }
        return map;
    }
}