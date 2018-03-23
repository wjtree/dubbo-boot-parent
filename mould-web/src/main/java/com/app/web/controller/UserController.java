package com.app.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.provider.UserProvider;
import com.app.core.base.BaseController;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("user")
public class UserController extends BaseController {
    @Reference(version = "1.0")
    private UserProvider userProvider;

    @PostMapping("login")
    public Object login(ModelMap modelMap, @RequestParam String account, @RequestParam String password) {
        Map<String, Object> map = userProvider.login(account, password);
        return getResponse(modelMap, map);
    }
}