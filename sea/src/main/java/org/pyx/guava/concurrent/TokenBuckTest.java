package org.pyx.guava.concurrent;

import java.util.stream.IntStream;

/**
 * 令牌桶测试
 * @author pyx
 * @date 2018/7/31
 */
public class TokenBuckTest {

    public static void main(String[] args) {
        TokenBuck tokenBuck = new TokenBuck(200);
        //目前测试时，让一个线程抢一次，不用循环抢
        //tokenBuck::buy 产生一个Runnable
        /*IntStream.range(0,300).forEach(i->{
            new Thread(tokenBuck::buy).run();
        });*/
        //循环抢
        IntStream.range(0,10).forEach(i->{
            new Thread(()->{
                for(;;){
                    tokenBuck.buy();
                }
            }).run();
        });

        try {
            Thread.sleep(60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
