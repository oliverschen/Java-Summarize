学习笔记

### 第九课

#### 1. 使用 Java 中的动态代理，实现一个简单的 AOP

通过动态代理，实现对指定方法进行前后拦截，[代码地址](https://github.com/oliverschen/JAVA-000/tree/main/Week_05/homework/jdkproxy/src/main/java/com/github/oliverschen/proxy)

代理类	

```java
/**
 * @author ck
 * Java 动态代理 AOP
 */
public class LogAOP implements InvocationHandler {

    public static void main(String[] args) {
        IOrderService orderService = new OrderServiceImpl();
        IOrderService o = (IOrderService) Proxy.newProxyInstance(
                orderService.getClass().getClassLoader(),
                orderService.getClass().getInterfaces(),
                new LogAOP(orderService)
        );
        o.placeOrder();
    }

    private Object object;

    public LogAOP(Object o) {
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = doBefore();
        Object result = method.invoke(this.object, args);
        doAfter(startTime);
        return result;
    }

    private void doAfter(long startTime) {
        System.out.println();
        System.out.println("AOP 结束调用，总共消耗：" + (System.currentTimeMillis() - startTime) + " ms");
    }

    private long doBefore() {
        long l = System.currentTimeMillis();
        System.out.println("AOP 开始调用时间：" + l);
        System.out.println();
        return l;
    }
}
```

接口

```java
public interface IOrderService {
    /**
     * 下单
     */
    boolean placeOrder();
}
```

实现类

```java
public class OrderServiceImpl implements IOrderService {

    @Override
    public boolean placeOrder() {
        System.out.println("下单中...");
        System.out.println("订单已创建");
        System.out.println("下单完成！");
        return true;
    }
}
```

总结：使用 AOP 能对代码进行无侵入的增强，也是 Spring 核心构成部分之一。

#### 2. 写代码实现 Spring bean 的装配，方式越多越好

总体使用了 XML 和 注解两种方式进行装配，[代码地址](https://github.com/oliverschen/JAVA-000/tree/main/Week_05/homework/spring-bean/src/main/java/com/github/oliverschen/springbean)

1. 使用 XML <beans> 标签，用 ClassPathXmlApplicationContext 进行加载 XML 解析装配获取bean
2. 使用 AnnotationConfigApplicationContext 注解扫描方式，加载 @Componet 注解装配 bean
3. 使用 AnnotationConfigApplicationContext 注解扫描，加载 @Configuration 配合 @Bean 注解进行配置

#### 3. 实现一个 Spring XML 自定义配置，配置一组 bean.

在 application.xml 中进行配置 bean,具体[代码地址](https://github.com/oliverschen/JAVA-000/tree/main/Week_05/homework/spring-bean/src/main/resources)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
                        http://www.springframework.org/schema/context
                        http://www.springframework.org/schema/context/spring-context-3.2.xsd
                        http://www.springframework.org/schema/aop
                        https://www.springframework.org/schema/aop/spring-aop.xsd">
    
    <bean id="student001" class="com.github.oliverschen.springbean.Student">
        <property name="age" value="25" />
        <property name="name" value="ck" />
    </bean>
    
    <bean id="student002" class="com.github.oliverschen.springbean.Student">
        <property name="age" value="24" />
        <property name="name" value="fz" />
    </bean>

    <bean id="school" class="com.github.oliverschen.springbean.School">
        <property name="address" value="深圳"/>
        <property name="schoolName" value="深大"/>
        <property name="student" ref="student001"/>
    </bean>
    <context:component-scan base-package="com.github.oliverschen.springbean" />
</beans>
```

### 第十课

#### 给前面课程提供的 Student 实现自动配置和 starter

用 spring.factories 的方式加载配置项，在配置项中装配需要的 bean，具体[代码](https://github.com/oliverschen/JAVA-000/tree/main/Week_05/homework/boot-starter)

1. 先创建 boot-starter 的模块作为基础模块，改模块自动加载需要的实例对象
2. 将 boot-starter 配置在需要的项目中，进行自动配置使用

在另外一个项目中测试，[代码地址](https://github.com/oliverschen/JAVA-000/blob/main/Week_05/homework/boot-bean/src/test/java/com/github/oliverschen/bootbean/BootBeanApplicationTests.java)

```java
@SpringBootTest
@RunWith(SpringRunner.class)
class BootBeanApplicationTests {

    @Autowired
    @Qualifier("bootUser")
    private User bootUser;

    @Autowired
    @Qualifier("bootSchool")
    private School bootSchool;

    @Autowired
    @Qualifier("bootStudent")
    private Student bootStudent;

    @Test
    void contextLoads() {
        System.out.println(bootUser);
        System.out.println(bootSchool);
        System.out.println(bootStudent);
    }

}
```

#### 研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法

[代码地址](https://github.com/oliverschen/JAVA-000/tree/main/Week_05/homework/boot-bean/src/main/java/com/github/oliverschen/bootbean/jdbc)

单例模式[代码地址](https://github.com/oliverschen/JAVA-000/tree/main/Week_05/homework/boot-bean/src/main/java/com/github/oliverschen/bootbean/singleton)，总的来说如果项目中框架（Spring）提供单例实现的话，尽量依赖框架机制实现，保证程序安全可靠。



