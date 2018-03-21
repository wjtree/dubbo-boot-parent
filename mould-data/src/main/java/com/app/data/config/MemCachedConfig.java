package com.app.data.config;

import com.whalin.MemCached.SockIOPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemCachedConfig {
    @Bean
    public SockIOPool sockIOPool() {
        // server list and weights
        String[] servers = {"111.230.185.199:12101"};
        Integer[] weights = {3};

        // grab an instance of our connection pool
        SockIOPool pool = SockIOPool.getInstance();

        // set the servers and the weights
        pool.setServers(servers);
        pool.setWeights(weights);

        // set some basic pool settings
        // 5 initial, 5 min, and 250 max conns and set the max idle time for a conn to 6 hours
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaxIdle(1000 * 60 * 60 * 6);

        // set the sleep for the maint thread
        // it will wake up every x seconds and maintain the pool size
        pool.setMaintSleep(30);

        // set some TCP settings
        // disable nagle
        // set the read timeout to 3 secs and don't set a connect timeout
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);

        // initialize the connection pool
        pool.initialize();

        return pool;
    }
}