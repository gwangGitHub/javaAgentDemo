package com.gwang.hotSwap.classLoader;

/**
 * Created by wanggang on 2018/8/6.
 */
public class Account {
    public String operation(int a) {
        String result = "operation..." + a;
        System.out.println(result);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        int a = 0;
        while (true) {
            a++;
            new Account().operation(a);
        }
    }
}
