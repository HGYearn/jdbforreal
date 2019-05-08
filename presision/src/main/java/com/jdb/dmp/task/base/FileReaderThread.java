package com.jdb.dmp.task.base;

import com.google.common.base.Splitter;
import com.jdb.dmp.domain.JdbUser;
import lombok.Data;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * Created by qimwang on 10/31/16.
 */
public @Data
class FileReaderThread extends Thread {
    PushTask task;
    private String dir;
    private String filePath;
    private String historyPushDir;

    public FileReaderThread(String dir, String filePath) {
        this.dir = dir;
        this.filePath = filePath;
    }

    public void run() {
        try {
            BufferedReader bufReader = Files.newBufferedReader(FileSystems.getDefault().getPath(dir, filePath));
            String line;
            String historyPushFile;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Set<String> pushHistorySet = new HashSet<>(1000);
            int days = 1;
            Calendar cal = Calendar.getInstance();
            while (days <= 5) {
                cal.add(Calendar.DAY_OF_YEAR, -1);
                historyPushFile = formatter.format(cal.getTime());
                if (Files.exists(FileSystems.getDefault().getPath(historyPushDir, historyPushFile))) {
                    BufferedReader historyPushReader = Files.newBufferedReader(FileSystems.getDefault().getPath(historyPushDir, historyPushFile));
                    while ((line = historyPushReader.readLine()) != null) {
                        pushHistorySet.add(line.trim());
                    }
                }
                days++;
            }

            int count = 0;
            while ( (line = bufReader.readLine()) != null) {
                String tline = line.trim();
                List<String> items = Splitter.on(',').splitToList(tline);
                String uuid;
                String phone;
                if (items.size() == 2) {
                    uuid = items.get(0);
                    phone = items.get(1);
                } else {
                    continue;
                }
                //如果最近发送过Push, 不再添加到队列中
                if (pushHistorySet.contains(uuid) || pushHistorySet.contains(phone)) {
                    continue;
                }

                System.out.println("放入策略队列: " + uuid);
                task.layerBlockingQueue.put(new JdbUser(uuid, phone));
                count++;
                if (count % 10000 == 0) {
                    Thread.sleep(1000);
                }
            }
            task.taskStatus = 1;
            bufReader.close();
        } catch (Exception e) {
            task.taskStatus = 1;
        } finally {
            task.taskStatus = 1;
        }
    }


    public static void main(String[] args) {
        try {
            String line;
            String historyPushFile;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Set<String> pushHistorySet = new HashSet<>(1000);
            int days = 1;
            Calendar cal = Calendar.getInstance();
            while (days <= 5) {
                cal.add(Calendar.DAY_OF_YEAR, -1);
                historyPushFile = formatter.format(cal.getTime());
                System.out.println(historyPushFile);
                BufferedReader historyPushReader = Files.newBufferedReader(FileSystems.getDefault().getPath("", historyPushFile));
                while ((line = historyPushReader.readLine()) != null) {
                    pushHistorySet.add(line.trim());
                }
                days++;
            }

            BufferedReader bufReader = Files.newBufferedReader(FileSystems.getDefault().getPath("", "20161116"));

            while ( (line = bufReader.readLine()) != null) {
                String tline = line.trim();
                List<String> items = Splitter.on(',').splitToList(tline);
                String uuid = items.get(0);
                String phone = items.get(1);
                //如果最近发送过Push, 不再添加到队列中
                if (pushHistorySet.contains(uuid) || pushHistorySet.contains(phone)) {
                    continue;
                }
                System.out.println("放入策略队列: " + uuid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
