package com.app.server;

import com.alibaba.fastjson.JSON;
import com.app.api.model.User;
import com.app.server.dao.UserMapper;
import com.app.server.service.UserService;
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

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {

        System.out.println("=====================");
        List<User> users = userMapper.selectAll();
        System.out.println(JSON.toJSONString(users));
        System.out.println("=====================");
    }

    @Test
    public void testSearchDB() {
        System.out.println("=========================");
//        User users = userMapper.selectByNameAndPwd("diva","123");
//        System.out.println(JSON.toJSONString(users));

        boolean diva = userService.checkUser("diva", "123");
        System.out.println(diva);
        System.out.println("=========================");
    }

    @Test
    public void testEhCache() {
        System.out.println("=========================");
        System.out.println("=========================");
    }
}