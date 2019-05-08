package com.jdb.dmp.config;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by qimwang on 10/14/16.
 */
@Configuration
public class SolrConfig {
    @Value("${zk.server}")
    String zkServer;

    @Value("${solr.collection}")
    String defaultCollection;

    @Bean(name="cloudSolrClient")
    public CloudSolrClient cloudSolrClient() throws IOException
    {
        String zkHostString = zkServer+"/solr";
        CloudSolrClient.Builder builder = new CloudSolrClient.Builder();
        builder.withZkHost(zkHostString);
        CloudSolrClient cloudSolrClient = builder.build();
        cloudSolrClient.setDefaultCollection(defaultCollection);
        return cloudSolrClient;
    }
}
