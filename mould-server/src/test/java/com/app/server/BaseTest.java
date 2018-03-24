package com.app.server;

import com.app.core.util.EhCacheUtil;
import org.junit.Test;

public class BaseTest {
    @Test
    public void testEhcache(){
        System.out.println("================");
        String demo1 = EhCacheUtil.expiryCache.get("demo1");
        System.out.println(demo1);

        EhCacheUtil.expiryCache.put("demo1","11111111111111");

        demo1 = EhCacheUtil.expiryCache.get("demo1");
        System.out.println(demo1);

        EhCacheUtil.expiryCache.clear();

        demo1 = EhCacheUtil.expiryCache.get("demo1");
        System.out.println(demo1);

        System.out.println("================");
    }
}
