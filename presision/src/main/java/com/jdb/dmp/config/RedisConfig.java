package com.jdb.dmp.config;


import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by qimwang on 11/1/16.
 */
@Configuration
public class RedisConfig {

    @Value("${redis.server}")
    String codisHost;

    @Value("${redis.port}")
    String port;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setMaxIdle(12);
        poolConfig.setMinEvictableIdleTimeMillis(100);
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxWaitMillis(100);
        return poolConfig;
    }

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = jedisPoolConfig();
        JedisPool pool = new JedisPool(poolConfig, codisHost, NumberUtils.toInt(port, 6379));
        return pool;
    }

}
