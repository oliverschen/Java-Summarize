package com.github.oliverschen.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HbaseUtil {


    public static void main(String[] args) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum","101.33.253.51:2181");
        Connection connection = ConnectionFactory.createConnection(configuration);

        Table table = connection.getTable(TableName.valueOf("mydemo:users"));
        Get get = new Get("rowKey1".getBytes(StandardCharsets.UTF_8));
        Result result = table.get(get);
        System.out.println(result);
        Put put = new Put("123".getBytes());
        put.addColumn("base".getBytes(),"username".getBytes(),"zhangsan".getBytes());
        put.addColumn("base".getBytes(),"birthday".getBytes(),"1980-9-8".getBytes());
        put.addColumn("other".getBytes(),"likes".getBytes(),"film,music".getBytes());
        table.put(put);
        connection.close();
    }
}
