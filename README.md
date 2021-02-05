# Java-Summarize
Java ☃️总结



---

## ☕️JVM

### 介绍 Java 

Java 是一种面向对象、静态类型、编译执行，有 VM/GC 和运行时、跨平台的高级语言。

首先 Java 文件会被 javac 编译成 .class 文件，文件系统将 .class 文件加载到虚拟机，然后根据代码生成 Java 对象实例。

### 字节码

Java 字节码类文件 .class 是Java编译器编译Java源文件 .java 产生的“目标文件”。它是一种8位字节的二进制流文件，其中包好了 魔数，版本信息，常量池，访问限制符，继承类和实现接口信息以及字段，方法，属性等部分。

#### 查看

> javap -c -verbose Hello

### 类加载器

#### 类生命周期

加载 ==> 验证  ==> 准备 ==> 解析 ==> 初始化 ==>  使用 ==> 卸载

#### 三类加载器

BootStrapClassLoader, ExtClasssLoader, AppClassLoader

#### 加载特点

1. 双亲委派：类首先会交由当前加载器的上级加载器，如果到顶级加载器没有加载到当前类，会逐级递减加载，直到加载到此类。
2. 负责依赖
3. 缓存加载

### 内存模型

1. 方法中使用的原生数据类型和对象引用地址在栈上存储；对象、对象成员与类定义、静态变量在堆上。

2. 堆内存又称为“共享堆”，堆中的所有对象，可以被所有线程访问, 只要他们能拿到对象的引用地址。如果一个线程可以访问某个对象时也就可以访问该对象的成员变量。
3. 如果两个线程同时调用某个对象的同一方法，则它们都可以访问到这个对象的成员变量，但每个线程的局部变量副本是独立的。

#### 组成

##### Heap

堆内存：JVM 中内存最大的一块区域，以分代策略组成，有 Young-gen「年轻代」和 Old-gen「老年代」，年轻代由 Eden-Space「新生代」和 S0，S1 区组成。GC 主要回收的就是这块区域，它是所有线程共享的。

##### No-Heap

非堆：Java8 中将 Perm-gen 「永久代」去掉了。非堆主要包括 **Metaspace**「元数据区」，和 Heap 区内存不在连续，开辟在本地内存中，最大可用空间是系统的可用空间。**Compressed Class Space**「类压缩区」如果设置了 UseCompressedClassPointers 参数，会对 Metaspace 和 Compressed class space 进行压缩。**Code Cache** 区：主要用来存放编译后的字节码和 JIT 代码。

##### Stack

虚拟机栈：属于线程独占区，每一个线程都只能访问自己的线程栈。有一个个 Frame/帧 组成。

##### Program Counter Register

程序计数器：线程独占区，主要用来存放当前线程执行的指令或者下一条将要执行的指令

##### Native Stack

本地方法栈：线程独占区，主要用来执行 native 方法。

### GC

#### 总结

1. 串行 GC（Serial GC）: 单线程执行，应用需要暂停；

2. 并行 GC（ParNew、Parallel Scavenge、Parallel Old）: 多线程并行地执行垃圾回收，关注与高吞吐；

3. CMS（Concurrent Mark-Sweep）: 多线程并发标记和清除，关注与降低延迟；

4. G1（G First）: 通过划分多个内存区域做增量整理和回收，进一步降低延迟；

5. ZGC（Z Garbage Collector）: 通过着色指针和读屏障，实现几乎全部的并发执行，几毫秒级别的延迟，线性可扩展；

6. Epsilon: 实验性的 GC，供性能分析使用；

7. Shenandoah: G1 的改进版本，跟 ZGC 类似。

#### GC选择

选择正确的 GC 算法，唯一可行的方式就是去尝试，一般性的指导原则：

1. 如果系统考虑吞吐优先，CPU 资源都用来最大程度处理业务，用 Parallel GC；

2. 如果系统考虑低延迟有限，每次 GC 时间尽量短，用 CMS GC；

3. 如果系统内存堆较大，同时希望整体来看平均 GC 时间可控，使用 G1 GC。对于内存大小的考量：
   1. 一般 4G 以上，算是比较大，用 G1 的性价比较高。
   2. 一般超过 8G，比如 16G-64G 内存，非常推荐使用 G1 GC。

### 调优

#### 高分配速率(High Allocation Rate)

分配速率(Allocation rate)表示单位时间内分配的内存量。通常使用 MB/sec 作为单位。上一次垃圾收集之后，与下一次 GC 开始之前的年轻代使用量，两者的差值除以时间,就是分配速率。分配速率过高就会严重影响程序的性能，在 JVM 中可能会导致巨大的 GC 开销。

>正常系统：分配速率较低 ~ 回收速率 -> 健康
>
>内存泄漏：分配速率 持续大于 回收速率 -> OOM
>
>性能劣化：分配速率较高 ~ 回收速率 -> 亚健康

#### 过早提升(Premature Promotion)

提升速率（promotion rate）用于衡量单位时间内从年轻代提升到老年代的数据量。一般使用 MB/sec 作为单位, 和分配速率类似。JVM 会将长时间存活的对象从年轻代提升到老年代。根据分代假设，可能存在一种情况，老年代中不仅有存活时间长的对象,，也可能有存活时间短的对象。这就是过早提升：对象存活时间还不够长的时候就被提升到了老年代。major GC 不是为频繁回收而设计的，但 major GC 现在也要清

理这些生命短暂的对象，就会导致 GC 暂停时间过长。这会严重影响系统的吞吐量。

### 总结图

<img src="https://github.com/oliverschen/Java-Summarize/blob/main/images/JVM.png" style="zoom:50%" />

### 资料

[Oracle 官方手册](https://www.oracle.com/webfolder/technetwork/tutorials/mooc/JVM_Troubleshooting/week1/lesson1.pdf)



---

## ✈️NIO

### Java Socket编程

使用Socket进行网络编程时，本质上就是两个进程之间的网络通信。其中一个进程必须充当服务器端，它会主动监听某个指定的端口，另一个进程必须充当客户端，它必须主动连接服务器的IP地址和指定端口，如果连接成功，服务器端和客户端就成功地建立了一个TCP连接，双方后续就可以随时发送和接收数据。

### I/O模型

|      |     阻塞      |      非阻塞       |
| :--: | :-----------: | :---------------: |
| 同步 | 阻塞 I/O 模型 |  非阻塞 I/O 模型  |
| 同步 | I/O 复用模型  | 信号驱动 I/O 模型 |
| 异步 |               |   异步 I/O 模型   |

#### 阻塞I/O

阻塞式 I/O 模型，一般通过在循环中等待接收客户端连接的请求，建立连接之后只能等待当前客户端通信完成才能接收其他客户端请求。「可以通过多线程来处理多个客户端连接」

- 等待数据就绪。通过网络 I/O 数据抵达接收端
- 数据拷贝。 将数据从内核态拷贝到用户态

#### 非阻塞I/O

内核会立即返回。用户进程第一个阶段不是阻塞的,需要不断的主动询问 kernel 数据好了没有；第二个阶段依然总是阻塞的。

- 设置状态。将请求设置为「非阻塞状态」，当数据没有准备好时线程不要进入阻塞队列，直接返回状态码 `EWOULDBLOCK` 
- 轮询。轮询查看数据是否准备好
- 数据拷贝。数据准备好时，将数据从内核态拷贝到用户态

#### I/O多路复用

也称事件驱动 I/O：就是在单线程里同时监控多个 Socket，通过 select/poll 函数轮询负责所有的 Socket，当某个 Socket 数据到达了，就通知用户进程。

- 请求先阻塞在 select/poll 上，阻塞多个请求，轮询查看数据是否准备好
- 告知客户端数据已准备好，客户端发起请求获取数据
- 将数据从内核态拷贝到用户态，返回

##### select/poll缺点 

1. 每次调用 select，都需要把 fd 集合从用户态拷贝到内核态，这个开销在 fd 很多时会很大

2. 同时每次调用 select 都需要在**内核遍历传递进来的所有 fd**，这个开销在 fd 很多时也很大

3. select 支持的文件描述符数量太小了，默认是1024

##### epoll

Linux 2.5.44内核中引入,2.6内核正式引入,可被用于代替 POSIX select 和 poll 系统调用

1. 内核与用户空间共享一块内存

2. 通过回调解决遍历问题

4. fd 没有限制，可以支撑10万连接

#### 信号驱动I/O

信号驱动 IO（SIGIO） 与 BIO 和 NIO 最大的区别就在于，在 IO 执行的数据准备阶段，不会阻塞用户进程。

- 客户端发送请求数据的信号，期间不会发生阻塞
- 服务端准备好数据后传递一个信号 SIGIO 给客户端。客户端发起请求获取数据
- 将数据从内核态拷贝到用户态，返回

#### 异步I/O

异步 IO **真正实现**了 IO 全流程的非阻塞。用户进程发出系统调用后立即返回，内核等待数据准备完成，然后将数据拷贝到用户进程缓冲区，然后发送信号告诉用户进程 IO 操作执行完毕「与 SIGIO 相比，一个是发送信号告诉用户进程数据准备完毕，一个是 IO 执行完毕」。

### Netty

Netty 提供**异步**的、**事件驱动**的网络应用程序框架和工具，用以快速开发高性能、高可靠性的网络服务器和客户端程序。也就是说，Netty 是一个**基于 NIO** 的客户，服务器端编程框架，使用Netty 可以确保你快速和简单的开发出一个网络应用，Netty相当简化和流线化了网络应用的编程开发过程，例如，TCP和UDP的socket服务开发。

#### 特性

高性能协议服务器

高吞吐，低延迟，低开销，零拷贝，可扩容，松耦合: 网络和业务逻辑分离，使用方便、可维护性好

#### 基本概念

##### Channel

通道，Java NIO 中的基础概念,代表一个打开的连接,可执行读取/写入 IO 操作。Netty 对 Channel 的所有 IO 操作都是非阻塞的。

##### ChannelFuture

Java 的 Future 接口，只能查询操作的完成情况, 或者阻塞当前线程等待操作完成。Netty 封装一个 ChannelFuture 接口。可以将回调方法传给 ChannelFuture，在操作完成时自动执行。

##### Event&Handler

Netty 基于事件驱动，事件和处理器可以关联到入站和出站数据流。

##### Encoder&Decoder

处理网络 IO 时，需要进行序列化和反序列化, 转换 Java 对象与字节流。对入站数据进行解码, 基类是 ByteToMessageDecoder。对出站数据进行编码, 基类是 MessageToByteEncoder。 

##### ChannelPipeline 

数据处理管道就是事件处理器链。有顺序、同一 Channel 的出站处理器和入站处理器在同一个列表中。

#### 优化

1. 不要阻塞 EventLoop

2. 系统参数优化

> ulimit -a /proc/sys/net/ipv4/tcp_fin_timeout, TcpTimedWaitDelay

3. 缓冲区优化

> SO_RCVBUF/SO_SNDBUF/SO_BACKLOG/ REUSEXXX

4. 心跳频率周期优化

> 心跳机制与断线重连

5. 内存与 ByteBuffer 优化

> DirectBuffer与HeapBuffer

6. 其他优化

> -ioRatio
>
> -Watermark
>
> -TrafficShaping

#### 应用

实现 API 网关

##### 常见网关

zuul ,zuul2 ,Spring Cloud Gateway ,openResty ,Kong 

### 总结图

<img src="https://github.com/oliverschen/Java-Summarize/blob/main/images/NIO.png" style="zoom:50%" />

### 资料

[Essential Netty in Action 《Netty 实战(精髓)》](https://waylau.gitbooks.io/essential-netty-in-action/content/GETTING%20STARTED/Asynchronous%20and%20Event%20Driven.html)



---

## 🚦并发编程

### 多线程

#### 创建

##### 实现 Runnable 接口

run() 方法是线程开启后真正执行的逻辑，跑出异常只能在当前线程中处理，主线程感知不到

##### 继承 Thread 类

Thread 类本身实现了 Runnable 接口，它的 start() 方法调用了 native 方法 start0()，真正开启一个线程。

##### 实现 Callable 接口

V call() throws Exception 方法是开启线程后真正执行的逻辑，它支持返回值，并且可以抛出异常。

#### 线程状态

1. Thread.sleep(long millis)，一定是**当前线程调用**此方法，当前线程进入 `TIMED_WAITING` 状态，但**不释放对象锁**，millis 后线程自动苏醒进入就绪状态。作用：给其它线程执行机会的最佳方式。

2. Thread.yield()，一定是**当前线程调用**此方法，当前线程放弃获取的 CPU 时间片，但**不释放锁资源**，由运行状态变为 `RAEDY`状态，让 OS 再次选择线程。作用：让相同优先级的线程轮流执行，但并不保证一定会轮流执行。实际中**无法保证 yield() 达到让步目的**，因为让步的线程还有可能被线程调度程序再次选中。Thread.yield() 不会导致阻塞。该方法与 sleep() 类似，只是不能由用户指定暂停多长时间。

3. t.join()/t.join(long millis)，当前线程里调用其它线程 t 的 join 方法，当前线程进入`WAITING/TIMED_WAITING` 状态，当前线程**不会释放已经持有的对象锁**。线程 t 执行完毕或者 millis 时间到，当前线程进入 `READY` 状态。

4. obj.wait()，**当前线程调用对象的** wait() 方法，当前线程**释放对象锁**，进入等待队列。依靠 notify()/notifyAll() 唤醒或者 wait(long timeout) timeout 时间到自动唤醒。

5. obj.notify()， 唤醒在此对象监视器上等待的单个线程，选择是任意性的。notifyAll() 唤醒在此对象监视器上等待的所有线程。

##### Java 线程生命周期

<img src="https://cdn.nlark.com/yuque/0/2021/png/2731471/1612507186369-2efb0dea-f3c5-40a8-8c60-7a6f0e5f8522.png" style="zoom:50%" />

#### 中断和异常

1. 线程内部自己处理异常，不溢出到外层。

2. 如果线程被 Object.wait, Thread.join 和 Thread.sleep 三种方法之一阻塞，此时调用该线程的 interrupt() 方法，那么该线程将抛出一个 InterruptedException 中断异常（该线程必须事先预备好处理此异常），从而提早地终结被阻塞状态。如果线程没有被阻塞，这时调用interrupt() 将不起作用，直到执行到 wait(),sleep(),join() 时,才马上会抛出 InterruptedException。

3. 如果是计算密集型的操作：分段处理，每个片段检查一下状态，是不是要终止。

#### 并发性质

##### 原子性

原子操作，注意跟事务 ACID 里原子性的区别与联系,对基本数据类型的变量的读取和赋值操作是原子性操作，即这些操作是不可被中断的，

要么执行，要么不执行。

##### 可见性

对于可见性，Java 提供了 volatile 关键字来保证可见性。当一个共享变量被 volatile 修饰时，它会保证修改的值会立即被更新到主存，当有其他线程需要读取时，它会去内存中读取新值。另外，通过 synchronized 和 Lock 也能够保证可见性，synchronized 和 Lock 能保证同一时刻只有一个线程获取锁然后执行同步代码，并且在释放锁之前会将对变量的修改刷新到主存当中。**volatile 并不能保证原子性。**

##### 有序性

Java 允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性。可以通过 volatile 关键字来保证一定的“有序性”（synchronized 和 Lock也可以）。

##### happens-before 原则（先行发生原则）

1. 程序次序规则：一个线程内，按照代码先后顺序

2. 锁定规则：一个 unLock 操作先行发生于后面对同一个锁的 lock 操作

3. volatile 变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作

4. 传递规则：如果操作 A 先行发生于操作 B，而操作 B 又先行发生于操作 C，则可以得出 A 先于 C

5. 线程启动规则：Thread 对象的 start() 方法先行发生于此线程的每个一个动作

6. 线程中断规则：对线程 interrupt() 方法的调用先行发生于被中断线程的代码检测到中断事件的发生

7. 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过 Thread.join() 方法结束、Thread.isAlive() 的返回值手段检测到线程已经终止执行

8. 对象终结规则：一个对象的初始化完成先行发生于他的 finalize() 方法的开始

### 线程池

#### 结构

1. Excutor: 执行者 – 顶层接口

2. ExcutorService: 接口 API
   1. ThreadPoolExecutor：普通线程池
   2. ScheduledThreadPoolExecutor：任务线程池

3. ThreadFactory: 线程工厂

4. Excutors: 工具类

#### 线程池参数

##### 缓冲队列

BlockingQueue 是**双缓冲队列**。BlockingQueue 内部使用两条队列，允许两个线程同时向队列一个存储，一个取出操作。在保证并发安全的同时，提高了队列的存取效率。

1. ArrayBlockingQueue:规定大小的 BlockingQueue，其构造必须指定大小。其所含的对象是 FIFO 顺序排序的。

2. LinkedBlockingQueue:大小不固定的 BlockingQueue，若其构造时指定大小，生成的 BlockingQueue 有大小限制，不指定大小，其大小有 Integer.MAX_VALUE 来决定。其所含的对象是 FIFO 顺序排序的。

3. PriorityBlockingQueue:类似于 LinkedBlockingQueue，但是其所含对象的排序不是 FIFO，而是依据对象的自然顺序或者构造函数的 Comparator 决定。

4. SynchronizedQueue:特殊的 BlockingQueue，对其的操作必须是放和取交替完成。

##### 拒绝策略

1. ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出 RejectedExecutionException 异常。

2. ThreadPoolExecutor.DiscardPolicy：丢弃任务，但是不抛出异常。

3. ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新提交被拒绝的任务

4. ThreadPoolExecutor.CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务

##### 创建线程池方法

###### newSingleThreadExecutor

创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程池保证所有任务的执行顺序按照任务的提交顺序执行。

###### newFixedThreadPool

创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。

###### newCachedThreadPool

创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添 加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说 JVM）能够创建的最大线程大小。

###### newScheduledThreadPool

创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。

### JUC

#### 常用特性

1. 锁机制类 Locks : Lock, Condition, ReadWriteLock

2. 原子操作类 Atomic : AtomicInteger

3. 线程池相关类 Executer : Future, Callable, Executor

4. 信号量三组工具类 Tools : CountDownLatch, CyclicBarrier, Semaphore

5. 并发集合类 Collections : CopyOnWriteArrayList, ConcurrentMap

#### 锁

##### synchronized 问题

1. 同步块的阻塞无法中断（不能 Interruptibly） 

2. 同步块的阻塞无法控制超时（无法自动解锁）

3. 同步块无法异步处理锁（即不能立即知道是否可以拿到锁）

4. 同步块无法根据条件灵活的加锁解锁（即只能跟同步块范围一致）

##### Lock

1. 使用方式灵活可控

2. 性能开销小

3. 锁工具包: java.util.concurrent.locks

##### 用锁的最佳实践

Doug Lea《Java 并发编程：设计原则与模式》一书中，推荐的三个用锁的最佳实践，它们分别是：

1. 永远只在更新对象的成员变量时加锁

2. 永远只在访问可变的成员变量时加锁

3. 永远不在调用其他对象的方法时加锁

秦老师总结-最小使用锁：

1. 降低锁范围：锁定代码的范围/作用域

2. 细分锁粒度：讲一个大锁，拆分成多个小锁

#### 原子类

##### 无锁技术

无锁技术的底层实现原理

1. Unsafe API - Compare-And-Swap

2. CPU 硬件指令支持: CAS 指令

核心实现原理：

1. volatile 保证读写操作都可见（注意不保证原子）

2. 使用 CAS 指令，作为乐观锁实现，通过自旋重试保证写入

并发压力跟锁性能的关系

1. 压力非常小，性能本身要求就不高；

2. 压力一般的情况下，无锁更快，大部分都一次写入；

3. 压力非常大时，自旋导致重试过多，资源消耗很大。

#### 工具类

##### AQS

AbstractQueuedSynchronizer，即队列同步器。它是构建锁或者其他同步组件的基础（如Semaphore、CountDownLatch、ReentrantLock、ReentrantReadWriteLock），是JUC并发包中的核心基础组件。抽象队列式的同步器，两种资源共享方式: 独占 | 共享，子类负责实现公平 OR 非公平

##### Semaphore

准入数量 N，N =1 则等价于独占锁。场景：同一时间控制并发线程数

##### CountdownLatch

使用**减数**操作实现。场景：Master 线程等待 Worker 线程把任务执行完

##### CyclicBarrier

使用**加数**操作实现。场景: 任务执行到一定阶段, 等待其他任务对齐。

#### 线程安全容器

##### CopyOnWriteArrayList

读写分离，最终一致

1. 写加锁，保证不会写混乱

2. 写在一个 Copy 副本上，而不是原始数据上（GC young 区用复制，old 区用本区内的移动）

##### ConcurrentHashMap

采用了**CAS + synchronized**来保证线程安全。

##### ThreadLocal

线程本地变量，场景: 每个线程一个副本， 不改方法签名静默传参，及时进行清理，存在内存泄漏风险。

### 总结图

<img src="https://github.com/oliverschen/Java-Summarize/blob/main/images/并发编程.png" style="zoom:50%" />



---

## 🧩框架

### Spring

#### 模块

| 模块              | 组件     | 组件        | 组件 |
| ----------------- | -------- | ----------- | ---- |
| Core              | Bean     | Context     | AOP  |
| Testing           | Mock     | TestContext |      |
| DataAccess        | Tx       | JDBC        | ORM  |
| SpringMVC/WebFlux | Web      |             |      |
| Integration       | remoting | JMS         | WS   |
| Languages         | Kotlin   | Groovy      |      |

#### AOP

Spring 早期版本的核心功能，管理对象生命周期与对象装配。 为了实现管理和装配，一个自然而然的想法就是，加一个中间层代理（字节码增强）来实现所有对象的托管。Spring 核心功能之一，接口类型默认使用 JDK 动态代理实现，非接口类型默认使用 CGlib 实现。 

##### 流程

<img src="https://cdn.nlark.com/yuque/0/2020/png/2731471/1608868731344-d57c3210-abe3-40d6-96d4-7b108a39c7cd.png?x-oss-process=image%2Fresize%2Cw_2062" style="zoom:50%" />

#### IoC

控制反转，也称为DI（Dependency Injection）依赖注入。对象装配思路的改进。 从对象 A 直接引用和操作对象 B，变成对象A里指需要依赖一个接口IB，系统启动和装配阶段，把 IB 接口的实例对象注入到对象 A，这样 A 就不需要依赖一个 IB 接口的具体实现，也就是类B。 从而可以实现在不修改代码的情况，修改配置文件，即可以运行时替换成注入IB接口另一实现类C的一个对象实例。

##### 流程

<img src="https://cdn.nlark.com/yuque/0/2020/png/2731471/1608736702583-7e2284b5-ccb4-4a27-9f19-0ff33683e487.png?x-oss-process=image%2Fresize%2Cw_2062" style="zoom:50%" />

#### Bean

##### 生命周期

<img src="https://cdn.nlark.com/yuque/0/2021/png/2731471/1612514147347-8de360d0-548c-4cc9-afac-5301027077c5.png" style="zoom:50%"/>

##### 记载过程

```json
1. 创建对象 ==> 属性赋值 ==> 初始化* ==> 注销接口注册

2. 初始化 ==> 检查 Aware 装配 ==> 前置处理、After处理 ==> 调用init method ==> 后置处理
```

#### SpringBoot

Spring 臃肿以后的必然选择。一切都是为了简化。约定大于配置。

- 让开发变简单
- 让配置变简单

- 让运行变简单

##### 特性

1. 创建独立运行的 Spring 应用

2. 直接嵌入 Tomcat 或 Jetty，Undertow，无需部署 WAR 包

3. 提供限定性的 starter 依赖简化配置（就是脚手架）

4. 在必要时自动化配置 Spring 和其他三方依赖库

5. 提供生产 production-ready 特性，例如指标度量，健康检查，外部配置等

6. 完全零代码生产和不需要 XML 配置

##### 核心原理

1. 自动化配置：简化配置核心基于 Configuration，EnableXX，Condition

2. spring-boot-starter：脚手架核心整合各种第三方类库，协同工具

##### 自动装配原理

`@SpringBootApplication`注解，SpringBoot 应用标注在某个类上说明这个类是 SpringBoot 的主配置类，SpringBoot 就会运行这个类的 main 方法来启动 SpringBoot 项目。它是复合注解，包含下面几个注解：

@SpringBootConfiguration：表明是一个配置类「封装了 @Configuration」

@AutoConfigurationPackage：自动配置扫描包路径

@EnableAutoConfiguration：自动配置类，倒入自动配置类 AutoConfigurationImportSelector  加载所有 META-INF/spring.factories 中存在的配置类（类似 SpringMVC 中加载所有 converter）

#### ORM

ORM（Object-Relational Mapping） 表示对象关系映射。

##### Hibernate

Hibernate 是一个开源的对象关系映射框架，它对 JDBC 进行了非常轻量级的对象封装，它将 POJO 与数据库表建立映射关系，是一个全自动的 orm 框架，hibernate 可以自动生成 SQL 语句，自动执行，使得 Java 程序员可以使用面向对象的思维来操纵数据库。

Hibernate 里需要定义实体类和 hbm 映射关系文件（IDE 一般有工具生成）。Hibernate 里可以使用 HQL、Criteria、Native SQL三种方式操作数据库。也可以作为 JPA 适配实现，使用 JPA 接口操作。

##### MyBatis

MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java 对象)映射成数据库中的记录。

##### MyBatis&Hibernate比较

MyBatis 与 Hibernate 的区别与联系？

1. Mybatis 优点：原生 SQL（XML 语法），直观，对 DBA 友好

2. Hibernate 优点：简单场景不用写 SQL（HQL、Cretiria、SQL）

3. Mybatis 缺点：繁琐，可以用 MyBatis-generator、MyBatis-Plus 之类的插件

4. Hibernate 缺点：对 DBA 不友好

##### JPA

JPA 的全称是 Java Persistence API， 即 Java 持久化 API，是一套基于 ORM 的规范，内部是由一系列的接口和抽象类构成。JPA 通过 JDK 5.0 注解描述对象-关系表映射关系，并将运行期的实体对象持久化到数据库中。核心 EntityManager

#### Java8

##### Lambda

Lambda 表达式（lambda expression）是一个匿名函数，Lambda 表达式基于数学中的λ演算得名，直接对应于其中的 lambda 抽象（lambda abstraction），是一个匿名函数，即没有函数名的函数。

##### Stream

Stream（流）是一个来自数据源的元素队列并支持聚合操作

1. 元素：特定类型的对象，形成一个队列。 Java 中的 Stream 并不会存储元素，而是按需计算。

2. 数据源：流的来源。 可以是集合，数组，I/O channel， 产生器 generator 等。

3. 聚合操作 类似 SQL 语句一样的操作， 比如 filter, map, reduce, find, match, sorted 等。
4. Pipelining：中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格(fluent style)。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路((shortcircuiting)。 
5. 内部迭代：以前对集合遍历都是通过 Iterator 或者 For-Each 的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream 提供了内部迭代的方式， 通过访问者模式(Visitor)实现。

#### Guava

Guava 是一种基于开源的 Java 库，其中包含谷歌正在由他们很多项目使用的很多核心库。这个库是为了方便编码，并减少编码错误。这个库提供用于集合，缓存，支持原语，并发性，常见注解，字符串处理，I/O 和验证的实用方法。JDK8 里的一些新特性源于 Guava。

##### Collections

Guava 对 JDK 集合的扩展，这是 Guava 最成熟和为人所知的部分

1. 不可变集合: 用不变的集合进行防御性编程和性能提升。

2. 新集合类型: multisets, multimaps, tables, bidirectional maps 等

3. 强大的集合工具类: 提供 java.util.Collections 中没有的集合工具

4. 扩展工具类：让实现和扩展集合类变得更容易，比如创建 Collection 的装饰器，或实现迭代器

##### Caches

本地缓存实现，支持多种缓存过期策略

##### Concurrency

ListenableFuture：完成后触发回调的 Future

##### 字符串处理[Strings]

非常有用的字符串工具，包括分割、连接、填充等操作

##### 事件总线[EventBus]

发布-订阅模式的组件通信，进程内模块间解耦

##### 反射[Reflection]

Guava 的 Java 反射机制工具类

#### 设计原则

S.O.L.I.D 是面向对象设计和编程(OOD&OOP)中几个重要编码原则(Programming Priciple)的首字母缩写。

1. SRP：The Single Responsibility Principle 单一责任原则

2. OCP：The Open Closed Principle 开放封闭原则

3. LSP：The Liskov Substitution Principle 里氏替换原则

4. ISP：The Interface Segregation Principle 接口分离原则

5. DIP：The Dependency Inversion Principle 依赖倒置原则

最小知识原则，KISS，高内聚低耦合



---

## 🗄Mysql

### 关系数据库

#### 设计范式

第一范式（1NF）：关系 R 属于第一范式，当且仅当R中的每一个属性A的值域只包含原子项

第二范式（2NF）：在满足 1NF 的基础上，消除非主属性对码的部分函数依赖

第三范式（3NF）：在满足 2NF 的基础上，消除非主属性对码的传递函数依赖

BC 范式（BCNF）：在满足 3NF 的基础上，消除主属性对码的部分和传递函数依赖

第四范式（4NF）：消除非平凡的多值依赖

第五范式（5NF）：消除一些不合适的连接依赖

### SQL

结构化查询语言包含 6 个部分

1. 数据查询语言（**DQL**: Data Query Language）：其语句，也称为“数据检索语句”，用以从表中获得数据，确定数据怎样在应用程序给出。保留字 SELECT 是 DQL（也是所有 SQL）用得最多的动词，其他 DQL 常用的保留字有 WHERE，ORDER BY，GROUP BY 和 HAVING。这些 DQL 保留字常与其它类型的 SQL 语句一起使用。

2. 数据操作语言（**DML**：Data Manipulation Language）：其语句包括动词 INSERT、UPDATE 和 DELETE。分别用于添加、修改和删除。

3. 事务控制语言（**TCL**）：它的语句能确保被 DML 语句影响的表的所有行及时得以更新。包括COMMIT（提交）命令、SAVEPOINT（保存点）命令、ROLLBACK（回滚）命令。

4. 数据控制语言（**DCL**）：它的语句通过 GRANT 或 REVOKE 实现权限控制，确定单个用户和用户组对数据库对象的访问。某些 RDBMS 可用 GRANT 或 REVOKE 控制对表单个列的访问。

5. 数据定义语言（**DDL**）：其语句包括动词 CREATE,ALTER 和 DROP。在数据库中创建新表或修改、删除表（CREAT TABLE 或 DROP TABLE）；为表加入索引等。

6. 指针控制语言（CCL）：像 DECLARE CURSOR，FETCH INTO 和 UPDATE WHERE CURRENT 用于对一个或多个表单独行的操作。

### Mysql原理

#### 执行流程

##### server层

###### 连接器

管理连接，权限验证，会查找缓存，如果有直接返回

###### 分析器

词法分析，语法分析，会查找缓存，如果有直接返回

###### 优化器

执行计划生产，索引选择

###### 执行器

操作引擎，返回结果

##### 引擎层

InnoDB，MyISAM等存储引擎

##### 索引原理

InnoDB 使用 B+ 树实现聚集索引，数据是按页来分块的，当一个数据被用到时，其附近的数据也通常会马上被使用。

###### InnoDB

1. 聚簇索引：树的叶子节点包含所有数据，典型就是 mysql 主键索引。

###### 问题

1. InnoDB 必须有主键，因为它存储的数据在主键 B+tree 的叶子节点

2. 推荐用整型的自增主键，因为 mysql 主键默认排序存储的，如果使用 UUID 之类的作为主键会造成数据页分裂造成空间利用率下降

###### 稀疏索引（非聚簇索引）

树上没有包含任何数据，只有只想数据列的指针，典型 mysql myISAM 引擎逐渐索引

###### 联合索引

多个字段组成的索引结构

###### 索引优化

1. 全值匹配
2. 最左前缀原则
3. 不在索引列上做任何操作（计算、函数、（自动or手动）类型转换），会导致索引失效而转 向全表扫描

4. 存储引擎不能使用索引中范围条件右边的列 

5. 尽量使用覆盖索引（只访问索引的查询（索引列包含查询列）），减少select *语句 

6. mysql 在使用不等于（！=或者<>）的时候无法使用索引会导致全表扫描，is null,is not null 也无法使用索引

7. like以通配符开头（'$abc...'）mysql索引失效会变成全表扫描操作

8. 字符串不加单引号索引失效，隐式转换问题

9. 少用or或in，用它查询时，mysql不一定使用索引，mysql内部优化器会根据检索比例、 表大小等多个因素整体评估是否使用索引

### 事务

#### 事务可靠性模型 ACID:

Atomicity: 原子性, 一次事务中的操作要么全部成功, 要么全部失败。

Consistency: 一致性, 跨表、跨行、跨事务, 数据库始终保持一致状态。

Isolation: 隔离性, 可见性, 保护事务不会互相干扰, 包含4种隔离级别。

Durability:, 持久性, 事务提交成功后,不会丢数据。如电源故障, 系统崩溃。

InnoDB:  双写缓冲区、故障恢复、操作系统、fsync() 、磁盘存储、缓存、UPS、网络、备份策略

#### 锁

##### 表级锁

1. 意向锁: 表明事务稍后要进行哪种类型的锁定（加表锁时不会发生遍历行查看是否持有行锁）

   1. 共享意向锁(IS): 打算在某些行上设置共享锁

   2. 排他意向锁(IX): 打算对某些行设置排他锁

2. Insert 意向锁: Insert 操作设置的间隙锁

3. 自增锁(AUTO-IN)

4. LOCK TABLES/DDL
5. MDL：MetaDate Lock

##### 行级锁(InnoDB)

1. 记录锁(Record): 始终锁定索引记录，注意隐藏的聚簇索引; 

2. 间隙锁(Gap): 

3. 临键锁(Next-Key): 记录锁+间隙锁的组合; 可“锁定”表中不存在记录

4. 谓词锁(Predicat): 空间索引

#### 事务隔离级别(Isolation)

1. 读未提交: READ UNCOMMITTED

2. 读已提交: READ COMMITTED

3. 可重复读: REPEATABLE READ ✅

4. 可串行化: SERIALIZABLE

事务隔离是数据库的基础特征。

##### MySQL

1. 可以设置全局的默认隔离级别

2. 可以单独设置会话的隔离级别

3. InnoDB 实现与标准之间的差异

###### Mysql事务

1. 未提交: READ UNCOMMITTED

很少使用，不能保证一致性，可能的问题: 脏读、幻读、不可重复读

2. 读已提交: READ COMMITTED

每次查询都会设置和读取自己的新快照，仅支持基于行的 bin-log。UPDATE 优化: 半一致读(semi-consistent read)

不可重复读: 不加锁的情况下, 其他事务 UPDATE 或 DELETE 会对查询结果有影响

幻读(Phantom): 加锁后, 不锁定间隙, 其他事务可以 INSERT。

3. 可重复读: REPEATABLE READ

InnoDB 的默认隔离级别，使用事务第一次读取时创建的快照，多版本技术

4. 串行化: SERIALIZABLE

最严格的级别，事务串行执行，资源消耗最大；

##### 日志

###### undo log

撤消日志，保证事务的原子性。用处: **事务回滚, 一致性读、崩溃恢复**。

记录事务回滚时所需的撤消操作，一条 INSERT 语句，对应一条 DELETE 的 undo log，每个 UPDATE 语句，对应一条相反 UPDATE 的 undo log

###### redo log

重做日志，确保事务的**持久性**，防止事务提交后数据未刷新到磁盘就掉电或崩溃。

1. 事务执行过程中写入 redo log,记录事务对数据页做了哪些修改。

2. 提升性能: WAL(Write-Ahead Logging) 技术, 先写日志, 再写磁盘。

3. 日志文件: ib_logfile0, ib_logfile1

4. 日志缓冲: innodb_log_buffer_size

5. 强刷: fsync()

##### MVCC

多版本并发控制，使 InnoDB 支持一致性读: READ COMMITTED 和 REPEATABLE READ 。 让查询不被阻塞、无需等待被其他事务持有的锁，这种技术手段可以增加并发性能。

###### 流程（查询）

当执行查询 SQL 时会生成一致性视图 read-view，它由执行查询时所有未提交事务 ID 数组（数组里最小 ID 为 min_ID）和已创建的最大事务 ID（max_ID）组成，查询的数据结果要跟 read-view 做比对从而得到快照结果

#### 总结图

<img src="" style="zoom:50%" />



---

## 🎯分库分表

