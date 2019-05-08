package com.jdb.dmp.solr;

import org.apache.solr.client.solrj.ResponseParser;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by qimwang on 10/14/16.
 */
@Service("solrService")
public class SolrService {
    @Autowired
    CloudSolrClient cloudSolrClient;

    public void connect() {
        cloudSolrClient.connect();
    }

    public void close() throws IOException {
        cloudSolrClient.close();
    }

    public QueryResponse query(SolrQuery query) throws IOException, SolrServerException {
        QueryResponse queryResponse = cloudSolrClient.query(query);
        return queryResponse;
    }

    public void setDefaultCollection(String defaultCollection) {
        cloudSolrClient.setDefaultCollection(defaultCollection);
    }

    public void setResponseParser(ResponseParser processor) {
        cloudSolrClient.setParser(processor);
    }
}
