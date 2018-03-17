package com.app.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.provider.HelloProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class HelloController {
    @Reference(version = "1.0")
    private HelloProvider helloProvider;

    @RequestMapping("hello")
    public String sayHello(@RequestParam String name) {
        String sayHello = helloProvider.sayHello(name);
        if (log.isInfoEnabled())
            log.info("调用 sayHello 接口成功，返回结果：{}", sayHello);
        return sayHello;
    }
}