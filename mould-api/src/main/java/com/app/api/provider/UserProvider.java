package com.app.api.provider;

import com.app.api.model.User;

import java.util.Map;

public interface UserProvider {
    /**
     * 登录验证
     *
     * @param account  用户名
     * @param password 密码
     * @return
     */
    Map<String, Object> login(String account, String password);

    /**
     * 插入用户对象
     *
     * @param user User
     */
    void addUser(User user);
}