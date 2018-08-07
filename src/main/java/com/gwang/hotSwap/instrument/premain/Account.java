package com.gwang.hotSwap.instrument.premain;

import com.gwang.hotSwap.instrument.byteBuddy.annotation.TraceTime;

/**
 * Created by wanggang on 2018/8/6.
 * 运行Accunt的main方法前需要配置JVM参数-javaagent:agent.jar的路径
 * 例如：-javaagent:Users/wanggang/Desktop/eclipseworkspace/javaAgentDemo/target/javaAgentDemo-1.0-SNAPSHOT.jar
 * 如果在控制台直接运行的话命令如下：
 * 如果是运行jar包：java -jar target.jar -javaagent:/apps/java-agent.jar
 * 如果是运行class类：java -javaagent:/apps/java-agent.jar target.class
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

    @TraceTime
    public String say(String words, int sleep) {
        String result = words + " sleep time:" + sleep;
        System.out.println(result);
        try {
            Thread.sleep(sleep);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        int a = 0;
        while (true) {
            a++;
            Account account = new Account();
            account.operation(a);
            account.say("Hello", a);
        }
    }
}
