package com.app.web.internal;

import com.app.api.exception.ApiException;
import com.app.api.internal.ApiResult;
import com.app.api.internal.ApiUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理系统异常，返回视图页面
     *
     * @param request HttpServletRequest
     * @param e       Exception
     * @return ModelAndView
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult defaultErrorHandler(HttpServletRequest request, Exception e) {
        log.error("异常类型：{}，\r\n异常消息：{}，\r\n异常栈：{}", e.getClass().getName(), e.getMessage(), e.getStackTrace());
        return ApiUtil.result(e, request.getRequestURL().toString());
    }

    /**
     * 处理 ApiException 异常，返回 ApiResult 对象
     *
     * @param request HttpServletRequest
     * @param e       ApiException
     * @return ApiResult
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ApiResult apiErrorHandler(HttpServletRequest request, ApiException e) {
        log.error("异常类型：{}，\r\n异常消息：{},\r\n异常栈：{}", e.getClass().getName(), e.getMessage(), e.getStackTrace());
        return ApiUtil.result(e, request.getRequestURL().toString());
    }
}