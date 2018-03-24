package com.app.web;

import com.app.core.util.IocUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.app.web"}, scanBasePackageClasses = {IocUtil.class})
public class MouldWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MouldWebApplication.class, args);
    }
}
