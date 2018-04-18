package com.app.server.internal;

import lombok.extern.log4j.Log4j2;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Log4j2
@Component
public class CuratorLockInit {
    /**
     * Zookeeper 服务器地址
     */
    @Value("${curator.connect.address}")
    private String connectString;
    /**
     * 重试之间等待的初始时间量，单位：毫秒
     */
    @Value("${curator.connect.sleeptime}")
    private int baseSleepTimeMs;
    /**
     * 最大重试次数
     */
    @Value("${curator.connect.retries}")
    private int maxRetries;
    /**
     * 分布式锁路径
     */
    @Value("${curator.lock.path}")
    private String lockPath;

    private CuratorFramework client = null;
    private InterProcessMutex interProcessMutex = null;

    @PostConstruct
    void init() {
        // 设置 Curator 连接 ZK 服务器的重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        // 使用默认会话超时和默认连接超时创建新客户端
        client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        // 启动客户端
        client.start();

        // 创建共享重入锁
        if (CuratorFrameworkState.STARTED.equals(client.getState()))
            interProcessMutex = new InterProcessMutex(client, lockPath);

        if (log.isInfoEnabled())
            log.info("Curator 客户端初始化完成，当前状态：{}，InterProcessMutex是否创建成功：{}", client.getState(), interProcessMutex != null);
    }

    @PreDestroy
    void destroy() {
        CloseableUtils.closeQuietly(client);

        if (log.isInfoEnabled())
            log.info("Curator 客户端关闭完成，当前状态：{}", client.getState());
    }
}
