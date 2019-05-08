package com.jdb.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.*;

/**
 * Created by qimwang on 9/26/16.
 */
public class HttpClientDemo {

    public static void main(String[] args) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
// Increase max total connection to 200
        cm.setMaxTotal(200);
// Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);
// Increase max connections for localhost:80 to 50
        HttpHost localhost = new HttpHost("100.73.22.2", 8983);
        cm.setMaxPerRoute(new HttpRoute(localhost), 50);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        String url = "http://100.73.22.2:8983/solr/hbaseCore/select?q=*%3A*&wt=json&indent=true";

        String pushUrl = "http://100.73.16.39:8080/notice/inner/message/batchAddMessageFast";
        try {

//            List<Object> jobDataList = Lists.newArrayList();

            Map<String, Object> jobData = Maps.newHashMap();

            Map<String, Object> messageAttrArray = Maps.newHashMap();
            messageAttrArray.put("needPush", 1);
            messageAttrArray.put("needShowSecretary", 0);

            Map<String, Object> pushInfoMap = Maps.newHashMap();
            pushInfoMap.put("priority", 2);
            pushInfoMap.put("type", 1);
            pushInfoMap.put("content", "%E5%86%85%E5%AE%B9");
            pushInfoMap.put("description", "%E5%86%85%E5%AE%B9");
            pushInfoMap.put("expireTime", System.currentTimeMillis() / 1000 + 200);

            Map<String, String> extData = Maps.newHashMap();
            extData.put("subtype", "321");
            extData.put("url", "http://link.url");

            pushInfoMap.put("extData", extData);
            pushInfoMap.put("title", "title");

            jobData.put("messageAttr", messageAttrArray);
            jobData.put("pushInfo", pushInfoMap);
            jobData.put("innerSource", "YUNYING");
            jobData.put("targetApp", "1");
            jobData.put("timestamp", System.currentTimeMillis() / 1000);
            jobData.put("userId", "");

//            jobDataList.add(jobData);

            System.out.println(JSON.toJSONString(jobData));
//            System.exit(0);

            String jsonString = JSON.toJSONString(jobData);

//            jsonString = "{\"messageAttr\":{\"needPush\":1,\"needShowSecretary\":0},\"pushInfo\":{\"priority\":2,\"type\":100,\"content\":\"%E5%86%85%E5%AE%B9\",\"description\":\"%E5%86%85%E5%AE%B9\",\"expireTime\":\"1476259184\",\"extData\":{\"subtype\":\"321\",\"url\":\"http://link.url\"},\"title\":\"title\"},\"innerSource\":\"YUNYING\", \"targetApp\":\"1\",\"timestamp\":1476257865054,\"userId\":\"\"}";
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("message", jsonString));
            formparams.add(new BasicNameValuePair("userIds", "556973230582147048"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            HttpPost httppost = new HttpPost(pushUrl);
            httppost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httppost);


//            CloseableHttpResponse response = httpClient.execute(new HttpGet(url));


            System.out.println(EntityUtils.toString(response.getEntity()));
//            String body = EntityUtils.toString(response.getEntity());
//            JSONObject result = (JSONObject)JSON.parse(body);
//            JSONObject response1 = (JSONObject)result.get("response");
//            JSONArray docs = (JSONArray)response1.get("docs");
//            Object[] documents = docs.toArray();
//
//            Iterator<Object> iterator = docs.iterator();
//            while (iterator.hasNext()) {
//                JSONObject doc = (JSONObject)iterator.next();
//                if (doc.containsKey("userid")) {
//                    System.out.println(doc.getString("userid"));
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
