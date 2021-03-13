package com.github.oliverschen.dynamic.aop;

import com.github.oliverschen.dynamic.annotation.Ds;
import com.github.oliverschen.dynamic.config.DataSourceHolder;
import com.github.oliverschen.dynamic.constant.DsType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ck
 */
@Slf4j
@Aspect
@Component
public class DataSourceAop {


    @Around("@annotation(com.github.oliverschen.dynamic.annotation.Ds)")
    public Object changeDataSource(ProceedingJoinPoint point) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            Ds ds = method.getAnnotation(Ds.class);
            log.debug("==> 当前使用的数据源：{}", ds.dsType().getName());
            String key;
            switch (ds.dsType()) {
                case PRIMARY:
                    key = ds.dsType().getName();
                    break;
                case SECONDARY:
                    key = DataSourceHolder.getSecondaryDs();
                    break;
                default:
                    key = DsType.PRIMARY.getName();
            }
            DataSourceHolder.setDataSource(key);
            return point.proceed();
        }finally {
            DataSourceHolder.clearDataSource();
        }
    }

}
