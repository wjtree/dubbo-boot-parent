package com.app.data;

import com.app.core.util.IocUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.app.data"}, scanBasePackageClasses = {IocUtil.class})
@MapperScan("com.app.data.dao")
public class MouldDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(MouldDataApplication.class, args);
    }
}
