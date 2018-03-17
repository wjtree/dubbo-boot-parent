package com.app.server.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.api.provider.HelloProvider;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service(version = "1.0")
public class HelloProviderImpl implements HelloProvider {
    @Override
    public String sayHello(String name) {
        if (log.isInfoEnabled())
            log.info("调用 sayHello 接口参数：{}", name);
        return "Hello " + name + " !";
    }
}