package com.gwang.hotSwap.instrument.byteBuddy.intercepter;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

/**
 * Created by wanggang on 2018/8/6.
 */
public class ExitTemplate {
    @Advice.OnMethodExit
    static void enter(@Advice.This Object self, @Advice.Origin Method method, @Advice.Return Object result) {
        System.out.println("OnMethodExit: " + self.getClass() + " method:" + method.getName());
        System.out.println("result class: " + result.getClass() + " value: " + result.toString());
    }
}
