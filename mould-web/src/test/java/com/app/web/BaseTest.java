package com.app.web;

import com.app.core.util.JwtUtil;
import org.junit.Test;

public class BaseTest {
    @Test
    public void test2() {
        System.out.println("========================================");
        String jwt = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZhIiwianRpIjoiYWQ1MGY2MWItNjZiZC00M2ViLWFiMjctYmExMDEyOGQyZjNmIiwiaWF0IjoxNTIzNjAyNTI4LCJleHAiOjE1MjM2MDMxMjgsImF1dGgiOiJVU0VSLEFETUlOIn0.DzToniGCDJinQJeDzqJ1jOlf2EUXI-0dxndzqzAYrxE";
        JwtUtil.parseJWT(jwt);

        System.out.println("========================================");
    }
}
