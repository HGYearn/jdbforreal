package com.jdb.dmp.util;

import com.google.common.base.Splitter;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;

/**
 * Created by qimwang on 11/15/16.
 */
public class SmsUtil {
    //发送sms
    private static Logger smsLogger = LoggerFactory.getLogger(PushUtil.class);
    public static String send(String url, String username, String password,
                                  String templateId, String mobiles, Map<String, String> udfParams, Logger logger) {
        List<String> userList = Splitter.on(',').splitToList(mobiles);
        for (String uuid : userList) {
            smsLogger.info(uuid.trim());
        }
        CloseableHttpClient httpClient = HttpClients.custom()
                .build();

        try {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("username", username));
            formParams.add(new BasicNameValuePair("password", password));
            formParams.add(new BasicNameValuePair("mobiles", mobiles));
            formParams.add(new BasicNameValuePair("templateId", templateId));
            formParams.add(new BasicNameValuePair("traceId", UUID.randomUUID().toString()));
            formParams.add(new BasicNameValuePair("sendTime", ""));
            formParams.add(new BasicNameValuePair("msgType", "1"));

            Set<String> keySet = udfParams.keySet();
            for(String key: keySet) {
                String value = udfParams.get(key);
                formParams.add(new BasicNameValuePair(key, value));
            }

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httppost);
            logger.info(EntityUtils.toString(response.getEntity()));
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

    public static void main(String[] args) {
        String url = "http://100.73.17.36:8080/sms/smsController/sendSms";
        String username = "m2xv2F";
        String password = "GW6cnf78";
        String templateId = "1704";
        String mobiles = "15810536308";
        Map<String, String> udfParams = new HashMap<>(2);
        udfParams.put("username", "王其敏");
        udfParams.put("amount", "10");

        Logger logger = LoggerFactory.getLogger(SmsUtil.class);
        SmsUtil.send(url, username, password, templateId, mobiles, udfParams, logger);
    }
}
