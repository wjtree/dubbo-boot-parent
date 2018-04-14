package com.app.web.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.provider.HelloProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloConsumer {
    @Reference(version = "1.0")
    private HelloProvider helloProvider;

    @Value("${info.app.message}")
    private String infoAppMsg;

    public String sayHello(String name) throws Exception {
        return helloProvider.sayHello(infoAppMsg + " - " + name);
    }
}
