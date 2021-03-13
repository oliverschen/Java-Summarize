package com.github.oliverschen.bootbean.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.github.oliverschen.bootbean.jdbc.SqlConst.PS_FIND;

/**
 * @author ck
 * 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 */
public class TxConnect extends BaseConnect{

    public static void main(String[] args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = getPrepSt(PS_FIND);

            rs = find(ps);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            close(null, rs, ps);
        }
    }

    private static ResultSet find(PreparedStatement ps) throws SQLException {
        ps.setString(1, "ck");
        ResultSet rs = ps.executeQuery();
        printAll(rs);
        return rs;
    }
}
