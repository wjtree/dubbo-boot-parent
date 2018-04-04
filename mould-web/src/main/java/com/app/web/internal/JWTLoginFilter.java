package com.app.web.internal;

import com.app.core.util.JwtUtil;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

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

    /**
     * 验证用户名和密码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Authentication
     * @throws AuthenticationException AuthenticationServiceException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            if (!request.getMethod().equals("POST"))
                throw new AuthenticationServiceException("[Security] Authentication method not supported: " + request.getMethod());

            // 获取用户名和密码
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // 获取用户权限集合
            Collection<GrantedAuthority> authorities = Lists.newArrayList(new SimpleGrantedAuthority("USER"));

            // 使用用户名和密码的方式认证
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, authorities);
            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);

            return authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            throw new AuthenticationServiceException("[Security] 认证出错，" + e.getMessage());
        }
    }

    /**
     * 认证成功后的信息处理，生成 JWT Token
     *
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @param chain      FilterChain
     * @param authResult Authentication
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 从认证对象中获取用户信息
        String userId = authResult.getPrincipal().toString();
        // 将 Token 装载到请求头和响应头中
        response.addHeader("Authorization", JwtUtil.buildJWT(userId));

        if (log.isDebugEnabled())
            log.debug("[Security] 生成 JWT Token 成功，用户编号：{}", userId);

        // 执行父类方法
        super.successfulAuthentication(request, response, chain, authResult);
    }
}