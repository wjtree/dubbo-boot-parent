package com.app.web.internal;

import com.app.core.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求鉴权拦截器
 */
@Log4j2
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;

        // 获取 HTTP HEAD 中的 TOKEN
        String authorization = request.getHeader("Authorization");
        // 校验 TOKEN
        flag = StringUtils.isNotBlank(authorization) ? JwtUtil.checkJWT(authorization) : false;
        // 如果校验未通过，返回 401 状态
        if (!flag)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        return flag;
    }
}