package com.gwang.hotSwap.instrument.byteBuddy.intercepter;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by wanggang on 2018/8/5.
 */
public class TimeInterceptor {
    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println("TimeInterceptor method:" + method + ": took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
