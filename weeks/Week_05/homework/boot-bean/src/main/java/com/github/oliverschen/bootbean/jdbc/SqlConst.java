package com.github.oliverschen.bootbean.jdbc;

/**
 * @author ck
 */
public interface SqlConst {

    String MYSQL_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    String MYSQL_CONNECT_URL = "jdbc:mysql://localhost:3306/geek?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=utf8";
    String MYSQL_USER = "root";
    String MYSQL_PWD = "root";


    String SQL_INSERT_USER = "insert into gk_user(name,birth,address)values(\"lisi\",\"2020-09-11\",\"gansu\");";
    String SQL_DELETE_USER = "delete from `gk_user` where id=";
    String SQL_UPDATE_USER = "update gk_user set name='%s' where id=%s";
    String SQL_FIND_USER = "select * from `gk_user`;";

    String PS_FIND = "select * from `gk_user` where name = ?";

}