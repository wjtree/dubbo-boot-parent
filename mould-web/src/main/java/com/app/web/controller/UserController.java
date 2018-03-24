package com.app.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.internal.ApiUtil;
import com.app.api.provider.UserProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference(version = "1.0")
    private UserProvider userProvider;

    @PostMapping("login")
    public Object login(String account, String password) {
        Map<String, Object> data = userProvider.login(account, password);
        return ApiUtil.result(data);
    }
}