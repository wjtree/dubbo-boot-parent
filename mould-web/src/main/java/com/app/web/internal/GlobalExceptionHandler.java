package com.app.web.internal;

import com.app.api.exception.ApiException;
import com.app.api.internal.ApiResult;
import com.app.api.internal.ApiUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Spring Boot 默认错误视图
     */
    private static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 处理系统异常，返回视图页面
     *
     * @param request HttpServletRequest
     * @param e       Exception
     * @return ModelAndView
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
        Map<String, ?> model = ApiUtil.result(e.getMessage(), request.getRequestURL().toString());
        return new ModelAndView(DEFAULT_ERROR_VIEW, model);
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
        return ApiUtil.result(e.getErrCode(), e.getErrMsg(), request.getRequestURL().toString());
    }
}