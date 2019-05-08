package com.jdb.dmp.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

/**
 * Created by qimwang on 11/1/16.
 */
@Service("redisService")
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    public String get(String key) {
        return jedisPool.getResource().get(key);
    }

    public void set(String key, String value) {
        jedisPool.getResource().set(key, value);
    }

    public void sadd(String key, String... members) {
        jedisPool.getResource().sadd(key, members);
    }
}
