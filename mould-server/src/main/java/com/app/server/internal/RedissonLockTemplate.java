package com.app.server.internal;

import com.app.core.util.IocUtil;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * 使用模版方法设计模式
 */
@Log4j2
public abstract class RedissonLockTemplate {
    /**
     * Redisson 可重入锁模版
     *
     * @param lockPath 锁路径
     * @param waitTime 获取锁的最长等待时间，单位：秒
     * @throws Exception Exception
     */
    public void lock(@NotNull String lockPath, @NotNull long waitTime) throws Exception {
        // 使用单例模式获取可重入锁对象
        RLock lock = IocUtil.getBean(RedissonLockInit.class).getRLock(lockPath);

        // 如果获取锁定，则为true；如果在获取锁定之前等待时间已过，则为false
        if (lock.tryLock(waitTime, TimeUnit.SECONDS)) {
            // 用户计算用户实际持有锁的时间
            long begin = System.currentTimeMillis();

            try {
                if (log.isInfoEnabled())
                    log.info("获取锁 {} 成功", lockPath);

                // 执行业务逻辑
                doLock(lockPath);
            } finally {
                // 释放锁
                lock.unlock();

                if (log.isInfoEnabled())
                    log.info("释放锁 {} 成功，持有时间：{} SECONDS", lockPath, (System.currentTimeMillis() - begin) / 1000);
            }
        }
    }

    /**
     * 需要添加分布式锁的业务逻辑方法
     *
     * @param lockPath 锁路径
     * @throws Exception Exception
     */
    protected abstract void doLock(String lockPath) throws Exception;
}
