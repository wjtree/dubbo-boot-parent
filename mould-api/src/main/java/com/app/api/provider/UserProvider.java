package com.app.api.provider;

import com.app.api.model.User;

public interface UserProvider {
    /**
     * 用户登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return Object
     */
    Object signIn(String username, String password);

    /**
     * 用户注册接口
     *
     * @param user 用户对象
     * @return Object
     */
    Object signUp(User user);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return Object
     */
    Object searchUser(String username);
}