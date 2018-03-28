package com.app.server.service;

import com.alibaba.fastjson.JSON;
import com.app.api.model.User;
import com.app.server.dao.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
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

    /**
     * 插入一条用户记录
     *
     * @param user User
     */
    public void addUser(User user) {
        userMapper.insert(user);
        log.info("成功插入一条用户记录：{}", JSON.toJSONString(user));
    }
}