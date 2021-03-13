学习笔记

#### （选做）分析前面作业设计的表，是否可以做垂直拆分。

> 已经完成拆分

#### （必做）设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

1. 下载 sharding-proxy 到本地，将 mysql 5.1.47 驱动包放在 lib目录下

2. 配置 sharing-proxy 的配置文件

server.yaml

```yaml
authentication:
 users:
   root:
     password: 
   sharding:
     password:  
     authorizedSchemas: sharding_geek

props:
 max-connections-size-per-query: 1
 acceptor-size: 16 
 executor-size: 16  
 proxy-frontend-flush-threshold: 128  
 proxy-transaction-type: LOCAL
 proxy-opentracing-enabled: false
 proxy-hint-enabled: false
 query-with-cipher-column: true
 sql-show: true
 check-table-metadata-enabled: false
```

config-sharding.yaml

```yaml
schemaName: sharding_geek

dataSourceCommon:
 username: root
 password:
 connectionTimeoutMilliseconds: 30000
 idleTimeoutMilliseconds: 60000
 maxLifetimeMilliseconds: 1800000
 maxPoolSize: 50
 minPoolSize: 1
 maintenanceIntervalMilliseconds: 30000

dataSources:
 geek_0:
   url: jdbc:mysql://127.0.0.1:3306/geek_0?serverTimezone=UTC&useSSL=false
 geek_1:
   url: jdbc:mysql://127.0.0.1:3306/geek_1?serverTimezone=UTC&useSSL=false

rules:
- !SHARDING
 tables:
   order:
     actualDataNodes: geek_${0..1}.order_${0..15}
     tableStrategy:
       standard:
         shardingColumn: id
         shardingAlgorithmName: order_inline
     keyGenerateStrategy:
       column: id
       keyGeneratorName: snowflake
 defaultDatabaseStrategy:
  standard:
    shardingColumn: user_id
    shardingAlgorithmName: database_inline
 defaultTableStrategy:
   none:
 
 shardingAlgorithms:
   database_inline:
     type: INLINE
     props:
       algorithm-expression: geek${user_id % 2}
   order_inline:
     type: INLINE
     props:
       algorithm-expression: order${id % 16}
```

使用客户端登录

```bash
mysql -h127.0.0.1 -P3307 -usharding
```

进入到当前逻辑数据库

```mysql
mysql> show databases;
+---------------+
| Database      |
+---------------+
| sharding_geek |
+---------------+
```

执行建表语句会自动给每个库各建16张表。

#### 基于hmily TCC或ShardingSphere的Atomikos XA实现一个简单的分布式 事务应用demo（二选一），提交到github。

代码[地址](https://github.com/oliverschen/JAVA-000/tree/main/Week_08/homework)

