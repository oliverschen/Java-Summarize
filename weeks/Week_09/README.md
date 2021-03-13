学习笔记

## 第17课

1、（选做）实现简单的Protocol Buffer/Thrift/gRPC(选任一个)远程调用demo。

### gRPC

1. 使用 gRPC maven 插件按照 protobuf 源文件生成 gRPC 相关的 Java 文件。
2. 将生成好的文件放到对应的类路径下，完成 client 端和 sever 端代码

代码地址[rpc-grpc](https://github.com/oliverschen/JAVA-000/tree/main/Week_09/rpc-grpc)

### RPC

RPC：远程过程调用，简单来说就是“像调用本地方法一样调用远程方法”

#### 原理

**核心是代理机制。**

1.本地代理存根: Stub

2.本地序列化反序列化

3.网络通信

4.远程序列化反序列化

5.远程服务存根: Skeleton

6.调用实际业务服务

7.原路返回服务结果

8.返回给本地调用方



3、（必做）改造自定义RPC的程序，提交到github： 

​	1）尝试将服务端写死查找接口实现类变成泛型和反射

将需要 rpc 调用的类添加 @Service 注解，交给 Spring 管理，在 Spring BeanPostProcessor 后置方法中放入一个 map 中，以Map<接口名，实现类> 的方式。

在 provider 中调用时直接用接口名调用，consumer 在 Map 中就可以获取到对应的具体实现类。

​	2）尝试将客户端动态代理改成AOP，添加异常处理

用 bytebuddy 方法拦截的形式实现代理，bytebuddy API 使用起来很友好，很容易上手。

​	3）尝试使用Netty+HTTP作为client端传输方式

用 netty 发送 http 请求实现

代码地址[rpcfx](https://github.com/oliverschen/JAVA-000/tree/main/Week_09/rpcfx)

## 第18 课

1.2作业已经练习完成

3、（必做）结合dubbo+hmily，实现一个TCC外汇交易处理，代码提交到github： 

​	1）用户A的美元账户和人民币账户都在A库，使用1美元兑换7人民币； 

​	2）用户B的美元账户和人民币账户都在B库，使用7人民币兑换1美元； 

​	3）设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。

代码地址[dubbo+hmily](https://github.com/oliverschen/JAVA-000/tree/main/Week_09/dubbo-hmily)

使用多数据源的方式完成分布式事务模拟，启动 dubbo-account 服务

执行`http://localhost:8080/ex/cny?cny=7` 实现 user_1 中用户张三转账 7 人民币给 user_2 中用户李四 1 美元到美元账户转账。

执行`http://localhost:8080/ex/usd?cny=14` 实现 user_2 中李四转账 2 美元给 user_1 中用户张三 14 人民币到人民币账户。

#### 问题

1. 关于多数据源配置时，mybatis 的 Java 和 数据库驼峰映射的参数 `map-underscore-to-camel-case` 会失效，需要手动将其添加到配置中

```java
@Bean(name = "sqlSessionFactoryUser1")
public SqlSessionFactory sqlSessionFactoryUser1(
        @Qualifier("dataSourceUser1") DataSource datasourceUser1,
        @Qualifier("configuration") org.apache.ibatis.session.Configuration configuration)
        throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(datasourceUser1);
  	// 添加配置
    bean.setConfiguration(configuration);
    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_PATH));
    return bean.getObject();
}
/**
 * 主要加载驼峰命名转换
 */
@Bean(name = "configuration")
@ConfigurationProperties(prefix = "mybatis.configuration")
public org.apache.ibatis.session.Configuration configuration() {
    return new org.apache.ibatis.session.Configuration();
}
```

2. hmily 中 `hmily-core` 需要排除 mongo 驱动包

```xml
<dependency>
    <groupId>org.dromara</groupId>
    <artifactId>hmily-core</artifactId>
    <version>2.1.1</version>
    <exclusions>
        <exclusion>
            <groupId>org.mongodb</groupId>
            <artifactId>mongo-java-driver</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

不排除会有一下错误：

```bashThe following method did not exist:
com.mongodb.MongoClientSettings$Builder.uuidRepresentation(Lorg/bson/UuidRepresentation;)Lcom/mongodb/MongoClientSettings$Builder;
```