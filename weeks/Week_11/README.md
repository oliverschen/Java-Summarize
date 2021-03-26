# 分布式缓存

## 数据分类

### 按照使用频率分类

1. 静态数据：一般不变，类似于字典表 

2. 准静态数据：变化频率很低，部门结构设置，全国行政区划数据等 

3. 中间状态数据：一些计算的可复用中间数据，变量副本，配置中心的本地副本

**热数据**：使用频率高 

**读写比较大**：读的频率 >> 写的频率

这些数据适合于使用缓存的方式访问 

广义上来说，为了加速数据处理，让业务更快访问的临时存放冗余数据，都是缓存。狭义上，现在我们一般在分布式系统里把缓存到内存的数据叫做内存缓存。

### 各类缓存

内存 ~ 可以看做是 CPU 和 磁盘之间的缓存 

CPU与内存的处理速度也不一致，出现 L1&L2 Cache 

网络处理，数据库引擎的各种Buffer，都可以看做是缓存 

GUI的Double Buffer（双缓冲），是一个经典的性能优化方法 

#### 缓存的本质

系统各级处理速度不匹配，导致利用空间换时间 

缓存是提升系统性能的一个简单有效的办法

### 缓存加载时机

1. **启动全量加载** ：全局有效，使用简单 

2. **懒加载**

   1. 同步使用加载 
      - 先看缓存是否有数据，没有的话从数据库读取 
      - 读取的数据，先放到内存，然后返回给调用方 

   2. 延迟异步加载

      - 从缓存获取数据，不管是否为空直接返回

      - 策略1异步：如果为空，则发起一个异步加载的线程，负责加载数据 

      - 策略2解耦：异步线程负责维护缓存的数据，定期或根据条件触发更新

### 有效性和数据同步

1. 为什么变动频率大，一致性要求高的数据不适合做缓存？

变动大：缓存数据和数据库数据一直存在差异

一致性高：意味着只有使用源数据/加了事务才是保险的

2. 如何看缓存的有效性？

**读写比**：对数据写操作导致数据变动，意味着维护成本。N:1

**命中率**：意味着缓存数据被使用，缓存是有价值的。90%+

对**数据一致性，性能，成本**的综合衡量，是引入缓存的必须指标

### 缓存使用不当问题

1. 系统预热导致启动慢

启动慢导致系统不能做到快速应对故障宕机等问题。 

2. 系统内存资源耗尽

滥用缓存「只加不减」，无用的数据不及时清理掉，会发生内存被塞满的极端情况。

## 本地缓存

1. JVM 进程中的缓存，比如 HashMap 缓存
2. Hibernate/Mybatis 都有对应一级和二级缓存
3. Guava Cache
4. Spring Cache

## 远程缓存

本地缓存在大规模集群环境时会存在大量冗余情况。长期驻留在 JVM 进程中还会影响 GC。缓存数据的调度处理，影响执行业务的线程，抢资源。 

### Redis/Memcached

缓存中间件：Redis 是一款开源的 NoSql 数据库。Memcached 是一款开源高性能，分布式内存对象缓存系统。 

### Hazelcast/Ignite

内存网格

## 常见问题

### 内存穿透

大量并发查询**不存在**的 KEY，导致将所有压力透传到数据库

#### 解决办法 

1. 缓存空值的 KEY，这样第一次不存在也会被加载会记录，下次拿到有这个 KEY。 

2. Bloom 过滤或 RoaringBitmap 判断 KEY 是否存在。 

3. 完全以缓存为准，使用延迟异步加载的策略2，这样就不会触发更新。

### 缓存击穿

某个 KEY 失效时，突然有大量并发查询访问此 KEY。

#### 解决办法 

1. KEY 的更新操作添加全局互斥锁。 

2. 完全以缓存为准，使用延迟异步加载的策略2，这样就不会触发更新。

### 缓存雪崩

当某一时刻发生大规模的缓存失效的情况，会有大量的请求进来直接打到数据库，导致数据库压力过大升值宕机。

#### 解决办法 

1. 更新策略在时间上做到比较均匀。 

2. 使用的热数据尽量分散到不同的机器上。 

3. 多台机器做主从复制或者多副本，实现高可用。 

4. 实现熔断限流机制，对系统进行负载能力控制。

## Redis高级功能

### 事务 

事务提供了一种“将多个命令打包， 然后一次性、按顺序地执行”的机制， 并且事务在执行的期间不会主动中断 —— 服务器在执行完事务中的所有命令之后， 才会继续处理其他客户端的其他命令。[redisBook](https://redisbook.readthedocs.io/en/latest/feature/transaction.html)

### Lua脚本

\- 类似于数据库的存储过程，mongodb的js脚本. [redisbook](https://redisbook.readthedocs.io/en/latest/feature/scripting.html)

```bash
#在脚本环境的初始化工作完成以后， Redis 就可以通过 EVAL 命令或 EVALSHA 命令执行 Lua 脚本了。
#其中， EVAL 直接对输入的脚本代码体（body）进行求值：

redis> EVAL "return 'hello world'" 0
"hello world"
#而 EVALSHA 则要求输入某个脚本的 SHA1 校验和， 这个校验和所对应的脚本必须至少被 EVAL 执行过一次：

redis> EVAL "return 'hello world'" 0
"hello world"

redis> EVALSHA 5332031c6b470dc5a0dd9b4bf2030dea6d65de91 0    // 上一个脚本的校验和
"hello world"
```

### 管道

Redis客户端与服务器之间使用TCP协议进行通信，并且很早就支持管道（pipelining）技术了。在某些高并发的场景下，网络开销成了Redis速度的瓶颈，所以需要使用管道技术来实现突破。

## 性能

### 命中率和读写比

```bash
127.0.0.1:6379> info stats
# Stats
keyspace_hits:100017 # 命中 key
keyspace_misses:0    # 没有命中 key
# 命中率
keyspace_hits / (keyspace_hits + keyspace_misses)
```

### 资源管理和分配

1. 尽量每个业务集群单独使用自己的Redis，不混用； 

2. 控制Redis资源的申请与使用，规范环境和Key的管理（以一线互联网为例）； 

3. 监控CPU 100%，优化高延迟的操作。

# 作业

## 第二十二课

### 3. （选做）spring集成练习

> 1）实现update方法，配合@CachePut 
>
> 2）实现delete方法，配合@CacheEvict 
>
> 3）将示例中的spring集成Lettuce改成jedis或redisson。 

redisson 和 redis 包版本需要注意，容易出现版本不兼容现象。[redis](https://github.com/oliverschen/Java-Summarize/tree/main/weeks/Week_11/redis)

### 4.（必做）基于Redis封装分布式数据操作

#### 1）在Java中实现一个简单的分布式锁

##### 技术点

1. 当前线程加的锁不能被其他线程释放

2. 设置等待时间，等待时间内获取不到锁直接返回

3. 循环等待时需要线程休眠，避免占用大量 CPU 资源

4. 释放锁时依赖 lua 脚本实现，需要判断是否是释放当前线程自己的锁 

##### 代码

```java
/**
 * @author ck
 */
@Slf4j
@Component
public class RedisLock<K, V> {
  
    @Resource
    private RedisTemplate<K, V> redisTemplate;

    /**
     * 当前锁 key
     */
    private K lockKey;
    /**
     * 当前锁唯一ID，防止被其他线程释放锁
     */
    private String lockId;

    /**
     * 锁执行结果
     */
    private static final Long UNLOCK_SUCCESS = 1L;
    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return 																										redis.call('del', KEYS[1]) else return 0 end";

    /**
     * redis 锁
     * @param key        加锁 key
     * @param expireTime 加锁时间
     * @param waitTime   等待时间
     */
    public boolean lock(K key, Long expireTime, Long waitTime) {
        this.lockKey = key;
        // 唯一ID，防止发生释放锁异常
        this.lockId = UUID.randomUUID().toString().replace("-", "");
        // 超过 waitTime 还没有获取到就返回失败
        long endTime = System.currentTimeMillis() / 1000 + waitTime;
        while (System.currentTimeMillis() / 1000 < endTime) {
            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, (V) lockId, expireTime, 																																										TimeUnit.SECONDS);
            if (result != null && result) {
                return true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    /**
     * 解锁
     */
    public boolean unlock() {
        if (lockId == null) {
            return false;
        }
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);
        try {
            // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockId);
            log.info("release lock success, lockId :{}, result:{}", lockId, result);
            if (UNLOCK_SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            log.error("unlock due to error", e);
        }
        return false;
    }

}
```

#### 2）在Java中实现一个分布式计数器，模拟减库存。 

##### 技术点

1. 减少可以不用加分布式锁，通过 lua 脚本，配合 incr 天然的原子性实现库存判断

```java
/**
 * 模拟减库存
 * @author ck
 */
@Component
public class RedisDb<K,V> {

    @Resource
    private RedisTemplate<K, V> redisTemplate;

    private static final Long SUCCESS = 1L;
    private static final Long FAIL = 0L;
    private static final String DECREMENT_SCRIPT = "if redis.call('decrby', KEYS[1], ARGV[1]) < 0 then 																							redis.call('incrby', KEYS[1], ARGV[1]) return 0 else return 1 end";

    /**
     * 增加库存
     * @param count 数量
     * @return 剩余数量
     */
    public Long increment(K k, Long count) {
        if (count <= 0L) {
            return (Long) redisTemplate.opsForValue().get(k);
        }
        return redisTemplate.opsForValue().increment(k, count);
    }

    /**
     * 减少库存
     * @param k key
     * @param count 减少数量
     * @return 是否成功扣减
     */
    public boolean decrement(K k, Integer count) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(DECREMENT_SCRIPT, Long.class);
        Long result = redisTemplate.execute(script, Collections.singletonList(k), count);
        if (SUCCESS.equals(result)) {
            return true;
        }
        if (FAIL.equals(result)) {
            throw new DbEmptyException("库存不足");
        }
        return false;
    }
}
```

#### 5、基于Redis的PubSub实现订单异步处理

##### 技术点

1. 需要创建一个监听类，编写监听方法
2. 绑定监听类的方法到 `MessageListenerAdapter` 上
3. 创建 `RedisMessageListenerContainer` 并且绑定 `MessageListenerAdapter` 和 channel name

##### 代码

###### 数据绑定

```java
/**
 * 绑定监听方法
 */
@Bean
public MessageListenerAdapter listenerAdapter(RedisListener redisListener) {
    return new MessageListenerAdapter(redisListener, REDIS_LISTEN_METHOD);
}
/**
 * 订阅配置
 */
@Bean
public RedisMessageListenerContainer container(RedisConnectionFactory factory,
                                               MessageListenerAdapter listenerAdapter) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(factory);
    container.addMessageListener(listenerAdapter, PatternTopic.of(REDIS_CHANNEL_ORDER));
    return container;
}
```

###### 监听

```java
/**
 * @author ck
 */
@Slf4j
@Component
public class RedisListener {


    public void process(String msg,String publishKey) throws IOException {
        Order order = JsonUtil.json2Object(msg, Order.class);
        log.info("publish key :{},order id :{} ===> process order info", publishKey, order.getId());
    }
}
```

###### 发布

```java
@Override
public void insert(Order order) {
    LocalDateTime now = LocalDateTime.now();
    order.setCreateTime(now);
    order.setUpdateTime(now);
    orderMapper.insert(order);
    // 发布订阅消息
    log.info("publish msg order id : {}", order.getId());
    redisTemplate.convertAndSend(REDIS_CHANNEL_ORDER, order);
}
```

以上作业代码地址[redis](https://github.com/oliverschen/JAVA-000/tree/main/Week_11)

## 第二十三课

### 1.（必做）配置redis的主从复制，sentinel高可用，Cluster集群。 

提交如下内容到github： 

#### 1）config配置文件

##### 主从复制

###### 结构

```bash
--docker-compose.yml
--redis/
	--conf/
		--redis.conf
	--data/
--redis-slave01/
	--conf/
		--redis-6380.conf
	--data/
--redis-slave02/
	--conf/
		--redis-6381.conf
	--data/
```

###### 主节点

```properties
bind 0.0.0.0
# 启用保护模式
protected-mode no
# 监听端口
port 6379
# 启动时不打印logo
always-show-logo no
# 设定密码认证
requirepass redis
```

###### 从节点

```properties
bind 0.0.0.0
# 启用保护模式
protected-mode no
# 监听端口
port 6380
# 启动时不打印logo
always-show-logo no
# 设定密码认证
requirepass redis
# 配置master节点信息
#slaveof <masterip> <masterport>
# 此处masterip所指定的redis-master是运行master节点的容器名
# Docker容器间可以使用容器名代替实际的IP地址来通信
replicaof 127.0.0.1 6379

# 设定连接主节点所使用的密码
masterauth "root"
```

另外还要修改端口 `6381` 配置第二个从节点

###### docker-compose

```yml
---

version: '3'

services:
  # 主节点的容器
  redis-master:
    image: redis:latest
    container_name: redis-master
    restart: always
    # 指定时区，保证容器内时间正确
    environment:
      TZ: "Asia/Shanghai"
    ports:
      - "6379:6379"
    network_mode: host
    volumes:
      # 映射配置文件和数据目录
      - ./redis/conf/redis.conf:/etc/redis/redis.conf
      - ./redis/data:/data
    sysctls:
      # 必要的内核参数
      net.core.somaxconn: '511'
    command: ["redis-server", "/etc/redis/redis.conf"]
  # 从节点1的容器
  redis-slave-1:
    image: redis:latest
    container_name: redis-slave-1
    restart: always
    depends_on:
      - redis-master
    environment:
      TZ: "Asia/Shanghai"
    ports:
      - "6380:6380"
    network_mode: host
    volumes:
      - ./redis-slave01/conf/redis-6380.conf:/etc/redis/redis.conf
      - ./redis-slave01/data:/data
    sysctls:
      net.core.somaxconn: '511'
    command: ["redis-server", "/etc/redis/redis.conf"]
  # 从节点2的容器
  redis-slave-2:
    image: redis:latest
    container_name: redis-slave-2
    restart: always
    depends_on:
      - redis-master
    ports:
      - "6381:6381"
    network_mode: host
    environment:
      TZ: "Asia/Shanghai"
    volumes:
      - ./redis-slave02/conf/redis-6381.conf:/etc/redis/redis.conf
      - ./redis-slave02/data:/data
    sysctls:
      net.core.somaxconn: '511'
    command: ["redis-server", "/etc/redis/redis.conf"]
```

###### 测试

主节点

```bash
127.0.0.1:6379> set d x
OK
```

从节点

```bash
127.0.0.1:6380> set d x
(error) READONLY You can't write against a read only replica.
# 读数据
127.0.0.1:6380> get d
"x"
```

##### sentinel高可用

###### 配置

```properties
bind 0.0.0.0
# 哨兵的端口号
port 26379

# 配置哨兵的监控参数
# 格式：sentinel monitor <master-name> <ip> <redis-port> <quorum>
# master-name是为这个被监控的master起的名字
# ip是被监控的master的IP或主机名。因为Docker容器之间可以使用容器名访问，所以这里写master节点的容器名
# redis-port是被监控节点所监听的端口号
# quorom设定了当几个哨兵判定这个节点失效后，才认为这个节点真的失效了
sentinel monitor local-master 127.0.0.1 6379 2

# 连接主节点的密码
# 格式：sentinel auth-pass <master-name> <password>
sentinel auth-pass local-master root

# master在连续多长时间无法响应PING指令后，就会主观判定节点下线，默认是30秒
# 格式：sentinel down-after-milliseconds <master-name> <milliseconds>
sentinel down-after-milliseconds local-master 30000
```

在创建2个配置，修改端口

###### docker-compose

```properties
---

version: '3'

services:
  redis-sentinel-1:
    image: redis:latest
    container_name: redis-sentinel-1
    restart: always
    network_mode: host
    volumes:
      - ./redis-sentinel-1.conf:/etc/redis/redis-sentinel.conf
    environment:
      TZ: "Asia/Shanghai"
    sysctls:
      net.core.somaxconn: '511'
    command: ["redis-sentinel", "/etc/redis/redis-sentinel.conf"]
  redis-sentinel-2:
    image: redis:latest
    container_name: redis-sentinel-2
    restart: always
    network_mode: host
    volumes:
      - ./redis-sentinel-2.conf:/etc/redis/redis-sentinel.conf
    environment:
      TZ: "Asia/Shanghai"
    sysctls:
      net.core.somaxconn: '511'
    command: ["redis-sentinel", "/etc/redis/redis-sentinel.conf"]
  redis-sentinel-3:
    image: redis:latest
    container_name: redis-sentinel-3
    restart: always
    network_mode: host
    volumes:
      - ./redis-sentinel-3.conf:/etc/redis/redis-sentinel.conf
    environment:
      TZ: "Asia/Shanghai"
    sysctls:
      net.core.somaxconn: '511'
    command: ["redis-sentinel", "/etc/redis/redis-sentinel.conf"]
```

###### 测试

```bash
# Sentinel
sentinel_masters:1
sentinel_tilt:0
sentinel_running_scripts:0
sentinel_scripts_queue_length:0
sentinel_simulate_failure_flags:0
master0:name=local-master,status=ok,address=127.0.0.1:6379,slaves=2,sentinels=3
```

停掉主节点 `6379`重试 30s 后会自动选主

```bash
127.0.0.1:26379> info sentinel
# Sentinel
sentinel_masters:1
sentinel_tilt:0
sentinel_running_scripts:0
sentinel_scripts_queue_length:0
sentinel_simulate_failure_flags:0
master0:name=local-master,status=ok,address=127.0.0.1:6380,slaves=2,sentinels=3
```

##### 集群

###### 配置

**主节点**

```bash
daemonize yes
port 7001
pidfile /var/run/redis-7001.pid
dir /Users/chenkui/develop/docker/app/redis-cluster/7001/
cluster-enabled yes
cluster-config-file nodes-7001.conf
cluster-node-timeout 10000
# bind 127.0.0.1（bind绑定的是自己机器网卡的ip，如果有多块网卡可以配多个ip，代表允许客户端通过机器的哪些网卡ip去访问，内网一般可以不配置bind，注释掉即可）
protected-mode  no
appendonly yes
```

修改端口复制 3 份到不同目录下

**从节点**

```bash
daemonize yes
port 7004
pidfile /var/run/redis-7004.pid
dir /Users/chenkui/develop/docker/app/redis-cluster/7004/
cluster-enabled yes
cluster-config-file nodes-7004.conf
cluster-node-timeout 10000
# bind 127.0.0.1（bind绑定的是自己机器网卡的ip，如果有多块网卡可以配多个ip，代表允许客户端通过机器的哪些网卡ip去访问，内网一般可以不配置bind，注释掉即可）
protected-mode  no
appendonly yes
```

修改端口复制 3 份到不同目录下

**启动所有节点**

> 指定不同端口配置文件启动所有节点
>
> ~/develop/docker/app/redis-cluster/7001/redis.conf

**创建集群**

```bash
./redis-cli --cluster create --cluster-replicas 1 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 127.0.0.1:7006
```

###### 测试

```bash
127.0.0.1:7001> cluster info
cluster_state:ok
cluster_slots_assigned:16384
cluster_slots_ok:16384
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6
cluster_size:3
cluster_current_epoch:6
cluster_my_epoch:1
cluster_stats_messages_ping_sent:83
cluster_stats_messages_pong_sent:96
cluster_stats_messages_sent:179
cluster_stats_messages_ping_received:91
cluster_stats_messages_pong_received:83
cluster_stats_messages_meet_received:5
cluster_stats_messages_received:179
```

```bash
➜  bin ./redis-cli -c -p 7001
127.0.0.1:7001> set x 1
-> Redirected to slot [16287] located at 127.0.0.1:7003
OK
```

#### 2）启动和操作、验证集群下数据读写的命令步骤

##### 配置

```yml
spring:
  redis:
# lettuce 客户端
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 3
# 单机配置
#    host: localhost
#    port: 6379
#    password: root
# 集群配置
    cluster:
      nodes:
        - 127.0.0.1:7001
        - 127.0.0.1:7002
        - 127.0.0.1:7003
        - 127.0.0.1:7004
        - 127.0.0.1:7005
        - 127.0.0.1:7006
      max-redirects: 12
# jedis 客户端
#    jedis:
#      pool:
#        max-idle: 8
#        max-active: 8
#        min-idle: 3
```

代码地址[redis](https://github.com/oliverschen/JAVA-000/tree/main/Week_11)
