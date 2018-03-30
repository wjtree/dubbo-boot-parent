package com.app.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.internal.ApiUtil;
import com.app.api.model.User;
import com.app.api.provider.UserProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户管理接口集", tags = "用户管理接口集")
@RestController
public class UserController {
    @Reference(version = "1.0")
    private UserProvider userProvider;

    @ApiOperation(value = "用户登录接口", notes = "根据用户名和密码校验用户合法性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataTypeClass = String.class)
    })
    @PostMapping("signIn")
    public Object signIn(String username, String password) {
        Object data = userProvider.signIn(username, password);
        return ApiUtil.result(data);
    }

    @ApiOperation(value = "用户注册接口", notes = "使用JSON字符串作为请求参数")
    @ApiImplicitParam(name = "user", value = "用户对象", required = true, dataTypeClass = User.class)
    @PostMapping("signUp")
    public Object signUp(@RequestBody User user) {
        userProvider.signUp(user);
        return ApiUtil.result(user);
    }
}