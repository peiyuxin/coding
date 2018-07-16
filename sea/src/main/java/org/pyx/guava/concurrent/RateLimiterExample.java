package org.pyx.guava.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.google.common.util.concurrent.RateLimiter;

import static java.lang.Thread.currentThread;

/**
 * @author pyx
 * @date 2018/7/17
 */
public class RateLimiterExample {
    //Guava  0.5的意思是 1秒中0.5次的操作，2秒1次的操作  从速度来限流，从每秒中能够执行的次数来
    private static final RateLimiter limiter = RateLimiter.create(0.5d);

    //同时只能有三个线程工作 Java1.5  从同时处理的线程个数来限流
    private static final Semaphore sem = new Semaphore(3);
    public static void testSemaphore(){
        try {
            sem.acquire();
            System.out.println(currentThread().getName()+" is doing work...");
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            sem.release();
            System.out.println(currentThread().getName()+" release the Semaphore.Other thread can get and do job");
        }
    }
    public static void runTestSemaphore(){
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0,10).forEach((i) -> {
            //RateLimiterExample::testSemaphore 这种写法是创建一个线程
            //RateLimiterExample::testSemaphore 等价与() ->RateLimiterExample.runTestSemaphore()     x->System.out.println(x)
            service.submit(() -> RateLimiterExample.runTestSemaphore());
            //service.submit(RateLimiterExample::testSemaphore);
        });
    }
}
