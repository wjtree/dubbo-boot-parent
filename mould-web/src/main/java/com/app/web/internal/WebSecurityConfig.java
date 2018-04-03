package com.app.web.internal;

import com.app.core.util.IocUtil;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@EnableWebSecurity(debug = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        // 忽略 Swagger2 请求
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html**", "/webjars/**")
                .antMatchers(HttpMethod.POST, "/signIn", "/signUp");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/css/**", "/image/**", "/index").permitAll()
                .antMatchers(HttpMethod.POST, "/signIn", "/signUp").permitAll()
//                .anyRequest().authenticated().and()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error").and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthFilter(authenticationManager()))
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(IocUtil.getBean(UserService.class))
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }
}