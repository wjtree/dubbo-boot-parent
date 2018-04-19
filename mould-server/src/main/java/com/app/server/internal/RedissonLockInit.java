package com.app.server.internal;

import com.app.core.code.Symbol;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化 Redisson 客户端及获取分布式锁
 */
@Log4j2
@Component
public class RedissonLockInit {
    // 服务器IP
    @Value("${spring.redis.host}")
    private String host;

    // 服务器端口
    @Value("${spring.redis.port}")
    private String port;

    // 密码
    @Value("${spring.redis.password}")
    private String password;

    // 数据库编号
    @Value("${spring.redis.database}")
    private int database;

    // Redisson 客户端
    private RedissonClient client = null;
    // Redis 节点地址前缀
    private static final String PREFIX = "redis://";
    // 可重入锁缓存
    private static final Map<String, RLock> R_LOCK_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        // 拼接节点地址
        String address = StringUtils.join(PREFIX, host, Symbol.COLON.getSymbol(), port);
        // 配置节点模式
        Config config = getSingleServerConfig(address, password, database);
        // 创建 Redisson 客户端实例
        client = Redisson.create(config);

        if (log.isInfoEnabled())
            log.info("Redisson 客户端启动完成");
    }

    @PreDestroy
    void destroy() {
        client.shutdown();

        if (log.isInfoEnabled())
            log.info("Redisson 客户端关闭完成");
    }

    /**
     * 单Redis节点模式
     *
     * @param address  节点地址，格式 redis://host:port
     * @param password 密码
     * @param database 数据库编号
     * @return {@link Config}
     */
    private Config getSingleServerConfig(String address, String password, int database) {
        Config config = new Config();
        config.useSingleServer().setAddress(address).setPassword(password).setDatabase(database);
        return config;
    }

    /**
     * 获取可重入锁
     *
     * @param lockPath 锁路径
     * @return InterProcessMutex 共享重入锁
     */
    synchronized RLock getRLock(String lockPath) {
        // 从缓存中获取 RLock 实例
        RLock rLock = R_LOCK_MAP.get(lockPath);

        if (rLock == null) {
            // 如果缓存中不存在，则初始化实例
            rLock = client.getLock(lockPath);
            // 存入缓存
            R_LOCK_MAP.put(lockPath, rLock);

            if (log.isInfoEnabled())
                log.info("初始化可重入锁成功，锁路径：{}", lockPath);
        }

        return rLock;
    }
}
