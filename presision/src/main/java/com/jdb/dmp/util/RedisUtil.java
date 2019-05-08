package com.jdb.dmp.util;

/**
 * Created by qimwang on 11/1/16.
 */
public class RedisUtil {
    public static int shardingKey(String uuid) {
        int[] count= new int[] { 1024 };
        int[] length= new int[] { 1 };
        PartitionUtil pu= new PartitionUtil(count, length);
        long memberId= Long.valueOf(uuid);
        return pu.partition(memberId);
    }
}
