package com.app.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * Spring Session 配置
 */
@EnableRedisHttpSession
public class HttpSessionConfig {
    /**
     * 利用Redis HttpSession在使用REST端点时支持Web应用程序<br/>
     * 不使用 cookie，将 sessionId 存放在返回头的 X-Auth-Token 字段中
     *
     * @return HttpSessionIdResolver
     */
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }
}