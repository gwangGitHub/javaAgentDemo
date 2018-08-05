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
public class AopAgentTransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        System.out.println("Transforming " + className);
        ClassPool pool;
        CtClass cl = null;
        if ("Account".equals(className)) {
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
                        System.out.println("Method: " + methods[i].getName());
                        if (methods[i].isEmpty() == false) {
                            System.out.println("Method not empty: " + methods[i].getName());
                            AOPInsertMethod(methods[i]);
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

    private void AOPInsertMethod(CtMethod method) throws NotFoundException,CannotCompileException {
        //situation 1:添加监控时间
        method.instrument(new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                System.out.println("trans method: " + m.getMethodName());
                m.replace("{ long stime = System.currentTimeMillis(); $_ = $proceed($$);System.out.println(\""
                        + m.getClassName() + "." + m.getMethodName()
                        + " cost:\" + (System.currentTimeMillis() - stime) + \" ms\");}");
            }
        });
        //situation 2:在方法体前后语句
      method.insertBefore("System.out.println(\"enter method\");");
      method.insertAfter("System.out.println(\"leave method\");");
    }
}
