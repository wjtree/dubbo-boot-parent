package com.app.server.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.api.model.User;
import com.app.api.provider.UserProvider;
import com.app.core.util.JwtUtil;
import com.app.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Service(version = "1.0")
public class UserProviderImpl implements UserProvider {
    @Autowired
    private UserService userService;

    @Override
    public Object signIn(String username, String password) {
        Map<String, Object> map = null;
        // 检查是否存在用户
        boolean flag = userService.checkUser(username, password);
        if (flag) {
            // 获取 JWT Token
            String token = JwtUtil.buildJWT(username);
            // 装载返回数据
            map = new HashMap<>();
            map.put("account", username);
            map.put("token", token);
        }
        return map;
    }

    @Override
    public Object signUp(User user) {
        return userService.addUser(user);
    }

    @Override
    public Object searchUser(String username) {
        return userService.searchUser(username);
    }
}