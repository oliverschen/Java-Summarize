package com.github.oliverschen;

import java.math.BigDecimal;
import java.sql.*;


/**
 * @author ck
 * 测试不同情况下 sql 写入
 */
public class InsertTest {

    static String MYSQL_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    static String MYSQL_CONNECT_URL = "jdbc:mysql://localhost:3306/geek?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=utf8";
    static String MYSQL_USER = "root";
    static String MYSQL_PWD = "";
    static Integer ONE_MILLION = 100_0000;


    public static void main(String[] args) {
        // 单条插入
//        insertByOne();
        
        // 批量插入
//        insertBatch();

        // 开启事物插入
        insertOneOnTx();
    }

    private static void insertOneOnTx() {
        long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO `geek`.`order`(`create_time`, `update_time`, `order_time`, `send_time`, " +
                "`goods_id`, `user_id`, `phone`, `country_id`, `city_id`, `address`, `amount`, `discount`," +
                " `coupon_id`, `express_aount`, `express_id`, `order_status`, `express_status`, `remark`, `deleted`) " +
                "VALUES (?, ?,?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            Date date = new Date(System.currentTimeMillis());
            ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (int i = 0; i < ONE_MILLION; i++) {
                ps.setDate(1, date);
                ps.setDate(2,date);
                ps.setDate(3,date);
                ps.setDate(4,date);
                ps.setLong(5,i);
                ps.setLong(6,i);
                ps.setString(7,"17889972228");
                ps.setInt(8,i);
                ps.setInt(9,i);
                ps.setString(10,"英国");
                ps.setBigDecimal(11, BigDecimal.valueOf(22));
                ps.setBigDecimal(12, BigDecimal.ZERO);
                ps.setLong(13, 1);
                ps.setBigDecimal(14, BigDecimal.valueOf(12));
                ps.setBigDecimal(15, BigDecimal.valueOf(i));
                ps.setInt(16, 1);
                ps.setInt(17, 1);
                ps.setString(18, "一般");
                ps.setInt(19, 0);
                ps.execute();
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(ps,connection);
        }
        System.out.println("单条插入时间：" + (System.currentTimeMillis() - startTime));


    }

    private static void insertBatch() {

        long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO `geek`.`order`(`create_time`, `update_time`, `order_time`, `send_time`, " +
                "`goods_id`, `user_id`, `phone`, `country_id`, `city_id`, `address`, `amount`, `discount`," +
                " `coupon_id`, `express_aount`, `express_id`, `order_status`, `express_status`, `remark`, `deleted`) " +
                "VALUES (?, ?,?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            Date date = new Date(System.currentTimeMillis());
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < ONE_MILLION; i++) {
                ps.setDate(1, date);
                ps.setDate(2,date);
                ps.setDate(3,date);
                ps.setDate(4,date);
                ps.setLong(5,i);
                ps.setLong(6,i);
                ps.setString(7,"17889972224");
                ps.setInt(8,i);
                ps.setInt(9,i);
                ps.setString(10,"美国");
                ps.setBigDecimal(11, BigDecimal.valueOf(11));
                ps.setBigDecimal(12, BigDecimal.TEN);
                ps.setLong(13, 1);
                ps.setBigDecimal(14, BigDecimal.valueOf(10));
                ps.setBigDecimal(15, BigDecimal.valueOf(i));
                ps.setInt(16, 1);
                ps.setInt(17, 1);
                ps.setString(18, "异常");
                ps.setInt(19, 0);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(ps,connection);
        }
        System.out.println("批量插入时间：" + (System.currentTimeMillis() - startTime));
        
    }

    private static void insertByOne() {
        long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO `geek`.`order`(`create_time`, `update_time`, `order_time`, `send_time`, " +
                "`goods_id`, `user_id`, `phone`, `country_id`, `city_id`, `address`, `amount`, `discount`," +
                " `coupon_id`, `express_aount`, `express_id`, `order_status`, `express_status`, `remark`, `deleted`) " +
                "VALUES (?, ?,?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            Date date = new Date(System.currentTimeMillis());
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < ONE_MILLION; i++) {
                ps.setDate(1, date);
                ps.setDate(2,date);
                ps.setDate(3,date);
                ps.setDate(4,date);
                ps.setLong(5,i);
                ps.setLong(6,i);
                ps.setString(7,"17889972222");
                ps.setInt(8,i);
                ps.setInt(9,i);
                ps.setString(10,"中国");
                ps.setBigDecimal(11, BigDecimal.valueOf(i));
                ps.setBigDecimal(12, BigDecimal.ONE);
                ps.setLong(13, 1);
                ps.setBigDecimal(14, BigDecimal.valueOf(10));
                ps.setBigDecimal(15, BigDecimal.valueOf(i));
                ps.setInt(16, 1);
                ps.setInt(17, 1);
                ps.setString(18, "正常");
                ps.setInt(19, 0);
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(ps,connection);
        }
        System.out.println("单条插入时间：" + (System.currentTimeMillis() - startTime));

    }




    private static void closeAll(PreparedStatement ps, Connection connection) {
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
    }

    /**
     * 获取连接
     */
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(MYSQL_DRIVER_CLASS);
        return DriverManager.getConnection(MYSQL_CONNECT_URL, MYSQL_USER, MYSQL_PWD);
    }

}