package com.jdb.dmp.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class HBaseConfig {

    @Value("${hbase.site.config.file}")
    String hbaseSiteFile;

    @Value("${hdfs.site.config.file}")
    String hdfsSiteFile;

    @Bean(name="hBaseConfiguration")
    public Configuration hBaseConfiguration() throws IOException {
        Configuration config = org.apache.hadoop.hbase.HBaseConfiguration.create();
        config.addResource(new Path(hbaseSiteFile));
        config.addResource(new Path(hdfsSiteFile));
        return config;
    }
}