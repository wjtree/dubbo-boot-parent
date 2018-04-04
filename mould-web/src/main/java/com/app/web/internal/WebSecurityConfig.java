package com.app.web.internal;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.model.User;
import com.app.api.provider.UserProvider;
import com.app.core.util.IocUtil;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

@Log4j2
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        // 忽略 Swagger2 请求
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html**", "/webjars/**")
                // 忽略静态资源
                .antMatchers("/css/**", "/image/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error").defaultSuccessUrl("/index").permitAll()
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTVerifyFilter(authenticationManager()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(IocUtil.getBean(CustomUserDetailsService.class))
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    @Component
    class CustomUserDetailsService implements UserDetailsService {
        @Reference(version = "1.0")
        private UserProvider userProvider;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Object obj = userProvider.searchUser(username);
            if (obj == null)
                throw new UsernameNotFoundException("[Security] 用户不存在");

            // 获取数据库中的用户名和密码
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
            User user = (User) obj;
            String dbName = user.getName();
            String dbPassword = user.getPassword();

            if (log.isDebugEnabled())
                log.debug("[Security] 查询数据库中的用户信息，用户名：{}，密码：{}", dbName, dbPassword);

            return new org.springframework.security.core.userdetails.User(dbName, dbPassword, Lists.newArrayList(authority));
        }
    }
}