package com.gwang.hotSwap.instrument.premain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by wanggang on 2018/8/7.
 */
public class AgentOriginTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("AgentOriginTransformer load Class:  " + className);
        return classfileBuffer;
    }
}
