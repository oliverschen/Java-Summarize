## docker 中安装
### hbase

#### 安装
```bash
docker pull harisekhon/hbase:1.3
docker run -d --name hbase -p 2181:2181 -p 16010:16010 -p 16020:16020 -p 16030:16030 -p 16000:16000 harisekhon/hbase
```
#### 配置 hostname
```bash
docker exec -it hbase bash
> hostname
> 86151fc55250
```
配置 hostname 到 hosts 文件中
```properties
127.0.0.0 86151fc55250
```

### kafka

#### 安装
docker-compose.yml
```dockerfile
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper   ## 镜像
    ports:
      - "2181:2181"                 ## 对外暴露的端口号
  kafka:
    image: wurstmeister/kafka       ## 镜像
    volumes: 
        - /etc/localtime:/etc/localtime ## 挂载位置（kafka镜像和宿主机器之间时间保持一直）
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: localhost   ## 修改:宿主机IP
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181       ## kafka 运行是基于zookeeper的
      KAFKA_ADVERTISED_PORT: 9092
      KAFKA_LOG_RETENTION_HOURS: 120
      KAFKA_MESSAGE_MAX_BYTES: 10000000
      KAFKA_REPLICA_FETCH_MAX_BYTES: 10000000
      KAFKA_GROUP_MAX_SESSION_TIMEOUT_MS: 60000
      KAFKA_NUM_PARTITIONS: 3
      KAFKA_DELETE_RETENTION_MS: 1000
  kafka-manager:  
    image: sheepkiller/kafka-manager                ## 镜像：开源的web管理kafka集群的界面
    environment:
        ZK_HOSTS: localhost                   ## 修改:宿主机IP
    ports:  
      - "9001:9000"                                 ## 暴露端口
```
停止和启动
```bash
docker-compose up -d ## 启动
docker-compose stop ## 停止
docker-compose down ## 停止并删除
```

#### 注意
kafka bash 执行命令时 zk 连接要用环境配置中的 zookeeper:2181，不然连接不了
