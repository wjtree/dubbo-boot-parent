package com.app.server;

import com.alibaba.fastjson.JSON;
import com.app.api.model.User;
import com.app.core.util.EhCacheUtil;
import com.app.core.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;

public class BaseTest {
    @Test
    public void testEhcache() {
        System.out.println("================");
        String demo1 = EhCacheUtil.expiryCache.get("demo1");
        System.out.println(demo1);

        EhCacheUtil.expiryCache.put("demo1", "11111111111111");

        demo1 = EhCacheUtil.expiryCache.get("demo1");
        System.out.println(demo1);

        EhCacheUtil.expiryCache.clear();

        demo1 = EhCacheUtil.expiryCache.get("demo1");
        System.out.println(demo1);

        System.out.println("================");
    }

    @Test
    public void testEhcache2() {
        User user = new User();
        user.setId("2");
        user.setName("tom");
        user.setAge(33);
        user.setPhone("888888");
        user.setNickname("汤姆");
        user.setPassword("999");


        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void test3() {
//        String jwt = JwtUtil.buildJWT("wjtree");
        String jwt = JwtUtil.buildJWT("wjtree", "ROLE_ADMIN,AUTH_WRITE");

        Claims claims = JwtUtil.parseJWT(jwt);
    }
}
