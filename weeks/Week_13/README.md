### 第 24 课作业

1. **(必做)** 搭建ActiveMQ服务，基于JMS，写代码分别实现对于queue和topic的消息 

生产和消费，代码提交到github。 

#### Docker搭建

```json
docker run -d --name activemq1 -p 61616:61616 -p 8161:8161 rmohr/activemq
```

##### 登录管理界面

```http
http://localhost:8161/admin/
```

#### 使用

##### provider

```java
/**
 * @author ck
 */
@Slf4j
public class Producer {


    public static void main(String[] args) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = null;
        Session session;
        MessageProducer producer;

        try {
            connection = factory.createConnection();
            // 开启连接
            connection.start();
            //AUTO_ACKNOWLEDGE 自动签收
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            // queue
//            Queue destination = session.createQueue(CK_MSG);
            // topic 
            Topic destination = session.createTopic(CK_MSG_TOPIC);
            producer = session.createProducer(destination);
            for (int i = 0; i < 5; i++) {
                String msg = "my id is : " + i;
                TextMessage textMessage = session.createTextMessage(msg);
                producer.send(textMessage);
                log.info("send success, id is :" + i);
            }
            session.commit();
        }catch (JMSException e) {
            e.printStackTrace();
            log.error("provider send error !");
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

##### consumer

```java
/**
 * @author ck
 */
@Slf4j
public class Consumer {

    public static void main(String[] args) {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = null;
        Session session;

        try {
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

//            Queue destination = session.createQueue(CK_MSG);
            Topic destination = session.createTopic(CK_MSG_TOPIC);

            MessageConsumer consumer = session.createConsumer(destination);

            while (true) {
                TextMessage receive = (TextMessage) consumer.receive(2000);
                if (receive != null) {
                    log.info("message info:::" + receive.getText());
                }else {
                    log.info("wait message...");
                    Thread.sleep(3000);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
            log.error("receive message error");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
```

#### 结果

##### provider

```bash
21:35:07.981 [main] DEBUG org.apache.activemq.TransactionContext - Begin:TX:ID:chenkui.local-56293-1612791307801-1:1:1
21:35:07.983 [main] INFO com.github.oliverschen.mq.Producer - send success, id is :0
21:35:07.983 [main] INFO com.github.oliverschen.mq.Producer - send success, id is :1
21:35:07.984 [main] INFO com.github.oliverschen.mq.Producer - send success, id is :2
21:35:07.984 [main] INFO com.github.oliverschen.mq.Producer - send success, id is :3
21:35:07.984 [main] INFO com.github.oliverschen.mq.Producer - send success, id is :4
```

##### consumer

```bash
21:35:03.339 [main] INFO com.github.oliverschen.mq.Consumer - wait message...
21:35:08.016 [main] INFO com.github.oliverschen.mq.Consumer - message info:::my id is : 0
21:35:08.016 [main] INFO com.github.oliverschen.mq.Consumer - message info:::my id is : 1
21:35:08.016 [main] INFO com.github.oliverschen.mq.Consumer - message info:::my id is : 2
21:35:08.016 [main] INFO com.github.oliverschen.mq.Consumer - message info:::my id is : 3
21:35:08.017 [main] INFO com.github.oliverschen.mq.Consumer - message info:::my id is : 4
```

### 第 25 课作业

1. **(必做)** 搭建一个3节点Kafka集群，测试功能和性能；实现spring kafka下对kafka集群的操作，将代码提交到github。 

#### Docker 搭建

##### kafka

```bash
docker run -d --name zookeeper --publish 2181:8181 --volume ~/develop/docker/app/zookeeper:/etc/localtime zookeeper:latest
```

```bash
docker run -d --name kafka --publish 9092:9092 \
--link zookeeper \
--env KAFKA_ZOOKEEPER_CONNECT=192.168.0.105:2181 \
--env KAFKA_ADVERTISED_HOST_NAME=192.168.0.105 \
--env KAFKA_ADVERTISED_PORT=9092  \
wurstmeister/kafka
```

##### 集群

kafka 集群暂时没有搭建，后面有空余时间完善*

#### 使用

##### pom

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

##### 配置

```properties
### kafka 配置
spring:
  kafka:
    bootstrap-servers: 127.0.0.1:9092
    ### producer
    producer:
      retries: 0
      batch-size: 16384
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    ### consumer
    consumer:
      group-id: jihe
      auto-offset-reset: earliest
      enable-auto-commit: true
      auto-commit-interval: 100
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
```

##### producer

```java
@GetMapping("/kafka")
public void kafkaProducer() throws JsonProcessingException {
    for (int i = 0; i < 10; i++) {
        Msg message = new Msg();
        message.setId(System.currentTimeMillis());
        message.setMsg("消息 ：" + i);
        message.setSendTime(LocalDateTime.now());
        log.info("+++++++++++++++++++++  message = {}", message.toString());
        kafkaTemplate.send("jihe", mapper.writeValueAsString(message));
    }
}
```

##### consumer

```java
public class Consumer {
    @KafkaListener(topics = {"jihe"})
    public void listen(ConsumerRecord<?, ?> record) {

        Optional.ofNullable(record.value()).ifPresent(out::println);

        log.info("record::::{}", record);
    }
}
```

##### 测试

```bash
2021-02-16 01:06:14.758  INFO 44501 --- [nio-8081-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version: 2.6.0
2021-02-16 01:06:14.758  INFO 44501 --- [nio-8081-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId: 62abe01bee039651
2021-02-16 01:06:14.758  INFO 44501 --- [nio-8081-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1613408774758
2021-02-16 01:06:14.765  INFO 44501 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : [Producer clientId=producer-1] Cluster ID: DOeAtGoQQ3yaS0VmoM5vfw
2021-02-16 01:06:14.782  INFO 44501 --- [nio-8081-exec-1] c.g.oliverschen.mq.MqAllApplication      : +++++++++++++++++++++  message = Msg(id=1613408774782, msg=消息 ：1, sendTime=2021-02-16T01:06:14.782)
2021-02-16 01:06:14.783  INFO 44501 --- [nio-8081-exec-1] c.g.oliverschen.mq.MqAllApplication      : +++++++++++++++++++++  message = Msg(id=1613408774783, msg=消息 ：2, sendTime=2021-02-16T01:06:14.783)
{"id":1613408774666,"msg":"消息 ：0","sendTime":{"dayOfWeek":"TUESDAY","dayOfYear":47,"year":2021,"month":"FEBRUARY","nano":666000000,"monthValue":2,"dayOfMonth":16,"hour":1,"minute":6,"second":14,"chronology":{"calendarType":"iso8601","id":"ISO"}}}
2021-02-16 01:06:14.825  INFO 44501 --- [ntainer#0-0-C-1] c.github.oliverschen.mq.kafka.Consumer   : record::::ConsumerRecord(topic = jihe, partition = 0, leaderEpoch = 0, offset = 0, CreateTime = 1613408774765, serialized key size = -1, serialized value size = 252, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = {"id":1613408774666,"msg":"消息 ：0","sendTime":{"dayOfWeek":"TUESDAY","dayOfYear":47,"year":2021,"month":"FEBRUARY","nano":666000000,"monthValue":2,"dayOfMonth":16,"hour":1,"minute":6,"second":14,"chronology":{"calendarType":"iso8601","id":"ISO"}}})
{"id":1613408774782,"msg":"消息 ：1","sendTime":{"dayOfWeek":"TUESDAY","dayOfYear":47,"year":2021,"month":"FEBRUARY","nano":782000000,"monthValue":2,"dayOfMonth":16,"hour":1,"minute":6,"second":14,"chronology":{"calendarType":"iso8601","id":"ISO"}}}
2021-02-16 01:06:14.826  INFO 44501 --- [ntainer#0-0-C-1] c.github.oliverschen.mq.kafka.Consumer   : record::::ConsumerRecord(topic = jihe, partition = 0, leaderEpoch = 0, offset = 1, CreateTime = 1613408774783, serialized key size = -1, serialized value size = 252, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = {"id":1613408774782,"msg":"消息 ：1","sendTime":{"dayOfWeek":"TUESDAY","dayOfYear":47,"year":2021,"month":"FEBRUARY","nano":782000000,"monthValue":2,"dayOfMonth":16,"hour":1,"minute":6,"second":14,"chronology":{"calendarType":"iso8601","id":"ISO"}}})
```

代码地址[mq-all](https://github.com/oliverschen/Java-Summarize/tree/main/weeks/Week_13/mq-all)

### 第 26 课作业

2. **(必做)** 思考和设计自定义MQ第二个版本或第三个版本，写代码实现其中至少一 个功能点，把设计思路和实现代码，提交到github。

#### 第一个版本-内存Queue

- [x] 基于内存Queue实现生产和消费API（已经完成） 

- [x] 创建内存BlockingQueue，作为底层消息存储 

- [x] 定义Topic，支持多个Topic 

- [x] 定义Producer，支持Send消息 

- [x] 定义Consumer，支持Poll消息 

老师实现，[代码地址](https://github.com/oliverschen/oli-mq/tree/v1.0)

#### 第二个版本：自定义Queue 

去掉内存Queue，设计自定义Queue，实现消息确认和消费offset 

- [x] 自定义内存Message数组模拟Queue。 

- [x] 使用指针记录当前消息写入位置。 

- [x] 对于每个命名消费者，用指针记录消费位置。 

因为数据没有真实的弹出，还在内存，容易OOM。 

#### 第三个版本：基于SpringMVC实现MQServer 

拆分broker和client(包括producer和consumer) 

- [ ] 将Queue保存到web server端 

- [ ] 设计消息读写API接口，确认接口，提交offset接口 

- [ ] producer和consumer通过httpclient访问Queue 

- [ ] 实现消息确认，offset提交 

- [ ] 实现consumer从offset增量拉取 

从单机走向服务器模式。 