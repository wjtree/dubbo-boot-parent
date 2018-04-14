package com.app.server;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.app.core.util.IocUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.app.server"}, scanBasePackageClasses = {IocUtil.class})
@EnableCaching
@MapperScan("com.app.server.dao")
@DubboComponentScan("com.app.server.provider")
public class MouldServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MouldServerApplication.class, args);
    }
}
