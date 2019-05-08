package com.jdb.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by qimwang on 9/30/16.
 */
public class JedisDemo {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.connect();

        jedis.select(8);

        jedis.set("tset", "dd");

        System.out.println(jedis.get("tset"));

        jedis.close();

        jedis.select(8);
        jedis.flushDB();

        long start = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();

        Map<String,String> data = new HashMap<String,String>(1000);
        for(int i = 0; i< 1000; i++) {
            data.put("key_"+i, "value_" + i);
            pipeline.hmset("k_" + i, data);
        }


        pipeline.sync();
//        pipeline.exec();
        long end = System.currentTimeMillis();

        System.out.println(end - start);

        System.out.println(jedis.dbSize());


        Set<String> keys = jedis.keys("k_*");
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key);
        }
    }
}
