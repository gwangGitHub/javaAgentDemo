package com.gwang.hotSwap.instrument.agentmain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

/**
 * Created by wanggang on 2018/8/5.
 */
public class AgentMainDemo {
    public static void agentmain(String agentArgs, Instrumentation inst)
            throws UnmodifiableClassException {
        System.out.println("Agent Main called");
        System.out.println("agentArgs : " + agentArgs);
        for (Class loadedClass : inst.getAllLoadedClasses()) {
            String loadedClassName = loadedClass.getName();
            if ("Account".equals(loadedClassName)) {
                System.out.println("loadedClassName : " + loadedClassName);
                try {
                    inst.addTransformer(new ClassFileTransformer() {
                        @Override
                        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                                ProtectionDomain protectionDomain, byte[] classfileBuffer)
                                throws IllegalClassFormatException {
                            System.out.println("transform loaded Class  :" + className);
                            return classfileBuffer;
                        }
                    }, true);
//                    inst.addTransformer(new AopAgentTransformer(), true);
                    inst.retransformClasses(loadedClass);
                } catch (Exception e) {
                    System.out.println("error : " + e.getMessage());
                }
            }
        }
    }
}
