package com.jdb.dmp.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by qimwang on 8/8/16.
 */
@Service("hBaseService")
public class HBaseService {
    @Autowired
    private Configuration hBaseConfiguration;

    public Connection getConnection() throws IOException {
        return ConnectionFactory.createConnection(hBaseConfiguration);
    }
}
