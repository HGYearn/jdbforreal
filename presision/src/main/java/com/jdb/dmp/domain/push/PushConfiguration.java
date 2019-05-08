package com.jdb.dmp.domain.push;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * Created by qimwang on 10/13/16.
 */
public @Data class PushConfiguration {
    private  int needPush = 1;
    private  int needShowSecretary = 0;

    private  int priority = 2;
    private  int type = 100;
    private  String content = "";
    private  String description = "";
    private  int expireTime = 0;


    private  int subtype = 321;
    private  String url = "";
    private  String title = "";
    private  String innerSource = "YUNYING";
    private  int targetApp = 1;
    private  int timestamp = 0;
    private  String userId = "";
    private  String group = "";

    public static class Builder {
        PushConfiguration pushConfiguration;
        public Builder newInstance() {
            pushConfiguration = new PushConfiguration();
            return this;
        }

        public void check() {
            if (pushConfiguration == null) {
                pushConfiguration = new PushConfiguration();
            }
        }
        public Builder setContent(String content) {
            check();
            pushConfiguration.content = content;
            return this;
        }
        public Builder setType(int type) {
            check();
            pushConfiguration.setType(type);
            return this;
        }

        public Builder setUrl(String url) {
            check();
            pushConfiguration.setUrl(url);
            return this;
        }

        public Builder setDescription(String description) {
            check();
            pushConfiguration.setDescription(description);
            return this;
        }

        public Builder setTitle(String title) {
            check();
            pushConfiguration.setTitle(title);
            return this;
        }

        public Builder setSubtype(int subtype) {
            check();
            pushConfiguration.setSubtype(subtype);
            return this;
        }

        public Builder setGroup(String group) {
            check();
            pushConfiguration.setGroup(group);
            return this;
        }

        public PushConfiguration build() {
            return pushConfiguration;
        }
    }

    public final String toJsonString() {
        Map<String, Object> jobData = Maps.newHashMap();
        Map<String, Object> messageAttrArray = Maps.newHashMap();
        messageAttrArray.put("needPush", needPush);
        messageAttrArray.put("needShowSecretary", needShowSecretary);

        Map<String, Object> pushInfoMap = Maps.newHashMap();
        pushInfoMap.put("priority", priority);
        pushInfoMap.put("type", type);
        pushInfoMap.put("content", content);
        pushInfoMap.put("description", description);
        pushInfoMap.put("expireTime", System.currentTimeMillis() / 1000 + 3600);

        Map<String, String> extData = Maps.newHashMap();
        extData.put("subtype", String.valueOf(subtype));

        if (subtype != 321) {
            extData.put("count", String.valueOf(1));
        } else {
            extData.put("url", url);
        }

        pushInfoMap.put("extData", extData);
        pushInfoMap.put("title", title);

        jobData.put("messageAttr", messageAttrArray);
        jobData.put("pushInfo", pushInfoMap);
        jobData.put("innerSource", innerSource);
        jobData.put("targetApp", targetApp);
        jobData.put("group", group);
        jobData.put("timestamp", System.currentTimeMillis() / 1000);
        jobData.put("userId", userId);
        return JSON.toJSONString(jobData);
    }
}
