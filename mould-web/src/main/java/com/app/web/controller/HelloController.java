package com.app.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.internal.ApiUtil;
import com.app.api.provider.HelloProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Reference(version = "1.0")
    private HelloProvider helloProvider;

    @RequestMapping("hello")
    public Object sayHello(String name) {
        String data = helloProvider.sayHello(name);
        return ApiUtil.result(data);
    }
}