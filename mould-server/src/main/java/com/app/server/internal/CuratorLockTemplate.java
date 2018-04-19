package com.app.server.internal;

import com.app.core.util.IocUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * 使用模版方法设计模式
 */
@Log4j2
public abstract class CuratorLockTemplate {
    /**
     * Curator 共享重入锁模版
     *
     * @param time 等待时间
     * @param unit 单位时间单位
     * @throws Exception Exception
     */
    public void lock(String lockName, @NotNull long time, @NotNull TimeUnit unit) throws Exception {
        // 使用单例模式获取共享重入锁对象
        InterProcessMutex lock = IocUtil.getBean(CuratorLockInit.class).getInterProcessMutex();

        // 如果获得了互斥锁，则返回true，否则返回false
        if (lock.acquire(time, unit)) {
            // 用户计算用户实际持有锁的时间
            long begin = System.currentTimeMillis();

            try {
                if (log.isInfoEnabled())
                    log.info("{} 获取锁成功，持有期限：{} {}", lockName, time, unit.name());

                // 执行业务逻辑
                doLock(lockName);
            } finally {
                // 释放锁
                lock.release();

                if (log.isInfoEnabled())
                    log.info("{} 释放锁成功，实际持有时间：{} SECONDS", lockName, (System.currentTimeMillis() - begin) / 1000);
            }
        }
    }

    /**
     * 需要添加分布式锁的业务逻辑方法
     *
     * @throws Exception Exception
     */
    protected abstract void doLock(String lockName) throws Exception;
}
