package com.app.web.internal;

import com.app.api.model.User;
import com.app.core.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * attemptAuthentication 接收并解析用户凭证<br/>
 * successfulAuthentication 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
 */
@Log4j2
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 将请求中的 body 转换为 User 对象
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            // 使用用户名和密码的方式认证
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());

            if (log.isDebugEnabled())
                log.debug("[JWT] 验证用户成功，用户编号：{}", user.getId());

            return authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new AuthenticationServiceException("[JWT] 用户名与密码校验出错，错误原因：" + e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {

        // 从认证对象中获取用户信息
        String userId = authResult.getPrincipal().toString();
        // 获取 JWT Token
        String token = JwtUtil.buildJWT(userId);
        // 将 Token 装载到响应头中
        response.addHeader("Authorization", token);

        if (log.isDebugEnabled())
            log.debug("[JWT] 生成 JWT Token 成功，用户编号：{}", userId);
    }
}