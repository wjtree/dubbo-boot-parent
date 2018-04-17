package com.app.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 标题
     */
    private static final String TITLE = "dubbo-boot-parent API";
    /**
     * 描述
     */
    private static final String DESCRIPTION = "@2018 Copyrignt. Powered by WangJian.";
    /**
     * 项目网址
     */
    private static final String TERMS_OF_SERVICE_URL = "https://github.com/wjtree/dubbo-boot-parent";
    /**
     * 项目作者
     */
    private static final String NAME = "WangJian";
    /**
     * 作者网站
     */
    private static final String URL = "https://www.wjtree.xin";
    /**
     * 作者邮箱
     */
    private static final String EMAIL = "21wjtree@163.com";
    /**
     * 开源协议
     */
    private static final String LICENSE = "Apache License, Version 2.0";
    /**
     * 开源协议网址
     */
    private static final String LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0";
    /**
     * 接口版本
     */
    private static final String VERSION = "1.0";

    /**
     * 设置Swagger UI生成的接口目录
     *
     * @return Docket
     */
    @Bean
    public Docket docket() {
        /*
         * 文档分组名 groupName("full")
         */
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.app.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 设置Swagger UI生成的接口文档
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .termsOfServiceUrl(TERMS_OF_SERVICE_URL)
                .contact(new Contact(NAME, URL, EMAIL))
                .license(LICENSE)
                .licenseUrl(LICENSE_URL)
                .version(VERSION)
                .build();
    }
}