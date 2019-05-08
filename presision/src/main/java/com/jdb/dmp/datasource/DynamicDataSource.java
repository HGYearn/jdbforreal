package com.jdb.dmp.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by qimwang on 7/18/16.
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
    @Override
    public Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();
    }
}