package com.jdb.util;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qimwang on 6/6/16.
 */
public class HBase {
    public static void main(String[] args)
    {
        try {
            Logger logger = LoggerFactory.getLogger(HBase.class);
            Configuration config = HBaseConfiguration.create();
            config.addResource(new Path("hbase-site.xml"));
            config.addResource(new Path("hdfs-site.xml"));
            byte[] CF = Bytes.toBytes("data");
            Connection connection = null;
            Table table = null;

            try {
                System.setProperty("hadoop.home.dir", "/");
                connection = ConnectionFactory.createConnection(config);
                Admin admin = connection.getAdmin();
//                HTableDescriptor[] tableDescriptors = admin.listTables("indexdemo-user");

                TableName tableName = TableName.valueOf("usertag");
                table = connection.getTable(tableName);
//                HTableDescriptor table = tables[0];
//                System.out.println("table.name" + table.getNameAsString());
//                admin.addColumn(table.getTableName(), new HColumnDescriptor(Bytes.toBytes("info:address")));

//                Get get = new Get(Bytes.toBytes("row1"));
//                Result r = table.get(get);

//                System.out.println("before output");
//                byte[] value = r.getValue(Bytes.toBytes("info"), Bytes.toBytes("firstname"));
//                System.out.println(Bytes.toString(value));
//                System.out.println("after output");

                // establish the connection to the cluster.
                // describe the data we want to write.
//                 Put p = new Put(Bytes.toBytes("row3"));
//                 p.addColumn(CF, Bytes.toBytes("huodong"), Bytes.toBytes("beijing"));
//                 table.put(p);

//                table = connection.getTable(tableDescriptors[0].getTableName());


                /**
                 * 读取一行数据
                 * */
                Get get = new Get(Bytes.toBytes("6524"));
                Result rt = table.get(get);
                System.out.println(new String(rt.getValue(CF, Bytes.toBytes("user_register_time"))));

                logger.info(new String(rt.getValue(CF, Bytes.toBytes("user_register_days"))));

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                // close everything down
//                if (table != null) table.close();
//                if (connection != null) connection.close();
                connection.close();
            }


//            Scan scan = new Scan();
//
//            scan.setFilter(filterList1);
//            ResultScanner rs = table.getScanner(scan);
//            for (Result r : rs) {
//                System.out.println("获得到rowkey:" + new String(r.getRow()));
//                for (KeyValue keyValue : r.raw()) {
//                    System.out.println("列：" + new String(keyValue.getFamily())
//                            + "====值:" + new String(keyValue.getValue()));
//                }
//            }
//            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}