package com.app.server;

import com.app.server.internal.CuratorLockTemplate;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class CuratorLockTest {
    // 模拟线程的数量
    private static final int QTY = 5;
    // 持有锁的时间
    private static final int TIME = 30;
    // 锁路径
    private static final String LOCK_PATH = "/example/lock";

    @Test
    public void test() throws Exception {
        CountDownLatch latch = new CountDownLatch(QTY);
        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < QTY; i++) {
            exec.submit(new LockRunnable("user" + i, latch));
        }

        exec.shutdown();
        latch.await();
    }

    static class LockRunnable implements Runnable {
        private String lockName;
        private CountDownLatch latch;

        LockRunnable(String lockName, CountDownLatch latch) {
            this.lockName = lockName;
            this.latch = latch;
        }

        public void run() {
            try {

                new CuratorLockTemplate() {
                    @Override
                    protected void doLock(String lockPath) throws Exception {
                        log.info("{} 获得资源", lockName);
                        log.info("{} 正在处理资源...", lockName);

                        // 模拟业务处理过程
                        Thread.sleep(5 * 1000);

                        log.info("{} 资源使用完毕", lockName);

                        // 将线程计速器减1
                        latch.countDown();
                    }
                }.lock(LOCK_PATH, TIME);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}