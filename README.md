# Java-Summarize
Java ☃️总结



---

## JVM

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

## NIO

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

