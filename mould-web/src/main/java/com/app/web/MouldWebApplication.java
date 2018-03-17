package com.app.web;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.app.web.controller")
public class MouldWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MouldWebApplication.class, args);
    }
}
