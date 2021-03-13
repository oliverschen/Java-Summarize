学习笔记

网关改造整体结构图

![](https://github.com/oliverschen/JAVA-000/blob/main/Week_03/gateway.png)

1. 改造 client 返回真实从服务器请求到的内容

改造代码[地址](https://github.com/oliverschen/JAVA-000/tree/main/Week_03/week03/client) ，client 访问网关 gateway，gateway 在去访问具体的服务，将请求分发到具体的服务上。

2. 用 netty 实现一个客户端

新增 netty [客户端](https://github.com/oliverschen/JAVA-000/blob/main/Week_03/week03/client/src/main/java/io/gihub/oliverschen/netty/NettyClient.java)，访问 gateway 网关。

3. 对请求添加 request header

当客户端的请求到达 gateway 之后，新增一个 handler 统一对请求头新增一个特定的键值对，具体代码[地址](https://github.com/oliverschen/JAVA-000/blob/main/Week_03/week03/netty-gateway/src/main/java/io/github/oliverschen/gateway/inbound/PerRequestHandler.java)

4. 新增一个随机路由算法，将请求随机分法到后端服务器上

请求到达 gateway 之后，新增一个 router 随机获取后端服务地址，这里不太好的是后端服务是写死在代码中的，后面有时间可以实现通过配置获取等方式，而且服务也没有心跳检查，当服务不可用时，随机到的服务请求也会被打到这台已经宕机的服务，这个也可以作为一个完善的点等待后期实现。具体代码[地址](https://github.com/oliverschen/JAVA-000/blob/main/Week_03/week03/netty-gateway/src/main/java/io/github/oliverschen/gateway/router/RibbonRouter.java)

