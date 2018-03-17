package com.app.web.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo 服务消费者配置
 */
@Configuration
public class DubboConfiguration {
    /**
     * 应用信息配置，用于计算依赖关系
     *
     * @return ApplicationConfig
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("mould-web");
        applicationConfig.setVersion("1.0");
        applicationConfig.setOrganization("china");
        applicationConfig.setOwner("wjtree");
        return applicationConfig;
    }

    /**
     * 注册中心配置
     *
     * @return RegistryConfig
     */
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://111.230.173.124:3381");
        registryConfig.setClient("curator");
        registryConfig.setFile("D:\\DownLoad\\mould-web");
        registryConfig.setCheck(false);
        return registryConfig;
    }

    /**
     * 服务消费者缺省值配置
     *
     * @return ConsumerConfig
     */
    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(5000);
        consumerConfig.setLoadbalance("leastactive");
        consumerConfig.setCheck(false);
        consumerConfig.setOwner("wjtree");
        consumerConfig.setVersion("1.0.0");
        return consumerConfig;
    }
}