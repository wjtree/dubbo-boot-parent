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
     * @param lockPath 锁路径
     * @param time     time 等待时间，单位：秒
     * @throws Exception Exception
     */
    public void lock(@NotNull String lockPath, @NotNull long time) throws Exception {
        // 使用单例模式获取共享重入锁对象
        InterProcessMutex lock = IocUtil.getBean(CuratorLockInit.class).getInterProcessMutex(lockPath);

        // 如果获得了互斥锁，则返回true，否则返回false
        if (lock.acquire(time, TimeUnit.SECONDS)) {
            // 用户计算用户实际持有锁的时间
            long begin = System.currentTimeMillis();

            try {
                if (log.isInfoEnabled())
                    log.info("获取锁 {} 成功，持有期限：{} SECONDS", lockPath, time);

                // 执行业务逻辑
                doLock(lockPath);
            } finally {
                // 释放锁
                lock.release();

                if (log.isInfoEnabled())
                    log.info("释放锁 {} 成功，实际持有时间：{} SECONDS", lockPath, (System.currentTimeMillis() - begin) / 1000);
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
