学习笔记

（必做）思考有多少种方式，在main函数启动一个新线程或线程池，异步运行一个方法，拿到这个方法的返回值后，退出主线程？

以下[代码地址](https://github.com/oliverschen/Java-Summarize/tree/main/weeks/Week_04/homework/src/main/java/com/github/oliverschen)

基础抽象类

```java
public abstract class AbstractBase {

    public abstract int asyncInvoke() throws Exception;

    public void template() {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        int result = 0;
        try {
            result = asyncInvoke();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    public static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    /**
     * 自定义线程池
     */
    public ThreadPoolExecutor getPool(Integer poolSize, Integer queueSize) {
        AtomicInteger num = new AtomicInteger(0);
        return new ThreadPoolExecutor(1, poolSize, 60L, SECONDS,
                new LinkedBlockingDeque<>(queueSize),
                r -> {
                    Thread t = new Thread(r);
                    t.setName("my-" + num.incrementAndGet());
                    t.setDaemon(false);
                    return t;
                });
    }

}
```

1. CompletableFuture 实现

   ```java
   public class V1 extends AbstractBase{
   
       public static void main(String[] args) {
           new V1().template();
       }
   
       @Override
       public int asyncInvoke() throws Exception {
           CompletableFuture<Integer> future = CompletableFuture.supplyAsync(AbstractBase::sum);
           return future.get();
       }
   }
   ```

2. FutureTask 实现

   ```java
   public class V2 extends AbstractBase{
   
       public static void main(String[] args) {
           new V2().template();
       }
   
       @Override
       public int asyncInvoke() throws Exception {
           FutureTask<Integer> task = new FutureTask<>(AbstractBase::sum);
           new Thread(task).start();
           return task.get();
       }
   }
   ```

3. CountDownLatch 实现

   ```java
   public class V3 extends AbstractBase{
   
       public static void main(String[] args) {
           new V3().template();
       }
   
       @Override
       public int asyncInvoke() throws Exception {
           CountDownLatch latch = new CountDownLatch(1);
           AtomicInteger result = new AtomicInteger();
           new Thread(() -> {
               try {
                   result.set(sum());
               }finally {
                   latch.countDown();
               }
           }).start();
           latch.await();
           return result.get();
       }
   }
   ```

4. CyclicBarrier 实现

   ```java
   public class V4 extends AbstractBase {
   
       public static void main(String[] args) {
           new V4().template();
       }
   
       @Override
       public int asyncInvoke() throws Exception {
           AtomicInteger result = new AtomicInteger();
           CyclicBarrier barrier = new CyclicBarrier(2);
           new Thread(() -> {
               try {
                   result.set(sum());
                   barrier.await();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               } catch (BrokenBarrierException e) {
                   e.printStackTrace();
               }
           }).start();
           barrier.await();
           return result.get();
       }
   }
   ```

5. Semaphore 实现，这个实现感觉不是很理想，最后使用了 join() 操作

   ```java
   public class V5 extends AbstractBase {
   
       public static void main(String[] args) {
           new V5().template();
       }
   
       @Override
       public int asyncInvoke() throws Exception {
           Semaphore semaphore = new Semaphore(1);
           AtomicInteger result = new AtomicInteger();
           Thread thread = new Thread(() -> {
               try {
                   semaphore.acquire(1);
                   result.set(sum());
               } catch (InterruptedException e) {
                   e.printStackTrace();
                   Thread.currentThread().interrupt();
               } finally {
                   semaphore.release();
               }
           });
           thread.start();
           thread.join();
           return result.get();
       }
   }
   ```

6. wait & notify 实现

   ```java
   public class V6 extends AbstractBase {
   
       public static void main(String[] args) {
           new V6().template();
       }
   
       @Override
       public int asyncInvoke() throws Exception {
           Object o = new Object();
           AtomicInteger result = new AtomicInteger();
           new Thread(() -> {
               synchronized (o) {
                   result.set(sum());
                   o.notify();
               }
           }).start();
           synchronized (o) {
               o.wait();
           }
           return result.get();
       }
   }
   ```

7. Future 实现

   ```java
   public class V7 extends AbstractBase {
   
       public static void main(String[] args) {
           new V7().template();
       }
   
       @Override
       public int asyncInvoke() throws Exception {
           ThreadPoolExecutor pool = getPool(1, 1);
           Future<Integer> result = pool.submit(AbstractBase::sum);
           return result.get();
       }
   }
   ```

8. join 实现

   ```java
   public class V8 extends AbstractBase {
   
       public static void main(String[] args) {
           new V8().template();
       }
   
       @Override
       public int asyncInvoke() throws Exception {
           AtomicInteger result = new AtomicInteger();
           Thread thread = new Thread(() -> result.set(sum()));
           thread.start();
           thread.join();
           return result.get();
       }
   }
   ```

9. LockSupport 实现

   ```java
   public class V9 extends AbstractBase {
   
       public static void main(String[] args) {
           new V9().template();
       }
   
       @Override
       public int asyncInvoke() throws Exception {
           AtomicInteger result = new AtomicInteger();
           Thread main = Thread.currentThread();
           new Thread(() -> {
               try {
                   result.set(sum());
               }finally {
                   LockSupport.unpark(main);
               }
           }).start();
           LockSupport.park();
           return result.get();
       }
   }
   ```

10. BlockingQueue 实现

    ```java
    public class V10 extends AbstractBase{
    
        public static void main(String[] args) {
            new V10().template();
        }
    
        @Override
        public int asyncInvoke() throws Exception {
            BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
            new Thread(() -> {
                try {
                    queue.put(sum());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }).start();
            return queue.take();
        }
    }
    ```

11. Lock & Condition 实现

    ```java
    public class V11 extends AbstractBase {
    
        public static void main(String[] args) {
            new V11().template();
        }
    
        @Override
        public int asyncInvoke() throws Exception {
            Lock lock = new ReentrantLock();
            Condition condition = lock.newCondition();
            AtomicInteger result = new AtomicInteger();
            new Thread(() -> {
               try {
                   lock.lock();
                   result.set(sum());
                   condition.signal();
               }finally {
                   lock.unlock();
               }
            }).start();
            try {
                lock.lock();
                condition.await();
            }finally {
                lock.unlock();
            }
            return result.get();
        }
    }
    ```

多线程和并发思维导图

![多线程和并发](https://github.com/oliverschen/Java-Summarize/blob/main/weeks/Week_04/多线程%26并发.png)