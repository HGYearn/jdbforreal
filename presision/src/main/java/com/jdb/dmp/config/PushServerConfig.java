package com.jdb.dmp.config;

import com.jdb.dmp.domain.push.PushServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by qimwang on 10/17/16.
 */
@Configuration
public class PushServerConfig {
    @Value("${push.server.url}")
    String pushUrl;

    @Bean
    public PushServer pushServer() {
        PushServer serverConfiguration = new PushServer();
        serverConfiguration.setPushUrl(pushUrl);
        return serverConfiguration;
    }
}
