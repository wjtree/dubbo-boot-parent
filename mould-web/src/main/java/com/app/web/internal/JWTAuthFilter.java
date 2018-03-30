//package com.app.web.internal;
//
//import com.app.core.util.JwtUtil;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 校验 JWT Token
// */
//@Log4j2
//public class JWTAuthFilter extends BasicAuthenticationFilter {
//
//    public JWTAuthFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String header = request.getHeader("Authorization");
//
//        if (StringUtils.isBlank(header) || !StringUtils.startsWith(header, "Bearer ")) {
//            log.warn("用户认证失败，请求头中 Authorization 字段不存在或格式错误");
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // 从 Token 中获取用户信息
//        String userId = JwtUtil.parseJWT(header).getSubject();
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        chain.doFilter(request, response);
//    }
//}