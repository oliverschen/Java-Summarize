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