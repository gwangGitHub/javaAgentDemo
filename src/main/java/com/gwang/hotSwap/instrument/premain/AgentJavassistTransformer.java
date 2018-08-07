package com.gwang.hotSwap.instrument.premain;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by wanggang on 2018/7/23.
 */
public class AgentJavassistTransformer implements ClassFileTransformer {

    private static final String TRANS_CLASS_NAME = "Account";
    private static final String TRANS_METHOD_NAME = "operation";

    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        System.out.println("AgentJavassistTransformer Transform " + className);
        ClassPool pool;
        CtClass cl = null;
        if (className.contains(TRANS_CLASS_NAME)) {
            System.out.println("Create Class " + className);
            try {
                pool = ClassPool.getDefault();

                cl = pool.makeClass(new java.io.ByteArrayInputStream(classfileBuffer));

//            CtMethod aop_method = pool.get("com.jdktest.instrument.AopMethods").
//                    getDeclaredMethod("aopMethod");
//            System.out.println(aop_method.getLongName());
//            CodeConverter convert = new CodeConverter();

                if (cl.isInterface() == false) {
                    CtMethod[] methods = cl.getDeclaredMethods();
                    for (int i = 0; i < methods.length; i++) {
                        if (methods[i].isEmpty() == false && isMethodNeedTrans(methods[i])) {
//                            AOPInsertMethodV1(methods[i]);
                            AOPInsertMethodV2(methods[i]);
                        }
                    }
                    transformed = cl.toBytecode();
                }
            } catch (Exception e) {
                System.err.println("Could not instrument  " + className
                        + ",  exception : " + e.getMessage());
            } finally {
                if (cl != null) {
                    cl.detach();
                }
            }
        }
        return transformed;
    }

    private boolean isMethodNeedTrans(CtMethod method) {
        if (method.getName().contains(TRANS_METHOD_NAME)) {
            return true;
        }
        return false;
    }

    private void AOPInsertMethodV1(CtMethod method) throws NotFoundException,CannotCompileException {
        System.out.println("AOPInsertMethodV1 trans Method: " + method.getName());
        //situation 1:添加监控时间
        method.instrument(new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                System.out.println("trans method: " + m.getMethodName());
                m.replace("{ long stime = System.currentTimeMillis(); $_ = $proceed($$);System.out.println(\""
                        + m.getClassName() + "." + m.getMethodName()
                        + " cost:\" + (System.currentTimeMillis() - stime) + \" ms\");}");
            }
        });
    }

    private void AOPInsertMethodV2(CtMethod method) throws NotFoundException,CannotCompileException{
        System.out.println("AOPInsertMethodV2 trans Method: " + method.getName());
        //situation 2:在方法体前后语句
        method.insertBefore("System.out.println(\"enter method\");");
        method.insertAfter("System.out.println(\"leave method\");");
    }
}
