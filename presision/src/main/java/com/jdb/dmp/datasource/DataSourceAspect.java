package com.jdb.dmp.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;

/**
 * Created by qimwang on 7/18/16.
 */
@Aspect
public class DataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);


    @Before("execution(* com.jdb.dmp.dao..*.*(..))")
    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] clazz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = clazz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.setDataSource(data.value());
                if (log.isDebugEnabled()) {
                    log.debug("method {} use {} data source", new Object[]{m.getName(), data.value()});
                }
            }
        } catch (Exception ex) {
            //log.warn("occurs exception", ex);
            DynamicDataSourceHolder.setDataSource(DynamicDataSourceHolder.DMP_DATA_SOURCE);
        }
    }
}
