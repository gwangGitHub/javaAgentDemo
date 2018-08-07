package com.gwang.hotSwap.instrument.agentmain;

import com.gwang.hotSwap.instrument.byteBuddy.intercepter.EnterTemplate;
import com.gwang.hotSwap.instrument.byteBuddy.intercepter.ExitTemplate;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Created by wanggang on 2018/8/7.
 */
public class AgentMainByteBuddyTransformer implements AgentBuilder.Transformer {

    private String transMethod;

    public AgentMainByteBuddyTransformer(String transMethod) {
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
        System.out.println("transform method called");
        return builder.visit(Advice
                        .to(EnterTemplate.class)
                        .on(named(transMethod)))
                .visit(Advice
                        .to(ExitTemplate.class)
                        .on(named(transMethod)));
    }
}
