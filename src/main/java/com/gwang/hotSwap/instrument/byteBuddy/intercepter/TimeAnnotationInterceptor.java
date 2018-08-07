package com.gwang.hotSwap.instrument.byteBuddy.intercepter;

import com.gwang.hotSwap.instrument.byteBuddy.annotation.TraceTime;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by wanggang on 2018/8/5.
 */
public class TimeAnnotationInterceptor {
    @RuntimeType
    public static Object interceptor(@Origin Class clazz,
                                     @Origin Method method,
                                     @SuperCall Callable<?> callable) throws Exception {

        TraceTime traceTime = method.getAnnotation(TraceTime.class);

        if (traceTime == null) {
            return callable.call();
        }

        long start = System.currentTimeMillis();
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println(clazz.getSimpleName() + ":" + method.getName() + " cost " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
