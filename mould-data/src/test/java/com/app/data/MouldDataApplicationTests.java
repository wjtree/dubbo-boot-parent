package com.app.data;

import com.alibaba.fastjson.JSON;
import com.app.data.dao.UserMapper;
import com.app.data.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MouldDataApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        System.out.println("=====================");
        List<User> users = userMapper.selectAll();
        System.out.println(JSON.toJSONString(users));
        System.out.println("=====================");
    }
}
