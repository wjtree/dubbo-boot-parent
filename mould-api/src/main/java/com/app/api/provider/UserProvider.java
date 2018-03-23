package com.app.api.provider;

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
}