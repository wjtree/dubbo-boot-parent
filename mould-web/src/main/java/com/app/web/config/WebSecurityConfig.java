package com.app.web.config;

import com.alibaba.fastjson.JSON;
import com.app.api.internal.ApiResult;
import com.app.api.internal.ApiUtil;
import com.app.api.model.User;
import com.app.core.util.IocUtil;
import com.app.core.util.JwtUtil;
import com.app.web.consumer.UserConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Spring Security 配置
 */
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        // 忽略 Swagger2 请求
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html**", "/webjars/**")
                // 忽略首页
                .antMatchers("/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用表单TOKEN
        http.csrf().disable()
                // 禁用 HttpSession
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 请求认证
                .authorizeRequests()
                // 忽略 static 目录下静态资源文件的认证
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // 忽略以下请求
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/signIn", "/signUp").permitAll()
                // 对除忽略路径外，其他所有请求路径进行认证
                .anyRequest().authenticated().and()
                // 登录验证并分发 JWT TOKEN
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // 验证 JWT TOKEN
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
//        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(IocUtil.getBean(CustomUserDetailsService.class))
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    @Component
    class CustomUserDetailsService implements UserDetailsService {
        @Autowired
        private UserConsumer consumer;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Object obj = consumer.searchUser(username);
            if (obj == null)
                throw new UsernameNotFoundException("[Security] 用户不存在");

            // 获取数据库中的用户名和密码
            User user = (User) obj;
            String dbName = user.getName();
            String dbPassword = user.getPassword();
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("USER,ADMIN");

            if (log.isDebugEnabled())
                log.debug("[Security] 查询数据库中的用户信息，用户名：{}，密码：{}", dbName, dbPassword);

            return new org.springframework.security.core.userdetails.User(dbName, dbPassword, authorities);
        }
    }

    /**
     * 处理登录请求，用户认证并分发TOKEN
     */
    class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

        JWTLoginFilter(String url, AuthenticationManager authManager) {
            super(new AntPathRequestMatcher(url, HttpMethod.POST.name()));
            setAuthenticationManager(authManager);
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                throws AuthenticationException, IOException, ServletException {
            // 解析请求参数
            User user;
            try {
                user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            } catch (Exception e) {
                throw new UsernameNotFoundException("认证参数错误");
            }

            if (log.isDebugEnabled())
                log.debug("[Security] 认证用户信息：{}", JSON.toJSONString(user));

            // 获取用户认证对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
            // 验证用户名和密码
            return getAuthenticationManager().authenticate(authenticationToken);
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
                throws IOException {
            // 获取用户名和用户权限
            String name = authResult.getName();
            Object[] authorities = AuthorityUtils.authorityListToSet(authResult.getAuthorities()).toArray();

            // 生成 JWT TOKEN，将权限数组使用英文逗号分隔
            String jwt = JwtUtil.buildJWT(name, StringUtils.join(authorities, ","));
            // 将 TOKEN 装在到返回结果对象中
            ApiResult result = ApiUtil.result(jwt);
            String json = JSON.toJSONString(result);

            if (log.isDebugEnabled())
                log.debug("[Security] 认证用户信息成功：{}", json);

            // 将 ApiResult 写入 body
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getOutputStream().write(json.getBytes(CharEncoding.UTF_8));
        }


        @Override
        protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
                throws IOException {
            // 返回认证失败的结果
            ApiResult result = ApiUtil.result(failed, request.getRequestURI());
            String json = JSON.toJSONString(result);

            if (log.isDebugEnabled())
                log.debug("[Security] 认证用户信息失败：{}", json);

            // 将 ApiResult 写入 body
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getOutputStream().write(json.getBytes(CharEncoding.UTF_8));
        }
    }

    /**
     * 验证JWT TOKEN
     */
    class JWTAuthenticationFilter extends GenericFilterBean {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
            // 获取请求头中的 Authorization 字段
            String claimsJws = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
            Authentication authentication = null;

            if (StringUtils.isNotBlank(claimsJws) || StringUtils.startsWith(claimsJws, JwtUtil.PREFIX)) {
                // 解析 Authorization 字段中的 TOKEN
                Claims claims = JwtUtil.parseJWT(claimsJws);
                // 根据解析的 TOKEN 获取用户名和权限
                String subject = claims.getSubject();
                String auth = claims.get(JwtUtil.CLAIM_AUTH, String.class);
                // 将用户权限字符串转换为集合
                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(auth);

                // 获取并装载用户认证对象
                authentication = new UsernamePasswordAuthenticationToken(subject, null, authorities);

                if (log.isDebugEnabled())
                    log.debug("[Security] 校验 JWT Token 成功");
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }
}