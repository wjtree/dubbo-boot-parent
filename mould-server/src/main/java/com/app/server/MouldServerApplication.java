package com.app.server;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.app.server.provider")
public class MouldServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MouldServerApplication.class, args);
	}
}
