package com.app.server;

import com.alibaba.fastjson.JSON;
import com.app.api.model.User;
import com.app.server.dao.UserMapper;
import com.app.server.internal.CuratorLockTemplate;
import com.app.server.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

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
        User user = new User();
        user.setId("2");
        user.setName("tom");
        user.setAge(33);
        user.setPhone("888888");
        user.setNickname("汤姆");
        user.setPassword("999");


        System.out.println(JSON.toJSONString(user));
//        userService.addUser(user);

        System.out.println("=========================");
    }

    @Test
    public void testEhCache2() throws Exception {
        System.out.println("=========================");


        new CuratorLockTemplate() {
            @Override
            protected void doLock(String clientName) throws Exception {
                System.out.println("===============================================");
                System.out.println("测试方法...");
                System.out.println("===============================================");
            }
        }.lock("test01", 6, TimeUnit.SECONDS);

        System.out.println("=========================");
    }
}