package com.gwang.hotSwap.classLoader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by wanggang on 2018/8/4.
 * 实验步骤：
 * 1.运行ClassLoaderDemo的main方法，这时候会不断重复打印出account类的输出
 * 2.修改Account类的输出
 * 3.重新compile一下Account类（可以调用ReCompileAccount的main方法，或者用idea的compile功能）
 * 4.再看ClassLoaderDemo的输出发现Account的输出变成新修改的输出，证明类被重新加载了
 */
public class ClassLoaderDemo {
    public static void main(String[] args)
            throws ClassNotFoundException, InterruptedException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        while (true) {
            ClassLoader loader = new ClassLoader() {
                @Override
                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    try {
                        String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

                        InputStream is = getClass().getResourceAsStream(fileName);
                        if (is == null) {
                            return super.loadClass(name);
                        }

                        byte[] b = new byte[is.available()];

                        is.read(b);
                        return defineClass(name, b, 0, b.length);

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new ClassNotFoundException(name);
                    }
                }
            };

            Class clazz = loader.loadClass("com.gwang.hotSwap.classLoader.Account");
            //TODO 为什么只能用同样包名com.gwang.hotSwap.classLoader下面的Account重新compile才能生效？
//            Class clazz = loader.loadClass("com.gwang.hotSwap.pojo.Account");
            Object account = clazz.newInstance();
//            account.getClass().getMethod("operation", new Class[]{}).invoke(account);
            account.getClass().getMethod("operation", new Class[]{int.class}).invoke(account, new Object[]{0});
//            Thread.sleep(5000);
        }
    }
}
