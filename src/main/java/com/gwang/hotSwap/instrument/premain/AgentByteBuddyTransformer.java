package com.gwang.hotSwap.instrument.premain;

import com.gwang.hotSwap.instrument.byteBuddy.intercepter.TimeInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Created by wanggang on 2018/8/7.
 */
public class AgentByteBuddyTransformer implements AgentBuilder.Transformer {

    private String transMethod;

    public AgentByteBuddyTransformer(String transMethod) {
        this.transMethod = transMethod;
    }

    /**
     * Allows for a transformation of a {@link net.bytebuddy.dynamic.DynamicType.Builder}.
     *
     * @param builder         The dynamic builder to transform.
     * @param typeDescription The description of the type currently being instrumented.
     * @param classLoader     The class loader of the instrumented class. Might be {@code null} to
     *                        represent the bootstrap class loader.
     * @return A transformed version of the supplied {@code builder}.
     */
    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
        System.out.println("AgentByteBuddyTransformer transform method called");
        return builder.method(named(transMethod))
                .intercept(MethodDelegation.to(TimeInterceptor.class));
    }
}
