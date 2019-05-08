package com.jdb.dmp.task.base;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qimwang on 10/28/16.
 */
public @Data class StrategyHandlerThread extends Thread {
    PushTask task;
    Logger logger = LoggerFactory.getLogger(StrategyHandlerThread.class);
    public void run() {
        try {
            task.initHBaseTable();
            while(task.getLayerBlockingQueue().size() > 0 || task.taskStatus != 1) {
                    task.dispatchStrategy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            task.closeHBaseTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
