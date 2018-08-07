package com.gwang.hotSwap.instrument.premain;

import com.gwang.hotSwap.instrument.byteBuddy.listener.ByteBuddyListener;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.nameEndsWith;

/**
 * Created by wanggang on 2018/7/23.
 */
public class PremainDemo {
    static private Instrumentation instrument = null;

    private static final String TRANS_CLASS_NAME = "Account";
    private static final String TRANS_METHOD_NAME = "operation";

    /**
     * The agent class must implement a public static premain method similar in principle to the main application entry point.
     * After the Java Virtual Machine (JVM) has initialized,
     * each premain method will be called in the order the agents were specified,
     * then the real application main method will be called.
     **/
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("PremainDemo.premain() was called.");

        //option1:use origin instrument
//        transWithInstument(inst);

        //option2:use byte buddy
        transWithByteBuddy();
    }

    private static void transWithInstument(Instrumentation inst) {
        /* Provides services that allow Java programming language agents to instrument programs running on the JVM.*/
        instrument = inst;

        /* ClassFileTransformer : An agent provides an implementation of this interface in order to transform class files.*/

        //example1:origin transformer
//        ClassFileTransformer trans = new AgentOriginTransformer();
        //example2:javassist transformer
        ClassFileTransformer trans = new AgentJavassistTransformer(TRANS_CLASS_NAME, TRANS_METHOD_NAME);

        System.out.println("Adding a javaagent premain instance to the JVM.");

        /*Registers the supplied transformer.*/
        instrument.addTransformer(trans);
    }

    private static void transWithByteBuddy() {
        ByteBuddyAgent.install();
        Instrumentation inst = ByteBuddyAgent.getInstrumentation();

        new AgentBuilder.Default()
                .type(nameEndsWith(TRANS_CLASS_NAME))
                .transform(new AgentByteBuddyTransformer(TRANS_METHOD_NAME))
                .with(new ByteBuddyListener())
                .installOnByteBuddyAgent();
    }
}
