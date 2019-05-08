package com.jdb.util;

import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CollectionParams;
import org.apache.solr.common.params.CursorMarkParams;

/**
 * Created by qimwang on 9/29/16.
 */
public class SolrDemo {

    public static void main(String[] args) {

        String zkHostString = "yz-100-73-42-4:2181,yz-100-73-42-6:2181/solr";
        CloudSolrClient.Builder builder = new CloudSolrClient.Builder();

        builder.withZkHost(zkHostString);
        CloudSolrClient cloudSolrClient = builder.build();
        cloudSolrClient.setDefaultCollection("hbase-collection1");
        cloudSolrClient.setParser(new XMLResponseParser());
        SolrQuery q = (new SolrQuery("*:*")).setRows(10).setSort(SolrQuery.SortClause.asc("id"));

//        q.set(CollectionParams.NAME, "tagcollection");

        String cursorMark = CursorMarkParams.CURSOR_MARK_START;
        boolean done = false;
        while (! done) {
            try {
                q.set(CursorMarkParams.CURSOR_MARK_PARAM, cursorMark);
                QueryResponse rsp  = cloudSolrClient.query(q);
                String nextCursorMark = rsp.getNextCursorMark();
//                doCustomProcessingOfResults(rsp);
                System.out.println(nextCursorMark);
                System.out.println(cursorMark);
                if (cursorMark.equals(nextCursorMark)) {
                    done = true;
                }
                cursorMark = nextCursorMark;
            } catch (Exception e) {

                e.printStackTrace();
                break;
            }
        }

        try {
            cloudSolrClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
