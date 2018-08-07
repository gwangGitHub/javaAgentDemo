package com.gwang.hotSwap.instrument.byteBuddy.intercepter;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

/**
 * Created by wanggang on 2018/8/6.
 */
public class EnterTemplate {
    @Advice.OnMethodEnter
    static void enter(@Advice.This Object self, @Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
        System.out.println("OnMethodEnter: " + self.getClass() + " method:" + method.getName());
        for (Object argument : arguments) {
            System.out.println("argument class: " + argument.getClass() + " value: " + argument.toString());
        }
//        new RuntimeException("not call method: " + method).getMessage();
    }
}
