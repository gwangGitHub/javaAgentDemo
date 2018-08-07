package com.gwang.hotSwap.instrument.byteBuddy.listener;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

/**
 * Created by wanggang on 2018/8/7.
 */
public class ByteBuddyListener implements AgentBuilder.Listener {
    /**
     * Invoked right before a successful transformation is applied.
     *
     * @param typeDescription The type that is being transformed.
     * @param classLoader     The class loader which is loading this type.
     * @param module          The transformed type's module or {@code null} if the current VM does not support modules.
     * @param dynamicType     The dynamic type that was created.
     */
    @Override
    public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, DynamicType dynamicType) {

    }

    /**
     * Invoked when a type is not transformed but ignored.
     *
     * @param typeDescription The type being ignored for transformation.
     * @param classLoader     The class loader which is loading this type.
     * @param module          The ignored type's module or {@code null} if the current VM does not support modules.
     */
    @Override
    public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {

    }

    /**
     * Invoked when an error has occurred during transformation.
     *
     * @param typeName    The type name of the instrumented type.
     * @param classLoader The class loader which is loading this type.
     * @param module      The instrumented type's module or {@code null} if the current VM does not support modules.
     * @param throwable   The occurred error.
     */
    @Override
    public void onError(String typeName, ClassLoader classLoader, JavaModule module, Throwable throwable) {
        System.out.println("onError method called, typeName:" + typeName + " throwable:" + throwable.getMessage());
        throwable.printStackTrace();
    }

    /**
     * Invoked after a class was attempted to be loaded, independently of its treatment.
     *
     * @param typeName    The binary name of the instrumented type.
     * @param classLoader The class loader which is loading this type.
     * @param module      The instrumented type's module or {@code null} if the current VM does not support modules.
     */
    @Override
    public void onComplete(String typeName, ClassLoader classLoader, JavaModule module) {

    }
}
