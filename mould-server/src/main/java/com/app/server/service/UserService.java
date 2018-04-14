package com.app.server.service;

import com.app.api.model.User;
import com.app.server.dao.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@CacheConfig(cacheNames = "users")
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 检查用户名和密码是否匹配
     *
     * @param username 用户名
     * @param password 密码
     * @return true or false
     */
    @Cacheable
    public boolean checkUser(String username, String password) {
        return userMapper.selectByNameAndPwd(username, password) != null;
    }

    /**
     * 插入一条用户记录
     *
     * @param user User
     */
    @CachePut
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    /**
     * 查询用户
     *
     * @param username 用户名
     * @return Object
     */
    @Cacheable
    public User searchUser(String username) {
        return userMapper.selectByName(username);
    }
}