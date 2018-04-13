package com.app.core.util;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT校验工具类
 * <ol>
 * <li>iss: jwt签发者</li>
 * <li>sub: jwt所面向的用户</li>
 * <li>aud: 接收jwt的一方</li>
 * <li>exp: jwt的过期时间，这个过期时间必须要大于签发时间</li>
 * <li>nbf: 定义在什么时间之前，该jwt都是不可用的</li>
 * <li>iat: jwt的签发时间</li>
 * <li>jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击</li>
 * </ol>
 */
@Log4j2
public class JwtUtil {
    /**
     * JWT 加解密类型
     */
    private static final SignatureAlgorithm DEFAULT_ALG = SignatureAlgorithm.HS256;
    /**
     * JWT 生成密钥使用的密码
     */
    private static final String DEFAULT_RULE = "wjtree.xin";
    /**
     * JWT 生成密钥使用的密码
     */
    private static final SecretKey DEFAULT_KEY = generateKey(DEFAULT_ALG, DEFAULT_RULE);
    /**
     * JWT 有效时间，单位：秒，默认10分钟有效期
     */
    private static final Integer DEFAULT_DURATION = 10 * 60;
    /**
     * JWT 添加至HTTP HEAD中的前缀
     */
    public static final String PREFIX = "Bearer ";
    /**
     * JWT payload 自定义参数，表示用户授权
     */
    public static final String CLAIM_AUTH = "auth";

    /**
     * 使用JWT默认方式，生成加解密密钥
     *
     * @param alg 加解密类型
     * @return
     */
    public static SecretKey generateKey(SignatureAlgorithm alg) {
        return MacProvider.generateKey(alg);
    }

    /**
     * 使用指定密钥生成规则，生成JWT加解密密钥
     *
     * @param alg  加解密类型
     * @param rule 密钥生成规则
     * @return
     */
    public static SecretKey generateKey(SignatureAlgorithm alg, String rule) {
        // 将密钥生成键转换为字节数组
        byte[] bytes = Base64.decodeBase64(rule);
        // 根据指定的加密方式，生成密钥
        return new SecretKeySpec(bytes, alg.getJcaName());
    }

    /**
     * 构建JWT
     *
     * @param alg      jwt 加密算法
     * @param key      jwt 加密密钥
     * @param sub      jwt 面向的用户
     * @param aud      jwt 接收方
     * @param jti      jwt 唯一身份标识
     * @param iss      jwt 签发者
     * @param nbf      jwt 生效日期时间
     * @param duration jwt 有效时间，单位：秒
     * @param claims   jwt 自定义参数键值对，如用户权限等
     * @return JWT字符串
     */
    public static String buildJWT(SignatureAlgorithm alg, Key key, String sub, String aud, String jti, String iss, Date nbf, Integer duration, Map<String, Object> claims) {
        // jwt的签发时间
        DateTime iat = DateTime.now();
        // jwt的过期时间，这个过期时间必须要大于签发时间
        DateTime exp = null;
        if (duration != null)
            exp = (nbf == null ? iat.plusSeconds(duration) : new DateTime(nbf).plusSeconds(duration));

        // 获取JWT字符串
        String compact = Jwts.builder()
                .signWith(alg, key)
                .setSubject(sub)
                .setAudience(aud)
                .setId(jti)
                .setIssuer(iss)
                .setNotBefore(nbf)
                .setIssuedAt(iat.toDate())
                .setExpiration(exp != null ? exp.toDate() : null)
                .addClaims(claims)
                .compact();

        // 在JWT字符串前添加"Bearer "字符串，用于加入"Authorization"请求头
        compact = PREFIX.concat(compact);

        if (log.isDebugEnabled())
            log.debug("JSON Web Tokens 构建成功：{}", compact);

        return compact;
    }

    /**
     * 构建JWT ，使用默认 KEY
     *
     * @param sub      jwt 面向的用户
     * @param aud      jwt 接收方
     * @param jti      jwt 唯一身份标识
     * @param iss      jwt 签发者
     * @param nbf      jwt 生效日期时间
     * @param duration jwt 有效时间，单位：秒
     * @param claims   jwt 自定义参数键值对，如用户权限等
     * @return JWT字符串
     */
    public static String buildJWT(String sub, String aud, String jti, String iss, Date nbf, Integer duration, Map<String, Object> claims) {
        return buildJWT(DEFAULT_ALG, DEFAULT_KEY, sub, aud, jti, iss, nbf, duration, claims);
    }

    /**
     * 构建JWT
     * <p>使用 UUID 作为 jti 唯一身份标识</p>
     * <p>JWT有效时间 600 秒，即 10 分钟</p>
     *
     * @param sub    jwt 面向的用户
     * @param claims jwt 自定义参数键值对，如用户权限等
     * @return JWT字符串
     */
    public static String buildJWT(String sub, Map<String, Object> claims) {
        return buildJWT(sub, null, UUID.randomUUID().toString(), null, null, DEFAULT_DURATION, claims);
    }

    /**
     * 构建JWT
     * <p>使用 UUID 作为 jti 唯一身份标识</p>
     * <p>JWT有效时间 600 秒，即 10 分钟</p>
     *
     * @param sub  jwt 面向的用户
     * @param auth jwt 自定义参数，用户权限
     * @return JWT字符串
     */
    public static String buildJWT(String sub, String auth) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_AUTH, auth);

        return buildJWT(sub, claims);
    }

    /**
     * 构建JWT
     * <p>使用 UUID 作为 jti 唯一身份标识</p>
     * <p>JWT有效时间 600 秒，即 10 分钟</p>
     *
     * @param sub jwt 面向的用户
     * @return JWT字符串
     */
    public static String buildJWT(String sub) {
        return buildJWT(sub, null, UUID.randomUUID().toString(), null, null, DEFAULT_DURATION, null);
    }

    /**
     * 解析JWT
     *
     * @param key       jwt 加密密钥
     * @param claimsJws jwt 内容文本
     * @return {@link Jws}
     */
    public static Jws<Claims> parseJWT(Key key, String claimsJws) {
        // 移除 JWT 前的"Bearer "字符串
        claimsJws = StringUtils.substringAfter(claimsJws, PREFIX);
        // 解析 JWT 字符串
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(claimsJws);

        if (log.isDebugEnabled())
            log.debug("JSON Web Tokens 解析成功：{}", JSON.toJSONString(jws));

        return jws;
    }

    /**
     * 解析JWT ，使用默认 KEY
     *
     * @param claimsJws jwt 内容文本
     * @return Claims
     */
    public static Claims parseJWT(String claimsJws) {
        // 获取 JWT 的 payload 部分
        return parseJWT(DEFAULT_KEY, claimsJws).getBody();
    }

    /**
     * 校验JWT ，使用默认 KEY
     *
     * @param claimsJws jwt 内容文本
     * @param sub       jwt 面向的用户
     * @return ture or false
     */
    public static Boolean checkJWT(String claimsJws, String sub) {
        return parseJWT(claimsJws).getSubject().equals(sub);
    }
}