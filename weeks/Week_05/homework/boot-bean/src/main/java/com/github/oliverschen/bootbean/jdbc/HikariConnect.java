package com.github.oliverschen.bootbean.jdbc;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

import static com.github.oliverschen.bootbean.jdbc.SqlConst.PS_FIND;

/**
 * @author ck
 * 3) 配置 Hikari 连接池，改进上述操作。
 */
public class HikariConnect extends BaseConnect {

    public static void main(String[] args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Connection conn = getHikariConn();
            ps = conn.prepareStatement(PS_FIND);
            ps.setString(1,"ck");
            rs = ps.executeQuery();
            printAll(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            close(null,rs,ps);
        }

    }

}
