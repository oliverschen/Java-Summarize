## 第十三课

（必做）按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。

执行时先清空表，然后分别执行得出结果

单条插入时间：340s

开启事物单条插入时间：146s

批量插入时间：275s

进过对比手动开启事物之后插入效率最高。

## 第十四课

1.（选做）配置一遍异步复制，半同步复制、组复制。 

### 部署

Docker 部署 Mysql5.7 

#### 配置

```
[mysqld]
## 设置server_id，唯一
server_id=1
## 复制过滤：去掉不尽兴同步的基础库
binlog-ignore-db=mysql
## 开启二进制日志功能，重要步骤
log-bin=replicas-mysql-bin
## 为每个session分配的内存，在事务过程中用来存储二进制日志的缓存
binlog_cache_size=1M
## 主从复制的格式（mixed,statement,row，默认格式是statement）
binlog_format=mixed
## 二进制日志自动删除/过期的天数。默认值为0，表示不自动删除。
expire_logs_days=7
## 跳过主从复制中遇到的所有错误或指定类型的错误，避免slave端复制中断。
## 如：1062错误是指一些主键重复，1032错误是因为主从数据库数据不一致
slave_skip_errors=1062
```

#### 启动

指定不同的配置和端口启动两个容器

> docker run --name mysql-6 -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -v ~/develop/mysql/mysql-6/data:/var/lib/mysql -v ~/develop/mysql/mysql-6/conf/my.cnf:/etc/mysql/my.cnf mysql:5.7



> docker run --name mysql-7 -d -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -v ~/develop/mysql/mysql-7/data:/var/lib/mysql -v ~/develop/mysql/mysql-7/conf/my.cnf:/etc/mysql/my.cnf mysql:5.7

#### 主节点

连接主节点授权

```
GRANT REPLICATION SLAVE ON *.* to 'sync'@'%' identified by 'root';
# 查看主节点状态
show master status;
```

#### IP

```
# 查看容器的 IP 地址
docker inspect --format '{{ .NetworkSettings.IPAddress }}' <container-ID> 
```

#### 从节点

连接主节点，指定主节点的地址信息

```
change master to master_host='172.17.0.2',master_user='sync',master_password='root',master_log_file='mysql-bin.000001',master_log_pos=437,master_port=3306;
```

开启从节点

```
start slave;
```

2.（必做）读写分离-动态切换数据源版本1.0

#### 配置

```properties
spring:
  datasource:
    test:
      jdbc-url: jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false
      password: root
      username: root
      driver-class-name: com.mysql.jdbc.Driver
      hikari:
        maximum-pool-size: 200
        minimum-idle: 50
    geek:
      jdbc-url: jdbc:mysql://127.0.0.1:3307/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false
      password: root
      username: root
      driver-class-name: com.mysql.jdbc.Driver
      hikari:
        maximum-pool-size: 200
        minimum-idle: 50
```

#### 主要流程

#### 实现

1. 通过实现 Datasource 抽象类 AbstractRoutingDataSource 实现动态数据源获取，重写方法 determineCurrentLookupKey() 来指定当前需要的数据源 key
2. 将数据源配置在 AbstractRoutingDataSource map 缓存中
3. 通过 determineTargetDataSource() 方法，在缓存数据源的 map 中根据 determineCurrentLookupKey() 返回的 key 获取具体的数据源

#### 配置

1. 根据配置文件，分别注入数据源
2. 配置动态数据源，将不同的数据源写到 map 中，赋值给 AbstractRoutingDataSource 中的 map
3. 根据动态数据源创建 sqlSessionFactory sqlTemplate 等组件类

#### 选择

1. 默认配置从库，定义注解，给需要切库的方法标记注解
2. 定义 AOP 切面，根据注解设置对应数据库的 key

代码地址[dynamic-source](https://github.com/oliverschen/Java-Summarize/tree/main/weeks/Week_07/dynamic-source)

3、（必做）读写分离-数据库框架版本2.0

#### ShardingSphere 读写分离

##### 配置

```properties
spring:
  shardingsphere:
    datasource:
      names: main,secondary
      main: # 主库配置
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbcUrl: jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false
        username: root
        password: root
      secondary: #从库配置
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbcUrl: jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false
        username: root
        password: root
    masterslave: # 主从信息配置
      name: ms
      master-data-source-name: main
      slave-data-source-names: secondary
    props:
      sql:
        show: true
```

使用 sharingshpere 实现数据库读写分离操作，具体代码地址 [dynamic-ss](https://github.com/oliverschen/Java-Summarize/tree/main/weeks/Week_07/dynamic-ss)



