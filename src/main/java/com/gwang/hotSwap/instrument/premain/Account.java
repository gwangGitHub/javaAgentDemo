package com.gwang.hotSwap.instrument.premain;

/**
 * Created by wanggang on 2018/8/6.
 * 运行Accunt的main方法前需要配置JVM参数-javaagent:agent.jar的路径
 * 例如：-javaagent:Users/wanggang/Desktop/eclipseworkspace/javaAgentDemo/target/javaAgentDemo-1.0-SNAPSHOT.jar
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
