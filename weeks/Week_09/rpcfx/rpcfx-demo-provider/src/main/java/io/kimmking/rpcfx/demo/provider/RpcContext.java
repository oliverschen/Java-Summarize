package io.kimmking.rpcfx.demo.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过注解识别 rpcService 注解标记的类，放在 map 中
 *
 * @author ck
 */
@Slf4j
@Component
public class RpcContext implements BeanPostProcessor {

    /**
     * 保存 RPC bean
     * Map<Interface name,Obeject>
     */
    private static final Map<String, Object> RPC_BEAN_MAP = new ConcurrentHashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Service annotation = bean.getClass().getAnnotation(Service.class);
        if (annotation != null) {
            log.info("当前 bean：[{}] 开始注入", beanName);
            Arrays.stream(bean.getClass().getInterfaces())
                    .forEach(itf -> RPC_BEAN_MAP.put(itf.getName(),bean));
        }
        return bean;
    }

    /**
     * get rpc bean
     */
    public static Object getBean(String beanName) {
        Object o = RPC_BEAN_MAP.get(beanName);
        if (o == null) {
            log.error("not found rpc bean {}", beanName);
        }
        return o;
    }

}
