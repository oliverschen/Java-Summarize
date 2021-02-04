# Java-Summarize
Java ☃️总结

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

## NIO

