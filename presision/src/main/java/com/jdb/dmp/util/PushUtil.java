package com.jdb.dmp.util;

import com.google.common.base.Splitter;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by qimwang on 11/8/16.
 */
public class PushUtil {
    //发送push
    private static Logger pushLogger = LoggerFactory.getLogger(PushUtil.class);
    public static String sendPush(String message, String users, String pushUrl, Logger logger) {
        List<String> userList = Splitter.on(',').splitToList(users);
        for (String uuid : userList) {
            pushLogger.info(uuid);
        }
        CloseableHttpClient httpClient = HttpClients.custom()
                .build();
        logger.info("before push execution");
        logger.info("push_message: " + message);
        logger.info("push_users: " + users);
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