package com.github.oliverschen.util;

import com.github.oliverschen.dto.UserDto;
import com.github.oliverschen.exception.HbaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.oliverschen.constant.CommonEnum.*;

@Slf4j
@Component
public class HbaseUtils {

    @Autowired
    private Admin admin;


    /**
     * 判断表是否存在
     * @param tableName 表名
     */
    public boolean isExists(String tableName) {
        TableName name = TableName.valueOf(tableName);
        try {
            return admin.tableExists(name);
        } catch (IOException e) {
            log.error("HbaseUtils#isExists: table not found");
            throw new HbaseException(TABLE_NOT_FOUND);
        }
    }


    /**
     * 创建表
     *
     * @param tableName    表名
     * @param columnFamily 列族
     * @param keys         分区集合
     */
    public boolean createTable(String tableName, List<String> columnFamily, List<String> keys) {
        if (isExists(tableName)) {
            TableName name = TableName.valueOf(tableName);
            Set<ColumnFamilyDescriptor> columnFamilies = columnFamily.stream()
                    .map(item -> ColumnFamilyDescriptorBuilder.newBuilder(item.getBytes()).build()).
                    collect(Collectors.toSet());
            TableDescriptor descriptor = TableDescriptorBuilder
                    .newBuilder(name)
                    .setColumnFamilies(columnFamilies)
                    .build();
            try {
                admin.createTable(descriptor);
            } catch (IOException e) {
                log.error("HbaseUtils#createTable error:", e);
                throw new HbaseException(TABLE_CREATE_ERROR);
            }
        }else {
            throw new HbaseException(TABLE_NOT_FOUND);
        }
        return false;
    }


    /**
     * 单条数据插入
     *
     * @param tableName    表名
     * @param rowKey       rowKey
     * @param columnFamily 列族
     * @param column       列
     * @param value        值
     */
    public void putSingleRow(String tableName, String rowKey, String columnFamily, String column, String value) {
        try {
            Table table = admin.getConnection().getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
            table.put(put);
            table.close();
        } catch (IOException e) {
            log.error("HbaseUtils#putSingleRow table not found:", e);
            throw new HbaseException(TABLE_INSERT_SINGLE_ERROR);
        }
    }

    /**
     * 删除单条数据
     * @param tableName 表名
     * @param rowKey rowKey
     */
    public boolean deleteSingleRow(String tableName, String rowKey) {
        try {
            Table table = admin.getConnection().getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
            table.close();
            return true;
        } catch (IOException e) {
            log.error("HbaseUtils#deleteSingleRow delete error:", e);
            throw new HbaseException(TABLE_DELETE_SINGLE_ERROR);
        }
    }


    /**
     * 查询单条数据
     * @param tableName 表名
     * @param rowKey rowKey
     * @param columFamily 列族
     * @param tClass 类名
     */
    public <T> T getSingleRow(String tableName, String rowKey, String columFamily,Class<T> tClass) {
        try {
            Table table = admin.getConnection().getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addFamily(Bytes.toBytes(columFamily));
            Result result = table.get(get);
            if (Objects.nonNull(result) && !result.isEmpty()) {
                List<Cell> cells = result.listCells();
                Map<String, String> map = new HashMap<>();
                cells.forEach(cell -> map.put(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                        cell.getQualifierLength()), Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                        cell.getValueLength())));
                T t = tClass.getDeclaredConstructor().newInstance();
                BeanUtils.populate(t, map);
                return t;
            }
        } catch (IOException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            log.error("HbaseUtils#getSingleRow get error:", e);
            throw new HbaseException(TABLE_GET_SINGLE_ERROR);
        }
        return null;
    }

}
