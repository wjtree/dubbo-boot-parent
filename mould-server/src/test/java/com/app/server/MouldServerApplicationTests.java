package com.app.server;

import com.alibaba.fastjson.JSON;
import com.app.server.dao.UserMapper;
import com.app.server.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MouldServerApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {

        System.out.println("=====================");
        List<User> users = userMapper.selectAll();
        System.out.println(JSON.toJSONString(users));
        System.out.println("=====================");
    }
}