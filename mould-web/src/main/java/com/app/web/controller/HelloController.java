package com.app.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.exception.ApiException;
import com.app.api.internal.ApiCode;
import com.app.api.internal.ApiUtil;
import com.app.api.provider.HelloProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "示例接口集", tags = "示例接口集")
@RestController
public class HelloController {
    @Reference(version = "1.0")
    private HelloProvider helloProvider;

    @ApiOperation(value = "简单示例接口", notes = "返回字符串")
    @ApiImplicitParam(name = "name", value = "姓名", required = true, dataTypeClass = String.class)
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