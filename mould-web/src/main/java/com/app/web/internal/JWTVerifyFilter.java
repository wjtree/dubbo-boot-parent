package com.app.web.internal;

import com.app.core.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验 JWT Token
 */
@Log4j2
public class JWTVerifyFilter extends BasicAuthenticationFilter {

    public JWTVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 校验 JWT Token
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param chain    FilterChain
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取请求头中的 Authorization 字段
        String header = request.getHeader("Authorization");

        if (StringUtils.isBlank(header) || !StringUtils.startsWith(header, "Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // 从 Token 中获取用户信息
        String userId = JwtUtil.parseJWT(header).getSubject();
        UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(userId, null);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        onSuccessfulAuthentication(request, response, authResult);

        if (log.isDebugEnabled())
            log.debug("[Security] Token 校验成功，用户编号：{}", userId);

        chain.doFilter(request, response);
    }
}