package com.github.oliverschen.bootbean.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

import static com.github.oliverschen.bootbean.jdbc.SqlConst.*;
import static com.github.oliverschen.bootbean.jdbc.SqlConst.MYSQL_PWD;

/**
 * @author ck
 */
public class BaseConnect {

    private static Connection connection;
    private static HikariDataSource hikariDataSource;

    static {
        // 注册驱动到驱动管理
        try {
            Class.forName(MYSQL_DRIVER_CLASS);
            connection = DriverManager.getConnection(MYSQL_CONNECT_URL, MYSQL_USER, MYSQL_PWD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

    public static PreparedStatement getPrepSt(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }


    public static void close(Statement st, ResultSet rs, PreparedStatement ps) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (hikariDataSource != null) {
            hikariDataSource.close();
        }
        System.out.println("资源正常关闭");
    }

    public static void printAll(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println("id:" + rs.getInt("id"));
            System.out.println("name:" + rs.getString("name"));
            System.out.println("birth:" + rs.getString("birth"));
            System.out.println("address:" + rs.getString("address"));
        }
    }


    public static HikariDataSource getHikariDs() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(MYSQL_CONNECT_URL);
        config.setUsername(MYSQL_USER);
        config.setPassword(MYSQL_PWD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariDataSource = new HikariDataSource(config);
        return hikariDataSource;
    }

    public static Connection getHikariConn() throws SQLException {
        connection = getHikariDs().getConnection();
        return connection;
    }
}
