package com.jdb.dmp.task.base;

import com.google.common.base.Joiner;
import com.jdb.dmp.task.push.MinorActiveUserPushTask;
import com.jdb.dmp.util.PartitionUtil;
import net.logstash.logback.marker.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by qimwang on 10/31/16.
 */
public class Test {
    public static void main(String[] args) {
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyMMdd");
        Calendar cal = Calendar.getInstance();
        String fileName = sdfd.format(cal.getTime());
        System.out.println(fileName);
        int[] count= new int[] { 1024 };
        int[] length= new int[] { 1 };
        PartitionUtil pu= new PartitionUtil(count, length);
        long memberId= Long.valueOf("482636696450519041");

        System.out.println(String.format("%04d", pu.partition(memberId)));

        System.out.println((memberId >> 22) % 1024);

        System.out.println(String.format("est %.1f%%", 12.5));

        List<String> userList = new ArrayList<>(2);
        userList.add("57893434");
        userList.add("9877448993");

        String uList = "'" + Joiner.on("', '").join(userList) + "'";
        System.out.println(uList);

        Logger logger = LoggerFactory.getLogger(MinorActiveUserPushTask.class);
//        Map<String, String> map = new HashMap<>(10);
//        map.put("test", "特色");
//        logger.info(Markers.appendEntries(map), "test");
        logger.info(Markers.append("name1", "value1"), "log message");

        String userName = "";
        String ip = "";
        logger.info("FAILED login for user='{}' from IP={}", userName, ip);

    }
}
