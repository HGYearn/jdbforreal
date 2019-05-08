package com.jdb.dmp.task.base;

import com.google.common.base.Joiner;
import com.jdb.dmp.domain.push.PushConfiguration;
import com.jdb.dmp.domain.push.PushServer;
import com.jdb.dmp.util.PushUtil;
import lombok.Data;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by qimwang on 10/31/16.
 */
public @Data
class PushSenderThread extends Thread {

    PushServer pushServer;
    com.jdb.dmp.task.base.PushTask task;
    int sendLimit = 3;
    String messageForS1 = "借贷宝出本金，你拿收益; 现在就领取体验金白赚钱!";
    String messageForS2 = "不出钱赚钱真的不是梦";
    String pushTitle = "借贷宝";

    Logger logger = LoggerFactory.getLogger(PushSenderThread.class);
    public PushSenderThread(Class clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public void run() {
        //是否存在未完成的数据
        while(true) {
            System.out.println("=======before sending ======");
            Iterator<BlockingQueue<String>> blockingQueueIterator = task.strategyQueueList.iterator();
            try {
                int queueIndex = 0;
                while (blockingQueueIterator.hasNext()) {
                    BlockingQueue<String> b1 = blockingQueueIterator.next();
                    if (b1.size() > 0) {
                        List<String> userList = new ArrayList(sendLimit);
                        for(int i = 0; i < sendLimit && i < b1.size(); i++) {
                            String uuid = b1.poll(1, TimeUnit.SECONDS);
                            if (uuid != null) {
                                userList.add(uuid);
                                task.getJedisPool().getResource().
                                        set(task.getPushedSetKeyPrefix() + uuid, uuid);
                                task.getJedisPool().getResource().expire(task.getPushedSetKeyPrefix() + uuid, 86400*5);
                            }
                        }
                        PushConfiguration pushConfiguration = task.pushConfigurationList.get(queueIndex);
                        PushUtil.sendPush(pushConfiguration.toJsonString(), Joiner.on(",").join(userList), pushServer.getPushUrl(),
                                logger);
                    }
                    queueIndex++;
                }
                boolean hasLegacyData = false;
                if (task.taskStatus == 1) {
                    Thread.sleep(10000);
                }

                Iterator<BlockingQueue<String>> iter = task.strategyQueueList.iterator();
                while (iter.hasNext()) {
                    BlockingQueue<String> b1 = iter.next();
                    if (b1.size() > 0) {
                        hasLegacyData = true;
                        break;
                    }
                }

                if (hasLegacyData || task.taskStatus == 1)
                    break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //发送push
    public String sendPush(String message, String users) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .build();
        String pushUrl = pushServer.getPushUrl();
        try {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("message", message));
            formParams.add(new BasicNameValuePair("userIds", users));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            HttpPost httppost = new HttpPost(pushUrl);
            httppost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httppost);
            logger.info("push message:" + message + " users: " + users + " response:"
                    + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException ie) {

            }
        }
        return "";
    }
}
