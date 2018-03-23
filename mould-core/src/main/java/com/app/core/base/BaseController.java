package com.app.core.base;

import com.app.core.exception.ApiException;
import com.app.core.util.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring MVC 全局异常处理
 */
@Log4j2
public class BaseController {
    /**
     * 返回内部逻辑正常时的请求结果
     *
     * @param modelMap {@link ModelMap}
     * @param data     请求返回数据
     * @return {@link ResponseEntity}
     */
    protected ResponseEntity<Object> getResponse(ModelMap modelMap, Object data) {
        // 请求无内部逻辑错误时，返回该结果
        return getResponse(modelMap, data, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
    }

    /**
     * 返回请求正常时的结果
     *
     * @param modelMap {@link ModelMap}
     * @param data     请求返回数据
     * @param httpCode 请求状态
     * @param httpMsg  请求状态说明
     * @return {@link ResponseEntity}
     */
    private ResponseEntity<Object> getResponse(@NonNull ModelMap modelMap, Object data, int httpCode, String httpMsg) {
        // 清空 ModelMap 数据
        modelMap.clear();
        // 装载请求返回数据
        modelMap.put("data", data);
        modelMap.put("httpcode", httpCode);
        modelMap.put("httpmsg", httpMsg);
        modelMap.put("timestamp", DateUtil.getCurrentDateTime());
        // 此处返回请求正常时的结果
        return ResponseEntity.ok(modelMap);
    }

    /**
     * 统一处理自定义异常
     *
     * @param ex      {@link Exception}
     * @param request {@link HttpServletRequest}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleCustomException(Exception ex, HttpServletRequest request) {
        // 异常代码和异常信息
        int httpCode = ex instanceof ApiException ? ((ApiException) ex).getErrCode() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        String httpMsg = ex instanceof ApiException ? ((ApiException) ex).getErrMsg() : ex.getMessage();

        // 异常日志打印及处理
        log.warn("请求错误，请求地址:{}，异常代码:{}，异常信息:{}.", request.getServletPath(), httpCode, httpMsg);

        // 请求发生内部逻辑错误时，返回该结果
        return getResponse(new ModelMap(), null, httpCode, httpMsg);
    }
}