package com.gwang.hotSwap.instrument.premain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * Created by wanggang on 2018/7/23.
 */
public class PremainDemo {
    static private Instrumentation instrument = null;
    /**
     * The agent class must implement a public static premain method similar in principle to the main application entry point.
     * After the Java Virtual Machine (JVM) has initialized,
     * each premain method will be called in the order the agents were specified,
     * then the real application main method will be called.
     **/
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("AopAgentTest.premain() was called.");

        /* Provides services that allow Java programming language agents to instrument programs running on the JVM.*/
        instrument = inst;

        /* ClassFileTransformer : An agent provides an implementation of this interface in order to transform class files.*/

        //example1:origin transformer
//        ClassFileTransformer trans = new AgentOriginTransformer();
        //example2:javassist transformer
        ClassFileTransformer trans = new AgentJavassistTransformer();

        System.out.println("Adding a javaagent premain instance to the JVM.");

        /*Registers the supplied transformer.*/
        instrument.addTransformer(trans);
    }
}
