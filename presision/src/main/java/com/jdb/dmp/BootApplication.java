package com.jdb.dmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by qimwang on 6/15/16.
 *
 * 09/20/2016  qimwang
 */

@SpringBootApplication
public class BootApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:elastic-job.xml");
        ConfigurableApplicationContext ctx = SpringApplication.run(BootApplication.class, args);
        ctx.getEnvironment().setActiveProfiles();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<BootApplication> applicationClass = BootApplication.class;
}