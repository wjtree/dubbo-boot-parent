package com.app.web.internal;

import com.app.core.util.JwtUtil;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
//            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // 使用用户名和密码的方式认证
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, Lists.newArrayList(authority));

            if (log.isDebugEnabled())
                log.debug("[JWT] 验证用户成功，用户编号：{}", username);

            return authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new AuthenticationServiceException("[JWT] 用户名与密码校验出错，错误原因：" + e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // 从认证对象中获取用户信息
        String userId = authResult.getPrincipal().toString();
        // 获取 JWT Token
        String token = JwtUtil.buildJWT(userId);
        // 将 Token 装载到请求头和响应头中
        request.setAttribute("Authorization", token);
        response.addHeader("Authorization", token);


        if (log.isDebugEnabled()) {
            log.debug("[JWT] 生成 JWT Token 成功，用户编号：{}", userId);
        }

        doFilter(request,response,chain);
    }
}