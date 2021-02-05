# ☁️分布式&微服务

### RPC

#### 基本原理

RPC是远程过程调用（Remote Procedure Call）的缩写形式。简单来说，就是“像调用本地方法一样调用远程方法”。

#### 调用流程

1. 本地代理存根: Stub
2. 本地序列化反序列化
3. 网络通信
4. 远程序列化反序列化
5. 远程服务存根: Skeleton
6. 调用实际业务服务
7. 原路返回服务结果
8. 返回给本地调用方

核心是代理机制。注意处理异常。

#### 序列化方式

1. 语言原生的序列化，RMI，Remoting

2. 二进制平台无关，Hessian，avro，kyro，fst等 

3. 文本，JSON、XML等

#### 框架

\- Corba/RMI/.NET Remoting

\- JSON RPC, XML RPC，WebService(Axis2, CXF)

\- Hessian, Thrift, Protocol Buffer, gRPC

#### 实现RPC框架

1. 设计：共享POJO实体类定义，接口定义。

2. 代理：Java下，代理可以选择动态代理，或者AOP实现

3. 序列化：

   - 语言原生的序列化，RMI，Remoting

   - 二进制平台无关，Hessian，avro，kyro，fst等 

   - 文本，JSON、XML等

4. 网络传输：

   - TCP/SSL

   - HTTP/HTTPS

5. 查找实现类：通过接口查找具体的业务服务实现。

### dubbo

Apache Dubbo 是一款高性能、轻量级的开源 Java 服务框架 

#### 六大核心能力

面向接口代理的高性能RPC调用，智能容错和负载均衡，服务自动注册和发现，高度可扩展能力，运行期流量调度，可视化的服务治理与运维。

#### 基础功能：RPC调用 

- 多协议（序列化、传输、RPC） 

- 服务注册发现 

- 配置、元数据管理

#### 扩展功能：集群、高可用、管控 

- 集群，负载均衡 

- 治理，路由， 

- 控制台，管理与监控

#### 整体架构

1. config 配置层：对外配置接口，以 ServiceConfig, ReferenceConfig 为中心，可以直接初始化配置类，也可以通过 spring 解析配置生成配置类 

2. proxy 服务代理层：服务接口透明代理，生成服务的客户端 Stub 和服务器端 Skeleton, 以 ServiceProxy 为中心，扩展接口为ProxyFactory 

3. registry 注册中心层：封装服务地址的注册与发现，以服务 URL 为中心，扩展接口为 RegistryFactory, Registry, RegistryService 

4. cluster 路由层：封装多个提供者的路由及负载均衡，并桥接注册中心，以 Invoker 为中心，扩展接口为 Cluster, Directory, Router, LoadBalance 

5. monitor 监控层：RPC 调用次数和调用时间监控，以 Statistics 为中心，扩展接口为 MonitorFactory, Monitor, MonitorService

6. protocol 远程调用层：封装 RPC 调用，以 Invocation, Result 为中心，扩展接口为 Protocol, Invoker, Exporter 

7. exchange 信息交换层：封装请求响应模式，同步转异步，以 Request, Response 为中心，扩展接口为 Exchanger, ExchangeChannel, ExchangeClient, ExchangeServer 

8. transport 网络传输层：抽象 mina 和 netty 为统一接口，以 Message 为中心，扩展接口为 Channel, Transporter, Client, Server, Codec 

9. serialize 数据序列化层：可复用的一些工具，扩展接口为 Serialization, ObjectInput, ObjectOutput, ThreadPool

#### 最佳实践

##### 开发分包 

建议将服务接口、服务模型、服务异常等均放在 API 包中，因为服务模型和异常也是 API 的一部分，这样做也符合分包原则：重用发布等价原则(REP)，共同重用原则 (CRP)。

服务接口尽可能大粒度，每个服务方法应代表一个功能，而不是某功能的一个步骤，否则将面临分布式事务问题，Dubbo 暂未提供分布式事务支持。

服务接口建议以业务场景为单位划分，并对相近业务做抽象，防止接口数量爆炸。 

不建议使用过于抽象的通用接口，如：Map query(Map)，这样的接口没有明确语义， 会给后期维护带来不便。

##### 环境隔离与分组

1. 多注册中心机制 
2. group机制 
3. 版本机制 

服务接口增加方法，或服务模型增加字段，可向后兼容，删除方法或删除字段，将不兼容，枚举类型新增字段也不兼容，需通过变更版本号升级。

##### 参数配置 

通用参数以 consumer 端为准，如果 consumer 端没有设置，使用 provider 数值 

建议在 Provider 端配置的 Consumer 端属性有： 

1. timeout：方法调用的超时时间 

2. retries：失败重试次数，缺省是 2

3. loadbalance：负载均衡算法 3，缺省是随机 random。 

4. actives：消费者端的最大并发调用限制，即当 Consumer 对一个服务的并发调用到上限后，新 调用会阻塞直到超时，可以配置在方法或服务上。 

建议在 Provider 端配置的 Provider 端属性有： 

1. threads：服务线程池大小 

2. executes：一个服务提供者并行执行请求上限，即当 Provider 对一个服务的并发调用达到上限后，新调用会阻塞，此时 Consumer 可能会超时。可以配置在方法或服务上。

##### 容器化部署 

注册的IP问题，两个解决办法： 

1. docker使用宿主机网络 

> docker xxx -net xxxxx 

2. docker参数指定注册的IP和端口, -e 

```properties
DUBBO_IP_TO_REGISTRY — 注册到注册中心的IP地址 

DUBBO_PORT_TO_REGISTRY — 注册到注册中心的端口 

DUBBO_IP_TO_BIND — 监听IP地址 

DUBBO_PORT_TO_BIND — 监听端口
```

##### 重试与幂等 

服务调用失败默认重试2次，如果接口不是幂等的，会造成业务重复处理

##### 分布式事务 

柔性事务，SAGA、TCC、AT 

- Seata 

- hmily 

不支持 XA

### 分布式服务化

#### RPC与分布式服务化的区别

RPC：技术概念 

1. 以RPC来讲，前面的自定义RPC功能已经差不多了。 

2. 可以再考虑一下性能优化，使用spring-boot等封装易用性。 

分布式服务化：服务是业务语义，偏向于业务与系统的集成 

1. 以分布式服务化框架的角度来看，我们还差前面的这些非功能性需求能力。 

2. 具体使用时，另外一个重点是如何设计分布式的业务服务。 

注意 ：服务 != 接口，服务可以用接口或接口文档之类的语言描述。 

#### 概念

1. 配置中心（ConfigCenter）：管理系统需要的配置参数信息 

2. 注册中心（RegistryCenter）：管理系统的服务注册、提供发现和协调能力 

3. 元数据中心（MetadataCenter）：管理各个节点使用的元数据信息 

相同点：都需要保存和读取数据/状态，变更通知 

不同点：配置是全局非业务参数，注册中心是运行期临时状态，元数据是业务模型

#### 服务注册发现

##### 服务提供者

服务提供者启动时， 将自己注册到注册中心（比如zk实现）的临时节点。 停止或者宕机时，临时节点消失。

##### 服务消费者

服务消费者启动时， 从注册中心代表服务的主节点拿到多个代表提供者的临时节点列表，并本地缓存，根据router和loadbalance算法从其中的某一个执行调用。 如果可用的提供者集合发生变化时，注册中心通知消费者刷新本地缓存的列表。 例如zk可以使用curator作为客户端操作。

#### 集群和路由

##### 服务集群

对于完全相同能力的多个服务，让它们能一切协同工作，分摊处理流量。 

##### 服务路由（Service Route） 

跟网关的路由一样 ，比如基于IP段的过滤，再比如服务都带上tag，用tag匹配这次调用范围。

##### 服务负载均衡（Service LoadBalance）

跟Nginx的负载均衡一样。 多个不同策略，原理不同，目的基本一致（尽量均匀）： Random（带权重） ，RoundRobin（轮询） 

，LeastActive（快的多给） ，ConsistentHashLoadBalance（同样参数请求到一个提供者）

#### 服务过滤和流控

##### 服务过滤 

所有的复杂处理，都可以抽象为管道+过滤器模式（Channel+Filter）这个机制是一个超级bug的存在， 可以用来实现额外的增强处理（类似AOP），也可以中断当前处理流程，返回特定数据。 

##### 服务流控（Flow Control）

稳定性工程，系统的容量有限，保持部分服务能力是最佳选择，然后在问题解决后恢复正常状态。响应式编程里，这就是所谓的回弹性（Resilient）。 需要流控的本质原因是，输入请求大于处理能力。

流控有三个级别： 

1. 限流（内部线程数，外部调用数或数据量） 

2. 服务降级（去掉不必要的业务逻辑，只保留核心逻辑） 

3. 过载保护（系统短时间不提供新的业务处理服务，积压处理完后再恢复输入请求）

