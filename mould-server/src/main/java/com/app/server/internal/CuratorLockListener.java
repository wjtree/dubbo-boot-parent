package com.app.server.internal;

import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * 如果报告 SUSPENDED 状态，则不能确定是否仍然保持锁定状态，除非随后收到重新连接状态。
 * <br/>如果报告失去状态，则确定不再持有该锁。
 */
@Log4j2
public class CuratorLockListener implements ConnectionStateListener {
    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        if (log.isInfoEnabled())
            log.info("Curator 状态监听器启动成功，当前客户端状态：{}", newState);

        // SUSPENDED 表示连接丢失，领导者、锁等应该暂停，直到连接重新建立。
        // LOST 当 Curator 认为 ZooKeeper 会话已过期时，它将设置LOST状态。
        if (ConnectionState.SUSPENDED.equals(newState) || ConnectionState.LOST.equals(newState)) {
            // 发生 SUSPENDED 或 LOST 状态变更时，分布式锁无法正常使用
            log.warn("Curator 状态异常，请检查网络连接并暂时停用分布式锁");
        }
    }
}
