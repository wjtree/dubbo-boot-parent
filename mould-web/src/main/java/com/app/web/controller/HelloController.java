package com.app.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.exception.ApiException;
import com.app.api.internal.ApiCode;
import com.app.api.internal.ApiUtil;
import com.app.api.provider.HelloProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Reference(version = "1.0")
    private HelloProvider helloProvider;

    @RequestMapping("hello")
    public Object sayHello(String name) throws Exception {
        if (StringUtils.equalsIgnoreCase(name, "aaa"))
            throw new ApiException(ApiCode.UNAUTHORIZED);
        else if (StringUtils.equalsIgnoreCase(name, "bbb"))
            throw new RuntimeException("运行时异常");
        else if (StringUtils.equalsIgnoreCase(name, "ccc"))
            throw new Exception("待检查异常，需捕获处理");

        String data = helloProvider.sayHello(name);
        return ApiUtil.result(data);
    }
}