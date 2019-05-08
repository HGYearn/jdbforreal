package com.jdb.util;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by qimwang on 9/29/16.
 */
public class IntegerDemo {



    public static void main(String[] args) {
        int name = 12;

        byte buf[] = "uE4dcyBe6Ah11GzZ".getBytes();



        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < buf.length; i++) {
            System.out.println(buf[i] & 0xff);
            System.out.println(Integer.toHexString(buf[i] & 0xff).toUpperCase());
            buffer.append(Integer.toHexString(buf[i] & 0xff).toUpperCase());
        }

        System.out.println(buffer.toString());


    }
}
