package com.gwang.hotSwap.classLoader;

/**
 * Created by wanggang on 2018/8/4.
 */
public class Account {
    public void operation() {
        System.out.println("operation...");
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Account().operation();
    }
}