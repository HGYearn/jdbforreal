package com.jdb.dmp.config;

import com.dangdang.ddframe.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by qimwang on 9/19/16.
 */
@Configuration
@ImportResource(locations={"classpath:${spring.profiles.active}/elastic-job.xml"})
public class JobConfig {
}
