package com.app.server.config;

import com.alibaba.dubbo.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo 服务提供者配置
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
        applicationConfig.setName("mould-server");
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
        registryConfig.setFile("D:\\DownLoad\\mould-server");
        registryConfig.setCheck(false);
        return registryConfig;
    }

    /**
     * 服务提供者缺省值配置
     *
     * @return ProviderConfig
     */
    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(5000);
        providerConfig.setLoadbalance("leastactive");
        providerConfig.setActives(0);
        providerConfig.setVersion("1.0.0");
        providerConfig.setOwner("wjtree");
        providerConfig.setThreads(100);
        providerConfig.setExecutes(200);
        return providerConfig;
    }

    /**
     * 服务提供者协议配置
     *
     * @return ProtocolConfig
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);
        return protocolConfig;
    }

    /**
     * 监控中心配置
     *
     * @return MonitorConfig
     */
    @Bean
    public MonitorConfig monitorConfig() {
        MonitorConfig monitorConfig = new MonitorConfig();
        // 监控中心协议，如果为protocol="registry"，表示从注册中心发现监控中心地址，否则直连监控中心
        monitorConfig.setProtocol("dubbo");
        monitorConfig.setAddress("111.230.173.124:7070");
        return monitorConfig;
    }
}