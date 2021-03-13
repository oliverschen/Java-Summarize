package com.github.oliverschen.bootbean.jdbc;

import java.sql.*;

import static com.github.oliverschen.bootbean.jdbc.SqlConst.*;

/**
 * @author ck
 * 1）使用 JDBC 原声接口，实现数据库的增删改查操作。
 */
public class JDBCConnect extends BaseConnect {

    public static void main(String[] args) {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = getStatement();
            findAll(st);
            System.out.println("##操作之后数据##");
            add(st);
            deleteById(st, 1);
            updateNameById(st, "ck", 2);
            rs = findAll(st);

        } catch ( SQLException e) {
            e.printStackTrace();
        }finally {
            close(st,rs, null);
        }

    }

    /**
     * 查询所有
     */
    private static ResultSet findAll(Statement st) throws SQLException {
        ResultSet rs = st.executeQuery(SQL_FIND_USER);
        printAll(rs);
        return rs;
    }

    /**
     * 更新
     */
    private static void updateNameById(Statement st, String name, int id) throws SQLException {
        st.executeUpdate(String.format(SQL_UPDATE_USER, name, id));
        System.out.println("更新完成,id:" + id);
    }

    /**
     * 删除
     */
    private static void deleteById(Statement st, int id) throws SQLException {
        st.execute(SQL_DELETE_USER + id);
        System.out.println("删除完成,id:" + id);
    }

    /**
     * 新增
     */
    private static void add(Statement st) throws SQLException {
        st.execute(SQL_INSERT_USER);
        System.out.println("新增完成");
    }
}
