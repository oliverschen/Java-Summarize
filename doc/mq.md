# 📮分布式消息

### MQ 作用
1. 异步通信：异步通信，减少线程等待，特别是处理批量等大事务、耗时操作。 
2. 系统解耦：系统不直接调用，降低依赖，特别是不在线也能保持通信最终完成。 
3. 削峰平谷：压力大的时候，缓冲部分请求消息，类似于背压处理。 
4. 可靠通信：提供多种消息模式、服务质量、顺序保障等。

### 消息处理的保障
三种QoS（注意：这是消息语义的，不是业务语义的）
1. At most once，至多一次，消息可能丢失但是不会重复发送； 

2. At least once，至少一次，消息不会丢失，但是可能会重复； 

3. Exactly once，精确一次，每条消息肯定会被传输一次且仅一次。

消息处理的事务性

1. 通过确认机制实现事务性； 
2. 可以被事务管理器管理，甚至可以支持XA。

### 消息协议

| STOMP | JMS            |
| ----- | -------------- |
| AMQP  | MQTT           |
| XMPP  | Open Messaging |

### 开源中间件

| 分类 |               |          |
| ---- | ------------- | -------- |
| 一代 | ActiveMq      | RabbitMq |
| 二代 | Kafka         | RocketMq |
| 三代 | Apache Pulsar |          |

### Kafka

Kafka 是一个消息系统，由 LinkedIn 于2011年设计开发，用作 LinkedIn 的活动流 （Activity Stream）和运营数据处理管道（Pipeline）的基础。 Kafka 是一种分布式的，基于发布 / 订阅的消息系统。

#### 主要设计目标如下

1. 以时间复杂度为 O(1) 的方式提供消息**持久化**能力，即使对 TB 级以上数据也能保证 常数时间复杂度的访问性能。 
2. **高吞吐率**。即使在非常廉价的商用机器上也能做到单机支持每秒 100K 条以上消息 的传输。 
3. 支持 Kafka Server 间的消息分区，及分布式消费，同时保证每个 Partition 内的消息**顺序传输**。 
4. 同时支持离线数据处理和实时数据处理。 
5. Scale out：支持在线**水平扩展**。

#### 基本概念

1. Broker：Kafka 集群包含一个或多个服务器，这种服务器被称为 broker。 
2. Topic：每条发布到 Kafka 集群的消息都有一个类别，这个类别被称为 Topic。 （物理上不同 Topic 的消息分开存储，逻辑上一个 Topic 的消息虽然保存于一个或 多个 broker 上，但用户只需指定消息的 Topic 即可生产或消费数据而不必关心数 据存于何处）。 
3. Partition：Partition 是物理上的概念，每个 Topic 包含一个或多个 Partition。 
4. Producer：负责发布消息到 Kafka broker。 
5. Consumer：消息消费者，向 Kafka broker 读取消息的客户端。 
6. Consumer Group：每个 Consumer 属于一个特定的 Consumer Group（可为每个 Consumer 指定 group name，若不指定 group name 则属于默认的 group）。

### EIP

集成领域的两大法宝，就是 RPC 和 Messaging 也是所有 SOA/ESB 的基础。

EIP里，所有的处理，都可以看做是： 

1. 数据从一个输入源头出发； 
2. 数据在一个管道流动； 
3. 经过一些处理节点，数据被过滤器处理，增强，或者转换，或者做个业务处理等等。 
4. 最后，数据输出到一个目的地。

#### 组件

[Camel](https://camel.apache.org)，[Spring Integration](https://spring.io/projects/spring-integration)

### 总结图

<img src="https://github.com/oliverschen/Java-Summarize/blob/main/images/MQ.png" style="zoom:50%" />