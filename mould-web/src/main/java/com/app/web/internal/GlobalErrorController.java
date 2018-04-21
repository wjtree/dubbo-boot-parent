package com.app.web.internal;

import com.app.api.internal.ApiResult;
import com.app.api.internal.ApiUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Default implementation of {@link ErrorAttributes}. Provides the following attributes
 * when possible:
 * <ul>
 * <li>timestamp - The time that the errors were extracted</li>
 * <li>status - The status code</li>
 * <li>error - The error reason</li>
 * <li>exception - The class name of the root exception (if configured)</li>
 * <li>message - The exception message</li>
 * <li>errors - Any {@link org.springframework.validation.ObjectError}s from a {@link org.springframework.validation.BindingResult} exception
 * <li>trace - The exception stack trace</li>
 * <li>path - The URL path when the exception was raised</li>
 * </ul>
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    private final ErrorProperties errorProperties;

    // if stack trace elements should be included
    private static final boolean includeStackTrace = false;

    public GlobalErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
        this.errorProperties = new ErrorProperties();
    }

    @RequestMapping
    public ApiResult error(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);

        // 将默认的错误信息字段，转换为 Rest 风格统一返回结果
        return ApiUtil.result(MapUtils.getInteger(map, "status"),
                MapUtils.getString(map, "message"),
                MapUtils.getString(map, "path"));
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }
}