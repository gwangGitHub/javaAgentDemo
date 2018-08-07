package com.gwang.hotSwap.instrument.agentmain;

import com.gwang.hotSwap.instrument.byteBuddy.listener.ByteBuddyListener;
import com.gwang.hotSwap.instrument.premain.AgentOriginTransformer;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.none;

/**
 * Created by wanggang on 2018/8/5.
 */
public class AgentMainDemo {

    public static final String TRANS_CLASS_END_NAME = "Account";
    public static final String TRANS_CLASS_FULL_NAME = "com.gwang.hotSwap.instrument.agentmain.Account";
    public static final String TRANS_METHOD_NAME = "operation";

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws UnmodifiableClassException {
        System.out.println("Agent Main called");
        System.out.println("agentArgs : " + agentArgs);

        //option1 use byte buddy to transform class
        transWithBytebuddy(inst);
        //option2 use origin instrument to transform class
//        trans(inst);
    }

    private static void transWithBytebuddy(Instrumentation inst) {
        new AgentBuilder
                .Default()
                .ignore(none())
                .disableClassFormatChanges()
//                .with(AgentBuilder.RedefinitionStrategy.REDEFINITION)
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
//                .type(nameEndsWith(TRANS_CLASS_END_NAME)) // 指定需要拦截的类
                .type(named(TRANS_CLASS_FULL_NAME)) // 指定需要拦截的类
                .transform(new AgentMainByteBuddyTransformer(TRANS_METHOD_NAME))
                .with(new ByteBuddyListener())
                .installOn(inst);
    }

    public static void trans(Instrumentation inst) {
        for (Class loadedClass : inst.getAllLoadedClasses()) {
            String loadedClassName = loadedClass.getName();
            if (TRANS_CLASS_FULL_NAME.equals(loadedClassName)) {
                System.out.println("loadedClassName : " + loadedClassName);
                try {
                    inst.addTransformer(new AgentOriginTransformer(), true);
                    inst.retransformClasses(loadedClass);
                } catch (Exception e) {
                    System.out.println("error : " + e.getMessage());
                }
            }
        }
    }
}
