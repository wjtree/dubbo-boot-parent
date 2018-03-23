package com.app.server.service;

import com.app.server.dao.UserMapper;
import com.app.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 检查用户名和密码是否匹配
     *
     * @param account  用户名
     * @param password 密码
     * @return true or false
     */
    public boolean checkUser(String account, String password) {
        User user = userMapper.selectByNameAndPwd(account, password);
        return user != null;
    }
}