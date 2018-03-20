package com.app.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.app.data.dao")
public class MouldDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MouldDataApplication.class, args);
	}
}
